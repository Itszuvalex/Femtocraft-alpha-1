package com.itszuvalex.femtocraft;

import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.player.EntityPlayer;

import java.util.logging.Level;

/**
 * Created by Chris on 9/3/2014.
 */
public class FemtocraftPlayerTracker implements IPlayerTracker {
    @Override
    public void onPlayerLogin(EntityPlayer player) {

    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
        Femtocraft.soundManager.stopAllSounds();
        Femtocraft.logger.log(Level.INFO, player.username + " Logged out");
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
        Femtocraft.soundManager.stopAllSounds();
        Femtocraft.logger.log(Level.INFO, player.username + " changed dimensions out");
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {

    }
}
