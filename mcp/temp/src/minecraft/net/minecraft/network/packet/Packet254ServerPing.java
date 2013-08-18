package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class Packet254ServerPing extends Packet {

   private static final int field_140051_d = (new Packet250CustomPayload()).func_73281_k();
   public int field_82559_a;
   public String field_140052_b;
   public int field_140053_c;


   public Packet254ServerPing() {}

   @SideOnly(Side.CLIENT)
   public Packet254ServerPing(int p_i2330_1_, String p_i2330_2_, int p_i2330_3_) {
      this.field_82559_a = p_i2330_1_;
      this.field_140052_b = p_i2330_2_;
      this.field_140053_c = p_i2330_3_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      try {
         this.field_82559_a = p_73267_1_.readByte();

         try {
            p_73267_1_.readByte();
            func_73282_a(p_73267_1_, 255);
            p_73267_1_.readShort();
            this.field_82559_a = p_73267_1_.readByte();
            if(this.field_82559_a >= 73) {
               this.field_140052_b = func_73282_a(p_73267_1_, 255);
               this.field_140053_c = p_73267_1_.readInt();
            }
         } catch (Throwable var3) {
            this.field_140052_b = "";
         }
      } catch (Throwable var4) {
         this.field_82559_a = 0;
         this.field_140052_b = "";
      }

   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeByte(1);
      p_73273_1_.writeByte(field_140051_d);
      Packet.func_73271_a("MC|PingHost", p_73273_1_);
      p_73273_1_.writeShort(3 + 2 * this.field_140052_b.length() + 4);
      p_73273_1_.writeByte(this.field_82559_a);
      Packet.func_73271_a(this.field_140052_b, p_73273_1_);
      p_73273_1_.writeInt(this.field_140053_c);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72467_a(this);
   }

   public int func_73284_a() {
      return 3 + this.field_140052_b.length() * 2 + 4;
   }

   public boolean func_140050_d() {
      return this.field_82559_a == 0;
   }

}
