package com.itszuvalex.femtocraft.industry.fabrication

import com.itszuvalex.femtocraft.industry.fabrication.resources.{ResourceAssemblerSchematics, ResourceFluid, ResourceItemStack, ResourcePower}
import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource

import scala.collection.mutable

/**
 * Created by Christopher on 1/19/2015.
 */
object ManagerFabricationSuite {
  val resourceNameMap = mutable.HashMap[Class[_ <: IResource], String]()

  def getClassName(clazz: Class[_ <: IResource]) = resourceNameMap.get(clazz)

  def init(): Unit = {
    registerClassNameMapping(classOf[ResourceFluid], "Fluids")
    registerClassNameMapping(classOf[ResourceItemStack], "Items")
    registerClassNameMapping(classOf[ResourcePower], "Power")
    registerClassNameMapping(classOf[ResourceAssemblerSchematics], "Assembler Schematic")
  }

  def registerClassNameMapping(clazz: Class[_ <: IResource], name: String) = resourceNameMap.put(clazz, name)
}
