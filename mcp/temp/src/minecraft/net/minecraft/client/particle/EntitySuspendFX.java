package net.minecraft.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntitySuspendFX extends EntityFX {

   public EntitySuspendFX(World p_i1231_1_, double p_i1231_2_, double p_i1231_4_, double p_i1231_6_, double p_i1231_8_, double p_i1231_10_, double p_i1231_12_) {
      super(p_i1231_1_, p_i1231_2_, p_i1231_4_ - 0.125D, p_i1231_6_, p_i1231_8_, p_i1231_10_, p_i1231_12_);
      this.field_70552_h = 0.4F;
      this.field_70553_i = 0.4F;
      this.field_70551_j = 0.7F;
      this.func_70536_a(0);
      this.func_70105_a(0.01F, 0.01F);
      this.field_70544_f *= this.field_70146_Z.nextFloat() * 0.6F + 0.2F;
      this.field_70159_w = p_i1231_8_ * 0.0D;
      this.field_70181_x = p_i1231_10_ * 0.0D;
      this.field_70179_y = p_i1231_12_ * 0.0D;
      this.field_70547_e = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      if(this.field_70170_p.func_72803_f(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)) != Material.field_76244_g) {
         this.func_70106_y();
      }

      if(this.field_70547_e-- <= 0) {
         this.func_70106_y();
      }

   }
}
