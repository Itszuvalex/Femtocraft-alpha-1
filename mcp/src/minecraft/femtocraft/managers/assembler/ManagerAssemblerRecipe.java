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

package femtocraft.managers.assembler;

import femtocraft.Femtocraft;
import femtocraft.FemtocraftConfigs;
import femtocraft.managers.assembler.EventAssemblerRegister.AssemblerDecompositionRegisterEvent;
import femtocraft.managers.assembler.EventAssemblerRegister.AssemblerRecompositionRegisterEvent;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;

/**
 * @author chris
 * @category Manager
 * @info This manager is responsible for all Femtocraft AssemblerRecipes. All
 * Assembler/Dissassemblers look to this manager for recipe lookup.
 * Recipes can be specified to only be disassemble-able, or only
 * reassemble-able. Dissassemblers simply break down items, Reassembles
 * must use schematics to specify the recipe to follow. <br>
 * All recipes are ordered according to their signature in the inventory.
 * The entire 9 slots are used for the input signature. ItemStack
 * stackSize does not matter for ordering. Exceptions will be thrown when
 * attempting to addInput recipes when their signature is already associated
 * with a recipe (no check is performed to see if the recipes are actually
 * equal or not.) When reconstructing, items must conform to the input
 * signature, and all 9 slots are important. Slots that are null in the
 * recipe must not contain any items, and vice versa. This will be
 * separately enforced in the schematic-creating TileEntities, but it it
 * is also stated here for reference.
 */
public class ManagerAssemblerRecipe {
    private SortedMap<ItemStack[], AssemblerRecipe> inputToRecipeMap;
    private SortedMap<ItemStack, AssemblerRecipe> outputToRecipeMap;
    private HashMap<EnumTechLevel, ArrayList<AssemblerRecipe>> techLevelToRecipeMap;
    private HashMap<ResearchTechnology, ArrayList<AssemblerRecipe>> technologyToRecipeMap;

    public ManagerAssemblerRecipe() {
        inputToRecipeMap = new TreeMap<ItemStack[], AssemblerRecipe>(
                new ComparatorItemStackArray());
        outputToRecipeMap = new TreeMap<ItemStack, AssemblerRecipe>(
                new ComparatorItemStack());
        techLevelToRecipeMap = new HashMap<EnumTechLevel, ArrayList<AssemblerRecipe>>();
        technologyToRecipeMap = new HashMap<ResearchTechnology, ArrayList<AssemblerRecipe>>();

        registerRecipes();
    }

    private void registerRecipes() {
        registerFemtoDecompositionRecipes();
        registerNanoDecompositionRecipes();
        registerMicroDecompositionRecipes();
        registerMacroDecompositionRecipes();

        registerFemtocraftAssemblerRecipes();
    }

