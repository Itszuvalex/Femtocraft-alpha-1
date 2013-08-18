package net.minecraft.client.multiplayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.multiplayer.WorldClient;

@SideOnly(Side.CLIENT)
class WorldClientINNER4 implements Callable {

   // $FF: synthetic field
   final WorldClient field_142029_a;


   WorldClientINNER4(WorldClient p_i2343_1_) {
      this.field_142029_a = p_i2343_1_;
   }

   public String func_142028_a() {
      return WorldClient.func_142030_c(this.field_142029_a).func_71401_C() == null?"Non-integrated multiplayer server":"Integrated singleplayer server";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_142028_a();
   }
}
