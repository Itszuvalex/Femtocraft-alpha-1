package femtocraft;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import femtocraft.managers.research.ManagerResearch;
import femtocraft.managers.research.ResearchPlayer;
import femtocraft.player.PropertiesNanite;
import femtocraft.transport.items.tiles.TileEntityVacuumTube;

public class FemtocraftPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player playerEntity) {
		if (packet.channel.equalsIgnoreCase(PropertiesNanite.PACKET_CHANNEL)) {
			PropertiesNanite.get((EntityPlayer) playerEntity).handlePacket(
					packet);
			return;
		}

		if (packet.channel.equalsIgnoreCase(ManagerResearch.RESEARCH_CHANNEL)) {
			handleResearchPacket(packet, playerEntity);
		}

		DataInputStream inputStream = new DataInputStream(
				new ByteArrayInputStream(packet.data));

		if (packet.channel.equalsIgnoreCase(TileEntityVacuumTube.packetChannel)) {
			handleVacuumTube(inputStream, playerEntity);
		}

	}

	private void handleResearchPacket(Packet250CustomPayload packet,
			Player player) {
		if (!(player instanceof EntityPlayer))
			return;

		EntityPlayer cp = (EntityPlayer) player;
		NBTTagCompound data;
		try {
			data = CompressedStreamTools.decompress(packet.data);
		} catch (IOException e) {
			e.printStackTrace();
			Femtocraft.logger
					.log(Level.SEVERE,
							"Error decompressing PlayerResearch data from packet.  This client will not be able to detect its research.");
			return;
		}
		ResearchPlayer rp = new ResearchPlayer(cp.username);
		rp.loadFromNBTTagCompound(data);

		Femtocraft.logger.log(Level.INFO,
				"Received PlayerResearch data packet update.");
		Femtocraft.researchManager.syncResearch(rp);
	}

	private void handleVacuumTube(DataInputStream stream, Player player) {
		try {
			int x = stream.readInt();
			int y = stream.readInt();
			int z = stream.readInt();

			byte items = stream.readByte();
			byte connections = stream.readByte();

			if (!(player instanceof EntityClientPlayerMP))
				return;

			EntityClientPlayerMP cp = (EntityClientPlayerMP) player;

			TileEntity tile = cp.worldObj.getBlockTileEntity(x, y, z);
			if (tile == null)
				return;
			if (!(tile instanceof TileEntityVacuumTube))
				return;
			TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;
			tube.parseItemMask(items);
			tube.parseConnectionMask(connections);
			cp.worldObj.markBlockForRenderUpdate(x, y, z);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
