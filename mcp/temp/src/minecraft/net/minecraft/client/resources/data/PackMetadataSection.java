package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.data.MetadataSection;

@SideOnly(Side.CLIENT)
public class PackMetadataSection implements MetadataSection {

   private final String field_110464_a;
   private final int field_110463_b;


   public PackMetadataSection(String p_i1312_1_, int p_i1312_2_) {
      this.field_110464_a = p_i1312_1_;
      this.field_110463_b = p_i1312_2_;
   }

   public String func_110461_a() {
      return this.field_110464_a;
   }

   public int func_110462_b() {
      return this.field_110463_b;
   }
}
