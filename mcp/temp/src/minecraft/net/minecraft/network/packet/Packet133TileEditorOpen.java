package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet133TileEditorOpen extends Packet {

   public int field_142037_a;
   public int field_142035_b;
   public int field_142036_c;
   public int field_142034_d;


   public Packet133TileEditorOpen() {}

   public Packet133TileEditorOpen(int p_i2345_1_, int p_i2345_2_, int p_i2345_3_, int p_i2345_4_) {
      this.field_142037_a = p_i2345_1_;
      this.field_142035_b = p_i2345_2_;
      this.field_142036_c = p_i2345_3_;
      this.field_142034_d = p_i2345_4_;
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_142031_a(this);
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_142037_a = p_73267_1_.readByte();
      this.field_142035_b = p_73267_1_.readInt();
      this.field_142036_c = p_73267_1_.readInt();
      this.field_142034_d = p_73267_1_.readInt();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeByte(this.field_142037_a);
      p_73273_1_.writeInt(this.field_142035_b);
      p_73273_1_.writeInt(this.field_142036_c);
      p_73273_1_.writeInt(this.field_142034_d);
   }

   public int func_73284_a() {
      return 13;
   }
}
