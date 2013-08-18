package net.minecraft.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
class CallableClientProfiler implements Callable {

   // $FF: synthetic field
   final Minecraft field_90046_a;


   CallableClientProfiler(Minecraft p_i1004_1_) {
      this.field_90046_a = p_i1004_1_;
   }

   public String func_90045_a() {
      return Minecraft.func_142024_b(this.field_90046_a).func_135041_c().toString();
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_90045_a();
   }
}
