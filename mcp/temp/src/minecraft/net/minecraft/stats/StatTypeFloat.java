package net.minecraft.stats;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;

final class StatTypeFloat implements IStatType {

   @SideOnly(Side.CLIENT)
   public String func_75843_a(int p_75843_1_) {
      return StatBase.func_75969_k().format((double)p_75843_1_ * 0.1D);
   }
}
