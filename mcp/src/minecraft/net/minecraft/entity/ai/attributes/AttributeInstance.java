package net.minecraft.entity.ai.attributes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;
import java.util.UUID;

public interface AttributeInstance
{
    Attribute func_111123_a();

    double func_111125_b();

    void func_111128_a(double d0);

    Collection func_111122_c();

    AttributeModifier func_111127_a(UUID uuid);

    void func_111121_a(AttributeModifier attributemodifier);

    void func_111124_b(AttributeModifier attributemodifier);

    @SideOnly(Side.CLIENT)
    void func_142049_d();

    double func_111126_e();
}
