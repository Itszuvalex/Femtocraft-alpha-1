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

import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.api.IInterfaceDevice;
import com.itszuvalex.femtocraft.api.IPowerBlockContainer;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityFemtoCubePort extends TileEntityPowerBase implements
                                                                 IMultiBlockComponent {
    private static int storage = 50000000;
    public
    @Saveable(desc = true)
    boolean output;
    private
    @Saveable(desc = true)
    MultiBlockInfo info;

    public TileEntityFemtoCubePort() {
        super();
        info = new MultiBlockInfo();
        setMaxStorage(storage);
        setTechLevel(EnumTechLevel.FEMTO);
        output = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityPowerBase#canAcceptPowerOfLevel(femtocraft
     * .managers.research.EnumTechLevel,
     * net.minecraftforge.common.ForgeDirection)
     */
    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level,
                                         ForgeDirection from) {
        if (!info.isValidMultiBlock()) {
            return false;
        }
        return super.canAcceptPowerOfLevel(level, from);
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityPowerBase#getCurrentPower()
     */
    @Override
    public int getCurrentPower() {
        if (info.isValidMultiBlock()) {
            if (isController()) {
                return super.getCurrentPower();
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.getCurrentPower();
            }
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityPowerBase#getMaxPower()
     */
    @Override
    public int getMaxPower() {
        if (info.isValidMultiBlock()) {
            if (isController()) {
                return super.getMaxPower();
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.getMaxPower();
            }
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityPowerBase#getFillPercentage()
     */
    @Override
    public float getFillPercentage() {
        if (info.isValidMultiBlock()) {
            if (isController()) {
                return super.getFillPercentage();
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.getFillPercentage();
            }
        }
        return super.getFillPercentage();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityPowerBase#getFillPercentageForCharging
     * (net.minecraftforge.common.ForgeDirection)
     */
    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            return output ? 1.f : 0.f;
        }
        return 1.f;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityPowerBase#getFillPercentageForOutput
     * (net.minecraftforge.common.ForgeDirection)
     */
    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        if (info.isValidMultiBlock()) {
            return output ? 1.f : 0.f;
        }
        return 0.f;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityPowerBase#canCharge(net.minecraftforge
     * .common.ForgeDirection)
     */
    @Override
    public boolean canCharge(ForgeDirection from) {
        if (!info.isValidMultiBlock() || output) {
            return false;
        }
        return super.canCharge(from);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityPowerBase#canConnect(net.minecraftforge
     * .common.ForgeDirection)
     */
    @Override
    public boolean canConnect(ForgeDirection from) {
        if (!info.isValidMultiBlock()) {
            return false;
        }
        return super.canConnect(from);
    }

    /*
       * (non-Javadoc)
       *
       * @see
       * TileEntityPowerBase#charge(net.minecraftforge.
       * common.ForgeDirection, int)
       */
    @Override
    public int charge(ForgeDirection from, int amount) {
        if (info.isValidMultiBlock() && !output) {
            if (isController()) {
                return super.charge(from, amount);
            }

            TileEntityFemtoCubePort fc = (TileEntityFemtoCubePort) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.controllerCharge(from, amount);
            }
        }
        return 0;
    }

    private int controllerCharge(ForgeDirection from, int amount) {
        if (isController()) {
            return super.charge(from, amount);
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityPowerBase#consume(int)
     */
    @Override
    public boolean consume(int amount) {
        if (info.isValidMultiBlock() && output) {
            if (isController()) {
                return super.consume(amount);
            }

            TileEntityFemtoCubePort fc = (TileEntityFemtoCubePort) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.controllerConsume(amount);
            }
        }
        return false;
    }

    private boolean controllerConsume(int amount) {
        return isController() && super.consume(amount);
    }

    private boolean isController() {
        return info.isValidMultiBlock()
                && ((info.x() == xCoord) && (info.y() == yCoord) && (info.z() == zCoord));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBase#handleDescriptionNBT(net.minecraft
     * .nbt.NBTTagCompound)
     */
    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        // info.loadFromNBT(compound.getCompoundTag("info"));
        // output = compound.getBoolean("output");

        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBase#onSideActivate(net.minecraft.entity
     * .player.EntityPlayer, int)
     */
    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (canPlayerUse(par5EntityPlayer) && info.isValidMultiBlock()) {
            ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
            if (item != null
                    && item.getItem() instanceof IInterfaceDevice
                    && ((IInterfaceDevice) item.getItem()).getInterfaceLevel().tier >= EnumTechLevel.FEMTO.tier) {
                output = !output;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return true;
            }
            else {
                par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj,
                        info.x(), info.y(), info.z());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasGUI() {
        return info.isValidMultiBlock();
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.FemtoCubeGuiID;
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = info.formMultiBlock(world, x, y, z);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
                worldObj.getBlockId(xCoord, yCoord, zCoord));
        return result;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean result = info.breakMultiBlock(world, x, y, z);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
                worldObj.getBlockId(xCoord, yCoord, zCoord));
        return result;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }
}