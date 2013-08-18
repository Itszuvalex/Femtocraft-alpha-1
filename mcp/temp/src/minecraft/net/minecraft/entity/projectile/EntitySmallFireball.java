package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {

   public EntitySmallFireball(World p_i1770_1_) {
      super(p_i1770_1_);
      this.func_70105_a(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World p_i1771_1_, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_) {
      super(p_i1771_1_, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
      this.func_70105_a(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World p_i1772_1_, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_) {
      super(p_i1772_1_, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
      this.func_70105_a(0.3125F, 0.3125F);
   }

   protected void func_70227_a(MovingObjectPosition p_70227_1_) {
      if(!this.field_70170_p.field_72995_K) {
         if(p_70227_1_.field_72308_g != null) {
            if(!p_70227_1_.field_72308_g.func_70045_F() && p_70227_1_.field_72308_g.func_70097_a(DamageSource.func_76362_a(this, this.field_70235_a), 5.0F)) {
               p_70227_1_.field_72308_g.func_70015_d(5);
            }
         } else {
            int var2 = p_70227_1_.field_72311_b;
            int var3 = p_70227_1_.field_72312_c;
            int var4 = p_70227_1_.field_72309_d;
            switch(p_70227_1_.field_72310_e) {
            case 0:
               --var3;
               break;
            case 1:
               ++var3;
               break;
            case 2:
               --var4;
               break;
            case 3:
               ++var4;
               break;
            case 4:
               --var2;
               break;
            case 5:
               ++var2;
            }

            if(this.field_70170_p.func_72799_c(var2, var3, var4)) {
               this.field_70170_p.func_94575_c(var2, var3, var4, Block.field_72067_ar.field_71990_ca);
            }
         }

         this.func_70106_y();
      }

   }

   public boolean func_70067_L() {
      return false;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      return false;
   }
}
