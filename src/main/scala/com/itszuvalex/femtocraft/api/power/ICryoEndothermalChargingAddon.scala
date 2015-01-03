package com.itszuvalex.femtocraft.api.power

/**
 * Created by Christopher Harris (Itszuvalex) on 11/1/14.
 * <p/>
 * Implemented by the TileEntity
 */
trait ICryoEndothermalChargingAddon {
  /**
   * Propagate power.
   *
   * @param power amount of power to propagate upwards.
   *              <p/>
   *              This is called whenever an addon wishes to propagate power up the addon stack.  If the tile above
   *              you is a base, call , otherwise call on the above you.
   */
  def propagatePower(power: Int)
}
