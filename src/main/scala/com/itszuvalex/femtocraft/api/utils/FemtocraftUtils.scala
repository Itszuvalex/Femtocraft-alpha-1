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
package com.itszuvalex.femtocraft.api.utils

import java.util
import java.util.{Random, UUID}

import com.itszuvalex.femtocraft.api.implicits.IDImplicits._
import com.itszuvalex.femtocraft.api.{EnumTechLevel, FemtocraftAPI}
import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.util.{ChatComponentText, EnumChatFormatting}
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary

import scala.collection.JavaConversions._

object FemtocraftUtils {
  /**
   *
   * @param username
   * @return True if any of the assistants of the given player are seen as online.
   */
  def isPlayerAssistantsOnline(username: String) = FemtocraftAPI
                                                   .getAssistantManager
                                                   .getPlayerAssistants(username)
                                                   .exists(isPlayerOnline)

  /**
   *
   * @param username
   * @return True if MinecraftServer sees the player as online.
   */
  def isPlayerOnline(username: String) = MinecraftServer.getServer.getAllUsernames.contains(username)

  /**
   *
   * @param username
   * @return The player entity of the player with username.
   */
  def getLocalPlayer(username: String): EntityPlayer = Minecraft.getMinecraft.theWorld.getPlayerEntityByName(username)

//  def getServerPlayer(username: String): EntityPlayerMP = MinecraftServer.getServer.getConfigurationManager.func_152612_a(username)

  def getServerPlayer(uuid: UUID): EntityPlayerMP = MinecraftServer.getServer.getConfigurationManager.playerEntityList.collectFirst { case player: EntityPlayerMP if player.getUniqueID.equals(uuid) => player}.orNull

  /**
   *
   * This DOES modify slots when attempting to place, regardless of true false.  Thus, passing a copy of the inventory is recommended when testing
   *
   * @param item Item used for matching/stacksize.  Does not modify item
   * @param slots Array of slots to attempt to place item.
   * @param restrictions Array of slot indexs to skip when placing.
   * @return True if slots contains room for item.
   */
  def placeItem(item: ItemStack, slots: Array[ItemStack], restrictions: Array[Int]): Boolean = {
    if (item == null) {
      return true
    }
    var amount = item.stackSize
    if (restrictions != null) {
      util.Arrays.sort(restrictions)
    }
    for (i <- 0 until slots.length) {
      if (restrictions == null || !(util.Arrays.binarySearch(restrictions, i) >= 0)) {
        if (slots(i) != null && FemtocraftUtils.compareItem(slots(i), item) == 0) {
          val slot = slots(i)
          val room = slot.getMaxStackSize - slot.stackSize
          if (room < amount) {
            slot.stackSize += room
            amount -= room
          } else {
            slot.stackSize += amount
            return true
          }
        }
      }
    }
    for (i <- 0 until slots.length) {
      if (restrictions == null || !(util.Arrays.binarySearch(restrictions, i) >= 0)) {
        if (slots(i) == null) {
          slots(i) = item.copy
          slots(i).stackSize = amount
          return true
        }
      }
    }
    false
  }

  /**
   * Drops the item in the world.
   *
   * @param item
   * @param world
   * @param x
   * @param y
   * @param z
   * @param rand
   */
  def dropItem(item: ItemStack, world: World, x: Int, y: Int, z: Int, rand: Random): Unit = {
    if (item == null) return

    val f = rand.nextFloat * 0.8F + 0.1F
    val f1 = rand.nextFloat * 0.8F + 0.1F
    val f2 = rand.nextFloat * 0.8F + 0.1F
    while (item.stackSize > 0) {
      var k1 = rand.nextInt(21) + 10
      if (k1 > item.stackSize) {
        k1 = item.stackSize
      }
      item.stackSize -= k1
      val entityitem = new EntityItem(world,
                                      (x.toFloat + f).toDouble,
                                      (y.toFloat + f1).toDouble,
                                      (z.toFloat + f2).toDouble,
                                      new ItemStack(item.getItem, k1, item.getItemDamage))
      if (item.hasTagCompound) {
        entityitem.getEntityItem.setTagCompound(item.getTagCompound.copy.asInstanceOf[NBTTagCompound])
      }
      val f3 = 0.05F
      entityitem.motionX = (rand.nextGaussian.toFloat * f3).toDouble
      entityitem.motionY = (rand.nextGaussian.toFloat * f3 + 0.2F).toDouble
      entityitem.motionZ = (rand.nextGaussian.toFloat * f3).toDouble
      world.spawnEntityInWorld(entityitem)
    }
  }

  /**
   *
   * This DOES modify slots when attempting to place, regardless of output.  Thus, it is recommended to pass a copy when testing.
   *
   * @param item Item used to attempt to place.  This is NOT modified.
   * @param slots
   * @param restrictions
   * @return
   */
  def removeItem(item: ItemStack, slots: Array[ItemStack], restrictions: Array[Int]): Boolean = {
    if (item == null) {
      return true
    }
    var amountLeftToRemove: Int = item.stackSize
    if (amountLeftToRemove <= 0) return true
    if (restrictions != null) {
      util.Arrays.sort(restrictions)
    }
    for (i <- 0 until slots.length) {
      if (restrictions == null || !(util.Arrays.binarySearch(restrictions, i) >= 0)) {
        if (slots(i) != null && FemtocraftUtils.compareItem(slots(i), item) == 0) {
          val slot = slots(i)
          val amount = slot.stackSize
          if (amount <= amountLeftToRemove) {
            slots(i) = null
            amountLeftToRemove -= amount
            if (amountLeftToRemove <= 0) {
              return true
            }
          } else {
            slot.stackSize -= amountLeftToRemove
            return true
          }
        }
      }
    }
    false
  }

