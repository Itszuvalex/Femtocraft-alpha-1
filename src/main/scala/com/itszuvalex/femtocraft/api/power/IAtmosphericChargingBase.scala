package com.itszuvalex.femtocraft.api.power

import com.itszuvalex.femtocraft.api.EnumTechLevel
import net.minecraft.world.World

/**
 * Implemented by the Block class, not the TileEntity
 */
trait IAtmosphericChargingBase {
  /**
   * Maximum number of addons supported by this charging base.
   *
   * @param world
   * @param x
   * @param y
   * @param z
   * @return
   */
  def maxAddonsSupported(world: World, x: Int, y: Int, z: Int): Int

  /**
   * Maximum techLevel of addons supportable by this charging base.
   *
   * @param world
   * @param x
   * @param y
   * @param z
   * @return
   */
  def maxTechSupported(world: World, x: Int, y: Int, z: Int): EnumTechLevel
}
