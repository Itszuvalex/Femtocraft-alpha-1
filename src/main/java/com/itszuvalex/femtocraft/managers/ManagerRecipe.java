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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
        temporalRecipes.init();
        dimensionalRecipes.init();
        registerRecipes();
        FissionReactorRegistry.init();
        CryogenRegistry.init();
        assemblyRecipes.init();
    }

    private void registerRecipes() {
        GameRegistry.addSmelting(Femtocraft.blockOreTitanium(), new ItemStack(
                Femtocraft.itemIngotTitanium()), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOrePlatinum(), new ItemStack(
                Femtocraft.itemIngotPlatinum()), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOreThorium(), new ItemStack(
                Femtocraft.itemIngotThorium()), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOreLodestone(), new ItemStack(Femtocraft.itemChunkLodestone()),
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
        GameRegistry.addSmelting(Femtocraft.itemIngotTitanium(),
                new ItemStack(Femtocraft.itemIngotTemperedTitanium()), 0.1f);

        GameRegistry.addSmelting(Femtocraft.itemPrimedBoard(), new ItemStack(
                Femtocraft.itemDopedBoard()), 0.1f);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemChunkLodestone()), "NN", "NN", 'N',
                Femtocraft.itemNuggetLodestone());
        GameRegistry.addShapelessRecipe(new ItemStack(Femtocraft.itemNuggetLodestone(), 4),
                new ItemStack(Femtocraft.itemChunkLodestone()));

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockResearchConsole()), "THT", "TCT", "RFR", 'T',
                Femtocraft.itemIngotTitanium(), 'H', Blocks.hopper, 'C', Items.comparator, 'R', Items.redstone, 'F',
                Femtocraft.itemIngotFarenite());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockResearchComputer()), "GGG", "TET", "RFR", 'G',
                Blocks.glass, 'T', Femtocraft.itemIngotTitanium(), 'E', Items.repeater, 'R', Items.redstone, 'F',
                Femtocraft.itemIngotFarenite());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemPrimedBoard()),
                "#", "$", '#', Femtocraft.itemConductivePowder(), '$',
                Femtocraft.itemBoard());
        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemPaperSchematic(), 3), "###", "###",
                "###", '#', Items.paper);
        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemBoard()), "###",
                '#', Items.stick);
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
                        "#-#", "###", '#', Items.gold_ingot, '-',
                        Femtocraft.itemSpool());

        GameRegistry
                .addShapedRecipe(new ItemStack(Femtocraft.itemSpoolPlatinum(), 8), "###",
                        "#-#", "###", '#', Femtocraft.itemIngotPlatinum(), '-',
                        Femtocraft.itemSpool());


        GameRegistry.addShapelessRecipe(new ItemStack(
                Femtocraft.itemConductivePowder(), 2), new ItemStack(
                Femtocraft.itemIngotFarenite()), new ItemStack(Items.dye, 1, 4));


        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroCoil(), 6),
                "MMM", "GPG", "MMM", 'M', Femtocraft.itemConductivePowder(), 'G',
                Femtocraft.itemSpoolGold(), 'P', Femtocraft.itemSpoolPlatinum());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemBattery()),
                "FCF", 'F', Items.iron_ingot, 'C',
                Femtocraft.itemConductivePowder());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroLogicCore()), "TGT", "GBG", "TTT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'G', Femtocraft.itemSpoolGold(), 'B',
                Femtocraft.itemMicrochip());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemKineticPulverizer()), "FTF", " P ", "FMF", 'F',
                Items.iron_ingot,
                'M', Femtocraft.itemMicrochip(), 'P', Blocks.piston
                , 'T', Femtocraft.itemIngotTemperedTitanium());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemArticulatingArm()), "MTM", "F T", "F S", 'M',
                Femtocraft.itemMicrochip(), 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'F', Items.iron_ingot,
                'S', Items.shears);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemHeatingElement()), " P ", "G G", "TBT", 'P',
                Femtocraft.itemSpoolPlatinum(), 'G', Femtocraft.itemSpoolGold(), 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'B', Femtocraft.itemMicrochip());

        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemMicroPlating()), "TCT", "CSC",
                "TCT", 'T', Femtocraft.itemIngotTemperedTitanium(), 'C',
                Femtocraft.itemMicrochip(), 'S', Items.slime_ball);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroCable(), 16), "MSM", "RCR", "MSM", 'M',
                Femtocraft.itemMicroCoil(),
                'S', Items.string, 'R', Items.redstone,
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
                'R', Items.comparator);

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

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMagneticInductionGenerator()), "MHT", "MHC",
                "MHT", 'M',
                Femtocraft.itemMicroPlating(), 'H',
                Femtocraft.itemHeatingElement(), 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'C',
                Femtocraft.itemMicroCoil());

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemVacuumCore())
                , " P ", "FEF", " P ", 'P', Femtocraft.itemIngotPlatinum(), 'F',
                Femtocraft.itemIngotFarenite(), 'E', Items.ender_pearl);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockSuctionPipe(), 16), "TFT", " V ", "TFT", 'T',
                Femtocraft.itemIngotTemperedTitanium(), 'F', Items.iron_ingot,
                'V', Femtocraft.itemVacuumCore());

    }
}
