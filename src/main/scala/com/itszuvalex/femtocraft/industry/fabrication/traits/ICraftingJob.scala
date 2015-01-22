package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util
import java.util.UUID

/**
 * Created by Itszuvalex on 1/19/15.
 */
trait ICraftingJob extends IWorkJob {

  def parent: UUID

  def children: util.Collection[UUID]

}
