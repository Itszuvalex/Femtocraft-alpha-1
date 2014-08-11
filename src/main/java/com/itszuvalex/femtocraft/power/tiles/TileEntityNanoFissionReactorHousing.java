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

import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityNanoFissionReactorHousing extends TileEntityBase implements IMultiBlockComponent, IFluidHandler, IInventory {
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info;

    public TileEntityNanoFissionReactorHousing() {
        info = new MultiBlockInfo();
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

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (info.isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return 0;
            return core.fill(from, resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (info.isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.drain(from, resource, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (info.isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.drain(from, maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (info.isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            return core != null && core.canFill(from, fluid);
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (info.isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            return core != null && core.canDrain(from, fluid);
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.getTankInfo(from);
        }
        return new FluidTankInfo[0];
    }

    @Override
    public int getSizeInventory() {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return 0;
            return core.getSizeInventory();
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.getStackInSlot(i);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.decrStackSize(i, j);
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.getStackInSlotOnClosing(i);
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return;
            core.setInventorySlotContents(i, itemstack);
        }
    }

    @Override
    public String getInvName() {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.getInvName();
        }
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            return core != null && core.isInvNameLocalized();
        }
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return 0;
            return core.getInventoryStackLimit();
        }
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            return core != null && core.isUseableByPlayer(entityplayer);
        }
        return false;
    }

    @Override
    public void openChest() {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return;
            core.openChest();
        }
    }

    @Override
    public void closeChest() {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (core == null) return;
            core.closeChest();
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (info.isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            return core != null && core.isItemValidForSlot(i, itemstack);
        }
        return false;
    }
}
