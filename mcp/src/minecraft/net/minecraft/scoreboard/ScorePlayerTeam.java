package net.minecraft.scoreboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ScorePlayerTeam extends Team
{
    private final Scoreboard theScoreboard;
    private final String field_96675_b;

    /** A set of all team member usernames. */
    private final Set membershipSet = new HashSet();
    private String field_96673_d;
    private String field_96674_e = "";
    private String field_96671_f = "";
    private boolean field_96672_g = true;
    private boolean field_98301_h = true;

    public ScorePlayerTeam(Scoreboard par1Scoreboard, String par2Str)
    {
        this.theScoreboard = par1Scoreboard;
        this.field_96675_b = par2Str;
        this.field_96673_d = par2Str;
    }

    public String func_96661_b()
    {
        return this.field_96675_b;
    }

    public String func_96669_c()
    {
        return this.field_96673_d;
    }

    public void func_96664_a(String par1Str)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Name cannot be null");
        }
        else
        {
            this.field_96673_d = par1Str;
            this.theScoreboard.func_96538_b(this);
        }
    }

    public Collection getMembershipCollection()
    {
        return this.membershipSet;
    }

    /**
     * Returns the color prefix for the player's team name
     */
    public String getColorPrefix()
    {
        return this.field_96674_e;
    }

    public void func_96666_b(String par1Str)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        else
        {
            this.field_96674_e = par1Str;
            this.theScoreboard.func_96538_b(this);
        }
    }

    /**
     * Returns the color suffix for the player's team name
     */
    public String getColorSuffix()
    {
        return this.field_96671_f;
    }

    public void func_96662_c(String par1Str)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Suffix cannot be null");
        }
        else
        {
            this.field_96671_f = par1Str;
            this.theScoreboard.func_96538_b(this);
        }
    }

    public String func_142053_d(String par1Str)
    {
        return this.getColorPrefix() + par1Str + this.getColorSuffix();
    }

    /**
     * Returns the player name including the color prefixes and suffixes
     */
    public static String formatPlayerName(Team par0Team, String par1Str)
    {
        return par0Team == null ? par1Str : par0Team.func_142053_d(par1Str);
    }

    public boolean func_96665_g()
    {
        return this.field_96672_g;
    }

    public void func_96660_a(boolean par1)
    {
        this.field_96672_g = par1;
        this.theScoreboard.func_96538_b(this);
    }

    public boolean func_98297_h()
    {
        return this.field_98301_h;
    }

    public void func_98300_b(boolean par1)
    {
        this.field_98301_h = par1;
        this.theScoreboard.func_96538_b(this);
    }

    public int func_98299_i()
    {
        int i = 0;

        if (this.func_96665_g())
        {
            i |= 1;
        }

        if (this.func_98297_h())
        {
            i |= 2;
        }

        return i;
    }

    @SideOnly(Side.CLIENT)
    public void func_98298_a(int par1)
    {
        this.func_96660_a((par1 & 1) > 0);
        this.func_98300_b((par1 & 2) > 0);
    }
}
