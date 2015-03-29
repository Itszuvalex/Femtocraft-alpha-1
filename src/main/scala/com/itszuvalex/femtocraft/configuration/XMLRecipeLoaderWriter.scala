package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util

/**
 * Created by Chris on 12/5/2014.
 */
abstract class XMLRecipeLoaderWriter[Type](file: File) extends XMLLoaderWriter(file) {
  def loadCustomRecipes(): util.Collection[Type]

  def seedInitialRecipes(recipes: util.Collection[Type])
}
