package net.minecraft.world.gen.structure;


class StructureStrongholdPieceWeight {

   public Class field_75194_a;
   public final int field_75192_b;
   public int field_75193_c;
   public int field_75191_d;


   public StructureStrongholdPieceWeight(Class p_i2076_1_, int p_i2076_2_, int p_i2076_3_) {
      this.field_75194_a = p_i2076_1_;
      this.field_75192_b = p_i2076_2_;
      this.field_75191_d = p_i2076_3_;
   }

   public boolean func_75189_a(int p_75189_1_) {
      return this.field_75191_d == 0 || this.field_75193_c < this.field_75191_d;
   }

   public boolean func_75190_a() {
      return this.field_75191_d == 0 || this.field_75193_c < this.field_75191_d;
   }
}
