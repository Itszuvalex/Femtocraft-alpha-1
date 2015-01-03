package com.itszuvalex.femtocraft.api.power

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
trait IPhlegethonTunnelAddon extends IPhlegethonTunnelComponent {
  def getPowerContribution(core: IPhlegethonTunnelCore): Float
}
