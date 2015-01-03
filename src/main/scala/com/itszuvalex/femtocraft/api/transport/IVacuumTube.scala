package com.itszuvalex.femtocraft.api.transport

import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.ForgeDirection

/**
 * @author Itszuvalex
 */
trait IVacuumTube {
  /**
   * @return Returns True if tube cannot accept any more items.
   */
  def isOverflowing: Boolean

  /**
   * @param item ItemStack to be inserted into tube.
   * @return True if ItemStack is able to be put into tube.
   */
  def canInsertItem(item: ItemStack, dir: ForgeDirection): Boolean

  /**
   * @param item ItemStack to be inserted into tube.
   * @return True if ItemStack was successfully put into tube.
   */
  def insertItem(item: ItemStack, dir: ForgeDirection): Boolean

  /**
   * @return True if given ForgeDirection is an input.  This allows multiple connections, compared to simply returning
   *         "input direction", causing lockout.
   */
  def isInput(dir: ForgeDirection): Boolean

  /**
   * @return True if given ForgeDirection is an output.  This allows multiple connections, compared to simply
   *         returning "output direction", causing lockout.
   */
  def isOutput(dir: ForgeDirection): Boolean

  /**
   * Clears connections to this tube from the given side.
   */
  def disconnect(dir: ForgeDirection): Unit

  /**
   * @param input Direction tube will now accept input from.
   * @return True if input successfully set.
   */
  def addInput(input: ForgeDirection): Boolean

  /**
   * @param output Direction tube will now send output to.
   * @return True if input successfully set.
   */
  def addOutput(output: ForgeDirection): Boolean
}
