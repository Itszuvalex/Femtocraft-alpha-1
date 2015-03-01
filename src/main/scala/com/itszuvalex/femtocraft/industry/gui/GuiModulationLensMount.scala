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
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import com.itszuvalex.femtocraft.core.gui.GuiBase
import com.itszuvalex.femtocraft.industry.containers.ContainerModulationLensMount
import com.itszuvalex.femtocraft.industry.gui.GuiModulationLensMount._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityModulationLensMount
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.util.{ResourceLocation, StatCollector}
import org.lwjgl.opengl.GL11

object GuiModulationLensMount {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/GuiModulationLensMount.png")
}

@SideOnly(Side.CLIENT) class GuiModulationLensMount(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, lensMount: TileEntityModulationLensMount) extends GuiBase(new ContainerModulationLensMount[TileEntityModulationLensMount](player, par1InventoryPlayer, lensMount)) {

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s = "Modulation Lens Mount"
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
  }
}
