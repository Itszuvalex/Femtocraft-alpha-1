package net.minecraft.world.gen.structure;


class StructureNetherBridgePieceWeight {

   public Class field_78828_a;
   public final int field_78826_b;
   public int field_78827_c;
   public int field_78824_d;
   public boolean field_78825_e;


   public StructureNetherBridgePieceWeight(Class p_i2055_1_, int p_i2055_2_, int p_i2055_3_, boolean p_i2055_4_) {
      this.field_78828_a = p_i2055_1_;
      this.field_78826_b = p_i2055_2_;
      this.field_78824_d = p_i2055_3_;
      this.field_78825_e = p_i2055_4_;
   }

   public StructureNetherBridgePieceWeight(Class p_i2056_1_, int p_i2056_2_, int p_i2056_3_) {
      this(p_i2056_1_, p_i2056_2_, p_i2056_3_, false);
   }

   public boolean func_78822_a(int p_78822_1_) {
      return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
   }

   public boolean func_78823_a() {
      return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
   }
}
