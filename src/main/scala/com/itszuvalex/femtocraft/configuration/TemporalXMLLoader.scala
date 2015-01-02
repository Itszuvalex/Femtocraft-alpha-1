package com.itszuvalex.femtocraft.configuration

import java.io.File

import com.itszuvalex.femtocraft.api.TemporalRecipe
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.mutable.ArrayBuffer
import scala.xml.Elem


/**
 * Created by Chris on 12/8/2014.
 */
class TemporalXMLLoader(file: File) extends CustomXMLLoader[TemporalRecipe](file) {
  override def loadItemsFromXML(xml: Elem): ArrayBuffer[TemporalRecipe] = {
    val ret = new ArrayBuffer[TemporalRecipe]
    val recipes = xml \ XMLSerializable.temporalRecipeTag
    ret ++= recipes.map(_.getTemporalRecipe)
  }
}
