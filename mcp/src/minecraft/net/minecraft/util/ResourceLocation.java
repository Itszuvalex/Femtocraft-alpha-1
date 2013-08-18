package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class ResourceLocation
{
    private final String field_110626_a;
    private final String field_110625_b;

    public ResourceLocation(String par1Str, String par2Str)
    {
        Validate.notNull(par2Str);

        if (par1Str != null && par1Str.length() != 0)
        {
            this.field_110626_a = par1Str;
        }
        else
        {
            this.field_110626_a = "minecraft";
        }

        this.field_110625_b = par2Str;
    }

    public ResourceLocation(String par1Str)
    {
        String s1 = "minecraft";
        String s2 = par1Str;
        int i = par1Str.indexOf(58);

        if (i >= 0)
        {
            s2 = par1Str.substring(i + 1, par1Str.length());

            if (i > 1)
            {
                s1 = par1Str.substring(0, i);
            }
        }

        this.field_110626_a = s1.toLowerCase();
        this.field_110625_b = s2;
    }

    public String func_110623_a()
    {
        return this.field_110625_b;
    }

    public String func_110624_b()
    {
        return this.field_110626_a;
    }

    public String toString()
    {
        return this.field_110626_a + ":" + this.field_110625_b;
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }
        else if (!(par1Obj instanceof ResourceLocation))
        {
            return false;
        }
        else
        {
            ResourceLocation resourcelocation = (ResourceLocation)par1Obj;
            return this.field_110626_a.equals(resourcelocation.field_110626_a) && this.field_110625_b.equals(resourcelocation.field_110625_b);
        }
    }

    public int hashCode()
    {
        return 31 * this.field_110626_a.hashCode() + this.field_110625_b.hashCode();
    }
}
