package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet19EntityAction extends Packet {

   public int field_73367_a;
   public int field_73366_b;
   public int field_111009_c;


   public Packet19EntityAction() {}

   @SideOnly(Side.CLIENT)
   public Packet19EntityAction(Entity p_i1451_1_, int p_i1451_2_) {
      this(p_i1451_1_, p_i1451_2_, 0);
   }

   @SideOnly(Side.CLIENT)
   public Packet19EntityAction(Entity p_i1452_1_, int p_i1452_2_, int p_i1452_3_) {
      this.field_73367_a = p_i1452_1_.field_70157_k;
      this.field_73366_b = p_i1452_2_;
      this.field_111009_c = p_i1452_3_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73367_a = p_73267_1_.readInt();
      this.field_73366_b = p_73267_1_.readByte();
      this.field_111009_c = p_73267_1_.readInt();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73367_a);
      p_73273_1_.writeByte(this.field_73366_b);
      p_73273_1_.writeInt(this.field_111009_c);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72473_a(this);
   }

   public int func_73284_a() {
      return 9;
   }
}
