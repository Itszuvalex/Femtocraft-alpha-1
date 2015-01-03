package com.itszuvalex.femtocraft.api.power

import com.itszuvalex.femtocraft.api.EnumTechLevel
import net.minecraft.world.World

/**
 * Implemented by the Block class, not the TileEntity.
 */
trait IAtmosphericChargingAddon {
  /**
   * Returns the amount of power generated this tick from this block.
   *
   * @param world
   * @param x
   * @param y
   * @param z
   * @return
   */
  def powerPerTick(world: World, x: Int, y: Int, z: Int): Float

  /**
   * What techlevel this addon is.
   *
   * @param world
   * @param x
   * @param y
   * @param z
   * @return
   */
  def techLevel(world: World, x: Int, y: Int, z: Int): EnumTechLevel

  /**
   * Returns true if this can support addon, false otherwise.
   *
   * @param addon
   * @return
   */
  def canSupportAddon(addon: IAtmosphericChargingAddon, world: World, x: Int, y: Int, z: Int): Boolean
}
