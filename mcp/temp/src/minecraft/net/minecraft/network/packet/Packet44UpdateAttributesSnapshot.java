package net.minecraft.network.packet;

import java.util.Collection;
import net.minecraft.network.packet.Packet44UpdateAttributes;

public class Packet44UpdateAttributesSnapshot {

   private final String field_142043_b;
   private final double field_142044_c;
   private final Collection field_142042_d;
   // $FF: synthetic field
   final Packet44UpdateAttributes field_142045_a;


   public Packet44UpdateAttributesSnapshot(Packet44UpdateAttributes p_i2346_1_, String p_i2346_2_, double p_i2346_3_, Collection p_i2346_5_) {
      this.field_142045_a = p_i2346_1_;
      this.field_142043_b = p_i2346_2_;
      this.field_142044_c = p_i2346_3_;
      this.field_142042_d = p_i2346_5_;
   }

   public String func_142040_a() {
      return this.field_142043_b;
   }

   public double func_142041_b() {
      return this.field_142044_c;
   }

   public Collection func_142039_c() {
      return this.field_142042_d;
   }
}
