package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet38EntityStatus extends Packet {

   public int field_73627_a;
   public byte field_73626_b;


   public Packet38EntityStatus() {}

   public Packet38EntityStatus(int p_i1431_1_, byte p_i1431_2_) {
      this.field_73627_a = p_i1431_1_;
      this.field_73626_b = p_i1431_2_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73627_a = p_73267_1_.readInt();
      this.field_73626_b = p_73267_1_.readByte();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73627_a);
      p_73273_1_.writeByte(this.field_73626_b);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72485_a(this);
   }

   public int func_73284_a() {
      return 5;
   }
}
