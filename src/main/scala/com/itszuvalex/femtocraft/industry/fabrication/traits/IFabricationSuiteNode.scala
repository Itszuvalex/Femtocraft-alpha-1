package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util.UUID

/**
 * Created by Christopher on 1/19/2015.
 */
trait IFabricationSuiteNode {

  def onEvent(event: IFabricationSuiteEvent)

  def ID: UUID

}
