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
package com.itszuvalex.femtocraft.managers.assembler

import java.io.IOException
import java.sql._
import java.util
import java.util.logging.Level

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.managers.research.{EnumTechLevel, ResearchTechnology}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompressedStreamTools

import scala.Array

/**
 * Created by Christopher Harris (Itszuvalex) on 6/28/14.
 */
object AssemblerRecipeDatabase {
  private val DB_ITEMS_DAMAGE        = "DAMAGE"
  private val DB_ITEMS_NBT           = "NBT"
  private val DB_ITEMS_STACKSIZE     = "STACKSIZE"
  private val DB_RECIPES_INPUT       = "INPUT"
  private val DB_RECIPES_INPUT_SIZE  = "INPUTSIZE"
  private val DB_RECIPES_MASS        = "MASS"
  private val DB_RECIPES_OUTPUT      = "OUTPUT"
  private val DB_RECIPES_OUTPUT_SIZE = "OUTPUTSIZE"
  private val DB_RECIPES_OUTPUT_NBT  = "OUTPUTNBT"
  private val DB_RECIPES_TECH_LEVEL  = "TECHLEVEL"
  private val DB_RECIPES_TECHNOLOGY  = "TECHNAME"
  private val DB_NULL_ITEM           = "null"
  private val DB_FILENAME            = "AssemblerRecipes.db"
  private val DB_TABLE_ITEMS         = "ITEMS"
  private val DB_TABLE_RECIPES       = "RECIPES"
  private val DB_ITEMS_ITEMID        = "ITEMID"
}

class AssemblerRecipeDatabase() {
  private var c: Connection = null
  var shouldRegister = true

  try {
    InitializeDatabase()
  }
  catch {
    case e: ClassNotFoundException =>
      e.printStackTrace()
      Femtocraft.log(Level.SEVERE, "SQLite dependency missing.")
      System.exit(1)
    case e: SQLException           =>
      e.printStackTrace()
      Femtocraft.log(Level.SEVERE, "Error opening connection")
  }

  @throws(classOf[ClassNotFoundException])
  @throws(classOf[SQLException])
  private def InitializeDatabase() {
    Class.forName("org.sqlite.JDBC")
    refreshConnection()
    shouldRegister = createRecipeTable
  }

  @throws(classOf[SQLException])
  private def refreshConnection() {
    if (c == null) {
      c = DriverManager.getConnection("jdbc:sqlite:" + "config/" + AssemblerRecipeDatabase.DB_FILENAME)
    }
  }

  private def createRecipeTable: Boolean = {
    var s: Statement = null
    try {
      refreshConnection()
      s = c.createStatement
      val sql = "CREATE TABLE " + AssemblerRecipeDatabase.DB_TABLE_RECIPES + "(ID INTEGER PRIMARY KEY, " + AssemblerRecipeDatabase.DB_RECIPES_INPUT + " STRING NOT NULL," + AssemblerRecipeDatabase.DB_RECIPES_INPUT_SIZE + " STRING NOT NULL," + AssemblerRecipeDatabase.DB_RECIPES_MASS + " INT CHECK(" + AssemblerRecipeDatabase.DB_RECIPES_MASS + " >= " + "0) NOT NULL," + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT + " STRING NOT NULL," + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT_SIZE + " STRING NOT NULL," + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT_NBT + " BYTES," + AssemblerRecipeDatabase.DB_RECIPES_TECH_LEVEL + " STRING NOT NULL," + AssemblerRecipeDatabase.DB_RECIPES_TECHNOLOGY + " STRING," + "UNIQUE(" + AssemblerRecipeDatabase.DB_RECIPES_INPUT + ", " + AssemblerRecipeDatabase.DB_RECIPES_MASS + "," + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT + "), " + "FOREIGN KEY(" + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT + ") REFERENCES ITEMS(ID))"
      s.executeUpdate(sql)
      s.close()
      true
    }
    catch {
      case e: SQLException => false
    }
  }

