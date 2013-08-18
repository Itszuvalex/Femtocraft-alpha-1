package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class RenderLiving extends RendererLivingEntity {

   public RenderLiving(ModelBase p_i1262_1_, float p_i1262_2_) {
      super(p_i1262_1_, p_i1262_2_);
   }

   protected boolean func_130007_b(EntityLiving p_130007_1_) {
      return super.func_110813_b(p_130007_1_) && (p_130007_1_.func_94059_bO() || p_130007_1_.func_94056_bM() && p_130007_1_ == this.field_76990_c.field_96451_i);
   }

   public void func_77031_a(EntityLiving p_77031_1_, double p_77031_2_, double p_77031_4_, double p_77031_6_, float p_77031_8_, float p_77031_9_) {
      super.func_130000_a(p_77031_1_, p_77031_2_, p_77031_4_, p_77031_6_, p_77031_8_, p_77031_9_);
      this.func_110827_b(p_77031_1_, p_77031_2_, p_77031_4_, p_77031_6_, p_77031_8_, p_77031_9_);
   }

   private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_) {
      return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
   }

   protected void func_110827_b(EntityLiving p_110827_1_, double p_110827_2_, double p_110827_4_, double p_110827_6_, float p_110827_8_, float p_110827_9_) {
      Entity var10 = p_110827_1_.func_110166_bE();
      if(var10 != null) {
         p_110827_4_ -= (1.6D - (double)p_110827_1_.field_70131_O) * 0.5D;
         Tessellator var11 = Tessellator.field_78398_a;
         double var12 = this.func_110828_a((double)var10.field_70126_B, (double)var10.field_70177_z, (double)(p_110827_9_ * 0.5F)) * 0.01745329238474369D;
         double var14 = this.func_110828_a((double)var10.field_70127_C, (double)var10.field_70125_A, (double)(p_110827_9_ * 0.5F)) * 0.01745329238474369D;
         double var16 = Math.cos(var12);
         double var18 = Math.sin(var12);
         double var20 = Math.sin(var14);
         if(var10 instanceof EntityHanging) {
            var16 = 0.0D;
            var18 = 0.0D;
            var20 = -1.0D;
         }

         double var22 = Math.cos(var14);
         double var24 = this.func_110828_a(var10.field_70169_q, var10.field_70165_t, (double)p_110827_9_) - var16 * 0.7D - var18 * 0.5D * var22;
         double var26 = this.func_110828_a(var10.field_70167_r + (double)var10.func_70047_e() * 0.7D, var10.field_70163_u + (double)var10.func_70047_e() * 0.7D, (double)p_110827_9_) - var20 * 0.5D - 0.25D;
         double var28 = this.func_110828_a(var10.field_70166_s, var10.field_70161_v, (double)p_110827_9_) - var18 * 0.7D + var16 * 0.5D * var22;
         double var30 = this.func_110828_a((double)p_110827_1_.field_70760_ar, (double)p_110827_1_.field_70761_aq, (double)p_110827_9_) * 0.01745329238474369D + 1.5707963267948966D;
         var16 = Math.cos(var30) * (double)p_110827_1_.field_70130_N * 0.4D;
         var18 = Math.sin(var30) * (double)p_110827_1_.field_70130_N * 0.4D;
         double var32 = this.func_110828_a(p_110827_1_.field_70169_q, p_110827_1_.field_70165_t, (double)p_110827_9_) + var16;
         double var34 = this.func_110828_a(p_110827_1_.field_70167_r, p_110827_1_.field_70163_u, (double)p_110827_9_);
         double var36 = this.func_110828_a(p_110827_1_.field_70166_s, p_110827_1_.field_70161_v, (double)p_110827_9_) + var18;
         p_110827_2_ += var16;
         p_110827_6_ += var18;
         double var38 = (double)((float)(var24 - var32));
         double var40 = (double)((float)(var26 - var34));
         double var42 = (double)((float)(var28 - var36));
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2884);
         boolean var44 = true;
         double var45 = 0.025D;
         var11.func_78371_b(5);

         int var47;
         float var48;
         for(var47 = 0; var47 <= 24; ++var47) {
            if(var47 % 2 == 0) {
               var11.func_78369_a(0.5F, 0.4F, 0.3F, 1.0F);
            } else {
               var11.func_78369_a(0.35F, 0.28F, 0.21000001F, 1.0F);
            }

            var48 = (float)var47 / 24.0F;
            var11.func_78377_a(p_110827_2_ + var38 * (double)var48 + 0.0D, p_110827_4_ + var40 * (double)(var48 * var48 + var48) * 0.5D + (double)((24.0F - (float)var47) / 18.0F + 0.125F), p_110827_6_ + var42 * (double)var48);
            var11.func_78377_a(p_110827_2_ + var38 * (double)var48 + 0.025D, p_110827_4_ + var40 * (double)(var48 * var48 + var48) * 0.5D + (double)((24.0F - (float)var47) / 18.0F + 0.125F) + 0.025D, p_110827_6_ + var42 * (double)var48);
         }

         var11.func_78381_a();
         var11.func_78371_b(5);

         for(var47 = 0; var47 <= 24; ++var47) {
            if(var47 % 2 == 0) {
               var11.func_78369_a(0.5F, 0.4F, 0.3F, 1.0F);
            } else {
               var11.func_78369_a(0.35F, 0.28F, 0.21000001F, 1.0F);
            }

            var48 = (float)var47 / 24.0F;
            var11.func_78377_a(p_110827_2_ + var38 * (double)var48 + 0.0D, p_110827_4_ + var40 * (double)(var48 * var48 + var48) * 0.5D + (double)((24.0F - (float)var47) / 18.0F + 0.125F) + 0.025D, p_110827_6_ + var42 * (double)var48);
            var11.func_78377_a(p_110827_2_ + var38 * (double)var48 + 0.025D, p_110827_4_ + var40 * (double)(var48 * var48 + var48) * 0.5D + (double)((24.0F - (float)var47) / 18.0F + 0.125F), p_110827_6_ + var42 * (double)var48 + 0.025D);
         }

         var11.func_78381_a();
         GL11.glEnable(2896);
         GL11.glEnable(3553);
         GL11.glEnable(2884);
      }

   }

   // $FF: synthetic method
   // $FF: bridge method
   protected boolean func_110813_b(EntityLivingBase p_110813_1_) {
      return this.func_130007_b((EntityLiving)p_110813_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_77101_a(EntityLivingBase p_77101_1_, double p_77101_2_, double p_77101_4_, double p_77101_6_, float p_77101_8_, float p_77101_9_) {
      this.func_77031_a((EntityLiving)p_77101_1_, p_77101_2_, p_77101_4_, p_77101_6_, p_77101_8_, p_77101_9_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_77031_a((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }
}
