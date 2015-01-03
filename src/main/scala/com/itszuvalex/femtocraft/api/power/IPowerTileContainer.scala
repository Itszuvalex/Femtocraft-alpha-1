package com.itszuvalex.femtocraft.api.power

import com.itszuvalex.femtocraft.api.EnumTechLevel
import net.minecraftforge.common.util.ForgeDirection

/**
 * @author Itszuvalex
 *
 *         Implemented by TileEntities that wish to manage OP power.  Simple implementations would be to forward all calls to
 *         a stored PowerContainer instance.  Advanced behavior can be achieved by manipulating the values of fill percentage for
 *         charging or output.  Femtocraft power generators report max(.75, getFillpercentage), whereas power consumers report min(.25, getFillPercentage).
 *         This ensure power is always flowing out of generators, and flowing into consumers.
 *
 *         To propagate power, every tick on the server side apply the IPowerAlgorithm distributePower on this tileEntity.  Optionally,
 *         pass a boolean[6] to flag those forge.ordinal == index directions to the algorithm to distribute power (true) or not (false).
 */
trait IPowerTileContainer {
  /**
   * @param level EnumTechLevel of power
   * @param from  Direction power will be coming from
   * @return True if can accept power of that level from the given direction
   */
  def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection): Boolean

  /**
   * @param to Direction from this Container
   * @return EnumTechLevel of power this machine will give to the given direction
   */
  def getTechLevel(to: ForgeDirection): EnumTechLevel

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
   * @param from Direction attempting to charge from.
   * @return Fill percentage for purposes of charging - allows tanks and whatnot to trick pipes into filling them I.E.
   *         return getFillPercentage() < .25f ? getFillPercentage() : .25f;
   */
  def getFillPercentageForCharging(from: ForgeDirection): Float

  /**
   * @param to Direction attempting to output to
   * @return Fill percentage for purposes of output - allows tanks and other TileEntities to trick pipes into not
   *         pulling all of their power.
   */
  def getFillPercentageForOutput(to: ForgeDirection): Float

  /**
   * @param from Direction attempting to input power from
   * @return True if container has room and can accept charging from direction 'from' false otherwise
   */
  def canCharge(from: ForgeDirection): Boolean

  /**
   * @param from Direction attempting to
   * @return True if container can be connected to from a given direction
   */
  def canConnect(from: ForgeDirection): Boolean

  /**
   * @param from   Direction charge is coming from.
   * @param amount Amount attempting to charge.
   * @return Total amount of @amount used to fill internal tank.
   */
  def charge(from: ForgeDirection, amount: Int): Int

  /**
   * @param amount Amount of power to drain from internal storage
   * @return True if all power was consumed, false otherwise. This anticipates all or nothing behavior.
   */
  def consume(amount: Int): Boolean
}
