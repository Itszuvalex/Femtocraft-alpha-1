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
import com.itszuvalex.femtocraft.core.traits.block.{RotateOnPlace, DroppableInventory}
import com.itszuvalex.femtocraft.industry.tiles.TileEntityNanoFabricator
import com.itszuvalex.femtocraft.render.RenderSimpleMachine
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.inventory.{Container, IInventory}
import net.minecraft.util.IIcon
import net.minecraft.world.World

class BlockNanoFabricator extends TileContainer(Material.iron) with DroppableInventory with RotateOnPlace {
  /**
   * Is the random generator used by furnace to drop the inventory contents in random directions.
   */
  private                        val rand             = new Random
  private var frontIcon: IIcon = null

  setBlockName("BlockNanoFabricator")
  setHardness(3.5f)
  setStepSound(Block.soundTypeMetal)
  setCreativeTab(Femtocraft.femtocraftTab)

  override def renderAsNormalBlock = false

  override def getRenderType = RenderSimpleMachine.renderID

  @SideOnly(Side.CLIENT) override def getIcon(par1: Int, par2: Int) = if (par1 == par2) frontIcon else blockIcon

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
    frontIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "NanoFabricator_front")
  }

  /**
   * Returns a new instance of a block's tile entity class. Called on placing the block.
   */
  override def createNewTileEntity(par1World: World, metadata: Int) = new TileEntityNanoFabricator

  /**
   * ejects contained items into the world, and notifies neighbours of an update, as appropriate
   */
  override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, metadata: Int) {
    world.getTileEntity(x, y, z) match {
      case te: TileEntityNanoFabricator if te.isWorking => te.reconstructingStacks.foreach(FemtocraftUtils.dropItem(_, world, x, y, z, rand))
      case _                                            =>
    }
    world.func_147453_f(x, y, z, block)
    super.breakBlock(world, x, y, z, block, metadata)
  }
}