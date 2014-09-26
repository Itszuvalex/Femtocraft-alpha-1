/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.entities.fx;

import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import static org.lwjgl.opengl.GL11.*;


/**
 * Created by Christopher Harris (Itszuvalex) on 9/25/14.
 */
@SideOnly(Side.CLIENT)
public class EntityFxPower extends EntityFX {
    float powerParticleScale;

    public EntityFxPower(World par1World, double x, double y, double z, float red, float green, float blue) {
        this(par1World, x, y, z, 1.0F, red, green, blue);
    }

    public EntityFxPower(World par1World, double x, double y, double z, float scale, float red, float green,
                         float blue) {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;

        this.particleTextureIndexX = 0;
        this.particleTextureIndexY = 0;

        float f4 = (float) Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * red * f4;
        this.particleGreen = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * green * f4;
        this.particleBlue = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * blue * f4;
        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.powerParticleScale = this.particleScale;
        this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        this.particleMaxAge = (int) ((float) this.particleMaxAge * scale);
        this.noClip = false;
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5,
                               float par6, float par7) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(RenderUtils.particleLocation);
        float scale = ((float) this.particleAge + par2) / (float) this.particleMaxAge * 32.0F;

        if (scale < 0.0F) {
            scale = 0.0F;
        }

        if (scale > 1.0F) {
            scale = 1.0F;
        }

        this.particleScale = this.powerParticleScale * scale;

        glDepthMask(false);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glAlphaFunc(GL_GREATER, 0.003921569f);
        float f6 = (float) this.particleTextureIndexX / 16.0F;
        float f7 = f6 + 0.0624375F;
        float f8 = (float) this.particleTextureIndexY / 16.0F;
        float f9 = f8 + 0.0624375F;
        float f10 = 0.1F * this.particleScale;

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) par2 - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) par2 - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) par2 - interpPosZ);
        float f14 = 1.0F;
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setBrightness(getBrightnessForRender(par2));
        par1Tessellator.setColorRGBA_F(
                this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha);
        par1Tessellator.addVertexWithUV((double) (f11 - par3 * f10 - par6 * f10), (double) (f12 -
                                                                                            par4 * f10), (double) (f13 -
                                                                                                                   par5 *
                                                                                                                   f10 -
                                                                                                                   par7 *
                                                                                                                   f10), (double) f7, (double) f9);
        par1Tessellator.addVertexWithUV((double) (f11 - par3 * f10 + par6 * f10), (double) (f12 +
                                                                                            par4 * f10), (double) (
                f13 - par5 * f10 + par7 * f10), (double) f7, (double) f8);
        par1Tessellator.addVertexWithUV((double) (f11 + par3 * f10 + par6 * f10), (double) (f12 +
                                                                                            par4 * f10), (double) (f13 +
                                                                                                                   par5 *
                                                                                                                   f10 +
                                                                                                                   par7 *
                                                                                                                   f10), (double) f6, (double) f8);
        par1Tessellator.addVertexWithUV((double) (f11 + par3 * f10 - par6 * f10), (double) (f12 -
                                                                                            par4 * f10), (double) (
                f13 + par5 * f10 - par7 * f10), (double) f6, (double) f9);
        par1Tessellator.draw();
        glDisable(GL_BLEND);
        glDepthMask(true);
        glAlphaFunc(GL_GREATER, 0.1f);
    }

    @Override
    public void setParticleTextureIndex(int par1) {
        this.particleTextureIndexX = par1 % 16;
        this.particleTextureIndexY = par1 / 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.setParticleTextureIndex(this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
}

