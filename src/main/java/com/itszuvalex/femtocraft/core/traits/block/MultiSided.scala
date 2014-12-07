package com.itszuvalex.femtocraft.core.traits.block

import com.itszuvalex.femtocraft.core.traits.block.MultiSided._
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.{IIcon, MathHelper}
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Chris on 12/6/2014.
 */
object MultiSided {
  //TODO : THIS DOES NOT WORK
  //     METADATA IS ONLY 16 POSSIBLE VALUES
  //    THIS REQURIES 24.  YOU WILL NEED TO GO TO TILE ENTITIES TO DO THIS.
  def getTopAndFrontFromMetadata(metadata: Byte): (ForgeDirection, ForgeDirection) = {
    val top = getOrientation(metadata / 4)
    var front = UNKNOWN
    (top, metadata % 4) match {
      //*****************************
      case (UP, 0) => front = NORTH
      case (UP, 1) => front = EAST
      case (UP, 2) => front = SOUTH
      case (UP, 3) => front = WEST
      //*****************************
      case (DOWN, 0) => front = SOUTH
      case (DOWN, 1) => front = WEST
      case (DOWN, 2) => front = NORTH
      case (DOWN, 3) => front = EAST
      //*****************************
      case (NORTH, 0) => front = UP
      case (NORTH, 1) => front = EAST
      case (NORTH, 2) => front = DOWN
      case (NORTH, 3) => front = WEST
      //*****************************
      case (SOUTH, 0) => front = DOWN
      case (SOUTH, 1) => front = WEST
      case (SOUTH, 2) => front = UP
      case (SOUTH, 3) => front = EAST
      //*****************************
      case (EAST, 0) => front = UP
      case (EAST, 1) => front = SOUTH
      case (EAST, 2) => front = DOWN
      case (EAST, 3) => front = NORTH
      //*****************************
      case (WEST, 0) => front = DOWN
      case (WEST, 1) => front = NORTH
      case (WEST, 2) => front = UP
      case (WEST, 3) => front = SOUTH
      //*****************************
      case _ =>
    }
    (top, front)
  }

  def getMetadataFromTopAndFront(top: ForgeDirection, front: ForgeDirection): Byte = {
    val ret: Byte = (top.ordinal * 4).toByte
    var s: Byte = 0
    (top, front) match {
      //*****************************
      case (UP, NORTH) => s = 0
      case (UP, EAST)  => s = 1
      case (UP, SOUTH) => s = 2
      case (UP, WEST)  => s = 3
      //*****************************
      case (DOWN, SOUTH) => s = 0
      case (DOWN, WEST)  => s = 1
      case (DOWN, NORTH) => s = 2
      case (DOWN, EAST)  => s = 3
      //*****************************
      case (NORTH, UP)   => s = 0
      case (NORTH, EAST) => s = 1
      case (NORTH, DOWN) => s = 2
      case (NORTH, WEST) => s = 3
      //*****************************
      case (SOUTH, DOWN) => s = 0
      case (SOUTH, WEST) => s = 1
      case (SOUTH, UP)   => s = 2
      case (SOUTH, EAST) => s = 3
      //*****************************
      case (EAST, UP)    => s = 0
      case (EAST, SOUTH) => s = 1
      case (EAST, DOWN)  => s = 2
      case (EAST, NORTH) => s = 3
      //*****************************
      case (WEST, DOWN)  => s = 0
      case (WEST, NORTH) => s = 1
      case (WEST, UP)    => s = 2
      case (WEST, SOUTH) => s = 3
      //*****************************
      case _ =>
    }
    (ret + s).toByte
  }

