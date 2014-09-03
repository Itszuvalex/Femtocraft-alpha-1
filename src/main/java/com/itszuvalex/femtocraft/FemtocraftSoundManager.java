package com.itszuvalex.femtocraft;

import com.itszuvalex.femtocraft.utils.WorldLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Chris on 9/3/2014.
 */
@SideOnly(value = Side.CLIENT)
public class FemtocraftSoundManager {
    public static final String PhlegethonTunnelIdentifier = Femtocraft.ID.toLowerCase() + ":" + "PhlegethonTunnel";

    private Map<WorldLocation, String> locToSoundIDMap;
    private Map<String, WorldLocation> soundIDToLocMap;
    private HashSet<WorldLocation> waitForEventList;

    public FemtocraftSoundManager() {
        locToSoundIDMap = new HashMap<WorldLocation, String>();
        soundIDToLocMap = new HashMap<String, WorldLocation>();
        waitForEventList = new HashSet<WorldLocation>();
    }

    public String getSoundIDForLocation(int x, int y, int z) {
        return locToSoundIDMap.get(new WorldLocation(null, x, y, z));
    }

    public WorldLocation getLocationForSoundID(String id) {
        return soundIDToLocMap.get(id);
    }

    public boolean addSoundIDForLocation(String id, int x, int y, int z) {
        WorldLocation loc = new WorldLocation(null, x, y, z);
        return locToSoundIDMap.put(loc, id) != null && soundIDToLocMap.put(id, loc) != null;
    }

    public boolean pauseSound(String id) {
        if (isSoundPlaying(id)) {
            Minecraft.getMinecraft().sndManager.sndSystem.pause(id);
            return true;
        }
        return false;
    }

    public boolean playSound(String id) {
        Minecraft.getMinecraft().sndManager.sndSystem.play(id);
        return isSoundPlaying(id);
    }

    public void playSound(World world, double xCoord, double yCoord, double zCoord, String soundName, float volume,
                          float pitch, boolean b) {
        world.playSound(xCoord, yCoord, zCoord, soundName, volume, pitch, b);
        waitForEventList.add(new WorldLocation(null, (int) xCoord, (int) yCoord, (int) zCoord));
    }

    public boolean isSoundPlaying(String id) {
        return Minecraft.getMinecraft().sndManager.sndSystem.playing(id);
    }

    public boolean stopSound(String id) {
        if (isSoundPlaying(id)) {
            Minecraft.getMinecraft().sndManager.sndSystem.stop(id);
            locToSoundIDMap.remove(soundIDToLocMap.remove(id));
            return true;
        }
        return false;
    }

    public boolean setSoundLooping(String id, boolean looping) {
        if (isSoundPlaying(id)) {
            Minecraft.getMinecraft().sndManager.sndSystem.setLooping(id, looping);
            return true;
        }
        return false;
    }

    public void stopAllSounds() {
        for (String id : soundIDToLocMap.keySet()) {
            stopSound(id);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {
        event.manager.addSound(PhlegethonTunnelIdentifier + ".wav");
    }

    @ForgeSubscribe
    public void onSoundPlay(PlaySoundSourceEvent event) {
        WorldLocation loc = new WorldLocation(null, (int) event.x, (int) event.y, (int) event.z);
        if (waitForEventList.contains(loc)) {
            addSoundIDForLocation(event.name, (int) event.x, (int) event.y, (int) event.z);
            waitForEventList.remove(loc);
        }
    }
}
