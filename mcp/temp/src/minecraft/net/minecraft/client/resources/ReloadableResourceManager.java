package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;

@SideOnly(Side.CLIENT)
public interface ReloadableResourceManager extends ResourceManager {

   void func_110541_a(List var1);

   void func_110542_a(ResourceManagerReloadListener var1);
}
