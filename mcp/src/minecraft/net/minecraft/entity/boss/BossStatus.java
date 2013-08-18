package net.minecraft.entity.boss;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class BossStatus
{
    public static float healthScale;
    public static int statusBarLength;
    public static String bossName;
    public static boolean field_82825_d;

    public static void func_82824_a(IBossDisplayData par0IBossDisplayData, boolean par1)
    {
        healthScale = par0IBossDisplayData.func_110143_aJ() / par0IBossDisplayData.func_110138_aP();
        statusBarLength = 100;
        bossName = par0IBossDisplayData.getEntityName();
        field_82825_d = par1;
    }
}
