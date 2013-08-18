package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieINNER1;

class EntityZombieGroupData implements EntityLivingData {

   public boolean field_142048_a;
   public boolean field_142046_b;
   // $FF: synthetic field
   final EntityZombie field_142047_c;


   private EntityZombieGroupData(EntityZombie p_i2348_1_, boolean p_i2348_2_, boolean p_i2348_3_) {
      this.field_142047_c = p_i2348_1_;
      this.field_142048_a = false;
      this.field_142046_b = false;
      this.field_142048_a = p_i2348_2_;
      this.field_142046_b = p_i2348_3_;
   }

   // $FF: synthetic method
   EntityZombieGroupData(EntityZombie p_i2349_1_, boolean p_i2349_2_, boolean p_i2349_3_, EntityZombieINNER1 p_i2349_4_) {
      this(p_i2349_1_, p_i2349_2_, p_i2349_3_);
   }
}
