package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet205ClientCommand extends Packet {

   public int field_73447_a;


   public Packet205ClientCommand() {}

   @SideOnly(Side.CLIENT)
   public Packet205ClientCommand(int p_i1415_1_) {
      this.field_73447_a = p_i1415_1_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73447_a = p_73267_1_.readByte();
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeByte(this.field_73447_a & 255);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72458_a(this);
   }

   public int func_73284_a() {
      return 1;
   }
}
