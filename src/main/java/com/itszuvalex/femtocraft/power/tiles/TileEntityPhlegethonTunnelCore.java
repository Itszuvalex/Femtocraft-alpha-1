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
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.api.power.IPhlegethonTunnelAddon;
import com.itszuvalex.femtocraft.api.power.IPhlegethonTunnelComponent;
import com.itszuvalex.femtocraft.api.power.IPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler;
import com.itszuvalex.femtocraft.network.messages.MessagePhlegethonTunnelCore;
import com.itszuvalex.femtocraft.sound.FemtocraftSoundManager;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
public class TileEntityPhlegethonTunnelCore extends TileEntityPowerProducer implements IPhlegethonTunnelCore,
        IInventory {
    public static float PowerGenBase = 150;
    public static int ContainerMax = 100000;
    @FemtocraftDataUtils.Saveable(desc = true)
    private boolean active = false;
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info = new MultiBlockInfo();
    @FemtocraftDataUtils.Saveable
    private BaseInventory inventory = new BaseInventory(1);

    @SideOnly(Side.CLIENT)
    private ISound sound = FemtocraftSoundManager.makePhlegethonSound(xCoord, yCoord, zCoord);

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(EnumTechLevel.FEMTO, ContainerMax);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public float getPowerGenBase() {
        return (float) (PowerGenBase / Math.log1p(getHeight()));
    }


    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        boolean wasActive = isActive();
        boolean wasMultiblock = isValidMultiBlock();
        super.handleDescriptionNBT(compound);
        setRenderUpdate();
        if ((wasActive && !isActive()) || (wasMultiblock && !isValidMultiBlock())) {
            Femtocraft.soundManager().stopSound(sound);
        } else if (!wasActive && isActive()) {
            Femtocraft.soundManager().playSound(sound);
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (worldObj.isRemote && isActive()) {
            Femtocraft.soundManager().stopSound(sound);
        }
    }

    @Override
    public float getTotalPowerGen() {
        float power = getPowerGenBase();
        for (ForgeDirection dir :
                ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX,
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


    @Override
    public boolean activate() {
        if (isActive()) {
            return false;
        }
        if (worldObj.isRemote) {
            return false;
        }

        ItemStack item = inventory.getStackInSlot(0);
        if (item == null) return false;

        if (item.getItem() != Femtocraft.itemPhlegethonTunnelPrimer()) {
            return false;
        }

        inventory.setInventorySlotContents(0, null);
        active = true;
        setModified();
        setUpdate();
        notifyTunnelOfChange(active);
        return true;
    }

    @Override
    public boolean deactivate() {
        if (!isActive()) {
            return false;
        }
        if (worldObj.isRemote) {
            return false;
        }

        active = false;
        setModified();
        setUpdate();
        notifyTunnelOfChange(active);
        return true;
    }

    private void notifyTunnelOfChange(boolean status) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    TileEntity te = worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z);
                    if (te instanceof IPhlegethonTunnelComponent) {
                        ((IPhlegethonTunnelComponent) te).onCoreActivityChange(status);
                    }
                }
            }
        }
    }

    @Override
    public void onChunkUnload() {
        if (worldObj.isRemote && isActive()) {
            Femtocraft.soundManager().stopSound(sound);
        }
        super.onChunkUnload();
    }

    @Override
    public void femtocraftServerUpdate() {
        if (isActive()) {
            charge((int) getTotalPowerGen());
        }
    }

    @Override
    public boolean isValidMultiBlock() {
        return info != null && info.isValidMultiBlock();
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
                worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.lava);
            } else {
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
        return this.canAcceptPowerOfLevel(level, ForgeDirection.UNKNOWN);
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        return isValidMultiBlock() && super.canCharge(from);
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        return isValidMultiBlock() && super.canConnect(from);
    }

    @Override
    public EnumTechLevel getTechLevel() {
        return super.getTechLevel(ForgeDirection.UNKNOWN);
    }

    @Override
    public float getFillPercentageForCharging() {
        return super.getFillPercentageForCharging(ForgeDirection.UNKNOWN);
    }

    @Override
    public float getFillPercentageForOutput() {
        return super.getFillPercentageForOutput(ForgeDirection.UNKNOWN);
    }

    @Override
    public boolean canCharge() {
        return isValidMultiBlock() && super.canCharge(ForgeDirection.UNKNOWN);
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        int charge = super.charge(from, amount);
        if (charge > 0) {
            setModified();
        }
        return charge;
    }

    @Override
    public int charge(int amount) {
        return this.charge(ForgeDirection.UNKNOWN, amount);
    }

    @Override
    public boolean consume(int amount) {
        boolean result = super.consume(amount);
        if (result) {
            setModified();
        }
        return result;
    }

    @Override
    public void onCoreActivityChange(boolean active) {
        setUpdate();
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
        onInventoryChanged();
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
        onInventoryChanged();
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return inventory.hasCustomInventoryName();
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
    public void openInventory() {
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return !isActive() && inventory.isItemValidForSlot(i, itemstack);
    }

    public void onActivateClick() {
        FemtocraftPacketHandler.INSTANCE().sendToServer(getMessage(true));
    }

    public void onDeactivateClick() {
        FemtocraftPacketHandler.INSTANCE().sendToServer(getMessage(false));
    }

    private MessagePhlegethonTunnelCore getMessage(boolean active) {
        return new MessagePhlegethonTunnelCore(xCoord, yCoord, zCoord, worldObj.provider.dimensionId, active);
    }

    public void handlePacket(boolean active) {
        if (active) {
            activate();
        } else {
            deactivate();
        }
    }
}
