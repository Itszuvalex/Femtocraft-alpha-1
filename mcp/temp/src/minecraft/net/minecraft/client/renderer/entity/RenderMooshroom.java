package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMooshroom extends RenderLiving {

   private static final ResourceLocation field_110880_a = new ResourceLocation("textures/entity/cow/mooshroom.png");


   public RenderMooshroom(ModelBase p_i1263_1_, float p_i1263_2_) {
      super(p_i1263_1_, p_i1263_2_);
   }

   public void func_77114_a(EntityMooshroom p_77114_1_, double p_77114_2_, double p_77114_4_, double p_77114_6_, float p_77114_8_, float p_77114_9_) {
      super.func_77031_a(p_77114_1_, p_77114_2_, p_77114_4_, p_77114_6_, p_77114_8_, p_77114_9_);
   }

   protected ResourceLocation func_110879_a(EntityMooshroom p_110879_1_) {
      return field_110880_a;
   }

   protected void func_77115_a(EntityMooshroom p_77115_1_, float p_77115_2_) {
      super.func_77029_c(p_77115_1_, p_77115_2_);
      if(!p_77115_1_.func_70631_g_()) {
         this.func_110776_a(TextureMap.field_110575_b);
         GL11.glEnable(2884);
         GL11.glPushMatrix();
         GL11.glScalef(1.0F, -1.0F, 1.0F);
         GL11.glTranslatef(0.2F, 0.4F, 0.5F);
         GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
         this.field_76988_d.func_78600_a(Block.field_72103_ag, 0, 1.0F);
         GL11.glTranslatef(0.1F, 0.0F, -0.6F);
         GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
         this.field_76988_d.func_78600_a(Block.field_72103_ag, 0, 1.0F);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         ((ModelQuadruped)this.field_77045_g).field_78150_a.func_78794_c(0.0625F);
         GL11.glScalef(1.0F, -1.0F, 1.0F);
         GL11.glTranslatef(0.0F, 0.75F, -0.2F);
         GL11.glRotatef(12.0F, 0.0F, 1.0F, 0.0F);
         this.field_76988_d.func_78600_a(Block.field_72103_ag, 0, 1.0F);
         GL11.glPopMatrix();
         GL11.glDisable(2884);
      }
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_77031_a(EntityLiving p_77031_1_, double p_77031_2_, double p_77031_4_, double p_77031_6_, float p_77031_8_, float p_77031_9_) {
      this.func_77114_a((EntityMooshroom)p_77031_1_, p_77031_2_, p_77031_4_, p_77031_6_, p_77031_8_, p_77031_9_);
   }

   // $FF: synthetic method
   protected void func_77029_c(EntityLivingBase p_77029_1_, float p_77029_2_) {
      this.func_77115_a((EntityMooshroom)p_77029_1_, p_77029_2_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_77101_a(EntityLivingBase p_77101_1_, double p_77101_2_, double p_77101_4_, double p_77101_6_, float p_77101_8_, float p_77101_9_) {
      this.func_77114_a((EntityMooshroom)p_77101_1_, p_77101_2_, p_77101_4_, p_77101_6_, p_77101_8_, p_77101_9_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110879_a((EntityMooshroom)p_110775_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_77114_a((EntityMooshroom)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

}