    private void registerFemtoDecompositionRecipes() {
        try {
            if (configRegisterRecipe("ItemCrystallite")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemRectangulon),
                        new ItemStack(Femtocraft.itemPlaneoid),
                        new ItemStack(Femtocraft.itemRectangulon), null, null,
                        null}, 3, new ItemStack(Femtocraft.itemCrystallite),
                        EnumTechLevel.FEMTO, null
                )); // ItemCrystallite
            }
            if (configRegisterRecipe("ItemMineralite")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null,
                                new ItemStack(Femtocraft.itemCubit),
                                new ItemStack(Femtocraft.itemPlaneoid),
                                new ItemStack(Femtocraft.itemCubit), null,
                                null, null}, 3, new ItemStack(
                        Femtocraft.itemMineralite),
                        EnumTechLevel.FEMTO, null
                )); // ItemMineralite
            }
            if (configRegisterRecipe("ItemMetallite")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null,
                                new ItemStack(Femtocraft.itemCubit),
                                new ItemStack(Femtocraft.itemRectangulon),
                                new ItemStack(Femtocraft.itemCubit), null,
                                null, null}, 3, new ItemStack(
                        Femtocraft.itemMetallite), EnumTechLevel.FEMTO,
                        null
                )); // ItemMetallite
            }
            if (configRegisterRecipe("ItemFaunite")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemRectangulon),
                        new ItemStack(Femtocraft.itemCubit),
                        new ItemStack(Femtocraft.itemRectangulon), null, null,
                        null}, 3, new ItemStack(Femtocraft.itemFaunite),
                        EnumTechLevel.FEMTO, null
                )); // ItemFaunite
            }
            if (configRegisterRecipe("ItemElectrite")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemPlaneoid),
                        new ItemStack(Femtocraft.itemCubit),
                        new ItemStack(Femtocraft.itemPlaneoid), null, null,
                        null}, 3, new ItemStack(Femtocraft.itemElectrite),
                        EnumTechLevel.FEMTO, null
                )); // ItemElectrite
            }
            if (configRegisterRecipe("ItemFlorite")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemPlaneoid),
                        new ItemStack(Femtocraft.itemRectangulon),
                        new ItemStack(Femtocraft.itemPlaneoid), null, null,
                        null}, 3, new ItemStack(Femtocraft.itemFlorite),
                        EnumTechLevel.FEMTO, null
                )); // ItemFlorite
            }
        } catch (AssemblerRecipeFoundException e) {
            Femtocraft.logger.log(Level.SEVERE, e.errMsg);
            Femtocraft.logger.log(Level.SEVERE,
                    "Femtocraft failed to load Femto-tier Assembler Recipes!");
        }
    }

    private void registerNanoDecompositionRecipes() {
        try {
            if (configRegisterRecipe("ItemMicroCrystal")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemCrystallite, 2),
                        new ItemStack(Femtocraft.itemElectrite, 2),
                        new ItemStack(Femtocraft.itemCrystallite, 2),
                        new ItemStack(Femtocraft.itemElectrite, 2),
                        new ItemStack(Femtocraft.itemCrystallite, 2),
                        new ItemStack(Femtocraft.itemElectrite, 2),
                        new ItemStack(Femtocraft.itemCrystallite, 2),
                        new ItemStack(Femtocraft.itemElectrite, 2),
                        new ItemStack(Femtocraft.itemCrystallite, 2)}, 2,
                        new ItemStack(Femtocraft.itemMicroCrystal),
                        EnumTechLevel.NANO, null
                )); // ItemMicroCrystal
            }

            if (configRegisterRecipe("ItemProteinChain")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null,
                                new ItemStack(Femtocraft.itemFaunite),
                                new ItemStack(Femtocraft.itemMineralite),
                                new ItemStack(Femtocraft.itemFaunite), null,
                                null, null}, 2, new ItemStack(
                        Femtocraft.itemProteinChain),
                        EnumTechLevel.NANO, null
                )); // ItemProteinChain
            }
            if (configRegisterRecipe("ItemNerveCluster")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null,
                                new ItemStack(Femtocraft.itemFaunite),
                                new ItemStack(Femtocraft.itemElectrite),
                                new ItemStack(Femtocraft.itemFaunite), null,
                                null, null}, 2, new ItemStack(
                        Femtocraft.itemNerveCluster),
                        EnumTechLevel.NANO, null
                )); // ItemNerveCluster
            }
            if (configRegisterRecipe("ItemConductiveAlloy")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemMetallite), null,
                        new ItemStack(Femtocraft.itemElectrite),
                        new ItemStack(Femtocraft.itemElectrite),
                        new ItemStack(Femtocraft.itemElectrite), null,
                        new ItemStack(Femtocraft.itemMetallite), null}, 2,
                        new ItemStack(Femtocraft.itemConductiveAlloy),
                        EnumTechLevel.NANO, null
                )); // ItemConductiveAlloy
            }
            if (configRegisterRecipe("ItemMetalComposite")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemMineralite), null,
                        new ItemStack(Femtocraft.itemMetallite),
                        new ItemStack(Femtocraft.itemMetallite),
                        new ItemStack(Femtocraft.itemMetallite), null,
                        new ItemStack(Femtocraft.itemMineralite), null}, 2,
                        new ItemStack(Femtocraft.itemMetalComposite),
                        EnumTechLevel.NANO, null
                )); // ItemMetalComposite
            }
            if (configRegisterRecipe("ItemFibrousStrand")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemFlorite),
                        null, new ItemStack(Femtocraft.itemMineralite), null,
                        null, null}, 2, new ItemStack(
                        Femtocraft.itemFibrousStrand), EnumTechLevel.NANO, null)); // ItemFibrousStrand
            }
            if (configRegisterRecipe("ItemMineralLattice")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemMineralite),
                        null, new ItemStack(Femtocraft.itemCrystallite), null,
                        null, null}, 2, new ItemStack(
                        Femtocraft.itemMineralLattice), EnumTechLevel.NANO,
                        null
                )); // ItemMineralLattice
            }
            if (configRegisterRecipe("ItemFungalSpores")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null,
                                new ItemStack(Femtocraft.itemFlorite),
                                new ItemStack(Femtocraft.itemCrystallite),
                                new ItemStack(Femtocraft.itemFlorite), null,
                                null, null}, 2, new ItemStack(
                        Femtocraft.itemFungalSpores),
                        EnumTechLevel.NANO, null
                )); // ItemFungalSpores
            }
            if (configRegisterRecipe("ItemIonicChunk")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemElectrite),
                        new ItemStack(Femtocraft.itemMineralite),
                        new ItemStack(Femtocraft.itemElectrite), null, null,
                        null}, 2, new ItemStack(Femtocraft.itemIonicChunk),
                        EnumTechLevel.NANO, null
                )); // ItemIonicChunk
            }
            if (configRegisterRecipe("ItemReplicatingMaterial")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null,
                                new ItemStack(Femtocraft.itemFlorite),
                                new ItemStack(Femtocraft.itemFaunite),
                                new ItemStack(Femtocraft.itemFlorite), null,
                                null, null}, 2, new ItemStack(
                        Femtocraft.itemReplicatingMaterial),
                        EnumTechLevel.NANO, null
                )); // ItemReplicatingMaterial
            }
            if (configRegisterRecipe("ItemSpinyFilament")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemCrystallite),
                        new ItemStack(Femtocraft.itemFaunite),
                        new ItemStack(Femtocraft.itemCrystallite), null, null,
                        null}, 2, new ItemStack(Femtocraft.itemSpinyFilament),
                        EnumTechLevel.NANO, null
                )); // ItemSpinyFilament
            }
            if (configRegisterRecipe("ItemHardenedBulb")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemCrystallite),
                        new ItemStack(Femtocraft.itemMetallite),
                        new ItemStack(Femtocraft.itemCrystallite), null, null,
                        null}, 2, new ItemStack(Femtocraft.itemHardenedBulb),
                        EnumTechLevel.NANO, null
                )); // ItemHardenedBulb
            }
            if (configRegisterRecipe("ItemMorphicChannel")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, new ItemStack(Femtocraft.itemElectrite),
                        new ItemStack(Femtocraft.itemFlorite),
                        new ItemStack(Femtocraft.itemElectrite), null, null,
                        null}, 2,
                        new ItemStack(Femtocraft.itemMorphicChannel),
                        EnumTechLevel.NANO, null
                )); // ItemMorphicChannel
            }
            if (configRegisterRecipe("ItemSynthesizedFiber")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null,
                                new ItemStack(Femtocraft.itemFlorite),
                                new ItemStack(Femtocraft.itemMetallite),
                                new ItemStack(Femtocraft.itemFlorite), null,
                                null, null}, 2, new ItemStack(
                        Femtocraft.itemSynthesizedFiber),
                        EnumTechLevel.NANO, null
                )); // ItemSynthesizedFiber
            }
            if (configRegisterRecipe("ItemOrganometallicPlate")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemMetallite), null,
                        new ItemStack(Femtocraft.itemFaunite),
                        new ItemStack(Femtocraft.itemFaunite),
                        new ItemStack(Femtocraft.itemFaunite), null,
                        new ItemStack(Femtocraft.itemMetallite), null}, 2,
                        new ItemStack(Femtocraft.itemOrganometallicPlate),
                        EnumTechLevel.NANO, null
                )); // ItemOrganometallicPlate
            }
        } catch (AssemblerRecipeFoundException e) {
            Femtocraft.logger.log(Level.SEVERE, e.errMsg);
            Femtocraft.logger.log(Level.SEVERE,
                    "Femtocraft failed to load Nano-tier Assembler Recipes!");
        }
    }

    private void registerMicroDecompositionRecipes() {
        try {
            if (configRegisterRecipe("Stone")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMineralLattice), null,
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Block.stone), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Grass")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Block.grass), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Dirt")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, new ItemStack(Femtocraft.itemMineralLattice),
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.dirt), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Cobblestone")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemMineralLattice), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.cobblestone), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("WoodPlank")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFibrousStrand),
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.planks), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Sapling")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{
                                new ItemStack(Femtocraft.itemFibrousStrand),
                                null, null,
                                new ItemStack(Femtocraft.itemFibrousStrand),
                                null, null,
                                new ItemStack(Femtocraft.itemFibrousStrand),
                                null, null}, 1, new ItemStack(Block.sapling),
                        EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Sand")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMicroCrystal), null, null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.sand), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Leaves")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.leaves), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Cobweb")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemSpinyFilament), null,
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Block.web), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("DeadBush")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, new ItemStack(Femtocraft.itemFibrousStrand),
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.deadBush), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Dandelion")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, new ItemStack(Femtocraft.itemMorphicChannel),
                        null, null, null, null, null}, 1, new ItemStack(
                        Block.plantYellow), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Rose")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, new ItemStack(Femtocraft.itemMorphicChannel),
                        null, null, null, null}, 1, new ItemStack(
                        Block.plantRed), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("MushroomBrown")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFungalSpores), null, null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.mushroomBrown), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("MushroomRed")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemFungalSpores), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Block.mushroomRed), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("MossStone")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMineralLattice),
                        new ItemStack(Femtocraft.itemFungalSpores), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Block.cobblestoneMossy), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Obsidian")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemHardenedBulb), null, null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.obsidian), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Ice")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemMicroCrystal), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Block.ice), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Cactus")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemSpinyFilament), null,
                        null, new ItemStack(Femtocraft.itemFibrousStrand),
                        null, null, null, null, null}, 1, new ItemStack(
                        Block.cactus), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Pumpkin")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFibrousStrand),
                        new ItemStack(Femtocraft.itemMorphicChannel), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.pumpkin), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Netherrack")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null,
                        new ItemStack(Femtocraft.itemMineralLattice), null,
                        null, null, null, null}, 1, new ItemStack(
                        Block.netherrack), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("SoulSand")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMicroCrystal),
                        new ItemStack(Femtocraft.itemIonicChunk), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Block.slowSand), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Glowstone")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemConductiveAlloy),
                        new ItemStack(Femtocraft.itemIonicChunk), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.glowstone), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Melon")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMorphicChannel),
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.melon), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Vine")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null,
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null, null}, 1, new ItemStack(Block.vine),
                        EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Mycelium")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFungalSpores),
                        new ItemStack(Femtocraft.itemMineralLattice), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.mycelium), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("LilyPad")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, null,
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null}, 1, new ItemStack(Block.waterlily),
                        EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("EnderStone")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemConductiveAlloy),
                        new ItemStack(Femtocraft.itemMineralLattice), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Block.whiteStone), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Cocoa")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMorphicChannel), null,
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Block.cocoaPlant), EnumTechLevel.MICRO,
                        null
                ));
            }
            if (configRegisterRecipe("Apple")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        new ItemStack(Femtocraft.itemMorphicChannel), null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.appleRed), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Coal")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMineralLattice),
                        new ItemStack(Femtocraft.itemIonicChunk), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.coal), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Diamond")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMicroCrystal, 8),
                        new ItemStack(Femtocraft.itemIonicChunk, 8),
                        new ItemStack(Femtocraft.itemMicroCrystal, 8),
                        new ItemStack(Femtocraft.itemIonicChunk, 8),
                        new ItemStack(Femtocraft.itemMicroCrystal, 8),
                        new ItemStack(Femtocraft.itemIonicChunk, 8),
                        new ItemStack(Femtocraft.itemMicroCrystal, 8),
                        new ItemStack(Femtocraft.itemIonicChunk, 8),
                        new ItemStack(Femtocraft.itemMicroCrystal, 8)}, 1,
                        new ItemStack(Item.diamond), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("IronIngot")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMetalComposite), null,
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Item.ingotIron), EnumTechLevel.MICRO,
                        null
                ));
            }
            if (configRegisterRecipe("GoldIngot")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMetalComposite),
                        new ItemStack(Femtocraft.itemHardenedBulb), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.ingotGold), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Stick")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, null, null,
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null}, 1, new ItemStack(Item.stick),
                        EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("String")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null, null, null, null}, 1,
                        new ItemStack(Item.silk), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Feather")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemSpinyFilament), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.feather), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Gunpowder")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemIonicChunk),
                        new ItemStack(Femtocraft.itemNerveCluster), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.gunpowder), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Seeds")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Item.seeds), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Wheat")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.wheat), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Flint")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemHardenedBulb), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.flint), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("RawPorkchop")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        new ItemStack(Femtocraft.itemNerveCluster), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.porkRaw), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Porkchop")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemProteinChain), null, null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.porkCooked), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Redstone")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemIonicChunk),
                        new ItemStack(Femtocraft.itemMineralLattice), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.redstone), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Snowball")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null, null, null, null, null, null,
                        new ItemStack(Femtocraft.itemMineralLattice)}, 1,
                        new ItemStack(Item.snowball), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Leather")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemProteinChain),
                        new ItemStack(Femtocraft.itemSynthesizedFiber), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.leather), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Clay")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemIonicChunk), null, null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.clay), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("SugarCane")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemSynthesizedFiber), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.reed), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Slimeball")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemNerveCluster), null, null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.slimeBall), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Egg")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemNerveCluster),
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Item.egg), EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("RawFish")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, new ItemStack(Femtocraft.itemNerveCluster),
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.fishRaw), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("CookedFish")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemProteinChain),
                        new ItemStack(Femtocraft.itemSpinyFilament), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.fishCooked), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Dye")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        new ItemStack(Femtocraft.itemSynthesizedFiber), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.dyePowder, 1, OreDictionary.WILDCARD_VALUE),
                        EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("Bone")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        new ItemStack(Femtocraft.itemHardenedBulb), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.bone), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("PumpkinSeeds")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemHardenedBulb),
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Item.pumpkinSeeds), EnumTechLevel.MICRO,
                        null
                ));
            }
            if (configRegisterRecipe("MelonSeeds")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemHardenedBulb),
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.melonSeeds), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("RawBeef")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemNerveCluster),
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.beefRaw), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("CookedBeef")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemProteinChain), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.beefCooked), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("RawChicken")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemNerveCluster), null, null,
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.chickenRaw), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("CookedChicken")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, new ItemStack(Femtocraft.itemProteinChain), null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.chickenCooked), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("RottenFlesh")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemSpinyFilament),
                        new ItemStack(Femtocraft.itemNerveCluster), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.rottenFlesh), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("EnderPearl")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemIonicChunk), null,
                        new ItemStack(Femtocraft.itemIonicChunk),
                        new ItemStack(Femtocraft.itemOrganometallicPlate),
                        new ItemStack(Femtocraft.itemIonicChunk), null,
                        new ItemStack(Femtocraft.itemIonicChunk), null}, 1,
                        new ItemStack(Item.enderPearl), EnumTechLevel.MICRO,
                        null
                ));
            }
            if (configRegisterRecipe("GhastTear")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemNerveCluster),
                        new ItemStack(Femtocraft.itemIonicChunk), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.ghastTear), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("NetherWart")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemFungalSpores),
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Item.netherStalkSeeds),
                        EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("SpiderEye")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemNerveCluster),
                        new ItemStack(Femtocraft.itemOrganometallicPlate),
                        null, null, null, null, null, null, null}, 1,
                        new ItemStack(Item.spiderEye), EnumTechLevel.MICRO,
                        null
                ));
            }
            if (configRegisterRecipe("BlazePowder")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMorphicChannel),
                        new ItemStack(Femtocraft.itemMicroCrystal), null, null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.blazePowder), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Emerald")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemMetalComposite),
                        new ItemStack(Femtocraft.itemConductiveAlloy), null,
                        null, null, null, null, null, null}, 1, new ItemStack(
                        Item.emerald), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Carrot")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null, null, null}, 1, new ItemStack(
                        Item.carrot), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Potato")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null,
                        new ItemStack(Femtocraft.itemReplicatingMaterial),
                        new ItemStack(Femtocraft.itemFibrousStrand), null,
                        null, null, null}, 1, new ItemStack(Item.potato),
                        EnumTechLevel.MICRO, null
                ));
            }
            if (configRegisterRecipe("BakedPotato")) {
                addReversableRecipe(new AssemblerRecipe(
                        new ItemStack[]{null, null, null, null, null, null,
                                new ItemStack(Femtocraft.itemFibrousStrand),
                                null, null}, 1,
                        new ItemStack(Item.bakedPotato), EnumTechLevel.MICRO,
                        null
                ));
            }
            if (configRegisterRecipe("PoisonPotato")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
                        null, null,
                        new ItemStack(Femtocraft.itemSynthesizedFiber), null,
                        null, null, null, null}, 1, new ItemStack(
                        Item.poisonousPotato), EnumTechLevel.MICRO, null));
            }
            if (configRegisterRecipe("Cake")) {
                addDecompositionRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Item.egg), new ItemStack(Item.sugar, 2),
                        new ItemStack(Item.wheat, 3), null, null, null, null,
                        null, null}, 1, new ItemStack(Item.cake),
                        EnumTechLevel.MACRO, null
                ));
            }

            if (configRegisterRecipe("NetherStar")) {
                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
                        new ItemStack(Femtocraft.itemHardenedBulb, 64),
                        new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                        new ItemStack(Femtocraft.itemHardenedBulb, 64),
                        new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                        new ItemStack(Item.diamond, 64),
                        new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                        new ItemStack(Femtocraft.itemHardenedBulb, 64),
                        new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                        new ItemStack(Femtocraft.itemHardenedBulb, 64)}, 1,
                        new ItemStack(Item.netherStar), EnumTechLevel.MICRO,
                        null
                ));
            }
        } catch (AssemblerRecipeFoundException e) {
            Femtocraft.logger.log(Level.SEVERE, e.errMsg);
            Femtocraft.logger.log(Level.SEVERE,
                    "Femtocraft failed to load Micro-tier Assembler Recipes!");
        }
    }

    private void registerMacroDecompositionRecipes() {

    }

    public void registerDefaultRecipes() {
        // Does not use Ore Dictionary values - this is why things like crafting
        // tables don't work.

        Femtocraft.logger
                .log(Level.WARNING,
                        "Registering assembler recipes from Vanilla Minecraft's Crafting Manager.\t This may take awhile ._.");

        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        List<ShapelessRecipes> shapelessRecipes = new ArrayList<ShapelessRecipes>();
        List<ShapedOreRecipe> shapedOre = new ArrayList<ShapedOreRecipe>();
        List<ShapelessOreRecipe> shapelessOre = new ArrayList<ShapelessOreRecipe>();

        Femtocraft.logger
                .log(Level.WARNING,
                        "Registering shaped recipes from Vanilla Minecraft's Crafting Manager.");
        for (IRecipe recipe : recipes) {
            if (getRecipe(recipe.getRecipeOutput()) != null) {
                Femtocraft.logger.log(Level.CONFIG,
                        "Assembler recipe already found for "
                                + recipe.getRecipeOutput().getDisplayName()
                                + "."
                );
                continue;
            }

            if (recipe instanceof ShapelessRecipes) {
                shapelessRecipes.add((ShapelessRecipes) recipe);
                continue;
            }

            if (recipe instanceof ShapedOreRecipe) {
                shapedOre.add((ShapedOreRecipe) recipe);
            }

            if (recipe instanceof ShapelessOreRecipe) {
                shapelessOre.add((ShapelessOreRecipe) recipe);
            }

            if (!(recipe instanceof ShapedRecipes)) {
                // When I figure out how to do the other recipes...WITHOUT
                // iterating through every conceivable combination of items and
                // damages in the scope of minecraft
                // It will go here.
                continue;
            }
            ShapedRecipes sr = (ShapedRecipes) recipe;

            Femtocraft.logger.log(Level.CONFIG,
                    "Attempting to register shaped assembler recipe for "
                            + sr.getRecipeOutput().getDisplayName() + "."
            );
            boolean valid = registerShapedRecipe(sr.recipeItems,
                    sr.getRecipeOutput(), sr.recipeWidth, sr.recipeHeight);
            if (!valid) {
                Femtocraft.logger.log(Level.WARNING,
                        "Failed to register shaped assembler recipe for "
                                + sr.getRecipeOutput().getDisplayName() + "!"
                );
            } else {
                Femtocraft.logger.log(Level.CONFIG,
                        "Loaded Vanilla Minecraft shaped recipe as assembler recipe for "
                                + sr.getRecipeOutput().getDisplayName() + "."
                );
            }
        }

        Femtocraft.logger.log(Level.WARNING,
                "Registering shaped ore recipes from Forge.");
        for (ShapedOreRecipe orecipe : shapedOre) {
            Femtocraft.logger.log(Level.CONFIG,
                    "Attempting to register shaped assembler recipe for "
                            + orecipe.getRecipeOutput().getDisplayName() + "."
            );
            // Hacky hacky hacky
            // They should at least have accessors for goodness sake
            int width = 0, height = 0;
            try {
                // Width
                {
                    Field width_field = ShapedOreRecipe.class
                            .getDeclaredField("width");
                    boolean prev = width_field.isAccessible();
                    if (!prev) {
                        width_field.setAccessible(true);
                    }
                    width = width_field.getInt(orecipe);
                    if (!prev) {
                        width_field.setAccessible(prev);
                    }
                }
                // Height
                {
                    Field height_field = ShapedOreRecipe.class
                            .getDeclaredField("height");
                    boolean prev = height_field.isAccessible();
                    if (!prev) {
                        height_field.setAccessible(true);
                    }
                    height = height_field.getInt(orecipe);
                    if (!prev) {
                        height_field.setAccessible(prev);
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            boolean valid = registerShapedOreRecipe(orecipe.getInput(),
                    orecipe.getRecipeOutput(), width, height);
            if (!valid) {
                Femtocraft.logger.log(Level.WARNING,
                        "Failed to register shaped assembler recipe for "
                                + orecipe.getRecipeOutput().getDisplayName()
                                + "!"
                );
            } else {
                Femtocraft.logger.log(Level.CONFIG,
                        "LoadedForge shaped ore recipe as assembler recipe for "
                                + orecipe.getRecipeOutput().getDisplayName()
                                + "."
                );
            }
        }

        Femtocraft.logger
                .log(Level.WARNING,
                        "Registering shapeless recipes from Vanilla Minecraft's Crafting Manager.");
        for (ShapelessRecipes recipe : shapelessRecipes) {
            if (getRecipe(recipe.getRecipeOutput()) != null) {
                Femtocraft.logger.log(Level.CONFIG,
                        "Assembler recipe already found for "
                                + recipe.getRecipeOutput().getDisplayName()
                                + "."
                );
                continue;
            }

            Femtocraft.logger.log(Level.CONFIG,
                    "Attempting to register shapeless assembler recipe for "
                            + recipe.getRecipeOutput().getDisplayName() + "."
            );

            boolean valid = registerShapelessRecipe(recipe.recipeItems,
                    recipe.getRecipeOutput());

            if (!valid) {
                Femtocraft.logger.log(Level.WARNING,
                        "Failed to register shapeless assembler recipe for "
                                + recipe.getRecipeOutput().getDisplayName()
                                + "!"
                );
                Femtocraft.logger
                        .log(Level.WARNING,
                                "I have no clue how this would happen...as the search space is literally thousands of configurations.  Sorry for the wait.");
            } else {
                Femtocraft.logger.log(Level.CONFIG,
                        "Loaded Vanilla Minecraft shapeless recipe as assembler recipe for + "
                                + recipe.getRecipeOutput().getDisplayName()
                                + "."
                );
            }
        }

        Femtocraft.logger.log(Level.WARNING,
                "Registering shapeless ore recipes from Forge.");
        for (ShapelessOreRecipe recipe : shapelessOre) {
            if (getRecipe(recipe.getRecipeOutput()) != null) {
                Femtocraft.logger.log(Level.CONFIG,
                        "Assembler recipe already found for "
                                + recipe.getRecipeOutput().getDisplayName()
                                + "."
                );
                continue;
            }

            Femtocraft.logger.log(Level.CONFIG,
                    "Attempting to register shapeless assembler recipe for "
                            + recipe.getRecipeOutput().getDisplayName() + "."
            );

            boolean valid = registerShapelessOreRecipe(recipe.getInput(),
                    recipe.getRecipeOutput());

            if (!valid) {
                Femtocraft.logger.log(Level.WARNING,
                        "Failed to register shapeless ore assembler recipe for "
                                + recipe.getRecipeOutput().getDisplayName()
                                + "!"
                );
                Femtocraft.logger
                        .log(Level.WARNING,
                                "I have no clue how this would happen...as the search space is literally thousands of configurations.  Sorry for the wait.");
            } else {
                Femtocraft.logger.log(Level.CONFIG,
                        "Loaded Forge shapeless ore recipe as assembler recipe for + "
                                + recipe.getRecipeOutput().getDisplayName()
                                + "."
                );
            }
        }
    }

    private boolean registerShapedOreRecipe(Object[] recipeInput,
                                            ItemStack recipeOutput, int width, int height) {
        boolean done = false;
        int xOffset = 0;
        int yOffset = 0;
        while ((!done) && (xOffset < 3) && (yOffset < 3)) {
            ItemStack[] input = new ItemStack[9];
            Arrays.fill(input, null);
            for (int i = 0; (i < recipeInput.length) && (i < 9); i++) {
                try {
                    ItemStack item;
                    Object obj = recipeInput[i];

                    if (obj instanceof ArrayList<?>) {
                        item = ((ArrayList<ItemStack>) obj).get(0);
                    } else {
                        item = (ItemStack) obj;
                    }
                    input[((i + xOffset) % width) + 3
                            * (yOffset + ((i + xOffset) / width))] = item == null ? null
//                            : item.copy();
                            : new ItemStack(item.itemID, 1, item.getItemDamage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    if (++xOffset >= 3) {
                        xOffset = 0;
                        ++yOffset;
                    }

                }
            }

            ItemStack output = recipeOutput.copy();
            if (output.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                output.setItemDamage(0);
            }

            for (ItemStack i : input) {
                if (i == null) {
                    continue;
                }

                if (i.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                    i.setItemDamage(0);
                }
            }

            try {
                addReversableRecipe(new AssemblerRecipe(input, 0, output,
                        EnumTechLevel.MACRO, null));
                done = true;
            } catch (AssemblerRecipeFoundException e) {
                // Attempt to offset, while staying inside crafting grid
                if ((++xOffset) >= 3) {
                    xOffset = 0;
                    ++yOffset;
                }
                done = false;
            }
        }

        return done;
    }

    private boolean registerShapedRecipe(ItemStack[] recipeItems,
                                         ItemStack recipeOutput, int recipeWidth, int recipeHeight) {
        boolean done = false;
        int xoffset = 0;
        int yoffset = 0;
        while ((!done) && ((xoffset + recipeWidth) <= 3)
                && ((yoffset + recipeHeight) <= 3)) {
            ItemStack[] input = new ItemStack[9];
            Arrays.fill(input, null);
            for (int i = 0; (i < recipeItems.length) && (i < 9); i++) {
                ItemStack item = recipeItems[i];
                input[((i + xoffset) % recipeWidth) + 3
                        * (yoffset + ((i + xoffset) / recipeWidth))] = item == null ? null
//                        : item.copy();
                        : new ItemStack(item.itemID, 1, item.getItemDamage());
            }

            try {
                addReversableRecipe(new AssemblerRecipe(input, 0,
                        recipeOutput.copy(), EnumTechLevel.MACRO, null));
                done = true;
            } catch (AssemblerRecipeFoundException e) {
                // Attempt to offset, while staying inside crafting grid
                if ((++xoffset + recipeWidth) > 3) {
                    xoffset = 0;
                    ++yoffset;
                }
                done = false;
            }
        }

        return done;
    }

    private boolean registerShapelessOreRecipe(List recipeItems,
                                               ItemStack recipeOutput) {
        boolean valid = false;
        int[] slots = new int[recipeItems.size()];

        // Exhaustively find a configuration that works - this should NEVER have
        // to go the full distance
        // but I don't want to half-ass the attempt in case there are MANY
        // collisions
        int offset = 0;
        while (!valid && ((offset + recipeItems.size()) <= 9)) {
            for (int i = 0; i < slots.length; ++i) {
                slots[i] = i;
            }

            while (!valid) {
                ItemStack[] input = new ItemStack[9];
                Arrays.fill(input, null);

                for (int i = 0; (i < slots.length) && (i < 9); ++i) {
                    ItemStack item;
                    Object obj = recipeItems.get(i);

                    if (obj instanceof ArrayList<?>) {
                        item = ((ArrayList<ItemStack>) obj).get(0);
                    } else {
                        item = (ItemStack) obj;
                    }

                    input[slots[i] + offset] = item == null ? null : item
                            .copy();
                }

                ItemStack output = recipeOutput.copy();
                if (output.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                    output.setItemDamage(0);
                }

                for (ItemStack i : input) {
                    if (i == null) {
                        continue;
                    }

                    if (i.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                        i.setItemDamage(0);
                    }
                }

                try {
                    addReversableRecipe(new AssemblerRecipe(input, 0, output,
                            EnumTechLevel.MACRO, null));
                    valid = true;
                } catch (AssemblerRecipeFoundException e) {
                    // Permute the slots
                    slots = permute(slots);

                    valid = false;
                }
            }
        }

        return valid;
    }

    private boolean registerShapelessRecipe(List recipeItems,
                                            ItemStack recipeOutput) {
        boolean valid = false;
        int[] slots = new int[recipeItems.size()];

        // Exhaustively find a configuration that works - this should NEVER have
        // to go the full distance
        // but I don't want to half-ass the attempt in case there are MANY
        // collisions
        int offset = 0;
        while (!valid && ((offset + recipeItems.size()) <= 9)) {
            for (int i = 0; i < slots.length; ++i) {
                slots[i] = i;
            }

            while (!valid) {
                ItemStack[] input = new ItemStack[9];
                Arrays.fill(input, null);

                for (int i = 0; (i < slots.length) && (i < 9); ++i) {
                    ItemStack item = (ItemStack) recipeItems.get(i);
                    input[slots[i] + offset] = item == null ? null : item
                            .copy();
                }

                try {
                    addReversableRecipe(new AssemblerRecipe(input, 0,
                            recipeOutput.copy(), EnumTechLevel.MACRO, null));
                    valid = true;
                } catch (AssemblerRecipeFoundException e) {
                    // Permute the slots
                    slots = permute(slots);

                    valid = false;
                }
            }
        }

        return valid;
    }

    private int[] permute(int[] slots) {
        int k = findHighestK(slots);
        int i = findHigherI(slots, k);

        // Switch k and i
        int prev = slots[k];
        slots[k] = slots[i];
        slots[i] = prev;

        // Reverse ordering of k+1 to end
        int remaining = (int) Math.ceil((slots.length - k + 1) / 2.);
        for (int r = k + 1, n = 0; (r < slots.length) && (n < remaining); ++r, ++n) {
            int pr = slots[r];
            slots[r] = slots[slots.length - n - 1];
            slots[slots.length - n - 1] = pr;
        }

        return slots;
    }

    private int findHighestK(int[] slots) {
        int ret = 0;
        for (int i = 0; i < slots.length - 1; ++i) {
            if ((slots[i] < slots[i + 1]) && (ret < i)) {
                ret = i;
            }
        }
        return ret;
    }

    private int findHigherI(int[] slots, int k) {
        int ret = 0;

        for (int i = 0; i < slots.length; ++i) {
            if ((slots[k] < slots[i]) && (ret < i)) {
                ret = i;
            }
        }
        return ret;
    }

    private void registerFemtocraftAssemblerRecipes() {
        try {
            if (configRegisterRecipe("IronOre")) {
//                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                        null, null, null,
//                        new ItemStack(Femtocraft.deconstructedIron, 2), null,
//                        null, null, null}, 0, new ItemStack(Block.oreIron),
//                        EnumTechLevel.MACRO, null
//                ));
            }
            if (configRegisterRecipe("GoldOre")) {
//                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                        null, null, null,
//                        new ItemStack(Femtocraft.deconstructedGold, 2), null,
//                        null, null, null}, 0, new ItemStack(Block.oreGold),
//                        EnumTechLevel.MACRO, null
//                ));
            }
            if (configRegisterRecipe("TitaniumOre")) {
//                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                        null, null, null,
//                        new ItemStack(Femtocraft.deconstructedTitanium, 2),
//                        null, null, null, null}, 0, new ItemStack(
//                        Femtocraft.blockOreTitanium), EnumTechLevel.MACRO, null));
            }
            if (configRegisterRecipe("ThoriumOre")) {
//                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                        null, null, null,
//                        new ItemStack(Femtocraft.deconstructedThorium, 2),
//                        null, null, null, null}, 0, new ItemStack(
//                        Femtocraft.blockOreThorium), EnumTechLevel.MACRO, null));
            }
            if (configRegisterRecipe("PlatinumOre")) {
//                addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                        null, null, null,
//                        new ItemStack(Femtocraft.deconstructedPlatinum, 2),
//                        null, null, null, null}, 0, new ItemStack(
//                        Femtocraft.blockOrePlatinum), EnumTechLevel.MACRO, null));
            }

            addDecompositionRecipe(new AssemblerRecipe(new ItemStack[]{
                    new ItemStack(Item.paper, 3), null, null, null, null, null,
                    null, null, null}, 0, new ItemStack(
                    Femtocraft.itemPaperSchematic), EnumTechLevel.MACRO, null));

            addRecompositionRecipe(new AssemblerRecipe(
                    new ItemStack[]{
                            new ItemStack(Femtocraft.itemSpoolGold),
                            new ItemStack(Femtocraft.itemSpoolGold),
                            new ItemStack(Femtocraft.itemSpoolGold),
                            new ItemStack(Femtocraft.itemConductivePowder),
                            new ItemStack(Femtocraft.itemConductivePowder),
                            new ItemStack(Femtocraft.itemConductivePowder),
                            new ItemStack(Block.planks, 1,
                                    OreDictionary.WILDCARD_VALUE),
                            new ItemStack(Block.planks, 1,
                                    OreDictionary.WILDCARD_VALUE),
                            new ItemStack(Block.planks, 1,
                                    OreDictionary.WILDCARD_VALUE)}, 0,
                    new ItemStack(Femtocraft.itemMicrochip, 6),
                    EnumTechLevel.MACRO, null
            ));

        } catch (AssemblerRecipeFoundException e) {
            Femtocraft.logger.log(Level.SEVERE, e.errMsg);
            Femtocraft.logger.log(Level.SEVERE,
                    "Femtocraft failed to load Femtocraft Assembler Recipes!");
        }
    }

    private boolean configRegisterRecipe(String name) {
        boolean register = false;
        boolean found = false;

        Field[] fields = FemtocraftConfigs.class.getFields();
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase("recipe" + name)) {
                found = true;

                try {
                    register = field.getBoolean(null);

                    Level logLevel = FemtocraftConfigs.silentRecipeLoadAlerts ? Level.CONFIG
                            : Level.INFO;

                    if (register) {
                        Femtocraft.logger.log(logLevel,
                                "Loading default AssemblerRecipe for " + name
                                        + "."
                        );
                    } else {
                        Femtocraft.logger
                                .log(logLevel,
                                        "Not loading AssemblerRecipe for "
                                                + name + "."
                                );
                    }
                } catch (Exception e) {
                    Femtocraft.logger.log(Level.WARNING,
                            "Exception - " + e.getLocalizedMessage()
                                    + " thrown while loading AssemblerRecipe "
                                    + name + "."
                    );
                }
            }
        }

        if (!found) {
            Femtocraft.logger
                    .log(Level.WARNING,
                            "No configuration option for AssemblerRecipe "
                                    + name
                                    + " has been found.  Please report this to Femtocraft developers immediately."
                    );
        }

        return register;
    }

    private void testRecipes() {
        AssemblerRecipe test = getRecipe(new ItemStack[]{null, null, null,
                new ItemStack(Femtocraft.itemPlaneoid),
                new ItemStack(Femtocraft.itemRectangulon),
                new ItemStack(Femtocraft.itemPlaneoid), null, null, null});
        Femtocraft.logger.log(Level.WARNING, "Recipe "
                + (test != null ? "found" : "not found") + ".");
        if (test != null) {
            Femtocraft.logger.log(
                    Level.WARNING,
                    "Output "
                            + (test.output.isItemEqual(new ItemStack(
                            Femtocraft.itemFlorite)) ? "matches"
                            : "does not match") + "."
            );
        }

        test = getRecipe(new ItemStack[]{null, null, null,
                new ItemStack(Femtocraft.itemRectangulon),
                new ItemStack(Femtocraft.itemRectangulon),
                new ItemStack(Femtocraft.itemPlaneoid), null, null, null});
        Femtocraft.logger.log(Level.WARNING, "Recipe "
                + (test != null ? "found" : "not found") + ".");

        test = getRecipe(new ItemStack(Femtocraft.itemFlorite));
        Femtocraft.logger.log(Level.WARNING, "Recipe "
                + (test != null ? "found" : "not found") + ".");
    }

    public boolean addReversableRecipe(AssemblerRecipe recipe)
            throws IllegalArgumentException, AssemblerRecipeFoundException {
        if (recipe.input.length != 9) {
            throw new IllegalArgumentException(
                    "AssemblerRecipe - Invalid Input Array Length!  Must be 9!");
        }
        ItemStack[] normalArray = normalizedInput(recipe);
        if (normalArray == null) {
            return false;
        }

        ItemStack normal = normalizedOutput(recipe);

        checkDecomposition(normal, recipe);
        checkRecomposition(normalArray, recipe);

        return registerRecomposition(normalArray, recipe)
                && registerDecomposition(normal, recipe);
    }

    public boolean addRecompositionRecipe(AssemblerRecipe recipe)
            throws IllegalArgumentException, AssemblerRecipeFoundException {
        if (recipe.input.length != 9) {
            throw new IllegalArgumentException(
                    "AssemblerRecipe - Invalid Input Array Length!  Must be 9!");
        }

        ItemStack[] normal = normalizedInput(recipe);
        if (normal == null) {
            return false;
        }

        checkRecomposition(normal, recipe);

        return registerRecomposition(normal, recipe);
    }

    public boolean addDecompositionRecipe(AssemblerRecipe recipe)
            throws IllegalArgumentException, AssemblerRecipeFoundException {
        if (recipe.input.length != 9) {
            throw new IllegalArgumentException(
                    "AssemblerRecipe - Invalid Input Array Length!  Must be 9!");
        }

        ItemStack normal = normalizedOutput(recipe);

        checkDecomposition(normal, recipe);

        return registerDecomposition(normal, recipe);
    }

    private boolean registerRecomposition(ItemStack[] normal,
                                          AssemblerRecipe recipe) {
        AssemblerRecompositionRegisterEvent event = new AssemblerRecompositionRegisterEvent(
                recipe);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            inputToRecipeMap.put(normal, recipe);
            addRecipeToTechLevelMap(recipe);
            addRecipeToTechnologyMap(recipe);
            return true;
        }
        return false;
    }

    private boolean registerDecomposition(ItemStack normal,
                                          AssemblerRecipe recipe) {
        AssemblerDecompositionRegisterEvent event = new AssemblerDecompositionRegisterEvent(
                recipe);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            outputToRecipeMap.put(normal, recipe);
            addRecipeToTechLevelMap(recipe);
            addRecipeToTechnologyMap(recipe);
            return true;
        }
        return false;
    }

    private void addRecipeToTechLevelMap(AssemblerRecipe recipe) {
        ArrayList<AssemblerRecipe> array = techLevelToRecipeMap
                .get(recipe.enumTechLevel);
        if (array == null) {
            array = new ArrayList<AssemblerRecipe>();
            techLevelToRecipeMap.put(recipe.enumTechLevel, array);
        }
        if (!array.contains(recipe)) {
            array.add(recipe);
        }
    }

    private void addRecipeToTechnologyMap(AssemblerRecipe recipe) {
        ArrayList<AssemblerRecipe> array = technologyToRecipeMap
                .get(recipe.tech);
        if (array == null) {
            array = new ArrayList<AssemblerRecipe>();
            technologyToRecipeMap.put(recipe.tech, array);
        }
        if (!array.contains(recipe)) {
            array.add(recipe);
        }
    }

    private void checkDecomposition(ItemStack normal, AssemblerRecipe recipe)
            throws AssemblerRecipeFoundException {
        if (outputToRecipeMap.containsKey(normal)) {
            throw new AssemblerRecipeFoundException(
                    "AssemblerRecipe found for Decomposition of item - "
                            + recipe.output.getDisplayName() + "."
            );
        }
    }

    private void checkRecomposition(ItemStack[] normal, AssemblerRecipe recipe)
            throws AssemblerRecipeFoundException {
        if (inputToRecipeMap.containsKey(normal)) {
            throw new AssemblerRecipeFoundException(
                    "AssemblerRecipe found for Recomposition of item - "
                            + recipe.output.getDisplayName() + "."
            );
        }
    }

    public boolean removeAnyRecipe(AssemblerRecipe recipe) {
        return removeDecompositionRecipe(recipe)
                || removeRecompositionRecipe(recipe);
    }

    public boolean removeReversableRecipe(AssemblerRecipe recipe) {
        return removeDecompositionRecipe(recipe)
                && removeRecompositionRecipe(recipe);
    }

    public boolean removeRecompositionRecipe(AssemblerRecipe recipe) {
        ItemStack[] normal = normalizedInput(recipe);
        return normal != null && (inputToRecipeMap.remove(normal) != null);
    }

    public boolean removeDecompositionRecipe(AssemblerRecipe recipe) {
        return (outputToRecipeMap.remove(normalizedOutput(recipe)) != null);
    }

    public boolean canCraft(ItemStack[] input) {
        if (input.length != 9) {
            return false;
        }
        AssemblerRecipe recipe = getRecipe(input);
        if (recipe == null) {
            return false;
        }

        for (int i = 0; i < 9; ++i) {
            ItemStack rec = recipe.input[i];
            if (input[i] == null || rec == null) {
                continue;
            }

            if (input[i].stackSize < input[i].stackSize) {
                return false;
            }
            if (FemtocraftUtils.compareItem(rec, input[i]) != 0) {
                return false;
            }
        }

        return true;
    }

    public boolean canCraft(ItemStack input) {
        AssemblerRecipe recipe = getRecipe(input);
        return recipe != null && input.stackSize >= recipe.output.stackSize
                && FemtocraftUtils.compareItem(recipe.output, input) == 0;
    }

    public AssemblerRecipe getRecipe(ItemStack[] input) {
        ItemStack[] normal = normalizedInput(input);
        if (normal == null) {
            return null;
        }
        return inputToRecipeMap.get(normal);
    }

    public AssemblerRecipe getRecipe(ItemStack output) {
        return outputToRecipeMap.get(normalizedItem(output));
    }

    public ArrayList<AssemblerRecipe> getRecipesForTechLevel(EnumTechLevel level) {
        return techLevelToRecipeMap.get(level);
    }

    public ArrayList<AssemblerRecipe> getRecipesForTechnology(
            ResearchTechnology tech) {
        return technologyToRecipeMap.get(tech);
    }

    public boolean hasResearchedRecipe(AssemblerRecipe recipe, String username) {
        return Femtocraft.researchManager.hasPlayerResearchedTechnology(
                username, recipe.tech);
    }

    private ItemStack normalizedOutput(AssemblerRecipe recipe) {
        return normalizedItem(recipe.output);
    }

    private ItemStack[] normalizedInput(AssemblerRecipe recipe) {
        return normalizedInput(recipe.input);
    }

    private ItemStack[] normalizedInput(ItemStack[] input) {
        if (input.length != 9) {
            return null;
        }

        ItemStack[] ret = new ItemStack[9];

        for (int i = 0; i < 9; i++) {
            ret[i] = normalizedItem(input[i]);
        }
        return ret;
    }

    private ItemStack normalizedItem(ItemStack original) {
        if (original == null) {
            return null;
        }
        return new ItemStack(original.itemID, 1, original.getItemDamage());
    }

    public static class AssemblerRecipeFoundException extends Exception {
        public String errMsg;

        public AssemblerRecipeFoundException(String message) {
            errMsg = message;
        }
    }
}
