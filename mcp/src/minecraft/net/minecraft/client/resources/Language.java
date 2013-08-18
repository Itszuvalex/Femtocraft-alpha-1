package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Language implements Comparable
{
    private final String field_135039_a;
    private final String field_135037_b;
    private final String field_135038_c;
    private final boolean field_135036_d;

    public Language(String par1Str, String par2Str, String par3Str, boolean par4)
    {
        this.field_135039_a = par1Str;
        this.field_135037_b = par2Str;
        this.field_135038_c = par3Str;
        this.field_135036_d = par4;
    }

    public String func_135034_a()
    {
        return this.field_135039_a;
    }

    public boolean func_135035_b()
    {
        return this.field_135036_d;
    }

    public String toString()
    {
        return String.format("%s (%s)", new Object[] {this.field_135038_c, this.field_135037_b});
    }

    public boolean equals(Object par1Obj)
    {
        return this == par1Obj ? true : (!(par1Obj instanceof Language) ? false : this.field_135039_a.equals(((Language)par1Obj).field_135039_a));
    }

    public int hashCode()
    {
        return this.field_135039_a.hashCode();
    }

    public int func_135033_a(Language par1Language)
    {
        return this.field_135039_a.compareTo(par1Language.field_135039_a);
    }

    public int compareTo(Object par1Obj)
    {
        return this.func_135033_a((Language)par1Obj);
    }
}
