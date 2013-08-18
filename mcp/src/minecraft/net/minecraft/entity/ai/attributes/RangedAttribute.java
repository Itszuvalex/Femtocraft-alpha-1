package net.minecraft.entity.ai.attributes;

public class RangedAttribute extends BaseAttribute
{
    private final double field_111120_a;
    private final double field_111118_b;
    private String field_111119_c;

    public RangedAttribute(String par1Str, double par2, double par4, double par6)
    {
        super(par1Str, par2);
        this.field_111120_a = par4;
        this.field_111118_b = par6;

        if (par4 > par6)
        {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        else if (par2 < par4)
        {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        else if (par2 > par6)
        {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    public RangedAttribute func_111117_a(String par1Str)
    {
        this.field_111119_c = par1Str;
        return this;
    }

    public String func_111116_f()
    {
        return this.field_111119_c;
    }

    public double func_111109_a(double par1)
    {
        if (par1 < this.field_111120_a)
        {
            par1 = this.field_111120_a;
        }

        if (par1 > this.field_111118_b)
        {
            par1 = this.field_111118_b;
        }

        return par1;
    }
}
