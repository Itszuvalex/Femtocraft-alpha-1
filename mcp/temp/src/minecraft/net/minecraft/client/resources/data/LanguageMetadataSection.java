package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;
import net.minecraft.client.resources.data.MetadataSection;

@SideOnly(Side.CLIENT)
public class LanguageMetadataSection implements MetadataSection {

   private final Collection field_135019_a;


   public LanguageMetadataSection(Collection p_i1311_1_) {
      this.field_135019_a = p_i1311_1_;
   }

   public Collection func_135018_a() {
      return this.field_135019_a;
   }
}
