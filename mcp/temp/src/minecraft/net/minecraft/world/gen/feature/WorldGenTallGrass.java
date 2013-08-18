package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTallGrass extends WorldGenerator {

   private int field_76535_a;
   private int field_76534_b;


   public WorldGenTallGrass(int p_i2026_1_, int p_i2026_2_) {
      this.field_76535_a = p_i2026_1_;
      this.field_76534_b = p_i2026_2_;
   }

   public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
      int var11;
      for(boolean var6 = false; ((var11 = p_76484_1_.func_72798_a(p_76484_3_, p_76484_4_, p_76484_5_)) == 0 || var11 == Block.field_71952_K.field_71990_ca) && p_76484_4_ > 0; --p_76484_4_) {
         ;
      }

      for(int var7 = 0; var7 < 128; ++var7) {
         int var8 = p_76484_3_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
         int var9 = p_76484_4_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
         int var10 = p_76484_5_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
         if(p_76484_1_.func_72799_c(var8, var9, var10) && Block.field_71973_m[this.field_76535_a].func_71854_d(p_76484_1_, var8, var9, var10)) {
            p_76484_1_.func_72832_d(var8, var9, var10, this.field_76535_a, this.field_76534_b, 2);
         }
      }

      return true;
   }
}
