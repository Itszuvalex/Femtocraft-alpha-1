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

import com.itszuvalex.femtocraft.render.RenderSimpleMachine._
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.world.IBlockAccess
import org.lwjgl.opengl.GL11

object RenderSimpleMachine {
  var renderID = 0
}

class RenderSimpleMachine extends ISimpleBlockRenderingHandler {
  def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {
    val metadata = 3
    val tessellator = Tessellator.instance
    block.setBlockBoundsForItemRender()
    renderer.setRenderBoundsFromBlock(block)
    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F)
    GL11.glTranslatef(-0.5F, -0.5F, -0.5F)
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, -1.0F, 0.0F)
    renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata))
    tessellator.draw
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, 1.0F, 0.0F)
    renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata))
    tessellator.draw
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, 0.0F, -1.0F)
    renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata))
    tessellator.draw
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, 0.0F, 1.0F)
    renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata))
    tessellator.draw
    tessellator.startDrawingQuads()
    tessellator.setNormal(-1.0F, 0.0F, 0.0F)
    renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata))
    tessellator.draw
    tessellator.startDrawingQuads()
    tessellator.setNormal(1.0F, 0.0F, 0.0F)
    renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata))
    tessellator.draw
    GL11.glTranslatef(0.5F, 0.5F, 0.5F)
  }

  def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    renderer.renderStandardBlock(block, x, y, z)
    true
  }

  def shouldRender3DInInventory(id: Int) = true

  def getRenderId = renderID
}
