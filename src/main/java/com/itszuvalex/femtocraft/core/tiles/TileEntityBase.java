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

package com.itszuvalex.femtocraft.core.tiles;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity {
    private
    @FemtocraftDataUtils.Saveable(desc = true, item = true)
    String owner;

    public TileEntityBase() {
        super();
        owner = null;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        FemtocraftDataUtils.loadObjectFromNBT(par1nbtTagCompound, this,
                FemtocraftDataUtils.EnumSaveType.WORLD);
        // owner = par1nbtTagCompound.getString(NBT_TAG);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
        FemtocraftDataUtils.saveObjectToNBT(par1nbtTagCompound, this,
                FemtocraftDataUtils.EnumSaveType.WORLD);
        // par1nbtTagCompound.setString(NBT_TAG, owner);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote) {
            return;
        }
        if (!shouldTick()) {
            return;
        }

        femtocraftServerUpdate();
    }

    public boolean shouldTick() {
        return !FemtocraftConfigs.requirePlayersOnlineForTileEntityTicks
               || FemtocraftUtils.isPlayerOnline(owner);
    }

    /**
     * Gated update call. This will only be called on the server, and only if the tile's {@link #shouldTick()} returns
     * true. This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to
     * update.
     */
    public void femtocraftServerUpdate() {
    }

    @Override
    public Packet getDescriptionPacket() {
        if (!hasDescription()) {
            return null;
        }

        NBTTagCompound compound = new NBTTagCompound();
        saveToDescriptionCompound(compound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, compound);
    }

    public boolean hasDescription() {
        return true;
    }

    public void saveToDescriptionCompound(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this,
                FemtocraftDataUtils.EnumSaveType.DESCRIPTION);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        super.onDataPacket(net, pkt);

        handleDescriptionNBT(pkt.data);
    }

    public void handleDescriptionNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                FemtocraftDataUtils.EnumSaveType.DESCRIPTION);
    }

    public String getPacketChannel() {
        return Femtocraft.ID;
    }

    public void loadInfoFromItemNBT(NBTTagCompound compound) {
        if (compound == null) {
            return;
        }
        FemtocraftDataUtils
                .loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.ITEM);
    }

    public void saveInfoToItemNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.ITEM);
    }

    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (hasGUI()) {
            par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, xCoord,
                    yCoord, zCoord);
            return true;
        }
        return false;
    }

    public boolean hasGUI() {
        return false;
    }

//    public boolean canPlayerUse(EntityPlayer par1EntityPlayer) {
//        boolean inRange = this.worldObj.getBlockTileEntity(this.xCoord,
//                this.yCoord, this.zCoord) == this
//                          && par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D,
//                (double) this.yCoord + 0.5D,
//                (double) this.zCoord + 0.5D) <= 64.0D;
//        boolean isOwner = owner == null || owner.isEmpty()
//                          || (owner.equals(par1EntityPlayer.username));
//        boolean isAssist = (owner != null && !owner.isEmpty())
//                           && Femtocraft.assistantManager.isPlayerAssistant(owner, par1EntityPlayer.username);
//        boolean isOp = ((MinecraftServer.getServer() != null && MinecraftServer.getServer()
//                .getConfigurationManager()
//                .isPlayerOpped(par1EntityPlayer.username)) ||
//                        par1EntityPlayer.capabilities.isCreativeMode);
//        return inRange
//               && (isOwner || isAssist || isOp);
//    }

    public boolean canPlayerUse(EntityPlayer player) {
        return player != null
               && (getOwner() == null
                   || getOwner().equals(player.username) ||
                   Femtocraft.assistantManager.isPlayerAssistant(getOwner(), player.username)
                   || (MinecraftServer.getServer() != null && MinecraftServer.getServer()
                .getConfigurationManager()
                .isPlayerOpped(player.username)) || player.capabilities.isCreativeMode);
    }

    public Object getMod() {
        return Femtocraft.instance;
    }

    /**
     * @return GuiID, if GUI handler uses ids and not checking instanceof
     */
    public int getGuiID() {
        return -1;
    }

    protected void setRenderUpdate() {
        if (worldObj != null) {
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }

    protected void setUpdate() {
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    protected void setModified() {
        if (worldObj != null) {
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
        }
    }
}
