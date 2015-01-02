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
package com.itszuvalex.femtocraft.fx

import com.itszuvalex.femtocraft.render.RenderUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.EntityFX
import net.minecraft.client.particle.EntityFX.{interpPosX, interpPosY, interpPosZ}
import net.minecraft.client.renderer.Tessellator
import net.minecraft.world.World
import org.lwjgl.opengl.GL11._

/**
 * Created by Christopher Harris (Itszuvalex) on 9/25/14.
 */
@SideOnly(Side.CLIENT)
class EntityFxPower(par1World: World, x: Double, y: Double, z: Double, scale: Float, red: Float, green: Float, blue: Float) extends EntityFX(par1World, x, y, z, 0.0D, 0.0D, 0.0D) {
  private var powerParticleScale = 0f

  def this(par1World: World, x: Double, y: Double, z: Double, red: Float, green: Float, blue: Float) =
    this(par1World, x, y, z, 1.0F, red, green, blue)

  {
    this.motionX *= 0.10000000149011612D
    this.motionY *= 0.10000000149011612D
    this.motionZ *= 0.10000000149011612D
    this.particleTextureIndexX = 0
    this.particleTextureIndexY = 0
    val f4 = Math.random.toFloat * 0.4F + 0.6F
    this.particleRed = ((Math.random * 0.20000000298023224D).toFloat + 0.8F) * red * f4
    this.particleGreen = ((Math.random * 0.20000000298023224D).toFloat + 0.8F) * green * f4
    this.particleBlue = ((Math.random * 0.20000000298023224D).toFloat + 0.8F) * blue * f4
    this.particleScale *= 0.75F
    this.particleScale *= scale
    this.powerParticleScale = this.particleScale
    this.particleMaxAge = (8.0D / (Math.random * 0.8D + 0.2D)).toInt
    this.particleMaxAge = (this.particleMaxAge.toFloat * scale).toInt
    this.noClip = false
  }

  override def getFXLayer = 3

  override def renderParticle(par1Tessellator: Tessellator, par2: Float, par3: Float, par4: Float, par5: Float, par6: Float, par7: Float) {
    Minecraft.getMinecraft.getTextureManager.bindTexture(RenderUtils.particleLocation)
    var scale = (this.particleAge.toFloat + par2) / this.particleMaxAge.toFloat * 32.0F
    if (scale < 0.0F) {
      scale = 0.0F
    }
    if (scale > 1.0F) {
      scale = 1.0F
    }
    this.particleScale = this.powerParticleScale * scale
    glDepthMask(false)
    glEnable(GL_BLEND)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    glAlphaFunc(GL_GREATER, 0.003921569f)
    val f6 = this.particleTextureIndexX.toFloat / 16.0F
    val f7 = f6 + 0.0624375F
    val f8 = this.particleTextureIndexY.toFloat / 16.0F
    val f9 = f8 + 0.0624375F
    val f10 = 0.1F * this.particleScale
    val f11 = (this.prevPosX + (this.posX - this.prevPosX) * par2.toDouble - interpPosX).toFloat
    val f12 = (this.prevPosY + (this.posY - this.prevPosY) * par2.toDouble - interpPosY).toFloat
    val f13 = (this.prevPosZ + (this.posZ - this.prevPosZ) * par2.toDouble - interpPosZ).toFloat
    val f14 = 1.0F
    par1Tessellator.startDrawingQuads()
    par1Tessellator.setBrightness(getBrightnessForRender(par2))
    par1Tessellator.setColorRGBA_F(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
    par1Tessellator.addVertexWithUV((f11 - par3 * f10 - par6 * f10).toDouble, (f12 - par4 * f10).toDouble, (f13 - par5 * f10 - par7 * f10).toDouble, f7.toDouble, f9.toDouble)
    par1Tessellator.addVertexWithUV((f11 - par3 * f10 + par6 * f10).toDouble, (f12 + par4 * f10).toDouble, (f13 - par5 * f10 + par7 * f10).toDouble, f7.toDouble, f8.toDouble)
    par1Tessellator.addVertexWithUV((f11 + par3 * f10 + par6 * f10).toDouble, (f12 + par4 * f10).toDouble, (f13 + par5 * f10 + par7 * f10).toDouble, f6.toDouble, f8.toDouble)
    par1Tessellator.addVertexWithUV((f11 + par3 * f10 - par6 * f10).toDouble, (f12 - par4 * f10).toDouble, (f13 + par5 * f10 - par7 * f10).toDouble, f6.toDouble, f9.toDouble)
    par1Tessellator.draw
    glDisable(GL_BLEND)
    glDepthMask(true)
    glAlphaFunc(GL_GREATER, 0.1f)
  }

  /**
   * Called to update the entity's position/logic.
   */
  override def onUpdate {
    this.prevPosX = this.posX
    this.prevPosY = this.posY
    this.prevPosZ = this.posZ
    if (({
      this.particleAge += 1;
      this.particleAge - 1
    }) >= this.particleMaxAge) {
      this.setDead
    }
    this.setParticleTextureIndex(this.particleAge * 8 / this.particleMaxAge)
    this.moveEntity(this.motionX, this.motionY, this.motionZ)
    if (this.posY == this.prevPosY) {
      this.motionX *= 1.1D
      this.motionZ *= 1.1D
    }
    this.motionX *= 0.9599999785423279D
    this.motionY *= 0.9599999785423279D
    this.motionZ *= 0.9599999785423279D
    if (this.onGround) {
      this.motionX *= 0.699999988079071D
      this.motionZ *= 0.699999988079071D
    }
  }

  override def setParticleTextureIndex(par1: Int) {
    this.particleTextureIndexX = par1 % 16
    this.particleTextureIndexY = par1 / 16
  }
}

