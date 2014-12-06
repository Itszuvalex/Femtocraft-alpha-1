package com.itszuvalex.femtocraft.configuration

import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils
import net.minecraft.item.ItemStack

import scala.xml.Node

/**
 * Created by Chris on 12/5/2014.
 */
object XMLSerializable {

  implicit class XMLSerializeString(value: String) {
    def toNode(tag: String) = <xml>
                                {value}
                              </xml>.copy(label = tag)
  }

  implicit class XMLSerializeInt(value: Int) {
    def toNode(tag: String) = <xml>
                                {value}
                              </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeFloat(value: Float) {
    def toNode(tag: String) = <xml>
                                {value}
                              </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeDouble(value: Double) {
    def toNode(tag: String) = <xml>
                                {value}
                              </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeBool(value: Boolean) {
    def toNode(tag: String) = <xml>
                                {value}
                              </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeItemStack(value: ItemStack) {
    def toNode(tag: String) = <xml>
                                {FemtocraftStringUtils.itemStackToString(value)}
                              </xml>.copy(label = tag)
  }

  implicit class XMLSerializeItemStackArray(value: Array[ItemStack]) {
    def toNode(tag: String) = <xml length={value.length.toString}>
                                {(value zip (0 until value.length)).map { pair => pair._1.toNode(tag + pair._2.toString)}}
                              </xml>.copy(label = tag)
  }

  implicit class XMLSerializeAssemblerRecipe(value: AssemblerRecipe) {
    def toNode = <AssemblerRecipe>
      {value.input.toNode("input")}{value.mass.toNode("mass")}{value.output.toNode("output")}{value.enumTechLevel.key.toNode("techLevel")}{value.tech.toNode("technology")}
    </AssemblerRecipe>
  }

  implicit class XMLDeserialize(node: Node) {
    def getString(tag: String): String = (node \ tag).text.trim

    def getInt(tag: String): Int = (node \ tag).text.trim.toInt

    def getFloat(tag: String): Float = (node \ tag).text.trim.toFloat

    def getDouble(tag: String): Double = (node \ tag).text.trim.toDouble

    def getBool(tag: String): Boolean = (node \ tag).text.trim.toBoolean

    def getItemStack(tag: String): ItemStack = {
      val nodes = node \ tag
      if (nodes == null) return null
      FemtocraftStringUtils.itemStackFromString(nodes.text.trim)
    }

    def getItemStackArray(tag: String): Array[ItemStack] = {
      val arrayNode = (node \ tag).head
      val size = (arrayNode \@ "length").toInt
      val array = new Array[ItemStack](size)
      (0 until size).foreach { i => array(i) = arrayNode.getItemStack(tag + i.toString)}
      array
    }

    def getAssemblerRecipe: AssemblerRecipe = {
      val recipe = new AssemblerRecipe
      recipe.input = node.getItemStackArray("input")
      recipe.mass = node.getInt("mass")
      recipe.output = node.getItemStack("output")
      recipe.enumTechLevel = EnumTechLevel.getTech(node.getString("techLevel"))
      recipe.tech = node.getString("technology")
      recipe
    }
  }

}
