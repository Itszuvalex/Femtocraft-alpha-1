package net.minecraft.entity.ai;

import java.util.Comparator;
import net.minecraft.entity.Entity;

public class EntityAINearestAttackableTargetSorter implements Comparator {

   private final Entity field_75459_b;


   public EntityAINearestAttackableTargetSorter(Entity p_i1662_1_) {
      this.field_75459_b = p_i1662_1_;
   }

   public int func_75458_a(Entity p_75458_1_, Entity p_75458_2_) {
      double var3 = this.field_75459_b.func_70068_e(p_75458_1_);
      double var5 = this.field_75459_b.func_70068_e(p_75458_2_);
      return var3 < var5?-1:(var3 > var5?1:0);
   }

   // $FF: synthetic method
   public int compare(Object p_compare_1_, Object p_compare_2_) {
      return this.func_75458_a((Entity)p_compare_1_, (Entity)p_compare_2_);
   }
}
