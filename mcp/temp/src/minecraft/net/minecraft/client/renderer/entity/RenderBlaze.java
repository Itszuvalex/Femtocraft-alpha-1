package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderBlaze extends RenderLiving {

   private static final ResourceLocation field_110837_a = new ResourceLocation("textures/entity/blaze.png");
   private int field_77068_a;


   public RenderBlaze() {
      super(new ModelBlaze(), 0.5F);
      this.field_77068_a = ((ModelBlaze)this.field_77045_g).func_78104_a();
   }

   public void func_77067_a(EntityBlaze p_77067_1_, double p_77067_2_, double p_77067_4_, double p_77067_6_, float p_77067_8_, float p_77067_9_) {
      int var10 = ((ModelBlaze)this.field_77045_g).func_78104_a();
      if(var10 != this.field_77068_a) {
         this.field_77068_a = var10;
         this.field_77045_g = new ModelBlaze();
      }

      super.func_77031_a(p_77067_1_, p_77067_2_, p_77067_4_, p_77067_6_, p_77067_8_, p_77067_9_);
   }

   protected ResourceLocation func_110836_a(EntityBlaze p_110836_1_) {
      return field_110837_a;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_77031_a(EntityLiving p_77031_1_, double p_77031_2_, double p_77031_4_, double p_77031_6_, float p_77031_8_, float p_77031_9_) {
      this.func_77067_a((EntityBlaze)p_77031_1_, p_77031_2_, p_77031_4_, p_77031_6_, p_77031_8_, p_77031_9_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_77101_a(EntityLivingBase p_77101_1_, double p_77101_2_, double p_77101_4_, double p_77101_6_, float p_77101_8_, float p_77101_9_) {
      this.func_77067_a((EntityBlaze)p_77101_1_, p_77101_2_, p_77101_4_, p_77101_6_, p_77101_8_, p_77101_9_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110836_a((EntityBlaze)p_110775_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_77067_a((EntityBlaze)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

}
