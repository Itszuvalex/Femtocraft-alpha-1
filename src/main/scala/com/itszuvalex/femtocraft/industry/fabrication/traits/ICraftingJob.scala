package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

import com.itszuvalex.femtocraft.api.core.ISaveable

/**
 * Created by Itszuvalex on 1/19/15.
 */
trait ICraftingJob extends ISaveable {

  def parent: ICraftingJob

  def children: util.Collection[ICraftingJob]

}
