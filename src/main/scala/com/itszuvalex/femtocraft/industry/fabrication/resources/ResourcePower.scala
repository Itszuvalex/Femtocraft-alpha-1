package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.api.power.{IPowerContainer, PowerContainer}
import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourcePower(private val container: IPowerContainer) extends IResource[IPowerContainer, Int] {
  override def resourceName = if (resource == null) "Power" else resource.getTechLevel.key + " Power"

  override def contains(other: IResource[IPowerContainer, Int]) =
    if (equivalent(other))
      amount >= other.amount
    else
      false

  override def amount = resource.getCurrentPower

  override def resource = container

  /**
   *
   * @param other
   * @return True if this is equivalent with resource.  Example, power storage with same tier, item stack with same id and damage, etc.
   */
  override def equivalent(other: IResource[IPowerContainer, Int]) =
    if (other.resource == null && resource != null) false
    else if (resource == null && other.resource != null) false
    else other.resource.getTechLevel == resource.getTechLevel

  override def utilize(other: IResource[IPowerContainer, Int]) = if (equivalent(other)) utilize(other.amount) else utilize(0)

  override def utilize(other: Int) = new ResourcePower(new PowerContainer(resource.getTechLevel, if (resource.consume(other)) other else 0))
}
