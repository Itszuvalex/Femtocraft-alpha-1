package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class AnimationMetadataSection implements MetadataSection
{
    private final List field_110478_a;
    private final int field_110476_b;
    private final int field_110477_c;
    private final int field_110475_d;

    public AnimationMetadataSection(List par1List, int par2, int par3, int par4)
    {
        this.field_110478_a = par1List;
        this.field_110476_b = par2;
        this.field_110477_c = par3;
        this.field_110475_d = par4;
    }

    public int func_110471_a()
    {
        return this.field_110477_c;
    }

    public int func_110474_b()
    {
        return this.field_110476_b;
    }

    public int func_110473_c()
    {
        return this.field_110478_a.size();
    }

    public int func_110469_d()
    {
        return this.field_110475_d;
    }

    private AnimationFrame func_130072_d(int par1)
    {
        return (AnimationFrame)this.field_110478_a.get(par1);
    }

    public int func_110472_a(int par1)
    {
        AnimationFrame animationframe = this.func_130072_d(par1);
        return animationframe.func_110495_a() ? this.field_110475_d : animationframe.func_110497_b();
    }

    public boolean func_110470_b(int par1)
    {
        return !((AnimationFrame)this.field_110478_a.get(par1)).func_110495_a();
    }

    public int func_110468_c(int par1)
    {
        return ((AnimationFrame)this.field_110478_a.get(par1)).func_110496_c();
    }

    public Set func_130073_e()
    {
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = this.field_110478_a.iterator();

        while (iterator.hasNext())
        {
            AnimationFrame animationframe = (AnimationFrame)iterator.next();
            hashset.add(Integer.valueOf(animationframe.func_110496_c()));
        }

        return hashset;
    }
}
