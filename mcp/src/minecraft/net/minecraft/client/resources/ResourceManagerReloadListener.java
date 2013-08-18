package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ResourceManagerReloadListener
{
    void func_110549_a(ResourceManager resourcemanager);
}
