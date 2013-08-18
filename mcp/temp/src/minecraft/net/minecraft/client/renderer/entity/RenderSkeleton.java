package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSkeleton extends RenderBiped {

   private static final ResourceLocation field_110862_k = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation field_110861_l = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");


   public RenderSkeleton() {
      super(new ModelSkeleton(), 0.5F);
   }

   protected void func_82438_a(EntitySkeleton p_82438_1_, float p_82438_2_) {
      if(p_82438_1_.func_82202_m() == 1) {
         GL11.glScalef(1.2F, 1.2F, 1.2F);
      }

   }

   protected void func_82422_c() {
      GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
   }

   protected ResourceLocation func_110860_a(EntitySkeleton p_110860_1_) {
      return p_110860_1_.func_82202_m() == 1?field_110861_l:field_110862_k;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110856_a(EntityLiving p_110856_1_) {
      return this.func_110860_a((EntitySkeleton)p_110856_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void func_77041_b(EntityLivingBase p_77041_1_, float p_77041_2_) {
      this.func_82438_a((EntitySkeleton)p_77041_1_, p_77041_2_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110860_a((EntitySkeleton)p_110775_1_);
   }

}
