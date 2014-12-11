package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util

import com.itszuvalex.femtocraft.api.TemporalRecipe
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._


/**
 * Created by Chris on 12/5/2014.
 */
class XMLTemporalRecipes(file: File) extends XMLLoaderWriter[TemporalRecipe](file) {
  def loadCustomRecipes(): util.Collection[TemporalRecipe] = {
    val recipes = xml \ XMLSerializable.temporalRecipeTag
    recipes.view.map(_.getTemporalRecipe).asJavaCollection
  }

  def seedInitialRecipes(recipes: util.Collection[TemporalRecipe]) = {
    recipes.view.foreach(recipe => xml = xml.copy(child = xml.child ++ recipe.toNode))
    save()
  }
}
