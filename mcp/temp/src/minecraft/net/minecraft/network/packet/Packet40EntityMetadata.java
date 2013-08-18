package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet40EntityMetadata extends Packet {

   public int field_73393_a;
   private List field_73392_b;


   public Packet40EntityMetadata() {}

   public Packet40EntityMetadata(int p_i1463_1_, DataWatcher p_i1463_2_, boolean p_i1463_3_) {
      this.field_73393_a = p_i1463_1_;
      if(p_i1463_3_) {
         this.field_73392_b = p_i1463_2_.func_75685_c();
      } else {
         this.field_73392_b = p_i1463_2_.func_75688_b();
      }

   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_73393_a = p_73267_1_.readInt();
      this.field_73392_b = DataWatcher.func_75686_a(p_73267_1_);
   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_73393_a);
      DataWatcher.func_75680_a(this.field_73392_b, p_73273_1_);
   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72493_a(this);
   }

   public int func_73284_a() {
      return 5;
   }

   @SideOnly(Side.CLIENT)
   public List func_73391_d() {
      return this.field_73392_b;
   }
}
