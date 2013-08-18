package net.minecraft.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageDataINNER1;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ThreadDownloadImageData extends AbstractTexture {

   private final String field_110562_b;
   private final IImageBuffer field_110563_c;
   private BufferedImage field_110560_d;
   private Thread field_110561_e;
   private SimpleTexture field_110558_f;
   private boolean field_110559_g;


   public ThreadDownloadImageData(String p_i1273_1_, ResourceLocation p_i1273_2_, IImageBuffer p_i1273_3_) {
      this.field_110562_b = p_i1273_1_;
      this.field_110563_c = p_i1273_3_;
      this.field_110558_f = p_i1273_2_ != null?new SimpleTexture(p_i1273_2_):null;
   }

   public int func_110552_b() {
      int var1 = super.func_110552_b();
      if(!this.field_110559_g && this.field_110560_d != null) {
         TextureUtil.func_110987_a(var1, this.field_110560_d);
         this.field_110559_g = true;
      }

      return var1;
   }

   public void func_110556_a(BufferedImage p_110556_1_) {
      this.field_110560_d = p_110556_1_;
   }

   public void func_110551_a(ResourceManager p_110551_1_) throws IOException {
      if(this.field_110560_d == null) {
         if(this.field_110558_f != null) {
            this.field_110558_f.func_110551_a(p_110551_1_);
            this.field_110553_a = this.field_110558_f.func_110552_b();
         }
      } else {
         TextureUtil.func_110987_a(this.func_110552_b(), this.field_110560_d);
      }

      if(this.field_110561_e == null) {
         this.field_110561_e = new ThreadDownloadImageDataINNER1(this);
         this.field_110561_e.setDaemon(true);
         this.field_110561_e.setName("Skin downloader: " + this.field_110562_b);
         this.field_110561_e.start();
      }

   }

   public boolean func_110557_a() {
      this.func_110552_b();
      return this.field_110559_g;
   }

   // $FF: synthetic method
   static String func_110554_a(ThreadDownloadImageData p_110554_0_) {
      return p_110554_0_.field_110562_b;
   }

   // $FF: synthetic method
   static IImageBuffer func_110555_b(ThreadDownloadImageData p_110555_0_) {
      return p_110555_0_.field_110563_c;
   }
}
