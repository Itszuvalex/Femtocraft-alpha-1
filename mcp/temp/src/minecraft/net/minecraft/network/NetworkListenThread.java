package net.minecraft.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.network.CallableConnectionName;
import net.minecraft.network.MemoryConnection;
import net.minecraft.network.NetServerHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ReportedException;

public abstract class NetworkListenThread {

   private final MinecraftServer field_71750_c;
   private final List field_71748_d = Collections.synchronizedList(new ArrayList());
   public volatile boolean field_71749_b;


   public NetworkListenThread(MinecraftServer p_i1532_1_) throws IOException {
      this.field_71750_c = p_i1532_1_;
      this.field_71749_b = true;
   }

   public void func_71745_a(NetServerHandler p_71745_1_) {
      this.field_71748_d.add(p_71745_1_);
   }

   public void func_71744_a() {
      this.field_71749_b = false;
   }

   public void func_71747_b() {
      for(int var1 = 0; var1 < this.field_71748_d.size(); ++var1) {
         NetServerHandler var2 = (NetServerHandler)this.field_71748_d.get(var1);

         try {
            var2.func_72570_d();
         } catch (Exception var6) {
            if(var2.field_72575_b instanceof MemoryConnection) {
               CrashReport var4 = CrashReport.func_85055_a(var6, "Ticking memory connection");
               CrashReportCategory var5 = var4.func_85058_a("Ticking connection");
               var5.func_71500_a("Connection", new CallableConnectionName(this, var2));
               throw new ReportedException(var4);
            }

            this.field_71750_c.func_98033_al().func_98235_b("Failed to handle packet for " + var2.field_72574_e.func_70023_ak() + "/" + var2.field_72574_e.func_71114_r() + ": " + var6, var6);
            var2.func_72565_c("Internal server error");
         }

         if(var2.field_72576_c) {
            this.field_71748_d.remove(var1--);
         }

         var2.field_72575_b.func_74427_a();
      }

   }

   public MinecraftServer func_71746_d() {
      return this.field_71750_c;
   }
}
