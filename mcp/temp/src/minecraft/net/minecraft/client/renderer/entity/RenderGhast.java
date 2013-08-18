package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderGhast extends RenderLiving {

   private static final ResourceLocation field_110869_a = new ResourceLocation("textures/entity/ghast/ghast.png");
   private static final ResourceLocation field_110868_f = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");


   public RenderGhast() {
      super(new ModelGhast(), 0.5F);
   }

   protected ResourceLocation func_110867_a(EntityGhast p_110867_1_) {
      return p_110867_1_.func_110182_bF()?field_110868_f:field_110869_a;
   }

   protected void func_77069_a(EntityGhast p_77069_1_, float p_77069_2_) {
      float var4 = ((float)p_77069_1_.field_70794_e + (float)(p_77069_1_.field_70791_f - p_77069_1_.field_70794_e) * p_77069_2_) / 20.0F;
      if(var4 < 0.0F) {
         var4 = 0.0F;
      }

      var4 = 1.0F / (var4 * var4 * var4 * var4 * var4 * 2.0F + 1.0F);
      float var5 = (8.0F + var4) / 2.0F;
      float var6 = (8.0F + 1.0F / var4) / 2.0F;
      GL11.glScalef(var6, var5, var6);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void func_77041_b(EntityLivingBase p_77041_1_, float p_77041_2_) {
      this.func_77069_a((EntityGhast)p_77041_1_, p_77041_2_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110867_a((EntityGhast)p_110775_1_);
   }

}
