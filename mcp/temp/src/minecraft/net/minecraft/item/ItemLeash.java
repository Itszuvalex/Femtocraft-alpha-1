package net.minecraft.item;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemLeash extends Item {

   public ItemLeash(int p_i1884_1_) {
      super(p_i1884_1_);
      this.func_77637_a(CreativeTabs.field_78040_i);
   }

   public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      int var11 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);
      if(Block.field_71973_m[var11] != null && Block.field_71973_m[var11].func_71857_b() == 11) {
         if(p_77648_3_.field_72995_K) {
            return true;
         } else {
            func_135066_a(p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean func_135066_a(EntityPlayer p_135066_0_, World p_135066_1_, int p_135066_2_, int p_135066_3_, int p_135066_4_) {
      EntityLeashKnot var5 = EntityLeashKnot.func_110130_b(p_135066_1_, p_135066_2_, p_135066_3_, p_135066_4_);
      boolean var6 = false;
      double var7 = 7.0D;
      List var9 = p_135066_1_.func_72872_a(EntityLiving.class, AxisAlignedBB.func_72332_a().func_72299_a((double)p_135066_2_ - var7, (double)p_135066_3_ - var7, (double)p_135066_4_ - var7, (double)p_135066_2_ + var7, (double)p_135066_3_ + var7, (double)p_135066_4_ + var7));
      if(var9 != null) {
         Iterator var10 = var9.iterator();

         while(var10.hasNext()) {
            EntityLiving var11 = (EntityLiving)var10.next();
            if(var11.func_110167_bD() && var11.func_110166_bE() == p_135066_0_) {
               if(var5 == null) {
                  var5 = EntityLeashKnot.func_110129_a(p_135066_1_, p_135066_2_, p_135066_3_, p_135066_4_);
               }

               var11.func_110162_b(var5, true);
               var6 = true;
            }
         }
      }

      return var6;
   }
}
