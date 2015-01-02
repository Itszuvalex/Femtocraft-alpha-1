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
import com.itszuvalex.femtocraft.industry.containers.ContainerNanoFabricator
import com.itszuvalex.femtocraft.industry.tiles.TileEntityNanoFabricator
import com.itszuvalex.femtocraft.render.RenderUtils
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.util.{ResourceLocation, StatCollector}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.FluidRegistry
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT) object GuiNanoFabricator {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/NanoFabricator.png")
}

@SideOnly(Side.CLIENT) class GuiNanoFabricator(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, tileEntity: TileEntityNanoFabricator) extends GuiBase(new ContainerNanoFabricator(player, par1InventoryPlayer, tileEntity)) {
  private val fabricatorInventory = tileEntity

  ySize = 204

  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    super.drawScreen(par1, par2, par3)
    if (isPointInRegion(10, 8, 16, 60, par1, par2)) {
      val furnaceCurrent = fabricatorInventory.currentPower
      val furnaceMax = fabricatorInventory.getMaxPower
      val text = FemtocraftUtils.formatIntegerToString(furnaceCurrent) + '/' + FemtocraftUtils.formatIntegerToString(furnaceMax) + " OP"
      drawCreativeTabHoveringText(text, par1, par2)
    }
    else if (isPointInRegion(150, 8, 16, 60, par1, par2)) {
      val massCurrent = fabricatorInventory.getMassAmount
      val massMax = fabricatorInventory.getMassCapacity
      val fluid = fabricatorInventory.getTankInfo(ForgeDirection.UNKNOWN)(0).fluid
      val name = if (fluid == null) "" else " " + FemtocraftUtils.capitalize(FluidRegistry.getFluidName(fluid))
      val text = FemtocraftUtils.formatIntegerToString(massCurrent) + '/' + FemtocraftUtils.formatIntegerToString(massMax) + " mB" + name
      drawCreativeTabHoveringText(text, par1, par2)
    }
    else if (isPointInRegion(94, 54, 16, 16, par1, par2)) {
      if (fabricatorInventory.getStackInSlot(10) == null) {
        drawCreativeTabHoveringText("Assembly Schematic", par1, par2)
      }
    }
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s = "Nano Fabricator"
    fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 5, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 4, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
  }

  /**
   * Draw the background layer for the GuiContainer (everything behind the items)
   */
  protected def drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    Minecraft.getMinecraft.getTextureManager.bindTexture(GuiNanoFabricator.texture)
    val k = (width - xSize) / 2
    val l = (height - ySize) / 2
    drawTexturedModalRect(k, l, 0, 0, xSize, ySize)
    var i1 = 0
    i1 = fabricatorInventory.getCookProgressScaled(30)
    drawTexturedModalRect(k + 86, l + 21, 176, 0, i1 + 1, 31)
    i1 = (fabricatorInventory.currentPower * 60) / fabricatorInventory.getMaxPower
    drawTexturedModalRect(k + 10, l + 8 + (60 - i1), 176, 31 + (60 - i1), 16, i1)
    val fluid = fabricatorInventory.getTankInfo(ForgeDirection.UNKNOWN)(0).fluid
    if (fluid != null) {
      val image = fluid.getFluid.getStillIcon
      i1 = (fabricatorInventory.getMassAmount * 60) / fabricatorInventory.getMassCapacity
      RenderUtils.renderLiquidInGUI(this, zLevel, image, k + 150, l + 8 + (60 - i1), 16, i1)
      Minecraft.getMinecraft.getTextureManager.bindTexture(GuiNanoFabricator.texture)
    }
    drawTexturedModalRect(k + 150, l + 8, 176, 91, 16, 60)
  }
}