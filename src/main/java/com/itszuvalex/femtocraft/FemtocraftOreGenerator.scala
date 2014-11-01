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

import java.util.Random

import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs
import cpw.mods.fml.common.IWorldGenerator
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraft.world.chunk.{Chunk, IChunkProvider}
import net.minecraft.world.gen.feature.WorldGenMinable

object FemtocraftOreGenerator {
  val ORE_GEN: String = "OreGen"

  def retroGenerate(random: Random, chunk: Chunk, regionOreConfig: NBTTagCompound) {
    generate(random, chunk.xPosition, chunk.zPosition, chunk.worldObj, regionOreConfig)
  }

  def shouldRetroGenerate(world: World, regionOreConfig: NBTTagCompound): Boolean = {
    world.provider.dimensionId match {
      case -1 =>
        shouldRetroGenNether(world, regionOreConfig)
      case 0  =>
        shouldRetroGenSurface(world, regionOreConfig)
      case 1  =>
        shouldRetroGenEnd(world, regionOreConfig)
      case _  =>
        shouldRetroGenSurface(world, regionOreConfig)
    }
  }

  private def shouldRetroGenEnd(world: World, regionOreConfig: NBTTagCompound) = false

  private def shouldRetroGenSurface(world: World, regionOreConfig: NBTTagCompound) =
    (FemtocraftConfigs.fareniteGen && !getOreGenerated(regionOreConfig, Femtocraft.blockOreFarenite.getUnlocalizedName)) ||
    (FemtocraftConfigs.platinumGen && !getOreGenerated(regionOreConfig, Femtocraft.blockOrePlatinum.getUnlocalizedName)) ||
    (FemtocraftConfigs.thoriumGen && !getOreGenerated(regionOreConfig, Femtocraft.blockOreThorium.getUnlocalizedName)) ||
    (FemtocraftConfigs.titaniumGen && !getOreGenerated(regionOreConfig, Femtocraft.blockOreTitanium.getUnlocalizedName) ||
     (FemtocraftConfigs.lodestoneGen && !getOreGenerated(regionOreConfig, Femtocraft.blockOreLodestone.getLocalizedName)))


  private def shouldRetroGenNether(world: World, regionOreConfig: NBTTagCompound) =
    FemtocraftConfigs.maleniteGen && !getOreGenerated(regionOreConfig, Femtocraft.blockOreMalenite.getUnlocalizedName)

  def generate(random: Random, chunkX: Int, chunkZ: Int, world: World, regionOreConfig: NBTTagCompound) {
    world.provider.dimensionId match {
      case -1 =>
        generateNether(world, random, chunkX * 16, chunkZ * 16, regionOreConfig)
      case 0  =>
        generateSurface(world, random, chunkX * 16, chunkZ * 16, regionOreConfig)
      case 1  =>
        generateEnd(world, random, chunkX * 16, chunkZ * 16, regionOreConfig)
      case _  =>
        generateSurface(world, random, chunkX * 16, chunkZ * 16, regionOreConfig)
    }
  }

  private def generateNether(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (FemtocraftConfigs.maleniteGen) {
      generateMalenite(world, random, i, j, regionOreConfig)
    }
  }

  private def generateSurface(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (FemtocraftConfigs.titaniumGen) {
      generateTitanium(world, random, i, j, regionOreConfig)
    }
    if (FemtocraftConfigs.platinumGen) {
      generatePlatinum(world, random, i, j, regionOreConfig)
    }
    if (FemtocraftConfigs.thoriumGen) {
      generateThorium(world, random, i, j, regionOreConfig)
    }
    if (FemtocraftConfigs.fareniteGen) {
      generateFarenite(world, random, i, j, regionOreConfig)
    }
    if (FemtocraftConfigs.lodestoneGen) {
      generateLodestone(world, random, i, j, regionOreConfig)
    }
  }

  def generateLodestone(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (getOreGenerated(regionOreConfig, Femtocraft.blockOreLodestone.getUnlocalizedName)) {
      return
    }
    for (k <- 0 until FemtocraftConfigs.lodestoneOreVeinsPerChunkCount) {
      val xCoord = i + random.nextInt(16)
      val yCoord = random.nextInt(FemtocraftConfigs.lodestoneOreYHeightMax - FemtocraftConfigs.lodestoneOreYHeightMin) + FemtocraftConfigs.lodestoneOreYHeightMin
      val zCoord = j + random.nextInt(16)
      new WorldGenMinable(Femtocraft.blockOreLodestone, FemtocraftConfigs.lodestoneOreBlockPerVeinCount).generate(world, random, xCoord, yCoord, zCoord)
    }
    markOreGeneration(regionOreConfig, Femtocraft.blockOreLodestone.getUnlocalizedName, true)
  }

  def generateFarenite(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (getOreGenerated(regionOreConfig, Femtocraft.blockOreFarenite.getUnlocalizedName)) {
      return
    }
    for (k <- 0 until FemtocraftConfigs.fareniteOreVeinsPerChunkCount) {
      val xCoord = i + random.nextInt(16)
      val yCoord = random.nextInt(FemtocraftConfigs.fareniteOreYHeightMax - FemtocraftConfigs.fareniteOreYHeightMin) + FemtocraftConfigs.fareniteOreYHeightMin
      val zCoord = j + random.nextInt(16)
      new WorldGenMinable(Femtocraft.blockOreFarenite, FemtocraftConfigs.fareniteOreBlockPerVeinCount).generate(world, random, xCoord, yCoord, zCoord)
    }
    markOreGeneration(regionOreConfig, Femtocraft.blockOreFarenite.getUnlocalizedName, true)
  }

