package net.minecraft.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class SimpleTexture extends AbstractTexture {

   private final ResourceLocation field_110568_b;


   public SimpleTexture(ResourceLocation p_i1275_1_) {
      this.field_110568_b = p_i1275_1_;
   }

   public void func_110551_a(ResourceManager p_110551_1_) throws IOException {
      InputStream var2 = null;

      try {
         Resource var3 = p_110551_1_.func_110536_a(this.field_110568_b);
         var2 = var3.func_110527_b();
         BufferedImage var4 = ImageIO.read(var2);
         boolean var5 = false;
         boolean var6 = false;
         if(var3.func_110528_c()) {
            try {
               TextureMetadataSection var7 = (TextureMetadataSection)var3.func_110526_a("texture");
               if(var7 != null) {
                  var5 = var7.func_110479_a();
                  var6 = var7.func_110480_b();
               }
            } catch (RuntimeException var11) {
               Minecraft.func_71410_x().func_98033_al().func_98235_b("Failed reading metadata of: " + this.field_110568_b, var11);
            }
         }

         TextureUtil.func_110989_a(this.func_110552_b(), var4, var5, var6);
      } finally {
         if(var2 != null) {
            var2.close();
         }

      }

   }
}
