package com.itszuvalex.femtocraft;

import com.itszuvalex.femtocraft.utils.WorldLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 9/3/2014.
 */
public class FemtocraftSoundManager {
    private Map<WorldLocation, String> locToSoundIDMap;

    public FemtocraftSoundManager() {
        locToSoundIDMap = new HashMap<WorldLocation, String>();
    }

    public String getSoundIDForLocation(int x, int y, int z) {
        return locToSoundIDMap.get(new WorldLocation(null, x, y, z));
    }

    public boolean addSoundIDForLocation(String id, int x, int y, int z) {
        return locToSoundIDMap.put(new WorldLocation(null, x, y, z), id) != null;
    }

    public void pauseSound(String id) {
        Minecraft.getMinecraft().sndManager.sndSystem.pause(id);
    }

    public void playSound(String id) {
        Minecraft.getMinecraft().sndManager.sndSystem.play(id);
    }

    public boolean isSoundPlaying(String id) {
        return Minecraft.getMinecraft().sndManager.sndSystem.playing(id);
    }

    public void stopSound(String id) {
        Minecraft.getMinecraft().sndManager.sndSystem.stop(id);
    }

    public void setSoundLooping(String id, boolean looping) {
        Minecraft.getMinecraft().sndManager.sndSystem.setLooping(id, looping);
    }

    @SideOnly(value = Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {
        event.manager.addSound(Femtocraft.ID.toLowerCase() + ":" + "PhlegethonTunnel.wav");
    }
}
