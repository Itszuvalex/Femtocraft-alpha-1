package net.minecraft.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.SoundManager;
import paulscode.sound.SoundSystem;

@SideOnly(Side.CLIENT)
class SoundManagerINNER1 implements Runnable {

   // $FF: synthetic field
   final SoundManager field_130090_a;


   SoundManagerINNER1(SoundManager p_i1324_1_) {
      this.field_130090_a = p_i1324_1_;
   }

   public void run() {
      SoundManager.func_130080_a(this.field_130090_a, new SoundSystem());
      SoundManager.func_130082_a(this.field_130090_a, true);
   }
}
