package com.itszuvalex.femtocraft.configuration

import java.io.File

import com.itszuvalex.femtocraft.configuration.XMLSerializable._
import com.itszuvalex.femtocraft.managers.research.Technology

import scala.collection.mutable.ArrayBuffer
import scala.xml.Elem


/**
 * Created by Chris on 12/8/2014.
 */
class TechnologyXMLLoader(file: File) extends CustomXMLLoader[Technology](file) {
  override def loadItemsFromXML(xml: Elem): ArrayBuffer[Technology] = {
    val ret = new ArrayBuffer[Technology]
    val recipes = xml \ XMLSerializable.technologyTag
    ret ++= recipes.map(_.getTechnology)
  }
}
