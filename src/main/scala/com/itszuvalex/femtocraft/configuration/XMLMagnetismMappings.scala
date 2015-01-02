package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util

import com.itszuvalex.femtocraft.configuration.XMLSerializable._
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._


/**
 * Created by Chris on 12/5/2014.
 */
class XMLMagnetismMappings(file: File) extends XMLLoaderWriter[(ItemStack, Int)](file) {
  def loadCustomRecipes(): util.Collection[(ItemStack, Int)] = {
    val recipes = xml \ XMLSerializable.magnetismMappingTag
    recipes.view.map(_.getMagnetismMapping).asJavaCollection
  }

  def seedInitialRecipes(recipes: util.Collection[(ItemStack, Int)]) = {
    recipes.view.foreach(recipe => xml = xml.copy(child = xml.child ++ recipe.toNode))
    save()
  }
}
