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
package com.itszuvalex.femtocraft.power.plasma

import java.util
import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.util.{AxisAlignedBB, DamageSource}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
object BlockPlasma {
  @Configurable val damagePerTick: Float = .2f
}

@Configurable class BlockPlasma extends BlockContainer(Material.lava) {
  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockUnbreakable()
  setBlockTextureName(Femtocraft.ID.toLowerCase + ":" + "BlockPlasma")

  override def renderAsNormalBlock = false

  override def getBlocksMovement(par1IBlockAccess: IBlockAccess, par2: Int, par3: Int, par4: Int) = false

  override def getCollisionBoundingBoxFromPool(par1World: World, par2: Int, par3: Int, par4: Int) = null

  override def isOpaqueCube = false

  override def updateTick(par1World: World, par2: Int, par3: Int, par4: Int, par5Random: Random) {
    super.updateTick(par1World, par2, par3, par4, par5Random)
    par1World.getEntitiesWithinAABB(classOf[Entity], AxisAlignedBB.getBoundingBox(par2, par3, par4, 1, 1, 1)).
    asInstanceOf[util.List[Entity]].foreach(_.attackEntityFrom(DamageSource.inFire, BlockPlasma.damagePerTick))
  }

  override def getRenderBlockPass = 1

  override def onEntityWalking(par1World: World, par2: Int, par3: Int, par4: Int, par5Entity: Entity) {
    super.onEntityWalking(par1World, par2, par3, par4, par5Entity)
    applyEffects(par5Entity)
  }

  override def onEntityCollidedWithBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5Entity: Entity) {
    super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity)
    applyEffects(par5Entity)
  }

  private def applyEffects(entity: Entity) {
    if (!entity.isBurning) {
      entity.setFire(4)
    }
  }

  override def getMobilityFlag = 2

  override def onFallenUpon(par1World: World, par2: Int, par3: Int, par4: Int, par5Entity: Entity, par6: Float) {
    super.onFallenUpon(par1World, par2, par3, par4, par5Entity, par6)
    applyEffects(par5Entity)
  }

  override def isReplaceable(world: IBlockAccess, x: Int, y: Int, z: Int) = false

  override def isBurning(world: IBlockAccess, x: Int, y: Int, z: Int) = true

  override def isFireSource(world: World, x: Int, y: Int, z: Int, side: ForgeDirection) = true

  override def onBlockAdded(par1World: World, par2: Int, par3: Int, par4: Int) {
    super.onBlockAdded(par1World, par2, par3, par4)
    par1World.getEntitiesWithinAABB(classOf[Entity], AxisAlignedBB.getBoundingBox(par2, par3, par4, 1, 1, 1)).asInstanceOf[util.List[Entity]].
    foreach(applyEffects)
  }

  def createNewTileEntity(world: World, metadata: Int) = new TileEntityPlasma
}
