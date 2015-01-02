package com.itszuvalex.femtocraft.core.traits.block

import net.minecraft.world.World

/**
 * Created by Itszuvalex on 1/1/15.
 */
trait SpatialReactions {

  def onPickup(world: World, x: Int, y: Int, z: Int)

  def onPlacement(world: World, x: Int, y: Int, z: Int)

}
