package com.itszuvalex.femtocraft.power

import com.itszuvalex.femtocraft.api.power.ICryogenHandler
import com.itszuvalex.femtocraft.power.CryogenActiveHandler._
import net.minecraft.init.Blocks._
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Chris on 9/8/2014.
 */
object CryogenActiveHandler {
  var waterToIcePower     = 100f
  var lavaToObsidianPower = 300f
  var airToSnowLayerPower = 10f
}

class CryogenActiveHandler extends ICryogenHandler {

  def canHandleBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    if (world.isAirBlock(x, y, z)) {
      world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, UP)
    }
    else {
      val block = world.getBlock(x, y, z)
      block == water || block == lava
    }
  }

  def powerForBlock(world: World, x: Int, y: Int, z: Int): Float = {
    if (world.isAirBlock(x, y, z)) {
      if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, UP)) {
        return airToSnowLayerPower
      }
    }
    else {
      val block = world.getBlock(x, y, z)
      if (block == water) {
        return waterToIcePower
      }
      else if (block == lava) {
        return lavaToObsidianPower
      }
    }
    0
  }

  def usedBlockForPower(world: World, x: Int, y: Int, z: Int) {
    if (world.isAirBlock(x, y, z)) {
      if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, UP)) {
        world.setBlock(x, y, z, snow_layer)
      }
    }
    else {
      val block = world.getBlock(x, y, z)
      if (block == water) {
        world.setBlock(x, y, z, ice)
      }
      else if (block == lava) {
        world.setBlock(x, y, z, obsidian)
      }
    }
  }
}
