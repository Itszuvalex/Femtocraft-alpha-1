/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.managers.research


import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.configuration.Configurable
import com.itszuvalex.femtocraft.implicits.ItemStackImplicits._
import com.itszuvalex.femtocraft.research.gui.GuiResearch
import com.itszuvalex.femtocraft.research.gui.technology.{GuiTechnology, GuiTechnologyDefault}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.item.ItemStack
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._

class Technology(n: String, shortDes: String, tech: EnumTechLevel, prereq: Array[String], display: ItemStack, key: Boolean, resMats: Array[ItemStack], discover: ItemStack, discoverDescription: String, researchDescription: String, discovered: Boolean, researched: Boolean) extends ITechnology {
  @Configurable(comment = "Name of technology.")
  protected var name                                 = n
  @Configurable(comment = "Displayed when moused over in Research Tree")
  protected var shortDescription                     = shortDes
  @Configurable(comment = "Tech level of Research.  Changes color of lines rendered to this tech in Research Tree")
  protected var level                                = tech
  @Configurable(comment = "Names of all prerequisite technologies.")
  protected var prerequisites        : Array[String] = if (prereq == null) new Array[String](0) else prereq
  @Configurable(comment = "What item stack is the icon for this technology.")
  protected var displayItem                          = display
  protected var xDisplay                             = 0
  protected var yDisplay                             = 0
  @Configurable(comment = "True if special background in Research Tree, false if normal")
  protected var keystone                             = key
  @Configurable(comment = "Null for free research, ItemStack[9] (can contain nulls) as required items to put into " + "research console.")
  protected var researchMaterials                    = if (resMats == null) new Array[ItemStack](0) else resMats
  @Configurable(comment = "ItemStack that replaces technology item when used.  This will only ever have a stack " + "size of 1.")
  protected var discoverItem                         = discover
  @Configurable(comment = "Description string displayed when Technology is clicked in the research tree.  This is displayed when " + "the Technology has been researched.  This is " + "parsed for recipes and automatically layed out across as many pages as needed.")
  protected var researchedDescription: String        = if (researchDescription == null) "" else researchDescription
  @Configurable(comment = "Description string displayed when Technology is clicked in the research tree.  This is displayed when " + "the Technology has been discovered but not researched.  This is " + "parsed for recipes and automatically layed out across as many pages as needed.")
  protected var discoveredDescription: String        = if (discoverDescription == null) "" else discoverDescription
  @Configurable(comment = "Set this to true to force this to be discovered off the bat.")
  protected var discoveredByDefault                  = discovered
  @Configurable(comment = "Set this to true to force this to be researched off the bat.")
  protected var researchedByDefault                  = researched


  def this(name: String, shortDescription: String, level: EnumTechLevel, prereq: Array[String], displayItem: ItemStack, isKeystone: Boolean, resMats: Array[ItemStack], discoverItem: ItemStack)
  = this(name, shortDescription, level, prereq, displayItem, isKeystone, resMats, discoverItem, "", "", false, false)

  def this(name: String, shortDescription: String, level: EnumTechLevel, prerequisites: Array[String], displayItem: ItemStack, isKeystone: Boolean, researchMaterials: Array[ItemStack])
  = this(name, shortDescription, level, prerequisites, displayItem, isKeystone, researchMaterials, null)


  override def getName = name

  def setName(n: String) = name = n

  override def getShortDescription = shortDescription

  def setShortDescription(s: String) = shortDescription = s

  override def getLevel = level

  def setLevel(s: EnumTechLevel) = level = s

  override def getPrerequisites: java.util.Collection[String] = prerequisites.toSeq

  def setPrerequisites(s: Array[String]) = prerequisites = s

  override def getDisplayItem = displayItem

  def setDisplayItem(s: ItemStack) = displayItem = s

  def isKeystone = keystone

  def setIsKeystone(s: Boolean) = keystone = s

  def getResearchMaterials = researchMaterials.deepCopy

  def setResearchMaterials(s: Array[ItemStack]) = researchMaterials = s

  override def getDiscoverItem = discoverItem

  def setDiscoverItem(s: ItemStack) = discoverItem = s

  override def getResearchedDescription = researchedDescription

  def setResearchedDescription(s: String) = researchedDescription = s

  override def getDiscoveredDescription = discoveredDescription

  def setDiscoveredDescription(s: String) = discoveredDescription = s

  override def isDiscoveredByDefault = discoveredByDefault

  def setIsDiscoveredByDefault(s: Boolean) = discoveredByDefault = s

  override def isResearchedByDefault = researchedByDefault

  def setIsResearchedByDefault(s: Boolean) = researchedByDefault = s

  def addPrerequisite(prereq: ITechnology): Boolean = addPrerequisite(prereq.getName)

  def addPrerequisite(prereq: String): Boolean = {
    prerequisites = util.Arrays.copyOf(prerequisites, prerequisites.length + 1)
    prerequisites(prerequisites.length - 1) = prereq
    true
  }

  def removePrerequisite(prereq: ITechnology): Boolean = this.removePrerequisite(prereq.getName)

  def removePrerequisite(name: String): Boolean = {
    if (!hasPrerequisite(name)) return false
    val prereqs = prerequisites.toSeq
    val ret = prereqs.remove(name)
    if (ret) {
      prerequisites = prereqs.toArray
//      prerequisites = prereqs.toArray(new Array[String](prereqs.size))
    }
    ret
  }

  def hasPrerequisite(prereq: Technology): Boolean = hasPrerequisite(prereq.name)

  def hasPrerequisite(prereq: String): Boolean = util.Arrays.asList(prerequisites).contains(prereq)

  @SideOnly(value = Side.CLIENT)
  def getGui(research: GuiResearch, status: ResearchStatus): GuiTechnology = {
    try {
      return getGuiClass.getConstructor(classOf[GuiResearch], classOf[ResearchStatus]).newInstance(research, status)
    }
    catch {
      case e: NoSuchMethodException =>
        Femtocraft.log(Level.ERROR, "Technologies must return a GuiTechnology class that supports the constructor" + "(GuiResearch, ResearchTechnologyStatus)")
        e.printStackTrace()
      case e: Exception             => e.printStackTrace()
    }
    null
  }

  @SideOnly(value = Side.CLIENT)
  def getGuiClass: Class[_ <: GuiTechnology] = if ((researchedDescription == null || researchedDescription.isEmpty) && (discoveredDescription == null || discoveredDescription.isEmpty)) classOf[GuiTechnologyDefault] else classOf[GuiTechnology]
}
