package net.minecraft.client.mco;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.McoServerListEmptyAnon;
import net.minecraft.client.mco.McoServerListUpdateTask;
import net.minecraft.util.Session;

@SideOnly(Side.CLIENT)
public class McoServerList {

   private volatile boolean field_98259_a;
   private McoServerListUpdateTask field_98257_b = new McoServerListUpdateTask(this, (McoServerListEmptyAnon)null);
   private Timer field_98258_c = new Timer();
   private Set field_140060_d = Sets.newHashSet();
   private List field_98255_d = Lists.newArrayList();
   private int field_130130_e;
   private boolean field_140059_g;
   private Session field_98254_f;
   private int field_140061_i;


   public McoServerList() {
      this.field_98258_c.schedule(this.field_98257_b, 0L, 10000L);
      this.field_98254_f = Minecraft.func_71410_x().func_110432_I();
   }

   public synchronized void func_130129_a(Session p_130129_1_) {
      this.field_98254_f = p_130129_1_;
      if(this.field_98259_a) {
         this.field_98259_a = false;
         this.field_98257_b = new McoServerListUpdateTask(this, (McoServerListEmptyAnon)null);
         this.field_98258_c = new Timer();
         this.field_98258_c.schedule(this.field_98257_b, 0L, 10000L);
      }

   }

   public synchronized boolean func_130127_a() {
      return this.field_140059_g;
   }

   public synchronized void func_98250_b() {
      this.field_140059_g = false;
   }

   public synchronized List func_98252_c() {
      return Lists.newArrayList(this.field_98255_d);
   }

   public int func_130124_d() {
      return this.field_130130_e;
   }

   public int func_140056_e() {
      return this.field_140061_i;
   }

   public synchronized void func_98248_d() {
      this.field_98259_a = true;
      this.field_98257_b.cancel();
      this.field_98258_c.cancel();
   }

   private synchronized void func_96426_a(List p_96426_1_) {
      int var2 = 0;
      Iterator var3 = this.field_140060_d.iterator();

      while(var3.hasNext()) {
         McoServer var4 = (McoServer)var3.next();
         if(p_96426_1_.remove(var4)) {
            ++var2;
         }
      }

      if(var2 == 0) {
         this.field_140060_d.clear();
      }

      this.field_98255_d = p_96426_1_;
      this.field_140059_g = true;
   }

   public synchronized void func_140058_a(McoServer p_140058_1_) {
      this.field_98255_d.remove(p_140058_1_);
      this.field_140060_d.add(p_140058_1_);
   }

   private void func_130123_a(int p_130123_1_) {
      this.field_130130_e = p_130123_1_;
   }

   // $FF: synthetic method
   static boolean func_98249_b(McoServerList p_98249_0_) {
      return p_98249_0_.field_98259_a;
   }

   // $FF: synthetic method
   static Session func_100014_a(McoServerList p_100014_0_) {
      return p_100014_0_.field_98254_f;
   }

   // $FF: synthetic method
   static void func_98247_a(McoServerList p_98247_0_, List p_98247_1_) {
      p_98247_0_.func_96426_a(p_98247_1_);
   }

   // $FF: synthetic method
   static void func_130122_a(McoServerList p_130122_0_, int p_130122_1_) {
      p_130122_0_.func_130123_a(p_130122_1_);
   }

   // $FF: synthetic method
   static int func_140057_b(McoServerList p_140057_0_, int p_140057_1_) {
      return p_140057_0_.field_140061_i = p_140057_1_;
   }
}
