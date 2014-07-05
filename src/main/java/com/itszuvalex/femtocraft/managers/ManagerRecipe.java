/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package com.itszuvalex.femtocraft.managers;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.assembler.ManagerAssemblerRecipe;
import com.itszuvalex.femtocraft.managers.dimensional.ManagerDimensionalRecipe;
import com.itszuvalex.femtocraft.managers.temporal.ManagerTemporalRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ManagerRecipe {
    public static ManagerAssemblerRecipe assemblyRecipes = new ManagerAssemblerRecipe();
    public static ManagerTemporalRecipe temporalRecipes = new
            ManagerTemporalRecipe();
    public static ManagerDimensionalRecipe dimensionalRecipes = new
            ManagerDimensionalRecipe();

    public ManagerRecipe() {
        registerRecipes();
    }

    private void registerRecipes() {
        GameRegistry.addSmelting(Femtocraft.blockOreTitanium.blockID, new ItemStack(
                Femtocraft.itemIngotTitanium), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOrePlatinum.blockID, new ItemStack(
                Femtocraft.itemIngotPlatinum), 0.1f);
        GameRegistry.addSmelting(Femtocraft.blockOreThorium.blockID, new ItemStack(
                Femtocraft.itemIngotThorium), 0.1f);
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
        GameRegistry.addSmelting(Femtocraft.itemIngotTitanium.itemID,
                new ItemStack(Femtocraft.itemIngotTemperedTitanium), 0.1f);

        GameRegistry.addSmelting(Femtocraft.itemPrimedBoard.itemID, new ItemStack(
                Femtocraft.itemDopedBoard), 0.1f);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemPrimedBoard),
                "#", "$", '#', Femtocraft.itemConductivePowder, '$',
                Femtocraft.itemBoard);
        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemPaperSchematic, 3), "###", "###",
                "###", '#', Item.paper);
        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemBoard), "###",
                '#', Item.stick);
        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemMicrochip), "#", "$", '#',
                Femtocraft.itemSpoolGold, '$', Femtocraft.itemDopedBoard);

        CraftingManager
                .getInstance()
                .getRecipeList()
                .add(new ShapedOreRecipe(new ItemStack(Femtocraft.itemSpool),
                        "# #", "#-#", "# #", '#', "plankWood", '-', "stickWood"));

        GameRegistry
                .addShapedRecipe(new ItemStack(Femtocraft.itemSpoolGold, 8), "###",
                        "#-#", "###", '#', Item.ingotGold, '-',
                        Femtocraft.itemSpool);

        GameRegistry
                .addShapedRecipe(new ItemStack(Femtocraft.itemSpoolPlatinum, 8), "###",
                        "#-#", "###", '#', Femtocraft.itemIngotPlatinum, '-',
                        Femtocraft.itemSpool);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroLogicCore), "TGT", "GBG", "TTT", 'T', Femtocraft.itemIngotTemperedTitanium, 'G', Femtocraft.itemSpoolGold, 'B', Femtocraft.itemMicrochip);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemHeatingElement), " P ", "G G", "TBT", 'P', Femtocraft.itemSpoolPlatinum, 'G', Femtocraft.itemSpoolGold, 'T', Femtocraft.itemIngotTemperedTitanium, 'B', Femtocraft.itemMicrochip);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.blockMicroFurnaceUnlit), "TMT", "MHM", "TLT", 'T', Femtocraft.itemIngotTemperedTitanium, 'M', Femtocraft.itemMicroPlating, 'H', Femtocraft.itemHeatingElement, 'L', Femtocraft.itemMicroLogicCore);

        GameRegistry.addShapelessRecipe(new ItemStack(
                Femtocraft.itemConductivePowder, 2), new ItemStack(
                Femtocraft.itemIngotFarenite), new ItemStack(Item.dyePowder, 1, 4));

        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemMicroPlating), "TCT", "CPC",
                "TCT", 'T', Femtocraft.itemIngotTemperedTitanium, 'C',
                Femtocraft.itemMicrochip, 'P', Femtocraft.itemConductivePowder);

    }
}