  def generateThorium(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (getOreGenerated(regionOreConfig, Femtocraft.blockOreThorium.getUnlocalizedName)) {
      return
    }
    for (k <- 0 until FemtocraftConfigs.thoriumOreVeinsPerChunkCount) {
      val xCoord = i + random.nextInt(16)
      val yCoord = random.nextInt(FemtocraftConfigs.thoriumOreYHeightMax - FemtocraftConfigs.thoriumOreYHeightMin) + FemtocraftConfigs.thoriumOreYHeightMin
      val zCoord = j + random.nextInt(16)
      new WorldGenMinable(Femtocraft.blockOreThorium, FemtocraftConfigs.thoriumOreBlockPerVeinCount).generate(world, random, xCoord, yCoord, zCoord)
    }
    markOreGeneration(regionOreConfig, Femtocraft.blockOreThorium.getUnlocalizedName, true)
  }

  def generatePlatinum(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (getOreGenerated(regionOreConfig, Femtocraft.blockOrePlatinum.getUnlocalizedName)) {
      return
    }
    for (k <- 0 until FemtocraftConfigs.platinumOreVeinsPerChunkCount) {
      val xCoord = i + random.nextInt(16)
      val yCoord = random.nextInt(FemtocraftConfigs.platinumOreYHeightMax - FemtocraftConfigs.platinumOreYHeightMin) + FemtocraftConfigs.platinumOreYHeightMin
      val zCoord = j + random.nextInt(16)
      new WorldGenMinable(Femtocraft.blockOrePlatinum, FemtocraftConfigs.platinumOreBlockPerVeinCount).generate(world, random, xCoord, yCoord, zCoord)
    }

    markOreGeneration(regionOreConfig, Femtocraft.blockOrePlatinum.getUnlocalizedName, true)
  }

  def generateTitanium(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (getOreGenerated(regionOreConfig, Femtocraft.blockOreTitanium.getUnlocalizedName)) {
      return
    }
    for (k <- 0 until FemtocraftConfigs.titaniumOreVeinsPerChunkCount) {
      val xCoord = i + random.nextInt(16)
      val yCoord = random.nextInt(FemtocraftConfigs.titaniumOreYHeightMax - FemtocraftConfigs.titaniumOreYHeightMin) + FemtocraftConfigs.titaniumOreYHeightMin
      val zCoord = j + random.nextInt(16)
      new WorldGenMinable(Femtocraft.blockOreTitanium, FemtocraftConfigs.titaniumOreBlockPerVeinCount).generate(world, random, xCoord, yCoord, zCoord)
    }
    markOreGeneration(regionOreConfig, Femtocraft.blockOreTitanium.getUnlocalizedName, true)
  }

  private def generateEnd(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
  }

  def generateMalenite(world: World, random: Random, i: Int, j: Int, regionOreConfig: NBTTagCompound) {
    if (getOreGenerated(regionOreConfig, Femtocraft.blockOreMalenite.getUnlocalizedName)) {
      return
    }
    for (k <- 0 until FemtocraftConfigs.maleniteOreVeinsPerChunkCount) {
      val xCoord = i + random.nextInt(16)
      val yCoord = random.nextInt(FemtocraftConfigs.maleniteOreYHeightMax - FemtocraftConfigs.maleniteOreYHeightMin) + FemtocraftConfigs.maleniteOreYHeightMin
      val zCoord = j + random.nextInt(16)
      new WorldGenMinable(Femtocraft.blockOreMalenite, FemtocraftConfigs.maleniteOreBlockPerVeinCount, Blocks.netherrack).generate(world, random, xCoord, yCoord, zCoord)
    }
    markOreGeneration(regionOreConfig, Femtocraft.blockOreMalenite.getUnlocalizedName, true)
  }

  def markOreGeneration(compound: NBTTagCompound, oreName: String, value: Boolean) {
    if (compound == null) return
    var femtocraftTag: NBTTagCompound = null
    if (!compound.hasKey(Femtocraft.ID.toLowerCase)) {
      femtocraftTag = new NBTTagCompound
      compound.setTag(Femtocraft.ID.toLowerCase, femtocraftTag)
    }
    else {
      femtocraftTag = compound.getCompoundTag(Femtocraft.ID.toLowerCase)
    }
    var oreTag: NBTTagCompound = null
    if (!femtocraftTag.hasKey(ORE_GEN)) {
      oreTag = new NBTTagCompound
      femtocraftTag.setTag(ORE_GEN, oreTag)
    }
    else {
      oreTag = femtocraftTag.getCompoundTag(ORE_GEN)
    }
    oreTag.setBoolean(oreName, value)
  }

  def getOreGenerated(compound: NBTTagCompound, oreName: String): Boolean = {
    if (compound == null) return false
    if (!compound.hasKey(Femtocraft.ID.toLowerCase)) return false
    val femtocraftTag = compound.getCompoundTag(Femtocraft.ID.toLowerCase)
    if (!femtocraftTag.hasKey(ORE_GEN)) return false
    val oreTag = femtocraftTag.getCompoundTag(ORE_GEN)
    if (!oreTag.hasKey(oreName)) return false
    oreTag.getBoolean(oreName)
  }
}

class FemtocraftOreGenerator extends IWorldGenerator {
  def generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkProvider, chunkProvider: IChunkProvider) {
    FemtocraftOreGenerator.generate(random, chunkX, chunkZ, world, null)
  }
}
