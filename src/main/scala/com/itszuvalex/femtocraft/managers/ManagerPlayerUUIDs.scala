package com.itszuvalex.femtocraft.managers

import java.io.File
import java.util.UUID

import com.itszuvalex.femtocraft.configuration.XMLLoaderWriter

import scala.collection.mutable

/**
 * Created by Christopher Harris (Itszuvalex) on 3/29/15.
 */
object ManagerPlayerUUIDs {
  private var xml: XMLLoaderWriter = null

  def setFile(file: File): Unit = {
    xml = new XMLLoaderWriter(file)
    load()
  }

  val UUIDToUsername = new mutable.HashMap[UUID, String]()
  val UsernameToUUID = new mutable.HashMap[String, UUID]()


  def getUsername(uuid: UUID) = UUIDToUsername.getOrElse(uuid, "")

  def getUUID(string: String) = UsernameToUUID.getOrElse(string, null)

  def addMapping(uuid: UUID, username: String) = {
    if (UUIDToUsername.get(uuid).orNull != username) {
      UUIDToUsername(uuid) = username
      UsernameToUUID(username) = uuid
      save()
    }
  }

  def save() = {
    xml.xml = <xml>
      {for (mapping <- UUIDToUsername) yield <Mapping uuid={mapping._1.toString} username={mapping._2}/>}
    </xml>
    xml.save()
  }

  def load() = {
    UUIDToUsername.clear()
    xml.load()
    (xml.xml \ "Mapping").foreach(node => try addMapping(UUID.fromString(node \@ "uuid"), node \@ "username") catch {case _: Throwable =>})
  }

}
