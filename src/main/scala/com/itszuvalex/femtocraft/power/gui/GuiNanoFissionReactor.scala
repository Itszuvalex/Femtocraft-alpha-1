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
import com.itszuvalex.femtocraft.power.containers.ContainerNanoFissionReactor
import com.itszuvalex.femtocraft.power.gui.GuiNanoFissionReactor._
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore
import com.itszuvalex.femtocraft.render.RenderUtils
import com.itszuvalex.femtocraft.sound.FemtocraftSoundUtils
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.util.{IIcon, ResourceLocation, StatCollector}
import net.minecraftforge.fluids.{FluidRegistry, FluidStack, IFluidTank}
import org.lwjgl.opengl.GL11

object GuiNanoFissionReactor {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/NanoFissionReactor.png")
  private val decrementButtonX      = 111
  private val decrementButtonWidth  = 117 - decrementButtonX
  private val decrementButtonY      = 61
  private val decrementButtonHeight = 67 - decrementButtonY
  private val incrementButtonX      = 122
  private val incrementButtonWidth  = 128 - incrementButtonX
  private val incrementButtonY      = 61
  private val incrementButtonHeight = 67 - incrementButtonY
  private val abortButtonX          = 37
  private val abortButtonWidth      = 108 - abortButtonX
  private val abortButtonY          = 67
  private val abortButtonHeight     = 75 - abortButtonY
}

