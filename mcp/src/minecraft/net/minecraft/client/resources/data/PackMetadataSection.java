package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PackMetadataSection implements MetadataSection
{
    private final String field_110464_a;
    private final int field_110463_b;

    public PackMetadataSection(String par1Str, int par2)
    {
        this.field_110464_a = par1Str;
        this.field_110463_b = par2;
    }

    public String func_110461_a()
    {
        return this.field_110464_a;
    }

    public int func_110462_b()
    {
        return this.field_110463_b;
    }
}
