package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class RendererLivingEntity extends Render {

   private static final ResourceLocation field_110814_a = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   protected ModelBase field_77045_g;
   protected ModelBase field_77046_h;


   public RendererLivingEntity(ModelBase p_i1261_1_, float p_i1261_2_) {
      this.field_77045_g = p_i1261_1_;
      this.field_76989_e = p_i1261_2_;
   }

   public void func_77042_a(ModelBase p_77042_1_) {
      this.field_77046_h = p_77042_1_;
   }

   private float func_77034_a(float p_77034_1_, float p_77034_2_, float p_77034_3_) {
      float var4;
      for(var4 = p_77034_2_ - p_77034_1_; var4 < -180.0F; var4 += 360.0F) {
         ;
      }

      while(var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return p_77034_1_ + p_77034_3_ * var4;
   }

   public void func_130000_a(EntityLivingBase p_130000_1_, double p_130000_2_, double p_130000_4_, double p_130000_6_, float p_130000_8_, float p_130000_9_) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      this.field_77045_g.field_78095_p = this.func_77040_d(p_130000_1_, p_130000_9_);
      if(this.field_77046_h != null) {
         this.field_77046_h.field_78095_p = this.field_77045_g.field_78095_p;
      }

      this.field_77045_g.field_78093_q = p_130000_1_.func_70115_ae();
      if(this.field_77046_h != null) {
         this.field_77046_h.field_78093_q = this.field_77045_g.field_78093_q;
      }

      this.field_77045_g.field_78091_s = p_130000_1_.func_70631_g_();
      if(this.field_77046_h != null) {
         this.field_77046_h.field_78091_s = this.field_77045_g.field_78091_s;
      }

      try {
         float var10 = this.func_77034_a(p_130000_1_.field_70760_ar, p_130000_1_.field_70761_aq, p_130000_9_);
         float var11 = this.func_77034_a(p_130000_1_.field_70758_at, p_130000_1_.field_70759_as, p_130000_9_);
         float var13;
         if(p_130000_1_.func_70115_ae() && p_130000_1_.field_70154_o instanceof EntityLivingBase) {
            EntityLivingBase var12 = (EntityLivingBase)p_130000_1_.field_70154_o;
            var10 = this.func_77034_a(var12.field_70760_ar, var12.field_70761_aq, p_130000_9_);
            var13 = MathHelper.func_76142_g(var11 - var10);
            if(var13 < -85.0F) {
               var13 = -85.0F;
            }

            if(var13 >= 85.0F) {
               var13 = 85.0F;
            }

            var10 = var11 - var13;
            if(var13 * var13 > 2500.0F) {
               var10 += var13 * 0.2F;
            }
         }

         float var26 = p_130000_1_.field_70127_C + (p_130000_1_.field_70125_A - p_130000_1_.field_70127_C) * p_130000_9_;
         this.func_77039_a(p_130000_1_, p_130000_2_, p_130000_4_, p_130000_6_);
         var13 = this.func_77044_a(p_130000_1_, p_130000_9_);
         this.func_77043_a(p_130000_1_, var13, var10, p_130000_9_);
         float var14 = 0.0625F;
         GL11.glEnable('\u803a');
         GL11.glScalef(-1.0F, -1.0F, 1.0F);
         this.func_77041_b(p_130000_1_, p_130000_9_);
         GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
         float var15 = p_130000_1_.field_70722_aY + (p_130000_1_.field_70721_aZ - p_130000_1_.field_70722_aY) * p_130000_9_;
         float var16 = p_130000_1_.field_70754_ba - p_130000_1_.field_70721_aZ * (1.0F - p_130000_9_);
         if(p_130000_1_.func_70631_g_()) {
            var16 *= 3.0F;
         }

         if(var15 > 1.0F) {
            var15 = 1.0F;
         }

         GL11.glEnable(3008);
         this.field_77045_g.func_78086_a(p_130000_1_, var16, var15, p_130000_9_);
         this.func_77036_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);

         float var19;
         int var18;
         float var20;
         float var22;
         for(int var17 = 0; var17 < 4; ++var17) {
            var18 = this.func_77032_a(p_130000_1_, var17, p_130000_9_);
            if(var18 > 0) {
               this.field_77046_h.func_78086_a(p_130000_1_, var16, var15, p_130000_9_);
               this.field_77046_h.func_78088_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);
               if((var18 & 240) == 16) {
                  this.func_82408_c(p_130000_1_, var17, p_130000_9_);
                  this.field_77046_h.func_78088_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);
               }

               if((var18 & 15) == 15) {
                  var19 = (float)p_130000_1_.field_70173_aa + p_130000_9_;
                  this.func_110776_a(field_110814_a);
                  GL11.glEnable(3042);
                  var20 = 0.5F;
                  GL11.glColor4f(var20, var20, var20, 1.0F);
                  GL11.glDepthFunc(514);
                  GL11.glDepthMask(false);

                  for(int var21 = 0; var21 < 2; ++var21) {
                     GL11.glDisable(2896);
                     var22 = 0.76F;
                     GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
                     GL11.glBlendFunc(768, 1);
                     GL11.glMatrixMode(5890);
                     GL11.glLoadIdentity();
                     float var23 = var19 * (0.001F + (float)var21 * 0.003F) * 20.0F;
                     float var24 = 0.33333334F;
                     GL11.glScalef(var24, var24, var24);
                     GL11.glRotatef(30.0F - (float)var21 * 60.0F, 0.0F, 0.0F, 1.0F);
                     GL11.glTranslatef(0.0F, var23, 0.0F);
                     GL11.glMatrixMode(5888);
                     this.field_77046_h.func_78088_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);
                  }

                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  GL11.glMatrixMode(5890);
                  GL11.glDepthMask(true);
                  GL11.glLoadIdentity();
                  GL11.glMatrixMode(5888);
                  GL11.glEnable(2896);
                  GL11.glDisable(3042);
                  GL11.glDepthFunc(515);
               }

               GL11.glDisable(3042);
               GL11.glEnable(3008);
            }
         }

         GL11.glDepthMask(true);
         this.func_77029_c(p_130000_1_, p_130000_9_);
         float var27 = p_130000_1_.func_70013_c(p_130000_9_);
         var18 = this.func_77030_a(p_130000_1_, var27, p_130000_9_);
         OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
         GL11.glDisable(3553);
         OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
         if((var18 >> 24 & 255) > 0 || p_130000_1_.field_70737_aN > 0 || p_130000_1_.field_70725_aQ > 0) {
            GL11.glDisable(3553);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthFunc(514);
            if(p_130000_1_.field_70737_aN > 0 || p_130000_1_.field_70725_aQ > 0) {
               GL11.glColor4f(var27, 0.0F, 0.0F, 0.4F);
               this.field_77045_g.func_78088_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);

               for(int var28 = 0; var28 < 4; ++var28) {
                  if(this.func_77035_b(p_130000_1_, var28, p_130000_9_) >= 0) {
                     GL11.glColor4f(var27, 0.0F, 0.0F, 0.4F);
                     this.field_77046_h.func_78088_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);
                  }
               }
            }

            if((var18 >> 24 & 255) > 0) {
               var19 = (float)(var18 >> 16 & 255) / 255.0F;
               var20 = (float)(var18 >> 8 & 255) / 255.0F;
               float var30 = (float)(var18 & 255) / 255.0F;
               var22 = (float)(var18 >> 24 & 255) / 255.0F;
               GL11.glColor4f(var19, var20, var30, var22);
               this.field_77045_g.func_78088_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);

               for(int var29 = 0; var29 < 4; ++var29) {
                  if(this.func_77035_b(p_130000_1_, var29, p_130000_9_) >= 0) {
                     GL11.glColor4f(var19, var20, var30, var22);
                     this.field_77046_h.func_78088_a(p_130000_1_, var16, var15, var13, var11 - var10, var26, var14);
                  }
               }
            }

            GL11.glDepthFunc(515);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glEnable(3553);
         }

         GL11.glDisable('\u803a');
      } catch (Exception var25) {
         var25.printStackTrace();
      }

      OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
      GL11.glEnable(3553);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
      this.func_77033_b(p_130000_1_, p_130000_2_, p_130000_4_, p_130000_6_);
   }

   protected void func_77036_a(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
      this.func_110777_b(p_77036_1_);
      if(!p_77036_1_.func_82150_aj()) {
         this.field_77045_g.func_78088_a(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
      } else if(!p_77036_1_.func_98034_c(Minecraft.func_71410_x().field_71439_g)) {
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
         GL11.glDepthMask(false);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glAlphaFunc(516, 0.003921569F);
         this.field_77045_g.func_78088_a(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
         GL11.glDisable(3042);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glPopMatrix();
         GL11.glDepthMask(true);
      } else {
         this.field_77045_g.func_78087_a(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, p_77036_1_);
      }

   }

   protected void func_77039_a(EntityLivingBase p_77039_1_, double p_77039_2_, double p_77039_4_, double p_77039_6_) {
      GL11.glTranslatef((float)p_77039_2_, (float)p_77039_4_, (float)p_77039_6_);
   }

   protected void func_77043_a(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
      GL11.glRotatef(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
      if(p_77043_1_.field_70725_aQ > 0) {
         float var5 = ((float)p_77043_1_.field_70725_aQ + p_77043_4_ - 1.0F) / 20.0F * 1.6F;
         var5 = MathHelper.func_76129_c(var5);
         if(var5 > 1.0F) {
            var5 = 1.0F;
         }

         GL11.glRotatef(var5 * this.func_77037_a(p_77043_1_), 0.0F, 0.0F, 1.0F);
      } else if((p_77043_1_.func_70023_ak().equals("Dinnerbone") || p_77043_1_.func_70023_ak().equals("Grumm")) && (!(p_77043_1_ instanceof EntityPlayer) || !((EntityPlayer)p_77043_1_).func_82238_cc())) {
         GL11.glTranslatef(0.0F, p_77043_1_.field_70131_O + 0.1F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      }

   }

   protected float func_77040_d(EntityLivingBase p_77040_1_, float p_77040_2_) {
      return p_77040_1_.func_70678_g(p_77040_2_);
   }

   protected float func_77044_a(EntityLivingBase p_77044_1_, float p_77044_2_) {
      return (float)p_77044_1_.field_70173_aa + p_77044_2_;
   }

   protected void func_77029_c(EntityLivingBase p_77029_1_, float p_77029_2_) {}

   protected void func_85093_e(EntityLivingBase p_85093_1_, float p_85093_2_) {
      int var3 = p_85093_1_.func_85035_bI();
      if(var3 > 0) {
         EntityArrow var4 = new EntityArrow(p_85093_1_.field_70170_p, p_85093_1_.field_70165_t, p_85093_1_.field_70163_u, p_85093_1_.field_70161_v);
         Random var5 = new Random((long)p_85093_1_.field_70157_k);
         RenderHelper.func_74518_a();

         for(int var6 = 0; var6 < var3; ++var6) {
            GL11.glPushMatrix();
            ModelRenderer var7 = this.field_77045_g.func_85181_a(var5);
            ModelBox var8 = (ModelBox)var7.field_78804_l.get(var5.nextInt(var7.field_78804_l.size()));
            var7.func_78794_c(0.0625F);
            float var9 = var5.nextFloat();
            float var10 = var5.nextFloat();
            float var11 = var5.nextFloat();
            float var12 = (var8.field_78252_a + (var8.field_78248_d - var8.field_78252_a) * var9) / 16.0F;
            float var13 = (var8.field_78250_b + (var8.field_78249_e - var8.field_78250_b) * var10) / 16.0F;
            float var14 = (var8.field_78251_c + (var8.field_78246_f - var8.field_78251_c) * var11) / 16.0F;
            GL11.glTranslatef(var12, var13, var14);
            var9 = var9 * 2.0F - 1.0F;
            var10 = var10 * 2.0F - 1.0F;
            var11 = var11 * 2.0F - 1.0F;
            var9 *= -1.0F;
            var10 *= -1.0F;
            var11 *= -1.0F;
            float var15 = MathHelper.func_76129_c(var9 * var9 + var11 * var11);
            var4.field_70126_B = var4.field_70177_z = (float)(Math.atan2((double)var9, (double)var11) * 180.0D / 3.1415927410125732D);
            var4.field_70127_C = var4.field_70125_A = (float)(Math.atan2((double)var10, (double)var15) * 180.0D / 3.1415927410125732D);
            double var16 = 0.0D;
            double var18 = 0.0D;
            double var20 = 0.0D;
            float var22 = 0.0F;
            this.field_76990_c.func_78719_a(var4, var16, var18, var20, var22, p_85093_2_);
            GL11.glPopMatrix();
         }

         RenderHelper.func_74519_b();
      }

   }

   protected int func_77035_b(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_) {
      return this.func_77032_a(p_77035_1_, p_77035_2_, p_77035_3_);
   }

   protected int func_77032_a(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
      return -1;
   }

   protected void func_82408_c(EntityLivingBase p_82408_1_, int p_82408_2_, float p_82408_3_) {}

   protected float func_77037_a(EntityLivingBase p_77037_1_) {
      return 90.0F;
   }

   protected int func_77030_a(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_) {
      return 0;
   }

   protected void func_77041_b(EntityLivingBase p_77041_1_, float p_77041_2_) {}

   protected void func_77033_b(EntityLivingBase p_77033_1_, double p_77033_2_, double p_77033_4_, double p_77033_6_) {
      if(this.func_110813_b(p_77033_1_)) {
         float var8 = 1.6F;
         float var9 = 0.016666668F * var8;
         double var10 = p_77033_1_.func_70068_e(this.field_76990_c.field_78734_h);
         float var12 = p_77033_1_.func_70093_af()?32.0F:64.0F;
         if(var10 < (double)(var12 * var12)) {
            String var13 = p_77033_1_.func_96090_ax();
            if(p_77033_1_.func_70093_af()) {
               FontRenderer var14 = this.func_76983_a();
               GL11.glPushMatrix();
               GL11.glTranslatef((float)p_77033_2_ + 0.0F, (float)p_77033_4_ + p_77033_1_.field_70131_O + 0.5F, (float)p_77033_6_);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
               GL11.glScalef(-var9, -var9, var9);
               GL11.glDisable(2896);
               GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
               GL11.glDepthMask(false);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               Tessellator var15 = Tessellator.field_78398_a;
               GL11.glDisable(3553);
               var15.func_78382_b();
               int var16 = var14.func_78256_a(var13) / 2;
               var15.func_78369_a(0.0F, 0.0F, 0.0F, 0.25F);
               var15.func_78377_a((double)(-var16 - 1), -1.0D, 0.0D);
               var15.func_78377_a((double)(-var16 - 1), 8.0D, 0.0D);
               var15.func_78377_a((double)(var16 + 1), 8.0D, 0.0D);
               var15.func_78377_a((double)(var16 + 1), -1.0D, 0.0D);
               var15.func_78381_a();
               GL11.glEnable(3553);
               GL11.glDepthMask(true);
               var14.func_78276_b(var13, -var14.func_78256_a(var13) / 2, 0, 553648127);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            } else {
               this.func_96449_a(p_77033_1_, p_77033_2_, p_77033_4_, p_77033_6_, var13, var9, var10);
            }
         }
      }

   }

   protected boolean func_110813_b(EntityLivingBase p_110813_1_) {
      return Minecraft.func_71382_s() && p_110813_1_ != this.field_76990_c.field_78734_h && !p_110813_1_.func_98034_c(Minecraft.func_71410_x().field_71439_g) && p_110813_1_.field_70153_n == null;
   }

   protected void func_96449_a(EntityLivingBase p_96449_1_, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_) {
      if(p_96449_1_.func_70608_bn()) {
         this.func_77038_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_ - 1.5D, p_96449_6_, 64);
      } else {
         this.func_77038_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_, p_96449_6_, 64);
      }

   }

   protected void func_77038_a(EntityLivingBase p_77038_1_, String p_77038_2_, double p_77038_3_, double p_77038_5_, double p_77038_7_, int p_77038_9_) {
      double var10 = p_77038_1_.func_70068_e(this.field_76990_c.field_78734_h);
      if(var10 <= (double)(p_77038_9_ * p_77038_9_)) {
         FontRenderer var12 = this.func_76983_a();
         float var13 = 1.6F;
         float var14 = 0.016666668F * var13;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)p_77038_3_ + 0.0F, (float)p_77038_5_ + p_77038_1_.field_70131_O + 0.5F, (float)p_77038_7_);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-var14, -var14, var14);
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         Tessellator var15 = Tessellator.field_78398_a;
         byte var16 = 0;
         if(p_77038_2_.equals("deadmau5")) {
            var16 = -10;
         }

         GL11.glDisable(3553);
         var15.func_78382_b();
         int var17 = var12.func_78256_a(p_77038_2_) / 2;
         var15.func_78369_a(0.0F, 0.0F, 0.0F, 0.25F);
         var15.func_78377_a((double)(-var17 - 1), (double)(-1 + var16), 0.0D);
         var15.func_78377_a((double)(-var17 - 1), (double)(8 + var16), 0.0D);
         var15.func_78377_a((double)(var17 + 1), (double)(8 + var16), 0.0D);
         var15.func_78377_a((double)(var17 + 1), (double)(-1 + var16), 0.0D);
         var15.func_78381_a();
         GL11.glEnable(3553);
         var12.func_78276_b(p_77038_2_, -var12.func_78256_a(p_77038_2_) / 2, var16, 553648127);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         var12.func_78276_b(p_77038_2_, -var12.func_78256_a(p_77038_2_) / 2, var16, -1);
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_130000_a((EntityLivingBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

}
