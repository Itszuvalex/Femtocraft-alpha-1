package com.itszuvalex.femtocraft.api.transport

import net.minecraftforge.fluids.IFluidHandler

/**
 * @author Itszuvalex
 */
trait ISuctionPipe extends IFluidHandler {
  /**
   * @return Pressure of this pipe. Liquids will flow from high pressure to low pressure pipes.
   */
  def getPressure: Int
}
