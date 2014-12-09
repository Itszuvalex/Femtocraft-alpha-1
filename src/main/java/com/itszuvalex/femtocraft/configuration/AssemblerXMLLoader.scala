package com.itszuvalex.femtocraft.configuration

import java.io.File

import com.itszuvalex.femtocraft.api.AssemblerRecipe
import com.itszuvalex.femtocraft.configuration.AssemblerXMLLoader._
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.mutable.ArrayBuffer
import scala.xml.Elem

object AssemblerXMLLoader {
  val recipeNode = "AssemblerRecipe"
}

/**
 * Created by Chris on 12/8/2014.
 */
class AssemblerXMLLoader(file: File) extends CustomXMLLoader[AssemblerRecipe](file) {
  if (!file.exists) file.mkdirs()

  override def loadItemsFromXML(xml: Elem): ArrayBuffer[AssemblerRecipe] = {
    val ret = new ArrayBuffer[AssemblerRecipe]
    val recipes = xml \ recipeNode
    ret ++= recipes.map(_.getAssemblerRecipe)
  }
}
