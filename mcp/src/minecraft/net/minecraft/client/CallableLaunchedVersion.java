package net.minecraft.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;

@SideOnly(Side.CLIENT)
class CallableLaunchedVersion implements Callable
{
    /** Reference to the Minecraft object. */
    final Minecraft mc;

    CallableLaunchedVersion(Minecraft par1Minecraft)
    {
        this.mc = par1Minecraft;
    }

    public String getLWJGLVersion()
    {
        return Minecraft.func_110431_a(this.mc);
    }

    public Object call()
    {
        return this.getLWJGLVersion();
    }
}
