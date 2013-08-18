package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface ResourceManager
{
    Set func_135055_a();

    Resource func_110536_a(ResourceLocation resourcelocation) throws IOException;

    List func_135056_b(ResourceLocation resourcelocation) throws IOException;
}
