package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapCoord;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MapItemRenderer {

   private static final ResourceLocation field_111277_a = new ResourceLocation("textures/map/map_icons.png");
   private final DynamicTexture field_78321_b;
   private int[] field_78323_a = new int[16384];
   private GameSettings field_78322_c;
   private final ResourceLocation field_111276_e;


   public MapItemRenderer(GameSettings p_i1045_1_, TextureManager p_i1045_2_) {
      this.field_78322_c = p_i1045_1_;
      this.field_78321_b = new DynamicTexture(128, 128);
      this.field_111276_e = p_i1045_2_.func_110578_a("map", this.field_78321_b);
      this.field_78323_a = this.field_78321_b.func_110565_c();

      for(int var4 = 0; var4 < this.field_78323_a.length; ++var4) {
         this.field_78323_a[var4] = 0;
      }

   }

   public void func_78319_a(EntityPlayer p_78319_1_, TextureManager p_78319_2_, MapData p_78319_3_) {
      for(int var4 = 0; var4 < 16384; ++var4) {
         byte var5 = p_78319_3_.field_76198_e[var4];
         if(var5 / 4 == 0) {
            this.field_78323_a[var4] = (var4 + var4 / 128 & 1) * 8 + 16 << 24;
         } else {
            int var6 = MapColor.field_76281_a[var5 / 4].field_76291_p;
            int var7 = var5 & 3;
            short var8 = 220;
            if(var7 == 2) {
               var8 = 255;
            }

            if(var7 == 0) {
               var8 = 180;
            }

            int var9 = (var6 >> 16 & 255) * var8 / 255;
            int var10 = (var6 >> 8 & 255) * var8 / 255;
            int var11 = (var6 & 255) * var8 / 255;
            this.field_78323_a[var4] = -16777216 | var9 << 16 | var10 << 8 | var11;
         }
      }

      this.field_78321_b.func_110564_a();
      byte var15 = 0;
      byte var16 = 0;
      Tessellator var17 = Tessellator.field_78398_a;
      float var18 = 0.0F;
      p_78319_2_.func_110577_a(this.field_111276_e);
      GL11.glEnable(3042);
      GL11.glBlendFunc(1, 771);
      GL11.glDisable(3008);
      var17.func_78382_b();
      var17.func_78374_a((double)((float)(var15 + 0) + var18), (double)((float)(var16 + 128) - var18), -0.009999999776482582D, 0.0D, 1.0D);
      var17.func_78374_a((double)((float)(var15 + 128) - var18), (double)((float)(var16 + 128) - var18), -0.009999999776482582D, 1.0D, 1.0D);
      var17.func_78374_a((double)((float)(var15 + 128) - var18), (double)((float)(var16 + 0) + var18), -0.009999999776482582D, 1.0D, 0.0D);
      var17.func_78374_a((double)((float)(var15 + 0) + var18), (double)((float)(var16 + 0) + var18), -0.009999999776482582D, 0.0D, 0.0D);
      var17.func_78381_a();
      GL11.glEnable(3008);
      GL11.glDisable(3042);
      p_78319_2_.func_110577_a(field_111277_a);
      int var20 = 0;

      for(Iterator var19 = p_78319_3_.field_76203_h.values().iterator(); var19.hasNext(); ++var20) {
         MapCoord var22 = (MapCoord)var19.next();
         GL11.glPushMatrix();
         GL11.glTranslatef((float)var15 + (float)var22.field_76214_b / 2.0F + 64.0F, (float)var16 + (float)var22.field_76215_c / 2.0F + 64.0F, -0.02F);
         GL11.glRotatef((float)(var22.field_76212_d * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
         GL11.glScalef(4.0F, 4.0F, 3.0F);
         GL11.glTranslatef(-0.125F, 0.125F, 0.0F);
         float var21 = (float)(var22.field_76216_a % 4 + 0) / 4.0F;
         float var12 = (float)(var22.field_76216_a / 4 + 0) / 4.0F;
         float var13 = (float)(var22.field_76216_a % 4 + 1) / 4.0F;
         float var14 = (float)(var22.field_76216_a / 4 + 1) / 4.0F;
         var17.func_78382_b();
         var17.func_78374_a(-1.0D, 1.0D, (double)((float)var20 * 0.001F), (double)var21, (double)var12);
         var17.func_78374_a(1.0D, 1.0D, (double)((float)var20 * 0.001F), (double)var13, (double)var12);
         var17.func_78374_a(1.0D, -1.0D, (double)((float)var20 * 0.001F), (double)var13, (double)var14);
         var17.func_78374_a(-1.0D, -1.0D, (double)((float)var20 * 0.001F), (double)var21, (double)var14);
         var17.func_78381_a();
         GL11.glPopMatrix();
      }

      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, -0.04F);
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

}
