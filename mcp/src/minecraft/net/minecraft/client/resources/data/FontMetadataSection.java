package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FontMetadataSection implements MetadataSection
{
    private final float[] field_110467_a;
    private final float[] field_110465_b;
    private final float[] field_110466_c;

    public FontMetadataSection(float[] par1ArrayOfFloat, float[] par2ArrayOfFloat, float[] par3ArrayOfFloat)
    {
        this.field_110467_a = par1ArrayOfFloat;
        this.field_110465_b = par2ArrayOfFloat;
        this.field_110466_c = par3ArrayOfFloat;
    }
}
