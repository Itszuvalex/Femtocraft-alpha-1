package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderCreeper extends RenderLiving {

   private static final ResourceLocation field_110831_a = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
   private static final ResourceLocation field_110830_f = new ResourceLocation("textures/entity/creeper/creeper.png");
   private ModelBase field_77064_a = new ModelCreeper(2.0F);


   public RenderCreeper() {
      super(new ModelCreeper(), 0.5F);
   }

   protected void func_77060_a(EntityCreeper p_77060_1_, float p_77060_2_) {
      float var3 = p_77060_1_.func_70831_j(p_77060_2_);
      float var4 = 1.0F + MathHelper.func_76126_a(var3 * 100.0F) * var3 * 0.01F;
      if(var3 < 0.0F) {
         var3 = 0.0F;
      }

      if(var3 > 1.0F) {
         var3 = 1.0F;
      }

      var3 *= var3;
      var3 *= var3;
      float var5 = (1.0F + var3 * 0.4F) * var4;
      float var6 = (1.0F + var3 * 0.1F) / var4;
      GL11.glScalef(var5, var6, var5);
   }

   protected int func_77063_a(EntityCreeper p_77063_1_, float p_77063_2_, float p_77063_3_) {
      float var4 = p_77063_1_.func_70831_j(p_77063_3_);
      if((int)(var4 * 10.0F) % 2 == 0) {
         return 0;
      } else {
         int var5 = (int)(var4 * 0.2F * 255.0F);
         if(var5 < 0) {
            var5 = 0;
         }

         if(var5 > 255) {
            var5 = 255;
         }

         short var6 = 255;
         short var7 = 255;
         short var8 = 255;
         return var5 << 24 | var6 << 16 | var7 << 8 | var8;
      }
   }

   protected int func_77062_a(EntityCreeper p_77062_1_, int p_77062_2_, float p_77062_3_) {
      if(p_77062_1_.func_70830_n()) {
         if(p_77062_1_.func_82150_aj()) {
            GL11.glDepthMask(false);
         } else {
            GL11.glDepthMask(true);
         }

         if(p_77062_2_ == 1) {
            float var4 = (float)p_77062_1_.field_70173_aa + p_77062_3_;
            this.func_110776_a(field_110831_a);
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            float var5 = var4 * 0.01F;
            float var6 = var4 * 0.01F;
            GL11.glTranslatef(var5, var6, 0.0F);
            this.func_77042_a(this.field_77064_a);
            GL11.glMatrixMode(5888);
            GL11.glEnable(3042);
            float var7 = 0.5F;
            GL11.glColor4f(var7, var7, var7, 1.0F);
            GL11.glDisable(2896);
            GL11.glBlendFunc(1, 1);
            return 1;
         }

         if(p_77062_2_ == 2) {
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
         }
      }

      return -1;
   }

   protected int func_77061_b(EntityCreeper p_77061_1_, int p_77061_2_, float p_77061_3_) {
      return -1;
   }

   protected ResourceLocation func_110829_a(EntityCreeper p_110829_1_) {
      return field_110830_f;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void func_77041_b(EntityLivingBase p_77041_1_, float p_77041_2_) {
      this.func_77060_a((EntityCreeper)p_77041_1_, p_77041_2_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int func_77030_a(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_) {
      return this.func_77063_a((EntityCreeper)p_77030_1_, p_77030_2_, p_77030_3_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int func_77032_a(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
      return this.func_77062_a((EntityCreeper)p_77032_1_, p_77032_2_, p_77032_3_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int func_77035_b(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_) {
      return this.func_77061_b((EntityCreeper)p_77035_1_, p_77035_2_, p_77035_3_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110829_a((EntityCreeper)p_110775_1_);
   }

}
