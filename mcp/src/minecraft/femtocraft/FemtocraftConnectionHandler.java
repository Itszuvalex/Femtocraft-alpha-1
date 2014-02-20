package femtocraft;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import femtocraft.managers.research.ManagerResearch;
import femtocraft.managers.research.ResearchPlayer;
import femtocraft.managers.research.ResearchTechnologyStatus;

public class FemtocraftConnectionHandler implements IConnectionHandler {

	public FemtocraftConnectionHandler() {
	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		ResearchPlayer pr = Femtocraft.researchManager
				.addPlayerResearch(netHandler.getPlayer().username);
		for (ResearchTechnologyStatus tech : pr.getTechnologies()) {
			Packet250CustomPayload techPacket = tech.toPacket();
			manager.addToSendQueue(techPacket);
		}
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
