package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.InputStream;
import net.minecraft.client.resources.data.MetadataSection;

@SideOnly(Side.CLIENT)
public interface Resource {

   InputStream func_110527_b();

   boolean func_110528_c();

   MetadataSection func_110526_a(String var1);
}
