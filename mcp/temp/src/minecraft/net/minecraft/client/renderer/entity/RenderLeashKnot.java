package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderLeashKnot extends Render {

   private static final ResourceLocation field_110802_a = new ResourceLocation("textures/entity/lead_knot.png");
   private ModelLeashKnot field_110801_f = new ModelLeashKnot();


   public void func_110799_a(EntityLeashKnot p_110799_1_, double p_110799_2_, double p_110799_4_, double p_110799_6_, float p_110799_8_, float p_110799_9_) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)p_110799_2_, (float)p_110799_4_, (float)p_110799_6_);
      float var10 = 0.0625F;
      GL11.glEnable('\u803a');
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glEnable(3008);
      this.func_110777_b(p_110799_1_);
      this.field_110801_f.func_78088_a(p_110799_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
      GL11.glPopMatrix();
   }

   protected ResourceLocation func_110800_a(EntityLeashKnot p_110800_1_) {
      return field_110802_a;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110800_a((EntityLeashKnot)p_110775_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_110799_a((EntityLeashKnot)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

}