  def compareItem(cur: ItemStack, in: ItemStack): Int = {
    if (cur == null && in != null) {
      return -1
    }
    if (cur != null && in == null) {
      return 1
    }
    if (cur == null && in == null) {
      return 0
    }
    if (cur.getItem.itemID < in.getItem.itemID) {
      return -1
    }
    if (cur.getItem.itemID > in.getItem.itemID) {
      return 1
    }
    val damage = cur.getItemDamage
    val indamage = in.getItemDamage
    if (damage == OreDictionary.WILDCARD_VALUE) return 0
    if (indamage == OreDictionary.WILDCARD_VALUE) return 0
    if (damage < indamage) {
      return -1
    }
    if (damage > indamage) {
      return 1
    }
    0
  }

  /**
   *
   * @param input
   * @return input with first letter capitalized.
   */
  def capitalize(input: String) = input.substring(0, 1).toUpperCase + input.substring(1)

  /**
   *
   * @return integer representation of blue, used for Micro-tier things.
   */
  def blueColor = colorFromARGB(255, 0, 0, 255)

  /**
   *
   * @param a [0, 255] alpha value, bit masked to fit
   * @param r [0, 255] alpha value, bit masked to fit
   * @param g [0, 255] alpha value, bit masked to fit
   * @param b [0, 255] alpha value, bit masked to fit
   * @return Helper function for generationg integer representations of argb colors.
   */
  def colorFromARGB(a: Int, r: Int, g: Int, b: Int) = {
    var r1 = 0
    r1 += (a & 255) << 24
    r1 += (r & 255) << 16
    r1 += (g & 255) << 8
    r1 += b & 255
    r1
  }

  def ARGBFromColor(color: Int): (Int, Int, Int, Int) =
    (
      ((color & (255 << 24)) >> 24) & 255,
      (color & (255 << 16)) >> 16,
      (color & (255 << 8)) >> 8,
      color & 255
      )

  /**
   *
   * @return integer representation of green, used for Nano-tier things.
   */
  def greenColor = colorFromARGB(255, 0, 255, 0)

  /**
   *
   * @return integer representation of orange, used for Femto-tier things.
   */
  def orangeColor = colorFromARGB(255, 255, 140, 0)

  /**
   *
   * @param name
   * @return Prepends Micro techlevel chat formatting Enum, and appends Reset formatting enum.
   */
  def blueify(name: String) = EnumTechLevel.MICRO.getTooltipEnum + name + EnumChatFormatting.RESET

  /**
   *
   * @param name
   * @return Prepends Nano techlevel chat formatting Enum, and appends Reset formatting enum.
   */
  def greenify(name: String) = EnumTechLevel.NANO.getTooltipEnum + name + EnumChatFormatting.RESET

  /**
   *
   * @param name
   * @return Prepends Femto techlevel chat formatting Enum, and appends Reset formatting enum.
   */
  def orangeify(name: String) = EnumTechLevel.FEMTO.getTooltipEnum + name + EnumChatFormatting.RESET

  /**
   *
   * @param i
   * @return Formats i to a string, with ,'s inserted.
   */
  def formatIntegerToString(i: Int) = formatIntegerString(String.valueOf(i))

  /**
   *
   * @param number
   * @return Formats string representation if integer with ,'s.
   */
  def formatIntegerString(number: String) = {
    val builder: StringBuilder = new StringBuilder(number)
    val length: Int = number.length
    for (i <- 0 until length) {
      if (i != 0 && i % 3 == 0) {
        builder.insert(length - i, ',')
      }
    }
    builder.toString()
  }

  def sendMessageToPlayer(username: String, message: String): Boolean = sendMessageToPlayer(username, message, "")

  def sendMessageToPlayer(username: String, message: String,
                          formatting: String): Boolean = sendMessageToPlayer(MinecraftServer
                                                                             .getServer
                                                                             .getConfigurationManager
                                                                             .func_152612_a(username),
                                                                             message,
                                                                             formatting)

  def sendMessageToPlayer(assistant: EntityPlayer, message: String): Boolean = sendMessageToPlayer(assistant,
                                                                                                   message,
                                                                                                   "")

  /**
   *
   * Sends chat message "(GOLD)[Femtocraft](RESET): (formatting)(message)(RESET)"
   * to player.
   *
   * @param player player to send message to
   * @param message Message to send to player
   * @param formatting Any formatting you wish to apply to the message as a whole.
   * @return True if player exists and message sent.
   */
  def sendMessageToPlayer(player: EntityPlayer, message: String, formatting: String): Boolean = {
    if (player != null) {
      player
      .addChatMessage(new ChatComponentText(new StringBuilder()
                                            .append(EnumChatFormatting.GOLD)
                                            .append(FemtocraftAPI.FemtocraftID)
                                            .append(EnumChatFormatting.RESET)
                                            .append(": ")
                                            .append(formatting)
                                            .append(message)
                                            .append(EnumChatFormatting.RESET)
                                            .toString()))
      return true
    }
    false
  }
}

