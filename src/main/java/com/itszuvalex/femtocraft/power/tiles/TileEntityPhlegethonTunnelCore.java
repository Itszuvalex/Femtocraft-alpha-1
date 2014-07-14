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
import com.itszuvalex.femtocraft.api.IPhlegethonTunnelAddon;
import com.itszuvalex.femtocraft.api.IPhlegethonTunnelComponent;
import com.itszuvalex.femtocraft.api.IPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.api.PowerContainer;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
public class TileEntityPhlegethonTunnelCore extends TileEntityBase implements IPhlegethonTunnelCore, IInventory {
    public static float PowerGenBase = 150;
    public static int ContainerMax = 100000;
    @FemtocraftDataUtils.Saveable(desc = true)
    private boolean active = false;
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info = new MultiBlockInfo();
    @FemtocraftDataUtils.Saveable
    private PowerContainer container = new PowerContainer();
    @FemtocraftDataUtils.Saveable
    private BaseInventory inventory = new BaseInventory(1);

    public TileEntityPhlegethonTunnelCore() {
        container.setTechLevel(EnumTechLevel.FEMTO);
        container.setMaxPower(ContainerMax);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public float getPowerGenBase() {
        return (float) (PowerGenBase / Math.log(getHeight() + 1));
    }

    @Override
    public float getTotalPowerGen() {
        float power = getPowerGenBase();
        for (ForgeDirection dir :
                ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (te instanceof IPhlegethonTunnelAddon) {
                power += ((IPhlegethonTunnelAddon) te).getPowerContribution(this);
            }
        }
        return power;
    }

    @Override
    public int getHeight() {
        return yCoord;
    }

    public void sendActivateMessage() {
        if (worldObj.isRemote) {

        }
    }


    @Override
    public boolean activate() {
        if (isActive()) {
            return false;
        }
        if (!worldObj.isRemote) {
            return false;
        }

        ItemStack item = inventory.getStackInSlot(0);
        if (item == null) return false;

        if (item.getItem() != Femtocraft.itemPhlegethonTunnelPrimer) {
            return false;
        }

        inventory.setInventorySlotContents(0, null);
        active = true;
        setModified();
        notifyTunnelOfChange(active);
        return true;
    }

    @Override
    public boolean deactivate() {
        if (!isActive()) {
            return false;
        }
        if (!worldObj.isRemote) {
            return false;
        }

        active = false;
        setModified();
        notifyTunnelOfChange(active);
        return true;
    }

    private void notifyTunnelOfChange(boolean status) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    TileEntity te = worldObj.getBlockTileEntity(xCoord + x, yCoord + y, zCoord + z);
                    if (te instanceof IPhlegethonTunnelComponent) {
                        ((IPhlegethonTunnelComponent) te).onCoreActivityChange(status);
                    }
                }
            }
        }
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        if (active) {
            charge((int) getTotalPowerGen());
        }
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean val = info.formMultiBlock(world, x, y, z);
        if (val) {
            setModified();
            setUpdate();
        }
        return val;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean val = info.breakMultiBlock(world, x, y, z);
        if (val) {
            if (isActive()) {
                worldObj.setBlock(xCoord, yCoord, zCoord, Block.lavaStill.blockID);
            }
            else {
                setModified();
                setUpdate();
            }
        }
        return val;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level) {
        return container.canAcceptPowerOfLevel(level);
    }

    @Override
    public EnumTechLevel getTechLevel() {
        return container.getTechLevel();
    }

    @Override
    public int getCurrentPower() {
        return container.getCurrentPower();
    }

    @Override
    public int getMaxPower() {
        return container.getMaxPower();
    }

    @Override
    public float getFillPercentage() {
        return container.getFillPercentage();
    }

    @Override
    public float getFillPercentageForCharging() {
        return container.getFillPercentageForCharging();
    }

    @Override
    public float getFillPercentageForOutput() {
        return container.getFillPercentageForOutput();
    }

    @Override
    public boolean canCharge() {
        return container.canCharge();
    }

    @Override
    public int charge(int amount) {
        int charge = container.charge(amount);
        if (charge >= 0) {
            setModified();
        }
        return charge;
    }

    @Override
    public boolean consume(int amount) {
        boolean result = container.consume(amount);
        if (result) {
            setModified();
        }
        return result;
    }

    @Override
    public void onCoreActivityChange(boolean active) {

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
        ItemStack is = inventory.decrStackSize(i, j);
        setModified();
        return is;
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
        return inventory.isInvNameLocalized();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return inventory.isUseableByPlayer(entityplayer);
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
        return !isActive() && inventory.isItemValidForSlot(i, itemstack);
    }
}
