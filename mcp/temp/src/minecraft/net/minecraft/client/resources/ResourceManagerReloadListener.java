package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.ResourceManager;

@SideOnly(Side.CLIENT)
public interface ResourceManagerReloadListener {

   void func_110549_a(ResourceManager var1);
}
