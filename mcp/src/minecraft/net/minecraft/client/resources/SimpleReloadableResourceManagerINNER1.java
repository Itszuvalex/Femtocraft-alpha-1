package net.minecraft.client.resources;

import com.google.common.base.Function;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class SimpleReloadableResourceManagerINNER1 implements Function
{
    final SimpleReloadableResourceManager field_130076_a;

    SimpleReloadableResourceManagerINNER1(SimpleReloadableResourceManager par1SimpleReloadableResourceManager)
    {
        this.field_130076_a = par1SimpleReloadableResourceManager;
    }

    public String func_130075_a(ResourcePack par1ResourcePack)
    {
        return par1ResourcePack.func_130077_b();
    }

    public Object apply(Object par1Obj)
    {
        return this.func_130075_a((ResourcePack)par1Obj);
    }
}
