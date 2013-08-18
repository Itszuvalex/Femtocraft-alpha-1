package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServerList;
import net.minecraft.client.mco.McoServerListEmptyAnon;
import net.minecraft.client.mco.McoServerListUpdateTaskComparator;

@SideOnly(Side.CLIENT)
class McoServerListUpdateTask extends TimerTask {

   private McoClient field_140066_b;
   // $FF: synthetic field
   final McoServerList field_140067_a;


   private McoServerListUpdateTask(McoServerList p_i2328_1_) {
      this.field_140067_a = p_i2328_1_;
   }

   public void run() {
      if(!McoServerList.func_98249_b(this.field_140067_a)) {
         this.func_140064_c();
         this.func_140062_a();
         this.func_140063_b();
      }

   }

   private void func_140062_a() {
      try {
         if(McoServerList.func_100014_a(this.field_140067_a) != null) {
            this.field_140066_b = new McoClient(McoServerList.func_100014_a(this.field_140067_a));
            List var1 = this.field_140066_b.func_96382_a().field_96430_d;
            if(var1 != null) {
               this.func_140065_a(var1);
               McoServerList.func_98247_a(this.field_140067_a, var1);
            }
         }
      } catch (ExceptionMcoService var2) {
         Minecraft.func_71410_x().func_98033_al().func_98232_c(var2.toString());
      } catch (IOException var3) {
         Minecraft.func_71410_x().func_98033_al().func_98236_b("Realms: could not parse response from server");
      }

   }

   private void func_140063_b() {
      try {
         if(McoServerList.func_100014_a(this.field_140067_a) != null) {
            int var1 = this.field_140066_b.func_130106_e();
            McoServerList.func_130122_a(this.field_140067_a, var1);
         }
      } catch (ExceptionMcoService var2) {
         Minecraft.func_71410_x().func_98033_al().func_98232_c(var2.toString());
      }

   }

   private void func_140064_c() {
      try {
         if(McoServerList.func_100014_a(this.field_140067_a) != null) {
            McoClient var1 = new McoClient(McoServerList.func_100014_a(this.field_140067_a));
            McoServerList.func_140057_b(this.field_140067_a, var1.func_96379_c());
         }
      } catch (ExceptionMcoService var2) {
         Minecraft.func_71410_x().func_98033_al().func_98232_c(var2.toString());
         McoServerList.func_140057_b(this.field_140067_a, 0);
      }

   }

   private void func_140065_a(List p_140065_1_) {
      Collections.sort(p_140065_1_, new McoServerListUpdateTaskComparator(this, McoServerList.func_100014_a(this.field_140067_a).func_111285_a(), (McoServerListEmptyAnon)null));
   }

   // $FF: synthetic method
   McoServerListUpdateTask(McoServerList p_i2329_1_, McoServerListEmptyAnon p_i2329_2_) {
      this(p_i2329_1_);
   }
}
