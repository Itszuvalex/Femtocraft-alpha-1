package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureMetadataSection implements MetadataSection
{
    private final boolean field_110482_a;
    private final boolean field_110481_b;

    public TextureMetadataSection(boolean par1, boolean par2)
    {
        this.field_110482_a = par1;
        this.field_110481_b = par2;
    }

    public boolean func_110479_a()
    {
        return this.field_110482_a;
    }

    public boolean func_110480_b()
    {
        return this.field_110481_b;
    }
}
