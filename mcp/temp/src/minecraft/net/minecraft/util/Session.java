package net.minecraft.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Session {

   private final String field_74286_b;
   private final String field_74287_c;


   public Session(String p_i1019_1_, String p_i1019_2_) {
      this.field_74286_b = p_i1019_1_;
      this.field_74287_c = p_i1019_2_;
   }

   public String func_111285_a() {
      return this.field_74286_b;
   }

   public String func_111286_b() {
      return this.field_74287_c;
   }
}
