package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.data.MetadataSection;

@SideOnly(Side.CLIENT)
public class FontMetadataSection implements MetadataSection {

   private final float[] field_110467_a;
   private final float[] field_110465_b;
   private final float[] field_110466_c;


   public FontMetadataSection(float[] p_i1310_1_, float[] p_i1310_2_, float[] p_i1310_3_) {
      this.field_110467_a = p_i1310_1_;
      this.field_110465_b = p_i1310_2_;
      this.field_110466_c = p_i1310_3_;
   }
}
