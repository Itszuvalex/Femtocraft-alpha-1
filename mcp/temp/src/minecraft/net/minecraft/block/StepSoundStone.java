package net.minecraft.block;

import net.minecraft.block.StepSound;

final class StepSoundStone extends StepSound {

   StepSoundStone(String p_i2269_1_, float p_i2269_2_, float p_i2269_3_) {
      super(p_i2269_1_, p_i2269_2_, p_i2269_3_);
   }

   public String func_72676_a() {
      return "random.glass";
   }

   public String func_82593_b() {
      return "step.stone";
   }
}
