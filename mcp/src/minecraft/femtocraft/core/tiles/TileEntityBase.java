/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.core.tiles;

import femtocraft.Femtocraft;
import femtocraft.FemtocraftConfigs;
import femtocraft.utils.FemtocraftDataUtils;
import femtocraft.utils.FemtocraftDataUtils.EnumSaveType;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity {
    private
    @Saveable(item = true)
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

    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        boolean inrange = this.worldObj.getBlockTileEntity(this.xCoord,
                this.yCoord, this.zCoord) == this
                && par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D,
                (double) this.yCoord + 0.5D,
                (double) this.zCoord + 0.5D) <= 64.0D;
        boolean isowner = owner == null || owner.isEmpty()
                || (owner.equals(par1EntityPlayer.username));
        return inrange
                && (isowner || (MinecraftServer.getServer()
                .getConfigurationManager()
                .isPlayerOpped(par1EntityPlayer.username) || par1EntityPlayer.capabilities.isCreativeMode));
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
     * Gated update call. This will only be called on the server, and only if
     * the tile's {@link #shouldTick()} returns true. This should be used
     * instead of updateEntity() for heavy computation, unless the tile
     * absolutely needs to update.
     */
    public void femtocraftServerUpdate() {
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        FemtocraftDataUtils.loadObjectFromNBT(par1nbtTagCompound, this,
                EnumSaveType.WORLD);
        // owner = par1nbtTagCompound.getString(NBT_TAG);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
        FemtocraftDataUtils.saveObjectToNBT(par1nbtTagCompound, this,
                EnumSaveType.WORLD);
        // par1nbtTagCompound.setString(NBT_TAG, owner);
    }

    public boolean hasDescription() {
        return true;
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

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        super.onDataPacket(net, pkt);

        handleDescriptionNBT(pkt.data);
    }

    public String getPacketChannel() {
        return Femtocraft.ID;
    }

    public void handleDescriptionNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                EnumSaveType.DESCRIPTION);
    }

    public void saveToDescriptionCompound(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this,
                EnumSaveType.DESCRIPTION);
    }

    public void loadInfoFromItemNBT(NBTTagCompound compound) {
        if (compound == null) {
            return;
        }
        FemtocraftDataUtils
                .loadObjectFromNBT(compound, this, EnumSaveType.ITEM);
    }

    public void saveInfoToItemNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this, EnumSaveType.ITEM);
    }

    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (hasGUI() && isUseableByPlayer(par5EntityPlayer)) {
            par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, xCoord,
                    yCoord, zCoord);
            return true;
        }
        return false;
    }

    /**
     * @return GuiID, if GUI handler uses ids and not checking instanceof
     */
    public int getGuiID() {
        return -1;
    }

    public Object getMod() {
        return Femtocraft.instance;
    }

    public boolean hasGUI() {
        return false;
    }

    protected void setRenderUpdate() {
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    protected void setUpdate() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    protected void setModified() {
        if (worldObj != null)
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }


}
