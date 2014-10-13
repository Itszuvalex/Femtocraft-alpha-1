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

import java.util
import java.util.List
import java.util.logging.Level

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.events.EventAssemblerRegister
import com.itszuvalex.femtocraft.managers.research.{EnumTechLevel, ResearchTechnology}
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.block.Block
import net.minecraft.item.crafting.{CraftingManager, IRecipe, ShapedRecipes, ShapelessRecipes}
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.oredict.{OreDictionary, ShapedOreRecipe, ShapelessOreRecipe}

import scala.collection.JavaConversions._


/** @author chris
  * @category Manager
  * @info This manager is responsible for all Femtocraft AssemblerRecipes. All Assembler/Dissassemblers look to this
  *       manager for recipe lookup. Recipes can be specified to only be disassemble-able, or only reassemble-able.
  *       Dissassemblers simply break down items, Reassembles must use schematics to specify the recipe to follow. <br> All
  *       recipes are ordered according to their signature in the inventory. The entire 9 slots are used for the input
  *       signature. ItemStack stackSize does not matter for ordering. Exceptions will be thrown when attempting to addInput
  *       recipes when their signature is already associated with a recipe (no check is performed to see if the recipes are
  *       actually equal or not.) When reconstructing, items must conform to the input signature, and all 9 slots are
  *       important. Slots that are null in the recipe must not contain any items, and vice versa. This will be separately
  *       enforced in the schematic-creating TileEntities, but it it is also stated here for reference.
  */
object ManagerAssemblerRecipe {
  val shapelessPermuteTimeMillis: Long = 10
}

class ManagerAssemblerRecipe {
  private val ard = new AssemblerRecipeDatabase

  def init() {
    registerRecipes()
  }

  private def registerRecipes() {
    Femtocraft.log(Level.INFO, "Registering Femtocraft assembler recipes.")
    if (ard.shouldRegister) {
      Femtocraft.assemblerConfigs.setBatchLoading(true)
      registerCustomRecipes()
      registerFemtoDecompositionRecipes()
      registerNanoDecompositionRecipes()
      registerMicroDecompositionRecipes
      registerMacroDecompositionRecipes()
      registerFemtocraftAssemblerRecipes()
      Femtocraft.assemblerConfigs.setBatchLoading(false)
    }
    Femtocraft.log(Level.INFO, "Finished registering Femtocraft assembler recipes.")
  }

  private def registerCustomRecipes() {
    Femtocraft.log(Level.INFO, "Registering custom assembler recipes.")
    Femtocraft.assemblerConfigs.loadCustomRecipes
    Femtocraft.log(Level.INFO, "Finished registering custom assembler recipes.")
  }

