package com.itszuvalex.femtocraft.core.traits.tile

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.api.utils.BaseInventory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack

/**
 * Created by Chris on 11/29/2014.
 */
trait Inventory extends TileEntityBase with ISidedInventory {
  @Saveable
  val inventory = defaultInventory

  def defaultInventory: BaseInventory

  override def getAccessibleSlotsFromSide(side: Int) = (0 until inventory.getSizeInventory).toArray

  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = true

  override def canInsertItem(slot: Int, item: ItemStack, side: Int) = true

  override def closeInventory() = inventory.closeInventory()

  override def decrStackSize(slot: Int, amount: Int) = {
    val ret = inventory.decrStackSize(slot, amount)
    markDirty()
    ret
  }

  override def getSizeInventory = inventory.getSizeInventory

  override def getInventoryStackLimit = inventory.getInventoryStackLimit

  override def isItemValidForSlot(slot: Int, item: ItemStack) = inventory.isItemValidForSlot(slot, item)

  override def getStackInSlotOnClosing(slot: Int): ItemStack = inventory.getStackInSlotOnClosing(slot)

  override def openInventory() = inventory.openInventory()

  override def setInventorySlotContents(slot: Int, item: ItemStack) = {
    inventory.setInventorySlotContents(slot, item)
    markDirty()
  }

  override def markDirty() = {
    inventory.markDirty()
    notifyNeighborsOfChange()
  }

  override def isUseableByPlayer(player: EntityPlayer) = canPlayerUse(player) && inventory.isUseableByPlayer(player)

  override def getStackInSlot(slot: Int) = inventory.getStackInSlot(slot)

  override def hasCustomInventoryName = inventory.hasCustomInventoryName

  override def getInventoryName = inventory.getInventoryName
}
