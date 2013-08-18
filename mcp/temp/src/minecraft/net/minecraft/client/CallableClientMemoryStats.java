package net.minecraft.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
class CallableClientMemoryStats implements Callable {

   // $FF: synthetic field
   final Minecraft field_90048_a;


   CallableClientMemoryStats(Minecraft p_i1005_1_) {
      this.field_90048_a = p_i1005_1_;
   }

   public String func_90047_a() {
      return this.field_90048_a.field_71424_I.field_76327_a?this.field_90048_a.field_71424_I.func_76322_c():"N/A (disabled)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_90047_a();
   }
}
