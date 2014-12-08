package com.itszuvalex.femtocraft.configuration

import java.io.File

import com.itszuvalex.femtocraft.Femtocraft
import org.apache.logging.log4j.Level

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, XML}


/**
 * Created by Chris on 12/8/2014.
 */
abstract class CustomXMLLoader[recipe](val file: File) {

  def loadRecipesFromXML(xml: Elem): ArrayBuffer[recipe]

  def loadRecipes(): ArrayBuffer[recipe] = {
    val ret = loadRecipes(file)
    Femtocraft.log(Level.INFO, "Loaded " + ret.size + " recipes from " + file.getPath)
    ret
  }

  private def loadRecipes(f: File): ArrayBuffer[recipe] = {
    Femtocraft.log(Level.INFO, "Loading " + f.getPath + "...")
    var ret = new ArrayBuffer[recipe]()
    f match {
      case dir if dir.isDirectory =>
        dir.listFiles().foreach(ret ++= loadRecipes(_))
      case fi if fi.isFile        =>
        ret ++= loadRecipesFromXML(XML.loadFile(fi))
      case _                      =>
    }
    ret
  }
}
