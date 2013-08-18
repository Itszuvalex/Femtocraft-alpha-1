package net.minecraft.crash;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

public class CallableMinecraftVersion implements Callable {

   // $FF: synthetic field
   final CrashReport field_71494_a;


   public CallableMinecraftVersion(CrashReport p_i1338_1_) {
      this.field_71494_a = p_i1338_1_;
   }

   public String func_71493_a() {
      return "1.6.2";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_71493_a();
   }
}
