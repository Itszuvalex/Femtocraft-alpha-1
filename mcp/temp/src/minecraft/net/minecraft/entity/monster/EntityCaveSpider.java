package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityCaveSpider extends EntitySpider {

   public EntityCaveSpider(World p_i1732_1_) {
      super(p_i1732_1_);
      this.func_70105_a(0.7F, 0.5F);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D);
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      if(super.func_70652_k(p_70652_1_)) {
         if(p_70652_1_ instanceof EntityLivingBase) {
            byte var2 = 0;
            if(this.field_70170_p.field_73013_u > 1) {
               if(this.field_70170_p.field_73013_u == 2) {
                  var2 = 7;
               } else if(this.field_70170_p.field_73013_u == 3) {
                  var2 = 15;
               }
            }

            if(var2 > 0) {
               ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, var2 * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public EntityLivingData func_110161_a(EntityLivingData p_110161_1_) {
      return p_110161_1_;
   }
}
