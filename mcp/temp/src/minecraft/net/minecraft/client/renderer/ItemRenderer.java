package net.minecraft.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ItemRenderer {

   private static final ResourceLocation field_110930_b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private static final ResourceLocation field_110931_c = new ResourceLocation("textures/map/map_background.png");
   private static final ResourceLocation field_110929_d = new ResourceLocation("textures/misc/underwater.png");
   private Minecraft field_78455_a;
   private ItemStack field_78453_b;
   private float field_78454_c;
   private float field_78451_d;
   private RenderBlocks field_78452_e = new RenderBlocks();
   public final MapItemRenderer field_78449_f;
   private int field_78450_g = -1;


   public ItemRenderer(Minecraft p_i1247_1_) {
      this.field_78455_a = p_i1247_1_;
      this.field_78449_f = new MapItemRenderer(p_i1247_1_.field_71474_y, p_i1247_1_.func_110434_K());
   }

   public void func_78443_a(EntityLivingBase p_78443_1_, ItemStack p_78443_2_, int p_78443_3_) {
      GL11.glPushMatrix();
      TextureManager var4 = this.field_78455_a.func_110434_K();
      if(p_78443_2_.func_94608_d() == 0 && p_78443_2_.field_77993_c < Block.field_71973_m.length && Block.field_71973_m[p_78443_2_.field_77993_c] != null && RenderBlocks.func_78597_b(Block.field_71973_m[p_78443_2_.field_77993_c].func_71857_b())) {
         var4.func_110577_a(var4.func_130087_a(0));
         this.field_78452_e.func_78600_a(Block.field_71973_m[p_78443_2_.field_77993_c], p_78443_2_.func_77960_j(), 1.0F);
      } else {
         Icon var5 = p_78443_1_.func_70620_b(p_78443_2_, p_78443_3_);
         if(var5 == null) {
            GL11.glPopMatrix();
            return;
         }

         var4.func_110577_a(var4.func_130087_a(p_78443_2_.func_94608_d()));
         Tessellator var6 = Tessellator.field_78398_a;
         float var7 = var5.func_94209_e();
         float var8 = var5.func_94212_f();
         float var9 = var5.func_94206_g();
         float var10 = var5.func_94210_h();
         float var11 = 0.0F;
         float var12 = 0.3F;
         GL11.glEnable('\u803a');
         GL11.glTranslatef(-var11, -var12, 0.0F);
         float var13 = 1.5F;
         GL11.glScalef(var13, var13, var13);
         GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
         func_78439_a(var6, var8, var9, var7, var10, var5.func_94211_a(), var5.func_94216_b(), 0.0625F);
         if(p_78443_2_.func_77962_s() && p_78443_3_ == 0) {
            GL11.glDepthFunc(514);
            GL11.glDisable(2896);
            var4.func_110577_a(field_110930_b);
            GL11.glEnable(3042);
            GL11.glBlendFunc(768, 1);
            float var14 = 0.76F;
            GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            float var15 = 0.125F;
            GL11.glScalef(var15, var15, var15);
            float var16 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(var16, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            func_78439_a(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(var15, var15, var15);
            var16 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-var16, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            func_78439_a(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glDepthFunc(515);
         }

         GL11.glDisable('\u803a');
      }

      GL11.glPopMatrix();
   }

   public static void func_78439_a(Tessellator p_78439_0_, float p_78439_1_, float p_78439_2_, float p_78439_3_, float p_78439_4_, int p_78439_5_, int p_78439_6_, float p_78439_7_) {
      p_78439_0_.func_78382_b();
      p_78439_0_.func_78375_b(0.0F, 0.0F, 1.0F);
      p_78439_0_.func_78374_a(0.0D, 0.0D, 0.0D, (double)p_78439_1_, (double)p_78439_4_);
      p_78439_0_.func_78374_a(1.0D, 0.0D, 0.0D, (double)p_78439_3_, (double)p_78439_4_);
      p_78439_0_.func_78374_a(1.0D, 1.0D, 0.0D, (double)p_78439_3_, (double)p_78439_2_);
      p_78439_0_.func_78374_a(0.0D, 1.0D, 0.0D, (double)p_78439_1_, (double)p_78439_2_);
      p_78439_0_.func_78381_a();
      p_78439_0_.func_78382_b();
      p_78439_0_.func_78375_b(0.0F, 0.0F, -1.0F);
      p_78439_0_.func_78374_a(0.0D, 1.0D, (double)(0.0F - p_78439_7_), (double)p_78439_1_, (double)p_78439_2_);
      p_78439_0_.func_78374_a(1.0D, 1.0D, (double)(0.0F - p_78439_7_), (double)p_78439_3_, (double)p_78439_2_);
      p_78439_0_.func_78374_a(1.0D, 0.0D, (double)(0.0F - p_78439_7_), (double)p_78439_3_, (double)p_78439_4_);
      p_78439_0_.func_78374_a(0.0D, 0.0D, (double)(0.0F - p_78439_7_), (double)p_78439_1_, (double)p_78439_4_);
      p_78439_0_.func_78381_a();
      float var8 = 0.5F * (p_78439_1_ - p_78439_3_) / (float)p_78439_5_;
      float var9 = 0.5F * (p_78439_4_ - p_78439_2_) / (float)p_78439_6_;
      p_78439_0_.func_78382_b();
      p_78439_0_.func_78375_b(-1.0F, 0.0F, 0.0F);

      int var10;
      float var11;
      float var12;
      for(var10 = 0; var10 < p_78439_5_; ++var10) {
         var11 = (float)var10 / (float)p_78439_5_;
         var12 = p_78439_1_ + (p_78439_3_ - p_78439_1_) * var11 - var8;
         p_78439_0_.func_78374_a((double)var11, 0.0D, (double)(0.0F - p_78439_7_), (double)var12, (double)p_78439_4_);
         p_78439_0_.func_78374_a((double)var11, 0.0D, 0.0D, (double)var12, (double)p_78439_4_);
         p_78439_0_.func_78374_a((double)var11, 1.0D, 0.0D, (double)var12, (double)p_78439_2_);
         p_78439_0_.func_78374_a((double)var11, 1.0D, (double)(0.0F - p_78439_7_), (double)var12, (double)p_78439_2_);
      }

      p_78439_0_.func_78381_a();
      p_78439_0_.func_78382_b();
      p_78439_0_.func_78375_b(1.0F, 0.0F, 0.0F);

      float var13;
      for(var10 = 0; var10 < p_78439_5_; ++var10) {
         var11 = (float)var10 / (float)p_78439_5_;
         var12 = p_78439_1_ + (p_78439_3_ - p_78439_1_) * var11 - var8;
         var13 = var11 + 1.0F / (float)p_78439_5_;
         p_78439_0_.func_78374_a((double)var13, 1.0D, (double)(0.0F - p_78439_7_), (double)var12, (double)p_78439_2_);
         p_78439_0_.func_78374_a((double)var13, 1.0D, 0.0D, (double)var12, (double)p_78439_2_);
         p_78439_0_.func_78374_a((double)var13, 0.0D, 0.0D, (double)var12, (double)p_78439_4_);
         p_78439_0_.func_78374_a((double)var13, 0.0D, (double)(0.0F - p_78439_7_), (double)var12, (double)p_78439_4_);
      }

      p_78439_0_.func_78381_a();
      p_78439_0_.func_78382_b();
      p_78439_0_.func_78375_b(0.0F, 1.0F, 0.0F);

      for(var10 = 0; var10 < p_78439_6_; ++var10) {
         var11 = (float)var10 / (float)p_78439_6_;
         var12 = p_78439_4_ + (p_78439_2_ - p_78439_4_) * var11 - var9;
         var13 = var11 + 1.0F / (float)p_78439_6_;
         p_78439_0_.func_78374_a(0.0D, (double)var13, 0.0D, (double)p_78439_1_, (double)var12);
         p_78439_0_.func_78374_a(1.0D, (double)var13, 0.0D, (double)p_78439_3_, (double)var12);
         p_78439_0_.func_78374_a(1.0D, (double)var13, (double)(0.0F - p_78439_7_), (double)p_78439_3_, (double)var12);
         p_78439_0_.func_78374_a(0.0D, (double)var13, (double)(0.0F - p_78439_7_), (double)p_78439_1_, (double)var12);
      }

      p_78439_0_.func_78381_a();
      p_78439_0_.func_78382_b();
      p_78439_0_.func_78375_b(0.0F, -1.0F, 0.0F);

      for(var10 = 0; var10 < p_78439_6_; ++var10) {
         var11 = (float)var10 / (float)p_78439_6_;
         var12 = p_78439_4_ + (p_78439_2_ - p_78439_4_) * var11 - var9;
         p_78439_0_.func_78374_a(1.0D, (double)var11, 0.0D, (double)p_78439_3_, (double)var12);
         p_78439_0_.func_78374_a(0.0D, (double)var11, 0.0D, (double)p_78439_1_, (double)var12);
         p_78439_0_.func_78374_a(0.0D, (double)var11, (double)(0.0F - p_78439_7_), (double)p_78439_1_, (double)var12);
         p_78439_0_.func_78374_a(1.0D, (double)var11, (double)(0.0F - p_78439_7_), (double)p_78439_3_, (double)var12);
      }

      p_78439_0_.func_78381_a();
   }

   public void func_78440_a(float p_78440_1_) {
      float var2 = this.field_78451_d + (this.field_78454_c - this.field_78451_d) * p_78440_1_;
      EntityClientPlayerMP var3 = this.field_78455_a.field_71439_g;
      float var4 = var3.field_70127_C + (var3.field_70125_A - var3.field_70127_C) * p_78440_1_;
      GL11.glPushMatrix();
      GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(var3.field_70126_B + (var3.field_70177_z - var3.field_70126_B) * p_78440_1_, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GL11.glPopMatrix();
      EntityPlayerSP var5 = (EntityPlayerSP)var3;
      float var6 = var5.field_71164_i + (var5.field_71155_g - var5.field_71164_i) * p_78440_1_;
      float var7 = var5.field_71163_h + (var5.field_71154_f - var5.field_71163_h) * p_78440_1_;
      GL11.glRotatef((var3.field_70125_A - var6) * 0.1F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef((var3.field_70177_z - var7) * 0.1F, 0.0F, 1.0F, 0.0F);
      ItemStack var8 = this.field_78453_b;
      float var9 = this.field_78455_a.field_71441_e.func_72801_o(MathHelper.func_76128_c(var3.field_70165_t), MathHelper.func_76128_c(var3.field_70163_u), MathHelper.func_76128_c(var3.field_70161_v));
      var9 = 1.0F;
      int var10 = this.field_78455_a.field_71441_e.func_72802_i(MathHelper.func_76128_c(var3.field_70165_t), MathHelper.func_76128_c(var3.field_70163_u), MathHelper.func_76128_c(var3.field_70161_v), 0);
      int var11 = var10 % 65536;
      int var12 = var10 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)var11 / 1.0F, (float)var12 / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float var13;
      float var20;
      float var22;
      if(var8 != null) {
         var10 = Item.field_77698_e[var8.field_77993_c].func_82790_a(var8, 0);
         var20 = (float)(var10 >> 16 & 255) / 255.0F;
         var22 = (float)(var10 >> 8 & 255) / 255.0F;
         var13 = (float)(var10 & 255) / 255.0F;
         GL11.glColor4f(var9 * var20, var9 * var22, var9 * var13, 1.0F);
      } else {
         GL11.glColor4f(var9, var9, var9, 1.0F);
      }

      float var14;
      float var15;
      float var16;
      float var21;
      Render var27;
      RenderPlayer var26;
      if(var8 != null && var8.field_77993_c == Item.field_77744_bd.field_77779_bT) {
         GL11.glPushMatrix();
         var21 = 0.8F;
         var20 = var3.func_70678_g(p_78440_1_);
         var22 = MathHelper.func_76126_a(var20 * 3.1415927F);
         var13 = MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F);
         GL11.glTranslatef(-var13 * 0.4F, MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F * 2.0F) * 0.2F, -var22 * 0.2F);
         var20 = 1.0F - var4 / 45.0F + 0.1F;
         if(var20 < 0.0F) {
            var20 = 0.0F;
         }

         if(var20 > 1.0F) {
            var20 = 1.0F;
         }

         var20 = -MathHelper.func_76134_b(var20 * 3.1415927F) * 0.5F + 0.5F;
         GL11.glTranslatef(0.0F, 0.0F * var21 - (1.0F - var2) * 1.2F - var20 * 0.5F + 0.04F, -0.9F * var21);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(var20 * -85.0F, 0.0F, 0.0F, 1.0F);
         GL11.glEnable('\u803a');
         this.field_78455_a.func_110434_K().func_110577_a(var3.func_110306_p());

         for(var12 = 0; var12 < 2; ++var12) {
            int var24 = var12 * 2 - 1;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float)var24);
            GL11.glRotatef((float)(-45 * var24), 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef((float)(-65 * var24), 0.0F, 1.0F, 0.0F);
            var27 = RenderManager.field_78727_a.func_78713_a(this.field_78455_a.field_71439_g);
            var26 = (RenderPlayer)var27;
            var16 = 1.0F;
            GL11.glScalef(var16, var16, var16);
            var26.func_82441_a(this.field_78455_a.field_71439_g);
            GL11.glPopMatrix();
         }

         var22 = var3.func_70678_g(p_78440_1_);
         var13 = MathHelper.func_76126_a(var22 * var22 * 3.1415927F);
         var14 = MathHelper.func_76126_a(MathHelper.func_76129_c(var22) * 3.1415927F);
         GL11.glRotatef(-var13 * 20.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-var14 * 20.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-var14 * 80.0F, 1.0F, 0.0F, 0.0F);
         var15 = 0.38F;
         GL11.glScalef(var15, var15, var15);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
         var16 = 0.015625F;
         GL11.glScalef(var16, var16, var16);
         this.field_78455_a.func_110434_K().func_110577_a(field_110931_c);
         Tessellator var30 = Tessellator.field_78398_a;
         GL11.glNormal3f(0.0F, 0.0F, -1.0F);
         var30.func_78382_b();
         byte var29 = 7;
         var30.func_78374_a((double)(0 - var29), (double)(128 + var29), 0.0D, 0.0D, 1.0D);
         var30.func_78374_a((double)(128 + var29), (double)(128 + var29), 0.0D, 1.0D, 1.0D);
         var30.func_78374_a((double)(128 + var29), (double)(0 - var29), 0.0D, 1.0D, 0.0D);
         var30.func_78374_a((double)(0 - var29), (double)(0 - var29), 0.0D, 0.0D, 0.0D);
         var30.func_78381_a();
         MapData var19 = Item.field_77744_bd.func_77873_a(var8, this.field_78455_a.field_71441_e);
         if(var19 != null) {
            this.field_78449_f.func_78319_a(this.field_78455_a.field_71439_g, this.field_78455_a.func_110434_K(), var19);
         }

         GL11.glPopMatrix();
      } else if(var8 != null) {
         GL11.glPushMatrix();
         var21 = 0.8F;
         if(var3.func_71052_bv() > 0) {
            EnumAction var23 = var8.func_77975_n();
            if(var23 == EnumAction.eat || var23 == EnumAction.drink) {
               var22 = (float)var3.func_71052_bv() - p_78440_1_ + 1.0F;
               var13 = 1.0F - var22 / (float)var8.func_77988_m();
               var14 = 1.0F - var13;
               var14 = var14 * var14 * var14;
               var14 = var14 * var14 * var14;
               var14 = var14 * var14 * var14;
               var15 = 1.0F - var14;
               GL11.glTranslatef(0.0F, MathHelper.func_76135_e(MathHelper.func_76134_b(var22 / 4.0F * 3.1415927F) * 0.1F) * (float)((double)var13 > 0.2D?1:0), 0.0F);
               GL11.glTranslatef(var15 * 0.6F, -var15 * 0.5F, 0.0F);
               GL11.glRotatef(var15 * 90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(var15 * 10.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(var15 * 30.0F, 0.0F, 0.0F, 1.0F);
            }
         } else {
            var20 = var3.func_70678_g(p_78440_1_);
            var22 = MathHelper.func_76126_a(var20 * 3.1415927F);
            var13 = MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F);
            GL11.glTranslatef(-var13 * 0.4F, MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F * 2.0F) * 0.2F, -var22 * 0.2F);
         }

         GL11.glTranslatef(0.7F * var21, -0.65F * var21 - (1.0F - var2) * 0.6F, -0.9F * var21);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glEnable('\u803a');
         var20 = var3.func_70678_g(p_78440_1_);
         var22 = MathHelper.func_76126_a(var20 * var20 * 3.1415927F);
         var13 = MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F);
         GL11.glRotatef(-var22 * 20.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-var13 * 20.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-var13 * 80.0F, 1.0F, 0.0F, 0.0F);
         var14 = 0.4F;
         GL11.glScalef(var14, var14, var14);
         float var17;
         float var18;
         if(var3.func_71052_bv() > 0) {
            EnumAction var25 = var8.func_77975_n();
            if(var25 == EnumAction.block) {
               GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
               GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
            } else if(var25 == EnumAction.bow) {
               GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
               GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
               var16 = (float)var8.func_77988_m() - ((float)var3.func_71052_bv() - p_78440_1_ + 1.0F);
               var17 = var16 / 20.0F;
               var17 = (var17 * var17 + var17 * 2.0F) / 3.0F;
               if(var17 > 1.0F) {
                  var17 = 1.0F;
               }

               if(var17 > 0.1F) {
                  GL11.glTranslatef(0.0F, MathHelper.func_76126_a((var16 - 0.1F) * 1.3F) * 0.01F * (var17 - 0.1F), 0.0F);
               }

               GL11.glTranslatef(0.0F, 0.0F, var17 * 0.1F);
               GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
               GL11.glTranslatef(0.0F, 0.5F, 0.0F);
               var18 = 1.0F + var17 * 0.2F;
               GL11.glScalef(1.0F, 1.0F, var18);
               GL11.glTranslatef(0.0F, -0.5F, 0.0F);
               GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            }
         }

         if(var8.func_77973_b().func_77629_n_()) {
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         }

         if(var8.func_77973_b().func_77623_v()) {
            this.func_78443_a(var3, var8, 0);
            int var28 = Item.field_77698_e[var8.field_77993_c].func_82790_a(var8, 1);
            var16 = (float)(var28 >> 16 & 255) / 255.0F;
            var17 = (float)(var28 >> 8 & 255) / 255.0F;
            var18 = (float)(var28 & 255) / 255.0F;
            GL11.glColor4f(var9 * var16, var9 * var17, var9 * var18, 1.0F);
            this.func_78443_a(var3, var8, 1);
         } else {
            this.func_78443_a(var3, var8, 0);
         }

         GL11.glPopMatrix();
      } else if(!var3.func_82150_aj()) {
         GL11.glPushMatrix();
         var21 = 0.8F;
         var20 = var3.func_70678_g(p_78440_1_);
         var22 = MathHelper.func_76126_a(var20 * 3.1415927F);
         var13 = MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F);
         GL11.glTranslatef(-var13 * 0.3F, MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F * 2.0F) * 0.4F, -var22 * 0.4F);
         GL11.glTranslatef(0.8F * var21, -0.75F * var21 - (1.0F - var2) * 0.6F, -0.9F * var21);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glEnable('\u803a');
         var20 = var3.func_70678_g(p_78440_1_);
         var22 = MathHelper.func_76126_a(var20 * var20 * 3.1415927F);
         var13 = MathHelper.func_76126_a(MathHelper.func_76129_c(var20) * 3.1415927F);
         GL11.glRotatef(var13 * 70.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-var22 * 20.0F, 0.0F, 0.0F, 1.0F);
         this.field_78455_a.func_110434_K().func_110577_a(var3.func_110306_p());
         GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
         GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(1.0F, 1.0F, 1.0F);
         GL11.glTranslatef(5.6F, 0.0F, 0.0F);
         var27 = RenderManager.field_78727_a.func_78713_a(this.field_78455_a.field_71439_g);
         var26 = (RenderPlayer)var27;
         var16 = 1.0F;
         GL11.glScalef(var16, var16, var16);
         var26.func_82441_a(this.field_78455_a.field_71439_g);
         GL11.glPopMatrix();
      }

      GL11.glDisable('\u803a');
      RenderHelper.func_74518_a();
   }

   public void func_78447_b(float p_78447_1_) {
      GL11.glDisable(3008);
      if(this.field_78455_a.field_71439_g.func_70027_ad()) {
         this.func_78442_d(p_78447_1_);
      }

      if(this.field_78455_a.field_71439_g.func_70094_T()) {
         int var2 = MathHelper.func_76128_c(this.field_78455_a.field_71439_g.field_70165_t);
         int var3 = MathHelper.func_76128_c(this.field_78455_a.field_71439_g.field_70163_u);
         int var4 = MathHelper.func_76128_c(this.field_78455_a.field_71439_g.field_70161_v);
         int var5 = this.field_78455_a.field_71441_e.func_72798_a(var2, var3, var4);
         if(this.field_78455_a.field_71441_e.func_72809_s(var2, var3, var4)) {
            this.func_78446_a(p_78447_1_, Block.field_71973_m[var5].func_71851_a(2));
         } else {
            for(int var6 = 0; var6 < 8; ++var6) {
               float var7 = ((float)((var6 >> 0) % 2) - 0.5F) * this.field_78455_a.field_71439_g.field_70130_N * 0.9F;
               float var8 = ((float)((var6 >> 1) % 2) - 0.5F) * this.field_78455_a.field_71439_g.field_70131_O * 0.2F;
               float var9 = ((float)((var6 >> 2) % 2) - 0.5F) * this.field_78455_a.field_71439_g.field_70130_N * 0.9F;
               int var10 = MathHelper.func_76141_d((float)var2 + var7);
               int var11 = MathHelper.func_76141_d((float)var3 + var8);
               int var12 = MathHelper.func_76141_d((float)var4 + var9);
               if(this.field_78455_a.field_71441_e.func_72809_s(var10, var11, var12)) {
                  var5 = this.field_78455_a.field_71441_e.func_72798_a(var10, var11, var12);
               }
            }
         }

         if(Block.field_71973_m[var5] != null) {
            this.func_78446_a(p_78447_1_, Block.field_71973_m[var5].func_71851_a(2));
         }
      }

      if(this.field_78455_a.field_71439_g.func_70055_a(Material.field_76244_g)) {
         this.func_78448_c(p_78447_1_);
      }

      GL11.glEnable(3008);
   }

   private void func_78446_a(float p_78446_1_, Icon p_78446_2_) {
      this.field_78455_a.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      Tessellator var3 = Tessellator.field_78398_a;
      float var4 = 0.1F;
      GL11.glColor4f(var4, var4, var4, 0.5F);
      GL11.glPushMatrix();
      float var5 = -1.0F;
      float var6 = 1.0F;
      float var7 = -1.0F;
      float var8 = 1.0F;
      float var9 = -0.5F;
      float var10 = p_78446_2_.func_94209_e();
      float var11 = p_78446_2_.func_94212_f();
      float var12 = p_78446_2_.func_94206_g();
      float var13 = p_78446_2_.func_94210_h();
      var3.func_78382_b();
      var3.func_78374_a((double)var5, (double)var7, (double)var9, (double)var11, (double)var13);
      var3.func_78374_a((double)var6, (double)var7, (double)var9, (double)var10, (double)var13);
      var3.func_78374_a((double)var6, (double)var8, (double)var9, (double)var10, (double)var12);
      var3.func_78374_a((double)var5, (double)var8, (double)var9, (double)var11, (double)var12);
      var3.func_78381_a();
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void func_78448_c(float p_78448_1_) {
      this.field_78455_a.func_110434_K().func_110577_a(field_110929_d);
      Tessellator var2 = Tessellator.field_78398_a;
      float var3 = this.field_78455_a.field_71439_g.func_70013_c(p_78448_1_);
      GL11.glColor4f(var3, var3, var3, 0.5F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glPushMatrix();
      float var4 = 4.0F;
      float var5 = -1.0F;
      float var6 = 1.0F;
      float var7 = -1.0F;
      float var8 = 1.0F;
      float var9 = -0.5F;
      float var10 = -this.field_78455_a.field_71439_g.field_70177_z / 64.0F;
      float var11 = this.field_78455_a.field_71439_g.field_70125_A / 64.0F;
      var2.func_78382_b();
      var2.func_78374_a((double)var5, (double)var7, (double)var9, (double)(var4 + var10), (double)(var4 + var11));
      var2.func_78374_a((double)var6, (double)var7, (double)var9, (double)(0.0F + var10), (double)(var4 + var11));
      var2.func_78374_a((double)var6, (double)var8, (double)var9, (double)(0.0F + var10), (double)(0.0F + var11));
      var2.func_78374_a((double)var5, (double)var8, (double)var9, (double)(var4 + var10), (double)(0.0F + var11));
      var2.func_78381_a();
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
   }

   private void func_78442_d(float p_78442_1_) {
      Tessellator var2 = Tessellator.field_78398_a;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      float var3 = 1.0F;

      for(int var4 = 0; var4 < 2; ++var4) {
         GL11.glPushMatrix();
         Icon var5 = Block.field_72067_ar.func_94438_c(1);
         this.field_78455_a.func_110434_K().func_110577_a(TextureMap.field_110575_b);
         float var6 = var5.func_94209_e();
         float var7 = var5.func_94212_f();
         float var8 = var5.func_94206_g();
         float var9 = var5.func_94210_h();
         float var10 = (0.0F - var3) / 2.0F;
         float var11 = var10 + var3;
         float var12 = 0.0F - var3 / 2.0F;
         float var13 = var12 + var3;
         float var14 = -0.5F;
         GL11.glTranslatef((float)(-(var4 * 2 - 1)) * 0.24F, -0.3F, 0.0F);
         GL11.glRotatef((float)(var4 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         var2.func_78382_b();
         var2.func_78374_a((double)var10, (double)var12, (double)var14, (double)var7, (double)var9);
         var2.func_78374_a((double)var11, (double)var12, (double)var14, (double)var6, (double)var9);
         var2.func_78374_a((double)var11, (double)var13, (double)var14, (double)var6, (double)var8);
         var2.func_78374_a((double)var10, (double)var13, (double)var14, (double)var7, (double)var8);
         var2.func_78381_a();
         GL11.glPopMatrix();
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
   }

   public void func_78441_a() {
      this.field_78451_d = this.field_78454_c;
      EntityClientPlayerMP var1 = this.field_78455_a.field_71439_g;
      ItemStack var2 = var1.field_71071_by.func_70448_g();
      boolean var3 = this.field_78450_g == var1.field_71071_by.field_70461_c && var2 == this.field_78453_b;
      if(this.field_78453_b == null && var2 == null) {
         var3 = true;
      }

      if(var2 != null && this.field_78453_b != null && var2 != this.field_78453_b && var2.field_77993_c == this.field_78453_b.field_77993_c && var2.func_77960_j() == this.field_78453_b.func_77960_j()) {
         this.field_78453_b = var2;
         var3 = true;
      }

      float var4 = 0.4F;
      float var5 = var3?1.0F:0.0F;
      float var6 = var5 - this.field_78454_c;
      if(var6 < -var4) {
         var6 = -var4;
      }

      if(var6 > var4) {
         var6 = var4;
      }

      this.field_78454_c += var6;
      if(this.field_78454_c < 0.1F) {
         this.field_78453_b = var2;
         this.field_78450_g = var1.field_71071_by.field_70461_c;
      }

   }

   public void func_78444_b() {
      this.field_78454_c = 0.0F;
   }

   public void func_78445_c() {
      this.field_78454_c = 0.0F;
   }

}
