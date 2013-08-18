package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;

@SideOnly(Side.CLIENT)
public class GrassColorReloadListener implements ResourceManagerReloadListener {

   private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");


   public void func_110549_a(ResourceManager p_110549_1_) {
      try {
         ColorizerGrass.func_77479_a(TextureUtil.func_110986_a(p_110549_1_, field_130078_a));
      } catch (IOException var3) {
         ;
      }

   }

}
