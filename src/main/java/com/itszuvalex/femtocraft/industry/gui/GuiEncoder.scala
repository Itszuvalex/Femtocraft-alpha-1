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
import com.itszuvalex.femtocraft.industry.containers.ContainerEncoder
import com.itszuvalex.femtocraft.industry.gui.GuiEncoder._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityEncoder
import com.itszuvalex.femtocraft.render.RenderUtils
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.util.{ResourceLocation, StatCollector}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.FluidRegistry
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT) object GuiEncoder {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/Encoder.png")
}

@SideOnly(Side.CLIENT) class GuiEncoder(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, private val encoder: TileEntityEncoder) extends GuiBase(new ContainerEncoder(player, par1InventoryPlayer, encoder)) {

  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    super.drawScreen(par1, par2, par3)
    if (isPointInRegion(18, 12, 16, 60, par1, par2)) {
      val furnaceCurrent = encoder.getCurrentPower
      val furnaceMax = encoder.getMaxPower
      val text = FemtocraftUtils.formatIntegerToString(furnaceCurrent) + '/' + FemtocraftUtils.formatIntegerToString(furnaceMax)
      drawCreativeTabHoveringText(text, par1, par2)
    }
    else if (isPointInRegion(150, 8, 16, 60, par1, par2)) {
      val massCurrent = encoder.getMassAmount
      val massMax = encoder.getMassCapacity
      val fluid = encoder.getTankInfo(ForgeDirection.UNKNOWN)(0).fluid
      val name = if (fluid == null) "" else " " + FluidRegistry.getFluidName(fluid)
      val text = FemtocraftUtils.formatIntegerToString(massCurrent) + '/' + FemtocraftUtils.formatIntegerToString(massMax) + " mB" + name
      drawCreativeTabHoveringText(text, par1, par2)
    }
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s = "Encoder"
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
    i1 = encoder.getProgressScaled(20)
    drawTexturedModalRect(k + 119, l + 25, 176, 0, 18, i1)
    i1 = (encoder.getFillPercentage * 60).toInt
    drawTexturedModalRect(k + 10, l + 68 - i1, 176, 100 - i1, 16, i1)
    val fluid = encoder.getTankInfo(ForgeDirection.UNKNOWN)(0).fluid
    if (fluid != null) {
      val image = fluid.getFluid.getStillIcon
      i1 = (encoder.getMassAmount * 60) / encoder.getMassCapacity
      RenderUtils.renderLiquidInGUI(this, zLevel, image, k + 150, l + 8 + (60 - i1), 16, i1)
      Minecraft.getMinecraft.getTextureManager.bindTexture(texture)
    }
    drawTexturedModalRect(k + 150, l + 8, 176, 100, 16, 60)
  }
}