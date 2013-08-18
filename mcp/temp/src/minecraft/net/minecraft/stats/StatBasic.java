package net.minecraft.stats;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class StatBasic extends StatBase {

   public StatBasic(int p_i1542_1_, String p_i1542_2_, IStatType p_i1542_3_) {
      super(p_i1542_1_, p_i1542_2_, p_i1542_3_);
   }

   public StatBasic(int p_i1543_1_, String p_i1543_2_) {
      super(p_i1543_1_, p_i1543_2_);
   }

   public StatBase func_75971_g() {
      super.func_75971_g();
      StatList.field_75941_c.add(this);
      return this;
   }
}
