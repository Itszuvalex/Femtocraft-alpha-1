package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util

import com.itszuvalex.femtocraft.configuration.XMLSerializable._
import com.itszuvalex.femtocraft.managers.research.Technology

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._


/**
 * Created by Chris on 12/5/2014.
 */
class XMLTechnology(file: File) extends XMLRecipeLoaderWriter[Technology](file) {
  def loadCustomRecipes(): util.Collection[Technology] = {
    val recipes = xml \ XMLSerializable.technologyTag
    recipes.view.map(_.getTechnology).asJavaCollection
  }

  def seedInitialRecipes(recipes: util.Collection[Technology]) = {
    recipes.view.foreach(recipe => xml = xml.copy(child = xml.child ++ recipe.toNode))
    save()
  }
}
