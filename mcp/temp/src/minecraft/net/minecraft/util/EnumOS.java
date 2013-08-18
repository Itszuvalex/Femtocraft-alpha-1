package net.minecraft.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum EnumOS {

   LINUX("LINUX", 0),
   SOLARIS("SOLARIS", 1),
   WINDOWS("WINDOWS", 2),
   MACOS("MACOS", 3),
   UNKNOWN("UNKNOWN", 4);
   // $FF: synthetic field
   private static final EnumOS[] field_110676_f = new EnumOS[]{LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN};


   private EnumOS(String p_i1357_1_, int p_i1357_2_) {}

}
