package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarpet extends Block {

   protected BlockCarpet(int p_i2290_1_) {
      super(p_i2290_1_, Material.field_111018_r);
      this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
      this.func_71907_b(true);
      this.func_71849_a(CreativeTabs.field_78031_c);
      this.func_111047_d(0);
   }

   @SideOnly(Side.CLIENT)
   public Icon func_71858_a(int p_71858_1_, int p_71858_2_) {
      return Block.field_72101_ab.func_71858_a(p_71858_1_, p_71858_2_);
   }

   public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_) {
      byte var5 = 0;
      float var6 = 0.0625F;
      return AxisAlignedBB.func_72332_a().func_72299_a((double)p_71872_2_ + this.field_72026_ch, (double)p_71872_3_ + this.field_72023_ci, (double)p_71872_4_ + this.field_72024_cj, (double)p_71872_2_ + this.field_72021_ck, (double)((float)p_71872_3_ + (float)var5 * var6), (double)p_71872_4_ + this.field_72019_cm);
   }

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return false;
   }

   public void func_71919_f() {
      this.func_111047_d(0);
   }

   public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_) {
      this.func_111047_d(p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_));
   }

   protected void func_111047_d(int p_111047_1_) {
      byte var2 = 0;
      float var3 = (float)(1 * (1 + var2)) / 16.0F;
      this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
   }

   public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_) {
      return super.func_71930_b(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) && this.func_71854_d(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
   }

   public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_) {
      this.func_111046_k(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
   }

   private boolean func_111046_k(World p_111046_1_, int p_111046_2_, int p_111046_3_, int p_111046_4_) {
      if(!this.func_71854_d(p_111046_1_, p_111046_2_, p_111046_3_, p_111046_4_)) {
         this.func_71897_c(p_111046_1_, p_111046_2_, p_111046_3_, p_111046_4_, p_111046_1_.func_72805_g(p_111046_2_, p_111046_3_, p_111046_4_), 0);
         p_111046_1_.func_94571_i(p_111046_2_, p_111046_3_, p_111046_4_);
         return false;
      } else {
         return true;
      }
   }

   public boolean func_71854_d(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_) {
      return !p_71854_1_.func_72799_c(p_71854_2_, p_71854_3_ - 1, p_71854_4_);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_71877_c(IBlockAccess p_71877_1_, int p_71877_2_, int p_71877_3_, int p_71877_4_, int p_71877_5_) {
      return p_71877_5_ == 1?true:super.func_71877_c(p_71877_1_, p_71877_2_, p_71877_3_, p_71877_4_, p_71877_5_);
   }

   public int func_71899_b(int p_71899_1_) {
      return p_71899_1_;
   }

   @SideOnly(Side.CLIENT)
   public void func_71879_a(int p_71879_1_, CreativeTabs p_71879_2_, List p_71879_3_) {
      for(int var4 = 0; var4 < 16; ++var4) {
         p_71879_3_.add(new ItemStack(p_71879_1_, 1, var4));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_94332_a(IconRegister p_94332_1_) {}
}
