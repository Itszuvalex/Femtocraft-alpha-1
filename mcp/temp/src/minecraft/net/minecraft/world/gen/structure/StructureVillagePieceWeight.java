package net.minecraft.world.gen.structure;


public class StructureVillagePieceWeight {

   public Class field_75090_a;
   public final int field_75088_b;
   public int field_75089_c;
   public int field_75087_d;


   public StructureVillagePieceWeight(Class p_i2098_1_, int p_i2098_2_, int p_i2098_3_) {
      this.field_75090_a = p_i2098_1_;
      this.field_75088_b = p_i2098_2_;
      this.field_75087_d = p_i2098_3_;
   }

   public boolean func_75085_a(int p_75085_1_) {
      return this.field_75087_d == 0 || this.field_75089_c < this.field_75087_d;
   }

   public boolean func_75086_a() {
      return this.field_75087_d == 0 || this.field_75089_c < this.field_75087_d;
   }
}
