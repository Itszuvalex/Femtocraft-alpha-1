package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.StructureStrongholdPieceWeight;

final class StructureStrongholdPieceWeight2 extends StructureStrongholdPieceWeight {

   StructureStrongholdPieceWeight2(Class p_i2069_1_, int p_i2069_2_, int p_i2069_3_) {
      super(p_i2069_1_, p_i2069_2_, p_i2069_3_);
   }

   public boolean func_75189_a(int p_75189_1_) {
      return super.func_75189_a(p_75189_1_) && p_75189_1_ > 4;
   }
}
