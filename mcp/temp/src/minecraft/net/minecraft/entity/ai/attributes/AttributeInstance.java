package net.minecraft.entity.ai.attributes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public interface AttributeInstance {

   Attribute func_111123_a();

   double func_111125_b();

   void func_111128_a(double var1);

   Collection func_111122_c();

   AttributeModifier func_111127_a(UUID var1);

   void func_111121_a(AttributeModifier var1);

   void func_111124_b(AttributeModifier var1);

   @SideOnly(Side.CLIENT)
   void func_142049_d();

   double func_111126_e();
}
