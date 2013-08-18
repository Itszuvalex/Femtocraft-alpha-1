package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import org.apache.commons.lang3.StringUtils;

public class Packet203AutoComplete extends Packet {

   private String field_73474_a;


   public Packet203AutoComplete() {}

   public Packet203AutoComplete(String p_i1409_1_) {
      this.field_73474_a = p_i1409_1_;
   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73474_a = func_73282_a(p_73267_1_, 32767);
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      func_73271_a(StringUtils.substring(this.field_73474_a, 0, 32767), p_73273_1_);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72461_a(this);
   }

   public int func_73284_a() {
      return 2 + this.field_73474_a.length() * 2;
   }

   public String func_73473_d() {
      return this.field_73474_a;
   }

   public boolean func_73278_e() {
      return true;
   }

   public boolean func_73268_a(Packet p_73268_1_) {
      return true;
   }
}
