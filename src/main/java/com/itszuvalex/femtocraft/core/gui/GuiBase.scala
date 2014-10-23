package com.itszuvalex.femtocraft.core.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container

/**
 * Created by Christopher Harris (Itszuvalex) on 10/19/14.
 */
abstract class GuiBase(c: Container) extends GuiContainer(c) {
  def isPointInRegion(x: Int, y: Int, width: Int, height: Int, mouseX: Int, mouseY: Int) = func_146978_c(x, y, width, height, mouseX, mouseY)

}
