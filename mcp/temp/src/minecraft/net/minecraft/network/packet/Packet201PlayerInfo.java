package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet201PlayerInfo extends Packet {

   public String field_73365_a;
   public boolean field_73363_b;
   public int field_73364_c;


   public Packet201PlayerInfo() {}

   public Packet201PlayerInfo(String p_i1453_1_, boolean p_i1453_2_, int p_i1453_3_) {
      this.field_73365_a = p_i1453_1_;
      this.field_73363_b = p_i1453_2_;
      this.field_73364_c = p_i1453_3_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73365_a = func_73282_a(p_73267_1_, 16);
      this.field_73363_b = p_73267_1_.readByte() != 0;
      this.field_73364_c = p_73267_1_.readShort();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      func_73271_a(this.field_73365_a, p_73273_1_);
      p_73273_1_.writeByte(this.field_73363_b?1:0);
      p_73273_1_.writeShort(this.field_73364_c);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72480_a(this);
   }

   public int func_73284_a() {
      return this.field_73365_a.length() + 2 + 1 + 2;
   }
}
