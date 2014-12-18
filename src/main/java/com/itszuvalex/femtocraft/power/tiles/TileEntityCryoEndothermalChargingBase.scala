package com.itszuvalex.femtocraft.power.tiles

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.power.{ICryoEndothermalChargingBase, PowerContainer}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.tiles.TileEntityCryoEndothermalChargingBase._
import com.itszuvalex.femtocraft.power.traits.PowerProducer
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Chris on 9/8/2014.
 */
object TileEntityCryoEndothermalChargingBase {
  @Configurable(comment = "Power capacity.")     val maxStorage = 25000
  @Configurable(comment = "Tech level of power") val powerLevel = EnumTechLevel.NANO
  @Configurable(comment = "Maximum number of addons supported") val maximumDepth = 15
}

@Configurable class TileEntityCryoEndothermalChargingBase extends TileEntityBase with PowerProducer with ICryoEndothermalChargingBase {
  override def defaultContainer = new PowerContainer(powerLevel, maxStorage)

  override def addPower(power: Int) = charge(ForgeDirection.DOWN, power)

  override def isTechLevelSupported(tech: EnumTechLevel) = powerLevel eq tech

  override def maximumDepthSupported() = maximumDepth
}
