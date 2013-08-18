package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public abstract class BlockRotatedPillar extends Block {

   @SideOnly(Side.CLIENT)
   protected Icon field_111051_a;


   protected BlockRotatedPillar(int p_i2250_1_, Material p_i2250_2_) {
      super(p_i2250_1_, p_i2250_2_);
   }

   public int func_71857_b() {
      return 31;
   }

   public int func_85104_a(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_) {
      int var10 = p_85104_9_ & 3;
      byte var11 = 0;
      switch(p_85104_5_) {
      case 0:
      case 1:
         var11 = 0;
         break;
      case 2:
      case 3:
         var11 = 8;
         break;
      case 4:
      case 5:
         var11 = 4;
      }

      return var10 | var11;
   }

   @SideOnly(Side.CLIENT)
   public Icon func_71858_a(int p_71858_1_, int p_71858_2_) {
      int var3 = p_71858_2_ & 12;
      int var4 = p_71858_2_ & 3;
      return var3 == 0 && (p_71858_1_ == 1 || p_71858_1_ == 0)?this.func_111049_d(var4):(var3 == 4 && (p_71858_1_ == 5 || p_71858_1_ == 4)?this.func_111049_d(var4):(var3 == 8 && (p_71858_1_ == 2 || p_71858_1_ == 3)?this.func_111049_d(var4):this.func_111048_c(var4)));
   }

   public int func_71899_b(int p_71899_1_) {
      return p_71899_1_ & 3;
   }

   @SideOnly(Side.CLIENT)
   protected abstract Icon func_111048_c(int var1);

   @SideOnly(Side.CLIENT)
   protected Icon func_111049_d(int p_111049_1_) {
      return this.field_111051_a;
   }

   public int func_111050_e(int p_111050_1_) {
      return p_111050_1_ & 3;
   }

   protected ItemStack func_71880_c_(int p_71880_1_) {
      return new ItemStack(this.field_71990_ca, 1, this.func_111050_e(p_71880_1_));
   }
}
