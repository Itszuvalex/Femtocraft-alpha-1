package com.itszuvalex.femtocraft.api.power

import com.itszuvalex.femtocraft.api.EnumTechLevel

/**
 * Created by Christopher Harris (Itszuvalex) on 11/1/14.
 */
trait ICryoEndothermalChargingBase {
  /**
   * @param power Amount of power to add
   * @return Amount of power used from power.
   */
  def addPower(power: Int): Int

  /**
   * @param tech tech level
   * @return True if this tech level is supported or not.
   */
  def isTechLevelSupported(tech: EnumTechLevel): Boolean

  /**
   *
   * @return The maximum number of ICryoendothermalChargingCoils that are supported by this charging base.
   */
  def maximumDepthSupported: Int
}
