package com.itszuvalex.femtocraft.industry

import java.util

import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * Created by Christopher on 2/20/2015.
 */
object LaserRegistry {

  val laserInfoMap = new mutable.HashMap[String, LaserInfo]

  def getModulations: util.Collection[String] = laserInfoMap.keySet

  def getColor(modulation: String): Int = laserInfoMap.get(modulation) match {
    case Some(info) => info.color
    case _          => FemtocraftUtils.colorFromARGB(255, 0, 0, 0)
  }

  def registerLaser(modulation: String, color: Int) = laserInfoMap(modulation) = new LaserInfo(color)

  private class LaserInfo(val color: Int)

}
