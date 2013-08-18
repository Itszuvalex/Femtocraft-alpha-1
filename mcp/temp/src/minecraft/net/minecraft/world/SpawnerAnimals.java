package net.minecraft.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;

public final class SpawnerAnimals {

   private HashMap field_77193_b = new HashMap();


   protected static ChunkPosition func_77189_a(World p_77189_0_, int p_77189_1_, int p_77189_2_) {
      Chunk var3 = p_77189_0_.func_72964_e(p_77189_1_, p_77189_2_);
      int var4 = p_77189_1_ * 16 + p_77189_0_.field_73012_v.nextInt(16);
      int var5 = p_77189_2_ * 16 + p_77189_0_.field_73012_v.nextInt(16);
      int var6 = p_77189_0_.field_73012_v.nextInt(var3 == null?p_77189_0_.func_72940_L():var3.func_76625_h() + 16 - 1);
      return new ChunkPosition(var4, var6, var5);
   }

   public int func_77192_a(WorldServer p_77192_1_, boolean p_77192_2_, boolean p_77192_3_, boolean p_77192_4_) {
      if(!p_77192_2_ && !p_77192_3_) {
         return 0;
      } else {
         this.field_77193_b.clear();

         int var5;
         int var8;
         for(var5 = 0; var5 < p_77192_1_.field_73010_i.size(); ++var5) {
            EntityPlayer var6 = (EntityPlayer)p_77192_1_.field_73010_i.get(var5);
            int var7 = MathHelper.func_76128_c(var6.field_70165_t / 16.0D);
            var8 = MathHelper.func_76128_c(var6.field_70161_v / 16.0D);
            byte var9 = 8;

            for(int var10 = -var9; var10 <= var9; ++var10) {
               for(int var11 = -var9; var11 <= var9; ++var11) {
                  boolean var12 = var10 == -var9 || var10 == var9 || var11 == -var9 || var11 == var9;
                  ChunkCoordIntPair var13 = new ChunkCoordIntPair(var10 + var7, var11 + var8);
                  if(!var12) {
                     this.field_77193_b.put(var13, Boolean.valueOf(false));
                  } else if(!this.field_77193_b.containsKey(var13)) {
                     this.field_77193_b.put(var13, Boolean.valueOf(true));
                  }
               }
            }
         }

         var5 = 0;
         ChunkCoordinates var34 = p_77192_1_.func_72861_E();
         EnumCreatureType[] var35 = EnumCreatureType.values();
         var8 = var35.length;

         for(int var36 = 0; var36 < var8; ++var36) {
            EnumCreatureType var37 = var35[var36];
            if((!var37.func_75599_d() || p_77192_3_) && (var37.func_75599_d() || p_77192_2_) && (!var37.func_82705_e() || p_77192_4_) && p_77192_1_.func_72907_a(var37.func_75598_a()) <= var37.func_75601_b() * this.field_77193_b.size() / 256) {
               Iterator var39 = this.field_77193_b.keySet().iterator();

               label110:
               while(var39.hasNext()) {
                  ChunkCoordIntPair var38 = (ChunkCoordIntPair)var39.next();
                  if(!((Boolean)this.field_77193_b.get(var38)).booleanValue()) {
                     ChunkPosition var40 = func_77189_a(p_77192_1_, var38.field_77276_a, var38.field_77275_b);
                     int var14 = var40.field_76930_a;
                     int var15 = var40.field_76928_b;
                     int var16 = var40.field_76929_c;
                     if(!p_77192_1_.func_72809_s(var14, var15, var16) && p_77192_1_.func_72803_f(var14, var15, var16) == var37.func_75600_c()) {
                        int var17 = 0;
                        int var18 = 0;

                        while(var18 < 3) {
                           int var19 = var14;
                           int var20 = var15;
                           int var21 = var16;
                           byte var22 = 6;
                           SpawnListEntry var23 = null;
                           EntityLivingData var24 = null;
                           int var25 = 0;

                           while(true) {
                              if(var25 < 4) {
                                 label103: {
                                    var19 += p_77192_1_.field_73012_v.nextInt(var22) - p_77192_1_.field_73012_v.nextInt(var22);
                                    var20 += p_77192_1_.field_73012_v.nextInt(1) - p_77192_1_.field_73012_v.nextInt(1);
                                    var21 += p_77192_1_.field_73012_v.nextInt(var22) - p_77192_1_.field_73012_v.nextInt(var22);
                                    if(func_77190_a(var37, p_77192_1_, var19, var20, var21)) {
                                       float var26 = (float)var19 + 0.5F;
                                       float var27 = (float)var20;
                                       float var28 = (float)var21 + 0.5F;
                                       if(p_77192_1_.func_72977_a((double)var26, (double)var27, (double)var28, 24.0D) == null) {
                                          float var29 = var26 - (float)var34.field_71574_a;
                                          float var30 = var27 - (float)var34.field_71572_b;
                                          float var31 = var28 - (float)var34.field_71573_c;
                                          float var32 = var29 * var29 + var30 * var30 + var31 * var31;
                                          if(var32 >= 576.0F) {
                                             if(var23 == null) {
                                                var23 = p_77192_1_.func_73057_a(var37, var19, var20, var21);
                                                if(var23 == null) {
                                                   break label103;
                                                }
                                             }

                                             EntityLiving var41;
                                             try {
                                                var41 = (EntityLiving)var23.field_76300_b.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_77192_1_});
                                             } catch (Exception var33) {
                                                var33.printStackTrace();
                                                return var5;
                                             }

                                             var41.func_70012_b((double)var26, (double)var27, (double)var28, p_77192_1_.field_73012_v.nextFloat() * 360.0F, 0.0F);
                                             if(var41.func_70601_bi()) {
                                                ++var17;
                                                p_77192_1_.func_72838_d(var41);
                                                var24 = var41.func_110161_a(var24);
                                                if(var17 >= var41.func_70641_bl()) {
                                                   continue label110;
                                                }
                                             }

                                             var5 += var17;
                                          }
                                       }
                                    }

                                    ++var25;
                                    continue;
                                 }
                              }

                              ++var18;
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

         return var5;
      }
   }

   public static boolean func_77190_a(EnumCreatureType p_77190_0_, World p_77190_1_, int p_77190_2_, int p_77190_3_, int p_77190_4_) {
      if(p_77190_0_.func_75600_c() == Material.field_76244_g) {
         return p_77190_1_.func_72803_f(p_77190_2_, p_77190_3_, p_77190_4_).func_76224_d() && p_77190_1_.func_72803_f(p_77190_2_, p_77190_3_ - 1, p_77190_4_).func_76224_d() && !p_77190_1_.func_72809_s(p_77190_2_, p_77190_3_ + 1, p_77190_4_);
      } else if(!p_77190_1_.func_72797_t(p_77190_2_, p_77190_3_ - 1, p_77190_4_)) {
         return false;
      } else {
         int var5 = p_77190_1_.func_72798_a(p_77190_2_, p_77190_3_ - 1, p_77190_4_);
         return var5 != Block.field_71986_z.field_71990_ca && !p_77190_1_.func_72809_s(p_77190_2_, p_77190_3_, p_77190_4_) && !p_77190_1_.func_72803_f(p_77190_2_, p_77190_3_, p_77190_4_).func_76224_d() && !p_77190_1_.func_72809_s(p_77190_2_, p_77190_3_ + 1, p_77190_4_);
      }
   }

   public static void func_77191_a(World p_77191_0_, BiomeGenBase p_77191_1_, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random p_77191_6_) {
      List var7 = p_77191_1_.func_76747_a(EnumCreatureType.creature);
      if(!var7.isEmpty()) {
         while(p_77191_6_.nextFloat() < p_77191_1_.func_76741_f()) {
            SpawnListEntry var8 = (SpawnListEntry)WeightedRandom.func_76271_a(p_77191_0_.field_73012_v, var7);
            EntityLivingData var9 = null;
            int var10 = var8.field_76301_c + p_77191_6_.nextInt(1 + var8.field_76299_d - var8.field_76301_c);
            int var11 = p_77191_2_ + p_77191_6_.nextInt(p_77191_4_);
            int var12 = p_77191_3_ + p_77191_6_.nextInt(p_77191_5_);
            int var13 = var11;
            int var14 = var12;

            for(int var15 = 0; var15 < var10; ++var15) {
               boolean var16 = false;

               for(int var17 = 0; !var16 && var17 < 4; ++var17) {
                  int var18 = p_77191_0_.func_72825_h(var11, var12);
                  if(func_77190_a(EnumCreatureType.creature, p_77191_0_, var11, var18, var12)) {
                     float var19 = (float)var11 + 0.5F;
                     float var20 = (float)var18;
                     float var21 = (float)var12 + 0.5F;

                     EntityLiving var22;
                     try {
                        var22 = (EntityLiving)var8.field_76300_b.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_77191_0_});
                     } catch (Exception var24) {
                        var24.printStackTrace();
                        continue;
                     }

                     var22.func_70012_b((double)var19, (double)var20, (double)var21, p_77191_6_.nextFloat() * 360.0F, 0.0F);
                     p_77191_0_.func_72838_d(var22);
                     var9 = var22.func_110161_a(var9);
                     var16 = true;
                  }

                  var11 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);

                  for(var12 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5); var11 < p_77191_2_ || var11 >= p_77191_2_ + p_77191_4_ || var12 < p_77191_3_ || var12 >= p_77191_3_ + p_77191_4_; var12 = var14 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5)) {
                     var11 = var13 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
                  }
               }
            }
         }

      }
   }
}
