package com.itszuvalex.femtocraft.industry.fabrication.traits

/**
 * Created by Christopher on 1/22/2015.
 */
trait IConsumableResource[C, T] extends IResource[C, T] {

  /**
   *
   * @param amount Amount to consume
   * @return True if resources consumed successfully.
   */
  def consume(amount: T): Boolean
}
