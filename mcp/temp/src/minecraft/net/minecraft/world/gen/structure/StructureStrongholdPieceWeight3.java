package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.StructureStrongholdPieceWeight;

final class StructureStrongholdPieceWeight3 extends StructureStrongholdPieceWeight {

   StructureStrongholdPieceWeight3(Class p_i2070_1_, int p_i2070_2_, int p_i2070_3_) {
      super(p_i2070_1_, p_i2070_2_, p_i2070_3_);
   }

   public boolean func_75189_a(int p_75189_1_) {
      return super.func_75189_a(p_75189_1_) && p_75189_1_ > 5;
   }
}
