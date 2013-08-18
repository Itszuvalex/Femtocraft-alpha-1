package net.minecraft.scoreboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class Team {

   public boolean func_142054_a(Team p_142054_1_) {
      return p_142054_1_ == null?false:this == p_142054_1_;
   }

   public abstract String func_96661_b();

   public abstract String func_142053_d(String var1);

   @SideOnly(Side.CLIENT)
   public abstract boolean func_98297_h();

   public abstract boolean func_96665_g();
}
