package net.minecraft.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

final class CreativeTabFood extends CreativeTabs {

   CreativeTabFood(int p_i1851_1_, String p_i1851_2_) {
      super(p_i1851_1_, p_i1851_2_);
   }

   @SideOnly(Side.CLIENT)
   public int func_78012_e() {
      return Item.field_77708_h.field_77779_bT;
   }
}
