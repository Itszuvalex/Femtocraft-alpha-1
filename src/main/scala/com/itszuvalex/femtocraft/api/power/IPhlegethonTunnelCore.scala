package com.itszuvalex.femtocraft.api.power

/**
 * Created by Christopher Harris (Itszuvalex) on 7/12/14.
 *
 * Use on any TileEntity that wishes to be able to be the managing core of the Phlegethon Tunnel.
 *
 * Used a looser Power containment Interface here, not sure why.
 */
trait IPhlegethonTunnelCore extends IPhlegethonTunnelComponent with IPowerContainer {
  /**
   * @return True if the Phlegethon Tunnel is active.
   */
  def isActive: Boolean

  /**
   * @return Base amount of power this core generates.
   */
  def getPowerGenBase: Float

  /**
   * Be careful when calling this from IPhlegethonTunnelAddon. This may cause a recursive loop as the core will
   * request all addon's power contributions.
   *
   * @return Amount of power this core generates, including all 6 neighboring addons.
   */
  def getTotalPowerGen: Float

  /**
   * @return Returns height of the Phlegethon Tunnel core.
   */
  def getHeight: Int

  /**
   * @return True if it successfully activates.  If it's already active, it should return false.
   */
  def activate: Boolean

  /**
   * @return True if it successfully deactivates.  If it's already inactive, it should return false;
   */
  def deactivate: Boolean
}