  private def createItemTable: Boolean = {
    var s: Statement = null
    try {
      refreshConnection()
      s = c.createStatement
      val sql = "CREATE TABLE " + AssemblerRecipeDatabase.DB_TABLE_ITEMS + "(ID INTEGER PRIMARY KEY NOT NULL," + AssemblerRecipeDatabase.DB_ITEMS_ITEMID + " INT NOT NULL," + AssemblerRecipeDatabase.DB_ITEMS_DAMAGE + " INT NOT NULL," + AssemblerRecipeDatabase.DB_ITEMS_STACKSIZE + " INT CHECK(" + AssemblerRecipeDatabase.DB_ITEMS_STACKSIZE + ">0) NOT NULL," + AssemblerRecipeDatabase.DB_ITEMS_NBT + " BYTES," + "UNIQUE(" + AssemblerRecipeDatabase.DB_ITEMS_ITEMID + ", " + AssemblerRecipeDatabase.DB_ITEMS_DAMAGE + ", " + AssemblerRecipeDatabase.DB_ITEMS_STACKSIZE + "))"
      s.executeUpdate(sql)
      s.close()
      true
    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "SQLite item table creation" + " failed")
        false
    }
  }

  def getRecipe(inputs: Array[ItemStack]): AssemblerRecipe = {
    var ac: AssemblerRecipe = null
    try {
      refreshConnection()
      val ps = c.prepareStatement("SELECT * FROM " + AssemblerRecipeDatabase.DB_TABLE_RECIPES + " WHERE " + AssemblerRecipeDatabase.DB_RECIPES_INPUT + " = ? " + "LIMIT 1")
      ps.setString(1, formatItems(inputs))
      val rs = ps.executeQuery
      if (rs.next) {
        ac = getRecipe(rs)
      }
      rs.close()
      ps.close()
    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "SQLite select recipe from " + "inputs failed")
    }
    if (Femtocraft.assemblerConfigs.isEnabled(ac)) ac else null
  }

  private def formatItems(items: scala.Array[ItemStack]): String = {
    val sb = new StringBuilder

    for (i <- 0 until items.length) {
      if (items(i) == null) {
        sb.append(AssemblerRecipeDatabase.DB_NULL_ITEM)
      }
      else {
        sb.append(items(i).itemID)
        sb.append(":")
        sb.append(items(i).getItemDamage)
      }
      if (i < items.length - 1) {
        sb.append(",")
      }
    }
    sb.toString()
  }

  @throws(classOf[SQLException])
  private def getRecipe(rs: ResultSet): AssemblerRecipe = {
    val ac = new AssemblerRecipe
    ac.input = getItems(rs.getString(AssemblerRecipeDatabase.DB_RECIPES_INPUT), rs.getString(AssemblerRecipeDatabase.DB_RECIPES_INPUT_SIZE))
    ac.mass = rs.getInt(AssemblerRecipeDatabase.DB_RECIPES_MASS)
    ac.output = getItem(rs.getString(AssemblerRecipeDatabase.DB_RECIPES_OUTPUT), rs.getString(AssemblerRecipeDatabase.DB_RECIPES_OUTPUT_SIZE), rs.getBytes(AssemblerRecipeDatabase.DB_RECIPES_OUTPUT_NBT))
    ac.enumTechLevel = EnumTechLevel.getTech(rs.getString(AssemblerRecipeDatabase.DB_RECIPES_TECH_LEVEL))
    ac.tech = rs.getString(AssemblerRecipeDatabase.DB_RECIPES_TECHNOLOGY)
    if (Femtocraft.assemblerConfigs.isEnabled(ac)) ac else null
  }

  private def getItems(items: String, stackSizes: String): Array[ItemStack] = (getItems(items), stackSizes.split(",")).zipped.map((i: ItemStack, s: String) => {if (i != null) {i.stackSize = s.toInt}; i})

  private def getItem(item: String, stackSize: String, nbt: Array[Byte]): ItemStack = {
    val id_damage = item.split(":")
    val result = new ItemStack(id_damage(0).toInt, stackSize.toInt, id_damage(1).toInt)
    if (nbt != null) {
      try {
        result.setTagCompound(CompressedStreamTools.decompress(nbt))
      }
      catch {
        case e: IOException =>
          e.printStackTrace()
      }
    }
    result
  }

  private def getItems(items: String): Array[ItemStack] =
    items.split(",").map(s => {
      if (s.matches(AssemblerRecipeDatabase.DB_NULL_ITEM)) null
      else {
        val id_damage: Array[String] = s.split(":")
        new ItemStack(id_damage(0).toInt, 1, id_damage(1).toInt)
      }
    })


  def getRecipe(output: ItemStack): AssemblerRecipe = {
    var ac: AssemblerRecipe = null
    try {
      refreshConnection()
      val ps = c.prepareStatement("SELECT * FROM " + AssemblerRecipeDatabase.DB_TABLE_RECIPES + " WHERE " + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT + " = ? " + "LIMIT 1")
      ps.setString(1, formatItem(output))
      val rs = ps.executeQuery
      if (rs.next) {
        ac = getRecipe(rs)
      }
      rs.close()
      ps.close()
    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "SQLite select recipe from " + "inputs failed")
    }
    if (Femtocraft.assemblerConfigs.isEnabled(ac)) ac else null
  }

  private def formatItem(item: ItemStack) = if (item == null) null else item.itemID + ":" + item.getItemDamage


  def insertRecipe(recipe: AssemblerRecipe): Boolean = {
    try {
      refreshConnection()
      val ps = c.prepareStatement("INSERT OR IGNORE INTO " + AssemblerRecipeDatabase.DB_TABLE_RECIPES + "(ID, " + AssemblerRecipeDatabase.DB_RECIPES_INPUT + ", " + AssemblerRecipeDatabase.DB_RECIPES_INPUT_SIZE + ", " + AssemblerRecipeDatabase.DB_RECIPES_MASS + "," + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT + ", " + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT_SIZE + ", " + AssemblerRecipeDatabase.DB_RECIPES_OUTPUT_NBT + ", " + AssemblerRecipeDatabase.DB_RECIPES_TECH_LEVEL + ", " + AssemblerRecipeDatabase.DB_RECIPES_TECHNOLOGY + ")" + "VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)")
      ps.setString(1, formatItems(recipe.input))
      ps.setString(2, formatItemSizes(recipe.input))
      ps.setInt(3, recipe.mass)
      ps.setString(4, formatItem(recipe.output))
      ps.setString(5, String.valueOf(recipe.output.stackSize))
      var nbt: Array[Byte] = null
      if (recipe.output.hasTagCompound) {
        try {
          nbt = CompressedStreamTools.compress(recipe.output.getTagCompound)
        }
        catch {
          case e: IOException => e.printStackTrace()
        }
      }
      ps.setBytes(6, nbt)
      ps.setString(7, recipe.enumTechLevel.key)
      ps.setString(8, recipe.tech)
      ps.executeUpdate
      ps.close()
      true
    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "SQLite insert recipe failed")
        false
    }
  }

  private def formatItemSizes(items: Array[ItemStack]) = {
    val sb = new StringBuilder
    for (i <- 0 until items.length) {
      if (items(i) == null) {
        sb.append(AssemblerRecipeDatabase.DB_NULL_ITEM)
      }
      else {
        sb.append(items(i).stackSize)
      }
      if (i < items.length - 1) {
        sb.append(",")
      }
    }
    sb.toString()
  }

  def getRecipesForLevel(level: EnumTechLevel): util.ArrayList[AssemblerRecipe] = {
    val arrayList = new util.ArrayList[AssemblerRecipe]
    try {
      refreshConnection()
      val ps = c.prepareStatement("SELECT * FROM " + AssemblerRecipeDatabase.DB_TABLE_RECIPES + " WHERE " + AssemblerRecipeDatabase.DB_RECIPES_TECH_LEVEL + " " + "=?", ResultSet.HOLD_CURSORS_OVER_COMMIT)
      ps.setString(1, level.key)
      val rs = ps.executeQuery
      while (rs.next) {
        val ac = getRecipe(rs)
        if (ac != null) {
          arrayList.add(ac)
        }
      }
      rs.close()
      ps.close()
    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "SQLite select recipe from " + "inputs failed")
    }
    arrayList
  }

  def getRecipesForTech(tech: ResearchTechnology): util.ArrayList[AssemblerRecipe] = getRecipesForTech(tech.name)

  def getRecipesForTech(techName: String): util.ArrayList[AssemblerRecipe] = {
    val arrayList = new util.ArrayList[AssemblerRecipe]
    try {
      refreshConnection()
      val ps = c.prepareStatement("SELECT * FROM " + AssemblerRecipeDatabase.DB_TABLE_RECIPES + " WHERE " + AssemblerRecipeDatabase.DB_RECIPES_TECHNOLOGY + " " + "= ?", ResultSet.HOLD_CURSORS_OVER_COMMIT)
      ps.setString(1, techName)
      val rs = ps.executeQuery
      while (rs.next) {
        val ac = getRecipe(rs)
        if (ac != null) {
          arrayList.add(ac)
        }
      }
      rs.close()
      ps.close()
    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "SQLite select recipe from " + "inputs failed")
    }
    arrayList
  }

  /**
   * DO NOT CALL THIS unless you have VERY good reason to.  This is a HUGE database and will take a long time to
   * load.
   *
   * @return
   */
  def getAllRecipes: util.ArrayList[AssemblerRecipe] = {
    val arrayList = new util.ArrayList[AssemblerRecipe]
    try {
      refreshConnection()
      val ps = c.prepareStatement("SELECT * FROM " + AssemblerRecipeDatabase.DB_TABLE_RECIPES, ResultSet.HOLD_CURSORS_OVER_COMMIT)
      val rs = ps.executeQuery
      while (rs.next) {
        val ac = getRecipe(rs)
        if (ac != null) {
          arrayList.add(ac)
        }
      }
      rs.close()
      ps.close()
    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "SQLite select recipe from " + "inputs failed")
    }
    arrayList
  }
}
