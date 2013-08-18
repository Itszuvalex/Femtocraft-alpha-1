package net.minecraft.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
class CallableModded implements Callable {

   // $FF: synthetic field
   final Minecraft field_74500_a;


   CallableModded(Minecraft p_i1012_1_) {
      this.field_74500_a = p_i1012_1_;
   }

   public String func_74499_a() {
      String var1 = ClientBrandRetriever.getClientModName();
      return !var1.equals("vanilla")?"Definitely; Client brand changed to \'" + var1 + "\'":(Minecraft.class.getSigners() == null?"Very likely; Jar signature invalidated":"Probably not. Jar signature remains and client brand is untouched.");
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_74499_a();
   }
}
