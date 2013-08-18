package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface ResourcePack {

   InputStream func_110590_a(ResourceLocation var1) throws IOException;

   boolean func_110589_b(ResourceLocation var1);

   Set func_110587_b();

   MetadataSection func_135058_a(MetadataSerializer var1, String var2) throws IOException;

   BufferedImage func_110586_a() throws IOException;

   String func_130077_b();
}
