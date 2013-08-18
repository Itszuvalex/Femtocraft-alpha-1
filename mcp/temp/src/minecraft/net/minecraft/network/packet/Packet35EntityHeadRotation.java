package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet35EntityHeadRotation extends Packet {

   public int field_73383_a;
   public byte field_73382_b;


   public Packet35EntityHeadRotation() {}

   public Packet35EntityHeadRotation(int p_i1458_1_, byte p_i1458_2_) {
      this.field_73383_a = p_i1458_1_;
      this.field_73382_b = p_i1458_2_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73383_a = p_73267_1_.readInt();
      this.field_73382_b = p_73267_1_.readByte();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73383_a);
      p_73273_1_.writeByte(this.field_73382_b);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72478_a(this);
   }

   public int func_73284_a() {
      return 5;
   }

   public boolean func_73278_e() {
      return true;
   }

   public boolean func_73268_a(Packet p_73268_1_) {
      Packet35EntityHeadRotation var2 = (Packet35EntityHeadRotation)p_73268_1_;
      return var2.field_73383_a == this.field_73383_a;
   }

   public boolean func_73277_a_() {
      return true;
   }
}
