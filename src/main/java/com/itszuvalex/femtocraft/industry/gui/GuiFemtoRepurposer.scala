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
import com.itszuvalex.femtocraft.industry.containers.ContainerFemtoRepurposer
import com.itszuvalex.femtocraft.industry.gui.GuiFemtoRepurposer._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityFemtoRepurposer
import com.itszuvalex.femtocraft.render.RenderUtils
import com.itszuvalex.femtocraft.utils.FemtocraftUtils._
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.util.{ResourceLocation, StatCollector}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.FluidRegistry
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT) object GuiFemtoRepurposer {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/FemtoRepurposer.png")
}

@SideOnly(Side.CLIENT) class GuiFemtoRepurposer(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, tileEntity: TileEntityFemtoRepurposer) extends GuiBase(new ContainerFemtoRepurposer(player, par1InventoryPlayer, tileEntity)) {
  private val repurposerInventory = tileEntity


  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    super.drawScreen(par1, par2, par3)
    if (isPointInRegion(10, 8, 16, 60, par1, par2)) {
      val furnaceCurrent = repurposerInventory.currentPower
      val furnaceMax = repurposerInventory.getMaxPower
      val text = formatIntegerToString(furnaceCurrent) + '/' + formatIntegerToString(furnaceMax)
      drawCreativeTabHoveringText(text, par1, par2)
    }
    else if (isPointInRegion(150, 8, 16, 60, par1, par2)) {
      val massCurrent = repurposerInventory.getMassAmount
      val massMax = repurposerInventory.getMassCapacity
      val fluid = repurposerInventory.getTankInfo(ForgeDirection.UNKNOWN)(0).fluid
      val name = if (fluid == null) "" else " " + FluidRegistry.getFluidName(fluid)
      val text = formatIntegerToString(massCurrent) + '/' + formatIntegerToString(massMax) + " mB" + name
      drawCreativeTabHoveringText(text, par1, par2)
    }
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s = "Femto Repurposer"
    fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, colorFromARGB(0, 255, 255, 255))
    fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, colorFromARGB(0, 255, 255, 255))
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
    i1 = repurposerInventory.getCookProgressScaled(26)
    drawTexturedModalRect(k + 60, l + 24, 176, 0, i1 + 1, 40)
    i1 = (repurposerInventory.currentPower * 60) / repurposerInventory.getMaxPower
    drawTexturedModalRect(k + 10, l + 8 + (60 - i1), 176, 40 + (60 - i1), 16, i1)
    val fluid = repurposerInventory.getTankInfo(ForgeDirection.UNKNOWN)(0).fluid
    if (fluid != null) {
      val image = fluid.getFluid.getStillIcon
      i1 = (repurposerInventory.getMassAmount * 60) / repurposerInventory.getMassCapacity
      RenderUtils.renderLiquidInGUI(this, zLevel, image, k + 150, l + 8 + (60 - i1), 16, i1)
      Minecraft.getMinecraft.getTextureManager.bindTexture(texture)
    }
    drawTexturedModalRect(k + 150, l + 8, 176, 100, 16, 60)
  }
}
