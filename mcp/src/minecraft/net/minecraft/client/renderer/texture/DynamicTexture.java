package net.minecraft.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.resources.ResourceManager;

@SideOnly(Side.CLIENT)
public class DynamicTexture extends AbstractTexture
{
    private final int[] field_110566_b;

    /** width of this icon in pixels */
    private final int width;

    /** height of this icon in pixels */
    private final int height;

    public DynamicTexture(BufferedImage par1BufferedImage)
    {
        this(par1BufferedImage.getWidth(), par1BufferedImage.getHeight());
        par1BufferedImage.getRGB(0, 0, par1BufferedImage.getWidth(), par1BufferedImage.getHeight(), this.field_110566_b, 0, par1BufferedImage.getWidth());
        this.func_110564_a();
    }

    public DynamicTexture(int par1, int par2)
    {
        this.width = par1;
        this.height = par2;
        this.field_110566_b = new int[par1 * par2];
        TextureUtil.func_110991_a(this.func_110552_b(), par1, par2);
    }

    public void func_110551_a(ResourceManager par1ResourceManager) throws IOException {}

    public void func_110564_a()
    {
        TextureUtil.func_110988_a(this.func_110552_b(), this.field_110566_b, this.width, this.height);
    }

    public int[] func_110565_c()
    {
        return this.field_110566_b;
    }
}
