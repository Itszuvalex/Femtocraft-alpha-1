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
package com.itszuvalex.femtocraft.industry.gui

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.gui.GuiBase
import com.itszuvalex.femtocraft.industry.containers.ContainerFemtoChronoshifter
import com.itszuvalex.femtocraft.industry.gui.GuiFemtoChronoshifter._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityFemtoChronoshifter
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.util.{ResourceLocation, StatCollector}
import org.lwjgl.opengl.GL11

object GuiFemtoChronoshifter {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/FemtoChronoshifter.png")
}

class GuiFemtoChronoshifter(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, private var inventory: TileEntityFemtoChronoshifter) extends GuiBase(new ContainerFemtoChronoshifter(player, par1InventoryPlayer, inventory)) {

  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    super.drawScreen(par1, par2, par3)
    val furnaceCurrent = inventory.getCurrentPower
    val furnaceMax = inventory.getMaxPower
    val text = FemtocraftUtils.formatIntegerToString(furnaceCurrent) + '/' + FemtocraftUtils.formatIntegerToString(furnaceMax) + " OP"
    if (isPointInRegion(18, 12, 16, 60, par1, par2)) {
      drawCreativeTabHoveringText(text, par1, par2)
    }
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s = "Femto Chronoshifter"
    fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
  }

  /**
   * Draw the background layer for the GuiContainer (everything behind the items)
   */
  protected def drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    Minecraft.getMinecraft.getTextureManager.bindTexture(texture)
    val k = (width - xSize) / 2
    val l = (height - ySize) / 2
    drawTexturedModalRect(k, l, 0, 0, xSize, ySize)
    var i1 = 0
    i1 = inventory.getCookProgressScaled(86)
    drawTexturedModalRect(k + 51, l + 39, 0, 167, i1, 8)
    i1 = (inventory.getCurrentPower * 60) / inventory.getMaxPower
    drawTexturedModalRect(k + 12, l + 11 + (60 - i1), 176, 34 + (60 - i1), 16 + (60 - i1), 60)
  }
}
