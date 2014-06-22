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

package femtocraft.managers;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.Femtocraft;
import femtocraft.managers.assembler.ManagerAssemblerRecipe;
import femtocraft.managers.dimensional.ManagerDimensionalRecipe;
import femtocraft.managers.temporal.ManagerTemporalRecipe;
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
        GameRegistry.addSmelting(Femtocraft.oreTitanium.blockID, new ItemStack(
                Femtocraft.ingotTitanium), 0.1f);
        GameRegistry.addSmelting(Femtocraft.orePlatinum.blockID, new ItemStack(
                Femtocraft.ingotPlatinum), 0.1f);
        GameRegistry.addSmelting(Femtocraft.oreThorium.blockID, new ItemStack(
                Femtocraft.ingotThorium), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedIron.itemID,
//                new ItemStack(Item.ingotIron), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedGold.itemID,
//                new ItemStack(Item.ingotGold), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedTitanium.itemID,
//                new ItemStack(Femtocraft.ingotTitanium), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedThorium.itemID,
//                new ItemStack(Femtocraft.ingotThorium), 0.1f);
//        GameRegistry.addSmelting(Femtocraft.deconstructedPlatinum.itemID,
//                new ItemStack(Femtocraft.ingotPlatinum), 0.1f);
        GameRegistry.addSmelting(Femtocraft.ingotTitanium.itemID,
                new ItemStack(Femtocraft.ingotTemperedTitanium), 0.1f);

        GameRegistry.addSmelting(Femtocraft.primedBoard.itemID, new ItemStack(
                Femtocraft.dopedBoard), 0.1f);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.primedBoard),
                "#", "$", '#', Femtocraft.conductivePowder, '$',
                Femtocraft.board);
        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.paperSchematic, 3), "###", "###",
                "###", '#', Item.paper);
        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.board), "###",
                '#', Item.stick);
        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.microchip), "#", "$", '#',
                Femtocraft.spoolGold, '$', Femtocraft.dopedBoard);

        CraftingManager
                .getInstance()
                .getRecipeList()
                .add(new ShapedOreRecipe(new ItemStack(Femtocraft.spool),
                        "# #", "#-#", "# #", '#', "plankWood", '-', "stickWood"));

        GameRegistry
                .addShapedRecipe(new ItemStack(Femtocraft.spoolGold, 8), "###",
                        "#-#", "###", '#', Item.ingotGold, '-',
                        Femtocraft.spool);

        GameRegistry
                .addShapedRecipe(new ItemStack(Femtocraft.spoolPlatinum, 8), "###",
                        "#-#", "###", '#', Femtocraft.ingotPlatinum, '-',
                        Femtocraft.spool);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemMicroLogicCore), "TGT", "GBG", "TTT", 'T', Femtocraft.ingotTemperedTitanium, 'G', Femtocraft.spoolGold, 'B', Femtocraft.microchip);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.itemHeatingCoil), " P ", "G G", "TBT", 'P', Femtocraft.spoolPlatinum, 'G', Femtocraft.spoolGold, 'T', Femtocraft.ingotTemperedTitanium, 'B', Femtocraft.microchip);

        GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.microFurnaceUnlit), "TMT", "MHM", "TLT", 'T', Femtocraft.ingotTemperedTitanium, 'M', Femtocraft.itemMicroPlating, 'H', Femtocraft.itemHeatingCoil, 'L', Femtocraft.itemMicroLogicCore);

        GameRegistry.addShapelessRecipe(new ItemStack(
                Femtocraft.conductivePowder, 2), new ItemStack(
                Femtocraft.ingotFarenite), new ItemStack(Item.dyePowder, 1, 4));

        GameRegistry.addShapedRecipe(
                new ItemStack(Femtocraft.itemMicroPlating), "TCT", "CPC",
                "TCT", 'T', Femtocraft.ingotTemperedTitanium, 'C',
                Femtocraft.microchip, 'P', Femtocraft.conductivePowder);

    }
}
