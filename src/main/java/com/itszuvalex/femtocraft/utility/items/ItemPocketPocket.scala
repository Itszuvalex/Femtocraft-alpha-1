package com.itszuvalex.femtocraft.utility.items

import java.util

import com.itszuvalex.femtocraft.core.items.ItemBase
import com.itszuvalex.femtocraft.utility.items.ItemPocketPocket._
import com.itszuvalex.femtocraft.utility.{BlockAndTileSnapshot, SpatialRelocation}
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
 * Created by Itszuvalex on 1/1/15.
 */
object ItemPocketPocket {
  val NBTStoredSnapshotTag          = "BlockAndTileSnapshot"
  val NBTStoredSnapshotBlockNameTag = "BlockSnapshotName"
}

class ItemPocketPocket(unlocalizedName: String) extends ItemBase(unlocalizedName) {
  setMaxStackSize(1)

  override def onItemUse(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, facing: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (getSnapshotBlockName(itemStack).isEmpty)
      onItemUseEmpty(itemStack, player, world, x, y, z, facing, hitX, hitY, hitZ)
    else
      onItemUseFilled(itemStack, player, world, x, y, z, facing, hitX, hitY, hitZ)
  }

  def onItemUseEmpty(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, facing: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    val block: Block = world.getBlock(x, y, z)

    if (!player.canPlayerEdit(x, y, z, facing, itemStack)) {
      return false
    }
    if (world.isRemote) return true

    val snapshot = SpatialRelocation.extractBlock(world, x, y, z)
    if (snapshot == null)
      false
    else {
      setBlockSnapshotItemStack(itemStack, snapshot)
      setSnapshotBlockName(itemStack, snapshot.block.getLocalizedName)
      true
    }
  }

  def setBlockSnapshotItemStack(itemStack: ItemStack, snapshot: BlockAndTileSnapshot): Boolean = {
    if (itemStack == null) return false
    snapshot.saveToNBT(getSnapshotTagCompound(itemStack, true))
    true
  }

  private def setSnapshotBlockName(itemStack: ItemStack, name: String): Unit = {
    if (itemStack == null) return
    if (itemStack.stackTagCompound == null) return
    itemStack.stackTagCompound.setString(NBTStoredSnapshotBlockNameTag, name)
  }

  def onItemUseFilled(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, facing: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    val block: Block = world.getBlock(x, y, z)

    val snapshot = getBlockSnapshotItemStack(itemStack)
    if (snapshot == null) {
      clearSnapshotItemStack(itemStack)
      return true
    }

    var side = facing
    var offsetX = x
    var offsetY = y
    var offsetZ = z
    if (block == Blocks.snow_layer && (world.getBlockMetadata(offsetX, offsetY, offsetZ) & 7) < 1) {
      side = 1
    }
    else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, offsetX, offsetY, offsetZ)) {
      if (side == 0) {
        offsetY -= 1
      }
      if (side == 1) {
        offsetY += 1
      }
      if (side == 2) {
        offsetZ -= 1
      }
      if (side == 3) {
        offsetZ += 1
      }
      if (side == 4) {
        offsetX -= 1
      }
      if (side == 5) {
        offsetX += 1
      }
    }

    if (world.isRemote) return true

    if (!player.canPlayerEdit(offsetX, offsetY, offsetZ, side, itemStack)) {
      false
    }
    else if (y == 255 && snapshot.block.getMaterial.isSolid) {
      false
    }
    else if (SpatialRelocation.applySnapshot(snapshot, world, offsetX, offsetY, offsetZ)) {
      clearSnapshotItemStack(itemStack)
      clearSnapshotBlockName(itemStack)
      true
    }
    else {
      false
    }
  }

  def getBlockSnapshotItemStack(itemStack: ItemStack): BlockAndTileSnapshot = {
    val tag = getSnapshotTagCompound(itemStack, false)
    if (tag == null) return null
    BlockAndTileSnapshot.loadFromNBT(tag)
  }

  private def getSnapshotTagCompound(itemStack: ItemStack, force: Boolean): NBTTagCompound = {
    if (itemStack == null) {
      return null
    }
    if (itemStack.stackTagCompound == null) {
      if (force)
        itemStack.stackTagCompound = new NBTTagCompound
      else
        return null
    }
    if (!itemStack.stackTagCompound.hasKey(NBTStoredSnapshotTag)) {
      if (force)
        itemStack.stackTagCompound.setTag(NBTStoredSnapshotTag, new NBTTagCompound)
      else
        return null
    }

    itemStack.stackTagCompound.getCompoundTag(NBTStoredSnapshotTag)
  }

  private def clearSnapshotItemStack(itemStack: ItemStack): Unit = {
    if (itemStack == null) return
    if (itemStack.stackTagCompound == null) return
    if (!itemStack.stackTagCompound.hasKey(NBTStoredSnapshotTag)) return
    itemStack.stackTagCompound.removeTag(NBTStoredSnapshotTag)
  }

  private def clearSnapshotBlockName(itemStack: ItemStack): Unit = {
    if (itemStack == null) return
    if (itemStack.stackTagCompound == null) return
    itemStack.stackTagCompound.removeTag(NBTStoredSnapshotBlockNameTag)
  }

  private def getSnapshotBlockName(itemStack: ItemStack): String = {
    if (itemStack == null) return ""
    if (itemStack.stackTagCompound == null) return ""
    itemStack.stackTagCompound.getString(NBTStoredSnapshotBlockNameTag)
  }

  override def addInformation(itemStack: ItemStack, player: EntityPlayer, tooltip: util.List[_], advanced: Boolean): Unit = {
    val tt = tooltip.asInstanceOf[util.List[String]]
    val name = getSnapshotBlockName(itemStack)
    tt.add(if (name.isEmpty) "empty" else {"Contains " + name})
  }
}
