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

import java.io.File

import com.itszuvalex.femtocraft.api.FemtocraftAPI
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import net.minecraft.server.MinecraftServer
import net.minecraft.world.World


/**
 * Created by Christopher Harris (Itszuvalex) on 7/26/14.
 */
object FemtocraftFileUtils {
  def savePathFemtocraft(world: World) = {
    val dir = new File(savePath(world), FemtocraftAPI.FemtocraftID.toLowerCase)
    if (!dir.exists) {
      dir.mkdir
    }
    dir.getPath
  }

  def savePath(world: World) = if (!MinecraftServer.getServer.isDedicatedServer) {
    Minecraft.getMinecraft.mcDataDir + "/saves/" + world.getSaveHandler.getWorldDirectoryName
  } else {MinecraftServer.getServer.getFile(world.getSaveHandler.getWorldDirectoryName).getPath}

  def configFolder: File = {
    val path = configPath
    val f = new File(path)
    if (!f.exists()) {
      f.mkdir()
    }
    f
  }

  def customConfigPath = configPath + "custom/"

  def autogenConfigPath = configPath + "autogen/"

  def configPath = if (FMLCommonHandler.instance.getEffectiveSide == Side.SERVER) {
    MinecraftServer.getServer.getFile("/config/" + FemtocraftAPI.FemtocraftID + "/").getPath + "/"
  } else {Minecraft.getMinecraft.mcDataDir + "/config/" + FemtocraftAPI.FemtocraftID + "/"}
}



