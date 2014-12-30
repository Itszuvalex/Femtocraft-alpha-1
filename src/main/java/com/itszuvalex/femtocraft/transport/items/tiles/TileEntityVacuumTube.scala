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
package com.itszuvalex.femtocraft.transport.items.tiles

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.transport.IVacuumTube
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.utils.{BaseInventory, FemtocraftUtils}
import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.{IInventory, ISidedInventory}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

object TileEntityVacuumTube {
  val ITEM_MASK_KEY       = "ItemMask"
  val CONNECTION_MASK_KEY = "ConnectionMask"
}

class TileEntityVacuumTube extends TileEntityBase with IVacuumTube {
  @Saveable private val items = new BaseInventory(4)
            var hasItem               = Array.fill[Boolean](4)(false)
  @Saveable var queuedItem: ItemStack = null
  private var inputDir           = UNKNOWN
  private var outputDir          = UNKNOWN
  private var inputTile : AnyRef = null
  private var outputTile: AnyRef = null
  private var overflowing        = false
  private var canFillInv         = true
  private var canExtractInv      = true
  private var needsCheckInput    = false
  private var needsCheckOutput   = false
  private var loading            = true

  override def hasDescription = true

  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity) {
    super.onDataPacket(net, pkt)
    val compound: NBTTagCompound = pkt.func_148857_g
    parseItemMask(compound.getByte(TileEntityVacuumTube.ITEM_MASK_KEY))
    parseConnectionMask(compound.getByte(TileEntityVacuumTube.CONNECTION_MASK_KEY))
  }

  def parseConnectionMask(mask: Byte) {
    val input: Int = mask & 7
    val output: Int = (mask >> 4) & 7
    inputDir = getOrientation(input)
    outputDir = getOrientation(output)
    val hasInput = ((mask >> 3) & 1) == 1
    val hasOutput = ((mask >> 7) & 1) == 1
    if (worldObj == null) {
      return
    }
    val inputTile = worldObj
                    .getTileEntity(xCoord + inputDir.offsetX, yCoord + inputDir.offsetY, zCoord + inputDir.offsetZ)
    if (inputTile == null) {
      if (hasInput) {
        needsCheckInput = true
      }
    } else {
      addInput(inputDir)
    }
    val outputTile = worldObj
                     .getTileEntity(xCoord + outputDir.offsetX, yCoord + outputDir.offsetY, zCoord + outputDir.offsetZ)
    if (outputTile == null) {
      if (hasOutput) {
        needsCheckOutput = true
      }
    } else {
      addOutput(outputDir)
    }
    setRenderUpdate()
  }

  def parseItemMask(mask: Byte) {
    var temp: Byte = 0
    for (i <- 0 until hasItem.length) {
      temp = mask
      hasItem(i) = ((temp >> i) & 1) == 1
    }

    temp = mask
    overflowing = ((temp >> hasItem.length) & 1) == 1
    setRenderUpdate()
  }

  override def updateEntity() {
    loading = false
    super.updateEntity()
  }

  override def writeToNBT(par1nbtTagCompound: NBTTagCompound) {
    super.writeToNBT(par1nbtTagCompound)
    par1nbtTagCompound.setByte("Connections", generateConnectionMask)
    par1nbtTagCompound.setByte("HasItems", generateItemMask)
  }

  override def getDescriptionPacket = generatePacket

  private def generatePacket: S35PacketUpdateTileEntity = {
    val compound: NBTTagCompound = new NBTTagCompound
    compound.setByte(TileEntityVacuumTube.ITEM_MASK_KEY, generateItemMask)
    compound.setByte(TileEntityVacuumTube.CONNECTION_MASK_KEY, generateConnectionMask)
    new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound)
  }

  def generateConnectionMask: Byte = {
    var output = 0
    output += FemtocraftUtils.indexOfForgeDirection(inputDir) & 7
    output += (if (missingInput) {0} else {1 << 3})
    output += (FemtocraftUtils.indexOfForgeDirection(outputDir) & 7) << 4
    output += (if (missingOutput) {0} else {1 << 7})
    output.toByte
  }

  def generateItemMask: Byte = {
    var output = 0
    for (i <- 0 until hasItem.length) {
      if (hasItem(i)) {
        output += 1 << i
      }
    }
    if (isOverflowing) {
      output += 1 << hasItem.length
    }
    output.toByte
  }

  override def femtocraftServerUpdate() {
    if (needsCheckInput || (inputDir != UNKNOWN && missingInput)) {
      if (!addInput(inputDir)) {
        return
      }
      needsCheckInput = false
    }
    if (needsCheckOutput || (outputDir != UNKNOWN && missingOutput)) {
      if (!addOutput(outputDir)) {
        return
      }
      needsCheckOutput = false
    }

    if (hasItem.forall(p => !p) && queuedItem == null) return

    if (items.getStackInSlot(3) != null) {
      outputTile match {
        case tube: IVacuumTube if tube.insertItem(items.getStackInSlot(3), outputDir.getOpposite) =>
          items.setInventorySlotContents(3, null)
          hasItem(3) = false
          setUpdate()
          markDirty()
        case sided: ISidedInventory if canFillInv                                                 =>
          val side = FemtocraftUtils.indexOfForgeDirection(outputDir.getOpposite)
          val slots = sided.getAccessibleSlotsFromSide(side)
          val invMax = sided.getInventoryStackLimit
          if (slots.exists { case slot if sided.canInsertItem(slot, items.getStackInSlot(3), side) &&
                                          sided.isItemValidForSlot(slot, items.getStackInSlot(3)) =>
            val slotStack = sided.getStackInSlot(slot)
            if (slotStack == null) {
              sided.setInventorySlotContents(slot, items.getStackInSlot(3))
              true
            } else if (FemtocraftUtils.compareItem(items.getStackInSlot(3), slotStack) == 0 &&
                       items.getStackInSlot(3).isStackable) {
              val itemMax = slotStack.getMaxStackSize
              val max = if (invMax > itemMax) itemMax else invMax
              val room = max - slotStack.stackSize
              val amount = if (room > items.getStackInSlot(3).stackSize) {
                items.getStackInSlot(3).stackSize
              } else {
                room
              }
              slotStack.stackSize += amount
              items.decrStackSize(3, amount)
              markDirty()
              sided.markDirty()
              items.getStackInSlot(3) == null || items.getStackInSlot(3).stackSize <= 0
            } else {
              false
            }
          case _                                                                                  => false
                           }) {
            items.setInventorySlotContents(3, null)
            hasItem(3) = false
            setUpdate()
            markDirty()
            sided.markDirty()
          } else {
            canFillInv = false
            setUpdate()
            markDirty()
          }
        case inv: IInventory if canFillInv                                                        =>
          val invMax: Int = inv.getInventoryStackLimit
          if ((0 until inv.getSizeInventory)
              .exists { case slot if inv.isItemValidForSlot(slot, items.getStackInSlot(3)) =>
            val slotStack = inv.getStackInSlot(slot)
            if (slotStack == null) {
              inv.setInventorySlotContents(slot, items.getStackInSlot(3))
              true
            } else if (FemtocraftUtils.compareItem(items.getStackInSlot(3), slotStack) == 0 &&
                       items.getStackInSlot(3).isStackable) {
              val itemMax = slotStack.getMaxStackSize
              val max = if (invMax > itemMax) itemMax else invMax
              val room = max - slotStack.stackSize
              val amount = if (room > items.getStackInSlot(3).stackSize) {
                items.getStackInSlot(3).stackSize
              } else {
                room
              }
              slotStack.stackSize += amount
              items.decrStackSize(3, amount)
              markDirty()
              inv.markDirty()
              items.getStackInSlot(3) == null || items.getStackInSlot(3).stackSize <= 0
            } else {
              false
            }
          case _                                                                           => false
                      }) {
            items.setInventorySlotContents(3, null)
            hasItem(3) = false
            setUpdate()
            markDirty()
            inv.markDirty()
          } else {
            canFillInv = false
            setUpdate()
            markDirty()
          }
        case _ if missingOutput                                                                   =>
          ejectItem(3)
        case _                                                                                    =>
      }
    }

    for (i <- items.getSizeInventory - 2 to 0 by -1) {
      if (items.getStackInSlot(i + 1) == null) {
        if (hasItem(i)) {
          setUpdate()
          markDirty()
        }
        hasItem(i + 1) = hasItem(i)
        hasItem(i) = false
        items.setInventorySlotContents(i + 1, items.getStackInSlot(i))
        items.setInventorySlotContents(i, null)
      }
    }

    if (isOverflowing) {
      overflowing = true
      setUpdate()
      markDirty()
      return
    }
    if (queuedItem != null) {
      items.setInventorySlotContents(0, queuedItem)
      hasItem(0) = true
      queuedItem = null
      setUpdate()
      markDirty()
    } else {
      inputTile match {
        case sided: ISidedInventory if canExtractInv =>
          val side: Int = FemtocraftUtils.indexOfForgeDirection(inputDir.getOpposite)
          val slots: Array[Int] = sided.getAccessibleSlotsFromSide(side)
          canExtractInv = slots.exists { case slot if {
            val stack = sided.getStackInSlot(slot)
            stack != null && sided.canExtractItem(slot, stack, side) && canAcceptItemStack(stack)
          }      =>
            items.setInventorySlotContents(0, sided.decrStackSize(slot, 64))
            hasItem(0) = true
            sided.markDirty()
            setUpdate()
            markDirty()
            true
          case _ => false
                                       }
        case inv: IInventory if canExtractInv        =>
          canExtractInv = (0 until inv.getSizeInventory).exists { case i if inv.getStackInSlot(i) != null &&
                                                                            canAcceptItemStack(inv.getStackInSlot(i)) =>
            items.setInventorySlotContents(0, inv.decrStackSize(i, inv.getInventoryStackLimit))
            hasItem(0) = true
            inv.markDirty()
            setUpdate()
            markDirty()
            true
          case _                                                                                                      => false
                                                                }
        case _ if missingInput                       =>
          var dir = inputDir
          if (inputDir == UNKNOWN) {
            if (outputDir == UNKNOWN) {
              dir = SOUTH
            } else {
              dir = outputDir.getOpposite
            }
          }
          val x: Float = xCoord + dir.offsetX
          val y: Float = yCoord + dir.offsetY
          val z: Float = zCoord + dir.offsetZ
          worldObj
          .getEntitiesWithinAABB(classOf[EntityItem], AxisAlignedBB.getBoundingBox(x, y, z, x + 1f, y + 1f, z + 1f))
          .foreach { case item: EntityItem if canAcceptItemStack(item.getEntityItem) =>
            item.addVelocity((xCoord + .5) - item.posX, (yCoord + .5) - item.posY, (zCoord + .5) - item.posZ)
                   }
        case _                                       =>
      }
    }
  }

  def missingInput = inputTile == null && !needsCheckInput

  def missingOutput = outputTile == null && !needsCheckOutput

  def isOverflowing: Boolean = {
    if (worldObj.isRemote && overflowing) return true
    outputTile match {
      case tube: IVacuumTube => !tube.canInsertItem(null, outputDir.getOpposite)
      case inv: IInventory   => !canFillInv && hasItem.forall(p => p) && queuedItem != null
      case _                 => false
    }
  }

  private def ejectItem(slot: Int) {
    val dropItem = items.getStackInSlot(slot)
    ejectItemStack(dropItem)
    items.setInventorySlotContents(slot, null)
    hasItem(slot) = false
    setUpdate()
    markDirty()
  }

  private def ejectItemStack(dropItem: ItemStack) {
    if (dropItem == null) {
      return
    }
    if (worldObj.isRemote) {
      return
    }
    var dir = outputDir
    if (dir == UNKNOWN) {
      if (inputDir == UNKNOWN) {
        dir = NORTH
      } else {
        dir = inputDir.getOpposite
      }
    }
    val entityitem = new EntityItem(worldObj,
                                    xCoord + .5 + dir.offsetX * .6f,
                                    yCoord + .5 + dir.offsetY * .6f,
                                    zCoord + .5f + dir.offsetZ * .6f,
                                    dropItem.copy)
    entityitem.motionX = dir.offsetX
    entityitem.motionY = dir.offsetY
    entityitem.motionZ = dir.offsetZ
    worldObj.spawnEntityInWorld(entityitem)
    setUpdate()
    markDirty()
  }

  override def markDirty() {
    if (!loading) {
      super.markDirty()
    }
  }

  protected def canAcceptItemStack(item: ItemStack): Boolean = true

  def addInput(dir: ForgeDirection): Boolean = {
    if (worldObj == null) {
      return false
    }
    if (dir == outputDir) {
      return false
    }
    if (inputTile != null && (dir == inputDir)) {
      return true
    }
    val tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)
    tile match {
      case _ if tile == this => return false
      case null              => return false
      case _                 =>
    }
    if (inputDir != UNKNOWN) {
      clearInput()
    }
    tile match {
      case inv: IInventory   =>
        inputTile = tile
        inputDir = dir
        setUpdate()
        setModified()
        true
      case tube: IVacuumTube =>
        inputTile = tile
        inputDir = dir
        tube.addOutput(dir.getOpposite)
        setUpdate()
        setModified()
        true
      case _                 => false
    }
  }

  private def clearInput() {
    inputTile match {
      case tube: IVacuumTube =>
        val opposite = inputDir.getOpposite
        inputDir = UNKNOWN
        tube.disconnect(opposite)
      case _                 =>
    }
    inputDir = UNKNOWN
    inputTile = null
    setUpdate()
    setModified()
  }

  def addOutput(dir: ForgeDirection): Boolean = {
    if (worldObj == null) {
      return false
    }
    if (dir == inputDir) {
      return false
    }
    if (outputTile != null && (dir == outputDir)) {
      return true
    }
    val tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)
    tile match {
      case _ if tile == this => return false
      case null              => return false
      case _                 =>
    }
    if (outputDir != UNKNOWN) {
      clearOutput()
    }
    tile match {
      case inv: IInventory   =>
        outputTile = tile
        outputDir = dir
        setUpdate()
        setModified()
        true
      case tube: IVacuumTube =>
        outputTile = tile
        outputDir = dir
        tube.addInput(dir.getOpposite)
        setUpdate()
        setModified()
        true
      case _                 => false

    }
  }

  private def clearOutput() {
    outputTile match {
      case tube: IVacuumTube =>
        val opposite = outputDir.getOpposite
        outputDir = UNKNOWN
        tube.disconnect(opposite)
      case _                 =>
    }
    outputDir = UNKNOWN
    outputTile = null
    setUpdate()
    setModified()
  }

  override def readFromNBT(par1nbtTagCompound: NBTTagCompound) {
    super.readFromNBT(par1nbtTagCompound)
    parseConnectionMask(par1nbtTagCompound.getByte("Connections"))
    parseItemMask(par1nbtTagCompound.getByte("HasItems"))
  }

  def canInsertItem(item: ItemStack, dir: ForgeDirection) = queuedItem == null

  def disconnect(dir: ForgeDirection) {
    if (isInput(dir)) {
      clearInput()
    } else if (isOutput(dir)) {
      clearOutput()
    }
  }

  def isInput(dir: ForgeDirection) = dir == inputDir

  def isOutput(dir: ForgeDirection) = dir == outputDir

  def isEndpoint = missingInput || missingOutput

  def onBlockBreak() {
    clearInput()
    clearOutput()
    for (i <- 0 until items.getSizeInventory) {
      ejectItem(i)
    }
    ejectItemStack(queuedItem)
  }

  def searchForMissingConnection() {
    if (missingInput) {
      searchForInput()
    }
    if (missingOutput) {
      searchForOutput()
    }
  }

  def searchForInput(): Boolean = {
    val check = new ArrayBuffer[ForgeDirection]
    ForgeDirection
    .VALID_DIRECTIONS
    .map(dir => (worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ), dir))
    .foreach { case (tube: IVacuumTube, dir) =>
      if (tube.isOutput(UNKNOWN)) {
        if (addInput(dir)) {
          return true
        }
      }
    case (_, dir)                            =>
      check += dir
             }

    check.exists(addInput)
  }

  def searchForOutput(): Boolean = {
    val check = new ArrayBuffer[ForgeDirection]
    ForgeDirection
    .VALID_DIRECTIONS
    .map(dir => (worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ), dir))
    .foreach { case (tube: IVacuumTube, dir) =>
      if (tube.isInput(UNKNOWN)) {
        if (addOutput(dir)) {
          return true
        }
      }
    case (_, dir)                            =>
      check += dir
             }

    check.exists(addOutput)
  }

  def searchForFullConnections() {
    val check = new ArrayBuffer[ForgeDirection]
    ForgeDirection
    .VALID_DIRECTIONS
    .map(dir => (worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ), dir))
    .foreach { case (tube: IVacuumTube, dir) =>
      if (tube.isInput(UNKNOWN) && missingOutput && addOutput(dir) ||
          tube.isOutput(UNKNOWN) && missingInput && addInput(dir)) {
      }
    case (_, dir)                            =>
      check += dir
             }

    check.forall(dir => missingOutput && addOutput(dir) || missingInput && addInput(dir))
  }

  def searchForConnection(): Boolean = {
    cycleSearch()
    false
  }

  private def cycleSearch() {
    var i: Int = 0
    if (missingInput && !missingOutput) {
      val temp = outputDir.getOpposite
      clearOutput()
      addInput(temp)
      return
    } else if (missingOutput && !missingInput) {
      val temp = inputDir.getOpposite
      clearInput()
      addOutput(temp)
      return
    }
    var lastOutputOrientation = FemtocraftUtils.indexOfForgeDirection(outputDir)
    var lastInputOrientation = FemtocraftUtils.indexOfForgeDirection(inputDir)
    do {
      lastOutputOrientation += 1
      clearOutput()
      if (lastOutputOrientation >= 6) {
        var j: Int = 0
        lastOutputOrientation = 0
        do {
          lastInputOrientation += 1
          clearInput()
          if (lastInputOrientation >= 6) {
            lastInputOrientation = 0
          }
          j += 1
        } while (!addInput(getOrientation(lastInputOrientation)) && (j < 6))
      }
      i += 1
    } while (!addOutput(getOrientation(lastOutputOrientation)) && (i < 6))
  }

  def validateConnections() {
    if (inputDir != UNKNOWN) {
      val tile = worldObj.getTileEntity(xCoord + inputDir.offsetX, yCoord + inputDir.offsetY, zCoord + inputDir.offsetZ)
      if (tile == null) {
        inputTile = null
        setUpdate()
        markDirty()
        inputDir = UNKNOWN
      } else {
        addInput(inputDir)
      }
    }
    if (outputDir != UNKNOWN) {
      val tile = worldObj
                 .getTileEntity(xCoord + outputDir.offsetX, yCoord + outputDir.offsetY, zCoord + outputDir.offsetZ)
      if (tile == null) {
        outputTile = null
        setUpdate()
        markDirty()
        outputDir = UNKNOWN
      } else {
        addOutput(outputDir)
      }
    }
  }

  def OnItemEntityCollision(item: EntityItem) {
    if (queuedItem != null) {
      return
    }
    if (!missingInput) {
      return
    }
    if (!canAcceptItemStack(item.getEntityItem)) {
      return
    }
    if (insertItem(ItemStack.copyItemStack(item.getEntityItem), inputDir)) {
      worldObj.removeEntity(item)
      setUpdate()
      markDirty()
    }
  }

  def insertItem(item: ItemStack, dir: ForgeDirection): Boolean = {
    if (item == null) return true
    if (isOverflowing) {
      return false
    }
    if (queuedItem == null) {
      queuedItem = item.copy
      if (items.getStackInSlot(0) == null) {
        items.setInventorySlotContents(0, queuedItem)
        queuedItem = null
        hasItem(0) = true
        setUpdate()
      }
      markDirty()
      return true
    }
    false
  }

  def onNeighborTileChange() {
    canFillInv = true
    canExtractInv = true
    validateConnections()
    if (inputDir == UNKNOWN) {
      searchForInput()
    }
    if (outputDir == UNKNOWN) {
      searchForOutput()
    }
  }

  def getInput = inputDir

  def getOutput = outputDir

  private def removeLastInput() {
    if (inputTile == null) {
      return
    }
    clearInput()
    setUpdate()
  }

  private def removeLastOutput() {
    if (outputTile == null) {
      return
    }
    clearOutput()
    setUpdate()
  }

}
