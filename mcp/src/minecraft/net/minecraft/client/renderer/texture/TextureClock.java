package net.minecraft.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public class TextureClock extends TextureAtlasSprite
{
    private double field_94239_h;
    private double field_94240_i;

    public TextureClock(String par1Str)
    {
        super(par1Str);
    }

    public void updateAnimation()
    {
        if (!this.field_110976_a.isEmpty())
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            double d0 = 0.0D;

            if (minecraft.theWorld != null && minecraft.thePlayer != null)
            {
                float f = minecraft.theWorld.getCelestialAngle(1.0F);
                d0 = (double)f;

                if (!minecraft.theWorld.provider.isSurfaceWorld())
                {
                    d0 = Math.random();
                }
            }

            double d1;

            for (d1 = d0 - this.field_94239_h; d1 < -0.5D; ++d1)
            {
                ;
            }

            while (d1 >= 0.5D)
            {
                --d1;
            }

            if (d1 < -1.0D)
            {
                d1 = -1.0D;
            }

            if (d1 > 1.0D)
            {
                d1 = 1.0D;
            }

            this.field_94240_i += d1 * 0.1D;
            this.field_94240_i *= 0.8D;
            this.field_94239_h += this.field_94240_i;
            int i;

            for (i = (int)((this.field_94239_h + 1.0D) * (double)this.field_110976_a.size()) % this.field_110976_a.size(); i < 0; i = (i + this.field_110976_a.size()) % this.field_110976_a.size())
            {
                ;
            }

            if (i != this.field_110973_g)
            {
                this.field_110973_g = i;
                TextureUtil.func_110998_a((int[])this.field_110976_a.get(this.field_110973_g), this.field_130223_c, this.field_130224_d, this.field_110975_c, this.field_110974_d, false, false);
            }
        }
    }
}
