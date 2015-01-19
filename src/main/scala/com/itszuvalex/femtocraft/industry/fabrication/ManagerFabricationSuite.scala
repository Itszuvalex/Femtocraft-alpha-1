package com.itszuvalex.femtocraft.industry.fabrication

import scala.collection.mutable

/**
 * Created by Christopher on 1/19/2015.
 */
object ManagerFabricationSuite {
  val classNameMap = mutable.HashMap[Class[_], String]()

  def registerClassNameMapping(clazz: Class[_], name: String) = classNameMap.put(clazz, name)

  def getClassName(clazz: Class[_]) = classNameMap.get(clazz)

}
