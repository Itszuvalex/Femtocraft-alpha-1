package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util

import com.itszuvalex.femtocraft.api.AssemblerRecipe
import com.itszuvalex.femtocraft.configuration.XMLRecipes._
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.xml.{PrettyPrinter, XML}

object XMLRecipes {
  val recipeNode = "AssemblerRecipe"
}

/**
 * Created by Chris on 12/5/2014.
 */
class XMLRecipes(file: File) {
  val initialized: Boolean = file.exists
  if (!initialized) {
    XML.save(file.getPath, <xml></xml>)
  }
  private var xml = XML.loadFile(file)

  def loadCustomRecipes(): util.Collection[AssemblerRecipe] = {
    val recipes = xml \ recipeNode
    recipes.map(_.getAssemblerRecipe).asJavaCollection
  }

  def seedInitialRecipes(recipes: util.Collection[AssemblerRecipe]) = {
    recipes.foreach(recipe => xml = xml.copy(child = xml.child ++ recipe.toNode))
    save()
  }

  private def save(): Unit = {
    val pp = new PrettyPrinter(80, 2)
    XML.save(file.getPath, XML.loadString(pp.format(xml)))
  }
}
