package net.minecraft.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.minecraft.client.resources.ResourceManager;

@SideOnly(Side.CLIENT)
public interface TextureObject {

   void func_110551_a(ResourceManager var1) throws IOException;

   int func_110552_b();
}
