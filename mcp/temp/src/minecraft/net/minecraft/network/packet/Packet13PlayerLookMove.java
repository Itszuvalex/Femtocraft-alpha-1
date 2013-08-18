package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.Packet10Flying;

public class Packet13PlayerLookMove extends Packet10Flying {

   public Packet13PlayerLookMove() {
      this.field_73547_i = true;
      this.field_73546_h = true;
   }

   public Packet13PlayerLookMove(double p_i1446_1_, double p_i1446_3_, double p_i1446_5_, double p_i1446_7_, float p_i1446_9_, float p_i1446_10_, boolean p_i1446_11_) {
      this.field_73545_a = p_i1446_1_;
      this.field_73543_b = p_i1446_3_;
      this.field_73541_d = p_i1446_5_;
      this.field_73544_c = p_i1446_7_;
      this.field_73542_e = p_i1446_9_;
      this.field_73539_f = p_i1446_10_;
      this.field_73540_g = p_i1446_11_;
      this.field_73547_i = true;
      this.field_73546_h = true;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73545_a = p_73267_1_.readDouble();
      this.field_73543_b = p_73267_1_.readDouble();
      this.field_73541_d = p_73267_1_.readDouble();
      this.field_73544_c = p_73267_1_.readDouble();
      this.field_73542_e = p_73267_1_.readFloat();
      this.field_73539_f = p_73267_1_.readFloat();
      super.func_73267_a(p_73267_1_);
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeDouble(this.field_73545_a);
      p_73273_1_.writeDouble(this.field_73543_b);
      p_73273_1_.writeDouble(this.field_73541_d);
      p_73273_1_.writeDouble(this.field_73544_c);
      p_73273_1_.writeFloat(this.field_73542_e);
      p_73273_1_.writeFloat(this.field_73539_f);
      super.func_73273_a(p_73273_1_);
   }

   public int func_73284_a() {
      return 41;
   }
}
