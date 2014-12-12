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
import com.itszuvalex.femtocraft.power.containers.ContainerMagnetoHydrodynamicGenerator
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagnetohydrodynamicGenerator
import com.itszuvalex.femtocraft.render.RenderUtils
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.{FluidRegistry, FluidStack, IFluidTank}
import org.lwjgl.opengl.GL11

/**
 * Created by Christopher Harris (Itszuvalex) on 8/27/14.
 */
object GuiMagnetohydrodynamicGenerator {
  val texture = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/MagnetohydrodynamicGenerator.png")
}

class GuiMagnetohydrodynamicGenerator(private val generator: TileEntityMagnetohydrodynamicGenerator) extends GuiBase(new ContainerMagnetoHydrodynamicGenerator(generator)) {
  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    super.drawScreen(par1, par2, par3)
    if (this.isPointInRegion(30, 24, 16, 120, par1, par2)) {
      val moltenSaltAmount: Int = this.generator.getMoltenSaltTank.getFluidAmount
      val moltenSaltMax: Int = this.generator.getMoltenSaltTank.getCapacity
      val fluid: FluidStack = this.generator.getMoltenSaltTank.getFluid
      val name: String = if (fluid == null) "" else " " + FluidRegistry.getFluidName(fluid)
      val text: String = FemtocraftUtils.formatIntegerToString(moltenSaltAmount) + '/' + FemtocraftUtils.formatIntegerToString(moltenSaltMax) + " mB" + name
      this.drawCreativeTabHoveringText(text, par1, par2)
    }
    else if (this.isPointInRegion(130, 24, 16, 120, par1, par2)) {
      val contaminatedSaltAmount: Int = this.generator.getContaminatedSaltTank.getFluidAmount
      val contamiantedSaltMax: Int = this.generator.getContaminatedSaltTank.getCapacity
      val fluid: FluidStack = this.generator.getContaminatedSaltTank.getFluid
      val name: String = if (fluid == null) "" else " " + FluidRegistry.getFluidName(fluid)
      val text: String = FemtocraftUtils.formatIntegerToString(contaminatedSaltAmount) + '/' + FemtocraftUtils.formatIntegerToString(contamiantedSaltMax) + " mB" + name
      this.drawCreativeTabHoveringText(text, par1, par2)
    }
  }

  protected override def drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
    val s: String = "Magnetohydrodynamic Generator"
    this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
    val power: String = FemtocraftUtils.formatIntegerToString(generator.getCurrentPower) + "/" + FemtocraftUtils.formatIntegerToString(generator.getMaxPower) + " OP"
    this.fontRendererObj.drawString(power, this.xSize / 2 - this.fontRendererObj.getStringWidth(power) / 2, this.ySize * 4 / 5, FemtocraftUtils.colorFromARGB(0, 255, 255, 255))
  }

  protected def drawGuiContainerBackgroundLayer(f: Float, i: Int, j: Int) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    Minecraft.getMinecraft.getTextureManager.bindTexture(GuiMagnetohydrodynamicGenerator.texture)
    val k: Int = (this.width - this.xSize) / 2
    val l: Int = (this.height - this.ySize) / 2
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize)
    val power: Int = generator.getCurrentPower * 72
    val max: Int = generator.getMaxPower
    val i1: Int = if ((power > 0) && (max > 0)) power / max else 0
    this.drawTexturedModalRect(k + 52, l + 47 + (72 - i1), 176, 72 - i1, 72, i1)
    renderTank(generator.getMoltenSaltTank, 30, 23, k, l)
    renderTank(generator.getContaminatedSaltTank, 130, 23, k, l)
    this.drawTexturedModalRect(k + 30, l + 23, 176, 72, 16, 60)
    this.drawTexturedModalRect(k + 30, l + 23 + 60, 176, 72, 16, 60)
    this.drawTexturedModalRect(k + 130, l + 23, 176, 72, 16, 60)
    this.drawTexturedModalRect(k + 130, l + 23 + 60, 176, 72, 16, 60)
  }

  private def renderTank(tank: IFluidTank, x: Int, y: Int, k: Int, l: Int) {
    val fluid = tank.getFluid
    if (fluid != null) {
      val image = fluid.getFluid.getStillIcon
      val i1: Int = (fluid.amount * 120) / tank.getCapacity
      RenderUtils.renderLiquidInGUI(this, this.zLevel, image, k + x, l + y + (120 - i1), 16, i1)
      Minecraft.getMinecraft.getTextureManager.bindTexture(GuiMagnetohydrodynamicGenerator.texture)
    }
  }
}
