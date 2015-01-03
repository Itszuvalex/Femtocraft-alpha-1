package com.itszuvalex.femtocraft.api.power

import com.itszuvalex.femtocraft.api.EnumTechLevel

/**
 * @author Itszuvalex
 *
 *         Most generic power containment interface.  This simply provides the interface into the power system without worrying about sidedness.
 *
 *         Expected Behaviors ->
 *         Implemented by an Item =>  A PowerStackWrapper made with any itemstack with this item should be valid.
 *         Anywhere else =>   Any general case on your own.
 */
trait IPowerContainer {
  /**
   * @param level EnumTechLevel of power of container
   * @return True if can accept power of that level
   */
  def canAcceptPowerOfLevel(level: EnumTechLevel): Boolean

  /**
   * @return EnumTechLevel of container
   */
  def getTechLevel: EnumTechLevel

  /**
   * @return Current storage amount of container
   */
  def getCurrentPower: Int

  /**
   * @return Max storage amount of container - used for percentage approximations during charging
   */
  def getMaxPower: Int

  /**
   * @return Actual fill percentage - for things like damage values, etc.
   */
  def getFillPercentage: Float

  /**
   * @return Fill percentage for purposes of charging - allows tanks and whatnot to trick pipes into filling them I.E.
   *         return getFillPercentage() < .25f ? getFillPercentage() : .25f;
   */
  def getFillPercentageForCharging: Float

  /**
   * @return Fill percentage for purposes of output - allows tanks and other TileEntities to trick pipes into not
   *         pulling all of their power.
   */
  def getFillPercentageForOutput: Float

  /**
   * @return True if container has room and can accept charging false otherwise
   */
  def canCharge: Boolean

  /**
   * @param amount Amount attempting to charge.
   * @return Total amount of @amount used to fill internal tank.
   */
  def charge(amount: Int): Int

  /**
   * @param amount Amount of power to drain from internal storage
   * @return True if all power was consumed, false otherwise. This anticipates all or nothing behavior.
   */
  def consume(amount: Int): Boolean
}
