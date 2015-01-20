package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

/**
 * Created by Christopher on 1/19/2015.
 */
trait IRequester extends IFabricationSuiteNode {

  def getRequestTypes: util.Collection[Class[IResource]]

  def getRequestGroups[T <: IResource](clazz: Class[T]): util.Map[String, IResourceGroup[T]]

  def providers: util.Collection[IProvider]

  def addProvider(provider: IProvider)

  def removeProvider(provider: IProvider)
}
