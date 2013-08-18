package net.minecraft.entity.ai.attributes;

import java.util.UUID;
import org.apache.commons.lang3.Validate;

public class AttributeModifier
{
    private final double field_111174_a;
    private final int field_111172_b;
    private final String field_111173_c;
    private final UUID field_111170_d;
    private boolean field_111171_e;

    public AttributeModifier(String par1Str, double par2, int par4)
    {
        this(UUID.randomUUID(), par1Str, par2, par4);
    }

    public AttributeModifier(UUID par1UUID, String par2Str, double par3, int par5)
    {
        this.field_111171_e = true;
        this.field_111170_d = par1UUID;
        this.field_111173_c = par2Str;
        this.field_111174_a = par3;
        this.field_111172_b = par5;
        Validate.notEmpty(par2Str, "Modifier name cannot be empty", new Object[0]);
        Validate.inclusiveBetween(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(par5), "Invalid operation", new Object[0]);
    }

    public UUID func_111167_a()
    {
        return this.field_111170_d;
    }

    public String func_111166_b()
    {
        return this.field_111173_c;
    }

    public int func_111169_c()
    {
        return this.field_111172_b;
    }

    public double func_111164_d()
    {
        return this.field_111174_a;
    }

    public boolean func_111165_e()
    {
        return this.field_111171_e;
    }

    public AttributeModifier func_111168_a(boolean par1)
    {
        this.field_111171_e = par1;
        return this;
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }
        else if (par1Obj != null && this.getClass() == par1Obj.getClass())
        {
            AttributeModifier attributemodifier = (AttributeModifier)par1Obj;

            if (this.field_111170_d != null)
            {
                if (!this.field_111170_d.equals(attributemodifier.field_111170_d))
                {
                    return false;
                }
            }
            else if (attributemodifier.field_111170_d != null)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.field_111170_d != null ? this.field_111170_d.hashCode() : 0;
    }

    public String toString()
    {
        return "AttributeModifier{amount=" + this.field_111174_a + ", operation=" + this.field_111172_b + ", name=\'" + this.field_111173_c + '\'' + ", id=" + this.field_111170_d + ", serialize=" + this.field_111171_e + '}';
    }
}
