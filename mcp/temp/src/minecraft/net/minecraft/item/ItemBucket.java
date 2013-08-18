package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBucket extends Item {

   private int field_77876_a;


   public ItemBucket(int p_i1836_1_, int p_i1836_2_) {
      super(p_i1836_1_);
      this.field_77777_bU = 1;
      this.field_77876_a = p_i1836_2_;
      this.func_77637_a(CreativeTabs.field_78026_f);
   }

   public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      boolean var4 = this.field_77876_a == 0;
      MovingObjectPosition var5 = this.func_77621_a(p_77659_2_, p_77659_3_, var4);
      if(var5 == null) {
         return p_77659_1_;
      } else {
         if(var5.field_72313_a == EnumMovingObjectType.TILE) {
            int var6 = var5.field_72311_b;
            int var7 = var5.field_72312_c;
            int var8 = var5.field_72309_d;
            if(!p_77659_2_.func_72962_a(p_77659_3_, var6, var7, var8)) {
               return p_77659_1_;
            }

            if(this.field_77876_a == 0) {
               if(!p_77659_3_.func_82247_a(var6, var7, var8, var5.field_72310_e, p_77659_1_)) {
                  return p_77659_1_;
               }

               if(p_77659_2_.func_72803_f(var6, var7, var8) == Material.field_76244_g && p_77659_2_.func_72805_g(var6, var7, var8) == 0) {
                  p_77659_2_.func_94571_i(var6, var7, var8);
                  if(p_77659_3_.field_71075_bZ.field_75098_d) {
                     return p_77659_1_;
                  }

                  if(--p_77659_1_.field_77994_a <= 0) {
                     return new ItemStack(Item.field_77786_ax);
                  }

                  if(!p_77659_3_.field_71071_by.func_70441_a(new ItemStack(Item.field_77786_ax))) {
                     p_77659_3_.func_71021_b(new ItemStack(Item.field_77786_ax.field_77779_bT, 1, 0));
                  }

                  return p_77659_1_;
               }

               if(p_77659_2_.func_72803_f(var6, var7, var8) == Material.field_76256_h && p_77659_2_.func_72805_g(var6, var7, var8) == 0) {
                  p_77659_2_.func_94571_i(var6, var7, var8);
                  if(p_77659_3_.field_71075_bZ.field_75098_d) {
                     return p_77659_1_;
                  }

                  if(--p_77659_1_.field_77994_a <= 0) {
                     return new ItemStack(Item.field_77775_ay);
                  }

                  if(!p_77659_3_.field_71071_by.func_70441_a(new ItemStack(Item.field_77775_ay))) {
                     p_77659_3_.func_71021_b(new ItemStack(Item.field_77775_ay.field_77779_bT, 1, 0));
                  }

                  return p_77659_1_;
               }
            } else {
               if(this.field_77876_a < 0) {
                  return new ItemStack(Item.field_77788_aw);
               }

               if(var5.field_72310_e == 0) {
                  --var7;
               }

               if(var5.field_72310_e == 1) {
                  ++var7;
               }

               if(var5.field_72310_e == 2) {
                  --var8;
               }

               if(var5.field_72310_e == 3) {
                  ++var8;
               }

               if(var5.field_72310_e == 4) {
                  --var6;
               }

               if(var5.field_72310_e == 5) {
                  ++var6;
               }

               if(!p_77659_3_.func_82247_a(var6, var7, var8, var5.field_72310_e, p_77659_1_)) {
                  return p_77659_1_;
               }

               if(this.func_77875_a(p_77659_2_, var6, var7, var8) && !p_77659_3_.field_71075_bZ.field_75098_d) {
                  return new ItemStack(Item.field_77788_aw);
               }
            }
         }

         return p_77659_1_;
      }
   }

   public boolean func_77875_a(World p_77875_1_, int p_77875_2_, int p_77875_3_, int p_77875_4_) {
      if(this.field_77876_a <= 0) {
         return false;
      } else {
         Material var5 = p_77875_1_.func_72803_f(p_77875_2_, p_77875_3_, p_77875_4_);
         boolean var6 = !var5.func_76220_a();
         if(!p_77875_1_.func_72799_c(p_77875_2_, p_77875_3_, p_77875_4_) && !var6) {
            return false;
         } else {
            if(p_77875_1_.field_73011_w.field_76575_d && this.field_77876_a == Block.field_71942_A.field_71990_ca) {
               p_77875_1_.func_72908_a((double)((float)p_77875_2_ + 0.5F), (double)((float)p_77875_3_ + 0.5F), (double)((float)p_77875_4_ + 0.5F), "random.fizz", 0.5F, 2.6F + (p_77875_1_.field_73012_v.nextFloat() - p_77875_1_.field_73012_v.nextFloat()) * 0.8F);

               for(int var7 = 0; var7 < 8; ++var7) {
                  p_77875_1_.func_72869_a("largesmoke", (double)p_77875_2_ + Math.random(), (double)p_77875_3_ + Math.random(), (double)p_77875_4_ + Math.random(), 0.0D, 0.0D, 0.0D);
               }
            } else {
               if(!p_77875_1_.field_72995_K && var6 && !var5.func_76224_d()) {
                  p_77875_1_.func_94578_a(p_77875_2_, p_77875_3_, p_77875_4_, true);
               }

               p_77875_1_.func_72832_d(p_77875_2_, p_77875_3_, p_77875_4_, this.field_77876_a, 0, 3);
            }

            return true;
         }
      }
   }
}
