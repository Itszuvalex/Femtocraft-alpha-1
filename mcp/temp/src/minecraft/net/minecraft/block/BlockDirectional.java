package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockDirectional extends Block {

   protected BlockDirectional(int p_i2190_1_, Material p_i2190_2_) {
      super(p_i2190_1_, p_i2190_2_);
   }

   public static int func_72217_d(int p_72217_0_) {
      return p_72217_0_ & 3;
   }
}
