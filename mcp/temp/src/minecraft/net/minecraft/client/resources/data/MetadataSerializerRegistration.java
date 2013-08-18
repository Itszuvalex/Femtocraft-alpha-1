package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.data.MetadataSectionSerializer;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.resources.data.MetadataSerializerEmptyAnon;

@SideOnly(Side.CLIENT)
class MetadataSerializerRegistration {

   final MetadataSectionSerializer field_110502_a;
   final Class field_110500_b;
   // $FF: synthetic field
   final MetadataSerializer field_110501_c;


   private MetadataSerializerRegistration(MetadataSerializer p_i1305_1_, MetadataSectionSerializer p_i1305_2_, Class p_i1305_3_) {
      this.field_110501_c = p_i1305_1_;
      this.field_110502_a = p_i1305_2_;
      this.field_110500_b = p_i1305_3_;
   }

   // $FF: synthetic method
   MetadataSerializerRegistration(MetadataSerializer p_i1306_1_, MetadataSectionSerializer p_i1306_2_, Class p_i1306_3_, MetadataSerializerEmptyAnon p_i1306_4_) {
      this(p_i1306_1_, p_i1306_2_, p_i1306_3_);
   }
}
