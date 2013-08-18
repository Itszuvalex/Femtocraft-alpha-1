package net.minecraft.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class ScheduledSound {

   String field_92069_a;
   float field_92067_b;
   float field_92068_c;
   float field_92065_d;
   float field_92066_e;
   float field_92063_f;
   int field_92064_g;


   public ScheduledSound(String p_i1325_1_, float p_i1325_2_, float p_i1325_3_, float p_i1325_4_, float p_i1325_5_, float p_i1325_6_, int p_i1325_7_) {
      this.field_92069_a = p_i1325_1_;
      this.field_92067_b = p_i1325_2_;
      this.field_92068_c = p_i1325_3_;
      this.field_92065_d = p_i1325_4_;
      this.field_92066_e = p_i1325_5_;
      this.field_92063_f = p_i1325_6_;
      this.field_92064_g = p_i1325_7_;
   }
}
