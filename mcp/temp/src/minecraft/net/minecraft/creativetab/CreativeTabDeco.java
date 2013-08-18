package net.minecraft.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

final class CreativeTabDeco extends CreativeTabs {

   CreativeTabDeco(int p_i1846_1_, String p_i1846_2_) {
      super(p_i1846_1_, p_i1846_2_);
   }

   @SideOnly(Side.CLIENT)
   public int func_78012_e() {
      return Item.field_77767_aC.field_77779_bT;
   }
}
