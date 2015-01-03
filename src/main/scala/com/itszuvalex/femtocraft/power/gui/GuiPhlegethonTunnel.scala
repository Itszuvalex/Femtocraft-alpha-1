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
package com.itszuvalex.femtocraft.power.gui

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.gui.GuiBase
import com.itszuvalex.femtocraft.power.containers.ContainerPhlegethonTunnel
import com.itszuvalex.femtocraft.power.gui.GuiPhlegethonTunnel._
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore
import com.itszuvalex.femtocraft.sound.FemtocraftSoundUtils
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.util.{EnumChatFormatting, ResourceLocation, StatCollector}
import org.lwjgl.opengl.GL11

object GuiPhlegethonTunnel {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/PhlegethonTunnel.png")
  private val activateButtonX        = 14
  private val activateButtonWidth    = 44 - activateButtonX
  private val activateButtonY        = 56
  private val activateButtonHeight   = 68 - activateButtonY
  private val deactivateButtonX      = 133
  private val deactivateButtonWidth  = 162 - deactivateButtonX
  private val deactivateButtonY      = 56
  private val deactivateButtonHeight = 68 - deactivateButtonY
}

class GuiPhlegethonTunnel(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, private val tunnel: TileEntityPhlegethonTunnelCore) extends GuiBase(new ContainerPhlegethonTunnel(player, par1InventoryPlayer, tunnel)) {

  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    super.drawScreen(par1, par2, par3)
    val k: Int = (this.width - this.xSize) / 2
    val l: Int = (this.height - this.ySize) / 2
    if (!tunnel.isActive && (par1 >= (k + activateButtonX)) && (par1 <= (k + activateButtonX + activateButtonWidth)) && (par2 >= (l + activateButtonY)) && (par2 <= (l + activateButtonY + activateButtonHeight))) {
      this.drawGradientRect(k + activateButtonX, l + activateButtonY, k + activateButtonWidth + activateButtonX, l + activateButtonY + activateButtonHeight, FemtocraftUtils.colorFromARGB(60, 45, 0, 110), FemtocraftUtils.colorFromARGB(60, 45, 0, 110))
    }
    if (tunnel.isActive && (par1 >= (k + deactivateButtonX)) && (par1 <= (k + deactivateButtonX + deactivateButtonWidth)) && (par2 >= (l + deactivateButtonY)) && (par2 <= (l + deactivateButtonY + deactivateButtonHeight))) {
      this.drawGradientRect(k + deactivateButtonX, l + deactivateButtonY, k + deactivateButtonWidth + deactivateButtonX, l + deactivateButtonY + deactivateButtonHeight, FemtocraftUtils.colorFromARGB(60, 45, 0, 110), FemtocraftUtils.colorFromARGB(60, 45, 0, 110))
    }
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s: String = "Phlegethon               Tunnel"
    this.fontRendererObj.drawString(s, (xSize - this.fontRendererObj.getStringWidth(s)) / 2, 6, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 76, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    if (!tunnel.isActive) {
      val activate: String = "Open"
      this.fontRendererObj.drawString(activate, (activateButtonX + activateButtonWidth / 2) - this.fontRendererObj.getStringWidth(activate) / 2, activateButtonY + (activateButtonHeight - fontRendererObj.FONT_HEIGHT) / 2, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    }
    else {
      val deactivate: String = "Close"
      this.fontRendererObj.drawString(deactivate, (deactivateButtonX + deactivateButtonWidth / 2) - this.fontRendererObj.getStringWidth(deactivate) / 2, deactivateButtonY + (deactivateButtonHeight - fontRendererObj.FONT_HEIGHT) / 2, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    }
    fontRendererObj.drawSplitString(EnumChatFormatting.WHITE + FemtocraftUtils.formatIntegerString(String.valueOf(tunnel.getCurrentPower)) + "/\n" + FemtocraftUtils.formatIntegerString(String.valueOf(tunnel.getMaxPower)) + " OP", 126, 16, 40, 40)
  }

  /**
   * Draw the background layer for the GuiContainer (everything behind the items)
   */
  protected def drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    Minecraft.getMinecraft.getTextureManager.bindTexture(texture)
    val k: Int = (this.width - this.xSize) / 2
    val l: Int = (this.height - this.ySize) / 2
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize)
    val power: Int = tunnel.getCurrentPower * 80
    val max: Int = tunnel.getMaxPower
    val i1: Int = if ((power > 0) && (max > 0)) power / max else 0
    this.drawTexturedModalRect(k + 52, l + 3 + (80 - i1), 176, 80 - i1, 70, i1)
  }

  protected override def mouseClicked(par1: Int, par2: Int, par3: Int) {
    if (par3 == 0) {
      val k: Int = (this.width - this.xSize) / 2
      val l: Int = (this.height - this.ySize) / 2
      if (!tunnel.isActive && (par1 >= (k + activateButtonX)) && (par1 <= (k + activateButtonX + activateButtonWidth)) && (par2 >= (l + activateButtonY)) && (par2 <= (l + activateButtonY + activateButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        tunnel.onActivateClick()
      }
      if (tunnel.isActive && (par1 >= (k + deactivateButtonX)) && (par1 <= (k + deactivateButtonX + deactivateButtonWidth)) && (par2 >= (l + deactivateButtonY)) && (par2 <= (l + deactivateButtonY + deactivateButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        tunnel.onDeactivateClick()
      }
      super.mouseClicked(par1, par2, par3)
    }
  }
}

