package com.itszuvalex.femtocraft.industry.fabrication.traits

/**
 * Created by Christopher on 1/22/2015.
 */
trait IRenewableResource[C, T] extends IResource[C, T] {

  /**
   * Renew this resource, as is, replenishing only amount.
   *
   * @param amount Amount to renew
   * @return Amount of resources used to renew.
   */
  def renew(amount: T): T

  /**
   * Renew this resource with a given source.  Used for comparison against things like power type, fluid type, etc.
   *
   * @param source Source of incoming resources.
   * @param amount Amount to renew
   * @return Amount of resources used to renew.
   */
  def renew(source: C, amount: T): T

  /**
   *
   * @param source Source to renew from.
   * @return True if can accept resources from source.
   */
  def canRenewFrom(source: C): Boolean

}
