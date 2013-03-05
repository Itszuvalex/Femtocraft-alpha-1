package femtocraft;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class FemtocraftPacketHandler  implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager,
                    Packet250CustomPayload packet, Player playerEntity) {
            // TODO Auto-generated method stub
    }

}
