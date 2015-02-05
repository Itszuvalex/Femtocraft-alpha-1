package com.itszuvalex.femtocraft.industry.fabrication

import com.itszuvalex.femtocraft.industry.fabrication.resources.{ResourceFluid, ResourceItems, ResourcePower}
import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource

import scala.collection.mutable

/**
 * Created by Christopher on 1/19/2015.
 */
object ManagerFabricationSuite {
  val resourceNameMap = mutable.HashMap[Class[_ <: IResource[_,_]], String]()

  def getClassName(clazz: Class[_ <: IResource[_,_]]) = resourceNameMap.get(clazz)

  def init(): Unit = {
    registerClassNameMapping(classOf[ResourceFluid], "Fluids")
    registerClassNameMapping(classOf[ResourceItems], "Items")
    registerClassNameMapping(classOf[ResourcePower], "Power")
  }

  def registerClassNameMapping(clazz: Class[_ <: IResource[_,_]], name: String) = resourceNameMap.put(clazz, name)
}
