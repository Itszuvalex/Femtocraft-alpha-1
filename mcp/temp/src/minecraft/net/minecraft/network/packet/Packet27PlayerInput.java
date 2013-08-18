package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet27PlayerInput extends Packet {

   private float field_111017_a;
   private float field_111015_b;
   private boolean field_111016_c;
   private boolean field_111014_d;


   public Packet27PlayerInput() {}

   @SideOnly(Side.CLIENT)
   public Packet27PlayerInput(float p_i1454_1_, float p_i1454_2_, boolean p_i1454_3_, boolean p_i1454_4_) {
      this.field_111017_a = p_i1454_1_;
      this.field_111015_b = p_i1454_2_;
      this.field_111016_c = p_i1454_3_;
      this.field_111014_d = p_i1454_4_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_111017_a = p_73267_1_.readFloat();
      this.field_111015_b = p_73267_1_.readFloat();
      this.field_111016_c = p_73267_1_.readBoolean();
      this.field_111014_d = p_73267_1_.readBoolean();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeFloat(this.field_111017_a);
      p_73273_1_.writeFloat(this.field_111015_b);
      p_73273_1_.writeBoolean(this.field_111016_c);
      p_73273_1_.writeBoolean(this.field_111014_d);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_110774_a(this);
   }

   public int func_73284_a() {
      return 10;
   }

   public float func_111010_d() {
      return this.field_111017_a;
   }

   public float func_111012_f() {
      return this.field_111015_b;
   }

   public boolean func_111013_g() {
      return this.field_111016_c;
   }

   public boolean func_111011_h() {
      return this.field_111014_d;
   }
}
