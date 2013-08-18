package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.WeightedRandomItem;

public class EnchantmentData extends WeightedRandomItem {

   public final Enchantment field_76302_b;
   public final int field_76303_c;


   public EnchantmentData(Enchantment p_i1930_1_, int p_i1930_2_) {
      super(p_i1930_1_.func_77324_c());
      this.field_76302_b = p_i1930_1_;
      this.field_76303_c = p_i1930_2_;
   }

   public EnchantmentData(int p_i1931_1_, int p_i1931_2_) {
      this(Enchantment.field_77331_b[p_i1931_1_], p_i1931_2_);
   }
}
