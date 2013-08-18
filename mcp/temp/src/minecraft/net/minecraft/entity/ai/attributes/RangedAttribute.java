package net.minecraft.entity.ai.attributes;

import net.minecraft.entity.ai.attributes.BaseAttribute;

public class RangedAttribute extends BaseAttribute {

   private final double field_111120_a;
   private final double field_111118_b;
   private String field_111119_c;


   public RangedAttribute(String p_i1609_1_, double p_i1609_2_, double p_i1609_4_, double p_i1609_6_) {
      super(p_i1609_1_, p_i1609_2_);
      this.field_111120_a = p_i1609_4_;
      this.field_111118_b = p_i1609_6_;
      if(p_i1609_4_ > p_i1609_6_) {
         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
      } else if(p_i1609_2_ < p_i1609_4_) {
         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
      } else if(p_i1609_2_ > p_i1609_6_) {
         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
      }
   }

   public RangedAttribute func_111117_a(String p_111117_1_) {
      this.field_111119_c = p_111117_1_;
      return this;
   }

   public String func_111116_f() {
      return this.field_111119_c;
   }

   public double func_111109_a(double p_111109_1_) {
      if(p_111109_1_ < this.field_111120_a) {
         p_111109_1_ = this.field_111120_a;
      }

      if(p_111109_1_ > this.field_111118_b) {
         p_111109_1_ = this.field_111118_b;
      }

      return p_111109_1_;
   }
}
