package com.itszuvalex.femtocraft.industry.fabrication

import java.util

/**
 * Created by Christopher on 1/19/2015.
 */
trait IResourceGroup[T >: Class[IResource]] {

  def getName: String

  def getType: T

  def getResources: util.Collection[IResource]

  def getSize: Int

}
