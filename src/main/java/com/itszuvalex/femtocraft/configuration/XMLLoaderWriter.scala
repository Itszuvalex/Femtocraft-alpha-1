package com.itszuvalex.femtocraft.configuration

import java.io.File
import java.util

import scala.xml.{PrettyPrinter, XML}

/**
 * Created by Chris on 12/5/2014.
 */
abstract class XMLLoaderWriter[Type](file: File) {
  val initialized: Boolean = file.exists
  if (!initialized) {
    XML.save(file.getPath, <xml></xml>)
  }
  protected var xml = XML.loadFile(file)

  def loadCustomRecipes(): util.Collection[Type]

  def seedInitialRecipes(recipes: util.Collection[Type])

  protected def save(): Unit = {
    val pp = new PrettyPrinter(80, 2)
    XML.save(file.getPath, XML.loadString(pp.format(xml)), "UTF-8", true, null)
  }
}
