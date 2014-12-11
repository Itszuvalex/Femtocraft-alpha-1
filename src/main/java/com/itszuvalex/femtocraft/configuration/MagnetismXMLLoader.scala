package com.itszuvalex.femtocraft.configuration

import java.io.File

import com.itszuvalex.femtocraft.configuration.XMLSerializable._
import net.minecraft.item.ItemStack

import scala.collection.mutable.ArrayBuffer
import scala.xml.Elem


/**
 * Created by Chris on 12/8/2014.
 */
class MagnetismXMLLoader(file: File) extends CustomXMLLoader[(ItemStack, Int)](file) {
  override def loadItemsFromXML(xml: Elem): ArrayBuffer[(ItemStack, Int)] = {
    val ret = new ArrayBuffer[(ItemStack, Int)]
    val recipes = xml \ XMLSerializable.magnetismMappingTag
    ret ++= recipes.map(_.getMagnetismMapping)
  }
}
