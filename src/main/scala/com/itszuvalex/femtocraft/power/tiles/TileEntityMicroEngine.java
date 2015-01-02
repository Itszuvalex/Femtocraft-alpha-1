///*
// * ******************************************************************************
// *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// *  * Itszuvalex@gmail.com
// *  *
// *  * This program is free software; you can redistribute it and/or
// *  * modify it under the terms of the GNU General Public License
// *  * as published by the Free Software Foundation; either version 2
// *  * of the License, or (at your option) any later version.
// *  *
// *  * This program is distributed in the hope that it will be useful,
// *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  * GNU General Public License for more details.
// *  *
// *  * You should have received a copy of the GNU General Public License
// *  * along with this program; if not, write to the Free Software
// *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// *  *****************************************************************************
// */
//
//package com.itszuvalex.femtocraft.power.tiles;
//
//import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
//import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityIndustry;
//import com.itszuvalex.femtocraft.api.EnumTechLevel;
//import com.itszuvalex.femtocraft.utils.BaseInventory;
//import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
//import cpw.mods.fml.common.registry.GameRegistry;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.ISidedInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.common.ForgeDirection;
//
//public class TileEntityMicroEngine extends TileEntityBaseEntityIndustry
//        implements ISidedInventory {
//    public static int powerPerTick = 1;
//    public static float powerPerTickMultipler = 1.f;
//    public static float cookTimeMultipler = 1.f;
//    private static int inputSlot = 0;
//    @FemtocraftDataUtils.Saveable
//    private int maxHeat = 0;
//    @FemtocraftDataUtils.Saveable
//    private int currentHeat = 0;
//    @FemtocraftDataUtils.Saveable
//    private BaseInventory inventory;
//
//    public TileEntityMicroEngine() {
//        super();
//        setTechLevel(EnumTechLevel.MICRO);
//        setMaxStorage(1000);
//        inventory = new BaseInventory(1);
//    }
//
//    @java.lang.Override
//    public boolean hasGUI() {
//        return true;
//    }
//
//    @Override
//    public int getGuiID() {
//        return FemtocraftGuiHandler.MicroEngineGuiID;
//    }
//
//    @java.lang.Override
//    public boolean isWorking() {
//        return currentHeat > 0;
//    }
//
//    @java.lang.Override
//    protected boolean canStartWork() {
//        return currentHeat == 0 && inventory.getStackInSlot(inputSlot) != null &&
//                GameRegistry.getFuelValue
//                        (inventory
//                                .getStackInSlot(inputSlot)) > 0;
//    }
//
//    @java.lang.Override
//    protected void startWork() {
//        maxHeat = currentHeat = (int) (cookTimeMultipler * GameRegistry.getFuelValue
//                (inventory
//                        .getStackInSlot(inputSlot)));
//        inventory.decrStackSize(inputSlot, 1);
//    }
//
//    @java.lang.Override
//    protected void continueWork() {
//        --currentHeat;
//        charge(ForgeDirection.UNKNOWN, (int) (powerPerTick * powerPerTickMultipler));
//    }
//
//    @java.lang.Override
//    protected boolean canFinishWork() {
//        return currentHeat <= 0 && maxHeat > 0;
//    }
//
//    @java.lang.Override
//    protected void finishWork() {
//        currentHeat = maxHeat = 0;
//    }
//
//    @java.lang.Override
//    public int[] getAccessibleSlotsFromSide(int var1) {
//        return new int[0];
//    }
//
//    @java.lang.Override
//    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
//        return GameRegistry.getFuelValue(itemstack) > 0;
//    }
//
//    @java.lang.Override
//    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
//        return true;
//    }
//
//    @java.lang.Override
//    public int getSizeInventory() {
//        return inventory.getSizeInventory();
//    }
//
//    @java.lang.Override
//    public ItemStack getStackInSlot(int i) {
//        return inventory.getStackInSlot(i);
//    }
//
//    @java.lang.Override
//    public ItemStack decrStackSize(int i, int j) {
//        return inventory.decrStackSize(i, j);
//    }
//
//    @java.lang.Override
//    public ItemStack getStackInSlotOnClosing(int i) {
//        return inventory.getStackInSlotOnClosing(i);
//    }
//
//    @java.lang.Override
//    public void setInventorySlotContents(int i, ItemStack itemstack) {
//        inventory.setInventorySlotContents(i, itemstack);
//    }
//
//    @java.lang.Override
//    public String getInvName() {
//        return inventory.getInvName();
//    }
//
//    @java.lang.Override
//    public boolean isInvNameLocalized() {
//        return false;
//    }
//
//    @java.lang.Override
//    public int getInventoryStackLimit() {
//        return inventory.getInventoryStackLimit();
//    }
//
//    @Override
//    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
//        return canPlayerUse(entityplayer);
//    }
//
//    @java.lang.Override
//    public void openChest() {
//
//    }
//
//    @java.lang.Override
//    public void closeChest() {
//
//    }
//
//    @java.lang.Override
//    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
//        return GameRegistry.getFuelValue(itemstack) > 0;
//    }
//}
