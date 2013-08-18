package net.minecraft.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.renderer.texture.Tickable;

@SideOnly(Side.CLIENT)
public interface TickableTextureObject extends TextureObject, Tickable {
}
