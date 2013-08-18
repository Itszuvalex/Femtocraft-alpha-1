package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDungeons extends WorldGenerator {

   public static final WeightedRandomChestContent[] field_111189_a = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Item.field_77765_aA.field_77779_bT, 0, 1, 1, 10), new WeightedRandomChestContent(Item.field_77703_o.field_77779_bT, 0, 1, 4, 10), new WeightedRandomChestContent(Item.field_77684_U.field_77779_bT, 0, 1, 1, 10), new WeightedRandomChestContent(Item.field_77685_T.field_77779_bT, 0, 1, 4, 10), new WeightedRandomChestContent(Item.field_77677_M.field_77779_bT, 0, 1, 4, 10), new WeightedRandomChestContent(Item.field_77683_K.field_77779_bT, 0, 1, 4, 10), new WeightedRandomChestContent(Item.field_77788_aw.field_77779_bT, 0, 1, 1, 10), new WeightedRandomChestContent(Item.field_77778_at.field_77779_bT, 0, 1, 1, 1), new WeightedRandomChestContent(Item.field_77767_aC.field_77779_bT, 0, 1, 4, 10), new WeightedRandomChestContent(Item.field_77819_bI.field_77779_bT, 0, 1, 1, 10), new WeightedRandomChestContent(Item.field_77797_bJ.field_77779_bT, 0, 1, 1, 10), new WeightedRandomChestContent(Item.field_111212_ci.field_77779_bT, 0, 1, 1, 10), new WeightedRandomChestContent(Item.field_111216_cf.field_77779_bT, 0, 1, 1, 2), new WeightedRandomChestContent(Item.field_111215_ce.field_77779_bT, 0, 1, 1, 5), new WeightedRandomChestContent(Item.field_111213_cg.field_77779_bT, 0, 1, 1, 1)};


   public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
      byte var6 = 3;
      int var7 = p_76484_2_.nextInt(2) + 2;
      int var8 = p_76484_2_.nextInt(2) + 2;
      int var9 = 0;

      int var10;
      int var11;
      int var12;
      for(var10 = p_76484_3_ - var7 - 1; var10 <= p_76484_3_ + var7 + 1; ++var10) {
         for(var11 = p_76484_4_ - 1; var11 <= p_76484_4_ + var6 + 1; ++var11) {
            for(var12 = p_76484_5_ - var8 - 1; var12 <= p_76484_5_ + var8 + 1; ++var12) {
               Material var13 = p_76484_1_.func_72803_f(var10, var11, var12);
               if(var11 == p_76484_4_ - 1 && !var13.func_76220_a()) {
                  return false;
               }

               if(var11 == p_76484_4_ + var6 + 1 && !var13.func_76220_a()) {
                  return false;
               }

               if((var10 == p_76484_3_ - var7 - 1 || var10 == p_76484_3_ + var7 + 1 || var12 == p_76484_5_ - var8 - 1 || var12 == p_76484_5_ + var8 + 1) && var11 == p_76484_4_ && p_76484_1_.func_72799_c(var10, var11, var12) && p_76484_1_.func_72799_c(var10, var11 + 1, var12)) {
                  ++var9;
               }
            }
         }
      }

      if(var9 >= 1 && var9 <= 5) {
         for(var10 = p_76484_3_ - var7 - 1; var10 <= p_76484_3_ + var7 + 1; ++var10) {
            for(var11 = p_76484_4_ + var6; var11 >= p_76484_4_ - 1; --var11) {
               for(var12 = p_76484_5_ - var8 - 1; var12 <= p_76484_5_ + var8 + 1; ++var12) {
                  if(var10 != p_76484_3_ - var7 - 1 && var11 != p_76484_4_ - 1 && var12 != p_76484_5_ - var8 - 1 && var10 != p_76484_3_ + var7 + 1 && var11 != p_76484_4_ + var6 + 1 && var12 != p_76484_5_ + var8 + 1) {
                     p_76484_1_.func_94571_i(var10, var11, var12);
                  } else if(var11 >= 0 && !p_76484_1_.func_72803_f(var10, var11 - 1, var12).func_76220_a()) {
                     p_76484_1_.func_94571_i(var10, var11, var12);
                  } else if(p_76484_1_.func_72803_f(var10, var11, var12).func_76220_a()) {
                     if(var11 == p_76484_4_ - 1 && p_76484_2_.nextInt(4) != 0) {
                        p_76484_1_.func_72832_d(var10, var11, var12, Block.field_72087_ao.field_71990_ca, 0, 2);
                     } else {
                        p_76484_1_.func_72832_d(var10, var11, var12, Block.field_71978_w.field_71990_ca, 0, 2);
                     }
                  }
               }
            }
         }

         var10 = 0;

         while(var10 < 2) {
            var11 = 0;

            while(true) {
               if(var11 < 3) {
                  label101: {
                     var12 = p_76484_3_ + p_76484_2_.nextInt(var7 * 2 + 1) - var7;
                     int var14 = p_76484_5_ + p_76484_2_.nextInt(var8 * 2 + 1) - var8;
                     if(p_76484_1_.func_72799_c(var12, p_76484_4_, var14)) {
                        int var15 = 0;
                        if(p_76484_1_.func_72803_f(var12 - 1, p_76484_4_, var14).func_76220_a()) {
                           ++var15;
                        }

                        if(p_76484_1_.func_72803_f(var12 + 1, p_76484_4_, var14).func_76220_a()) {
                           ++var15;
                        }

                        if(p_76484_1_.func_72803_f(var12, p_76484_4_, var14 - 1).func_76220_a()) {
                           ++var15;
                        }

                        if(p_76484_1_.func_72803_f(var12, p_76484_4_, var14 + 1).func_76220_a()) {
                           ++var15;
                        }

                        if(var15 == 1) {
                           p_76484_1_.func_72832_d(var12, p_76484_4_, var14, Block.field_72077_au.field_71990_ca, 0, 2);
                           WeightedRandomChestContent[] var16 = WeightedRandomChestContent.func_92080_a(field_111189_a, new WeightedRandomChestContent[]{Item.field_92105_bW.func_92114_b(p_76484_2_)});
                           TileEntityChest var17 = (TileEntityChest)p_76484_1_.func_72796_p(var12, p_76484_4_, var14);
                           if(var17 != null) {
                              WeightedRandomChestContent.func_76293_a(p_76484_2_, var16, var17, 8);
                           }
                           break label101;
                        }
                     }

                     ++var11;
                     continue;
                  }
               }

               ++var10;
               break;
            }
         }

         p_76484_1_.func_72832_d(p_76484_3_, p_76484_4_, p_76484_5_, Block.field_72065_as.field_71990_ca, 0, 2);
         TileEntityMobSpawner var18 = (TileEntityMobSpawner)p_76484_1_.func_72796_p(p_76484_3_, p_76484_4_, p_76484_5_);
         if(var18 != null) {
            var18.func_98049_a().func_98272_a(this.func_76543_b(p_76484_2_));
         } else {
            System.err.println("Failed to fetch mob spawner entity at (" + p_76484_3_ + ", " + p_76484_4_ + ", " + p_76484_5_ + ")");
         }

         return true;
      } else {
         return false;
      }
   }

   private String func_76543_b(Random p_76543_1_) {
      int var2 = p_76543_1_.nextInt(4);
      return var2 == 0?"Skeleton":(var2 == 1?"Zombie":(var2 == 2?"Zombie":(var2 == 3?"Spider":"")));
   }

}
