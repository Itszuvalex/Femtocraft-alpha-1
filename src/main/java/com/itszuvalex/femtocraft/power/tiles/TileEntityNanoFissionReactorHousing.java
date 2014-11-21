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

import com.itszuvalex.femtocraft.FemtocraftGuiConstants;
import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityNanoFissionReactorHousing extends TileEntityBase implements IMultiBlockComponent, IFluidHandler,
        IInventory {
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info;

    public TileEntityNanoFissionReactorHousing() {
        info = new MultiBlockInfo();
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiConstants.NanoFissionReactorGuiID();
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(),
                    info.z());
            // Big Oops? Or chunk unloaded...despite having player activating it
            // >.>
            if (te == null) {
                return false;
            }

            par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, info.x(),
                    info.y(), info.z());
            return true;
        }
        return false;
    }


    @Override
    public boolean isValidMultiBlock() {
        return info != null && info.isValidMultiBlock();
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
    public int fill(ForgeDirection from, FluidStack resource,
                    boolean doFill) {
        if (isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return 0;
            int result = core.fill(from, resource, doFill);
            if (result > 0) {
                setModified();
            }
            return result;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        if (isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            FluidStack result = core.drain(from, resource, doDrain);
            if (result != null) {
                setModified();
            }
            return result;
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain,
                            boolean doDrain) {
        if (isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            FluidStack result = core.drain(from, maxDrain, doDrain);
            if (result != null) {
                setModified();
            }
            return result;
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (info.isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getTileEntity(info.x(), info.y(), info.z());
            return core != null && core.canFill(from, fluid);
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getTileEntity(info.x(), info.y(), info.z());
            return core != null && core.canDrain(from, fluid);
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (isValidMultiBlock()) {
            IFluidHandler core = (IFluidHandler) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.getTankInfo(from);
        }
        return new FluidTankInfo[0];
    }

    @Override
    public int getSizeInventory() {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return 0;
            return core.getSizeInventory();
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            ItemStack result = core.getStackInSlot(i);
            if (result != null) {
                setModified();
                this.onInventoryChanged();
            }
            return result;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            ItemStack result = core.decrStackSize(i, j);
            if (result != null) {
                setModified();
                this.onInventoryChanged();
            }
            return result;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.getStackInSlotOnClosing(i);
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return;
            core.setInventorySlotContents(i, itemstack);
            setModified();
            this.onInventoryChanged();
        }
    }

    @Override
    public String getInventoryName() {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return null;
            return core.getInventoryName();
        }
        return null;
    }

    @Override
    public boolean hasGUI() {
        return isValidMultiBlock();
    }

    @Override
    public boolean hasCustomInventoryName() {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            return core != null && core.hasCustomInventoryName();
        }
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return 0;
            return core.getInventoryStackLimit();
        }
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            return core != null && core.isUseableByPlayer(entityplayer);
        }
        return false;
    }

    @Override
    public void openInventory() {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return;
            core.openInventory();
        }
    }

    @Override
    public void closeInventory() {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (core == null) return;
            core.closeInventory();
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (isValidMultiBlock()) {
            IInventory core = (IInventory) worldObj.getTileEntity(info.x(), info.y(), info.z());
            return core != null && core.isItemValidForSlot(i, itemstack);
        }
        return false;
    }
}
