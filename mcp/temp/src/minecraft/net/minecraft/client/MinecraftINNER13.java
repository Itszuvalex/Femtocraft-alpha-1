package net.minecraft.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
class MinecraftINNER13 implements Callable {

   // $FF: synthetic field
   final Minecraft field_142056_a;


   MinecraftINNER13(Minecraft p_i2341_1_) {
      this.field_142056_a = p_i2341_1_;
   }

   public String func_142055_a() {
      int var1 = this.field_142056_a.field_71441_e.func_82732_R().func_82591_c();
      int var2 = 56 * var1;
      int var3 = var2 / 1024 / 1024;
      int var4 = this.field_142056_a.field_71441_e.func_82732_R().func_82590_d();
      int var5 = 56 * var4;
      int var6 = var5 / 1024 / 1024;
      return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_142055_a();
   }
}
