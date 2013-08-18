package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentWaterWorker extends Enchantment {

   public EnchantmentWaterWorker(int p_i1939_1_, int p_i1939_2_) {
      super(p_i1939_1_, p_i1939_2_, EnumEnchantmentType.armor_head);
      this.func_77322_b("waterWorker");
   }

   public int func_77321_a(int p_77321_1_) {
      return 1;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 40;
   }

   public int func_77325_b() {
      return 1;
   }
}
