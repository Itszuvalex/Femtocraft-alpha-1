package net.minecraft.world.gen.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.gen.structure.ComponentStrongholdPortalRoom;
import net.minecraft.world.gen.structure.ComponentStrongholdStairs;
import net.minecraft.world.gen.structure.StructureStrongholdPieceWeight;

public class ComponentStrongholdStairs2 extends ComponentStrongholdStairs {

   public StructureStrongholdPieceWeight field_75027_a;
   public ComponentStrongholdPortalRoom field_75025_b;
   public List field_75026_c = new ArrayList();


   public ComponentStrongholdStairs2(int p_i2083_1_, Random p_i2083_2_, int p_i2083_3_, int p_i2083_4_) {
      super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
   }

   public ChunkPosition func_74868_a() {
      return this.field_75025_b != null?this.field_75025_b.func_74868_a():super.func_74868_a();
   }
}
