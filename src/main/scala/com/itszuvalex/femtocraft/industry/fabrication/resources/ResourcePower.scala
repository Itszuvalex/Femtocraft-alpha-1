package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.api.power.{IPowerContainer, PowerContainer}
import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourcePower(private val container: IPowerContainer) extends IResource[IPowerContainer, Int] {
  override def resource = container

  override def utilize(resource: Int) = new ResourcePower(new PowerContainer(container.getTechLevel, if (container.consume(resource)) resource else 0))

  override def resourceName = if (container == null) "Power" else container.getTechLevel.key + " Power"

  override def amount = container.getCurrentPower
}
