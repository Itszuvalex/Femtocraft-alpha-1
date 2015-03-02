package com.itszuvalex.femtocraft.api.industry

import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 2/24/15.
 */
trait ILaserInteractable {
  /**
   *
   * @param dir Direction laser is coming from.
   * @param strength Strength of laser at this block.
   * @param modulation Modulation of laser.
   * @param distance Distance of laser at this block.
   */
  def interact(dir: ForgeDirection, strength: Int, modulation:String, distance: Int)

}
