package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

/**
 * Created by Itszuvalex on 1/20/15.
 */
trait ICraftingRecipes[C, T] extends IResource[C, T] {

  /**
   *
   * @return List of resources generated from this crafting node.
   */
  def result: util.Collection[IResource[C,T]]

  /**
   *
   * @return List of resources required to perform this crafting job.
   */
  def prerequisites: util.Collection[IResource[C,T]]
}
