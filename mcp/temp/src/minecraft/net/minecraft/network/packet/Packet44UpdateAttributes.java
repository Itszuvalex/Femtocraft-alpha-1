package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet44UpdateAttributesSnapshot;

public class Packet44UpdateAttributes extends Packet {

   private int field_111005_a;
   private final List field_111004_b = new ArrayList();


   public Packet44UpdateAttributes() {}

   public Packet44UpdateAttributes(int p_i1486_1_, Collection p_i1486_2_) {
      this.field_111005_a = p_i1486_1_;
      Iterator var3 = p_i1486_2_.iterator();

      while(var3.hasNext()) {
         AttributeInstance var4 = (AttributeInstance)var3.next();
         this.field_111004_b.add(new Packet44UpdateAttributesSnapshot(this, var4.func_111123_a().func_111108_a(), var4.func_111125_b(), var4.func_111122_c()));
      }

   }

   public void func_73267_a(DataInput p_73267_1_) throws IOException {
      this.field_111005_a = p_73267_1_.readInt();
      int var2 = p_73267_1_.readInt();

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = func_73282_a(p_73267_1_, 64);
         double var5 = p_73267_1_.readDouble();
         ArrayList var7 = new ArrayList();
         short var8 = p_73267_1_.readShort();

         for(int var9 = 0; var9 < var8; ++var9) {
            UUID var10 = new UUID(p_73267_1_.readLong(), p_73267_1_.readLong());
            var7.add(new AttributeModifier(var10, "Unknown synced attribute modifier", p_73267_1_.readDouble(), p_73267_1_.readByte()));
         }

         this.field_111004_b.add(new Packet44UpdateAttributesSnapshot(this, var4, var5, var7));
      }

   }

   public void func_73273_a(DataOutput p_73273_1_) throws IOException {
      p_73273_1_.writeInt(this.field_111005_a);
      p_73273_1_.writeInt(this.field_111004_b.size());
      Iterator var2 = this.field_111004_b.iterator();

      while(var2.hasNext()) {
         Packet44UpdateAttributesSnapshot var3 = (Packet44UpdateAttributesSnapshot)var2.next();
         func_73271_a(var3.func_142040_a(), p_73273_1_);
         p_73273_1_.writeDouble(var3.func_142041_b());
         p_73273_1_.writeShort(var3.func_142039_c().size());
         Iterator var4 = var3.func_142039_c().iterator();

         while(var4.hasNext()) {
            AttributeModifier var5 = (AttributeModifier)var4.next();
            p_73273_1_.writeLong(var5.func_111167_a().getMostSignificantBits());
            p_73273_1_.writeLong(var5.func_111167_a().getLeastSignificantBits());
            p_73273_1_.writeDouble(var5.func_111164_d());
            p_73273_1_.writeByte(var5.func_111169_c());
         }
      }

   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_110773_a(this);
   }

   public int func_73284_a() {
      return 8 + this.field_111004_b.size() * 24;
   }

   @SideOnly(Side.CLIENT)
   public int func_111002_d() {
      return this.field_111005_a;
   }

   @SideOnly(Side.CLIENT)
   public List func_111003_f() {
      return this.field_111004_b;
   }
}
