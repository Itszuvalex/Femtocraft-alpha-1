package net.minecraft.entity.ai.attributes;

import net.minecraft.entity.ai.attributes.Attribute;

public abstract class BaseAttribute implements Attribute {

   private final String field_111115_a;
   private final double field_111113_b;
   private boolean field_111114_c;


   protected BaseAttribute(String p_i1607_1_, double p_i1607_2_) {
      this.field_111115_a = p_i1607_1_;
      this.field_111113_b = p_i1607_2_;
      if(p_i1607_1_ == null) {
         throw new IllegalArgumentException("Name cannot be null!");
      }
   }

   public String func_111108_a() {
      return this.field_111115_a;
   }

   public double func_111110_b() {
      return this.field_111113_b;
   }

   public boolean func_111111_c() {
      return this.field_111114_c;
   }

   public BaseAttribute func_111112_a(boolean p_111112_1_) {
      this.field_111114_c = p_111112_1_;
      return this;
   }

   public int hashCode() {
      return this.field_111115_a.hashCode();
   }
}
