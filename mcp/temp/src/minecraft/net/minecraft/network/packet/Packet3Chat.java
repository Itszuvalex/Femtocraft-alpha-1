package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;

public class Packet3Chat extends Packet {

   public String field_73476_b;
   private boolean field_73477_c;


   public Packet3Chat() {
      this.field_73477_c = true;
   }

   public Packet3Chat(ChatMessageComponent p_i1410_1_) {
      this(p_i1410_1_.func_111062_i());
   }

   public Packet3Chat(ChatMessageComponent p_i1411_1_, boolean p_i1411_2_) {
      this(p_i1411_1_.func_111062_i(), p_i1411_2_);
   }

   public Packet3Chat(String p_i1412_1_) {
      this(p_i1412_1_, true);
   }

   public Packet3Chat(String p_i1413_1_, boolean p_i1413_2_) {
      this.field_73477_c = true;
      if(p_i1413_1_.length() > 32767) {
         p_i1413_1_ = p_i1413_1_.substring(0, 32767);
      }

      this.field_73476_b = p_i1413_1_;
      this.field_73477_c = p_i1413_2_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73476_b = func_73282_a(p_73267_1_, 32767);
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      func_73271_a(this.field_73476_b, p_73273_1_);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72481_a(this);
   }

   public int func_73284_a() {
      return 2 + this.field_73476_b.length() * 2;
   }

   public boolean func_73475_d() {
      return this.field_73477_c;
   }
}
