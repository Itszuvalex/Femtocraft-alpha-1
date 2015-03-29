package com.itszuvalex.femtocraft.managers

import java.io.File

import com.itszuvalex.femtocraft.configuration.XMLLoaderWriter

import scala.collection.mutable

/**
 * Created by Christopher Harris (Itszuvalex) on 3/29/15.
 */
object ManagerPlayerUUIDs {
  var xml: XMLLoaderWriter = null

  def setFile(file: File): Unit = {
    xml = new XMLLoaderWriter(file)
    load()
  }

  val UUIDToUsername = new mutable.HashMap[String, String]()

  def getUsername(string: String) = UUIDToUsername.get(string)

  def addMapping(uuid: String, username: String) = {
    if (UUIDToUsername.get(uuid).orNull != username) {
      UUIDToUsername(uuid) = username
      save()
    }
  }

  def save() = {
    xml.xml = <xml>
      {for (mapping <- UUIDToUsername) yield <Mapping uuid={mapping._1} username={mapping._2}/>}
    </xml>
    xml.save()
  }

  def load() = {
    UUIDToUsername.clear()
    xml.load()
    (xml.xml \ "Mapping").foreach(node => addMapping(node \@ "uuid", node \@ "username"))
  }

}
