package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

/**
 * Created by Christopher on 1/19/2015.
 */
trait IResourceGroup[T <: IResource] {

  def getName: String

  def getType = classOf[T]

  def getResources: util.Collection[T]

  def getSize: Int

}
