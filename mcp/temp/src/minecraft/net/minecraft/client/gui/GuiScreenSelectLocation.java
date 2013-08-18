package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class GuiScreenSelectLocation {

   private final Minecraft field_104092_f;
   private int field_104093_g;
   private int field_104105_h;
   protected int field_104098_a;
   protected int field_104096_b;
   private int field_104106_i;
   private int field_104103_j;
   protected final int field_104097_c;
   private int field_104104_k;
   private int field_104101_l;
   protected int field_104094_d;
   protected int field_104095_e;
   private float field_104102_m = -2.0F;
   private float field_104099_n;
   private float field_104100_o;
   private int field_104111_p = -1;
   private long field_104110_q;
   private boolean field_104109_r = true;
   private boolean field_104108_s;
   private int field_104107_t;


   public GuiScreenSelectLocation(Minecraft p_i1116_1_, int p_i1116_2_, int p_i1116_3_, int p_i1116_4_, int p_i1116_5_, int p_i1116_6_) {
      this.field_104092_f = p_i1116_1_;
      this.field_104093_g = p_i1116_2_;
      this.field_104105_h = p_i1116_3_;
      this.field_104098_a = p_i1116_4_;
      this.field_104096_b = p_i1116_5_;
      this.field_104097_c = p_i1116_6_;
      this.field_104103_j = 0;
      this.field_104106_i = p_i1116_2_;
   }

   public void func_104084_a(int p_104084_1_, int p_104084_2_, int p_104084_3_, int p_104084_4_) {
      this.field_104093_g = p_104084_1_;
      this.field_104105_h = p_104084_2_;
      this.field_104098_a = p_104084_3_;
      this.field_104096_b = p_104084_4_;
      this.field_104103_j = 0;
      this.field_104106_i = p_104084_1_;
   }

   protected abstract int func_77217_a();

   protected abstract void func_77213_a(int var1, boolean var2);

   protected abstract boolean func_77218_a(int var1);

   protected abstract boolean func_104086_b(int var1);

   protected int func_130003_b() {
      return this.func_77217_a() * this.field_104097_c + this.field_104107_t;
   }

   protected abstract void func_130004_c();

   protected abstract void func_77214_a(int var1, int var2, int var3, int var4, Tessellator var5);

   protected void func_104088_a(int p_104088_1_, int p_104088_2_, Tessellator p_104088_3_) {}

   protected void func_104089_a(int p_104089_1_, int p_104089_2_) {}

   protected void func_104087_b(int p_104087_1_, int p_104087_2_) {}

   private void func_104091_h() {
      int var1 = this.func_104085_d();
      if(var1 < 0) {
         var1 /= 2;
      }

      if(this.field_104100_o < 0.0F) {
         this.field_104100_o = 0.0F;
      }

      if(this.field_104100_o > (float)var1) {
         this.field_104100_o = (float)var1;
      }

   }

   public int func_104085_d() {
      return this.func_130003_b() - (this.field_104096_b - this.field_104098_a - 4);
   }

   public void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == this.field_104104_k) {
            this.field_104100_o -= (float)(this.field_104097_c * 2 / 3);
            this.field_104102_m = -2.0F;
            this.func_104091_h();
         } else if(p_73875_1_.field_73741_f == this.field_104101_l) {
            this.field_104100_o += (float)(this.field_104097_c * 2 / 3);
            this.field_104102_m = -2.0F;
            this.func_104091_h();
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_104094_d = p_73863_1_;
      this.field_104095_e = p_73863_2_;
      this.func_130004_c();
      int var4 = this.func_77217_a();
      int var5 = this.func_104090_g();
      int var6 = var5 + 6;
      int var9;
      int var10;
      int var11;
      int var13;
      int var19;
      if(Mouse.isButtonDown(0)) {
         if(this.field_104102_m == -1.0F) {
            boolean var7 = true;
            if(p_73863_2_ >= this.field_104098_a && p_73863_2_ <= this.field_104096_b) {
               int var8 = this.field_104093_g / 2 - 110;
               var9 = this.field_104093_g / 2 + 110;
               var10 = p_73863_2_ - this.field_104098_a - this.field_104107_t + (int)this.field_104100_o - 4;
               var11 = var10 / this.field_104097_c;
               if(p_73863_1_ >= var8 && p_73863_1_ <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                  boolean var12 = var11 == this.field_104111_p && Minecraft.func_71386_F() - this.field_104110_q < 250L;
                  this.func_77213_a(var11, var12);
                  this.field_104111_p = var11;
                  this.field_104110_q = Minecraft.func_71386_F();
               } else if(p_73863_1_ >= var8 && p_73863_1_ <= var9 && var10 < 0) {
                  this.func_104089_a(p_73863_1_ - var8, p_73863_2_ - this.field_104098_a + (int)this.field_104100_o - 4);
                  var7 = false;
               }

               if(p_73863_1_ >= var5 && p_73863_1_ <= var6) {
                  this.field_104099_n = -1.0F;
                  var19 = this.func_104085_d();
                  if(var19 < 1) {
                     var19 = 1;
                  }

                  var13 = (int)((float)((this.field_104096_b - this.field_104098_a) * (this.field_104096_b - this.field_104098_a)) / (float)this.func_130003_b());
                  if(var13 < 32) {
                     var13 = 32;
                  }

                  if(var13 > this.field_104096_b - this.field_104098_a - 8) {
                     var13 = this.field_104096_b - this.field_104098_a - 8;
                  }

                  this.field_104099_n /= (float)(this.field_104096_b - this.field_104098_a - var13) / (float)var19;
               } else {
                  this.field_104099_n = 1.0F;
               }

               if(var7) {
                  this.field_104102_m = (float)p_73863_2_;
               } else {
                  this.field_104102_m = -2.0F;
               }
            } else {
               this.field_104102_m = -2.0F;
            }
         } else if(this.field_104102_m >= 0.0F) {
            this.field_104100_o -= ((float)p_73863_2_ - this.field_104102_m) * this.field_104099_n;
            this.field_104102_m = (float)p_73863_2_;
         }
      } else {
         while(!this.field_104092_f.field_71474_y.field_85185_A && Mouse.next()) {
            int var16 = Mouse.getEventDWheel();
            if(var16 != 0) {
               if(var16 > 0) {
                  var16 = -1;
               } else if(var16 < 0) {
                  var16 = 1;
               }

               this.field_104100_o += (float)(var16 * this.field_104097_c / 2);
            }
         }

         this.field_104102_m = -1.0F;
      }

      this.func_104091_h();
      GL11.glDisable(2896);
      GL11.glDisable(2912);
      Tessellator var18 = Tessellator.field_78398_a;
      this.field_104092_f.func_110434_K().func_110577_a(Gui.field_110325_k);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float var17 = 32.0F;
      var18.func_78382_b();
      var18.func_78378_d(2105376);
      var18.func_78374_a((double)this.field_104103_j, (double)this.field_104096_b, 0.0D, (double)((float)this.field_104103_j / var17), (double)((float)(this.field_104096_b + (int)this.field_104100_o) / var17));
      var18.func_78374_a((double)this.field_104106_i, (double)this.field_104096_b, 0.0D, (double)((float)this.field_104106_i / var17), (double)((float)(this.field_104096_b + (int)this.field_104100_o) / var17));
      var18.func_78374_a((double)this.field_104106_i, (double)this.field_104098_a, 0.0D, (double)((float)this.field_104106_i / var17), (double)((float)(this.field_104098_a + (int)this.field_104100_o) / var17));
      var18.func_78374_a((double)this.field_104103_j, (double)this.field_104098_a, 0.0D, (double)((float)this.field_104103_j / var17), (double)((float)(this.field_104098_a + (int)this.field_104100_o) / var17));
      var18.func_78381_a();
      var9 = this.field_104093_g / 2 - 92 - 16;
      var10 = this.field_104098_a + 4 - (int)this.field_104100_o;
      if(this.field_104108_s) {
         this.func_104088_a(var9, var10, var18);
      }

      int var14;
      for(var11 = 0; var11 < var4; ++var11) {
         var19 = var10 + var11 * this.field_104097_c + this.field_104107_t;
         var13 = this.field_104097_c - 4;
         if(var19 <= this.field_104096_b && var19 + var13 >= this.field_104098_a) {
            int var15;
            if(this.field_104109_r && this.func_104086_b(var11)) {
               var14 = this.field_104093_g / 2 - 110;
               var15 = this.field_104093_g / 2 + 110;
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glDisable(3553);
               var18.func_78382_b();
               var18.func_78378_d(0);
               var18.func_78374_a((double)var14, (double)(var19 + var13 + 2), 0.0D, 0.0D, 1.0D);
               var18.func_78374_a((double)var15, (double)(var19 + var13 + 2), 0.0D, 1.0D, 1.0D);
               var18.func_78374_a((double)var15, (double)(var19 - 2), 0.0D, 1.0D, 0.0D);
               var18.func_78374_a((double)var14, (double)(var19 - 2), 0.0D, 0.0D, 0.0D);
               var18.func_78381_a();
               GL11.glEnable(3553);
            }

            if(this.field_104109_r && this.func_77218_a(var11)) {
               var14 = this.field_104093_g / 2 - 110;
               var15 = this.field_104093_g / 2 + 110;
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glDisable(3553);
               var18.func_78382_b();
               var18.func_78378_d(8421504);
               var18.func_78374_a((double)var14, (double)(var19 + var13 + 2), 0.0D, 0.0D, 1.0D);
               var18.func_78374_a((double)var15, (double)(var19 + var13 + 2), 0.0D, 1.0D, 1.0D);
               var18.func_78374_a((double)var15, (double)(var19 - 2), 0.0D, 1.0D, 0.0D);
               var18.func_78374_a((double)var14, (double)(var19 - 2), 0.0D, 0.0D, 0.0D);
               var18.func_78378_d(0);
               var18.func_78374_a((double)(var14 + 1), (double)(var19 + var13 + 1), 0.0D, 0.0D, 1.0D);
               var18.func_78374_a((double)(var15 - 1), (double)(var19 + var13 + 1), 0.0D, 1.0D, 1.0D);
               var18.func_78374_a((double)(var15 - 1), (double)(var19 - 1), 0.0D, 1.0D, 0.0D);
               var18.func_78374_a((double)(var14 + 1), (double)(var19 - 1), 0.0D, 0.0D, 0.0D);
               var18.func_78381_a();
               GL11.glEnable(3553);
            }

            this.func_77214_a(var11, var9, var19, var13, var18);
         }
      }

      GL11.glDisable(2929);
      byte var20 = 4;
      this.func_104083_b(0, this.field_104098_a, 255, 255);
      this.func_104083_b(this.field_104096_b, this.field_104105_h, 255, 255);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3008);
      GL11.glShadeModel(7425);
      GL11.glDisable(3553);
      var18.func_78382_b();
      var18.func_78384_a(0, 0);
      var18.func_78374_a((double)this.field_104103_j, (double)(this.field_104098_a + var20), 0.0D, 0.0D, 1.0D);
      var18.func_78374_a((double)this.field_104106_i, (double)(this.field_104098_a + var20), 0.0D, 1.0D, 1.0D);
      var18.func_78384_a(0, 255);
      var18.func_78374_a((double)this.field_104106_i, (double)this.field_104098_a, 0.0D, 1.0D, 0.0D);
      var18.func_78374_a((double)this.field_104103_j, (double)this.field_104098_a, 0.0D, 0.0D, 0.0D);
      var18.func_78381_a();
      var18.func_78382_b();
      var18.func_78384_a(0, 255);
      var18.func_78374_a((double)this.field_104103_j, (double)this.field_104096_b, 0.0D, 0.0D, 1.0D);
      var18.func_78374_a((double)this.field_104106_i, (double)this.field_104096_b, 0.0D, 1.0D, 1.0D);
      var18.func_78384_a(0, 0);
      var18.func_78374_a((double)this.field_104106_i, (double)(this.field_104096_b - var20), 0.0D, 1.0D, 0.0D);
      var18.func_78374_a((double)this.field_104103_j, (double)(this.field_104096_b - var20), 0.0D, 0.0D, 0.0D);
      var18.func_78381_a();
      var19 = this.func_104085_d();
      if(var19 > 0) {
         var13 = (this.field_104096_b - this.field_104098_a) * (this.field_104096_b - this.field_104098_a) / this.func_130003_b();
         if(var13 < 32) {
            var13 = 32;
         }

         if(var13 > this.field_104096_b - this.field_104098_a - 8) {
            var13 = this.field_104096_b - this.field_104098_a - 8;
         }

         var14 = (int)this.field_104100_o * (this.field_104096_b - this.field_104098_a - var13) / var19 + this.field_104098_a;
         if(var14 < this.field_104098_a) {
            var14 = this.field_104098_a;
         }

         var18.func_78382_b();
         var18.func_78384_a(0, 255);
         var18.func_78374_a((double)var5, (double)this.field_104096_b, 0.0D, 0.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)this.field_104096_b, 0.0D, 1.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)this.field_104098_a, 0.0D, 1.0D, 0.0D);
         var18.func_78374_a((double)var5, (double)this.field_104098_a, 0.0D, 0.0D, 0.0D);
         var18.func_78381_a();
         var18.func_78382_b();
         var18.func_78384_a(8421504, 255);
         var18.func_78374_a((double)var5, (double)(var14 + var13), 0.0D, 0.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)(var14 + var13), 0.0D, 1.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)var14, 0.0D, 1.0D, 0.0D);
         var18.func_78374_a((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
         var18.func_78381_a();
         var18.func_78382_b();
         var18.func_78384_a(12632256, 255);
         var18.func_78374_a((double)var5, (double)(var14 + var13 - 1), 0.0D, 0.0D, 1.0D);
         var18.func_78374_a((double)(var6 - 1), (double)(var14 + var13 - 1), 0.0D, 1.0D, 1.0D);
         var18.func_78374_a((double)(var6 - 1), (double)var14, 0.0D, 1.0D, 0.0D);
         var18.func_78374_a((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
         var18.func_78381_a();
      }

      this.func_104087_b(p_73863_1_, p_73863_2_);
      GL11.glEnable(3553);
      GL11.glShadeModel(7424);
      GL11.glEnable(3008);
      GL11.glDisable(3042);
   }

   protected int func_104090_g() {
      return this.field_104093_g / 2 + 124;
   }

   private void func_104083_b(int p_104083_1_, int p_104083_2_, int p_104083_3_, int p_104083_4_) {
      Tessellator var5 = Tessellator.field_78398_a;
      this.field_104092_f.func_110434_K().func_110577_a(Gui.field_110325_k);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float var6 = 32.0F;
      var5.func_78382_b();
      var5.func_78384_a(4210752, p_104083_4_);
      var5.func_78374_a(0.0D, (double)p_104083_2_, 0.0D, 0.0D, (double)((float)p_104083_2_ / var6));
      var5.func_78374_a((double)this.field_104093_g, (double)p_104083_2_, 0.0D, (double)((float)this.field_104093_g / var6), (double)((float)p_104083_2_ / var6));
      var5.func_78384_a(4210752, p_104083_3_);
      var5.func_78374_a((double)this.field_104093_g, (double)p_104083_1_, 0.0D, (double)((float)this.field_104093_g / var6), (double)((float)p_104083_1_ / var6));
      var5.func_78374_a(0.0D, (double)p_104083_1_, 0.0D, 0.0D, (double)((float)p_104083_1_ / var6));
      var5.func_78381_a();
   }
}
