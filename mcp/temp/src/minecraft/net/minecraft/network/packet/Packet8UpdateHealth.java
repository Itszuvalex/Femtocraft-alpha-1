package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet8UpdateHealth extends Packet {

   public float field_73640_a;
   public int field_73638_b;
   public float field_73639_c;


   public Packet8UpdateHealth() {}

   public Packet8UpdateHealth(float p_i1469_1_, int p_i1469_2_, float p_i1469_3_) {
      this.field_73640_a = p_i1469_1_;
      this.field_73638_b = p_i1469_2_;
      this.field_73639_c = p_i1469_3_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73640_a = p_73267_1_.readFloat();
      this.field_73638_b = p_73267_1_.readShort();
      this.field_73639_c = p_73267_1_.readFloat();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeFloat(this.field_73640_a);
      p_73273_1_.writeShort(this.field_73638_b);
      p_73273_1_.writeFloat(this.field_73639_c);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72521_a(this);
   }

   public int func_73284_a() {
      return 8;
   }

   public boolean func_73278_e() {
      return true;
   }

   public boolean func_73268_a(Packet p_73268_1_) {
      return true;
   }
}
