package net.minecraft.block;

import net.minecraft.block.StepSound;

final class StepSoundAnvil extends StepSound {

   StepSoundAnvil(String p_i2271_1_, float p_i2271_2_, float p_i2271_3_) {
      super(p_i2271_1_, p_i2271_2_, p_i2271_3_);
   }

   public String func_72676_a() {
      return "dig.stone";
   }

   public String func_82593_b() {
      return "random.anvil_land";
   }
}
