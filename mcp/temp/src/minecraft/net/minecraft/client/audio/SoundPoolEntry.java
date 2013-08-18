package net.minecraft.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URL;

@SideOnly(Side.CLIENT)
public class SoundPoolEntry {

   private final String field_77385_a;
   private final URL field_77384_b;


   public SoundPoolEntry(String p_i1323_1_, URL p_i1323_2_) {
      this.field_77385_a = p_i1323_1_;
      this.field_77384_b = p_i1323_2_;
   }

   public String func_110458_a() {
      return this.field_77385_a;
   }

   public URL func_110457_b() {
      return this.field_77384_b;
   }
}
