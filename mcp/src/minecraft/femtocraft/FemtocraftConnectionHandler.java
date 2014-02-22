package femtocraft;

import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import femtocraft.managers.research.ManagerResearch;
import femtocraft.managers.research.ResearchPlayer;

public class FemtocraftConnectionHandler implements IConnectionHandler {

	public FemtocraftConnectionHandler() {
	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		ResearchPlayer pr = Femtocraft.researchManager
				.addPlayerResearch(((EntityPlayer) player).username);
		NBTTagCompound data = new NBTTagCompound();
		pr.saveToNBTTagCompound(data);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = ManagerResearch.RESEARCH_CHANNEL;
		try {
			packet.data = CompressedStreamTools.compress(data);
		} catch (IOException e) {
			e.printStackTrace();
			Femtocraft.logger
					.log(Level.SEVERE,
							"Error writing "
									+ pr.username
									+ "'s PlayerResearch to packet data.  It will fail to sync to his client.");
			return;
		}
		packet.length = packet.data.length;
		Femtocraft.logger.log(Level.INFO, "Detecting " + pr.username
				+ " joining.  Sending PlayerResearch data packet.");
		PacketDispatcher.sendPacketToPlayer(packet, player);
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
	}

}
