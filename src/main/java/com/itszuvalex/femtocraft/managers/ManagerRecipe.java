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

package com.itszuvalex.femtocraft.managers;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.assembler.ManagerAssemblerRecipe;
import com.itszuvalex.femtocraft.managers.dimensional.ManagerDimensionalRecipe;
import com.itszuvalex.femtocraft.managers.temporal.ManagerTemporalRecipe;
import com.itszuvalex.femtocraft.power.CryogenRegistry;
import com.itszuvalex.femtocraft.power.FissionReactorRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ManagerRecipe {
    public ManagerAssemblerRecipe assemblyRecipes = new ManagerAssemblerRecipe();
    public ManagerTemporalRecipe temporalRecipes = new
            ManagerTemporalRecipe();
    public ManagerDimensionalRecipe dimensionalRecipes = new
            ManagerDimensionalRecipe();

    public ManagerRecipe() {
    }

    public void init() {
        assemblyRecipes.init();
        temporalRecipes.init();
        dimensionalRecipes.init();
        registerRecipes();
        FissionReactorRegistry.init();
        CryogenRegistry.init();
    }

    private void registerRecipes() {
        GameRegistry.addSmelting(Femtocraft.blockOreTitanium().blockID, new ItemStack(
                Femtocraft.itemIngotTitanium()), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOrePlatinum().blockID, new ItemStack(
                Femtocraft.itemIngotPlatinum()), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOreThorium().blockID, new ItemStack(
                Femtocraft.itemIngotThorium()), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOreLodestone().blockID, new ItemStack(Femtocraft.itemChunkLodestone()),
                0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedIron.itemID,
//                new ItemStack(Item.ingotIron), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedGold.itemID,
//                new ItemStack(Item.ingotGold), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedTitanium.itemID,
//                new ItemStack(Femtocraft.itemIngotTitanium), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedThorium.itemID,
//                new ItemStack(Femtocraft.itemIngotThorium), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedPlatinum.itemID,
//                new ItemStack(Femtocraft.itemIngotPlatinum), 0.1f);
        GameRegistry.addSmelting(Femtocraft.itemIngotTitanium().itemID,
                new ItemStack(Femtocraft.itemIngotTemperedTitanium()), 0.1f);

        GameRegistry.addSmelting(Femtocraft.itemPrimedBoard().itemID, new ItemStack(
                Femtocraft.itemDopedBoard()), 0.1f);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemChunkLodestone()), "NN", "NN", 'N',
                Femtocraft.itemNuggetLodestone());
        GameRegistry.addShapelessRecipe(new ItemStack(Femtocraft.itemNuggetLodestone(), 4),
                new ItemStack(Femtocraft.itemChunkLodestone()));

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockResearchConsole()), "THT", "TCT", "RFR", 'T',
                Femtocraft.itemIngotTitanium(), 'H', Block.hopperBlock, 'C', Item.comparator, 'R', Item.redstone, 'F',
                Femtocraft.itemIngotFarenite());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockResearchComputer()), "GGG", "TET", "RFR", 'G',
                Block.glass, 'T', Femtocraft.itemIngotTitanium(), 'E', Item.redstoneRepeater, 'R', Item.redstone, 'F',
                Femtocraft.itemIngotFarenite());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemPrimedBoard()),
                "#", "$", '#', Femtocraft.itemConductivePowder(), '$',
                Femtocraft.itemBoard());
        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemPaperSchematic(), 3), "###", "###",
                "###", '#', Item.paper);
        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemBoard()), "###",
                '#', Item.stick);
        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemMicrochip()), "#", "$", '#',
                Femtocraft.itemSpoolGold(), '$', Femtocraft.itemDopedBoard());


        CraftingManager
                .getInstance()
                .getRecipeList()
                .add(new ShapedOreRecipe(new ItemStack(Femtocraft.itemSpool()),
                        "# #", "#-#", "# #", '#', "plankWood", '-', "stickWood"));

        GameRegistry
                .addShapedRecipe(new ItemStack(Femtocraft.itemSpoolGold(), 8), "###",
                        "#-#", "###", '#', Item.ingotGold, '-',
                        Femtocraft.itemSpool());

        GameRegistry
                .addShapedRecipe(new ItemStack(Femtocraft.itemSpoolPlatinum(), 8), "###",
                        "#-#", "###", '#', Femtocraft.itemIngotPlatinum(), '-',
                        Femtocraft.itemSpool());


        GameRegistry.addShapelessRecipe(new ItemStack(
                Femtocraft.itemConductivePowder(), 2), new ItemStack(
                Femtocraft.itemIngotFarenite()), new ItemStack(Item.dyePowder, 1, 4));


        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroCoil(), 6),
                "MMM", "GPG", "MMM", 'M', Femtocraft.itemConductivePowder(), 'G',
                Femtocraft.itemSpoolGold(), 'P', Femtocraft.itemSpoolPlatinum());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemBattery()),
                "FCF", 'F', Item.ingotIron, 'C',
                Femtocraft.itemConductivePowder());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroLogicCore()), "TGT", "GBG", "TTT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'G', Femtocraft.itemSpoolGold(), 'B', Femtocraft.itemMicrochip());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemKineticPulverizer()), "FTF", " P ", "FMF", 'F', Item.ingotIron,
                'M', Femtocraft.itemMicrochip(), 'P', Block.pistonBase
                , 'T', Femtocraft.itemIngotTemperedTitanium());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemArticulatingArm()), "MTM", "F T", "F S", 'M',
                Femtocraft.itemMicrochip(), 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'F', Item.ingotIron,
                'S', Item.shears);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemHeatingElement()), " P ", "G G", "TBT", 'P',
                Femtocraft.itemSpoolPlatinum(), 'G', Femtocraft.itemSpoolGold(), 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'B', Femtocraft.itemMicrochip());

        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemMicroPlating()), "TCT", "CSC",
                "TCT", 'T', Femtocraft.itemIngotTemperedTitanium(), 'C',
                Femtocraft.itemMicrochip(), 'S', Item.slimeBall);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroCable(), 16), "MSM", "RCR", "MSM", 'M',
                Femtocraft.itemMicroCoil(),
                'S', Item.silk, 'R', Item.redstone,
                'C', Femtocraft.itemConductivePowder());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroCube())
                , "BTB", "TBT", "BTB", 'B', Femtocraft.itemBattery(), 'T',
                Femtocraft.itemIngotTemperedTitanium());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemDissassemblyArray()), "MKM", "K K", "TTT", 'M',
                Femtocraft.itemMicrochip(), 'K',
                Femtocraft.itemKineticPulverizer(), 'T',
                Femtocraft.itemIngotTemperedTitanium());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemAssemblyArray()), "A A", "MTM", 'A',
                Femtocraft.itemArticulatingArm(), 'M', Femtocraft.itemMicrochip(),
                'T',
                Femtocraft.itemIngotTemperedTitanium());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroChargingCoil()), "CMC", "CIC", "CMC", 'C',
                Femtocraft.itemConductivePowder(), 'M', Femtocraft.blockMicroCable(),
                'I', Femtocraft.itemMicroCoil());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroChargingBase()), "TMT", "MUM", "TCT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'U',
                Femtocraft.blockMicroCube(),
                'M', Femtocraft.blockMicroCable(), 'C', Femtocraft.itemMicroLogicCore());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockEncoder()),
                "TMT", "MRM", "TMT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'M', Femtocraft.itemMicroPlating(),
                'R', Item.comparator);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroDeconstructor()), "TMT", "MDM", "TCT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'M', Femtocraft.itemMicroPlating(),
                'D', Femtocraft.itemDissassemblyArray(), 'C', Femtocraft.itemMicroLogicCore());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroReconstructor()), "TMT", "MAM", "TCT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'M',
                Femtocraft.itemMicroPlating(), 'A',
                Femtocraft.itemAssemblyArray(), 'C',
                Femtocraft.itemMicroLogicCore());


        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroFurnaceUnlit()), "TMT", "MHM", "TLT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'M', Femtocraft.itemMicroPlating(), 'H',
                Femtocraft.itemHeatingElement(), 'L', Femtocraft.itemMicroLogicCore());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMagneticInductionGenerator()), "MHT", "MHC", "MHT", 'M',
                Femtocraft.itemMicroPlating(), 'H',
                Femtocraft.itemHeatingElement(), 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'C',
                Femtocraft.itemMicroCoil());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemVacuumCore())
                , " P ", "FEF", " P ", 'P', Femtocraft.itemIngotPlatinum(), 'F',
                Femtocraft.itemIngotFarenite(), 'E', Item.enderPearl);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockSuctionPipe(), 16), "TFT", " V ", "TFT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'F', Item.ingotIron,
                'V', Femtocraft.itemVacuumCore());

    }
}