  private def registerFemtoDecompositionRecipes() {
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemRectangulon), null, null, null), 3, new ItemStack(Femtocraft.itemCrystallite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemCubit), null, null, null), 3, new ItemStack(Femtocraft.itemMineralite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemCubit), null, null, null), 3, new ItemStack(Femtocraft.itemMetallite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemRectangulon), null, null, null), 3, new ItemStack(Femtocraft.itemFaunite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemPlaneoid), null, null, null), 3, new ItemStack(Femtocraft.itemElectrite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), null, null, null), 3, new ItemStack(Femtocraft.itemFlorite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
  }

  private def registerNanoDecompositionRecipes() {
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2)), 2, new ItemStack(Femtocraft.itemMicroCrystal), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemMineralite), new ItemStack(Femtocraft.itemFaunite), null, null, null), 2, new ItemStack(Femtocraft.itemProteinChain), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemFaunite), null, null, null), 2, new ItemStack(Femtocraft.itemNerveCluster), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMetallite), null, new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemElectrite), null, new ItemStack(Femtocraft.itemMetallite), null), 2, new ItemStack(Femtocraft.itemConductiveAlloy), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMineralite), null, new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemMetallite), null, new ItemStack(Femtocraft.itemMineralite), null), 2, new ItemStack(Femtocraft.itemMetalComposite), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), null, new ItemStack(Femtocraft.itemMineralite), null, null, null), 2, new ItemStack(Femtocraft.itemFibrousStrand), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemMineralite), null, new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2, new ItemStack(Femtocraft.itemMineralLattice), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemCrystallite), new ItemStack(Femtocraft.itemFlorite), null, null, null), 2, new ItemStack(Femtocraft.itemFungalSpores), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemMineralite), new ItemStack(Femtocraft.itemElectrite), null, null, null), 2, new ItemStack(Femtocraft.itemIonicChunk), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemFlorite), null, null, null), 2, new ItemStack(Femtocraft.itemReplicatingMaterial), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCrystallite), new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2, new ItemStack(Femtocraft.itemSpinyFilament), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCrystallite), new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2, new ItemStack(Femtocraft.itemHardenedBulb), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemElectrite), null, null, null), 2, new ItemStack(Femtocraft.itemMorphicChannel), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemFlorite), null, null, null), 2, new ItemStack(Femtocraft.itemSynthesizedFiber), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMetallite), null, new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemFaunite), null, new ItemStack(Femtocraft.itemMetallite), null), 2, new ItemStack(Femtocraft.itemOrganometallicPlate), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
  }

  private def registerMicroDecompositionRecipes {
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null, null), 1, new ItemStack(Block.stone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null, null), 1, new ItemStack(Block.grass), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null), 1, new ItemStack(Block.dirt), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Block.cobblestone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Block.planks), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null), 1, new ItemStack(Block.sapling), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null, null, null, null), 1, new ItemStack(Block.sand), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Block.leaves), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null, null, null, null, null), 1, new ItemStack(Block.web), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null), 1, new ItemStack(Block.deadBush), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null), 1, new ItemStack(Block.plantYellow), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null), 1, new ItemStack(Block.plantRed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null, null, null, null), 1, new ItemStack(Block.mushroomBrown), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null, null, null), 1, new ItemStack(Block.mushroomRed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice), new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null, null, null), 1, new ItemStack(Block.cobblestoneMossy), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null, null), 1, new ItemStack(Block.obsidian), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null, null, null), 1, new ItemStack(Block.ice), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null), 1, new ItemStack(Block.cactus), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null, null, null), 1, new ItemStack(Block.pumpkin), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null), 1, new ItemStack(Block.netherrack), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Block.slowSand), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemConductiveAlloy), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Item.glowstone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Block.melon), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null), 1, new ItemStack(Block.vine), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores), new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Block.mycelium), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null), 1, new ItemStack(Block.waterlily), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemConductiveAlloy), new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Block.whiteStone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null, null, null, null), 1, new ItemStack(Block.cocoaPlant), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null, null), 1, new ItemStack(Item.appleRed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Item.coal), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8)), 1, new ItemStack(Item.diamond), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite), null, null, null, null, null, null, null, null), 1, new ItemStack(Item.ingotIron), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite), new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null), 1, new ItemStack(Item.ingotGold), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null), 1, new ItemStack(Item.stick), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null, null), 1, new ItemStack(Item.silk), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null, null, null, null), 1, new ItemStack(Item.feather), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk), new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null), 1, new ItemStack(Item.gunpowder), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Item.seeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Item.wheat), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null), 1, new ItemStack(Item.flint), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null), 1, new ItemStack(Item.porkRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null, null, null, null), 1, new ItemStack(Item.porkCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk), new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Item.redstone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, null, null, null, new ItemStack(Femtocraft.itemMineralLattice)), 1, new ItemStack(Item.snowball), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain), new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null, null, null), 1, new ItemStack(Item.leather), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null, null), 1, new ItemStack(Item.clay), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null, null, null), 1, new ItemStack(Item.reed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null, null), 1, new ItemStack(Item.slimeBall), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Item.egg), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null), 1, new ItemStack(Item.fishRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain), new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null, null, null, null), 1, new ItemStack(Item.fishCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null, null, null), 1, new ItemStack(Item.dyePowder, 1, OreDictionary.WILDCARD_VALUE), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null), 1, new ItemStack(Item.bone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Item.pumpkinSeeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemHardenedBulb), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null), 1, new ItemStack(Item.melonSeeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null), 1, new ItemStack(Item.beefRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null, null, null), 1, new ItemStack(Item.beefCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), null, null, new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null), 1, new ItemStack(Item.chickenRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null, null), 1, new ItemStack(Item.chickenCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null), 1, new ItemStack(Item.rottenFlesh), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemIonicChunk), null, new ItemStack(Femtocraft.itemIonicChunk), new ItemStack(Femtocraft.itemOrganometallicPlate), new ItemStack(Femtocraft.itemIonicChunk), null, new ItemStack(Femtocraft.itemIonicChunk), null), 1, new ItemStack(Item.enderPearl), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Item.ghastTear), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Item.netherStalkSeeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemOrganometallicPlate), null, null, null, null, null, null, null), 1, new ItemStack(Item.spiderEye), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel), new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null, null, null), 1, new ItemStack(Item.blazePowder), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite), new ItemStack(Femtocraft.itemConductiveAlloy), null, null, null, null, null, null, null), 1, new ItemStack(Item.emerald), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null), 1, new ItemStack(Item.carrot), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null), 1, new ItemStack(Item.potato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null), 1, new ItemStack(Item.bakedPotato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null), 1, new ItemStack(Item.poisonousPotato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addDecompositionRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.egg), new ItemStack(Item.sugar, 2), new ItemStack(Item.wheat, 3), null, null, null, null, null, null), 1, new ItemStack(Item.cake), EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Femtocraft.itemHardenedBulb, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Item.diamond, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Femtocraft.itemHardenedBulb, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Femtocraft.itemHardenedBulb, 64)), 1, new ItemStack(Item.netherStar), EnumTechLevel.MICRO, FemtocraftTechnologies.NETHER_STAR_FABRICATION))
  }

  private def registerMacroDecompositionRecipes() {
  }

  def registerDefaultRecipes() {
    Femtocraft.log(Level.INFO, "Scraping Minecraft recipe registries for assembler recipe mappings.")
    if (!ard.shouldRegister) {
      Femtocraft.log(Level.INFO, "Database already exists.  " + "Skipping item registration.")
      return
    }
    Femtocraft.log(Level.WARNING, "Registering assembler recipes from Vanilla Minecraft's Crafting Manager.\t This may take " + "awhile ._.")
    val recipes = CraftingManager.getInstance.getRecipeList.filter(i => i != null && i.isInstanceOf[IRecipe]).map(_.asInstanceOf[IRecipe])
    Femtocraft.log(Level.WARNING, "Registering shaped recipes from Vanilla Minecraft's Crafting Manager.")
    recipes.filter(i => i.isInstanceOf[ShapedRecipes] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapedRecipes])
    .foreach(sr => {
      Femtocraft.log(Level.CONFIG, "Attempting to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + ".")
      val valid = registerShapedRecipe(sr.recipeItems, sr.getRecipeOutput, sr.recipeWidth, sr.recipeHeight)
      if (!valid) {
        Femtocraft.log(Level.WARNING, "Failed to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + "!")
      }
      else {
        Femtocraft.log(Level.CONFIG, "Loaded Vanilla Minecraft shaped recipe as assembler recipe for " + sr.getRecipeOutput.getDisplayName + ".")
      }
    })
    Femtocraft.log(Level.WARNING, "Registering shaped ore recipes from Forge.")
    recipes.filter(i => i.isInstanceOf[ShapedOreRecipe] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapedOreRecipe])
    .foreach(orecipe => {
      Femtocraft.log(Level.CONFIG, "Attempting to register shaped assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + ".")
      var width = 0
      var height = 0
      try {
        val width_field = classOf[ShapedOreRecipe].getDeclaredField("width")
        var prev = width_field.isAccessible
        if (!prev) {
          width_field.setAccessible(true)
        }
        width = width_field.getInt(orecipe)
        if (!prev) {
          width_field.setAccessible(prev)
        }


        val height_field = classOf[ShapedOreRecipe].getDeclaredField("height")
        prev = height_field.isAccessible
        if (!prev) {
          height_field.setAccessible(true)
        }
        height = height_field.getInt(orecipe)
        if (!prev) {
          height_field.setAccessible(prev)
        }

      }
      catch {
        case e: SecurityException        => e.printStackTrace()
        case e: NoSuchFieldException     => e.printStackTrace()
        case e: IllegalArgumentException => e.printStackTrace()
        case e: IllegalAccessException   => e.printStackTrace()
      }
      val valid = registerShapedOreRecipe(orecipe.getInput, orecipe.getRecipeOutput, width, height)
      if (!valid) {
        Femtocraft.log(Level.WARNING, "Failed to register shaped assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + "!")
      }
      else {
        Femtocraft.log(Level.CONFIG, "LoadedForge shaped ore recipe as assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + ".")
      }
    })
    Femtocraft.log(Level.WARNING, "Registering shapeless recipes from Vanilla Minecraft's Crafting Manager.")
    recipes.filter(i => i.isInstanceOf[ShapelessRecipes] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapelessRecipes])
    .foreach(recipe => {
      Femtocraft.log(Level.CONFIG, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + ".")
      val valid: Boolean = registerShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput)
      if (!valid) {
        Femtocraft.log(Level.WARNING, "Failed to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + "!")
        Femtocraft.log(Level.WARNING, "I have no clue how this would happen...as the search space is literally " + "thousands of configurations.  Sorry for the wait.")
      }
      else {
        Femtocraft.log(Level.CONFIG, "Loaded Vanilla Minecraft shapeless recipe as assembler recipe for + " + recipe.getRecipeOutput.getDisplayName + ".")
      }
    })

    Femtocraft.log(Level.WARNING, "Registering shapeless ore recipes from Forge.")
    recipes.filter(i => i.isInstanceOf[ShapelessOreRecipe] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapelessOreRecipe])
    .foreach(recipe => {
      Femtocraft.log(Level.CONFIG, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + ".")
      val valid: Boolean = registerShapelessOreRecipe(recipe.getInput, recipe.getRecipeOutput)
      if (!valid) {
        Femtocraft.log(Level.WARNING, "Failed to register shapeless ore assembler recipe for " + recipe.getRecipeOutput.getDisplayName + "!")
        Femtocraft.log(Level.WARNING, "I have no clue how this would happen...as the search space is literally " + "thousands of configurations.  Sorry for the wait.")
      }
      else {
        Femtocraft.log(Level.CONFIG, "Loaded Forge shapeless ore recipe as assembler recipe for + " + recipe.getRecipeOutput.getDisplayName + ".")
      }
    })
    Femtocraft.log(Level.INFO, "Finished mapping Minecraft recipes to assembler recipes.")
  }

  private def registerShapedOreRecipe(recipeInput: Array[AnyRef], recipeOutput: ItemStack, width: Int, height: Int): Boolean = {
    var done = false
    var xOffset = 0
    var yOffset = 0
    val input = Array.fill[ItemStack](9)(null)
    val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
    if (recipe.output.getItemDamage == OreDictionary.WILDCARD_VALUE) {
      recipe.output.setItemDamage(0)
    }
    while ((!done) && (xOffset < 3) && (yOffset < 3)) {
      for (i <- 0 until Math.min(recipeInput.length, 9)) {
        try {
          var item: ItemStack = null
          val obj: AnyRef = recipeInput(i)
          if (obj.isInstanceOf[util.ArrayList[_]]) {
            try {
              item = obj.asInstanceOf[util.ArrayList[ItemStack]].get(0)
            }
            catch {
              case exc: IndexOutOfBoundsException =>
                Femtocraft.log(Level.SEVERE, "Ore recipe with nothing registered in " + "ore dictionary for " + recipe.output.getDisplayName + ".")
                return false
            }
          }
          else {
            item = obj.asInstanceOf[ItemStack]
          }
          input(((i + xOffset) % width) + 3 * (yOffset + ((i + xOffset) / width))) = if (item == null) null else new ItemStack(item.itemID, 1, item.getItemDamage)
        }
        catch {
          case e: ArrayIndexOutOfBoundsException =>
            if ( {xOffset += 1; xOffset} >= 3) {
              xOffset = 0
              yOffset += 1
            }
        }
      }

      for (i <- input) {
        if (i != null) {
          if (i.getItemDamage == OreDictionary.WILDCARD_VALUE) {
            i.setItemDamage(0)
          }
        }
      }
      if (addReversableRecipe(recipe)) {
        done = true
      }
      else {
        if ( {xOffset += 1; xOffset} >= 3) {
          xOffset = 0
          yOffset += 1
        }
        done = false
      }
    }
    done
  }

  private def registerShapedRecipe(recipeItems: Array[ItemStack], recipeOutput: ItemStack, recipeWidth: Int, recipeHeight: Int): Boolean = {
    var done = false
    var xoffset = 0
    var yoffset = 0
    val input = Array.fill[ItemStack](9)(null)
    val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
    while ((!done) && ((xoffset + recipeWidth) <= 3) && ((yoffset + recipeHeight) <= 3)) {
      for (i <- 0 until Math.min(recipeItems.length, 9)) {
        val item = recipeItems(i)
        input(((i + xoffset) % recipeWidth) + 3 * (yoffset + ((i + xoffset) / recipeWidth))) = if (item == null) null else new ItemStack(item.itemID, 1, item.getItemDamage)
      }
      if (addReversableRecipe(recipe))
        done = true
      else {
        if (({xoffset += 1; xoffset} + recipeWidth) > 3) {
          xoffset = 0
          yoffset += 1
        }
        done = false
      }
    }
    done
  }

  private def registerShapelessOreRecipe(recipeItems: List[_], recipeOutput: ItemStack): Boolean = {
    var valid = false
    var slots = new Array[Int](recipeItems.size)
    val input = Array.fill[ItemStack](9)(null)
    val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
    val timeStart = System.currentTimeMillis
    if (recipe.output.getItemDamage == OreDictionary.WILDCARD_VALUE) {
      recipe.output.setItemDamage(0)
    }
    val offset = 0
    while (!valid && ((offset + recipeItems.size) <= 9)) {
      for (i <- 0 until slots.length) {
        slots(i) = i
      }
      while (!valid) {
        for (i <- 0 until Math.min(slots.length, 9)) {
          var item: ItemStack = null
          val obj = recipeItems.get(i)
          if (obj.isInstanceOf[util.ArrayList[_]]) {
            try {
              item = obj.asInstanceOf[util.ArrayList[ItemStack]].get(0)
            }
            catch {
              case exc: IndexOutOfBoundsException => {
                Femtocraft.log(Level.SEVERE, "Ore recipe with nothing registered in " + "ore dictionary for " + recipe.output.getDisplayName + ".")
                return false
              }
            }
          }
          else {
            item = obj.asInstanceOf[ItemStack]
          }
          input(slots(i) + offset) = if (item == null) null else item.copy
        }

        for (i <- input) {
          if (i != null) {
            if (i.getItemDamage == OreDictionary.WILDCARD_VALUE) {
              i.setItemDamage(0)
            }
          }
        }
        if ((System.currentTimeMillis - timeStart) > ManagerAssemblerRecipe.shapelessPermuteTimeMillis) {
          return false
        }

        if (addReversableRecipe(recipe))
          valid = true
        else {
          slots = permute(slots)
          valid = false
        }
      }
    }

    valid
  }

  private def registerShapelessRecipe(recipeItems: List[_], recipeOutput: ItemStack): Boolean = {
    var valid = false
    val slots = new Array[Int](recipeItems.size)
    val input = scala.Array.fill[ItemStack](9)(null)
    val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
    val timeStart = System.currentTimeMillis
    val offset = 0
    while (!valid && ((offset + recipeItems.size) <= 9)) {
      for (i <- 0 until slots.length) {
        slots(i) = i
      }

      while (!valid) {
        for (i <- 0 until Math.min(slots.length, 9)) {
          val item = recipeItems.get(i).asInstanceOf[ItemStack]
          input(slots(i) + offset) = if (item == null) null else item.copy
        }

        if ((System.currentTimeMillis - timeStart) > ManagerAssemblerRecipe.shapelessPermuteTimeMillis) {
          return false
        }
        addReversableRecipe(recipe)
        valid = true
      }
    }
    valid
  }

  private def permute(slots: Array[Int]): Array[Int] = {
    val k = findHighestK(slots)
    val i = findHigherI(slots, k)
    val prev= slots(k)
    slots(k) = slots(i)
    slots(i) = prev
    val remaining= Math.ceil((slots.length - k + 1) / 2f).toInt
    var r = k + 1
    var n = 0
    while ((r < slots.length) && (n < remaining)) {
      val pr = slots(r)
      slots(r) = slots(slots.length - n - 1)
      slots(slots.length - n - 1) = pr
      r += 1
      n += 1
    }
    slots
  }

  private def findHighestK(slots: Array[Int]): Int = {
    var ret: Int = 0
    for (i <- 0 until (slots.length - 1)) {
      if ((slots(i) < slots(i + 1)) && (ret < i)) {
        ret = i
      }
    }
    ret
  }

  private def findHigherI(slots: Array[Int], k: Int): Int = {
    var ret: Int = 0
    for (i <- 0 until slots.length) {
      if ((slots(k) < slots(i)) && (ret < i)) {
        ret = i
      }
    }
    ret
  }

  private def registerFemtocraftAssemblerRecipes() {
    addDecompositionRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.paper, 3), null, null, null, null, null, null, null, null), 0, new ItemStack(Femtocraft.itemPaperSchematic), EnumTechLevel.MACRO, FemtocraftTechnologies.ALGORITHMS))
    addRecompositionRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpoolGold), new ItemStack(Femtocraft.itemSpoolGold), new ItemStack(Femtocraft.itemSpoolGold), new ItemStack(Femtocraft.itemConductivePowder), new ItemStack(Femtocraft.itemConductivePowder), new ItemStack(Femtocraft.itemConductivePowder), new ItemStack(Block.planks, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Block.planks, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Block.planks, 1, OreDictionary.WILDCARD_VALUE)), 0, new ItemStack(Femtocraft.itemMicrochip, 6), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemMicrochip), new ItemStack(Femtocraft.blockMicroChargingCoil), new ItemStack(Femtocraft.itemMicrochip), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.blockMicroChargingCapacitor), EnumTechLevel.MICRO, FemtocraftTechnologies.POTENTIAL_HARVESTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Item.ingotGold), new ItemStack(Femtocraft.itemIngotTemperedTitanium), null, new ItemStack(Femtocraft.itemVacuumCore), null, new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Item.ingotGold), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.blockVacuumTube, 16), EnumTechLevel.MICRO, FemtocraftTechnologies.VACUUM_TUBES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemDopedBoard), new ItemStack(Femtocraft.itemMicrochip), new ItemStack(Femtocraft.itemDopedBoard), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.netherQuartz)), 0, new ItemStack(Femtocraft.itemNanochip, 2), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.comparator), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.redstone), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.redstone), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.comparator)), 0, new ItemStack(Femtocraft.itemNanoCalculator), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.redstone), new ItemStack(Item.comparator), new ItemStack(Item.redstone), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Block.torchRedstoneActive), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.redstone), new ItemStack(Item.comparator)), 0, new ItemStack(Femtocraft.itemNanoRegulator), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.redstoneRepeater), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.redstoneRepeater), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.comparator), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Item.redstoneRepeater)), 0, new ItemStack(Femtocraft.itemNanoSimulator), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherQuartz), null, null, new ItemStack(Femtocraft.itemIngotFarenite), null, null, new ItemStack(Item.netherQuartz), null, null), 0, new ItemStack(Femtocraft.itemFluidicConductor), EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemMicroPlating), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.itemNanoPlating, 3), EnumTechLevel.MICRO, FemtocraftTechnologies.ARTIFICIAL_MATERIALS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemMicroLogicCore), new ItemStack(Femtocraft.itemNanoCalculator), null, null, null, null, null, null), 0, new ItemStack(Femtocraft.itemBasicAICore), EnumTechLevel.MICRO, FemtocraftTechnologies.ADVANCED_PROGRAMMING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemNanoSimulator), null, new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemBasicAICore), new ItemStack(Femtocraft.itemNanoCalculator), null, new ItemStack(Femtocraft.itemNanoSimulator), null), 0, new ItemStack(Femtocraft.itemLearningCore), EnumTechLevel.MICRO, FemtocraftTechnologies.PATTERN_RECOGNITION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null, new ItemStack(Femtocraft.itemBasicAICore), null, null, new ItemStack(Femtocraft.itemNanoCalculator), null, null), 0, new ItemStack(Femtocraft.itemSchedulerCore), EnumTechLevel.MICRO, FemtocraftTechnologies.WORKLOAD_SCHEDULING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null, null, new ItemStack(Femtocraft.itemBasicAICore), null, null, null, new ItemStack(Femtocraft.itemNanoRegulator)), 0, new ItemStack(Femtocraft.itemManagerCore), EnumTechLevel.MICRO, FemtocraftTechnologies.RESOURCE_OPTIMIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.itemNanoCoil, 6), EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.blockNanoCable, 8), EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.blockMicroFurnaceUnlit), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.blockNanoInnervatorUnlit), EnumTechLevel.MICRO, FemtocraftTechnologies.KINETIC_DISSOCIATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockMicroDeconstructor), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.blockNanoDismantler), EnumTechLevel.MICRO, FemtocraftTechnologies.ATOMIC_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.blockMicroReconstructor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemManagerCore)), 0, new ItemStack(Femtocraft.blockNanoFabricator), EnumTechLevel.MICRO, FemtocraftTechnologies.ATOMIC_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.blockNanoCable)), 0, new ItemStack(Femtocraft.blockCryoEndothermalChargingBase), EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil), EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Item.netherQuartz), new ItemStack(Item.pocketSundial), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemNanoCalculator)), 0, new ItemStack(Femtocraft.itemTemporalResonator), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Item.enderPearl), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Item.enderPearl), new ItemStack(Item.compass), new ItemStack(Item.enderPearl), new ItemStack(Femtocraft.itemNanoSimulator), new ItemStack(Item.enderPearl), new ItemStack(Femtocraft.itemNanoSimulator)), 0, new ItemStack(Femtocraft.itemDimensionalMonopole), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.ingotIron), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Item.ingotIron), new ItemStack(Item.ingotIron), new ItemStack(Femtocraft.itemTemporalResonator), new ItemStack(Item.ingotIron), new ItemStack(Item.ingotIron), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Item.ingotIron)), 0, new ItemStack(Femtocraft.itemSelfFulfillingOracle), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum)), 0, new ItemStack(Femtocraft.itemCrossDimensionalCommunicator), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemSelfFulfillingOracle), new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockNanoFabricator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemTemporalResonator), new ItemStack(Femtocraft.itemNanoRegulator)), 0, new ItemStack(Femtocraft.blockNanoHorologe), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoSimulator), new ItemStack(Femtocraft.itemCrossDimensionalCommunicator), new ItemStack(Femtocraft.itemNanoSimulator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockNanoFabricator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Femtocraft.itemNanoRegulator)), 0, new ItemStack(Femtocraft.blockNanoEnmesher), EnumTechLevel.NANO, FemtocraftTechnologies.DIMENSIONAL_BRAIDING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum)), 0, new ItemStack(Femtocraft.itemDigitalSchematic, 8), EnumTechLevel.NANO, FemtocraftTechnologies.DIGITIZED_WORKLOADS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockMicroCable), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.blockNanoCable), null, null, null, null, null, null), 0, new ItemStack(Femtocraft.blockOrbitalEqualizer), EnumTechLevel.NANO, FemtocraftTechnologies.POTENTIALITY_TRANSFORMATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.blockMicroCube)), 0, new ItemStack(Femtocraft.blockNanoCubeFrame), EnumTechLevel.NANO, FemtocraftTechnologies.INDUSTRIAL_STORAGE))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.blockNanoCubeFrame), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.blockNanoCable)), 0, new ItemStack(Femtocraft.blockNanoCubePort), EnumTechLevel.NANO, FemtocraftTechnologies.INDUSTRIAL_STORAGE))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanochip)), 0, new ItemStack(Femtocraft.itemFissionReactorPlating), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null, new ItemStack(Femtocraft.itemFissionReactorPlating), null, null, new ItemStack(Femtocraft.itemNanoRegulator), null, null), 0, new ItemStack(Femtocraft.blockFissionReactorHousing), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemLearningCore), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Item.diamond), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Femtocraft.itemManagerCore)), 0, new ItemStack(Femtocraft.blockFissionReactorCore), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemFissionReactorPlating), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.blockOrbitalEqualizer), new ItemStack(Block.chest), new ItemStack(Femtocraft.blockOrbitalEqualizer), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemFissionReactorPlating), new ItemStack(Femtocraft.itemNanochip)), 0, new ItemStack(Femtocraft.blockDecontaminationChamber), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotFarenite), null, new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemIngotThorium), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemIngotThorium), new ItemStack(Femtocraft.itemIngotFarenite), null, new ItemStack(Femtocraft.itemIngotFarenite)), 0, new ItemStack(Femtocraft.itemIngotThFaSalt, 2), EnumTechLevel.NANO, FemtocraftTechnologies.THORIUM_FISSIBILITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.eyeOfEnder)), 0, new ItemStack(Femtocraft.itemMinosGate, 2), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.magmaCream), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.magmaCream), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.magmaCream), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.magmaCream)), 0, new ItemStack(Femtocraft.itemCharosGate, 2), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.fireballCharge), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.fireballCharge), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.fireballCharge), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.fireballCharge)), 0, new ItemStack(Femtocraft.itemCerberusGate, 2), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMinosGate), null, new ItemStack(Femtocraft.itemCharosGate), new ItemStack(Item.ghastTear), new ItemStack(Femtocraft.itemCharosGate), null, new ItemStack(Femtocraft.itemMinosGate), null), 0, new ItemStack(Femtocraft.itemErinyesCircuit, 3), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemCerberusGate), null, new ItemStack(Femtocraft.itemMinosGate), new ItemStack(Item.book), new ItemStack(Femtocraft.itemCharosGate), null, new ItemStack(Femtocraft.itemMinosGate), null), 0, new ItemStack(Femtocraft.itemMinervaComplex), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.slimeBall), null, new ItemStack(Item.slimeBall), new ItemStack(Femtocraft.itemMinosGate), new ItemStack(Block.pistonStickyBase), new ItemStack(Femtocraft.itemCerberusGate), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemAtlasMount), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_ROBOTICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.feather), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemHermesBus), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.gunpowder), new ItemStack(Item.ghastTear), new ItemStack(Item.gunpowder), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemPandoraCube), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Block.pistonBase), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemHerculesDrive), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_ROBOTICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Item.comparator), new ItemStack(Femtocraft.itemPanLocationalComputer), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemOrpheusProcessor), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.diamond), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Item.diamond), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemAtlasMount), new ItemStack(Item.diamond), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Item.diamond)), 0, new ItemStack(Femtocraft.itemFemtoPlating, 2), EnumTechLevel.NANO, FemtocraftTechnologies.ELEMENT_MANUFACTURING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoInnervatorUnlit), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCerberusGate), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCerberusGate)), 0, new ItemStack(Femtocraft.blockFemtoImpulserUnlit), EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_EXCITATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoDismantler), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCharosGate), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCharosGate)), 0, new ItemStack(Femtocraft.blockFemtoRepurposer), EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoFabricator), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemMinosGate), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemMinosGate)), 0, new ItemStack(Femtocraft.blockFemtoCoagulator), EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.netherrackBrick), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.netherrackBrick), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Item.netherrackBrick)), 0, new ItemStack(Femtocraft.itemStyxValve), EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.itemFemtoCoil, 6), EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.diamond), new ItemStack(Item.diamond), new ItemStack(Item.diamond), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockFemtoCable, 8), EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemPandoraCube), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase), new ItemStack(Item.netherStar), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemPandoraCube), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockPhlegethonTunnelCore), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Item.eyeOfEnder), new ItemStack(Item.diamond), new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemDimensionalMonopole)), 0, new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Item.netherrackBrick)), 0, new ItemStack(Femtocraft.blockPhlegethonTunnelFrame), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Block.pistonBase), new ItemStack(Block.blockIron), new ItemStack(Block.pistonBase), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockSisyphusStabilizer), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Item.netherStar), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemAtlasMount), new ItemStack(Femtocraft.itemMinervaComplex)), 0, new ItemStack(Femtocraft.itemQuantumSchematic, 8), EnumTechLevel.FEMTO, FemtocraftTechnologies.SPIN_RETENTION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoHorologe), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemInfallibleEstimator)), 0, new ItemStack(Femtocraft.blockFemtoChronoshifter), EnumTechLevel.FEMTO, FemtocraftTechnologies.TEMPORAL_THREADING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoEnmesher), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemOrpheusProcessor)), 0, new ItemStack(Femtocraft.blockFemtoEntangler), EnumTechLevel.FEMTO, FemtocraftTechnologies.DIMENSIONAL_SUPERPOSITIONS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.blockFemtoCable), null, null, null, null, null, null), 0, new ItemStack(Femtocraft.blockNullEqualizer), EnumTechLevel.FEMTO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemAtlasMount), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockFemtoCubePort), EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Item.netherrackBrick)), 0, new ItemStack(Femtocraft.blockFemtoCubeFrame), EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemCerberusGate), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockFemtoCubeChassis), EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.diamond), new ItemStack(Item.netherrackBrick), new ItemStack(Item.diamond), new ItemStack(Item.diamond), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Item.diamond), new ItemStack(Item.diamond), new ItemStack(Item.netherrackBrick), new ItemStack(Item.diamond)), 0, new ItemStack(Femtocraft.itemStellaratorPlating), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.netherStar), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer)), 0, new ItemStack(Femtocraft.blockStellaratorCore), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Block.glass), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Block.glass), new ItemStack(Item.diamond), new ItemStack(Block.glass), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Block.glass), new ItemStack(Femtocraft.itemStellaratorPlating)), 0, new ItemStack(Femtocraft.blockStellaratorFocus), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Block.netherBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.netherrackBrick)), 0, new ItemStack(Femtocraft.blockStellaratorHousing), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.netherrackBrick)), 0, new ItemStack(Femtocraft.blockStellaratorOpticalMaser), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.blazeRod), new ItemStack(Item.blazeRod), new ItemStack(Item.blazeRod), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating)), 0, new ItemStack(Femtocraft.blockPlasmaConduit, 8), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Item.blazeRod), new ItemStack(Block.dispenser), new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Item.netherrackBrick)), 0, new ItemStack(Femtocraft.blockPlasmaVent), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockPlasmaConduit)), 0, new ItemStack(Femtocraft.blockPlasmaTurbine), EnumTechLevel.FEMTO, FemtocraftTechnologies.ENERGY_CONVERSION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Block.blockDiamond), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating)), 0, new ItemStack(Femtocraft.blockPlasmaCondenser), EnumTechLevel.FEMTO, FemtocraftTechnologies.MATTER_CONVERSION))
  }

  private def testRecipes() {
    var test: AssemblerRecipe = getRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), null, null, null))
    Femtocraft.log(Level.WARNING, "Recipe " + (if (test != null) "found" else "not found") + ".")
    if (test != null) {
      Femtocraft.log(Level.WARNING, "Output " + (if (test.output.isItemEqual(new ItemStack(Femtocraft.itemFlorite))) "matches" else "does not match") + ".")
    }
    test = getRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), null, null, null))
    Femtocraft.log(Level.WARNING, "Recipe " + (if (test != null) "found" else "not found") + ".")
    test = getRecipe(new ItemStack(Femtocraft.itemFlorite))
    Femtocraft.log(Level.WARNING, "Recipe " + (if (test != null) "found" else "not found") + ".")
  }

  def getRecipe(input: Array[ItemStack]): AssemblerRecipe = {
    val normal = normalizedInput(input)
    if (normal == null) {
      return null
    }
    ard.getRecipe(input)
  }

  def getRecipe(output: ItemStack): AssemblerRecipe = ard.getRecipe(output)

  private def normalizedInput(input: Array[ItemStack]): Array[ItemStack] = {
    if (input.length != 9) {
      return null
    }
    val ret: Array[ItemStack] = new Array[ItemStack](9)
    for (i <- 0 until 9) {
      ret(i) = normalizedItem(input(i))
    }
    ret
  }

  private def normalizedItem(original: ItemStack) = if (original == null) null else new ItemStack(original.itemID, 1, original.getItemDamage)

  @throws(classOf[IllegalArgumentException])
  def addReversableRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    val normalArray = normalizedInput(recipe)
    if (normalArray == null) {
      return false
    }
    val normal = normalizedOutput(recipe)
    if (!checkDecomposition(normal, recipe) || !checkRecomposition(normalArray, recipe)) {
      Femtocraft.log(Level.WARNING, "Assembler recipe already exists for " + recipe.output.getUnlocalizedName + ".")
      return false
    }
    registerRecomposition(normalArray, recipe) && registerDecomposition(normal, recipe)
  }

  @throws(classOf[IllegalArgumentException])
  def addRecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    val normal = normalizedInput(recipe)
    if (normal == null) {
      return false
    }
    if (!checkRecomposition(normal, recipe)) {
      Femtocraft.log(Level.WARNING, "Assembler recipe already exists for " + recipe.output.getUnlocalizedName + ".")
      return false
    }
    registerRecomposition(normal, recipe)
  }

  @throws(classOf[IllegalArgumentException])
  def addDecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    val normal = normalizedOutput(recipe)
    if (!checkDecomposition(normal, recipe)) {
      Femtocraft.log(Level.WARNING, "Assembler recipe already exists for " + recipe.output.getUnlocalizedName + ".")
      return false
    }
    registerDecomposition(normal, recipe)
  }

  private def registerRecomposition(normal: Array[ItemStack], recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.AssemblerRecompositionRegisterEvent(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      ard.insertRecipe(recipe)
      addRecipeToTechLevelMap(recipe)
      addRecipeToTechnologyMap(recipe)
      return true
    }
    false
  }

  private def registerDecomposition(normal: ItemStack, recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.AssemblerDecompositionRegisterEvent(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      ard.insertRecipe(recipe)
      addRecipeToTechLevelMap(recipe)
      addRecipeToTechnologyMap(recipe)
      return true
    }
    false
  }

  private def addRecipeToTechLevelMap(recipe: AssemblerRecipe) {
  }

  private def addRecipeToTechnologyMap(recipe: AssemblerRecipe) {
  }

  private def checkDecomposition(normal: ItemStack, recipe: AssemblerRecipe) = ard.getRecipe(normal) == null

  private def checkRecomposition(normal: Array[ItemStack], recipe: AssemblerRecipe) = ard.getRecipe(normal) == null

  def removeAnyRecipe(recipe: AssemblerRecipe) = removeDecompositionRecipe(recipe) || removeRecompositionRecipe(recipe)

  def removeDecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    false
  }

  def removeRecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    false
  }

  private def normalizedOutput(recipe: AssemblerRecipe): ItemStack = normalizedItem(recipe.output)


  private def normalizedInput(recipe: AssemblerRecipe): Array[ItemStack] = normalizedInput(recipe.input)


  def removeReversableRecipe(recipe: AssemblerRecipe) = removeDecompositionRecipe(recipe) && removeRecompositionRecipe(recipe)


  def canCraft(input: Array[ItemStack]): Boolean = {
    if (input.length != 9) {
      return false
    }
    val recipe: AssemblerRecipe = getRecipe(input)
    if (recipe == null) {
      return false
    }
    for (i <- 0 until 9) {
      val rec = recipe.input(i)
      if (!(input(i) == null || rec == null)) {
        if (input(i).stackSize < input(i).stackSize) {
          return false
        }
        if (FemtocraftUtils.compareItem(rec, input(i)) != 0) {
          return false
        }
      }
    }
    true
  }

  def canCraft(input: ItemStack) = {
    val recipe = getRecipe(input)
    recipe != null && input.stackSize >= recipe.output.stackSize && FemtocraftUtils.compareItem(recipe.output, input) == 0
  }

  def getRecipesForTechLevel(level: EnumTechLevel) = ard.getRecipesForLevel(level)

  def getAllRecipes = ard.getAllRecipes

  def getRecipesForTechnology(tech: ResearchTechnology) = ard.getRecipesForTech(tech)

  def getRecipesForTechnology(techName: String) = ard.getRecipesForTech(techName)

  def hasResearchedRecipe(recipe: AssemblerRecipe, username: String) = Femtocraft.researchManager.hasPlayerResearchedTechnology(username, recipe.tech)
}
