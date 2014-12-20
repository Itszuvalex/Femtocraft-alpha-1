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
package com.itszuvalex.femtocraft.research.gui.technology

import java.util
import java.util.{ArrayList, List}

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, DimensionalRecipe, TemporalRecipe}
import com.itszuvalex.femtocraft.managers.research.ResearchStatus
import com.itszuvalex.femtocraft.research.gui.GuiResearch
import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnology._
import com.itszuvalex.femtocraft.sound.FemtocraftSoundUtils
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.{FontRenderer, GuiScreen}
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.item.ItemStack
import net.minecraft.util.{EnumChatFormatting, ResourceLocation}
import org.lwjgl.opengl.{GL11, GL12}

import scala.collection.JavaConversions._

object GuiTechnology {
  val descriptionWidth : Int = 238
  val descriptionHeight: Int = 117
  private val texture: ResourceLocation = new ResourceLocation(Femtocraft.ID.toLowerCase, "textures/guis/GuiTechnology.png")
}

class GuiTechnology(private val guiResearch: GuiResearch, private val status: ResearchStatus) extends GuiScreen {
  private val tech                  = Femtocraft.researchManager.getTechnology(status.tech)
  private val renderer              = new GuiTechnologyRenderer(this, if (status.researched) tech.getResearchedDescription else tech.getDiscoveredDescription)
  private val xSize                 = 256
  private val ySize                 = 202
  private val backButtonX           = 8
  private val backButtonY           = 11
  private val backButtonWidth       = 52 - 8
  private val backButtonHeight      = 26 - 8
  private val pageLeftButtonX       = 8
  private val pageLeftButtonY       = 54
  private val pageLeftButtonWidth   = 25 - 8
  private val pageLeftButtonHeight  = 71 - 54
  private val pageRightButtonX      = 88
  private val pageRightButtonY      = 54
  private val pageRightButtonWidth  = 106 - 88
  private val pageRightButtonHeight = 71 - 54
  private var displayPage           = 1

  override def doesGuiPauseGame = false

  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    this.drawDefaultBackground()
    this.zLevel = 0.0F
    GL11.glDepthFunc(GL11.GL_GEQUAL)
    GL11.glPushMatrix()
    GL11.glTranslatef(0.0F, 0.0F, -200.0F)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glEnable(GL12.GL_RESCALE_NORMAL)
    GL11.glEnable(GL11.GL_COLOR_MATERIAL)
    Minecraft.getMinecraft.getTextureManager.bindTexture(texture)
    val k: Int = (this.width - this.xSize) / 2
    val l: Int = (this.height - this.ySize) / 2

