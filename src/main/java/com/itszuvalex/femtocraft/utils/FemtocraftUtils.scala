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
package com.itszuvalex.femtocraft.utils

import java.util
import java.util.Random

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.implicits.IDImplicits._
import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.util.{ChatComponentText, EnumChatFormatting}
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.oredict.OreDictionary

object FemtocraftUtils {
  def indexOfForgeDirection(dir: ForgeDirection) = dir.ordinal

  def isPlayerOnline(username: String) = MinecraftServer.getServer.getAllUsernames.contains(username)

  def getPlayer(username: String) = Minecraft.getMinecraft.theWorld.getPlayerEntityByName(username)


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
    if ((damage != OreDictionary.WILDCARD_VALUE) || (indamage != OreDictionary.WILDCARD_VALUE)) {
      if (damage < indamage) {
        return -1
      }
      if (damage > indamage) {
        return 1
      }
    }
    0
  }

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
        if (slots(i) != null && slots(i).isItemEqual(item)) {
          val slot = slots(i)
          val room = slot.getMaxStackSize - slot.stackSize
          if (room < amount) {
            slot.stackSize += room
            amount -= room
          }
          else {
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

  def dropItem(item: ItemStack, world: World, x: Int, y: Int, z: Int, rand: Random): Unit = {
    if (item != null) {
      val f = rand.nextFloat * 0.8F + 0.1F
      val f1 = rand.nextFloat * 0.8F + 0.1F
      val f2 = rand.nextFloat * 0.8F + 0.1F
      while (item.stackSize > 0) {
        var k1 = rand.nextInt(21) + 10
        if (k1 > item.stackSize) {
          k1 = item.stackSize
        }
        item.stackSize -= k1
        val entityitem = new EntityItem(world, (x.toFloat + f).toDouble, (y.toFloat + f1).toDouble, (z.toFloat + f2).toDouble, new ItemStack(item.getItem, k1, item.getItemDamage))
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
  }

  def removeItem(item: ItemStack, slots: Array[ItemStack], restrictions: Array[Int]): Boolean = {
    if (item == null) {
      return true
    }
    var amountLeftToRemove: Int = item.stackSize
    if (restrictions != null) {
      util.Arrays.sort(restrictions)
    }
    for (i <- 0 until slots.length) {
      if (restrictions == null || !(util.Arrays.binarySearch(restrictions, i) >= 0)) {
        if (slots(i) != null && slots(i).isItemEqual(item)) {
          val slot = slots(i)
          val amount = slot.stackSize
          if (amount <= amountLeftToRemove) {
            slots(i) = null
            amountLeftToRemove -= amount
            if (amountLeftToRemove <= 0) {
              return true
            }
          }
          else {
            slot.stackSize -= amountLeftToRemove
            return true
          }
        }
      }
    }
    false
  }

  def capitalize(input: String) = input.substring(0, 1).toUpperCase + input.substring(1)

  def blueColor = colorFromARGB(0, 0, 0, 255)

  def greenColor = colorFromARGB(0, 0, 255, 0)

  def orangeColor = colorFromARGB(0, 255, 140, 0)

  def colorFromARGB(a: Int, r: Int, g: Int, b: Int) = {
    var r1 = 0
    r1 += (a & 255) << 24
    r1 += (r & 255) << 16
    r1 += (g & 255) << 8
    r1 += b & 255
    r1
  }

  def blueify(name: String) = EnumTechLevel.MICRO.getTooltipEnum + name + EnumChatFormatting.RESET

  def greenify(name: String) = EnumTechLevel.NANO.getTooltipEnum + name + EnumChatFormatting.RESET

  def orangeify(name: String) = EnumTechLevel.FEMTO.getTooltipEnum + name + EnumChatFormatting.RESET

  def formatIntegerToString(i: Int) = formatIntegerString(String.valueOf(i))

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

  def sendMessageToPlayer(username: String, message: String, formatting: String): Boolean = sendMessageToPlayer(MinecraftServer.getServer.getConfigurationManager.func_152612_a(username), message, formatting)

  def sendMessageToPlayer(assistant: EntityPlayer, message: String): Boolean = sendMessageToPlayer(assistant, message, "")

  def sendMessageToPlayer(assistant: EntityPlayer, message: String, formatting: String): Boolean = {
    if (assistant != null) {
      assistant.addChatMessage(new ChatComponentText(new StringBuilder().append(EnumChatFormatting.GOLD).append("Femtocraft").append(EnumChatFormatting.RESET).append(": ").append(formatting).append(message).append(EnumChatFormatting.RESET).toString()))
      return true
    }
    false
  }
}

