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
package com.itszuvalex.femtocraft.render

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.entities.fx.EntityFxPower
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.particle.EntityFX
import net.minecraft.client.renderer.Tessellator
import net.minecraft.util.{IIcon, ResourceLocation}
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._
import org.lwjgl.opengl.GL11

object RenderUtils {
  val MICRO_POWER_PARTICLE = "MicroPower"
  val NANO_POWER_PARTICLE  = "NanoPower"
  val FEMTO_POWER_PARTICLE = "FemtoPower"

  def renderCube(x: Float, y: Float, z: Float, startx: Float, starty: Float, startz: Float, endx: Float, endy: Float, endz: Float, texture: IIcon) {
    renderCube(x, y, z, startx, starty, startz, endx, endy, endz, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
  }

  def renderCube(x: Float, y: Float, z: Float, startx: Float, starty: Float, startz: Float, endx: Float, endy: Float, endz: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    drawTopFace(x, y, z, startx, endx, startz, endz, endy, texture, minU, maxU, minV, maxV)
    drawBottomFace(x, y, z, startx, endx, startz, endz, starty, texture, minU, maxU, minV, maxV)
    drawNorthFace(x, y, z, startx, endx, starty, endy, startz, texture, minU, maxU, minV, maxV)
    drawEastFace(x, y, z, starty, endy, startz, endz, endx, texture, minU, maxU, minV, maxV)
    drawSouthFace(x, y, z, startx, endx, starty, endy, endz, texture, minU, maxU, minV, maxV)
    drawWestFace(x, y, z, starty, endy, startz, endz, startx, texture, minU, maxU, minV, maxV)
  }

  def drawTopFace(x: Float, y: Float, z: Float, xmin: Float, xmax: Float, zmin: Float, zmax: Float, yoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tes = Tessellator.instance
    tes.addTranslation(x, y, z)
    tes.addVertexWithUV(xmin, yoffset, zmin, minU, maxV)
    tes.addVertexWithUV(xmin, yoffset, zmax, minU, minV)
    tes.addVertexWithUV(xmax, yoffset, zmax, maxU, minV)
    tes.addVertexWithUV(xmax, yoffset, zmin, maxU, maxV)
    tes.addTranslation(-x, -y, -z)
  }

  def drawBottomFace(x: Float, y: Float, z: Float, xmin: Float, xmax: Float, zmin: Float, zmax: Float, yoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tes = Tessellator.instance
    tes.addTranslation(x, y, z)
    tes.addVertexWithUV(xmin, yoffset, zmin, minU, maxV)
    tes.addVertexWithUV(xmax, yoffset, zmin, minU, minV)
    tes.addVertexWithUV(xmax, yoffset, zmax, maxU, minV)
    tes.addVertexWithUV(xmin, yoffset, zmax, maxU, maxV)
    tes.addTranslation(-x, -y, -z)
  }

  def drawNorthFace(x: Float, y: Float, z: Float, xmin: Float, xmax: Float, ymin: Float, ymax: Float, zoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tes = Tessellator.instance
    tes.addTranslation(x, y, z)
    tes.addVertexWithUV(xmin, ymin, zoffset, minU, maxV)
    tes.addVertexWithUV(xmin, ymax, zoffset, minU, minV)
    tes.addVertexWithUV(xmax, ymax, zoffset, maxU, minV)
    tes.addVertexWithUV(xmax, ymin, zoffset, maxU, maxV)
    tes.addTranslation(-x, -y, -z)
  }

  def drawEastFace(x: Float, y: Float, z: Float, ymin: Float, ymax: Float, zmin: Float, zmax: Float, xoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tes = Tessellator.instance
    tes.addTranslation(x, y, z)
    tes.addVertexWithUV(xoffset, ymin, zmin, minU, maxV)
    tes.addVertexWithUV(xoffset, ymax, zmin, minU, minV)
    tes.addVertexWithUV(xoffset, ymax, zmax, maxU, minV)
    tes.addVertexWithUV(xoffset, ymin, zmax, maxU, maxV)
    tes.addTranslation(-x, -y, -z)
  }

  def drawSouthFace(x: Float, y: Float, z: Float, xmin: Float, xmax: Float, ymin: Float, ymax: Float, zoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tes = Tessellator.instance
    tes.addTranslation(x, y, z)
    tes.addVertexWithUV(xmin, ymin, zoffset, minU, maxV)
    tes.addVertexWithUV(xmax, ymin, zoffset, minU, minV)
    tes.addVertexWithUV(xmax, ymax, zoffset, maxU, minV)
    tes.addVertexWithUV(xmin, ymax, zoffset, maxU, maxV)
    tes.addTranslation(-x, -y, -z)
  }

  def drawWestFace(x: Float, y: Float, z: Float, ymin: Float, ymax: Float, zmin: Float, zmax: Float, xoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tes = Tessellator.instance
    tes.addTranslation(x, y, z)
    tes.addVertexWithUV(xoffset, ymin, zmin, minU, maxV)
    tes.addVertexWithUV(xoffset, ymin, zmax, minU, minV)
    tes.addVertexWithUV(xoffset, ymax, zmax, maxU, minV)
    tes.addVertexWithUV(xoffset, ymax, zmin, maxU, maxV)
    tes.addTranslation(-x, -y, -z)
  }

  def renderDoubleSidedCube(x: Float, y: Float, z: Float, startx: Float, starty: Float, startz: Float, endx: Float, endy: Float, endz: Float, texture: IIcon) {
    drawTopFace(x, y, z, startx, endx, startz, endz, endy, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawBottomFace(x, y, z, startx, endx, startz, endz, endy, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawBottomFace(x, y, z, startx, endx, startz, endz, starty, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawTopFace(x, y, z, startx, endx, startz, endz, starty, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawNorthFace(x, y, z, startx, endx, starty, endy, startz, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawSouthFace(x, y, z, startx, endx, starty, endy, startz, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawEastFace(x, y, z, starty, endy, startz, endz, endx, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawWestFace(x, y, z, starty, endy, startz, endz, endx, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawSouthFace(x, y, z, startx, endx, starty, endy, endz, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawNorthFace(x, y, z, startx, endx, starty, endy, endz, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawWestFace(x, y, z, starty, endy, startz, endz, startx, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
    drawEastFace(x, y, z, starty, endy, startz, endz, startx, texture, texture.getMinU, texture.getMaxU, texture.getMinV, texture.getMaxV)
  }

  def makeTopFace(xmin: Float, xmax: Float, zmin: Float, zmax: Float, yoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float): RenderQuad = {
    val a = new RenderPoint
    val b = new RenderPoint
    val c = new RenderPoint
    val d = new RenderPoint
    a.y = {b.y = {c.y = {d.y = yoffset; d.y}; c.y}; b.y}
    a.x = {b.x = xmin; b.x}
    c.x = {d.x = xmax; d.x}
    a.z = {d.z = zmin; d.z}
    b.z = {c.z = zmax; c.z}
    new RenderQuad(a, b, c, d, texture, minU, maxU, minV, maxV)
  }

  def makeBottomFace(xmin: Float, xmax: Float, zmin: Float, zmax: Float, yoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float): RenderQuad = {
    val a = new RenderPoint
    val b = new RenderPoint
    val c = new RenderPoint
    val d = new RenderPoint
    a.y = {b.y = {c.y = {d.y = yoffset; d.y}; c.y}; b.y}
    a.x = {d.x = xmin; d.x}
    b.x = {c.x = xmax; c.x}
    a.z = {b.z = zmin; b.z}
    c.z = {d.z = zmax; d.z}
    new RenderQuad(a, b, c, d, texture, minU, maxU, minV, maxV)
  }

  def makeNorthFace(xmin: Float, xmax: Float, ymin: Float, ymax: Float, zoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float): RenderQuad = new RenderQuad(new RenderPoint(xmax, ymax, zoffset), new RenderPoint(xmax, ymin, zoffset), new RenderPoint(xmin, ymin, zoffset), new RenderPoint(xmin, ymax, zoffset), texture, minU, maxU, minV, maxV)

  def makeEastFace(ymin: Float, ymax: Float, zmin: Float, zmax: Float, xoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float): RenderQuad = new RenderQuad(new RenderPoint(xoffset, ymin, zmin), new RenderPoint(xoffset, ymax, zmin), new RenderPoint(xoffset, ymax, zmax), new RenderPoint(xoffset, ymin, zmax), texture, minU, maxU, minV, maxV)

  def makeWestFace(ymin: Float, ymax: Float, zmin: Float, zmax: Float, xoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float): RenderQuad = new RenderQuad(new RenderPoint(xoffset, ymin, zmin), new RenderPoint(xoffset, ymin, zmax), new RenderPoint(xoffset, ymax, zmax), new RenderPoint(xoffset, ymax, zmin), texture, minU, maxU, minV, maxV)

  def makeSouthFace(xmin: Float, xmax: Float, ymin: Float, ymax: Float, zoffset: Float, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float): RenderQuad = new RenderQuad(new RenderPoint(xmin, ymax, zoffset), new RenderPoint(xmin, ymin, zoffset), new RenderPoint(xmax, ymin, zoffset), new RenderPoint(xmax, ymax, zoffset), texture, minU, maxU, minV, maxV)

  def drawArbitraryFace(x: Float, y: Float, z: Float, xmin: Float, xmax: Float, ymin: Float, ymax: Float, zmin: Float, zmax: Float, normal: ForgeDirection, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    normal match {
      case UP => drawTopFace(x, y, z, xmin, xmax, zmin, zmax, ymax, texture, minU, maxU, minV, maxV)
      case DOWN => drawBottomFace(x, y, z, xmin, xmax, zmin, zmax, ymin, texture, minU, maxU, minV, maxV)
      case NORTH => drawNorthFace(x, y, z, xmin, xmax, ymin, ymax, zmin, texture, minU, maxU, minV, maxV)
      case EAST => drawEastFace(x, y, z, ymin, ymax, zmin, zmax, xmax, texture, minU, maxU, minV, maxV)
      case SOUTH => drawSouthFace(x, y, z, xmin, xmax, ymin, ymax, zmax, texture, minU, maxU, minV, maxV)
      case WEST => drawWestFace(x, y, z, ymin, ymax, zmin, zmax, xmin, texture, minU, maxU, minV, maxV)
      case _ =>
    }
  }

  def drawFaceByPoints(x: Float, y: Float, z: Float, A: RenderPoint, B: RenderPoint, C: RenderPoint, D: RenderPoint, texture: IIcon, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tes = Tessellator.instance
    tes.addTranslation(x, y, z)
    tes.addVertexWithUV(A.x, A.y, A.z, minU, maxV)
    tes.addVertexWithUV(B.x, B.y, B.z, minU, minV)
    tes.addVertexWithUV(C.x, C.y, C.z, maxU, minV)
    tes.addVertexWithUV(D.x, D.y, D.z, maxU, maxV)
    tes.addTranslation(-x, -y, -z)
  }

  def renderLiquidInGUI(container: GuiContainer, zheight: Float, icon: IIcon, x: Int, y: Int, width: Int, height: Int) {
    val man = Minecraft.getMinecraft.getTextureManager
    man.bindTexture(man.getResourceLocation(0))
    renderLiquidInGUI_height(container, zheight, icon, x, y, width, height)
  }

  private def renderLiquidInGUI_height(container: GuiContainer, zheight: Float, icon: IIcon, x: Int, y: Int, width: Int, height: Int) {
    var i = 0
    var remaining = height
    if ((height - width) > 0) {
      {
        i = 0
        while (width < remaining) {
          {
            drawTexturedModalSquareFromIcon(zheight, x, y + i, width, icon)
          }
          i += width
          remaining -= width
        }
      }
    }
    drawTexturedModalRectFromIcon(zheight, x, y + i, width, remaining, icon.getMinU, icon.getMaxU, icon.getMinV, icon.getInterpolatedV((remaining * 16f) / width))
  }

  private def drawTexturedModalSquareFromIcon(zheight: Float, x: Int, y: Int, size: Int, icon: IIcon) {
    drawTexturedModalRectFromIcon(zheight, x, y, size, size, icon.getMinU, icon.getMaxU, icon.getMinV, icon.getMaxV)
  }

  private def drawTexturedModalRectFromIcon(zheight: Float, x: Int, y: Int, width: Int, height: Int, minU: Float, maxU: Float, minV: Float, maxV: Float) {
    val tessellator = Tessellator.instance
    tessellator.startDrawingQuads()
    tessellator.addVertexWithUV(x.toDouble, (y + height).toDouble, zheight.toDouble, minU.toDouble, maxV.toDouble)
    tessellator.addVertexWithUV((x + width).toDouble, (y + height).toDouble, zheight.toDouble, maxU.toDouble, maxV.toDouble)
    tessellator.addVertexWithUV((x + width).toDouble, y.toDouble, zheight.toDouble, maxU.toDouble, minV.toDouble)
    tessellator.addVertexWithUV(x.toDouble, y.toDouble, zheight.toDouble, minU.toDouble, minV.toDouble)
    tessellator.draw
  }

  private def renderLiquidInGUI_width(container: GuiContainer, zheight: Float, icon: IIcon, x: Int, y: Int, width: Int, height: Int) {
    var i = 0
    var remaining = width
    if ((width - height) > 0) {
      {
        i = 0
        while (height < remaining) {
          {
            drawTexturedModalSquareFromIcon(zheight, x + i, y, height, icon)
          }
          i += height
          remaining -= height
        }
      }
    }
    drawTexturedModalRectFromIcon(zheight, x + i, y, remaining, height, icon.getMinU, icon.getInterpolatedV((remaining * 16f) / height), icon.getMinV, icon.getMaxV)
  }

  def drawLine(x1: Int, x2: Int, y1: Int, y2: Int, width: Int, color: Int) {
    val difX = (x2 - x1).toFloat
    val difY = (y2 - y1).toFloat
    val length = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2)).toFloat
    val xS = (width * difY / length) / 2f
    val yS = (width * difX / length) / 2f
    val alpha = (color >> 24 & 255).toFloat / 255.0F
    val red = (color >> 16 & 255).toFloat / 255.0F
    val green = (color >> 8 & 255).toFloat / 255.0F
    val blue = (color & 255).toFloat / 255.0F
    val tessellator = Tessellator.instance
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glColor4f(red, green, blue, alpha)
    tessellator.startDrawingQuads()
    tessellator.addVertex(x2.toDouble - xS, y2.toDouble + yS, 0.0D)
    tessellator.addVertex(x2.toDouble + xS, y2.toDouble - yS, 0.0D)
    tessellator.addVertex(x1.toDouble + xS, y1.toDouble - yS, 0.0D)
    tessellator.addVertex(x1.toDouble - xS, y1.toDouble + yS, 0.0D)
    tessellator.draw
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    GL11.glDisable(GL11.GL_BLEND)
  }

  var particleLocation: ResourceLocation = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/particles/particles.png")

  def spawnParticle(world: World, name: String, x: Double, y: Double, z: Double): EntityFX = {
    val mc = Minecraft.getMinecraft
    val deltaX = mc.renderViewEntity.posX - x
    val deltaY = mc.renderViewEntity.posY - y
    val deltaZ = mc.renderViewEntity.posZ - z
    val renderDistance = 16D
    var fx: EntityFX = null
    if ((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) > (renderDistance * renderDistance)) {
      return null
    }
    if (name == MICRO_POWER_PARTICLE) {
      fx = new EntityFxPower(world, x, y, z, .1f, .1f, 1.0f)
    }
    else if (name == NANO_POWER_PARTICLE) {
      fx = new EntityFxPower(world, x, y, z, .1f, 1.0f, .1f)
    }
    else if (name == FEMTO_POWER_PARTICLE) {
      fx = new EntityFxPower(world, x, y, z, 1f, .5f, .1f)
    }
    mc.effectRenderer.addEffect(fx)
    fx
  }
}

