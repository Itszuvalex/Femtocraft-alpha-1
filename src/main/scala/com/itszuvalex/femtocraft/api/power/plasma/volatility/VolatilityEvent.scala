package com.itszuvalex.femtocraft.api.power.plasma.volatility

import com.itszuvalex.femtocraft.api.FemtocraftAPI
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
abstract class VolatilityEvent(private val _triggeringFlow: IPlasmaFlow, private val _volatilityLevel: Int,
                               private val _volatilityEnergy: Long)
  extends IVolatilityEvent {
  _triggeringFlow
  .setTemperature((_triggeringFlow.getTemperature - _volatilityEnergy / FemtocraftAPI
                                                                        .getPlasmaManager
                                                                        .temperatureToEnergy).toLong)
  
  def triggeringFlow = _triggeringFlow
  
  def volatilityLevel = _volatilityLevel
  
  def volatilityEnergy = _volatilityEnergy
}
