package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.data.MetadataSection;

@SideOnly(Side.CLIENT)
public class TextureMetadataSection implements MetadataSection {

   private final boolean field_110482_a;
   private final boolean field_110481_b;


   public TextureMetadataSection(boolean p_i1313_1_, boolean p_i1313_2_) {
      this.field_110482_a = p_i1313_1_;
      this.field_110481_b = p_i1313_2_;
   }

   public boolean func_110479_a() {
      return this.field_110482_a;
   }

   public boolean func_110480_b() {
      return this.field_110481_b;
   }
}
