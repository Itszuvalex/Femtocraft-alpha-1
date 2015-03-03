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
package com.itszuvalex.femtocraft.power.render

import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import com.itszuvalex.femtocraft.industry.LaserRegistry
import com.itszuvalex.femtocraft.industry.blocks.BlockLaser
import com.itszuvalex.femtocraft.industry.tiles.TileEntityLaser
import com.itszuvalex.femtocraft.proxy.ProxyClient
import com.itszuvalex.femtocraft.render.{RenderModel, RenderPoint, RenderQuad}
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.util.ForgeDirection
import org.lwjgl.opengl.GL11

class RenderLaser extends ISimpleBlockRenderingHandler {
  private var segmentUpDown    : RenderModel = null
  private var segmentNorthSouth: RenderModel = null
  private var segmentEastWest  : RenderModel = null


  def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {
    block match {
      case laser: BlockLaser =>
        val tessellator: Tessellator = Tessellator.instance
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F)
        tessellator.startDrawingQuads()
        tessellator.setColorOpaque_F(1, 1, 1)
        renderLaser(laser, 0, 0, 0, ForgeDirection.WEST)
        tessellator.draw
        GL11.glTranslatef(0.5F, 0.5F, 0.5F)
      case _ =>
    }
  }

  def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    block match {
      case laser: BlockLaser =>
        world.getTileEntity(x, y, z) match {
          case laserTile: TileEntityLaser =>
            val tessellator: Tessellator = Tessellator.instance
            tessellator.setBrightness(15 << 20 | 15 << 4)
            val (a, r, g, b) = FemtocraftUtils.ARGBFromColor(LaserRegistry.getColor(laserTile.getModulation))
            tessellator.setColorRGBA(r, g, b, a)
            renderLaser(laser, x, y, z, laserTile.getDirection)
            true
          case _ => false
        }
      case _ => false
    }
  }

  override def shouldRender3DInInventory(modelID: Int) = true

  override def getRenderId = ProxyClient.FemtocraftLaserRenderID

  private def renderLaser(coil: BlockLaser, x: Int, y: Int, z: Int, dir: ForgeDirection) {
    if (segmentUpDown == null) {
      createLaser(coil)
    }
    var model = segmentUpDown
    dir match {
      //      case ForgeDirection.UP | ForgeDirection.DOWN =>
      case ForgeDirection.EAST | ForgeDirection.WEST => model = segmentEastWest
      case ForgeDirection.NORTH | ForgeDirection.SOUTH => model = segmentNorthSouth
      case _ =>
    }
    model.location = new RenderPoint(x, y, z)
    model.draw()
  }

  private def createLaser(laser: BlockLaser) {
    segmentUpDown = new RenderModel
    val minY = 0f
    val maxY = 1f
    val min = 7f / 16f
    val max = 9f / 16f
    val minU = laser.laserIcon.getInterpolatedU(7f)
    val maxU = laser.laserIcon.getInterpolatedU(9f)
    val minV = laser.laserIcon.getMinV
    val maxV = laser.laserIcon.getMaxV
    val connectorNorth = new RenderQuad(new RenderPoint(min, minY, min), new RenderPoint(min, maxY, min), new RenderPoint(max, maxY, min), new RenderPoint(max, minY, min), laser.laserIcon, minU, maxU, minV, maxV)
    val connectorSouth = new RenderQuad(new RenderPoint(max, minY, max), new RenderPoint(max, maxY, max), new RenderPoint(min, maxY, max), new RenderPoint(min, minY, max), laser.laserIcon, minU, maxU, minV, maxV)
    val connectorEast = new RenderQuad(new RenderPoint(max, minY, min), new RenderPoint(max, maxY, min), new RenderPoint(max, maxY, max), new RenderPoint(max, minY, max), laser.laserIcon, minU, maxU, minV, maxV)
    val connectorWest = new RenderQuad(new RenderPoint(min, minY, max), new RenderPoint(min, maxY, max), new RenderPoint(min, maxY, min), new RenderPoint(min, minY, min), laser.laserIcon, minU, maxU, minV, maxV)
    segmentUpDown.addQuad(connectorNorth)
    segmentUpDown.addQuad(connectorSouth)
    segmentUpDown.addQuad(connectorEast)
    segmentUpDown.addQuad(connectorWest)
    val top: RenderQuad = new RenderQuad(new RenderPoint(min, maxY, min), new RenderPoint(min, maxY, max), new RenderPoint(max, maxY, max), new RenderPoint(max, maxY, min), laser.laserIcon, minU, maxU, laser.laserIcon.getInterpolatedV(7f), laser.laserIcon.getInterpolatedV(9f))
    val bot: RenderQuad = new RenderQuad(new RenderPoint(max, minY, min), new RenderPoint(max, minY, max), new RenderPoint(min, minY, max), new RenderPoint(min, minY, min), laser.laserIcon, minU, maxU, laser.laserIcon.getInterpolatedV(7f), laser.laserIcon.getInterpolatedV(9f))
    segmentUpDown.addQuad(top)
    segmentUpDown.addQuad(bot)

    segmentNorthSouth = segmentUpDown.rotatedOnXAxis(90d * Math.PI / 180d, .5f, .5f)
    segmentEastWest = segmentUpDown.rotatedOnZAxis(90d * Math.PI / 180d, .5f, .5f)

  }
}
