package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet61DoorChange extends Packet {

   public int field_73567_a;
   public int field_73565_b;
   public int field_73566_c;
   public int field_73563_d;
   public int field_73564_e;
   private boolean field_82561_f;


   public Packet61DoorChange() {}

   public Packet61DoorChange(int p_i1438_1_, int p_i1438_2_, int p_i1438_3_, int p_i1438_4_, int p_i1438_5_, boolean p_i1438_6_) {
      this.field_73567_a = p_i1438_1_;
      this.field_73566_c = p_i1438_2_;
      this.field_73563_d = p_i1438_3_;
      this.field_73564_e = p_i1438_4_;
      this.field_73565_b = p_i1438_5_;
      this.field_82561_f = p_i1438_6_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73567_a = p_73267_1_.readInt();
      this.field_73566_c = p_73267_1_.readInt();
      this.field_73563_d = p_73267_1_.readByte() & 255;
      this.field_73564_e = p_73267_1_.readInt();
      this.field_73565_b = p_73267_1_.readInt();
      this.field_82561_f = p_73267_1_.readBoolean();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73567_a);
      p_73273_1_.writeInt(this.field_73566_c);
      p_73273_1_.writeByte(this.field_73563_d & 255);
      p_73273_1_.writeInt(this.field_73564_e);
      p_73273_1_.writeInt(this.field_73565_b);
      p_73273_1_.writeBoolean(this.field_82561_f);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72462_a(this);
   }

   public int func_73284_a() {
      return 21;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_82560_d() {
      return this.field_82561_f;
   }
}
