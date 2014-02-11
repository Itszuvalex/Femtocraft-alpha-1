package femtocraft;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import femtocraft.core.tiles.FemtocraftTile;
import femtocraft.industry.TileEntity.VacuumTubeTile;
import femtocraft.player.FemtocraftNaniteProperties;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;

public class FemtocraftPacketHandler  implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager,
                    Packet250CustomPayload packet, Player playerEntity) {
    	if(packet.channel.equalsIgnoreCase(FemtocraftNaniteProperties.PACKET_CHANNEL))
    	{
    		FemtocraftNaniteProperties.get((EntityPlayer) playerEntity).handlePacket(packet);
    		return;
    	}
    	
    	
    	
    	DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
    	
//       	if(packet.channel.equalsIgnoreCase(Femtocraft.ID))
//        {
//       		
//        }
//       	else if(packet.channel.equalsIgnoreCase(FemtopowerMicroCubeTile.packetChannel))
//       	{
//       		handleMicroCube(inputStream, playerEntity);
//       	}
       	if(packet.channel.equalsIgnoreCase(VacuumTubeTile.packetChannel))
       	{
       		handleVacuumTube(inputStream, playerEntity);
       	}
       	else
       	{
       		handleDefault(inputStream, playerEntity);
       	}
    }
    
    private void handleVacuumTube(DataInputStream stream,
			Player player) {
    	try
   		{
   			int x = stream.readInt();
   			int y = stream.readInt();
   			int z = stream.readInt();
   			
   			byte items = stream.readByte();
   			byte connections = stream.readByte();
   			
   			if(!(player instanceof EntityClientPlayerMP)) return;
   			
			EntityClientPlayerMP cp = (EntityClientPlayerMP)player;
			
			TileEntity tile = cp.worldObj.getBlockTileEntity(x, y, z);
			if(tile == null) return;		
			if(!(tile instanceof VacuumTubeTile)) return;
			VacuumTubeTile tube = (VacuumTubeTile)tile;
			tube.parseItemMask(items);
			tube.parseConnectionMask(connections);
			cp.worldObj.markBlockForRenderUpdate(x, y, z);
   		}
   		catch(IOException e)
   		{
   			e.printStackTrace();
   			return;
   		}	
	}

	private void handleMicroCube(DataInputStream stream, Player player)
    {
   		try
   		{
   			int x = stream.readInt();
   			int y = stream.readInt();
   			int z = stream.readInt();
   			
   			byte outputs = stream.readByte();
   			
   			if(!(player instanceof EntityClientPlayerMP)) return;
   			
			EntityClientPlayerMP cp = (EntityClientPlayerMP)player;
			
			TileEntity tile = cp.worldObj.getBlockTileEntity(x, y, z);
			if(tile == null) return;		
			if(!(tile instanceof FemtopowerMicroCubeTile)) return;
			FemtopowerMicroCubeTile cube = (FemtopowerMicroCubeTile)tile;
			cube.parseOutputMask(outputs);
			cp.worldObj.markBlockForRenderUpdate(x, y, z);
   		}
   		catch(IOException e)
   		{
   			e.printStackTrace();
   			return;
   		}
    }
	
	private void handleDefault(DataInputStream stream, Player player)
	{
		try
   		{
			int world = stream.readInt();
   			int x = stream.readInt();
   			int y = stream.readInt();
   			int z = stream.readInt();
//
//   			if(!(player instanceof EntityClientPlayerMP)) return;
//   			
//   			
//			EntityClientPlayerMP cp = (EntityClientPlayerMP)player;
//			
			TileEntity te = DimensionManager.getWorld(world).getBlockTileEntity(x, y, z);
			if(te == null) return;		
			if(!(te instanceof FemtocraftTile)) return;
			FemtocraftTile tile = (FemtocraftTile)te;
			tile.handleDescriptionNBT(CompressedStreamTools.readCompressed(stream));
   		}
   		catch(IOException e)
   		{
   			e.printStackTrace();
   			return;
   		}
	}
}
