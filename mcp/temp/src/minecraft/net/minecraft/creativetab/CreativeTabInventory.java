package net.minecraft.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

final class CreativeTabInventory extends CreativeTabs {

   CreativeTabInventory(int p_i1844_1_, String p_i1844_2_) {
      super(p_i1844_1_, p_i1844_2_);
   }

   @SideOnly(Side.CLIENT)
   public int func_78012_e() {
      return Block.field_72077_au.field_71990_ca;
   }
}
