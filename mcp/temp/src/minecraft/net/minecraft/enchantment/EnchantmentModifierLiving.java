package net.minecraft.enchantment;

import net.minecraft.enchantment.Empty3;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.IEnchantmentModifier;
import net.minecraft.entity.EntityLivingBase;

final class EnchantmentModifierLiving implements IEnchantmentModifier {

   public float field_77495_a;
   public EntityLivingBase field_77494_b;


   private EnchantmentModifierLiving() {}

   public void func_77493_a(Enchantment p_77493_1_, int p_77493_2_) {
      this.field_77495_a += p_77493_1_.func_77323_a(p_77493_2_, this.field_77494_b);
   }

   // $FF: synthetic method
   EnchantmentModifierLiving(Empty3 p_i1928_1_) {
      this();
   }
}
