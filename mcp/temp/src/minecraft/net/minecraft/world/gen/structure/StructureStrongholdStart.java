package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentStrongholdStairs2;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;

class StructureStrongholdStart extends StructureStart {

   public StructureStrongholdStart(World p_i2067_1_, Random p_i2067_2_, int p_i2067_3_, int p_i2067_4_) {
      StructureStrongholdPieces.func_75198_a();
      ComponentStrongholdStairs2 var5 = new ComponentStrongholdStairs2(0, p_i2067_2_, (p_i2067_3_ << 4) + 2, (p_i2067_4_ << 4) + 2);
      this.field_75075_a.add(var5);
      var5.func_74861_a(var5, this.field_75075_a, p_i2067_2_);
      List var6 = var5.field_75026_c;

      while(!var6.isEmpty()) {
         int var7 = p_i2067_2_.nextInt(var6.size());
         StructureComponent var8 = (StructureComponent)var6.remove(var7);
         var8.func_74861_a(var5, this.field_75075_a, p_i2067_2_);
      }

      this.func_75072_c();
      this.func_75067_a(p_i2067_1_, p_i2067_2_, 10);
   }
}