  def getRotatedSide(orig: ForgeDirection, top: ForgeDirection, front: ForgeDirection): ForgeDirection = {
    if (orig == UNKNOWN) return UNKNOWN

    (top, front) match {
      case (NORTH, dir) =>
        dir match {
          case UP   =>
            orig match {
              case UP    => NORTH
              case DOWN  => SOUTH
              case NORTH => UP
              case EAST  => WEST
              case SOUTH => DOWN
              case WEST  => EAST
              case _     => UNKNOWN
            }
          case EAST =>
            orig match {
              case UP    => EAST
              case DOWN  => WEST
              case NORTH => UP
              case EAST  => NORTH
              case SOUTH => DOWN
              case WEST  => SOUTH
              case _     => UNKNOWN
            }
          case DOWN =>
            orig match {
              case UP    => SOUTH
              case DOWN  => NORTH
              case NORTH => UP
              case EAST  => EAST
              case SOUTH => DOWN
              case WEST  => WEST
              case _     => UNKNOWN
            }
          case WEST =>
            orig match {
              case UP    => WEST
              case DOWN  => EAST
              case NORTH => UP
              case EAST  => SOUTH
              case SOUTH => DOWN
              case WEST  => NORTH
              case _     => UNKNOWN
            }
          case _    => UNKNOWN
        }
      case (UP, dir)    =>
        dir match {
          case NORTH => orig
          case EAST  =>
            orig match {
              case UP    => orig
              case DOWN  => orig
              case NORTH => WEST
              case EAST  => NORTH
              case SOUTH => EAST
              case WEST  => SOUTH
              case _     => UNKNOWN
            }
          case SOUTH => orig.getOpposite
          case WEST  =>
            orig match {
              case UP    => orig
              case DOWN  => orig
              case NORTH => EAST
              case EAST  => SOUTH
              case SOUTH => WEST
              case WEST  => NORTH
              case _     => UNKNOWN
            }
          case _     => UNKNOWN
        }
      case (EAST, dir)  =>
        dir match {
          case UP    =>
            orig match {
              case UP    => NORTH
              case DOWN  => SOUTH
              case NORTH => EAST
              case EAST  => UP
              case SOUTH => WEST
              case WEST  => DOWN
              case _     => UNKNOWN
            }
          case SOUTH =>
            orig match {
              case UP    => EAST
              case DOWN  => WEST
              case NORTH => SOUTH
              case EAST  => UP
              case SOUTH => NORTH
              case WEST  => DOWN
              case _     => UNKNOWN
            }
          case DOWN  =>
            orig match {
              case UP    => SOUTH
              case DOWN  => NORTH
              case NORTH => WEST
              case EAST  => UP
              case SOUTH => EAST
              case WEST  => DOWN
              case _     => UNKNOWN
            }
          case NORTH =>
            orig match {
              case UP    => WEST
              case DOWN  => EAST
              case NORTH => NORTH
              case EAST  => UP
              case SOUTH => SOUTH
              case WEST  => DOWN
              case _     => UNKNOWN
            }
          case _     => UNKNOWN
        }
      //*****************************
      case (UNKNOWN, _) => UNKNOWN
      case (_, UNKNOWN) => UNKNOWN
      case (t, dir)     => getRotatedSide(orig.getOpposite, t.getOpposite, dir.getOpposite)
      case _            => UNKNOWN
    }
  }
}

trait MultiSided extends Block {
  val icons = new Array[IIcon](6)

  /**
   * Called whenever the block is added into the world. Args: world, x, y, z
   */
  override def onBlockAdded(world: World, x: Int, y: Int, z: Int) {
    super.onBlockAdded(world, x, y, z)
    setDefaultDirection(world, x, y, z)
  }

  override def getIcon(side: Int, metadata: Int): IIcon = {
    val (top, front) = getTopAndFrontFromMetadata(metadata.toByte)
    getRotatedSide(ForgeDirection.getOrientation(side), top, front) match {
      case UNKNOWN => super.getIcon(side, metadata)
      case other   => icons(other.ordinal)
    }
  }

  override def registerBlockIcons(register: IIconRegister): Unit = {
    /*
    Assume north face is front.
    Assume up face is top.
     */
    icons(NORTH.ordinal()) = register.registerIcon(getTextureNameForSide(NORTH))
    icons(EAST.ordinal()) = register.registerIcon(getTextureNameForSide(EAST))
    icons(SOUTH.ordinal()) = register.registerIcon(getTextureNameForSide(SOUTH))
    icons(WEST.ordinal()) = register.registerIcon(getTextureNameForSide(WEST))
    icons(UP.ordinal()) = register.registerIcon(getTextureNameForSide(UP))
    icons(DOWN.ordinal()) = register.registerIcon(getTextureNameForSide(DOWN))
  }

  def getTextureNameForSide(side: ForgeDirection): String

  /**
   * set a blocks direction
   */
  private def setDefaultDirection(world: World, x: Int, y: Int, z: Int) {
    if (!world.isRemote) {
      val south = world.getBlock(x, y, z - 1)
      val north = world.getBlock(x, y, z + 1)
      val west = world.getBlock(x - 1, y, z)
      val east = world.getBlock(x + 1, y, z)
      val top = UP
      var front = UNKNOWN
      if (south.isOpaqueCube && !north.isOpaqueCube) {
        front = NORTH
      }
      if (north.isOpaqueCube && !south.isOpaqueCube) {
        front = SOUTH
      }
      if (west.isOpaqueCube && !east.isOpaqueCube) {
        front = EAST
      }
      if (east.isOpaqueCube && !west.isOpaqueCube) {
        front = WEST
      }
      world.setBlockMetadataWithNotify(x, y, z, getMetadataFromTopAndFront(top, front), 2)
    }
  }

  /**
   * Called when the block is placed in the world.
   */
  override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, entity: EntityLivingBase, itemStack: ItemStack) {
    super.onBlockPlacedBy(world, x, y, z, entity, itemStack)
    val mask = MathHelper.floor_double((entity.rotationYaw * 4.0F / 360.0F).toDouble + 0.5D) & 3
    if (mask == 0) {
      world.setBlockMetadataWithNotify(x, y, z, getMetadataFromTopAndFront(UP, SOUTH), 2) // SOUTH
    }
    if (mask == 1) {
      world.setBlockMetadataWithNotify(x, y, z, getMetadataFromTopAndFront(UP, EAST), 2) // EAST
    }
    if (mask == 2) {
      world.setBlockMetadataWithNotify(x, y, z, getMetadataFromTopAndFront(UP, NORTH), 2) // NORTH
    }
    if (mask == 3) {
      world.setBlockMetadataWithNotify(x, y, z, getMetadataFromTopAndFront(NORTH, WEST), 2) // WEST
    }
  }
}
