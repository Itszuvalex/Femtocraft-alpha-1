package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockLog extends BlockRotatedPillar {

   public static final String[] field_72142_a = new String[]{"oak", "spruce", "birch", "jungle"};
   @SideOnly(Side.CLIENT)
   private Icon[] field_111052_c;
   @SideOnly(Side.CLIENT)
   private Icon[] field_94388_cO;


   protected BlockLog(int p_i2279_1_) {
      super(p_i2279_1_, Material.field_76245_d);
      this.func_71849_a(CreativeTabs.field_78030_b);
   }

   public int func_71925_a(Random p_71925_1_) {
      return 1;
   }

   public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_) {
      return Block.field_71951_J.field_71990_ca;
   }

   public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_) {
      byte var7 = 4;
      int var8 = var7 + 1;
      if(p_71852_1_.func_72904_c(p_71852_2_ - var8, p_71852_3_ - var8, p_71852_4_ - var8, p_71852_2_ + var8, p_71852_3_ + var8, p_71852_4_ + var8)) {
         for(int var9 = -var7; var9 <= var7; ++var9) {
            for(int var10 = -var7; var10 <= var7; ++var10) {
               for(int var11 = -var7; var11 <= var7; ++var11) {
                  int var12 = p_71852_1_.func_72798_a(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11);
                  if(var12 == Block.field_71952_K.field_71990_ca) {
                     int var13 = p_71852_1_.func_72805_g(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11);
                     if((var13 & 8) == 0) {
                        p_71852_1_.func_72921_c(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11, var13 | 8, 4);
                     }
                  }
               }
            }
         }
      }

   }

   @SideOnly(Side.CLIENT)
   protected Icon func_111048_c(int p_111048_1_) {
      return this.field_111052_c[p_111048_1_];
   }

   @SideOnly(Side.CLIENT)
   protected Icon func_111049_d(int p_111049_1_) {
      return this.field_94388_cO[p_111049_1_];
   }

   public static int func_72141_e(int p_72141_0_) {
      return p_72141_0_ & 3;
   }

   @SideOnly(Side.CLIENT)
   public void func_71879_a(int p_71879_1_, CreativeTabs p_71879_2_, List p_71879_3_) {
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 0));
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 1));
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 2));
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 3));
   }

   @SideOnly(Side.CLIENT)
   public void func_94332_a(IconRegister p_94332_1_) {
      this.field_111052_c = new Icon[field_72142_a.length];
      this.field_94388_cO = new Icon[field_72142_a.length];

      for(int var2 = 0; var2 < this.field_111052_c.length; ++var2) {
         this.field_111052_c[var2] = p_94332_1_.func_94245_a(this.func_111023_E() + "_" + field_72142_a[var2]);
         this.field_94388_cO[var2] = p_94332_1_.func_94245_a(this.func_111023_E() + "_" + field_72142_a[var2] + "_top");
      }

   }

}
