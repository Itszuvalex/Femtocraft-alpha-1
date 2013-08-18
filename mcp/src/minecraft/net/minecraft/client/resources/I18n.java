package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class I18n
{
    private static Locale field_135054_a;

    static void func_135051_a(Locale par0Locale)
    {
        field_135054_a = par0Locale;
    }

    public static String func_135053_a(String par0Str)
    {
        return field_135054_a.func_135027_a(par0Str);
    }

    public static String func_135052_a(String par0Str, Object ... par1ArrayOfObj)
    {
        return field_135054_a.func_135023_a(par0Str, par1ArrayOfObj);
    }
}
