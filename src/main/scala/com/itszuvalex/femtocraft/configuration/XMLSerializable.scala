package com.itszuvalex.femtocraft.configuration

import com.itszuvalex.femtocraft.api._
import com.itszuvalex.femtocraft.managers.research.Technology
import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils
import net.minecraft.item.ItemStack

import scala.xml.Utility.escape
import scala.xml.{Elem, Node}

/**
 * Created by Chris on 12/5/2014.
 */
object XMLSerializable {
  val assemblerRecipeTag   = "AssemblerRecipe"
  val temporalRecipeTag    = "TemporalRecipe"
  val dimensionalRecipeTag = "DimensionalRecipe"
  val magnetismMappingTag  = "MagnetismMapping"
  val technologyTag        = "Technology"

  trait XMLSerial[Type] {
    def toNode(tag: String): Elem
  }

  implicit class XMLSerializeString(value: String) extends XMLSerial[String] {
    override def toNode(tag: String) = <xml>
                                         {value}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeInt(value: Int) extends XMLSerial[Int] {
    override def toNode(tag: String) = value.toString.toNode(tag)
  }

  implicit class XMLSerializeFloat(value: Float) extends XMLSerial[Float] {
    override def toNode(tag: String) = value.toString.toNode(tag)
  }

  implicit class XMLSerializeDouble(value: Double) extends XMLSerial[Double] {
    override def toNode(tag: String) = value.toString.toNode(tag)
  }

  implicit class XMLSerializeBool(value: Boolean) extends XMLSerial[Boolean] {
    override def toNode(tag: String) = value.toString.toNode(tag)
  }

  implicit class XMLSerializeItemStack(value: ItemStack) extends XMLSerial[ItemStack] {
    override def toNode(tag: String) = FemtocraftStringUtils.itemStackToString(value).toNode(tag)
  }

  implicit class XMLSerializeItemStackArray(value: Array[ItemStack]) extends XMLSerial[Array[ItemStack]] {
    override def toNode(tag: String) = <xml length={value.length.toString}>
                                         {(value zip (0 until value.length)).map { pair => pair._1.toNode(tag + pair._2.toString)}}
                                       </xml>.copy(label = tag, minimizeEmpty = true)
  }

  implicit class XMLSerializeStringArray(value: Array[String]) extends XMLSerial[Array[String]] {
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


  implicit class XMLSerializeTechnology(value: Technology) {
    def toNode = {
      val prereqs = new Array[String](value.getPrerequisites.size)
      value.getPrerequisites.toArray(prereqs)
      val ret = <xml>
                  {value.getName.toNode("name")}{value.getShortDescription.toNode("shortDescription")}{value.getLevel.key.toNode("techLevel")}{prereqs.toNode("prerequisites")}{value.getDisplayItem.toNode("displayItem")}{value.isKeystone.toNode("keystone")}{value.getResearchMaterials.toNode("researchMaterials")}{value.getDiscoverItem.toNode("discoverItem")}{value.getResearchedDescription.toNode("researchedDescription")}{value.getDiscoveredDescription.toNode("discoveredDescription")}{value.isDiscoveredByDefault.toNode("discoveredByDefault")}{value.isResearchedByDefault.toNode("researchedByDefault")}
                </xml>.copy(label = technologyTag)

      ret
    }
  }

  implicit class XMLDeserialize(node: Node) {

    def getTechnology: Technology = {
      val name = node.getString("name")
      val shortDescription = node.getString("shortDescription")
      val level = EnumTechLevel.getTech(node.getString("techLevel"))
      val prerequisites = node.getStringArray("prerequisites")
      val displayItem = node.getItemStack("displayItem")
      val keystone = node.getBool("keystone")
      val researchMaterials = node.getItemStackArray("researchMaterials")
      val discoverItem = node.getItemStack("discoverItem")
      val researchedDescription = node.getString("researchedDescription")
      val discoveredDescription = node.getString("discoveredDescription")
      val discoveredByDefault = node.getBool("discoveredByDefault")
      val researchedByDefault = node.getBool("researchedByDefault")
      new Technology(name, shortDescription, level, prerequisites, displayItem, keystone, researchMaterials, discoverItem, discoveredDescription, researchedDescription, discoveredByDefault, researchedByDefault)
    }

    def getBool(tag: String): Boolean = getString(tag).toBoolean

    def getStringArray(tag: String): Array[String] = {
      val arrayNode = (node \ tag).head
      val size = (arrayNode \@ "length").toInt
      val array = new Array[String](size)
      (0 until size).foreach { i => array(i) = arrayNode.getString(tag + i.toString)}
      array
    }

    def getFloat(tag: String): Float = getString(tag).toFloat

    def getDouble(tag: String): Double = getString(tag).toDouble

    def getAssemblerRecipe: AssemblerRecipe = {
      val recipe = new AssemblerRecipe
      recipe.input = node.getItemStackArray("input")
      recipe.mass = node.getInt("mass")
      recipe.output = node.getItemStack("output")
      recipe.enumTechLevel = EnumTechLevel.getTech(node.getString("techLevel"))
      recipe.tech = node.getString("technology")
      recipe.`type` = AssemblerRecipeType.valueOf(node.getString("type"))
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

    def getItemStackArray(tag: String): Array[ItemStack] = {
      val arrayNode = (node \ tag).head
      val size = (arrayNode \@ "length").toInt
      val array = new Array[ItemStack](size)
      (0 until size).foreach { i => array(i) = arrayNode.getItemStack(tag + i.toString)}
      array
    }

    def getMagnetismMapping: (ItemStack, Int) = {
      (node.getItemStack("item"), node.getInt("strength"))
    }

    def getItemStack(tag: String): ItemStack = {
      FemtocraftStringUtils.itemStackFromString(getString(tag))
    }

    def getInt(tag: String): Int = getString(tag).toInt

    def getString(tag: String): String = (node \ tag).text.trim

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
