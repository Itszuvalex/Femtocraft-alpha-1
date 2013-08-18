package net.minecraft.entity.ai;

import java.util.Collections;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTargetSelector;
import net.minecraft.entity.ai.EntityAINearestAttackableTargetSorter;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAINearestAttackableTarget extends EntityAITarget {

   private final Class field_75307_b;
   private final int field_75308_c;
   private final EntityAINearestAttackableTargetSorter field_75306_g;
   private final IEntitySelector field_82643_g;
   private EntityLivingBase field_75309_a;


   public EntityAINearestAttackableTarget(EntityCreature p_i1663_1_, Class p_i1663_2_, int p_i1663_3_, boolean p_i1663_4_) {
      this(p_i1663_1_, p_i1663_2_, p_i1663_3_, p_i1663_4_, false);
   }

   public EntityAINearestAttackableTarget(EntityCreature p_i1664_1_, Class p_i1664_2_, int p_i1664_3_, boolean p_i1664_4_, boolean p_i1664_5_) {
      this(p_i1664_1_, p_i1664_2_, p_i1664_3_, p_i1664_4_, p_i1664_5_, (IEntitySelector)null);
   }

   public EntityAINearestAttackableTarget(EntityCreature p_i1665_1_, Class p_i1665_2_, int p_i1665_3_, boolean p_i1665_4_, boolean p_i1665_5_, IEntitySelector p_i1665_6_) {
      super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
      this.field_75307_b = p_i1665_2_;
      this.field_75308_c = p_i1665_3_;
      this.field_75306_g = new EntityAINearestAttackableTargetSorter(p_i1665_1_);
      this.func_75248_a(1);
      this.field_82643_g = new EntityAINearestAttackableTargetSelector(this, p_i1665_6_);
   }

   public boolean func_75250_a() {
      if(this.field_75308_c > 0 && this.field_75299_d.func_70681_au().nextInt(this.field_75308_c) != 0) {
         return false;
      } else {
         double var1 = this.func_111175_f();
         List var3 = this.field_75299_d.field_70170_p.func_82733_a(this.field_75307_b, this.field_75299_d.field_70121_D.func_72314_b(var1, 4.0D, var1), this.field_82643_g);
         Collections.sort(var3, this.field_75306_g);
         if(var3.isEmpty()) {
            return false;
         } else {
            this.field_75309_a = (EntityLivingBase)var3.get(0);
            return true;
         }
      }
   }

   public void func_75249_e() {
      this.field_75299_d.func_70624_b(this.field_75309_a);
      super.func_75249_e();
   }
}
