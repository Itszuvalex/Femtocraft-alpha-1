package femtocraft.core.tiles;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.Femtocraft;
import femtocraft.FemtocraftConfigs;
import femtocraft.FemtocraftUtils;

public class FemtocraftTile extends TileEntity {
	private String owner;
	private final String NBT_TAG = "owner";
	
	public FemtocraftTile() {
		owner = "";
	}

	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	 public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        boolean inrange =  this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
        boolean isowner = owner.isEmpty() || (owner.equals(par1EntityPlayer.username));
        return inrange && isowner;
    }
	 
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) return;
		if(!shouldTick()) return;
		
		femtocraftServerUpdate();
	}

	public boolean shouldTick()
	{
		if(FemtocraftConfigs.requirePlayersOnlineForTileEntityTicks)
			return FemtocraftUtils.isPlayerOnline(owner);
		
		return true;
	}
	
	/**
	 *  Gated update call.  This will only be called on the server, and only if the tile's {@link #shouldTick()} returns true.
	 *  This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to update.
	 */
	public void femtocraftServerUpdate()
	{}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		
		owner = par1nbtTagCompound.getString(NBT_TAG);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setString(NBT_TAG, owner);
	}

	public boolean hasDescription()
	{
		return true;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		if(!hasDescription()) return null;
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		
		NBTTagCompound compound = new NBTTagCompound();
		
		saveToDescriptionCompound(compound);
		
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(worldObj.provider.dimensionId);
	        outputStream.writeInt(xCoord);
	        outputStream.writeInt(yCoord);
	        outputStream.writeInt(zCoord);
			CompressedStreamTools.writeCompressed(compound, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    packet.channel = getPacketChannel();
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();
		
		return packet;
	}
	
	public String getPacketChannel()
	{
		return Femtocraft.ID;
	}
	
	public void handleDescriptionNBT(NBTTagCompound compound)
	{
		owner = compound.getString(NBT_TAG);
	}
	
	public void saveToDescriptionCompound(NBTTagCompound compound)
	{
		compound.setString(NBT_TAG, owner);
	}

	public void loadInfoFromItemNBT(NBTTagCompound compound)
	{
		if(compound == null) return;
		owner = compound.getString(NBT_TAG);
	}
	
	public void saveInfoToItemNBT(NBTTagCompound compound)
	{
		compound.setString(NBT_TAG, owner);
	}


}
