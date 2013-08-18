package net.minecraft.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

class EntityAINearestAttackableTargetSelector implements IEntitySelector {

   // $FF: synthetic field
   final IEntitySelector field_111103_c;
   // $FF: synthetic field
   final EntityAINearestAttackableTarget field_111102_d;


   EntityAINearestAttackableTargetSelector(EntityAINearestAttackableTarget p_i1661_1_, IEntitySelector p_i1661_2_) {
      this.field_111102_d = p_i1661_1_;
      this.field_111103_c = p_i1661_2_;
   }

   public boolean func_82704_a(Entity p_82704_1_) {
      return !(p_82704_1_ instanceof EntityLivingBase)?false:(this.field_111103_c != null && !this.field_111103_c.func_82704_a(p_82704_1_)?false:this.field_111102_d.func_75296_a((EntityLivingBase)p_82704_1_, false));
   }
}
