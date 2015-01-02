package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util
import com.itszuvalex.femtocraft.api.AssemblerRecipe
import com.itszuvalex.femtocraft.configuration.XMLSerializable._

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

/**
 * Created by Chris on 12/5/2014.
 */
class XMLAssemblerRecipes(file: File) extends XMLLoaderWriter[AssemblerRecipe](file) {
  def loadCustomRecipes(): util.Collection[AssemblerRecipe] = {
    val recipes = xml \ XMLSerializable.assemblerRecipeTag
    recipes.view.map(_.getAssemblerRecipe).asJavaCollection
  }

  def seedInitialRecipes(recipes: util.Collection[AssemblerRecipe]) = {
    recipes.view.foreach(recipe => xml = xml.copy(child = xml.child ++ recipe.toNode))
    save()
  }
}
