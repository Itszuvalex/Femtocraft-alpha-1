package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet100OpenWindow extends Packet {

   public int field_73431_a;
   public int field_73429_b;
   public String field_73430_c;
   public int field_73428_d;
   public boolean field_94500_e;
   public int field_111008_f;


   public Packet100OpenWindow() {}

   public Packet100OpenWindow(int p_i1423_1_, int p_i1423_2_, String p_i1423_3_, int p_i1423_4_, boolean p_i1423_5_) {
      this.field_73431_a = p_i1423_1_;
      this.field_73429_b = p_i1423_2_;
      this.field_73430_c = p_i1423_3_;
      this.field_73428_d = p_i1423_4_;
      this.field_94500_e = p_i1423_5_;
   }

   public Packet100OpenWindow(int p_i1424_1_, int p_i1424_2_, String p_i1424_3_, int p_i1424_4_, boolean p_i1424_5_, int p_i1424_6_) {
      this(p_i1424_1_, p_i1424_2_, p_i1424_3_, p_i1424_4_, p_i1424_5_);
      this.field_111008_f = p_i1424_6_;
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72516_a(this);
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73431_a = p_73267_1_.readByte() & 255;
      this.field_73429_b = p_73267_1_.readByte() & 255;
      this.field_73430_c = func_73282_a(p_73267_1_, 32);
      this.field_73428_d = p_73267_1_.readByte() & 255;
      this.field_94500_e = p_73267_1_.readBoolean();
      if(this.field_73429_b == 11) {
         this.field_111008_f = p_73267_1_.readInt();
      }

   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeByte(this.field_73431_a & 255);
      p_73273_1_.writeByte(this.field_73429_b & 255);
      func_73271_a(this.field_73430_c, p_73273_1_);
      p_73273_1_.writeByte(this.field_73428_d & 255);
      p_73273_1_.writeBoolean(this.field_94500_e);
      if(this.field_73429_b == 11) {
         p_73273_1_.writeInt(this.field_111008_f);
      }

   }

   public int func_73284_a() {
      return this.field_73429_b == 11?8 + this.field_73430_c.length():4 + this.field_73430_c.length();
   }
}
