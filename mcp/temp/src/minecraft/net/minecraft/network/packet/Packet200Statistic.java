package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet200Statistic extends Packet {

   public int field_73472_a;
   public int field_73471_b;


   public Packet200Statistic() {}

   public Packet200Statistic(int p_i1408_1_, int p_i1408_2_) {
      this.field_73472_a = p_i1408_1_;
      this.field_73471_b = p_i1408_2_;
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72517_a(this);
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73472_a = p_73267_1_.readInt();
      this.field_73471_b = p_73267_1_.readInt();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73472_a);
      p_73273_1_.writeInt(this.field_73471_b);
   }

   public int func_73284_a() {
      return 6;
   }

   public boolean func_73277_a_() {
      return true;
   }
}
