package net.minecraft.client.multiplayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.LanServer;
import net.minecraft.client.multiplayer.ThreadLanServerPing;

@SideOnly(Side.CLIENT)
public class LanServerList {

   private ArrayList field_77555_b = new ArrayList();
   boolean field_77556_a;


   public synchronized boolean func_77553_a() {
      return this.field_77556_a;
   }

   public synchronized void func_77552_b() {
      this.field_77556_a = false;
   }

   public synchronized List func_77554_c() {
      return Collections.unmodifiableList(this.field_77555_b);
   }

   public synchronized void func_77551_a(String p_77551_1_, InetAddress p_77551_2_) {
      String var3 = ThreadLanServerPing.func_77524_a(p_77551_1_);
      String var4 = ThreadLanServerPing.func_77523_b(p_77551_1_);
      if(var4 != null) {
         var4 = p_77551_2_.getHostAddress() + ":" + var4;
         boolean var5 = false;
         Iterator var6 = this.field_77555_b.iterator();

         while(var6.hasNext()) {
            LanServer var7 = (LanServer)var6.next();
            if(var7.func_77488_b().equals(var4)) {
               var7.func_77489_c();
               var5 = true;
               break;
            }
         }

         if(!var5) {
            this.field_77555_b.add(new LanServer(var3, var4));
            this.field_77556_a = true;
         }

      }
   }
}
