/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityNanoFissionReactorCore extends TileEntityBase implements IInventory, IFluidHandler, IMultiBlockComponent {
    public static double cooledSaltHeatMultiplier = .01;
    public static double moltenSaltHeatMultiplier = .02;
    public static double unstableTemperatureThreshold = .66;
    public static double criticalTemperatureThreshold = .83;
    public static double solidSaltToThoriumRatio = .2;
    public static double enviroHeatLossMultiplier = .99;
    public static int cooledSaltTankMaxAmount = 100000;
    public static int moltenSaltTankMaxAmount = 100000;
    public static int thoriumStoreMaxAmount = 10000;
    public static int temperatureMaxAmount = 3000;
    public static final int heatSlot = 0;
    public static final int thoriumSlot = 2;
    public static final int saltSlot = 1;
    @FemtocraftDataUtils.Saveable
    private BaseInventory inventory;
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info;
    @FemtocraftDataUtils.Saveable
    private FluidTank cooledSaltTank;
    @FemtocraftDataUtils.Saveable
    private FluidTank moltenSaltTank;
    private int thoriumStoreMax;
    @FemtocraftDataUtils.Saveable
    private int thoriumStoreCurrent;
    private int temperatureMax;

    public float getThoriumConcentrationTarget() {
        return thoriumConcentrationTarget;
    }

    public void setThoriumConcentrationTarget(float thoriumConcentrationTarget) {
        this.thoriumConcentrationTarget = thoriumConcentrationTarget;
    }

    public float getTemperatureCurrent() {
        return temperatureCurrent;
    }

    public void setTemperatureCurrent(int temperatureCurrent) {
        this.temperatureCurrent = temperatureCurrent;
    }

    public int getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(int temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public float getThoriumStoreCurrent() {
        return thoriumStoreCurrent;
    }

    public void setThoriumStoreCurrent(int thoriumStoreCurrent) {
        this.thoriumStoreCurrent = thoriumStoreCurrent;
    }

    @FemtocraftDataUtils.Saveable
    private float temperatureCurrent;
    @FemtocraftDataUtils.Saveable
    private float thoriumConcentrationTarget;

    public TileEntityNanoFissionReactorCore() {
        inventory = new BaseInventory(3);
        info = new MultiBlockInfo();
        cooledSaltTank = new FluidTank(cooledSaltTankMaxAmount);
        moltenSaltTank = new FluidTank(moltenSaltTankMaxAmount);
        thoriumStoreMax = thoriumStoreMaxAmount;
        temperatureMax = temperatureMaxAmount;
        thoriumStoreCurrent = 0;
        temperatureCurrent = 0;
        thoriumConcentrationTarget = 0;
    }

    public void abortReaction() {
        thoriumStoreCurrent = 0;
        cooledSaltTank.setFluid(null);
        moltenSaltTank.setFluid(null);
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        loseHeat();
        gainHeat();
        meltThorium();
        meltSalt();
        heatSalt();
        meltWorld();
    }

    private void heatSalt() {

    }

    private void meltWorld() {
        if (temperatureCurrent > temperatureMax) {

        }
        else if (temperatureCurrent > temperatureMax * criticalTemperatureThreshold) {

        }
        else if (temperatureCurrent > temperatureMax * unstableTemperatureThreshold) {

        }
    }

    private void meltThorium() {
        if (getThoriumConcentration() < thoriumConcentrationTarget) {
            ItemStack item = getStackInSlot(thoriumSlot);
            if (item == null) {
                return;
            }
            FemtocraftPowerUtils.FissionReactorReagent reagent = FemtocraftPowerUtils.getThoriumSource(item);
            if (reagent == null) {
                return;
            }
            if (reagent.item.stackSize <= item.stackSize && getTemperatureCurrent() >= reagent.temp && (thoriumStoreMax - thoriumStoreCurrent) >= reagent.amount) {
                decrStackSize(thoriumSlot, reagent.item.stackSize);
                temperatureCurrent -= reagent.temp;
                thoriumStoreCurrent += reagent.amount;
            }
        }
    }

    public float getThoriumConcentration() {
        return ((float) thoriumStoreCurrent / (float) thoriumStoreMax);
    }

    private void meltSalt() {
        ItemStack item = getStackInSlot(saltSlot);
        if (item == null) {
            return;
        }
        FemtocraftPowerUtils.FissionReactorReagent reagent = FemtocraftPowerUtils.getSaltSource(item);
        if (reagent == null) {
            return;
        }
        if (reagent.item.stackSize <= item.stackSize && getTemperatureCurrent() >= reagent.temp && (cooledSaltTank.getCapacity() - getCooledSaltAmount()) >= reagent.amount && thoriumStoreCurrent >= (reagent.amount * solidSaltToThoriumRatio)) {
            decrStackSize(saltSlot, reagent.item.stackSize);
            temperatureCurrent -= reagent.temp;
            addCooledSalt(reagent.amount);
            thoriumStoreCurrent -= reagent.amount * solidSaltToThoriumRatio;
        }
    }

    public int addCooledSalt(int amount) {
        return cooledSaltTank.fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt, amount), true);
    }

    public int addMoltenSalt(int amount) {
        return moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt, amount), true);
    }

    private void gainHeat() {
        temperatureCurrent += getCooledSaltAmount() * cooledSaltHeatMultiplier * (1. + getThoriumConcentration());
        temperatureCurrent += getMoltenSaltAmount() * moltenSaltHeatMultiplier * (1. + getThoriumConcentration());
    }

    public int getMoltenSaltAmount() {
        return moltenSaltTank.getFluidAmount();
    }

    public int getCooledSaltAmount() {
        return cooledSaltTank.getFluidAmount();
    }

    private void loseHeat() {
        temperatureCurrent *= enviroHeatLossMultiplier;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        FluidStack fill = null;
        if (resource.getFluid() == Femtocraft.fluidCooledContaminatedMoltenSalt) {
            if (doFill) {

            }

        }
        else if (resource.getFluid() == Femtocraft.fluidCooledMoltenSalt) {
            fill = resource;
        }
        else {
            return 0;
        }

        int result = cooledSaltTank.fill(fill, doFill);
        if (result > 0) {
            setModified();
        }
        return result;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource.getFluid() != Femtocraft.fluidMoltenSalt) return null;
        return drain(from, resource.amount, doDrain);
    }


    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack result = moltenSaltTank.drain(maxDrain, doDrain);
        if (result != null && result.amount > 0) {
            setModified();
        }
        return result;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == Femtocraft.fluidCooledContaminatedMoltenSalt || fluid == Femtocraft.fluidCooledMoltenSalt;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluid == Femtocraft.fluidMoltenSalt;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{
                cooledSaltTank.getInfo(), moltenSaltTank.getInfo()
        };
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        ItemStack stack = inventory.decrStackSize(i, j);
        setModified();
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory.setInventorySlotContents(i, itemstack);
        setModified();
    }

    @Override
    public String getInvName() {
        return inventory.getInvName();
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return canPlayerUse(entityplayer);
    }

    @Override
    public void openChest() {
        inventory.openChest();
    }

    @Override
    public void closeChest() {
        inventory.closeChest();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return inventory.isItemValidForSlot(i, itemstack);
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean ret = info.formMultiBlock(world, x, y, z);
        if (ret) {
            setModified();
            setUpdate();
        }
        return ret;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean ret = info.breakMultiBlock(world, x, y, z);
        if (ret) {
            setModified();
            setUpdate();
        }
        return ret;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }
}
