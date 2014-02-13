package femtocraft;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import femtocraft.industry.TileEntity.VacuumTubeTile;
import femtocraft.player.FemtocraftNaniteProperties;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class FemtocraftPacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager,
                             Packet250CustomPayload packet, Player playerEntity) {
        if (packet.channel
                .equalsIgnoreCase(FemtocraftNaniteProperties.PACKET_CHANNEL)) {
            FemtocraftNaniteProperties.get((EntityPlayer) playerEntity)
                    .handlePacket(packet);
            return;
        }

        DataInputStream inputStream = new DataInputStream(
                new ByteArrayInputStream(packet.data));

        if (packet.channel.equalsIgnoreCase(VacuumTubeTile.packetChannel)) {
            handleVacuumTube(inputStream, playerEntity);
        }
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
            if (!(tile instanceof VacuumTubeTile))
                return;
            VacuumTubeTile tube = (VacuumTubeTile) tile;
            tube.parseItemMask(items);
            tube.parseConnectionMask(connections);
            cp.worldObj.markBlockForRenderUpdate(x, y, z);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
