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
package com.itszuvalex.femtocraft

import java.io.{File, FileInputStream, FileOutputStream}
import java.util.Random

import com.itszuvalex.femtocraft.FemtocraftOreRetrogenHandler._
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs
import com.itszuvalex.femtocraft.utils.FemtocraftFileUtils
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.nbt.{CompressedStreamTools, NBTTagCompound}
import net.minecraft.world.World
import net.minecraftforge.event.world.ChunkDataEvent
import org.apache.logging.log4j.Level

/**
 * Created by Christopher Harris (Itszuvalex) on 7/26/14.
 */
object FemtocraftOreRetrogenHandler {
  private val DIRECTORY      = "Retrogen"
  private var isRetroGenning = false
}

class FemtocraftOreRetrogenHandler {
  private val random = new Random

  @SubscribeEvent def onChunkLoad(event: ChunkDataEvent.Load) {
    if (event.world.isRemote) return
    if (event.isCanceled) return
    if (!FemtocraftConfigs.retroGen) return
    if (isRetroGenning) return
    isRetroGenning = true
    val regionX = event.getChunk.xPosition >> 5
    val regionZ = event.getChunk.zPosition >> 5
    var regionCompound = getRetroGenFile(event.world, regionX, regionZ)
    if (regionCompound == null) {
      if (FemtocraftConfigs.retrogenAlerts) {
        Femtocraft.log(Level.WARN, "No Retrogen File found for worldId:" + event.world.provider.dimensionId + " x:" + regionX + " z:" + regionZ + ".  Generating now.")
      }
    }
    regionCompound = if (regionCompound == null) new NBTTagCompound else regionCompound
    if (FemtocraftOreGenerator.shouldRetroGenerate(event.world, regionCompound)) {
      val minRegionChunkX: Int = regionX << 5
      val maxRegionChunkX: Int = minRegionChunkX + 32
      val minRegionChunkZ: Int = regionZ << 5
      val maxRegionChunkZ: Int = maxRegionChunkX + 32
      for (x <- minRegionChunkX until maxRegionChunkX) {
        for (z <- minRegionChunkZ until maxRegionChunkZ) {
          FemtocraftOreGenerator.retroGenerate(random, event.world.getChunkFromChunkCoords(x, z), regionCompound)
        }
      }
      saveRetroGenFile(event.world, regionX, regionZ, regionCompound)
    }
    isRetroGenning = false
  }

  private def saveRetroGenFile(world: World, regionX: Int, regionZ: Int, regionCompound: NBTTagCompound): Boolean = {
    try {
      val folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY)
      if (!folder.exists) {
        folder.mkdir
      }
      val worldFolder = new File(folder.getPath, String.valueOf(world.provider.dimensionId))
      if (!worldFolder.exists) {
        worldFolder.mkdir
      }
      val filename = getRegionFileName(regionX, regionZ)
      try {
        val file = new File(worldFolder, filename)
        if (!file.exists) {
          file.createNewFile
        }
        val fileoutputstream = new FileOutputStream(file)
        CompressedStreamTools.writeCompressed(regionCompound, fileoutputstream)
        fileoutputstream.flush()
        fileoutputstream.close()
      }
      catch {
        case exception: Exception =>
          Femtocraft.log(Level.ERROR, "Failed to save data for " + filename + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
          exception.printStackTrace()
      }
    }
    catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR, "Failed to create folder " + FemtocraftFileUtils.savePathFemtocraft(world) + File.pathSeparator + DIRECTORY + ".")
        e.printStackTrace()
        return false
    }
    false
  }

  private def getRetroGenFile(world: World, regionX: Int, regionZ: Int): NBTTagCompound = {
    try {
      val folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY)
      if (!folder.exists) {
        if (FemtocraftConfigs.retrogenAlerts) {
          Femtocraft.log(Level.WARN, "No " + DIRECTORY + " folder found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        }
        return null
      }
      val worldFolder = new File(folder.getPath, String.valueOf(world.provider.dimensionId))
      if (!worldFolder.exists) {
        if (FemtocraftConfigs.retrogenAlerts) {
          Femtocraft.log(Level.WARN, "No " + String.valueOf(world.provider.dimensionId) + " folder found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        }
        return null
      }
      val filename = getRegionFileName(regionX, regionZ)
      try {
        val regionFile = new File(worldFolder.getPath, filename)
        if (!regionFile.exists) return null
        val fileinputstream: FileInputStream = new FileInputStream(regionFile)
        val data = CompressedStreamTools.readCompressed(fileinputstream)
        fileinputstream.close()
        return data
      }
      catch {
        case e: Exception =>
          Femtocraft.log(Level.ERROR, "Failed to load data from file " + filename + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
          e.printStackTrace()
      }
    }
    catch {
      case exception: Exception =>
        Femtocraft.log(Level.ERROR, "Failed to load data from folder " + DIRECTORY + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        exception.printStackTrace()
        return null
    }
    null
  }

  private def getRegionFileName(regionX: Int, regionZ: Int) = "region." + regionX + "." + regionZ + ".dat"
}
