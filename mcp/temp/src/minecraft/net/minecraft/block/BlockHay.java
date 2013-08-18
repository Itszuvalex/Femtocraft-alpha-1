package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

public class BlockHay extends BlockRotatedPillar {

   public BlockHay(int p_i2210_1_) {
      super(p_i2210_1_, Material.field_76247_b);
      this.func_71849_a(CreativeTabs.field_78030_b);
   }

   public int func_71857_b() {
      return 31;
   }

   @SideOnly(Side.CLIENT)
   protected Icon func_111048_c(int p_111048_1_) {
      return this.field_94336_cN;
   }

   @SideOnly(Side.CLIENT)
   public void func_94332_a(IconRegister p_94332_1_) {
      this.field_111051_a = p_94332_1_.func_94245_a(this.func_111023_E() + "_top");
      this.field_94336_cN = p_94332_1_.func_94245_a(this.func_111023_E() + "_side");
   }
}
