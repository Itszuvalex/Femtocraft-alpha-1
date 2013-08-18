package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ExceptionMcoHttp extends RuntimeException {

   public ExceptionMcoHttp(String p_i1139_1_, Exception p_i1139_2_) {
      super(p_i1139_1_, p_i1139_2_);
   }
}
