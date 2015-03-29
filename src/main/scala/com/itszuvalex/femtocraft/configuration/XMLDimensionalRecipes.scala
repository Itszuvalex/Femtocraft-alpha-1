package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util
import com.itszuvalex.femtocraft.api.industry.DimensionalRecipe
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._


/**
 * Created by Chris on 12/5/2014.
 */
class XMLDimensionalRecipes(file: File) extends XMLRecipeLoaderWriter[DimensionalRecipe](file) {
  def loadCustomRecipes(): util.Collection[DimensionalRecipe] = {
    val recipes = xml \ XMLSerializable.dimensionalRecipeTag
    recipes.view.map(_.getDimensionalRecipe).asJavaCollection
  }

  def seedInitialRecipes(recipes: util.Collection[DimensionalRecipe]) = {
    recipes.view.foreach(recipe => xml = xml.copy(child = xml.child ++ recipe.toNode))
    save()
  }
}
