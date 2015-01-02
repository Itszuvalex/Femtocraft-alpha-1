package com.itszuvalex.femtocraft.configuration

import java.io.File
import com.itszuvalex.femtocraft.api.AssemblerRecipe
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.mutable.ArrayBuffer
import scala.xml.Elem


/**
 * Created by Chris on 12/8/2014.
 */
class AssemblerXMLLoader(file: File) extends CustomXMLLoader[AssemblerRecipe](file) {
  override def loadItemsFromXML(xml: Elem): ArrayBuffer[AssemblerRecipe] = {
    val ret = new ArrayBuffer[AssemblerRecipe]
    val recipes = xml \ XMLSerializable.assemblerRecipeTag
    ret ++= recipes.map(_.getAssemblerRecipe)
  }
}
