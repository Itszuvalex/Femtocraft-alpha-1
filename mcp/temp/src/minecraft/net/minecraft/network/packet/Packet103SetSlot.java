package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet103SetSlot extends Packet {

   public int field_73637_a;
   public int field_73635_b;
   public ItemStack field_73636_c;


   public Packet103SetSlot() {}

   public Packet103SetSlot(int p_i1427_1_, int p_i1427_2_, ItemStack p_i1427_3_) {
      this.field_73637_a = p_i1427_1_;
      this.field_73635_b = p_i1427_2_;
      this.field_73636_c = p_i1427_3_ == null?p_i1427_3_:p_i1427_3_.func_77946_l();
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72490_a(this);
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73637_a = p_73267_1_.readByte();
      this.field_73635_b = p_73267_1_.readShort();
      this.field_73636_c = func_73276_c(p_73267_1_);
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeByte(this.field_73637_a);
      p_73273_1_.writeShort(this.field_73635_b);
      func_73270_a(this.field_73636_c, p_73273_1_);
   }

   public int func_73284_a() {
      return 8;
   }
}
