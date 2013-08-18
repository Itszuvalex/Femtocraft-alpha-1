package net.minecraft.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

final class CreativeTabRedstone extends CreativeTabs {

   CreativeTabRedstone(int p_i1847_1_, String p_i1847_2_) {
      super(p_i1847_1_, p_i1847_2_);
   }

   @SideOnly(Side.CLIENT)
   public int func_78012_e() {
      return Block.field_71954_T.field_71990_ca;
   }
}
