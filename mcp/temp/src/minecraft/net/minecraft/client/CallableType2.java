package net.minecraft.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
class CallableType2 implements Callable {

   // $FF: synthetic field
   final Minecraft field_82887_a;


   CallableType2(Minecraft p_i1013_1_) {
      this.field_82887_a = p_i1013_1_;
   }

   public String func_82886_a() {
      return "Client (map_client.txt)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_82886_a();
   }
}
