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
package com.itszuvalex.femtocraft.industry.blocks

import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.traits.block.{DroppableInventory, RotateOnPlace}
import com.itszuvalex.femtocraft.industry.tiles.TileEntityNanoInnervator
import com.itszuvalex.femtocraft.render.RenderSimpleMachine
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.inventory.{Container, IInventory}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.{IIcon, MovingObjectPosition}
import net.minecraft.world.World

object BlockNanoInnervator {
  /**
   * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
   * furnace block changes from idle to active and vice-versa.
   */
  private var keepFurnaceInventory: Boolean = false

  def updateFurnaceBlockState(active: Boolean, world: World, x: Int, y: Int, z: Int) {
    val l = world.getBlockMetadata(x, y, z)
    val tileentity = world.getTileEntity(x, y, z)
    keepFurnaceInventory = true
    TileContainer.shouldDrop = false
    world.setBlock(x, y, z, if (active) Femtocraft.blockNanoInnervatorLit else Femtocraft.blockNanoInnervatorUnlit)
    TileContainer.shouldDrop = true
    keepFurnaceInventory = false
    world.setBlockMetadataWithNotify(x, y, z, l, 2)
    if (tileentity != null) {
      tileentity.validate()
      world.setTileEntity(x, y, z, tileentity)
    }
  }
}

class BlockNanoInnervator(private val active: Boolean) extends TileContainer(Material.iron) with DroppableInventory with RotateOnPlace {
  /**
   * Is the random generator used by furnace to drop the inventory contents in random directions.
   */
  private val furnaceRand      = new Random
  private var frontIcon: IIcon = null

  setHardness(3.5f)
  setStepSound(Block.soundTypeMetal)
  setCreativeTab(Femtocraft.femtocraftTab)
  if (active) setLightLevel(0.875F)

  override def renderAsNormalBlock = false

  override def getRenderType = RenderSimpleMachine.renderID

  @SideOnly(Side.CLIENT) override def getIcon(par1: Int, par2: Int) = if (par1 == par2) frontIcon else blockIcon

  override def getPickBlock(target: MovingObjectPosition, world: World, x: Int, y: Int, z: Int) = new ItemStack(Femtocraft.blockNanoInnervatorUnlit)

  override def getItemDropped(p_149650_1_ : Int, p_149650_2_ : Random, p_149650_3_ : Int) = Item.getItemFromBlock(Femtocraft.blockNanoInnervatorUnlit)

  @SideOnly(Side.CLIENT) override def randomDisplayTick(par1World: World, par2: Int, par3: Int, par4: Int, par5Random: Random) {
    if (active) {
      val l: Int = par1World.getBlockMetadata(par2, par3, par4)
      val f: Float = par2.toFloat + 0.5F
      val f1: Float = par3.toFloat + 0.0F + par5Random.nextFloat * 6.0F / 16.0F
      val f2: Float = par4.toFloat + 0.5F
      val f3: Float = 0.52F
      val f4: Float = par5Random.nextFloat * 0.6F - 0.3F
      if (l == 4) {
        par1World.spawnParticle("smoke", (f - f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
        par1World.spawnParticle("flame", (f - f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
      }
      else if (l == 5) {
        par1World.spawnParticle("smoke", (f + f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
        par1World.spawnParticle("flame", (f + f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
      }
      else if (l == 2) {
        par1World.spawnParticle("smoke", (f + f4).toDouble, f1.toDouble, (f2 - f3).toDouble, 0.0D, 0.0D, 0.0D)
        par1World.spawnParticle("flame", (f + f4).toDouble, f1.toDouble, (f2 - f3).toDouble, 0.0D, 0.0D, 0.0D)
      }
      else if (l == 3) {
        par1World.spawnParticle("smoke", (f + f4).toDouble, f1.toDouble, (f2 + f3).toDouble, 0.0D, 0.0D, 0.0D)
        par1World.spawnParticle("flame", (f + f4).toDouble, f1.toDouble, (f2 + f3).toDouble, 0.0D, 0.0D, 0.0D)
      }
    }
  }

  /**
   * If this returns true, then comparators facing away from this block will use the value from
   * getComparatorInputOverride instead of the actual redstone signal strength.
   */
  override def hasComparatorInputOverride = true

  /**
   * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
   * strength when this block inputs to a comparator.
   */
  override def getComparatorInputOverride(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) = Container.calcRedstoneFromInventory(par1World.getTileEntity(par2, par3, par4).asInstanceOf[IInventory])

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "NanoMachineBlock_side")
    frontIcon = par1IconRegister.registerIcon(if (active) Femtocraft.ID.toLowerCase + ":" + "NanoInnervator_front_lit" else Femtocraft.ID.toLowerCase + ":" + "NanoInnervator_front_unlit")
  }

  /**
   * Returns a new instance of a block's tile entity class. Called on placing the block.
   */
  override def createNewTileEntity(par1World: World, metadata: Int) = new TileEntityNanoInnervator

  /**
   * ejects contained items into the world, and notifies neighbours of an update, as appropriate
   */
  override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, metadata: Int) {
    val invDrop = shouldDrop
    if (!BlockNanoInnervator.keepFurnaceInventory) {
      world.getTileEntity(x, y, z) match {
        case te: TileEntityNanoInnervator if te.isWorking => FemtocraftUtils.dropItem(te.smeltingStack, world, x, y, z, furnaceRand)
        case _                                            =>
      }
      world.func_147453_f(x, y, z, block)
      shouldDrop = true
    }
    else {
      shouldDrop = false
    }
    super.breakBlock(world, x, y, z, block, metadata)
    shouldDrop = invDrop
  }

}
