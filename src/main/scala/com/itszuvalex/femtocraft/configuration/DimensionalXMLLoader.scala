package com.itszuvalex.femtocraft.configuration

import java.io.File
import com.itszuvalex.femtocraft.api.industry.DimensionalRecipe
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.mutable.ArrayBuffer
import scala.xml.Elem


/**
 * Created by Chris on 12/8/2014.
 */
class DimensionalXMLLoader(file: File) extends CustomXMLLoader[DimensionalRecipe](file) {
  override def loadItemsFromXML(xml: Elem): ArrayBuffer[DimensionalRecipe] = {
    val ret = new ArrayBuffer[DimensionalRecipe]
    val recipes = xml \ XMLSerializable.dimensionalRecipeTag
    ret ++= recipes.map(_.getDimensionalRecipe)
  }
}
