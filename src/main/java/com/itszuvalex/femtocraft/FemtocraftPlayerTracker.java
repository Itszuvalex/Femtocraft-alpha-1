package com.itszuvalex.femtocraft;

import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;

import static com.itszuvalex.femtocraft.FemtocraftSoundManager.SoundActions.STOP_ALL_SOUNDS;

/**
 * Created by Chris on 9/3/2014.
 */
public class FemtocraftPlayerTracker implements IPlayerTracker {
    @Override
    public void onPlayerLogin(EntityPlayer player) {

    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
//        PacketDispatcher.sendPacketToPlayer(Femtocraft.soundManager.getPacket(STOP_ALL_SOUNDS), (Player) player);
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
        PacketDispatcher.sendPacketToPlayer(Femtocraft.soundManager.getPacket(STOP_ALL_SOUNDS), (Player) player);
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {

    }
}
