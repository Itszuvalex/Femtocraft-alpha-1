package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

@SideOnly(Side.CLIENT)
public interface ReloadableResourceManager extends ResourceManager
{
    void func_110541_a(List list);

    void func_110542_a(ResourceManagerReloadListener resourcemanagerreloadlistener);
}
