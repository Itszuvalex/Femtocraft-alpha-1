package net.minecraft.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

final class CreativeTabMisc extends CreativeTabs {

   CreativeTabMisc(int p_i1849_1_, String p_i1849_2_) {
      super(p_i1849_1_, p_i1849_2_);
   }

   @SideOnly(Side.CLIENT)
   public int func_78012_e() {
      return Item.field_77750_aQ.field_77779_bT;
   }
}
