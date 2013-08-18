package net.minecraft.client.resources.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;

@SideOnly(Side.CLIENT)
public class LanguageMetadataSection implements MetadataSection
{
    private final Collection field_135019_a;

    public LanguageMetadataSection(Collection par1Collection)
    {
        this.field_135019_a = par1Collection;
    }

    public Collection func_135018_a()
    {
        return this.field_135019_a;
    }
}
