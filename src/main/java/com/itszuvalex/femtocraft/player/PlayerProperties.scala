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
package com.itszuvalex.femtocraft.player

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.player.IFemtocraftPlayerProperty
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler
import com.itszuvalex.femtocraft.network.messages.MessagePlayerProperty
import cpw.mods.fml.common.FMLCommonHandler
import net.minecraft.entity.Entity
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.common.IExtendedEntityProperties
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._

object PlayerProperties {
  val PROP_TAG = "femtocraft.player.properties"
  private val propertiesClasses = new util.TreeMap[String, Class[_ <: IFemtocraftPlayerProperty]]

  def registerPlayerProperty(name: String, propertyClass: Class[_ <: IFemtocraftPlayerProperty]): Boolean = {
    if (propertiesClasses.containsKey(name)) return false
    propertiesClasses.put(name, propertyClass) != null
  }

  def register(player: EntityPlayer) = player.registerExtendedProperties(PROP_TAG, new PlayerProperties(player))

  def get(player: EntityPlayer) = player.getExtendedProperties(PROP_TAG).asInstanceOf[PlayerProperties]
}

class PlayerProperties(protected final var player: EntityPlayer) extends IExtendedEntityProperties {
  private val properties = new util.TreeMap[String, IFemtocraftPlayerProperty]


  for (entry <- PlayerProperties.propertiesClasses.entrySet) {
    try {
      properties.put(entry.getKey, entry.getValue.newInstance)
    }
    catch {
      case e: InstantiationException =>
        Femtocraft.log(Level.ERROR, "Failed to create new instance of " + entry.getKey + " on creating PlayerProperties for player: " + player + " name: " + player.getCommandSenderName)
        e.printStackTrace()
      case e: IllegalAccessException =>
        e.printStackTrace()
    }
  }

  def getProperty(name: String) = properties.get(name)

  def this() = this(null)

  def sync() {
    if (FMLCommonHandler.instance.getEffectiveSide.isClient) {
      return
    }
    val compound = new NBTTagCompound

    for (entry <- properties.entrySet) {
      savePropertyToCompound(entry.getKey, compound)
    }
    FemtocraftPacketHandler.INSTANCE.sendTo(new MessagePlayerProperty(player.getCommandSenderName, compound), player.asInstanceOf[EntityPlayerMP])
  }

  def sync(property: String) {
    if (FMLCommonHandler.instance.getEffectiveSide.isClient) return
    val packetCompound = new NBTTagCompound
    savePropertyToCompound(property, packetCompound)
    FemtocraftPacketHandler.INSTANCE.sendTo(new MessagePlayerProperty(player.getCommandSenderName, packetCompound), player.asInstanceOf[EntityPlayerMP])
  }

  private def savePropertyToCompound(property: String, packetCompound: NBTTagCompound) {
    if (FMLCommonHandler.instance.getEffectiveSide.isClient) return
    val prop = properties.get(property)
    if (prop != null) {
      val propCompound = new NBTTagCompound
      prop.toDescriptionPacket(propCompound)
      packetCompound.setTag(property, propCompound)
    }
  }

  def saveNBTData(compound: NBTTagCompound) {
    val propTag = new NBTTagCompound
    for (entry <- properties.entrySet) {
      val entryComp = new NBTTagCompound
      entry.getValue.saveToNBT(entryComp)
      propTag.setTag(entry.getKey, entryComp)
    }
    compound.setTag(PlayerProperties.PROP_TAG, propTag)
  }

  def loadNBTData(compound: NBTTagCompound) {
    val propTag = compound.getCompoundTag(PlayerProperties.PROP_TAG)
    for (entry <- properties.entrySet) {
      entry.getValue.loadFromNBT(propTag.getCompoundTag(entry.getKey))
    }
  }

  def init(entity: Entity, world: World) {
  }

  def handlePacket(compound: NBTTagCompound) {
    for (entry <- properties.entrySet) {
      if (compound.hasKey(entry.getKey)) {
        entry.getValue.loadFromDescription(compound.getCompoundTag(entry.getKey))
      }
    }
  }
}
