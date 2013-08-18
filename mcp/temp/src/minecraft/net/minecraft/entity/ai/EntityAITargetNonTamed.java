package net.minecraft.entity.ai;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget {

   private EntityTameable field_75310_g;


   public EntityAITargetNonTamed(EntityTameable p_i1666_1_, Class p_i1666_2_, int p_i1666_3_, boolean p_i1666_4_) {
      super(p_i1666_1_, p_i1666_2_, p_i1666_3_, p_i1666_4_);
      this.field_75310_g = p_i1666_1_;
   }

   public boolean func_75250_a() {
      return !this.field_75310_g.func_70909_n() && super.func_75250_a();
   }
}
