package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet39AttachEntity extends Packet {

   public int field_111007_a;
   public int field_111006_b;
   public int field_73296_b;


   public Packet39AttachEntity() {}

   public Packet39AttachEntity(int p_i1464_1_, Entity p_i1464_2_, Entity p_i1464_3_) {
      this.field_111007_a = p_i1464_1_;
      this.field_111006_b = p_i1464_2_.field_70157_k;
      this.field_73296_b = p_i1464_3_ != null?p_i1464_3_.field_70157_k:-1;
   }

   public int func_73284_a() {
      return 8;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_111006_b = p_73267_1_.readInt();
      this.field_73296_b = p_73267_1_.readInt();
      this.field_111007_a = p_73267_1_.readUnsignedByte();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_111006_b);
      p_73273_1_.writeInt(this.field_73296_b);
      p_73273_1_.writeByte(this.field_111007_a);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72484_a(this);
   }

   public boolean func_73278_e() {
      return true;
   }

   public boolean func_73268_a(Packet p_73268_1_) {
      Packet39AttachEntity var2 = (Packet39AttachEntity)p_73268_1_;
      return var2.field_111006_b == this.field_111006_b;
   }
}
