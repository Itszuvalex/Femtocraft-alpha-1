package com.itszuvalex.femtocraft.configuration

import com.itszuvalex.femtocraft.api.{AssemblerRecipe, DimensionalRecipe, EnumTechLevel, TemporalRecipe}
import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils
import net.minecraft.item.ItemStack

import scala.xml.{Elem, Node}

/**
 * Created by Chris on 12/5/2014.
 */
object XMLSerializable {
  val assemblerRecipeTag   = "AssemblerRecipe"
  val temporalRecipeTag    = "TemporalRecipe"
  val dimensionalRecipeTag = "DimensionalRecipe"
  val magnetismMappingTag  = "MagnetismMapping"

  trait XMLSerial[Type] {
    def toNode(tag: String): Elem
  }

  implicit class XMLSerializeString(value: String) extends XMLSerial[String] {
    override def toNode(tag: String) = <xml>
                                         {value}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeInt(value: Int) extends XMLSerial[Int] {
    override def toNode(tag: String) = <xml>
                                         {value}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeFloat(value: Float) extends XMLSerial[Float] {
    override def toNode(tag: String) = <xml>
                                         {value}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeDouble(value: Double) extends XMLSerial[Double] {
    override def toNode(tag: String) = <xml>
                                         {value}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeBool(value: Boolean) extends XMLSerial[Boolean] {
    override def toNode(tag: String) = <xml>
                                         {value}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeItemStack(value: ItemStack) extends XMLSerial[ItemStack] {
    override def toNode(tag: String) = <xml>
                                         {FemtocraftStringUtils.itemStackToString(value)}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeItemStackArray(value: Array[ItemStack]) extends XMLSerial[Array[ItemStack]] {
    override def toNode(tag: String) = <xml length={value.length.toString}>
                                         {(value zip (0 until value.length)).map { pair => pair._1.toNode(tag + pair._2.toString)}}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeAssemblerRecipe(value: AssemblerRecipe) {
    def toNode = <xml>
                   {value.input.toNode("input")}{value.mass.toNode("mass")}{value.output.toNode("output")}{value.enumTechLevel.key.toNode("techLevel")}{value.tech.toNode("technology")}{value.`type`.getValue.toNode("type")}
                 </xml>.copy(label = assemblerRecipeTag)
  }

  implicit class XMLSerializeTemporalRecipe(value: TemporalRecipe) {
    def toNode = <xml>
                   {value.configurators.toNode("configurators")}{value.input.toNode("input")}{value.output.toNode("output")}{value.techLevel.key.toNode("techLevel")}{value.tech.toNode("tech")}{value.ticks.toNode("ticks")}
                 </xml>.copy(label = temporalRecipeTag)
  }

  implicit class XMLSerializeDimensionalRecipe(value: DimensionalRecipe) {
    def toNode = <xml>
                   {value.configurators.toNode("configurators")}{value.input.toNode("input")}{value.output.toNode("output")}{value.techLevel.key.toNode("techLevel")}{value.tech.toNode("tech")}{value.ticks.toNode("ticks")}
                 </xml>.copy(label = dimensionalRecipeTag)
  }

  implicit class XMLSerializeMagnetismMapping(value: (ItemStack, Int)) {
    def toNode = <xml>
                   {value._1.toNode("item")}{value._2.toNode("strength")}
                 </xml>.copy(label = magnetismMappingTag)
  }

  implicit class XMLDeserialize(node: Node) {

    def getFloat(tag: String): Float = (node \ tag).text.trim.toFloat

    def getDouble(tag: String): Double = (node \ tag).text.trim.toDouble

    def getBool(tag: String): Boolean = (node \ tag).text.trim.toBoolean

    def getAssemblerRecipe: AssemblerRecipe = {
      val recipe = new AssemblerRecipe
      recipe.input = node.getItemStackArray("input")
      recipe.mass = node.getInt("mass")
      recipe.output = node.getItemStack("output")
      recipe.enumTechLevel = EnumTechLevel.getTech(node.getString("techLevel"))
      recipe.tech = node.getString("technology")
      recipe.`type` = AssemblerRecipe.RecipeType.valueOf(node.getString("type"))
      recipe
    }

    def getTemporalRecipe: TemporalRecipe = {
      val configurators = node.getItemStackArray("configurators")
      val input = node.getItemStack("input")
      val output = node.getItemStack("output")
      val techLevel = EnumTechLevel.getTech(node.getString("techLevel"))
      val tech = node.getString("tech")
      val ticks = node.getInt("ticks")
      new TemporalRecipe(input, configurators, output, ticks, techLevel, tech)
    }

    def getString(tag: String): String = (node \ tag).text.trim

    def getInt(tag: String): Int = (node \ tag).text.trim.toInt

    def getItemStackArray(tag: String): Array[ItemStack] = {
      val arrayNode = (node \ tag).head
      val size = (arrayNode \@ "length").toInt
      val array = new Array[ItemStack](size)
      (0 until size).foreach { i => array(i) = arrayNode.getItemStack(tag + i.toString)}
      array
    }

    def getItemStack(tag: String): ItemStack = {
      val nodes = node \ tag
      if (nodes == null) return null
      FemtocraftStringUtils.itemStackFromString(nodes.text.trim)
    }

    def getMagnetismMapping: (ItemStack, Int) = {
      (node.getItemStack("item"), node.getInt("strength"))
    }

    def getDimensionalRecipe: DimensionalRecipe = {
      val configurators = node.getItemStackArray("configurators")
      val input = node.getItemStack("input")
      val output = node.getItemStack("output")
      val techLevel = EnumTechLevel.getTech(node.getString("techLevel"))
      val tech = node.getString("tech")
      val ticks = node.getInt("ticks")
      new DimensionalRecipe(input, configurators, output, ticks, techLevel, tech)
    }
  }

}