    GL11.glEnable(GL11.GL_BLEND)
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize)
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glEnable(GL11.GL_DEPTH_TEST)
    GL11.glDepthFunc(GL11.GL_LEQUAL)

    GL11.glDisable(GL11.GL_TEXTURE_2D)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)


    GL11.glDepthFunc(GL11.GL_LEQUAL)
    GL11.glDisable(GL11.GL_DEPTH_TEST)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    super.drawScreen(par1, par2, par3)
    val tooltip = new ArrayList[String]
    renderInformation(k + 9, l + 76, 237, 116, displayPage, par1, par2, tooltip, status.researched)
    val s: String = status.tech
    val width: Int = 188 - (66 + 16 + 2)
    val top: Int = if (fontRendererObj.getStringWidth(s) > width) 12 else 17
    fontRendererObj.drawSplitString(EnumChatFormatting.WHITE + s + EnumChatFormatting.RESET, k + 84, l + top, width, 27 - top)
    if ((par1 >= (k + backButtonX)) && (par1 <= (k + backButtonX + backButtonWidth)) && (par2 >= (l + backButtonY)) && (par2 <= (l + backButtonY + backButtonHeight))) {
      this.drawGradientRect(k + backButtonX, l + backButtonY, k + backButtonWidth + backButtonX, l + backButtonY + backButtonHeight, FemtocraftUtils.colorFromARGB(60, 45, 0, 110), FemtocraftUtils.colorFromARGB(60, 45, 0, 110))
    }
    this.fontRendererObj.drawString("Back", k + backButtonX + backButtonWidth / 2 - this.fontRendererObj.getStringWidth("Back") / 2, l + backButtonY + (backButtonHeight - this.fontRendererObj.FONT_HEIGHT) / 2, FemtocraftUtils.colorFromARGB(255, 255, 255, 255))
    var color: Int = FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
    if (displayPage > 1) {
      if ((par1 >= (k + pageLeftButtonX)) && (par1 <= (k + pageLeftButtonX + pageLeftButtonWidth)) && (par2 >= (l + pageLeftButtonY)) && (par2 <= (l + pageLeftButtonY + pageLeftButtonHeight))) {
        this.drawGradientRect(k + pageLeftButtonX, l + pageLeftButtonY, k + pageLeftButtonX + pageLeftButtonWidth, l + pageLeftButtonY + pageLeftButtonHeight, color, color)
      }
      this.fontRendererObj.drawString("<-", k + pageLeftButtonX + pageLeftButtonWidth / 2 - this.fontRendererObj.getStringWidth("<-") / 2, l + pageLeftButtonY + (pageLeftButtonHeight - this.fontRendererObj.FONT_HEIGHT) / 2 + 1, FemtocraftUtils.colorFromARGB(255, 255, 255, 255))
    }
    else {
      color = FemtocraftUtils.colorFromARGB(60, 0, 0, 0)
      this.drawGradientRect(k + pageLeftButtonX, l + pageLeftButtonY, k + pageLeftButtonX + pageLeftButtonWidth, l + pageLeftButtonY + pageLeftButtonHeight, color, color)
    }
    color = FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
    if (displayPage < getNumPages(status.researched)) {
      if ((par1 >= (k + pageRightButtonX)) && (par1 <= (k + pageRightButtonX + pageRightButtonWidth)) && (par2 >= (l + pageRightButtonY)) && (par2 <= (l + pageRightButtonY + pageRightButtonHeight))) {
        this.drawGradientRect(k + pageRightButtonX, l + pageRightButtonY, k + pageRightButtonX + pageRightButtonWidth, l + pageRightButtonY + pageRightButtonHeight, color, color)
      }
      this.fontRendererObj.drawString("->", k + pageRightButtonX + pageRightButtonWidth / 2 - this.fontRendererObj.getStringWidth("->") / 2, l + pageRightButtonY + (pageRightButtonHeight - this.fontRendererObj.FONT_HEIGHT) / 2 + 1, FemtocraftUtils.colorFromARGB(255, 255, 255, 255))
    }
    else {
      color = FemtocraftUtils.colorFromARGB(60, 0, 0, 0)
      this.drawGradientRect(k + pageRightButtonX, l + pageRightButtonY, k + pageRightButtonX + pageRightButtonWidth, l + pageRightButtonY + pageRightButtonHeight, color, color)
    }
    val pageString = "Page %s/%s".format(displayPage, getNumPages(status.researched))
    this.fontRendererObj.drawString(pageString, k + pageLeftButtonX + (pageLeftButtonWidth + pageRightButtonX - pageLeftButtonX) / 2 - this.fontRendererObj.getStringWidth(pageString) / 2, l + pageLeftButtonY + (pageLeftButtonHeight - this.fontRendererObj.FONT_HEIGHT) / 2 + 1, FemtocraftUtils.colorFromARGB(255, 255, 255, 255))
    this.zLevel = 0
    val renderitem = new RenderItem
    RenderHelper.enableGUIStandardItemLighting()
    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glEnable(GL12.GL_RESCALE_NORMAL)
    GL11.glEnable(GL11.GL_COLOR_MATERIAL)
    renderitem.renderItemAndEffectIntoGUI(Minecraft.getMinecraft.fontRenderer, Minecraft.getMinecraft.getTextureManager, tech.getDisplayItem, k + 66, l + 12)
    RenderHelper.disableStandardItemLighting()
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glDisable(GL11.GL_LIGHTING)
    this.renderCraftingGrid(k + 194, l + 11, tech.getResearchMaterials, par1, par2, tooltip)
    this.zLevel = 0.0F
    this.drawHoveringText(tooltip, par1, par2, this.fontRendererObj)
    GL11.glPopMatrix()
    GL11.glEnable(GL11.GL_DEPTH_TEST)
    GL11.glEnable(GL11.GL_LIGHTING)
    RenderHelper.disableStandardItemLighting()
  }

  protected override def drawHoveringText(par1List: List[_], par2: Int, par3: Int, font: FontRenderer) {
    if (!par1List.isEmpty) {
      var k: Int = 0

      for (aPar1List <- par1List) {
        val s: String = aPar1List.asInstanceOf[String]
        val l: Int = font.getStringWidth(s)
        if (l > k) {
          k = l
        }
      }
      var i1: Int = par2 + 12
      var j1: Int = par3 - 12
      var k1: Int = 8
      if (par1List.size > 1) {
        k1 += 2 + (par1List.size - 1) * 10
      }
      if (i1 + k > this.width) {
        i1 -= 28 + k
      }
      if (j1 + k1 + 6 > this.height) {
        j1 = this.height - k1 - 6
      }
      val l1: Int = -267386864
      this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1)
      this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1)
      this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1)
      this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1)
      this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1)
      val i2: Int = 1347420415
      val j2: Int = (i2 & 16711422) >> 1 | i2 & -16777216
      this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2)
      this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2)
      this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2)
      this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2)

      for (k2 <- 0 until par1List.size) {
        val s1: String = par1List.get(k2).asInstanceOf[String]
        font.drawStringWithShadow(s1, i1, j1, -1)
        if (k2 == 0) {
          j1 += 2
        }
        j1 += 10
      }
    }
  }

  /**
   * @param x
   * @param y
   * @param width
   * @param height
   * @param pageNum
   * @param mouseX
   * @param mouseY
   * @param isResearched
   */
  protected def renderInformation(x: Int, y: Int, width: Int, height: Int, pageNum: Int, mouseX: Int, mouseY: Int, tooltip: List[_], isResearched: Boolean) {
    renderer.render(x, y, width, height, pageNum, mouseX, mouseY, tooltip, isResearched)
  }

  def getNumPages(researched: Boolean) = renderer.getPageCount

  protected override def keyTyped(par1: Char, par2: Int) {
    if (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode) {
      this.mc.displayGuiScreen(guiResearch)
    }
    else {
      super.keyTyped(par1, par2)
    }
  }

  protected override def mouseClicked(par1: Int, par2: Int, par3: Int) {
    if (par3 == 0) {
      val k: Int = (this.width - this.xSize) / 2
      val l: Int = (this.height - this.ySize) / 2
      if ((par1 >= (k + backButtonX)) && (par1 <= (k + backButtonX + backButtonWidth)) && (par2 >= (l + backButtonY)) && (par2 <= (l + backButtonY + backButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        this.mc.displayGuiScreen(guiResearch)
      }
      if ((displayPage > 1) && (par1 >= (k + pageLeftButtonX)) && (par1 <= (k + pageLeftButtonX + pageLeftButtonWidth)) && (par2 >= (l + pageLeftButtonY)) && (par2 <= (l + pageLeftButtonY + pageLeftButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        displayPage -= 1
      }
      if ((displayPage < getNumPages(status.researched)) && (par1 >= (k + pageRightButtonX)) && (par1 <= (k + pageRightButtonX + pageRightButtonWidth)) && (par2 >= (l + pageRightButtonY)) && (par2 <= (l + pageRightButtonY + pageRightButtonHeight))) {
        FemtocraftSoundUtils.playClickSound()
        displayPage += 1
      }
    }
    super.mouseClicked(par1, par2, par3)
  }

  /**
   * Height must be greater than 54. Width must be MUCH greater than 54, to allow room for the info string to be
   * drawn.
   *
   * @param x
   * @param y
   * @param width
   * @param height
   * @param items
   * @param info
   */
  private[technology] def renderCraftingGridWithInfo(x: Int, y: Int, width: Int, height: Int, items: Array[ItemStack], mouseX: Int, mouseY: Int, tooltip: List[_], info: String) {
    val padding: Int = 2
    renderCraftingGrid(x, y, items, mouseX, mouseY, tooltip)
    val format = EnumChatFormatting.WHITE + info + EnumChatFormatting.RESET
    this.fontRendererObj.drawSplitString(format, x + 54 + padding, y + padding, width - 54 - 2 * padding, height - 2 * padding)
  }

  /**
   * 54 width and height
   *
   * @param x     X of Top Left
   * @param y     Y of Top Left
   * @param items Up to 9 items to render into the crafting grid
   */
  private[technology] def renderCraftingGrid(x: Int, y: Int, items: Array[ItemStack], mouseX: Int, mouseY: Int, tooltip: List[_]) {
    val ir: Array[ItemStack] = if (items != null) items else new Array[ItemStack](9)
    val renderitem = new RenderItem

    for (i <- 0 until 9) {
      val item: ItemStack = if (i >= ir.length) null else ir(i)
      val xr: Int = x + 18 * (i % 3)
      val yr: Int = y + 18 * (i / 3)
      renderItemSlot(xr, yr, item, renderitem, mouseX, mouseY, tooltip)
    }

  }

  private[technology] def renderTemporalRecipeWithInfo(x: Int, y: Int, width: Int, height: Int, recipe: TemporalRecipe, mouseX: Int, mouseY: Int, tooltip: List[_], info: String) {
    val padding: Int = 2
    renderTemporalRecipe(x, y, width, height, recipe, mouseX, mouseY, tooltip)
    val format = EnumChatFormatting.WHITE + info + EnumChatFormatting.RESET
    fontRendererObj.drawSplitString(format, x + 90 + 2 * padding, y + 2 * padding, width - 2 * padding - 72, height - 2 * padding)
  }

  private[technology] def renderTemporalRecipe(x: Int, y: Int, width: Int, height: Int, recipe: TemporalRecipe, mouseX: Int, mouseY: Int, tooltip: List[_]) {
    if (recipe == null) return
    val recipeDir: String = recipe.techLevel.getTooltipEnum + "----->" + EnumChatFormatting.RESET
    renderItemSlot(x, y + 18, recipe.input, new RenderItem, mouseX, mouseY, tooltip)

    for (i <- 0 until recipe.configurators.length) {
      renderItemSlot(x + 18 + 18 * (i % 3), y + 36 * Math.floor(i / 3).toInt, recipe.configurators(i), new RenderItem, mouseX, mouseY, tooltip)
    }

    GL11.glColor4f(1f, 1f, 1f, 1f)
    fontRendererObj.drawSplitString(recipeDir, x + 18 + (54 - fontRendererObj.getStringWidth(recipeDir)) / 2 + 1, y + (54 - fontRendererObj.FONT_HEIGHT) / 2, fontRendererObj.getStringWidth(recipeDir) + 2, fontRendererObj.FONT_HEIGHT)
    renderItemSlot(x + 72 + 2, y + 18, recipe.output, new RenderItem, mouseX, mouseY, tooltip)
  }

  private[technology] def renderItemSlot(x: Int, y: Int, item: ItemStack, renderitem: RenderItem, mouseX: Int, mouseY: Int, tooltip: List[_]) {
    GL11.glColor4f(1f, 1f, 1f, 1f)
    Minecraft.getMinecraft.renderEngine.bindTexture(texture)
    drawTexturedModalRect(x, y, 194, 11, 18, 18)
    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glEnable(GL11.GL_CULL_FACE)
    RenderHelper.enableGUIStandardItemLighting()
    if (item == null) {
      RenderHelper.disableStandardItemLighting()
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      GL11.glDisable(GL11.GL_LIGHTING)
      return
    }
    val xr: Int = x + 1
    val yr: Int = y + 1
    renderitem.renderItemIntoGUI(Minecraft.getMinecraft.fontRenderer, Minecraft.getMinecraft.getTextureManager, item, xr, yr, true)
    renderitem.renderItemOverlayIntoGUI(Minecraft.getMinecraft.fontRenderer, Minecraft.getMinecraft.getTextureManager, item, xr, yr)
    RenderHelper.disableStandardItemLighting()
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glDisable(GL11.GL_LIGHTING)
    if (mouseX > x && mouseX < x + 17 && mouseY > y && mouseY < y + 17) {
      tooltip.asInstanceOf[util.List[String]].addAll(item.getTooltip(Minecraft.getMinecraft.thePlayer, Minecraft.getMinecraft.gameSettings.advancedItemTooltips).asInstanceOf[util.List[String]])
    }
  }

  private[technology] def renderDimensionalRecipeWithInfo(x: Int, y: Int, width: Int, height: Int, recipe: DimensionalRecipe, mouseX: Int, mouseY: Int, tooltip: List[_], info: String) {
    val padding: Int = 2
    renderDimensionalRecipe(x, y, width, height, recipe, mouseX, mouseY, tooltip)
    val format = EnumChatFormatting.WHITE + info + EnumChatFormatting.RESET
    this.fontRendererObj.drawSplitString(format, x + 90 + padding, y + padding, width - 90 - 2 * padding, height - 2 * padding)
  }

  private[technology] def renderDimensionalRecipe(x: Int, y: Int, width: Int, height: Int, recipe: DimensionalRecipe, mouseX: Int, mouseY: Int, tooltip: List[_]) {
    if (recipe == null) return
    val recipeDir: String = recipe.techLevel.getTooltipEnum + "->" + EnumChatFormatting.RESET
    val ri: RenderItem = new RenderItem
    var r_width: Int = 0
    var r_height: Int = 0
    recipe.configurators.length match {
      case 4  =>
        renderItemSlot(x + 18, y + 18, recipe.input, ri, mouseX, mouseY, tooltip)
        renderItemSlot(x + 18, y, recipe.configurators(0), ri, mouseX, mouseY, tooltip)
        renderItemSlot(x, y + 18, recipe.configurators(1), ri, mouseX, mouseY, tooltip)
        renderItemSlot(x + 36, y + 18, recipe.configurators(2), ri, mouseX, mouseY, tooltip)
        renderItemSlot(x + 18, y + 36, recipe.configurators(3), ri, mouseX, mouseY, tooltip)
        r_width = 54
        r_height = 54
      case 12 =>
        renderItemSlot(x + (18 * 4) / 2 - 9, y + (18 * 4) / 2 - 9, recipe.input, ri, mouseX, mouseY, tooltip)

        for (i <- 0 until 4) {
          renderItemSlot(x + 18 * i, y, recipe.configurators(i), ri, mouseX, mouseY, tooltip)
        }


        for (i <- 0 until 2) {
          renderItemSlot(x + 54 * i, y + 18, recipe.configurators(4 + i), ri, mouseX, mouseY, tooltip)
        }

        for (i <- 0 until 2) {
          renderItemSlot(x + 54 * i, y + 36, recipe.configurators(6 + i), ri, mouseX, mouseY, tooltip)
        }
        for (i <- 0 until 4) {
          renderItemSlot(x + 18 * i, y + 54, recipe.configurators(8 + i), ri, mouseX, mouseY, tooltip)
        }
        r_width = 72
        r_height = 72
      case _  =>
        return
    }
    GL11.glColor4f(1f, 1f, 1f, 1f)
    fontRendererObj.drawSplitString(recipeDir, x + r_width + 1, y + (r_height - fontRendererObj.FONT_HEIGHT) / 2, fontRendererObj.getStringWidth(recipeDir) + 2, fontRendererObj.FONT_HEIGHT)
    renderItemSlot(x + r_width + fontRendererObj.getStringWidth(recipeDir) + 2, y + r_height / 2 - 9, recipe.output, new RenderItem, mouseX, mouseY, tooltip)
  }

  private[technology] def renderAssemblerRecipeWithInfo(x: Int, y: Int, width: Int, height: Int, recipe: AssemblerRecipe, mouseX: Int, mouseY: Int, tooltip: util.List[_], info: String) {
    val padding: Int = 2
    renderAssemblerRecipe(x, y, width, height, recipe, mouseX, mouseY, tooltip)
    val format = EnumChatFormatting.WHITE + info + EnumChatFormatting.RESET
    fontRendererObj.drawSplitString(format, x + 54 + fontRendererObj.getStringWidth("<->") + 18 + 2 * padding, y + 2 * padding, width - 2 * padding - fontRendererObj.getStringWidth("<->") - 54 - 18, height - 2 * padding)
  }

  private[technology] def renderAssemblerRecipe(x: Int, y: Int, width: Int, height: Int, recipe: AssemblerRecipe, mouseX: Int, mouseY: Int, tooltip: util.List[_]) {
    val recipeDir: String = (if (recipe == null) "" + EnumChatFormatting.WHITE else recipe.enumTechLevel.getTooltipEnum) + "<->" + EnumChatFormatting.RESET
    renderCraftingGrid(x, y, if (recipe == null) null else recipe.input, mouseX, mouseY, tooltip)
    GL11.glColor4f(1f, 1f, 1f, 1f)
    fontRendererObj.drawSplitString(recipeDir, x + 54 + 1, y + (54 - fontRendererObj.FONT_HEIGHT) / 2, fontRendererObj.getStringWidth(recipeDir) + 2, fontRendererObj.FONT_HEIGHT)
    renderItemSlot(x + 54 + fontRendererObj.getStringWidth(recipeDir) + 2, y + 18, if (recipe == null) null else recipe.output, new RenderItem, mouseX, mouseY, tooltip)
  }

  private[technology] def renderCraftingRecipeWithInfo(x: Int, y: Int, width: Int, height: Int, input: Array[ItemStack], output: ItemStack, mouseX: Int, mouseY: Int, tooltip: List[_], info: String) {
    val padding: Int = 2
    renderCraftingRecipe(x, y, width, height, input, output, mouseX, mouseY, tooltip)
    val format = EnumChatFormatting.WHITE + info + EnumChatFormatting.RESET
    fontRendererObj.drawSplitString(format, x + 54 + fontRendererObj.getStringWidth("->") + 18 + 2 * padding, y + 2 * padding, width - 2 * padding - fontRendererObj.getStringWidth("->") - 54 - 18, height - 2 * padding)
  }

  private[technology] def renderCraftingRecipe(x: Int, y: Int, width: Int, height: Int, input: Array[ItemStack], output: ItemStack, mouseX: Int, mouseY: Int, tooltip: List[_]) {
    val recipeDir: String = EnumChatFormatting.WHITE + "->" + EnumChatFormatting.RESET
    renderCraftingGrid(x, y, input, mouseX, mouseY, tooltip)
    GL11.glColor4f(1f, 1f, 1f, 1f)
    fontRendererObj.drawSplitString(recipeDir, x + 54 + 1, y + (54 - fontRendererObj.FONT_HEIGHT) / 2, fontRendererObj.getStringWidth(recipeDir) + 2, fontRendererObj.FONT_HEIGHT)
    renderItemSlot(x + 54 + fontRendererObj.getStringWidth(recipeDir) + 2, y + 18, output, new RenderItem, mouseX, mouseY, tooltip)
  }
}
