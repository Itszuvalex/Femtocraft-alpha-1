package net.minecraft.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.EnumOS;

@SideOnly(Side.CLIENT)
public class Util {

   public static EnumOS func_110647_a() {
      String var0 = System.getProperty("os.name").toLowerCase();
      return var0.contains("win")?EnumOS.WINDOWS:(var0.contains("mac")?EnumOS.MACOS:(var0.contains("solaris")?EnumOS.SOLARIS:(var0.contains("sunos")?EnumOS.SOLARIS:(var0.contains("linux")?EnumOS.LINUX:(var0.contains("unix")?EnumOS.LINUX:EnumOS.UNKNOWN)))));
   }
}
