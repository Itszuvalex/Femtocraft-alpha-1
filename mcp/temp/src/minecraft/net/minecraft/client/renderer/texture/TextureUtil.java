package net.minecraft.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TextureUtil {

   private static final IntBuffer field_111000_c = GLAllocation.func_74527_f(4194304);
   public static final DynamicTexture field_111001_a = new DynamicTexture(16, 16);
   public static final int[] field_110999_b = field_111001_a.func_110565_c();


   public static int func_110996_a() {
      return GL11.glGenTextures();
   }

   public static int func_110987_a(int p_110987_0_, BufferedImage p_110987_1_) {
      return func_110989_a(p_110987_0_, p_110987_1_, false, false);
   }

   public static void func_110988_a(int p_110988_0_, int[] p_110988_1_, int p_110988_2_, int p_110988_3_) {
      func_94277_a(p_110988_0_);
      func_110998_a(p_110988_1_, p_110988_2_, p_110988_3_, 0, 0, false, false);
   }

   public static void func_110998_a(int[] p_110998_0_, int p_110998_1_, int p_110998_2_, int p_110998_3_, int p_110998_4_, boolean p_110998_5_, boolean p_110998_6_) {
      int var7 = 4194304 / p_110998_1_;
      func_110992_b(p_110998_5_);
      func_110997_a(p_110998_6_);

      int var10;
      for(int var8 = 0; var8 < p_110998_1_ * p_110998_2_; var8 += p_110998_1_ * var10) {
         int var9 = var8 / p_110998_1_;
         var10 = Math.min(var7, p_110998_2_ - var9);
         int var11 = p_110998_1_ * var10;
         func_110994_a(p_110998_0_, var8, var11);
         GL11.glTexSubImage2D(3553, 0, p_110998_3_, p_110998_4_ + var9, p_110998_1_, var10, '\u80e1', '\u8367', field_111000_c);
      }

   }

   public static int func_110989_a(int p_110989_0_, BufferedImage p_110989_1_, boolean p_110989_2_, boolean p_110989_3_) {
      func_110991_a(p_110989_0_, p_110989_1_.getWidth(), p_110989_1_.getHeight());
      return func_110995_a(p_110989_0_, p_110989_1_, 0, 0, p_110989_2_, p_110989_3_);
   }

   public static void func_110991_a(int p_110991_0_, int p_110991_1_, int p_110991_2_) {
      func_94277_a(p_110991_0_);
      GL11.glTexImage2D(3553, 0, 6408, p_110991_1_, p_110991_2_, 0, '\u80e1', '\u8367', (IntBuffer)null);
   }

   public static int func_110995_a(int p_110995_0_, BufferedImage p_110995_1_, int p_110995_2_, int p_110995_3_, boolean p_110995_4_, boolean p_110995_5_) {
      func_94277_a(p_110995_0_);
      func_110993_a(p_110995_1_, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
      return p_110995_0_;
   }

   private static void func_110993_a(BufferedImage p_110993_0_, int p_110993_1_, int p_110993_2_, boolean p_110993_3_, boolean p_110993_4_) {
      int var5 = p_110993_0_.getWidth();
      int var6 = p_110993_0_.getHeight();
      int var7 = 4194304 / var5;
      int[] var8 = new int[var7 * var5];
      func_110992_b(p_110993_3_);
      func_110997_a(p_110993_4_);

      for(int var9 = 0; var9 < var5 * var6; var9 += var5 * var7) {
         int var10 = var9 / var5;
         int var11 = Math.min(var7, var6 - var10);
         int var12 = var5 * var11;
         p_110993_0_.getRGB(0, var10, var5, var11, var8, 0, var5);
         func_110990_a(var8, var12);
         GL11.glTexSubImage2D(3553, 0, p_110993_1_, p_110993_2_ + var10, var5, var11, '\u80e1', '\u8367', field_111000_c);
      }

   }

   private static void func_110997_a(boolean p_110997_0_) {
      if(p_110997_0_) {
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
      } else {
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10243, 10497);
      }

   }

   private static void func_110992_b(boolean p_110992_0_) {
      if(p_110992_0_) {
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
      } else {
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
      }

   }

   private static void func_110990_a(int[] p_110990_0_, int p_110990_1_) {
      func_110994_a(p_110990_0_, 0, p_110990_1_);
   }

   private static void func_110994_a(int[] p_110994_0_, int p_110994_1_, int p_110994_2_) {
      int[] var3 = p_110994_0_;
      if(Minecraft.func_71410_x().field_71474_y.field_74337_g) {
         var3 = func_110985_a(p_110994_0_);
      }

      field_111000_c.clear();
      field_111000_c.put(var3, p_110994_1_, p_110994_2_);
      field_111000_c.position(0).limit(p_110994_2_);
   }

   static void func_94277_a(int p_94277_0_) {
      GL11.glBindTexture(3553, p_94277_0_);
   }

   public static int[] func_110986_a(ResourceManager p_110986_0_, ResourceLocation p_110986_1_) throws IOException {
      BufferedImage var2 = ImageIO.read(p_110986_0_.func_110536_a(p_110986_1_).func_110527_b());
      int var3 = var2.getWidth();
      int var4 = var2.getHeight();
      int[] var5 = new int[var3 * var4];
      var2.getRGB(0, 0, var3, var4, var5, 0, var3);
      return var5;
   }

   public static int[] func_110985_a(int[] p_110985_0_) {
      int[] var1 = new int[p_110985_0_.length];

      for(int var2 = 0; var2 < p_110985_0_.length; ++var2) {
         int var3 = p_110985_0_[var2] >> 24 & 255;
         int var4 = p_110985_0_[var2] >> 16 & 255;
         int var5 = p_110985_0_[var2] >> 8 & 255;
         int var6 = p_110985_0_[var2] & 255;
         int var7 = (var4 * 30 + var5 * 59 + var6 * 11) / 100;
         int var8 = (var4 * 30 + var5 * 70) / 100;
         int var9 = (var4 * 30 + var6 * 70) / 100;
         var1[var2] = var3 << 24 | var7 << 16 | var8 << 8 | var9;
      }

      return var1;
   }

   static {
      int var0 = -16777216;
      int var1 = -524040;
      int[] var2 = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
      int[] var3 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
      int var4 = var2.length;

      for(int var5 = 0; var5 < 16; ++var5) {
         System.arraycopy(var5 < var4?var2:var3, 0, field_110999_b, 16 * var5, var4);
         System.arraycopy(var5 < var4?var3:var2, 0, field_110999_b, 16 * var5 + var4, var4);
      }

      field_111001_a.func_110564_a();
   }
}
