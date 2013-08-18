package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet55BlockDestroy extends Packet {

   private int field_73329_a;
   private int field_73327_b;
   private int field_73328_c;
   private int field_73325_d;
   private int field_73326_e;


   public Packet55BlockDestroy() {}

   public Packet55BlockDestroy(int p_i1482_1_, int p_i1482_2_, int p_i1482_3_, int p_i1482_4_, int p_i1482_5_) {
      this.field_73329_a = p_i1482_1_;
      this.field_73327_b = p_i1482_2_;
      this.field_73328_c = p_i1482_3_;
      this.field_73325_d = p_i1482_4_;
      this.field_73326_e = p_i1482_5_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73329_a = p_73267_1_.readInt();
      this.field_73327_b = p_73267_1_.readInt();
      this.field_73328_c = p_73267_1_.readInt();
      this.field_73325_d = p_73267_1_.readInt();
      this.field_73326_e = p_73267_1_.readUnsignedByte();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73329_a);
      p_73273_1_.writeInt(this.field_73327_b);
      p_73273_1_.writeInt(this.field_73328_c);
      p_73273_1_.writeInt(this.field_73325_d);
      p_73273_1_.write(this.field_73326_e);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72465_a(this);
   }

   public int func_73284_a() {
      return 13;
   }

   @SideOnly(Side.CLIENT)
   public int func_73322_d() {
      return this.field_73329_a;
   }

   @SideOnly(Side.CLIENT)
   public int func_73321_f() {
      return this.field_73327_b;
   }

   @SideOnly(Side.CLIENT)
   public int func_73324_g() {
      return this.field_73328_c;
   }

   @SideOnly(Side.CLIENT)
   public int func_73320_h() {
      return this.field_73325_d;
   }

   @SideOnly(Side.CLIENT)
   public int func_73323_i() {
      return this.field_73326_e;
   }

   public boolean func_73278_e() {
      return true;
   }

   public boolean func_73268_a(Packet p_73268_1_) {
      Packet55BlockDestroy var2 = (Packet55BlockDestroy)p_73268_1_;
      return var2.field_73329_a == this.field_73329_a;
   }
}
