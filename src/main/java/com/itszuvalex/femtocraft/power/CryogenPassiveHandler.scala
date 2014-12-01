package com.itszuvalex.femtocraft.power

import com.itszuvalex.femtocraft.api.power.ICryogenHandler
import com.itszuvalex.femtocraft.power.CryogenPassiveHandler._
import net.minecraft.init.Blocks._
import net.minecraft.world.World

/**
 * Created by Chris on 9/8/2014.
 */
object CryogenPassiveHandler {
  var powerPerTickForSnow: Float = 2f / 4f
  var powerPerTickForIce : Float = 5f / 4f
}

class CryogenPassiveHandler extends ICryogenHandler {
  def canHandleBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    val block = world.getBlock(x, y, z)
    block == ice || block == snow
  }

  def powerForBlock(world: World, x: Int, y: Int, z: Int): Float = {
    val block = world.getBlock(x, y, z)
    if (block == ice) powerPerTickForIce else if (block == snow) powerPerTickForSnow else 0
  }

  def usedBlockForPower(world: World, x: Int, y: Int, z: Int) {
  }
}
