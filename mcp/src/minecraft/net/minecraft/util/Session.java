package net.minecraft.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Session
{
    private final String username;
    private final String sessionId;

    public Session(String par1Str, String par2Str)
    {
        this.username = par1Str;
        this.sessionId = par2Str;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getSessionID()
    {
        return this.sessionId;
    }
}
