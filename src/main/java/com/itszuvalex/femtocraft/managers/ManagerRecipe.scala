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
package com.itszuvalex.femtocraft.managers

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.managers.assembler.ManagerAssemblerRecipe
import com.itszuvalex.femtocraft.managers.dimensional.ManagerDimensionalRecipe
import com.itszuvalex.femtocraft.managers.temporal.ManagerTemporalRecipe
import com.itszuvalex.femtocraft.power.{CryogenRegistry, FissionReactorRegistry}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.{CraftingManager, IRecipe}
import net.minecraftforge.oredict.ShapedOreRecipe

import com.itszuvalex.femtocraft.managers.RecipeHelper._

class ManagerRecipe {
  var assemblyRecipes    = new ManagerAssemblerRecipe
  var temporalRecipes    = new ManagerTemporalRecipe
  var dimensionalRecipes = new ManagerDimensionalRecipe

  def init() {
    temporalRecipes.init()
    dimensionalRecipes.init()
    registerRecipes()
    FissionReactorRegistry.init()
    CryogenRegistry.init()
    assemblyRecipes.init()
  }

  private def registerRecipes() {
    GameRegistry.addSmelting(Femtocraft.blockOreTitanium, new ItemStack(Femtocraft.itemIngotTitanium), 0.1f)
    GameRegistry.addSmelting(Femtocraft.blockOrePlatinum, new ItemStack(Femtocraft.itemIngotPlatinum), 0.1f)
    GameRegistry.addSmelting(Femtocraft.blockOreThorium, new ItemStack(Femtocraft.itemIngotThorium), 0.1f)
    GameRegistry.addSmelting(Femtocraft.blockOreLodestone, new ItemStack(Femtocraft.itemChunkLodestone), 0.1f)
    GameRegistry.addSmelting(Femtocraft.itemIngotTitanium, new ItemStack(Femtocraft.itemIngotTemperedTitanium), 0.1f)
    GameRegistry.addSmelting(Femtocraft.itemPrimedBoard, new ItemStack(Femtocraft.itemDopedBoard), 0.1f)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemChunkLodestone), Array("NN", "NN", 'N', Femtocraft.itemNuggetLodestone): _*)
    GameRegistry.addShapelessRecipe(new ItemStack(Femtocraft.itemNuggetLodestone, 4), Array(new ItemStack(Femtocraft.itemChunkLodestone)): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockResearchConsole), Array("THT", "TCT", "RFR", 'T', Femtocraft.itemIngotTitanium, 'H', Blocks.hopper, 'C', Items.comparator, 'R', Items.redstone, 'F', Femtocraft.itemIngotFarenite): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockResearchComputer), Array("GGG", "TET", "RFR", 'G', Blocks.glass, 'T', Femtocraft.itemIngotTitanium, 'E', Items.repeater, 'R', Items.redstone, 'F', Femtocraft.itemIngotFarenite): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemPrimedBoard), Array("#", "$", '#', Femtocraft.itemConductivePowder, '$', Femtocraft.itemBoard): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemPaperSchematic, 3), Array("###", "###", "###", '#', Items.paper): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemBoard), Array("###", '#', Items.stick): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicrochip), Array("#", "$", '#', Femtocraft.itemSpoolGold, '$', Femtocraft.itemDopedBoard): _*)
    CraftingManager.getInstance.getRecipeList.asInstanceOf[util.List[IRecipe]].add(new ShapedOreRecipe(new ItemStack(Femtocraft.itemSpool), Array[Any]("# #", "#-#", "# #", '#', "plankWood", '-', "stickWood").box:_*))
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemSpoolGold, 8), Array("###", "#-#", "###", '#', Items.gold_ingot, '-', Femtocraft.itemSpool): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemSpoolPlatinum, 8), Array("###", "#-#", "###", '#', Femtocraft.itemIngotPlatinum, '-', Femtocraft.itemSpool): _*)
    GameRegistry.addShapelessRecipe(new ItemStack(Femtocraft.itemConductivePowder, 2), Array(new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.dye, 1, 4)): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroCoil, 6), Array("MMM", "GPG", "MMM", 'M', Femtocraft.itemConductivePowder, 'G', Femtocraft.itemSpoolGold, 'P', Femtocraft.itemSpoolPlatinum): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemBattery), Array(" P ", "FCF", "FCF", 'P', Femtocraft.itemSpoolPlatinum, 'F', Items.iron_ingot, 'C', Femtocraft.itemConductivePowder): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroLogicCore), Array("TGT", "GBG", "TTT", 'T', Femtocraft.itemIngotTemperedTitanium, 'G', Femtocraft.itemSpoolGold, 'B', Femtocraft.itemMicrochip): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemInterfaceDeviceMicro), Array("  C", "PC ", "MP ", 'C', Femtocraft.itemMicroCoil, 'P', Femtocraft.itemMicroPlating, 'M', Femtocraft.itemMicrochip): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemKineticPulverizer), Array("FTF", " P ", "FMF", 'F', Items.iron_ingot, 'M', Femtocraft.itemMicrochip, 'P', Blocks.piston, 'T', Femtocraft.itemIngotTemperedTitanium): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemArticulatingArm), Array("MTM", "F T", "F S", 'M', Femtocraft.itemMicrochip, 'T', Femtocraft.itemIngotTemperedTitanium, 'F', Items.iron_ingot, 'S', Items.shears): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemHeatingElement), Array(" P ", "G G", "TBT", 'P', Femtocraft.itemSpoolPlatinum, 'G', Femtocraft.itemSpoolGold, 'T', Femtocraft.itemIngotTemperedTitanium, 'B', Femtocraft.itemMicrochip): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroPlating), Array("TCT", "CSC", "TCT", 'T', Femtocraft.itemIngotTemperedTitanium, 'C', Femtocraft.itemMicrochip, 'S', Items.slime_ball): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroCable, 16), Array("MSM", "RCR", "MSM", 'M', Femtocraft.itemMicroCoil, 'S', Items.string, 'R', Items.redstone, 'C', Femtocraft.itemConductivePowder): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroCube), Array("BTB", "TBT", "BTB", 'B', Femtocraft.itemBattery, 'T', Femtocraft.itemIngotTemperedTitanium): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemDissassemblyArray), Array("MKM", "K K", "TTT", 'M', Femtocraft.itemMicrochip, 'K', Femtocraft.itemKineticPulverizer, 'T', Femtocraft.itemIngotTemperedTitanium): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemAssemblyArray), Array("A A", "MTM", 'A', Femtocraft.itemArticulatingArm, 'M', Femtocraft.itemMicrochip, 'T', Femtocraft.itemIngotTemperedTitanium): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroChargingCoil), Array("CMC", "CIC", "CMC", 'C', Femtocraft.itemConductivePowder, 'M', Femtocraft.blockMicroCable, 'I', Femtocraft.itemMicroCoil): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroChargingBase), Array("TMT", "MUM", "TCT", 'T', Femtocraft.itemIngotTemperedTitanium, 'U', Femtocraft.blockMicroCube, 'M', Femtocraft.blockMicroCable, 'C', Femtocraft.itemMicroLogicCore): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockEncoder), Array("TMT", "MRM", "TMT", 'T', Femtocraft.itemIngotTemperedTitanium, 'M', Femtocraft.itemMicroPlating, 'R', Items.comparator): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroDeconstructor), Array("TMT", "MDM", "TCT", 'T', Femtocraft.itemIngotTemperedTitanium, 'M', Femtocraft.itemMicroPlating, 'D', Femtocraft.itemDissassemblyArray, 'C', Femtocraft.itemMicroLogicCore): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroReconstructor), Array("TMT", "MAM", "TCT", 'T', Femtocraft.itemIngotTemperedTitanium, 'M', Femtocraft.itemMicroPlating, 'A', Femtocraft.itemAssemblyArray, 'C', Femtocraft.itemMicroLogicCore): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroFurnaceUnlit), Array("TMT", "MHM", "TLT", 'T', Femtocraft.itemIngotTemperedTitanium, 'M', Femtocraft.itemMicroPlating, 'H', Femtocraft.itemHeatingElement, 'L', Femtocraft.itemMicroLogicCore): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMagneticInductionGenerator), Array("MHT", "MHC", "MHT", 'M', Femtocraft.itemMicroPlating, 'H', Femtocraft.itemHeatingElement, 'T', Femtocraft.itemIngotTemperedTitanium, 'C', Femtocraft.itemMicroCoil): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemVacuumCore), Array("LPL", "FEF", "LPL", 'P', Femtocraft.itemIngotPlatinum, 'F', Femtocraft.itemIngotFarenite, 'E', Items.ender_pearl, 'L', Femtocraft.itemNuggetLodestone): _*)
    GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockSuctionPipe, 16), Array("TFT", " V ", "TFT", 'T', Femtocraft.itemIngotTemperedTitanium, 'F', Items.iron_ingot, 'V', Femtocraft.itemVacuumCore): _*)
  }
}
