package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet4UpdateTime extends Packet {

   public long field_82562_a;
   public long field_73301_a;


   public Packet4UpdateTime() {}

   public Packet4UpdateTime(long p_i1476_1_, long p_i1476_3_, boolean p_i1476_5_) {
      this.field_82562_a = p_i1476_1_;
      this.field_73301_a = p_i1476_3_;
      if(!p_i1476_5_) {
         this.field_73301_a = -this.field_73301_a;
         if(this.field_73301_a == 0L) {
            this.field_73301_a = -1L;
         }
      }

   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_82562_a = p_73267_1_.readLong();
      this.field_73301_a = p_73267_1_.readLong();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeLong(this.field_82562_a);
      p_73273_1_.writeLong(this.field_73301_a);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72497_a(this);
   }

   public int func_73284_a() {
      return 16;
   }

   public boolean func_73278_e() {
      return true;
   }

   public boolean func_73268_a(Packet p_73268_1_) {
      return true;
   }

   public boolean func_73277_a_() {
      return true;
   }
}
