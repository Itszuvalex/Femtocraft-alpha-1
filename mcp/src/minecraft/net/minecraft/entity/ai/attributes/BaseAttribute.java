package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements Attribute
{
    private final String field_111115_a;
    private final double field_111113_b;
    private boolean field_111114_c;

    protected BaseAttribute(String par1Str, double par2)
    {
        this.field_111115_a = par1Str;
        this.field_111113_b = par2;

        if (par1Str == null)
        {
            throw new IllegalArgumentException("Name cannot be null!");
        }
    }

    public String func_111108_a()
    {
        return this.field_111115_a;
    }

    public double func_111110_b()
    {
        return this.field_111113_b;
    }

    public boolean func_111111_c()
    {
        return this.field_111114_c;
    }

    public BaseAttribute func_111112_a(boolean par1)
    {
        this.field_111114_c = par1;
        return this;
    }

    public int hashCode()
    {
        return this.field_111115_a.hashCode();
    }
}
