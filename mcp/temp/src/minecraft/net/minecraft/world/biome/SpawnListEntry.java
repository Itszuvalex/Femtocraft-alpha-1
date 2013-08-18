package net.minecraft.world.biome;

import net.minecraft.util.WeightedRandomItem;

public class SpawnListEntry extends WeightedRandomItem {

   public Class field_76300_b;
   public int field_76301_c;
   public int field_76299_d;


   public SpawnListEntry(Class p_i1970_1_, int p_i1970_2_, int p_i1970_3_, int p_i1970_4_) {
      super(p_i1970_2_);
      this.field_76300_b = p_i1970_1_;
      this.field_76301_c = p_i1970_3_;
      this.field_76299_d = p_i1970_4_;
   }
}
