package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet18Animation extends Packet {

   public int field_73470_a;
   public int field_73469_b;


   public Packet18Animation() {}

   public Packet18Animation(Entity p_i1407_1_, int p_i1407_2_) {
      this.field_73470_a = p_i1407_1_.field_70157_k;
      this.field_73469_b = p_i1407_2_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73470_a = p_73267_1_.readInt();
      this.field_73469_b = p_73267_1_.readByte();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73470_a);
      p_73273_1_.writeByte(this.field_73469_b);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72524_a(this);
   }

   public int func_73284_a() {
      return 5;
   }
}
