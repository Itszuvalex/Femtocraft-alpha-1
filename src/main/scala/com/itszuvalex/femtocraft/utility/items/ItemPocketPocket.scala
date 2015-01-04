package com.itszuvalex.femtocraft.utility.items

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.items.ItemBase
import com.itszuvalex.femtocraft.utility.items.ItemPocketPocket._
import com.itszuvalex.femtocraft.utility.{BlockAndTileSnapshot, SpatialRelocation}
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.IIcon
import net.minecraft.world.World

import scala.util.Random

/**
 * Created by Itszuvalex on 1/1/15.
 */
object ItemPocketPocket {
  val NBTStoredSnapshotTag          = "BlockAndTileSnapshot"
  val NBTStoredSnapshotBlockNameTag = "BlockSnapshotName"
}

class ItemPocketPocket extends ItemBase("ItemPocketPocket") {
  setMaxStackSize(1)

  var emptyIcon : IIcon = null
  var filledIcon: IIcon = null

  private def getSnapshotBlockName(itemStack: ItemStack): String = {
    if (itemStack == null) return ""
    if (itemStack.stackTagCompound == null) return ""
    itemStack.stackTagCompound.getString(NBTStoredSnapshotBlockNameTag)
  }

  override def getIcon(stack: ItemStack, renderPass: Int, player: EntityPlayer, usingItem: ItemStack,
                       useRemaining: Int): IIcon =  {
    if (getSnapshotBlockName(stack).isEmpty) {
      emptyIcon
    } else {
      filledIcon
    }
  }

  override def getIconIndex(itemStack: ItemStack): IIcon = {
    if (getSnapshotBlockName(itemStack).isEmpty) {
      emptyIcon
    } else {
      filledIcon
    }
  }

  override def registerIcons(register: IIconRegister): Unit = {
    emptyIcon = register.registerIcon(Femtocraft.ID + ":" + "ItemPocketPocket_empty")
    filledIcon = register.registerIcon(Femtocraft.ID + ":" + "ItemPocketPocket_filled")
  }

  override def onItemUse(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, facing: Int,
                         hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (getSnapshotBlockName(itemStack).isEmpty) {
      onItemUseEmpty(itemStack, player, world, x, y, z, facing, hitX, hitY, hitZ)
    } else {
      onItemUseFilled(itemStack, player, world, x, y, z, facing, hitX, hitY, hitZ)
    }
  }

  def onItemUseEmpty(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, facing: Int,
                     hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (!player.canPlayerEdit(x, y, z, facing, itemStack)) {
      return false
    }
    if (world.isRemote) {
      (0 until 8).foreach { i =>
        world
        .spawnParticle("portal",
                       x + (Random.nextDouble - 0.5D),
                       y + Random.nextDouble - 0.25D,
                       z + (Random.nextDouble - 0.5D),
                       (Random.nextDouble - 0.5D) * 2.0D,
                       -Random.nextDouble,
                       (Random.nextDouble - 0.5D) * 2.0D)
                          }
      return true
    }

    val snapshot = SpatialRelocation.extractBlock(world, x, y, z)
    if (snapshot == null) {
      false
    } else {
      setBlockSnapshotItemStack(itemStack, snapshot)
      setSnapshotBlockName(itemStack, snapshot.block.getLocalizedName)
      world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F)
      true
    }
  }

  def setBlockSnapshotItemStack(itemStack: ItemStack, snapshot: BlockAndTileSnapshot): Boolean = {
    if (itemStack == null) return false
    snapshot.saveToNBT(getSnapshotTagCompound(itemStack, true))
    true
  }

  private def getSnapshotTagCompound(itemStack: ItemStack, force: Boolean): NBTTagCompound = {
    if (itemStack == null) {
      return null
    }
    if (itemStack.stackTagCompound == null) {
      if (force) {
        itemStack.stackTagCompound = new NBTTagCompound
      } else {
        return null
      }
    }
    if (!itemStack.stackTagCompound.hasKey(NBTStoredSnapshotTag)) {
      if (force) {
        itemStack.stackTagCompound.setTag(NBTStoredSnapshotTag, new NBTTagCompound)
      } else {
        return null
      }
    }

    itemStack.stackTagCompound.getCompoundTag(NBTStoredSnapshotTag)
  }

  private def setSnapshotBlockName(itemStack: ItemStack, name: String): Unit = {
    if (itemStack == null) return
    if (itemStack.stackTagCompound == null) return
    itemStack.stackTagCompound.setString(NBTStoredSnapshotBlockNameTag, name)
  }

  def onItemUseFilled(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, facing: Int,
                      hitX: Float, hitY: Float, hitZ: Float): Boolean = {
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
    } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block
                                                                                                 .isReplaceable(world,
                                                                                                                offsetX,
                                                                                                                offsetY,
                                                                                                                offsetZ)) {
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

    if (world.isRemote) {
      (0 until 8).foreach { i =>
        world
        .spawnParticle("portal",
                       offsetX + (Random.nextDouble - 0.5D),
                       offsetY + Random.nextDouble - 0.25D,
                       offsetZ + (Random.nextDouble - 0.5D),
                       (Random.nextDouble - 0.5D) * 2.0D,
                       -Random.nextDouble,
                       (Random.nextDouble - 0.5D) * 2.0D)
                          }
      return true
    }
    if (!player.canPlayerEdit(offsetX, offsetY, offsetZ, side, itemStack)) {
      false
    } else if (y == 255 && snapshot.block.getMaterial.isSolid) {
      false
    } else if (SpatialRelocation.applySnapshot(snapshot, world, offsetX, offsetY, offsetZ)) {
      clearSnapshotItemStack(itemStack)
      clearSnapshotBlockName(itemStack)
      world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F)
      true
    } else {
      false
    }
  }

  def getBlockSnapshotItemStack(itemStack: ItemStack): BlockAndTileSnapshot = {
    val tag = getSnapshotTagCompound(itemStack, false)
    if (tag == null) return null
    BlockAndTileSnapshot.loadFromNBT(tag)
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

  override def addInformation(itemStack: ItemStack, player: EntityPlayer, tooltip: util.List[_],
                              advanced: Boolean): Unit = {
    val tt = tooltip.asInstanceOf[util.List[String]]
    val name = getSnapshotBlockName(itemStack)
    tt.add(if (name.isEmpty) "empty" else {"Contains " + name})
  }
}
