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
  
  def loadItemsFromXML(xml: Elem): ArrayBuffer[recipe]
  
  def loadItems(): ArrayBuffer[recipe] = {
    val ret = loadItems(file)
    Femtocraft.log(Level.INFO, "Loaded " + ret.size + " items from " + file.getPath + ".")
    ret
  }
  
  private def loadItems(f: File): ArrayBuffer[recipe] = {
    Femtocraft.log(Level.INFO, "Loading " + f.getPath + "...")
    var ret = new ArrayBuffer[recipe]()
    f match {
      case dir if dir.isDirectory => dir.listFiles().foreach(ret ++= loadItems(_))
      case fi if fi.isFile        =>
        try ret ++= loadItemsFromXML(XML.loadFile(fi))
        catch {
          case e: Exception =>
            Femtocraft.log(Level.ERROR, "An error occured while loading " + fi.getPath + ".")
            e.printStackTrace()
        }
      case _                      =>
    }
    ret
  }
}
