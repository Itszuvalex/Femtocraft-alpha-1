package net.minecraft.client.multiplayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.multiplayer.WorldClient;

@SideOnly(Side.CLIENT)
class WorldClientINNER3 implements Callable {

   // $FF: synthetic field
   final WorldClient field_142027_a;


   WorldClientINNER3(WorldClient p_i2342_1_) {
      this.field_142027_a = p_i2342_1_;
   }

   public String func_142026_a() {
      return WorldClient.func_142030_c(this.field_142027_a).field_71439_g.func_142021_k();
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_142026_a();
   }
}
