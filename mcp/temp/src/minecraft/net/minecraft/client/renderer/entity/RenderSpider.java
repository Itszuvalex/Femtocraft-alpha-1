package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSpider extends RenderLiving {

   private static final ResourceLocation field_110891_a = new ResourceLocation("textures/entity/spider_eyes.png");
   private static final ResourceLocation field_110890_f = new ResourceLocation("textures/entity/spider/spider.png");


   public RenderSpider() {
      super(new ModelSpider(), 1.0F);
      this.func_77042_a(new ModelSpider());
   }

   protected float func_77095_a(EntitySpider p_77095_1_) {
      return 180.0F;
   }

   protected int func_77097_a(EntitySpider p_77097_1_, int p_77097_2_, float p_77097_3_) {
      if(p_77097_2_ != 0) {
         return -1;
      } else {
         this.func_110776_a(field_110891_a);
         float var4 = 1.0F;
         GL11.glEnable(3042);
         GL11.glDisable(3008);
         GL11.glBlendFunc(1, 1);
         if(p_77097_1_.func_82150_aj()) {
            GL11.glDepthMask(false);
         } else {
            GL11.glDepthMask(true);
         }

         char var5 = '\uf0f0';
         int var6 = var5 % 65536;
         int var7 = var5 / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)var6 / 1.0F, (float)var7 / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
         return 1;
      }
   }

   protected ResourceLocation func_110889_a(EntitySpider p_110889_1_) {
      return field_110890_f;
   }

   // $FF: synthetic method
   protected float func_77037_a(EntityLivingBase p_77037_1_) {
      return this.func_77095_a((EntitySpider)p_77037_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int func_77032_a(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
      return this.func_77097_a((EntitySpider)p_77032_1_, p_77032_2_, p_77032_3_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110889_a((EntitySpider)p_110775_1_);
   }

}
