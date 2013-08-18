package net.minecraft.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

final class CreativeTabBlock extends CreativeTabs {

   CreativeTabBlock(int p_i1845_1_, String p_i1845_2_) {
      super(p_i1845_1_, p_i1845_2_);
   }

   @SideOnly(Side.CLIENT)
   public int func_78012_e() {
      return Block.field_72107_ae.field_71990_ca;
   }
}
