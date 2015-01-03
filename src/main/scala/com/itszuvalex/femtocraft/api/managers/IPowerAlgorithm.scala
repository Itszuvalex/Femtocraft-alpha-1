package com.itszuvalex.femtocraft.api.managers

import com.itszuvalex.femtocraft.api.power.IPowerTileContainer
import net.minecraft.world.World

/**
 * Created by Chris on 1/2/2015.
 */
trait IPowerAlgorithm {

  /**
   *
   * @param container Container to distribute power from.
   * @param world World containing container.
   * @param connections Boolean array of six 6, where the Array[ForgeDirection.ordinal()] tells the algorithm to attempt to distribute power in this direction (true), or not (false).
   *                    If null is passed in, it will manually check all 6 directions for IPowerTileContainers.
   * @param x X location of container in world
   * @param y Y location of container in world
   * @param z Z location of container in world
   */
  def distributePower(container: IPowerTileContainer, connections: Array[Boolean], world: World, x: Int, y: Int, z: Int)
}
