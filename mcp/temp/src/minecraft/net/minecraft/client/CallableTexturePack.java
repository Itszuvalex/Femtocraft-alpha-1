package net.minecraft.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
class CallableTexturePack implements Callable {

   // $FF: synthetic field
   final Minecraft field_90051_a;


   CallableTexturePack(Minecraft p_i1003_1_) {
      this.field_90051_a = p_i1003_1_;
   }

   public String func_90050_a() {
      return this.field_90051_a.field_71474_y.field_74346_m;
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_90050_a();
   }
}
