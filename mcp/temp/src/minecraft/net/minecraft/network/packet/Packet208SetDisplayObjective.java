package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.scoreboard.ScoreObjective;

public class Packet208SetDisplayObjective extends Packet {

   public int field_96481_a;
   public String field_96480_b;


   public Packet208SetDisplayObjective() {}

   public Packet208SetDisplayObjective(int p_i1462_1_, ScoreObjective p_i1462_2_) {
      this.field_96481_a = p_i1462_1_;
      if(p_i1462_2_ == null) {
         this.field_96480_b = "";
      } else {
         this.field_96480_b = p_i1462_2_.func_96679_b();
      }

   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_96481_a = p_73267_1_.readByte();
      this.field_96480_b = func_73282_a(p_73267_1_, 16);
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeByte(this.field_96481_a);
      func_73271_a(this.field_96480_b, p_73273_1_);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_96438_a(this);
   }

   public int func_73284_a() {
      return 3 + this.field_96480_b.length();
   }
}
