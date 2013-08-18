package net.minecraft.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URL;

@SideOnly(Side.CLIENT)
public class SoundPoolEntry
{
    private final String soundName;
    private final URL soundUrl;

    public SoundPoolEntry(String par1Str, URL par2URL)
    {
        this.soundName = par1Str;
        this.soundUrl = par2URL;
    }

    public String func_110458_a()
    {
        return this.soundName;
    }

    public URL func_110457_b()
    {
        return this.soundUrl;
    }
}
