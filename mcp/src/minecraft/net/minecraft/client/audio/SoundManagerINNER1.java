package net.minecraft.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import paulscode.sound.SoundSystem;

@SideOnly(Side.CLIENT)
class SoundManagerINNER1 implements Runnable
{
    final SoundManager field_130090_a;

    SoundManagerINNER1(SoundManager par1SoundManager)
    {
        this.field_130090_a = par1SoundManager;
    }

    public void run()
    {
        SoundManager.func_130080_a(this.field_130090_a, new SoundSystem());
        SoundManager.func_130082_a(this.field_130090_a, true);
    }
}