class GuiNanoFissionReactor(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, private val reactor: TileEntityNanoFissionReactorCore) extends GuiBase(new ContainerNanoFissionReactor(player, par1InventoryPlayer, reactor)) {

  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    super.drawScreen(par1, par2, par3)
    val k: Int = (this.width - this.xSize) / 2
    val l: Int = (this.height - this.ySize) / 2
    if ((par1 >= (k + incrementButtonX)) && (par1 <= (k + incrementButtonX + incrementButtonWidth)) && (par2 >= (l + incrementButtonY)) && (par2 <= (l + incrementButtonY + incrementButtonHeight))) {
      this.drawGradientRect(k + incrementButtonX, l + incrementButtonY, k + incrementButtonWidth + incrementButtonX, l + incrementButtonY + incrementButtonHeight, FemtocraftUtils.colorFromARGB(60, 45, 0, 110), FemtocraftUtils.colorFromARGB(60, 45, 0, 110))
    }
    if ((par1 >= (k + decrementButtonX)) && (par1 <= (k + decrementButtonX + decrementButtonWidth)) && (par2 >= (l + decrementButtonY)) && (par2 <= (l + decrementButtonY + decrementButtonHeight))) {
      this.drawGradientRect(k + decrementButtonX, l + decrementButtonY, k + decrementButtonWidth + decrementButtonX, l + decrementButtonY + decrementButtonHeight, FemtocraftUtils.colorFromARGB(60, 45, 0, 110), FemtocraftUtils.colorFromARGB(60, 45, 0, 110))
    }
    if ((par1 >= (k + abortButtonX)) && (par1 <= (k + abortButtonX + abortButtonWidth)) && (par2 >= (l + abortButtonY)) && (par2 <= (l + abortButtonY + abortButtonHeight))) {
      this.drawGradientRect(k + abortButtonX, l + abortButtonY, k + abortButtonWidth + abortButtonX, l + abortButtonY + abortButtonHeight, FemtocraftUtils.colorFromARGB(60, 230, 230, 0), FemtocraftUtils.colorFromARGB(60, 230, 230, 0))
    }
    if (this.isPointInRegion(8, 28, 16, 46, par1, par2)) {
      val temperatureCurrent: Int = this.reactor.getTemperatureCurrent.toInt
      val temperatureMax: Int = this.reactor.getTemperatureMax
      this.drawCreativeTabHoveringText("" + temperatureCurrent + '/' + temperatureMax + " TU", par1, par2)
    }
    else if (this.isPointInRegion(134, 8, 16, 60, par1, par2)) {
      val cooledSaltAmount: Int = this.reactor.getCooledSaltAmount
      val cooledSaltMax: Int = this.reactor.getCooledSaltTank.getCapacity
      val fluid: FluidStack = this.reactor.getCooledSaltTank.getFluid
      val name: String = if (fluid == null) "" else " " + FluidRegistry.getFluidName(fluid)
      val text: String = FemtocraftUtils.formatIntegerToString(cooledSaltAmount) + '/' + FemtocraftUtils.formatIntegerToString(cooledSaltMax) + " mB" + name
      this.drawCreativeTabHoveringText(text, par1, par2)
    }
    else if (this.isPointInRegion(152, 8, 16, 60, par1, par2)) {
      val moltenSaltAmount: Int = this.reactor.getMoltenSaltAmount
      val moltenSaltMax: Int = this.reactor.getMoltenSaltTank.getCapacity
      val fluid: FluidStack = this.reactor.getMoltenSaltTank.getFluid
      val name: String = if (fluid == null) "" else " " + FluidRegistry.getFluidName(fluid)
      val text: String = FemtocraftUtils.formatIntegerToString(moltenSaltAmount) + '/' + FemtocraftUtils.formatIntegerToString(moltenSaltMax) + " mB" + name
      this.drawCreativeTabHoveringText(text, par1, par2)
    }
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s: String = "Fission Reactor"
    this.fontRendererObj.drawString(s, (110 + 25) / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 76, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    this.fontRendererObj.drawString("-", 112, 61, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    this.fontRendererObj.drawString("+", 123, 61, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    val abort: String = "ABORT"
    this.fontRendererObj.drawString(abort, (abortButtonX + abortButtonWidth / 2) - this.fontRendererObj.getStringWidth(abort) / 2, 68, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    val concentrationTarget: String = "%2.1f".format(reactor.getThoriumConcentrationTarget * 100f) + "%"
    this.fontRendererObj.drawString(concentrationTarget, 112 + (127 - 112) / 2 - this.fontRendererObj.getStringWidth(concentrationTarget) / 2, 48 + (58 - 48) / 2 - this.fontRendererObj.FONT_HEIGHT / 2, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    val currentConcentration: String = "%2.1f".format(reactor.getThoriumConcentration * 100f) + "%"
    this.fontRendererObj.drawString(currentConcentration, 90 + (104 - 90) / 2 - this.fontRendererObj.getStringWidth(currentConcentration) / 2, 51 + (61 - 51) / 2 - this.fontRendererObj.FONT_HEIGHT / 2, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    this.fontRendererObj.drawString("Thorium", 29, 51, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
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
    var i1: Int = 0
    reactor.getState match {
      case TileEntityNanoFissionReactorCore.ReactorState.ACTIVE   => drawTexturedModalRect(k + 30, l + 70, 176, 6, 3, 3)
      case TileEntityNanoFissionReactorCore.ReactorState.UNSTABLE => drawTexturedModalRect(k + 30, l + 70, 176, 3, 3, 3)
      case TileEntityNanoFissionReactorCore.ReactorState.CRITICAL => drawTexturedModalRect(k + 30, l + 70, 176, 0, 3, 3)
      case _                                                      =>
    }
    val heatHeight: Int = 46
    i1 = (Math.min(this.reactor.getTemperatureCurrent / this.reactor.getTemperatureMax.toFloat, 1f) * heatHeight).toInt
    this.drawTexturedModalRect(k + 7, l + 27 + (heatHeight - i1), 176, 69, 8, 3)
    renderTank(this.reactor.getCooledSaltTank, 134, 8, k, l)
    renderTank(this.reactor.getMoltenSaltTank, 152, 8, k, l)
    this.drawTexturedModalRect(k + 134, l + 8, 176, 9, 16, 60)
    this.drawTexturedModalRect(k + 152, l + 8, 176, 9, 16, 60)
  }

  private def renderTank(tank: IFluidTank, x: Int, y: Int, k: Int, l: Int) {
    val fluid: FluidStack = tank.getFluid
    if (fluid != null) {
      val image: IIcon = fluid.getFluid.getStillIcon
      val i1: Int = (fluid.amount * 59) / tank.getCapacity
      RenderUtils.renderLiquidInGUI(this, this.zLevel, image, k + x, l + y + (59 - i1), 16, i1)
      Minecraft.getMinecraft.getTextureManager.bindTexture(texture)
    }
  }

  protected override def mouseClicked(par1: Int, par2: Int, par3: Int) {
    if (par3 == 0) {
      val k: Int = (this.width - this.xSize) / 2
      val l: Int = (this.height - this.ySize) / 2
      if ((par1 >= (k + incrementButtonX)) && (par1 <= (k + incrementButtonX + incrementButtonWidth)) && (par2 >= (l + incrementButtonY)) && (par2 <= (l + incrementButtonY + incrementButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        reactor.onIncrementClick()
      }
      if ((par1 >= (k + decrementButtonX)) && (par1 <= (k + decrementButtonX + decrementButtonWidth)) && (par2 >= (l + decrementButtonY)) && (par2 <= (l + decrementButtonY + decrementButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        reactor.onDecrementClick()
      }
      if ((par1 >= (k + abortButtonX)) && (par1 <= (k + abortButtonX + abortButtonWidth)) && (par2 >= (l + abortButtonY)) && (par2 <= (l + abortButtonY + abortButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        reactor.onAbortClick()
      }
    }
    super.mouseClicked(par1, par2, par3)
  }
}

