package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSheep extends RenderLiving {

   private static final ResourceLocation field_110885_a = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private static final ResourceLocation field_110884_f = new ResourceLocation("textures/entity/sheep/sheep.png");


   public RenderSheep(ModelBase p_i1266_1_, ModelBase p_i1266_2_, float p_i1266_3_) {
      super(p_i1266_1_, p_i1266_3_);
      this.func_77042_a(p_i1266_2_);
   }

   protected int func_77113_a(EntitySheep p_77113_1_, int p_77113_2_, float p_77113_3_) {
      if(p_77113_2_ == 0 && !p_77113_1_.func_70892_o()) {
         this.func_110776_a(field_110885_a);
         float var4 = 1.0F;
         int var5 = p_77113_1_.func_70896_n();
         GL11.glColor3f(var4 * EntitySheep.field_70898_d[var5][0], var4 * EntitySheep.field_70898_d[var5][1], var4 * EntitySheep.field_70898_d[var5][2]);
         return 1;
      } else {
         return -1;
      }
   }

   protected ResourceLocation func_110883_a(EntitySheep p_110883_1_) {
      return field_110884_f;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int func_77032_a(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
      return this.func_77113_a((EntitySheep)p_77032_1_, p_77032_2_, p_77032_3_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110883_a((EntitySheep)p_110775_1_);
   }

}
