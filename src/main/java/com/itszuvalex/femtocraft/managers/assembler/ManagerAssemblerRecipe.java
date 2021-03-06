///*
// * ******************************************************************************
// *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// *  * Itszuvalex@gmail.com
// *  *
// *  * This program is free software; you can redistribute it and/or
// *  * modify it under the terms of the GNU General Public License
// *  * as published by the Free Software Foundation; either version 2
// *  * of the License, or (at your option) any later version.
// *  *
// *  * This program is distributed in the hope that it will be useful,
// *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  * GNU General Public License for more details.
// *  *
// *  * You should have received a copy of the GNU General Public License
// *  * along with this program; if not, write to the Free Software
// *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// *  *****************************************************************************
// */
//
//package com.itszuvalex.femtocraft.managers.assembler;
//
//import com.itszuvalex.femtocraft.Femtocraft;
//import com.itszuvalex.femtocraft.api.events.EventAssemblerRegister.AssemblerDecompositionRegisterEvent;
//import com.itszuvalex.femtocraft.api.events.EventAssemblerRegister.AssemblerRecompositionRegisterEvent;
//import com.itszuvalex.femtocraft.api.EnumTechLevel;
//import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
//import com.itszuvalex.femtocraft.research.FemtocraftTechnologies;
//import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.CraftingManager;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.ShapedRecipes;
//import net.minecraft.item.crafting.ShapelessRecipes;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.oredict.OreDictionary;
//import net.minecraftforge.oredict.ShapedOreRecipe;
//import net.minecraftforge.oredict.ShapelessOreRecipe;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.logging.Level;
//
///**
// * @author chris
// * @category Manager
// * @info This manager is responsible for all Femtocraft AssemblerRecipes. All Assembler/Dissassemblers look to this
// * manager for recipe lookup. Recipes can be specified to only be disassemble-able, or only reassemble-able.
// * Dissassemblers simply break down items, Reassembles must use schematics to specify the recipe to follow. <br> All
// * recipes are ordered according to their signature in the inventory. The entire 9 slots are used for the input
// * signature. ItemStack stackSize does not matter for ordering. Exceptions will be thrown when attempting to addInput
// * recipes when their signature is already associated with a recipe (no check is performed to see if the recipes are
// * actually equal or not.) When reconstructing, items must conform to the input signature, and all 9 slots are
// * important. Slots that are null in the recipe must not contain any items, and vice versa. This will be separately
// * enforced in the schematic-creating TileEntities, but it it is also stated here for reference.
// */
//public class ManagerAssemblerRecipe {
//    public static final long shapelessPermuteTimeMillis = 10;
//    AssemblerRecipeDatabase ard = new AssemblerRecipeDatabase();
//
//
//    public void init() {
//        registerRecipes();
//    }
//
//    private void registerRecipes() {
//        Femtocraft.log(Level.INFO, "Registering Femtocraft assembler recipes.");
//        if (ard.shouldRegister()) {
//            Femtocraft.assemblerConfigs().setBatchLoading(true);
//            registerCustomRecipes();
//            registerFemtoDecompositionRecipes();
//            registerNanoDecompositionRecipes();
//            registerMicroDecompositionRecipes();
//            registerMacroDecompositionRecipes();
//
//            registerFemtocraftAssemblerRecipes();
//            Femtocraft.assemblerConfigs().setBatchLoading(false);
//        }
//        Femtocraft.log(Level.INFO, "Finished registering Femtocraft assembler recipes.");
//    }
//
//    private void registerCustomRecipes() {
//        Femtocraft.log(Level.INFO, "Registering custom assembler recipes.");
//        List<AssemblerRecipe> custom = Femtocraft.assemblerConfigs().loadCustomRecipes();
//        for (AssemblerRecipe recipe : custom) {
//        }
//        Femtocraft.log(Level.INFO, "Finished registering custom assembler recipes.");
//    }
//
//
//    private void registerFemtoDecompositionRecipes() {
//        try {
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemRectangulon()),
//                    new ItemStack(Femtocraft.itemPlaneoid()),
//                    new ItemStack(Femtocraft.itemRectangulon()), null, null,
//                    null}, 3, new ItemStack(Femtocraft.itemCrystallite()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS
//            )); // ItemCrystallite
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null,
//                            new ItemStack(Femtocraft.itemCubit()),
//                            new ItemStack(Femtocraft.itemPlaneoid()),
//                            new ItemStack(Femtocraft.itemCubit()), null,
//                            null, null}, 3, new ItemStack(
//                    Femtocraft.itemMineralite()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS
//            )); // ItemMineralite
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null,
//                            new ItemStack(Femtocraft.itemCubit()),
//                            new ItemStack(Femtocraft.itemRectangulon()),
//                            new ItemStack(Femtocraft.itemCubit()), null,
//                            null, null}, 3, new ItemStack(
//                    Femtocraft.itemMetallite()), EnumTechLevel.FEMTO,
//                    FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS
//            )); // ItemMetallite
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemRectangulon()),
//                    new ItemStack(Femtocraft.itemCubit()),
//                    new ItemStack(Femtocraft.itemRectangulon()), null, null,
//                    null}, 3, new ItemStack(Femtocraft.itemFaunite()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS
//            )); // ItemFaunite
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemPlaneoid()),
//                    new ItemStack(Femtocraft.itemCubit()),
//                    new ItemStack(Femtocraft.itemPlaneoid()), null, null,
//                    null}, 3, new ItemStack(Femtocraft.itemElectrite()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS
//            )); // ItemElectrite
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemPlaneoid()),
//                    new ItemStack(Femtocraft.itemRectangulon()),
//                    new ItemStack(Femtocraft.itemPlaneoid()), null, null,
//                    null}, 3, new ItemStack(Femtocraft.itemFlorite()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS
//            )); // ItemFlorite
//        } catch (AssemblerRecipeFoundException e) {
//            Femtocraft.log(Level.SEVERE, e.errMsg);
//            Femtocraft.log(Level.SEVERE,
//                    "Femtocraft failed to load Femto-tier Assembler Recipes!");
//        }
//    }
//
//    private void registerNanoDecompositionRecipes() {
//        try {
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemCrystallite(), 2),
//                    new ItemStack(Femtocraft.itemElectrite(), 2),
//                    new ItemStack(Femtocraft.itemCrystallite(), 2),
//                    new ItemStack(Femtocraft.itemElectrite(), 2),
//                    new ItemStack(Femtocraft.itemCrystallite(), 2),
//                    new ItemStack(Femtocraft.itemElectrite(), 2),
//                    new ItemStack(Femtocraft.itemCrystallite(), 2),
//                    new ItemStack(Femtocraft.itemElectrite(), 2),
//                    new ItemStack(Femtocraft.itemCrystallite(), 2)}, 2,
//                    new ItemStack(Femtocraft.itemMicroCrystal()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemMicroCrystal
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null,
//                            new ItemStack(Femtocraft.itemFaunite()),
//                            new ItemStack(Femtocraft.itemMineralite()),
//                            new ItemStack(Femtocraft.itemFaunite()), null,
//                            null, null}, 2, new ItemStack(
//                    Femtocraft.itemProteinChain()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemProteinChain
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null,
//                            new ItemStack(Femtocraft.itemFaunite()),
//                            new ItemStack(Femtocraft.itemElectrite()),
//                            new ItemStack(Femtocraft.itemFaunite()), null,
//                            null, null}, 2, new ItemStack(
//                    Femtocraft.itemNerveCluster()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemNerveCluster
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemMetallite()), null,
//                    new ItemStack(Femtocraft.itemElectrite()),
//                    new ItemStack(Femtocraft.itemElectrite()),
//                    new ItemStack(Femtocraft.itemElectrite()), null,
//                    new ItemStack(Femtocraft.itemMetallite()), null}, 2,
//                    new ItemStack(Femtocraft.itemConductiveAlloy()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemConductiveAlloy
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemMineralite()), null,
//                    new ItemStack(Femtocraft.itemMetallite()),
//                    new ItemStack(Femtocraft.itemMetallite()),
//                    new ItemStack(Femtocraft.itemMetallite()), null,
//                    new ItemStack(Femtocraft.itemMineralite()), null}, 2,
//                    new ItemStack(Femtocraft.itemMetalComposite()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemMetalComposite
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemFlorite()),
//                    null, new ItemStack(Femtocraft.itemMineralite()), null,
//                    null, null}, 2, new ItemStack(
//                    Femtocraft.itemFibrousStrand()), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY)
// ); //
//            // ItemFibrousStrand
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemMineralite()),
//                    null, new ItemStack(Femtocraft.itemCrystallite()), null,
//                    null, null}, 2, new ItemStack(
//                    Femtocraft.itemMineralLattice()), EnumTechLevel.NANO,
//                    FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemMineralLattice
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null,
//                            new ItemStack(Femtocraft.itemFlorite()),
//                            new ItemStack(Femtocraft.itemCrystallite()),
//                            new ItemStack(Femtocraft.itemFlorite()), null,
//                            null, null}, 2, new ItemStack(
//                    Femtocraft.itemFungalSpores()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemFungalSpores
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemElectrite()),
//                    new ItemStack(Femtocraft.itemMineralite()),
//                    new ItemStack(Femtocraft.itemElectrite()), null, null,
//                    null}, 2, new ItemStack(Femtocraft.itemIonicChunk()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemIonicChunk
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null,
//                            new ItemStack(Femtocraft.itemFlorite()),
//                            new ItemStack(Femtocraft.itemFaunite()),
//                            new ItemStack(Femtocraft.itemFlorite()), null,
//                            null, null}, 2, new ItemStack(
//                    Femtocraft.itemReplicatingMaterial()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemReplicatingMaterial
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemCrystallite()),
//                    new ItemStack(Femtocraft.itemFaunite()),
//                    new ItemStack(Femtocraft.itemCrystallite()), null, null,
//                    null}, 2, new ItemStack(Femtocraft.itemSpinyFilament()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemSpinyFilament
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemCrystallite()),
//                    new ItemStack(Femtocraft.itemMetallite()),
//                    new ItemStack(Femtocraft.itemCrystallite()), null, null,
//                    null}, 2, new ItemStack(Femtocraft.itemHardenedBulb()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemHardenedBulb
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, new ItemStack(Femtocraft.itemElectrite()),
//                    new ItemStack(Femtocraft.itemFlorite()),
//                    new ItemStack(Femtocraft.itemElectrite()), null, null,
//                    null}, 2,
//                    new ItemStack(Femtocraft.itemMorphicChannel()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemMorphicChannel
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null,
//                            new ItemStack(Femtocraft.itemFlorite()),
//                            new ItemStack(Femtocraft.itemMetallite()),
//                            new ItemStack(Femtocraft.itemFlorite()), null,
//                            null, null}, 2, new ItemStack(
//                    Femtocraft.itemSynthesizedFiber()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemSynthesizedFiber
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemMetallite()), null,
//                    new ItemStack(Femtocraft.itemFaunite()),
//                    new ItemStack(Femtocraft.itemFaunite()),
//                    new ItemStack(Femtocraft.itemFaunite()), null,
//                    new ItemStack(Femtocraft.itemMetallite()), null}, 2,
//                    new ItemStack(Femtocraft.itemOrganometallicPlate()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY
//            )); // ItemOrganometallicPlate
//        } catch (AssemblerRecipeFoundException e) {
//            Femtocraft.log(Level.SEVERE, e.errMsg);
//            Femtocraft.log(Level.SEVERE,
//                    "Femtocraft failed to load Nano-tier Assembler Recipes!");
//        }
//    }
//
//    private void registerMicroDecompositionRecipes() {
//        try {
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMineralLattice()), null,
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Block.stone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Block.grass), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, new ItemStack(Femtocraft.itemMineralLattice()),
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.dirt), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemMineralLattice()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.cobblestone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFibrousStrand()),
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.planks), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{
//                            new ItemStack(Femtocraft.itemFibrousStrand()),
//                            null, null,
//                            new ItemStack(Femtocraft.itemFibrousStrand()),
//                            null, null,
//                            new ItemStack(Femtocraft.itemFibrousStrand()),
//                            null, null}, 1, new ItemStack(Block.sapling),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMicroCrystal()), null, null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.sand), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.leaves), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemSpinyFilament()), null,
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Block.web), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, new ItemStack(Femtocraft.itemFibrousStrand()),
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.deadBush), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, new ItemStack(Femtocraft.itemMorphicChannel()),
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Block.plantYellow), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, new ItemStack(Femtocraft.itemMorphicChannel()),
//                    null, null, null, null}, 1, new ItemStack(
//                    Block.plantRed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFungalSpores()), null, null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.mushroomBrown), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemFungalSpores()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Block.mushroomRed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMineralLattice()),
//                    new ItemStack(Femtocraft.itemFungalSpores()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Block.cobblestoneMossy), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemHardenedBulb()), null, null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.obsidian), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemMicroCrystal()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Block.ice), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemSpinyFilament()), null,
//                    null, new ItemStack(Femtocraft.itemFibrousStrand()),
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Block.cactus), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFibrousStrand()),
//                    new ItemStack(Femtocraft.itemMorphicChannel()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.pumpkin), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null,
//                    new ItemStack(Femtocraft.itemMineralLattice()), null,
//                    null, null, null, null}, 1, new ItemStack(
//                    Block.netherrack), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMicroCrystal()),
//                    new ItemStack(Femtocraft.itemIonicChunk()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Block.slowSand), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemConductiveAlloy()),
//                    new ItemStack(Femtocraft.itemIonicChunk()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.glowstone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMorphicChannel()),
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.melon), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null,
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null, null}, 1, new ItemStack(Block.vine),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFungalSpores()),
//                    new ItemStack(Femtocraft.itemMineralLattice()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.mycelium), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, null,
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null}, 1, new ItemStack(Block.waterlily),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemConductiveAlloy()),
//                    new ItemStack(Femtocraft.itemMineralLattice()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Block.whiteStone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMorphicChannel()), null,
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Block.cocoaPlant), EnumTechLevel.MICRO,
//                    FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    new ItemStack(Femtocraft.itemMorphicChannel()), null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.appleRed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMineralLattice()),
//                    new ItemStack(Femtocraft.itemIonicChunk()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.coal), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMicroCrystal(), 8),
//                    new ItemStack(Femtocraft.itemIonicChunk(), 8),
//                    new ItemStack(Femtocraft.itemMicroCrystal(), 8),
//                    new ItemStack(Femtocraft.itemIonicChunk(), 8),
//                    new ItemStack(Femtocraft.itemMicroCrystal(), 8),
//                    new ItemStack(Femtocraft.itemIonicChunk(), 8),
//                    new ItemStack(Femtocraft.itemMicroCrystal(), 8),
//                    new ItemStack(Femtocraft.itemIonicChunk(), 8),
//                    new ItemStack(Femtocraft.itemMicroCrystal(), 8)}, 1,
//                    new ItemStack(Item.diamond), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMetalComposite()), null,
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Item.ingotIron), EnumTechLevel.MICRO,
//                    FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMetalComposite()),
//                    new ItemStack(Femtocraft.itemHardenedBulb()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.ingotGold), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, null, null,
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null}, 1, new ItemStack(Item.stick),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Item.silk), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemSpinyFilament()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.feather), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemIonicChunk()),
//                    new ItemStack(Femtocraft.itemNerveCluster()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.gunpowder), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Item.seeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.wheat), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemHardenedBulb()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.flint), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    new ItemStack(Femtocraft.itemNerveCluster()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.porkRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemProteinChain()), null, null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.porkCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemIonicChunk()),
//                    new ItemStack(Femtocraft.itemMineralLattice()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.redstone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null, null, null, null, null, null,
//                    new ItemStack(Femtocraft.itemMineralLattice())}, 1,
//                    new ItemStack(Item.snowball), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemProteinChain()),
//                    new ItemStack(Femtocraft.itemSynthesizedFiber()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.leather), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemIonicChunk()), null, null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.clay), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemSynthesizedFiber()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.reed), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemNerveCluster()), null, null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.slimeBall), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemNerveCluster()),
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Item.egg), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, new ItemStack(Femtocraft.itemNerveCluster()),
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.fishRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemProteinChain()),
//                    new ItemStack(Femtocraft.itemSpinyFilament()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.fishCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    new ItemStack(Femtocraft.itemSynthesizedFiber()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.dyePowder, 1, OreDictionary.WILDCARD_VALUE),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    new ItemStack(Femtocraft.itemHardenedBulb()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.bone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemHardenedBulb()),
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Item.pumpkinSeeds), EnumTechLevel.MICRO,
//                    FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemHardenedBulb()),
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.melonSeeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemNerveCluster()),
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.beefRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemProteinChain()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.beefCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemNerveCluster()), null, null,
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.chickenRaw), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, new ItemStack(Femtocraft.itemProteinChain()), null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.chickenCooked), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemSpinyFilament()),
//                    new ItemStack(Femtocraft.itemNerveCluster()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.rottenFlesh), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemIonicChunk()), null,
//                    new ItemStack(Femtocraft.itemIonicChunk()),
//                    new ItemStack(Femtocraft.itemOrganometallicPlate()),
//                    new ItemStack(Femtocraft.itemIonicChunk()), null,
//                    new ItemStack(Femtocraft.itemIonicChunk()), null}, 1,
//                    new ItemStack(Item.enderPearl), EnumTechLevel.MICRO,
//                    FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemNerveCluster()),
//                    new ItemStack(Femtocraft.itemIonicChunk()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.ghastTear), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemFungalSpores()),
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Item.netherStalkSeeds),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemNerveCluster()),
//                    new ItemStack(Femtocraft.itemOrganometallicPlate()),
//                    null, null, null, null, null, null, null}, 1,
//                    new ItemStack(Item.spiderEye), EnumTechLevel.MICRO,
//                    FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMorphicChannel()),
//                    new ItemStack(Femtocraft.itemMicroCrystal()), null, null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.blazePowder), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemMetalComposite()),
//                    new ItemStack(Femtocraft.itemConductiveAlloy()), null,
//                    null, null, null, null, null, null}, 1, new ItemStack(
//                    Item.emerald), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null, null, null}, 1, new ItemStack(
//                    Item.carrot), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null,
//                    new ItemStack(Femtocraft.itemReplicatingMaterial()),
//                    new ItemStack(Femtocraft.itemFibrousStrand()), null,
//                    null, null, null}, 1, new ItemStack(Item.potato),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(
//                    new ItemStack[]{null, null, null, null, null, null,
//                            new ItemStack(Femtocraft.itemFibrousStrand()),
//                            null, null}, 1,
//                    new ItemStack(Item.bakedPotato), EnumTechLevel.MICRO,
//                    FemtocraftTechnologies.BASIC_CHEMISTRY
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null,
//                    null, null,
//                    new ItemStack(Femtocraft.itemSynthesizedFiber()), null,
//                    null, null, null, null}, 1, new ItemStack(
//                    Item.poisonousPotato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY));
//            addDecompositionRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Item.egg), new ItemStack(Item.sugar, 2),
//                    new ItemStack(Item.wheat, 3), null, null, null, null,
//                    null, null}, 1, new ItemStack(Item.cake),
//                    EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES
//            ));
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Femtocraft.itemHardenedBulb(), 64),
//                    new ItemStack(Femtocraft.itemOrganometallicPlate(), 64),
//                    new ItemStack(Femtocraft.itemHardenedBulb(), 64),
//                    new ItemStack(Femtocraft.itemOrganometallicPlate(), 64),
//                    new ItemStack(Item.diamond, 64),
//                    new ItemStack(Femtocraft.itemOrganometallicPlate(), 64),
//                    new ItemStack(Femtocraft.itemHardenedBulb(), 64),
//                    new ItemStack(Femtocraft.itemOrganometallicPlate(), 64),
//                    new ItemStack(Femtocraft.itemHardenedBulb(), 64)}, 1,
//                    new ItemStack(Item.netherStar), EnumTechLevel.MICRO,
//                    FemtocraftTechnologies.NETHER_STAR_FABRICATION
//            ));
//        } catch (AssemblerRecipeFoundException e) {
//            Femtocraft.log(Level.SEVERE, e.errMsg);
//            Femtocraft.log(Level.SEVERE,
//                    "Femtocraft failed to load Micro-tier Assembler Recipes!");
//        }
//    }
//
//    private void registerMacroDecompositionRecipes() {
//
//    }
//
//    public void registerDefaultRecipes() {
//        Femtocraft.log(Level.INFO, "Scraping Minecraft recipe registries for assembler recipe mappings.");
//
//        if (!ard.shouldRegister()) {
//            Femtocraft.log(Level.INFO, "Database already exists.  " +
//                                       "Skipping item registration.");
//            return;
//        }
//
//
//        // Does not use Ore Dictionary values - this is why things like crafting
//        // tables don't work.
//
//        Femtocraft
//                .log(Level.WARNING,
//                        "Registering assembler recipes from Vanilla Minecraft's Crafting Manager.\t This may take " +
//                        "awhile ._.");
//
//        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
//
//        for (int i = 0; i < 4; ++i) {
//            if (i == 0) {
//                Femtocraft
//                        .log(Level.WARNING,
//                                "Registering shaped recipes from Vanilla Minecraft's Crafting Manager.");
//            } else if (i == 1) {
//                Femtocraft
//                        .log(Level.WARNING,
//                                "Registering shaped ore recipes from Forge.");
//            } else if (i == 2) {
//                Femtocraft
//                        .log(Level.WARNING,
//                                "Registering shapeless recipes from Vanilla Minecraft's Crafting Manager.");
//            } else if (i == 3) {
//                Femtocraft.log(Level.WARNING,
//                        "Registering shapeless ore recipes from Forge.");
//            }
//
//
//            for (IRecipe recipe : recipes) {
//                if (getRecipe(recipe.getRecipeOutput()) != null) {
//                    Femtocraft.log(Level.CONFIG,
//                            "Assembler recipe already found for "
//                            + recipe.getRecipeOutput().getDisplayName()
//                            + "."
//                    );
//                    continue;
//                }
//
//                if (i == 0 && recipe instanceof ShapedRecipes) {
//                    // When I figure out how to do the other recipes...WITHOUT
//                    // iterating through every conceivable combination of items and
//                    // damages in the scope of minecraft
//                    // It will go here.
//
//                    ShapedRecipes sr = (ShapedRecipes) recipe;
//
//                    Femtocraft.log(Level.CONFIG,
//                            "Attempting to register shaped assembler recipe for "
//                            + sr.getRecipeOutput().getDisplayName() + "."
//                    );
//                    boolean valid = registerShapedRecipe(sr.recipeItems,
//                            sr.getRecipeOutput(), sr.recipeWidth, sr.recipeHeight);
//                    if (!valid) {
//                        Femtocraft.log(Level.WARNING,
//                                "Failed to register shaped assembler recipe for "
//                                + sr.getRecipeOutput().getDisplayName() + "!"
//                        );
//                    } else {
//                        Femtocraft.log(Level.CONFIG,
//                                "Loaded Vanilla Minecraft shaped recipe as assembler recipe for "
//                                + sr.getRecipeOutput().getDisplayName() + "."
//                        );
//                    }
//                } else if (i == 1 && recipe instanceof ShapedOreRecipe) {
//                    ShapedOreRecipe orecipe = (ShapedOreRecipe) recipe;
//
//                    Femtocraft.log(Level.CONFIG,
//                            "Attempting to register shaped assembler recipe for "
//                            + orecipe.getRecipeOutput().getDisplayName() + "."
//                    );
//                    // Hacky hacky hacky
//                    // They should at least have accessors for goodness sake
//                    int width = 0, height = 0;
//                    try {
//                        // Width
//                        {
//                            Field width_field = ShapedOreRecipe.class
//                                    .getDeclaredField("width");
//                            boolean prev = width_field.isAccessible();
//                            if (!prev) {
//                                width_field.setAccessible(true);
//                            }
//                            width = width_field.getInt(orecipe);
//                            if (!prev) {
//                                width_field.setAccessible(prev);
//                            }
//                        }
//                        // Height
//                        {
//                            Field height_field = ShapedOreRecipe.class
//                                    .getDeclaredField("height");
//                            boolean prev = height_field.isAccessible();
//                            if (!prev) {
//                                height_field.setAccessible(true);
//                            }
//                            height = height_field.getInt(orecipe);
//                            if (!prev) {
//                                height_field.setAccessible(prev);
//                            }
//                        }
//                    } catch (SecurityException e) {
//                        e.printStackTrace();
//                    } catch (NoSuchFieldException e) {
//                        e.printStackTrace();
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//
//                    boolean valid = registerShapedOreRecipe(orecipe.getInput(),
//                            orecipe.getRecipeOutput(), width, height);
//                    if (!valid) {
//                        Femtocraft.log(Level.WARNING,
//                                "Failed to register shaped assembler recipe for "
//                                + orecipe.getRecipeOutput().getDisplayName()
//                                + "!"
//                        );
//                    } else {
//                        Femtocraft.log(Level.CONFIG,
//                                "LoadedForge shaped ore recipe as assembler recipe for "
//                                + orecipe.getRecipeOutput().getDisplayName()
//                                + "."
//                        );
//                    }
//                } else if (i == 2 && recipe instanceof ShapelessRecipes) {
//                    if (getRecipe(recipe.getRecipeOutput()) != null) {
//                        Femtocraft.log(Level.CONFIG,
//                                "Assembler recipe already found for "
//                                + recipe.getRecipeOutput().getDisplayName()
//                                + "."
//                        );
//                        continue;
//                    }
//
//                    Femtocraft.log(Level.CONFIG,
//                            "Attempting to register shapeless assembler recipe for "
//                            + recipe.getRecipeOutput().getDisplayName() + "."
//                    );
//
//                    boolean valid = registerShapelessRecipe(
//                            ((ShapelessRecipes) recipe)
//                                    .recipeItems,
//                            recipe.getRecipeOutput());
//
//                    if (!valid) {
//                        Femtocraft.log(Level.WARNING,
//                                "Failed to register shapeless assembler recipe for "
//                                + recipe.getRecipeOutput().getDisplayName()
//                                + "!"
//                        );
//                        Femtocraft
//                                .log(Level.WARNING,
//                                        "I have no clue how this would happen...as the search space is literally " +
//                                        "thousands of configurations.  Sorry for the wait.");
//                    } else {
//                        Femtocraft.log(Level.CONFIG,
//                                "Loaded Vanilla Minecraft shapeless recipe as assembler recipe for + "
//                                + recipe.getRecipeOutput().getDisplayName()
//                                + "."
//                        );
//                    }
//                } else if (i == 3 && recipe instanceof ShapelessOreRecipe) {
//                    if (getRecipe(recipe.getRecipeOutput()) != null) {
//                        Femtocraft.log(Level.CONFIG,
//                                "Assembler recipe already found for "
//                                + recipe.getRecipeOutput().getDisplayName()
//                                + "."
//                        );
//                        continue;
//                    }
//
//                    Femtocraft.log(Level.CONFIG,
//                            "Attempting to register shapeless assembler recipe for "
//                            + recipe.getRecipeOutput().getDisplayName() + "."
//                    );
//
//                    boolean valid = registerShapelessOreRecipe((
//                                    (ShapelessOreRecipe) recipe).getInput(),
//                            recipe.getRecipeOutput());
//
//                    if (!valid) {
//                        Femtocraft.log(Level.WARNING,
//                                "Failed to register shapeless ore assembler recipe for "
//                                + recipe.getRecipeOutput().getDisplayName()
//                                + "!"
//                        );
//                        Femtocraft
//                                .log(Level.WARNING,
//                                        "I have no clue how this would happen...as the search space is literally " +
//                                        "thousands of configurations.  Sorry for the wait.");
//                    } else {
//                        Femtocraft.log(Level.CONFIG,
//                                "Loaded Forge shapeless ore recipe as assembler recipe for + "
//                                + recipe.getRecipeOutput().getDisplayName()
//                                + "."
//                        );
//                    }
//                }
//            }
//        }
//        Femtocraft.log(Level.INFO, "Finished mapping Minecraft recipes to assembler recipes.");
//    }
//
//    private boolean registerShapedOreRecipe(Object[] recipeInput,
//                                            ItemStack recipeOutput, int width, int height) {
//        boolean done = false;
//        int xOffset = 0;
//        int yOffset = 0;
//        ItemStack[] input = new ItemStack[9];
//        AssemblerRecipe recipe = new AssemblerRecipe(input, 0,
//                recipeOutput.copy(),
//                EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES);
//
//        if (recipe.output.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
//            recipe.output.setItemDamage(0);
//        }
//
//        while ((!done) && (xOffset < 3) && (yOffset < 3)) {
//            Arrays.fill(input, null);
//            for (int i = 0; (i < recipeInput.length) && (i < 9); i++) {
//                try {
//                    ItemStack item;
//                    Object obj = recipeInput[i];
//
//                    if (obj instanceof ArrayList<?>) {
//                        try {
//                            item = ((ArrayList<ItemStack>) obj).get(0);
//                        } catch (IndexOutOfBoundsException exc) {
//                            Femtocraft.log(Level.SEVERE,
//                                    "Ore recipe with nothing registered in " +
//                                    "ore dictionary for " + recipe
//                                            .output.getDisplayName() + ".");
//                            return false;
//                        }
//                    } else {
//                        item = (ItemStack) obj;
//                    }
//                    input[((i + xOffset) % width) + 3
//                                                    * (yOffset + ((i + xOffset) / width))] = item == null ? null
////                            : item.copy();
//                            : new ItemStack(item.itemID, 1, item.getItemDamage());
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    if (++xOffset >= 3) {
//                        xOffset = 0;
//                        ++yOffset;
//                    }
//
//                }
//            }
//
//            for (ItemStack i : input) {
//                if (i == null) {
//                    continue;
//                }
//
//                if (i.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
//                    i.setItemDamage(0);
//                }
//            }
//
//            try {
//                addReversableRecipe(recipe);
//                done = true;
//            } catch (AssemblerRecipeFoundException e) {
//                // Attempt to offset, while staying inside crafting grid
//                if ((++xOffset) >= 3) {
//                    xOffset = 0;
//                    ++yOffset;
//                }
//                done = false;
//            }
//        }
//
//        return done;
//    }
//
//    private boolean registerShapedRecipe(ItemStack[] recipeItems,
//                                         ItemStack recipeOutput, int recipeWidth, int recipeHeight) {
//        boolean done = false;
//        int xoffset = 0;
//        int yoffset = 0;
//        ItemStack[] input = new ItemStack[9];
//        AssemblerRecipe recipe = new AssemblerRecipe(input, 0,
//                recipeOutput.copy(), EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES);
//
//        while ((!done) && ((xoffset + recipeWidth) <= 3)
//               && ((yoffset + recipeHeight) <= 3)) {
//            Arrays.fill(input, null);
//            for (int i = 0; (i < recipeItems.length) && (i < 9); i++) {
//                ItemStack item = recipeItems[i];
//                input[((i + xoffset) % recipeWidth) + 3
//                                                      * (yoffset + ((i + xoffset) / recipeWidth))] = item == null ?
// null
////                        : item.copy();
//                        : new ItemStack(item.itemID, 1, item.getItemDamage());
//            }
//
//            try {
//                addReversableRecipe(recipe);
//                done = true;
//            } catch (AssemblerRecipeFoundException e) {
//                // Attempt to offset, while staying inside crafting grid
//                if ((++xoffset + recipeWidth) > 3) {
//                    xoffset = 0;
//                    ++yoffset;
//                }
//                done = false;
//            }
//        }
//
//        return done;
//    }
//
//    private boolean registerShapelessOreRecipe(List recipeItems,
//                                               ItemStack recipeOutput) {
//        boolean valid = false;
//        int[] slots = new int[recipeItems.size()];
//        ItemStack[] input = new ItemStack[9];
//        AssemblerRecipe recipe = new AssemblerRecipe(input, 0,
//                recipeOutput.copy(), EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES);
//
//        long timeStart = System.currentTimeMillis();
//
//        if (recipe.output.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
//            recipe.output.setItemDamage(0);
//        }
//
//        // Exhaustively find a configuration that works - this should NEVER have
//        // to go the full distance
//        // but I don't want to half-ass the attempt in case there are MANY
//        // collisions
//        int offset = 0;
//        while (!valid && ((offset + recipeItems.size()) <= 9)) {
//            for (int i = 0; i < slots.length; ++i) {
//                slots[i] = i;
//            }
//
//            while (!valid) {
//                Arrays.fill(input, null);
//
//                for (int i = 0; (i < slots.length) && (i < 9); ++i) {
//                    ItemStack item;
//                    Object obj = recipeItems.get(i);
//
//                    if (obj instanceof ArrayList<?>) {
//                        try {
//                            item = ((ArrayList<ItemStack>) obj).get(0);
//                        } catch (IndexOutOfBoundsException exc) {
//                            Femtocraft.log(Level.SEVERE,
//                                    "Ore recipe with nothing registered in " +
//                                    "ore dictionary for " + recipe
//                                            .output.getDisplayName() + ".");
//                            return false;
//                        }
//                    } else {
//                        item = (ItemStack) obj;
//                    }
//
//                    input[slots[i] + offset] = item == null ? null : item
//                            .copy();
//                }
//
//                for (ItemStack i : input) {
//                    if (i == null) {
//                        continue;
//                    }
//
//                    if (i.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
//                        i.setItemDamage(0);
//                    }
//                }
//
//
//                try {
//                    if ((System.currentTimeMillis() - timeStart) >
//                        shapelessPermuteTimeMillis) {
//                        return false;
//                    }
//                    addReversableRecipe(recipe);
//                    valid = true;
//                } catch (AssemblerRecipeFoundException e) {
//                    // Permute the slots
//                    slots = permute(slots);
//
//                    valid = false;
//                }
//            }
//        }
//
//        return valid;
//    }
//
//    private boolean registerShapelessRecipe(List recipeItems,
//                                            ItemStack recipeOutput) {
//        boolean valid = false;
//        int[] slots = new int[recipeItems.size()];
//        ItemStack[] input = new ItemStack[9];
//        AssemblerRecipe recipe = new AssemblerRecipe(input, 0,
//                recipeOutput.copy(), EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES);
//
//        long timeStart = System.currentTimeMillis();
//
//        // Exhaustively find a configuration that works - this should NEVER have
//        // to go the full distance
//        // but I don't want to half-ass the attempt in case there are MANY
//        // collisions
//        int offset = 0;
//        while (!valid && ((offset + recipeItems.size()) <= 9)) {
//            for (int i = 0; i < slots.length; ++i) {
//                slots[i] = i;
//            }
//
//            while (!valid) {
//                Arrays.fill(input, null);
//
//                for (int i = 0; (i < slots.length) && (i < 9); ++i) {
//                    ItemStack item = (ItemStack) recipeItems.get(i);
//                    input[slots[i] + offset] = item == null ? null : item
//                            .copy();
//                }
//
//                try {
//                    if ((System.currentTimeMillis() - timeStart) >
//                        shapelessPermuteTimeMillis) {
//                        return false;
//                    }
//                    addReversableRecipe(recipe);
//                    valid = true;
//                } catch (AssemblerRecipeFoundException e) {
//                    // Permute the slots
//                    slots = permute(slots);
//
//                    valid = false;
//                }
//            }
//        }
//
//        return valid;
//    }
//
//    private int[] permute(int[] slots) {
//        int k = findHighestK(slots);
//        int i = findHigherI(slots, k);
//
//        // Switch k and i
//        int prev = slots[k];
//        slots[k] = slots[i];
//        slots[i] = prev;
//
//        // Reverse ordering of k+1 to end
//        int remaining = (int) Math.ceil((slots.length - k + 1) / 2.);
//        for (int r = k + 1, n = 0; (r < slots.length) && (n < remaining); ++r, ++n) {
//            int pr = slots[r];
//            slots[r] = slots[slots.length - n - 1];
//            slots[slots.length - n - 1] = pr;
//        }
//
//        return slots;
//    }
//
//    private int findHighestK(int[] slots) {
//        int ret = 0;
//        for (int i = 0; i < slots.length - 1; ++i) {
//            if ((slots[i] < slots[i + 1]) && (ret < i)) {
//                ret = i;
//            }
//        }
//        return ret;
//    }
//
//    private int findHigherI(int[] slots, int k) {
//        int ret = 0;
//
//        for (int i = 0; i < slots.length; ++i) {
//            if ((slots[k] < slots[i]) && (ret < i)) {
//                ret = i;
//            }
//        }
//        return ret;
//    }
//
//    private void registerFemtocraftAssemblerRecipes() {
//        try {
//            //Micro
//            addDecompositionRecipe(new AssemblerRecipe(new ItemStack[]{
//                    new ItemStack(Item.paper, 3), null, null, null, null, null,
//                    null, null, null}, 0, new ItemStack(
//                    Femtocraft.itemPaperSchematic()), EnumTechLevel.MACRO, FemtocraftTechnologies.ALGORITHMS));
//
//            addRecompositionRecipe(new AssemblerRecipe(
//                    new ItemStack[]{
//                            new ItemStack(Femtocraft.itemSpoolGold()),
//                            new ItemStack(Femtocraft.itemSpoolGold()),
//                            new ItemStack(Femtocraft.itemSpoolGold()),
//                            new ItemStack(Femtocraft.itemConductivePowder()),
//                            new ItemStack(Femtocraft.itemConductivePowder()),
//                            new ItemStack(Femtocraft.itemConductivePowder()),
//                            new ItemStack(Block.planks, 1,
//                                    OreDictionary.WILDCARD_VALUE),
//                            new ItemStack(Block.planks, 1,
//                                    OreDictionary.WILDCARD_VALUE),
//                            new ItemStack(Block.planks, 1,
//                                    OreDictionary.WILDCARD_VALUE)}, 0,
//                    new ItemStack(Femtocraft.itemMicrochip(), 6),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CIRCUITS
//            ));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new
//                    ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.blockMicroCube()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.itemMicrochip()),
//                    new ItemStack(Femtocraft.blockMicroChargingCoil()),
//                    new ItemStack(Femtocraft.itemMicrochip()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.blockMicroCube()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium())}, 0,
//                    new ItemStack(Femtocraft.blockMicroChargingCapacitor()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.POTENTIAL_HARVESTING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new
//                    ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Item.ingotGold), new ItemStack(Femtocraft.itemIngotTemperedTitanium()), null,
//                    new ItemStack(Femtocraft.itemVacuumCore()), null,
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Item.ingotGold), new ItemStack(Femtocraft.itemIngotTemperedTitanium())}, 0,
//                    new ItemStack(Femtocraft.blockVacuumTube(), 16),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.VACUUM_TUBES));
//
////            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new
////                    ItemStack(Femtocraft.itemIngotTemperedTitanium),
////                    new ItemStack(Femtocraft.itemIngotPlatinum),
////                    new ItemStack(Femtocraft
////                            .itemIngotTemperedTitanium), null,
////                    new ItemStack(Femtocraft.itemVacuumCore), null,
////                    new ItemStack(Femtocraft.itemIngotTemperedTitanium),
////                    new ItemStack(Femtocraft.itemIngotPlatinum),
////                    new ItemStack(Femtocraft
////                            .itemIngotTemperedTitanium)}, 0,
////                    new ItemStack(Femtocraft.blockVacuumTubeHub, 16),
////                    EnumTechLevel.MICRO, null));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherQuartz),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.netherQuartz),
//                    new ItemStack(Femtocraft.itemDopedBoard()), new ItemStack(Femtocraft.itemMicrochip()),
//                    new ItemStack(Femtocraft.itemDopedBoard()), new ItemStack(Item.netherQuartz),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.netherQuartz)}, 0,
//                    new ItemStack(Femtocraft.itemNanochip(), 2),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.comparator),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.redstone),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.redstone),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.comparator)}, 0,
//                    new ItemStack(Femtocraft.itemNanoCalculator()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemIngotFarenite()),
//                    new ItemStack(Item.redstone), new ItemStack(Item.comparator), new ItemStack(Item.redstone),
//                    new ItemStack(Femtocraft.itemNanochip()), new ItemStack(Block.torchRedstoneActive),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.redstone),
//                    new ItemStack(Item.comparator)}, 0,
//                    new ItemStack(Femtocraft.itemNanoRegulator()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.redstoneRepeater),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.redstoneRepeater),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.comparator),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Item.redstoneRepeater)}, 0,
//                    new ItemStack(Femtocraft.itemNanoSimulator()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherQuartz), null, null,
//                    new ItemStack(Femtocraft.itemIngotFarenite()), null, null, new ItemStack(Item.netherQuartz), null,
//                    null}, 0,
//                    new ItemStack(Femtocraft.itemFluidicConductor()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft
//                    .itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemNanoRegulator
//                    ()),
//                    new ItemStack(Femtocraft.itemMicroPlating()), new ItemStack(Femtocraft.itemNanoRegulator()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium())}, 0,
//                    new ItemStack(Femtocraft.itemNanoPlating(), 3),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.ARTIFICIAL_MATERIALS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoCalculator()),
//                    new ItemStack(Femtocraft.itemMicroLogicCore()), new ItemStack(Femtocraft.itemNanoCalculator()),
//                    null,
//                    null, null, null, null, null}, 0,
//                    new ItemStack(Femtocraft.itemBasicAICore()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.ADVANCED_PROGRAMMING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft
// .itemNanoSimulator())
//                    , null, new ItemStack(Femtocraft.itemNanoCalculator()), new ItemStack(Femtocraft
// .itemBasicAICore()),
//                    new ItemStack(Femtocraft.itemNanoCalculator()), null, new ItemStack(Femtocraft
// .itemNanoSimulator()),
//                    null}, 0,
//                    new ItemStack(Femtocraft.itemLearningCore()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.PATTERN_RECOGNITION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoRegulator()),
//                    null, null, new ItemStack(Femtocraft.itemBasicAICore()), null, null,
//                    new ItemStack(Femtocraft.itemNanoCalculator()), null, null}, 0,
//                    new ItemStack(Femtocraft.itemSchedulerCore()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.WORKLOAD_SCHEDULING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoRegulator()),
//                    null, null, null, new ItemStack(Femtocraft.itemBasicAICore()), null, null, null,
//                    new ItemStack(Femtocraft.itemNanoRegulator())}, 0,
//                    new ItemStack(Femtocraft.itemManagerCore()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.RESOURCE_OPTIMIZATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemFluidicConductor()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemFluidicConductor()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemFluidicConductor()), new ItemStack(Femtocraft.itemNanoPlating())}, 0,
//                    new ItemStack(Femtocraft.itemNanoCoil(), 6),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherQuartz),
//                    new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz),
//                    new ItemStack(Femtocraft.itemNanoCoil()), new ItemStack(Femtocraft.itemNanoCoil()),
//                    new ItemStack(Femtocraft.itemNanoCoil()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemNanoPlating())}, 0,
//                    new ItemStack(Femtocraft.blockNanoCable(), 8),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft
//                    .itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.itemManagerCore()), new ItemStack(Femtocraft.itemSchedulerCore()),
//                    new ItemStack(Femtocraft.blockMicroFurnaceUnlit()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemNanoPlating()
//            )}, 0,
//                    new ItemStack(Femtocraft.blockNanoInnervatorUnlit()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.KINETIC_DISSOCIATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemManagerCore
// ()),
//                    new ItemStack(Femtocraft.blockMicroDeconstructor()), new ItemStack(Femtocraft.itemSchedulerCore
// ()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemIngotTemperedTitanium
// ()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium())}, 0,
//                    new ItemStack(Femtocraft.blockNanoDismantler()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.ATOMIC_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft
//                    .itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemSchedulerCore()),
//                    new ItemStack(Femtocraft.blockMicroReconstructor()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()), new ItemStack(Femtocraft.itemManagerCore()
//            )}, 0,
//                    new ItemStack(Femtocraft.blockNanoFabricator()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.ATOMIC_MANIPULATION));
//
////            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoRegulator),
//// null, null, new ItemStack(Femtocraft.blockVacuumTube), null, null, new ItemStack(Femtocraft.itemLearningCore),
//// null, null}, 0,
////                    new ItemStack(Femtocraft.blockDiscriminatingVacuumTube, 2),
////                    EnumTechLevel.MICRO, null));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemManagerCore()), new ItemStack(Femtocraft.itemSchedulerCore()),
//                    new ItemStack(Femtocraft.itemManagerCore()), new ItemStack(Femtocraft.blockNanoCable()),
//                    new ItemStack(Femtocraft.itemNanoCoil()), new ItemStack(Femtocraft.blockNanoCable())}, 0,
//                    new ItemStack(Femtocraft.blockCryoEndothermalChargingBase()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemNanoCoil()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemIngotFarenite()),
//                    new ItemStack(Femtocraft.itemNanochip()), new ItemStack(Femtocraft.itemIngotFarenite()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemNanoCoil()),
//                    new ItemStack(Femtocraft.itemNanoPlating())}, 0,
//                    new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil()),
//                    EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING));
//
//            //Nano
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoRegulator()),
//                    new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemNanoRegulator()),
//                    new ItemStack(Item.netherQuartz), new ItemStack(Item.pocketSundial),
//                    new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemNanoCalculator()),
//                    new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemNanoCalculator())}, 0,
//                    new ItemStack(Femtocraft.itemTemporalResonator()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoRegulator()),
//                    new ItemStack(Item.enderPearl), new ItemStack(Femtocraft.itemNanoRegulator()),
//                    new ItemStack(Item.enderPearl), new ItemStack(Item.compass), new ItemStack(Item.enderPearl),
//                    new ItemStack(Femtocraft.itemNanoSimulator()), new ItemStack(Item.enderPearl),
//                    new ItemStack(Femtocraft.itemNanoSimulator())}, 0,
//                    new ItemStack(Femtocraft.itemDimensionalMonopole()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.ingotIron),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Item.ingotIron),
//                    new ItemStack(Item.ingotIron), new ItemStack(Femtocraft.itemTemporalResonator()),
//                    new ItemStack(Item.ingotIron), new ItemStack(Item.ingotIron),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Item.ingotIron)}, 0,
//                    new ItemStack(Femtocraft.itemSelfFulfillingOracle()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemIngotPlatinum()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemIngotPlatinum()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemDimensionalMonopole
// ()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemIngotPlatinum()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemIngotPlatinum())}, 0,
//                    new ItemStack(Femtocraft.itemCrossDimensionalCommunicator()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoCalculator()),
//                    new ItemStack(Femtocraft.itemSelfFulfillingOracle()), new ItemStack(Femtocraft.itemNanoCalculator
//                    ()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.blockNanoFabricator()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemNanoRegulator()),
//                    new ItemStack(Femtocraft.itemTemporalResonator()), new ItemStack(Femtocraft.itemNanoRegulator())
//            }, 0,
//                    new ItemStack(Femtocraft.blockNanoHorologe()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoSimulator()),
//                    new ItemStack(Femtocraft.itemCrossDimensionalCommunicator()),
//                    new ItemStack(Femtocraft.itemNanoSimulator()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.blockNanoFabricator()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemNanoRegulator()), new ItemStack(Femtocraft.itemDimensionalMonopole
// ()),
//                    new ItemStack(Femtocraft.itemNanoRegulator())}, 0,
//                    new ItemStack(Femtocraft.blockNanoEnmesher()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.DIMENSIONAL_BRAIDING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemIngotPlatinum()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemIngotPlatinum()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemManagerCore()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemIngotPlatinum()),
//                    new ItemStack(Femtocraft.itemIngotPlatinum()), new ItemStack(Femtocraft.itemIngotPlatinum())},
//                    0,
//                    new ItemStack(Femtocraft.itemDigitalSchematic(), 8),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.DIGITIZED_WORKLOADS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.blockMicroCable()),
//                    new ItemStack(Femtocraft.itemFluidicConductor()), new ItemStack(Femtocraft.blockNanoCable()),
// null,
//                    null, null, null, null, null}, 0,
//                    new ItemStack(Femtocraft.blockOrbitalEqualizer()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.POTENTIALITY_TRANSFORMATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.blockMicroCube()),
//                    new ItemStack(Femtocraft.itemNanoRegulator()), new ItemStack(Femtocraft.blockMicroCube()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.blockMicroCube()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.blockMicroCube()),
//                    new ItemStack(Femtocraft.itemNanoCalculator()), new ItemStack(Femtocraft.blockMicroCube())}, 0,
//                    new ItemStack(Femtocraft.blockNanoCubeFrame()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.INDUSTRIAL_STORAGE));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.blockNanoCable()),
//                    new ItemStack(Femtocraft.blockNanoCable()), new ItemStack(Femtocraft.itemManagerCore()),
//                    new ItemStack(Femtocraft.itemFluidicConductor()), new ItemStack(Femtocraft.blockNanoCubeFrame()),
//                    new ItemStack(Femtocraft.itemFluidicConductor()), new ItemStack(Femtocraft.itemManagerCore()),
//                    new ItemStack(Femtocraft.blockNanoCable()), new ItemStack(Femtocraft.blockNanoCable())}, 0,
//                    new ItemStack(Femtocraft.blockNanoCubePort()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.INDUSTRIAL_STORAGE));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemFluidicConductor()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemFluidicConductor()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemNanoPlating()), new ItemStack(Femtocraft.itemNanochip())}, 0,
//                    new ItemStack(Femtocraft.itemFissionReactorPlating()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanoRegulator()),
//                    null, null, new ItemStack(Femtocraft.itemFissionReactorPlating()), null, null,
//                    new ItemStack(Femtocraft.itemNanoRegulator()), null, null}, 0,
//                    new ItemStack(Femtocraft.blockFissionReactorHousing()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemLearningCore()),
//                    new ItemStack(Femtocraft.blockFissionReactorHousing()),
//                    new ItemStack(Femtocraft.itemSchedulerCore())
//                    , new ItemStack(Femtocraft.blockFissionReactorHousing()), new ItemStack(Item.diamond),
//                    new ItemStack(Femtocraft.blockFissionReactorHousing()), new ItemStack(Femtocraft
// .itemManagerCore()),
//                    new ItemStack(Femtocraft.blockFissionReactorHousing()), new ItemStack(Femtocraft.itemManagerCore
//                    ())}, 0,
//                    new ItemStack(Femtocraft.blockFissionReactorCore()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemFissionReactorPlating()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.blockOrbitalEqualizer()), new ItemStack(Block.chest),
//                    new ItemStack(Femtocraft.blockOrbitalEqualizer()), new ItemStack(Femtocraft.itemNanochip()),
//                    new ItemStack(Femtocraft.itemFissionReactorPlating()), new ItemStack(Femtocraft.itemNanochip())
// }, 0,
//                    new ItemStack(Femtocraft.blockDecontaminationChamber()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemIngotFarenite()),
//                    null, new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Femtocraft.itemIngotThorium()),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), new ItemStack(Femtocraft.itemIngotThorium()),
//                    new ItemStack(Femtocraft.itemIngotFarenite()), null, new ItemStack(Femtocraft.itemIngotFarenite
// ())},
//                    0, new ItemStack(Femtocraft.itemIngotThFaSalt(), 2), EnumTechLevel.NANO,
//                    FemtocraftTechnologies.THORIUM_FISSIBILITY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.eyeOfEnder),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Item.eyeOfEnder),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Item.netherQuartz),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Item.eyeOfEnder),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Item.eyeOfEnder)}, 0,
//                    new ItemStack(Femtocraft.itemMinosGate(), 2),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new
//                    ItemStack(Item.magmaCream), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.magmaCream), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.magmaCream), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.magmaCream)}, 0,
//                    new ItemStack(Femtocraft.itemCharosGate(), 2),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new
//                    ItemStack(Item.fireballCharge), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.fireballCharge), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.netherQuartz), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.fireballCharge), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.fireballCharge)}, 0,
//                    new ItemStack(Femtocraft.itemCerberusGate(), 2),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY));
//
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft.itemMinosGate()),
//                    null, new ItemStack(Femtocraft.itemCharosGate()), new ItemStack(Item.ghastTear),
//                    new ItemStack(Femtocraft.itemCharosGate()), null, new ItemStack(Femtocraft.itemMinosGate()),
//                    null}, 0,
//                    new ItemStack(Femtocraft.itemErinyesCircuit(), 3),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft.itemCerberusGate
// ()),
//                    null, new ItemStack(Femtocraft.itemMinosGate()), new ItemStack(Item.book),
//                    new ItemStack(Femtocraft.itemCharosGate()), null, new ItemStack(Femtocraft.itemMinosGate()), null
//            }, 0,
//                    new ItemStack(Femtocraft.itemMinervaComplex()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.slimeBall), null,
//                    new ItemStack(Item.slimeBall), new ItemStack(Femtocraft.itemMinosGate()),
//                    new ItemStack(Block.pistonStickyBase), new ItemStack(Femtocraft.itemCerberusGate()),
//                    new ItemStack(Femtocraft.itemErinyesCircuit()), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Femtocraft.itemErinyesCircuit())}, 0,
//                    new ItemStack(Femtocraft.itemAtlasMount()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_ROBOTICS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemErinyesCircuit()),
//                    new ItemStack(Femtocraft.itemMinervaComplex()), new ItemStack(Femtocraft.itemErinyesCircuit()),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Item.feather),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Femtocraft.itemErinyesCircuit()),
//                    new ItemStack(Femtocraft.itemMinervaComplex()), new ItemStack(Femtocraft.itemErinyesCircuit())
// }, 0,
//                    new ItemStack(Femtocraft.itemHermesBus()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.gunpowder),
//                    new ItemStack(Item.ghastTear), new ItemStack(Item.gunpowder),
//                    new ItemStack(Femtocraft.itemMinervaComplex()), new ItemStack(Femtocraft.itemPandoraCube()),
//                    new ItemStack(Femtocraft.itemMinervaComplex()), new ItemStack(Femtocraft.itemErinyesCircuit()),
//                    new ItemStack(Block.pistonBase), new ItemStack(Femtocraft.itemErinyesCircuit())}, 0,
//                    new ItemStack(Femtocraft.itemHerculesDrive()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_ROBOTICS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemMinervaComplex()),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Femtocraft.itemMinervaComplex()),
//                    new ItemStack(Femtocraft.itemInfallibleEstimator()), new ItemStack(Item.comparator),
//                    new ItemStack(Femtocraft.itemPanLocationalComputer()),
//                    new ItemStack(Femtocraft.itemErinyesCircuit())
//                    , new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Femtocraft.itemErinyesCircuit())
// }, 0,
//                    new ItemStack(Femtocraft.itemOrpheusProcessor()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.diamond),
//                    new ItemStack(Femtocraft.itemErinyesCircuit()), new ItemStack(Item.diamond),
//                    new ItemStack(Femtocraft.itemHermesBus()), new ItemStack(Femtocraft.itemNanoPlating()),
//                    new ItemStack(Femtocraft.itemAtlasMount()), new ItemStack(Item.diamond),
//                    new ItemStack(Femtocraft.itemErinyesCircuit()), new ItemStack(Item.diamond)}, 0,
//                    new ItemStack(Femtocraft.itemFemtoPlating(), 2),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.ELEMENT_MANUFACTURING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemErinyesCircuit()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemErinyesCircuit()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.blockNanoInnervatorUnlit
// ()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemCerberusGate()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemCerberusGate())}, 0,
//                    new ItemStack(Femtocraft.blockFemtoImpulserUnlit()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_EXCITATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemOrpheusProcessor()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemHerculesDrive()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.blockNanoDismantler()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemCharosGate()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemCharosGate())}, 0,
//                    new ItemStack(Femtocraft.blockFemtoRepurposer()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemOrpheusProcessor()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemHerculesDrive()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.blockNanoFabricator()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemMinosGate()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemMinosGate())}, 0,
//                    new ItemStack(Femtocraft.blockFemtoCoagulator()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_MANIPULATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Item.netherrackBrick), new ItemStack(Femtocraft.itemIngotMalenite()),
//                    new ItemStack(Item.netherrackBrick), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemIngotMalenite()), new ItemStack(Item.netherrackBrick)}, 0,
//                    new ItemStack(Femtocraft.itemStyxValve()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemStyxValve()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemStyxValve()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemStyxValve()), new ItemStack(Femtocraft.itemFemtoPlating())}, 0,
//                    new ItemStack(Femtocraft.itemFemtoCoil(), 6),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.diamond),
//                    new ItemStack(Item.diamond), new ItemStack(Item.diamond), new ItemStack(Femtocraft
// .itemFemtoCoil())
//                    , new ItemStack(Femtocraft.itemFemtoCoil()), new ItemStack(Femtocraft.itemFemtoCoil()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemFemtoPlating())}, 0,
//                    new ItemStack(Femtocraft.blockFemtoCable(), 8),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemPandoraCube()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.blockCryoEndothermalChargingBase()), new ItemStack(Item.netherStar),
//                    new ItemStack(Femtocraft.blockCryoEndothermalChargingBase()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemPandoraCube()),
//                    new ItemStack(Femtocraft.itemFemtoPlating())}, 0,
//                    new ItemStack(Femtocraft.blockPhlegethonTunnelCore()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft
// .itemDimensionalMonopole())
//                    , new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemDimensionalMonopole()),
//                    new ItemStack(Item.eyeOfEnder), new ItemStack(Item.diamond), new ItemStack(Item.eyeOfEnder),
//                    new ItemStack(Femtocraft.itemDimensionalMonopole()), new ItemStack(Item.eyeOfEnder),
//                    new ItemStack(Femtocraft.itemDimensionalMonopole())}, 0,
//                    new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Item.netherrackBrick)}, 0,
//                    new ItemStack(Femtocraft.blockPhlegethonTunnelFrame()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Block.pistonBase), new ItemStack(Block.blockIron), new ItemStack(Block.pistonBase),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemFemtoPlating())}, 0,
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer()),
//                    EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION));
//
//            //Femto
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemHerculesDrive()),
//                    new ItemStack(Femtocraft.itemOrpheusProcessor()), new ItemStack(Femtocraft.itemHerculesDrive()),
//                    new ItemStack(Femtocraft.itemHermesBus()), new ItemStack(Item.netherStar),
//                    new ItemStack(Femtocraft.itemHermesBus()), new ItemStack(Femtocraft.itemMinervaComplex()),
//                    new ItemStack(Femtocraft.itemAtlasMount()), new ItemStack(Femtocraft.itemMinervaComplex())}, 0,
//                    new ItemStack(Femtocraft.itemQuantumSchematic(), 8),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.SPIN_RETENTION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemOrpheusProcessor()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemOrpheusProcessor()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.blockNanoHorologe()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemInfallibleEstimator()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemInfallibleEstimator())
//            }, 0,
//                    new ItemStack(Femtocraft.blockFemtoChronoshifter()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.TEMPORAL_THREADING));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemHerculesDrive()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemHerculesDrive()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.blockNanoEnmesher()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemOrpheusProcessor()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemOrpheusProcessor())
// }, 0,
//                    new ItemStack(Femtocraft.blockFemtoEntangler()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.DIMENSIONAL_SUPERPOSITIONS));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.blockNanoCable()),
//                    new ItemStack(Femtocraft.itemStyxValve()), new ItemStack(Femtocraft.blockFemtoCable()), null,
// null,
//                    null, null, null, null}, 0,
//                    new ItemStack(Femtocraft.blockNullEqualizer()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.SPONTANEOUS_GENERATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.blockFemtoCable()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.blockFemtoCable()), new ItemStack(Femtocraft.itemAtlasMount()),
//                    new ItemStack(Femtocraft.blockFemtoCable()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.blockFemtoCable()), new ItemStack(Femtocraft.itemFemtoPlating())}, 0,
//                    new ItemStack(Femtocraft.blockFemtoCubePort()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Femtocraft.itemHermesBus()),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Item.netherrackBrick)}, 0,
//                    new ItemStack(Femtocraft.blockFemtoCubeFrame()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemHermesBus()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemHermesBus()), new ItemStack(Femtocraft.itemCerberusGate()),
//                    new ItemStack(Femtocraft.itemHermesBus()), new ItemStack(Femtocraft.itemFemtoPlating()),
//                    new ItemStack(Femtocraft.itemHermesBus()), new ItemStack(Femtocraft.itemFemtoPlating())}, 0,
//                    new ItemStack(Femtocraft.blockFemtoCubeChassis()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.diamond),
//                    new ItemStack(Item.netherrackBrick), new ItemStack(Item.diamond), new ItemStack(Item.diamond),
//                    new ItemStack(Femtocraft.itemFemtoPlating()), new ItemStack(Item.diamond),
//                    new ItemStack(Item.diamond), new ItemStack(Item.netherrackBrick), new ItemStack(Item.diamond)}, 0,
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft
// .blockSisyphusStabilizer())
//                    , new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.netherStar),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer())}, 0,
//                    new ItemStack(Femtocraft.blockStellaratorCore()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemStellaratorPlating
// ()),
//                    new ItemStack(Block.glass), new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Block.glass), new ItemStack(Item.diamond), new ItemStack(Block.glass),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Block.glass),
//                    new ItemStack(Femtocraft.itemStellaratorPlating())}, 0,
//                    new ItemStack(Femtocraft.blockStellaratorFocus()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Block.netherBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.netherrackBrick)}, 0,
//                    new ItemStack(Femtocraft.blockStellaratorHousing()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.netherrackBrick)}, 0,
//                    new ItemStack(Femtocraft.blockStellaratorOpticalMaser()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemStellaratorPlating
// ()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.blazeRod),
//                    new ItemStack(Item.blazeRod), new ItemStack(Item.blazeRod),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating())}, 0,
//                    new ItemStack(Femtocraft.blockPlasmaConduit(), 8),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.blockPlasmaConduit()), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Item.blazeRod),
//                    new ItemStack(Block.dispenser), new ItemStack(Item.netherrackBrick),
//                    new ItemStack(Femtocraft.blockPlasmaConduit()), new ItemStack(Item.netherrackBrick)}, 0,
//                    new ItemStack(Femtocraft.blockPlasmaVent()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.blockPlasmaConduit()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Femtocraft.blockPlasmaConduit
// ()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Femtocraft.itemFemtoCoil()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Femtocraft.blockPlasmaConduit
// ()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()), new ItemStack(Femtocraft.blockPlasmaConduit()
//            )}, 0,
//                    new ItemStack(Femtocraft.blockPlasmaTurbine()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.ENERGY_CONVERSION));
//
//            addReversableRecipe(new AssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.itemStellaratorPlating
// ()),
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer()), new ItemStack(Block.blockDiamond),
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating()),
//                    new ItemStack(Femtocraft.blockSisyphusStabilizer()),
//                    new ItemStack(Femtocraft.itemStellaratorPlating())}, 0,
//                    new ItemStack(Femtocraft.blockPlasmaCondenser()),
//                    EnumTechLevel.FEMTO, FemtocraftTechnologies.MATTER_CONVERSION));
//
//        } catch (AssemblerRecipeFoundException e) {
//            Femtocraft.log(Level.SEVERE, e.errMsg);
//            Femtocraft.log(Level.SEVERE,
//                    "Femtocraft failed to load Femtocraft Assembler Recipes!");
//        }
//    }
//
//    private void testRecipes() {
//        AssemblerRecipe test = getRecipe(new ItemStack[]{null, null, null,
//                new ItemStack(Femtocraft.itemPlaneoid()),
//                new ItemStack(Femtocraft.itemRectangulon()),
//                new ItemStack(Femtocraft.itemPlaneoid()), null, null, null});
//        Femtocraft.log(Level.WARNING, "Recipe "
//                                      + (test != null ? "found" : "not found") + ".");
//        if (test != null) {
//            Femtocraft.log(
//                    Level.WARNING,
//                    "Output "
//                    + (test.output.isItemEqual(new ItemStack(
//                            Femtocraft.itemFlorite())) ? "matches"
//                            : "does not match") + "."
//            );
//        }
//
//        test = getRecipe(new ItemStack[]{null, null, null,
//                new ItemStack(Femtocraft.itemRectangulon()),
//                new ItemStack(Femtocraft.itemRectangulon()),
//                new ItemStack(Femtocraft.itemPlaneoid()), null, null, null});
//        Femtocraft.log(Level.WARNING, "Recipe "
//                                      + (test != null ? "found" : "not found") + ".");
//
//        test = getRecipe(new ItemStack(Femtocraft.itemFlorite()));
//        Femtocraft.log(Level.WARNING, "Recipe "
//                                      + (test != null ? "found" : "not found") + ".");
//    }
//
//    public AssemblerRecipe getRecipe(ItemStack[] input) {
//        ItemStack[] normal = normalizedInput(input);
//        if (normal == null) {
//            return null;
//        }
//        return ard.getRecipe(input);
////        return inputToRecipeMap.get(normal);
//    }
//
//    public AssemblerRecipe getRecipe(ItemStack output) {
//        return ard.getRecipe(output);
////        return outputToRecipeMap.get(normalizedItem(output));
//    }
//
//    private ItemStack[] normalizedInput(ItemStack[] input) {
//        if (input.length != 9) {
//            return null;
//        }
//
//        ItemStack[] ret = new ItemStack[9];
//
//        for (int i = 0; i < 9; i++) {
//            ret[i] = normalizedItem(input[i]);
//        }
//        return ret;
//    }
//
//    private ItemStack normalizedItem(ItemStack original) {
//        if (original == null) {
//            return null;
//        }
//        return new ItemStack(original.itemID, 1, original.getItemDamage());
//    }
//
//    public boolean addReversableRecipe(AssemblerRecipe recipe)
//            throws IllegalArgumentException, AssemblerRecipeFoundException {
//        if (recipe.input.length != 9) {
//            throw new IllegalArgumentException(
//                    "AssemblerRecipe - Invalid Input Array Length!  Must be 9!");
//        }
//        ItemStack[] normalArray = normalizedInput(recipe);
//        if (normalArray == null) {
//            return false;
//        }
//
//        ItemStack normal = normalizedOutput(recipe);
//
//        if (!checkDecomposition(normal, recipe) ||
//            !checkRecomposition(normalArray, recipe)) {
//            Femtocraft.log(Level.WARNING,
//                    "Assembler recipe already exists for " + recipe.output.getUnlocalizedName() + ".");
//            return false;
//        }
//
//        return registerRecomposition(normalArray, recipe)
//               && registerDecomposition(normal, recipe);
//    }
//
//    public boolean addRecompositionRecipe(AssemblerRecipe recipe)
//            throws IllegalArgumentException, AssemblerRecipeFoundException {
//        if (recipe.input.length != 9) {
//            throw new IllegalArgumentException(
//                    "AssemblerRecipe - Invalid Input Array Length!  Must be 9!");
//        }
//
//        ItemStack[] normal = normalizedInput(recipe);
//        if (normal == null) {
//            return false;
//        }
//
//        if (!checkRecomposition(normal, recipe)) {
//            Femtocraft.log(Level.WARNING,
//                    "Assembler recipe already exists for " + recipe.output.getUnlocalizedName() + ".");
//            return false;
//        }
//
//        return registerRecomposition(normal, recipe);
//    }
//
//    public boolean addDecompositionRecipe(AssemblerRecipe recipe)
//            throws IllegalArgumentException, AssemblerRecipeFoundException {
//        if (recipe.input.length != 9) {
//            throw new IllegalArgumentException(
//                    "AssemblerRecipe - Invalid Input Array Length!  Must be 9!");
//        }
//
//        ItemStack normal = normalizedOutput(recipe);
//
//        if (!checkDecomposition(normal, recipe)) {
//            Femtocraft.log(Level.WARNING,
//                    "Assembler recipe already exists for " + recipe.output.getUnlocalizedName() + ".");
//            return false;
//        }
//
//        return registerDecomposition(normal, recipe);
//    }
//
//    private boolean registerRecomposition(ItemStack[] normal,
//                                          AssemblerRecipe recipe) {
//        AssemblerRecompositionRegisterEvent event = new AssemblerRecompositionRegisterEvent(
//                recipe);
//        Femtocraft.assemblerConfigs().loadAssemblerRecipe(recipe);
//        if (!MinecraftForge.EVENT_BUS.post(event)) {
//            ard.insertRecipe(recipe);
////            inputToRecipeMap.put(normal, recipe);
//            addRecipeToTechLevelMap(recipe);
//            addRecipeToTechnologyMap(recipe);
//            return true;
//        }
//        return false;
//    }
//
//    private boolean registerDecomposition(ItemStack normal,
//                                          AssemblerRecipe recipe) {
//        AssemblerDecompositionRegisterEvent event = new AssemblerDecompositionRegisterEvent(
//                recipe);
//        Femtocraft.assemblerConfigs().loadAssemblerRecipe(recipe);
//        if (!MinecraftForge.EVENT_BUS.post(event)) {
//            ard.insertRecipe(recipe);
////            outputToRecipeMap.put(normal, recipe);
//            addRecipeToTechLevelMap(recipe);
//            addRecipeToTechnologyMap(recipe);
//            return true;
//        }
//        return false;
//    }
//
//    private void addRecipeToTechLevelMap(AssemblerRecipe recipe) {
////        ArrayList<AssemblerRecipe> array = techLevelToRecipeMap
////                .get(recipe.enumTechLevel);
////        if (array == null) {
////            array = new ArrayList<AssemblerRecipe>();
////            techLevelToRecipeMap.put(recipe.enumTechLevel, array);
////        }
////        if (!array.contains(recipe)) {
////            array.add(recipe);
////        }
//    }
//
//    private void addRecipeToTechnologyMap(AssemblerRecipe recipe) {
////        ArrayList<AssemblerRecipe> array = technologyToRecipeMap
////                .get(recipe.tech);
////        if (array == null) {
////            array = new ArrayList<AssemblerRecipe>();
////            technologyToRecipeMap.put(recipe.tech, array);
////        }
////        if (!array.contains(recipe)) {
////            array.add(recipe);
////        }
//    }
//
//    private boolean checkDecomposition(ItemStack normal, AssemblerRecipe recipe) {
//        if (ard.getRecipe(normal) != null) {
////        if (outputToRecipeMap.containsKey(normal)) {
//            return false;
//
//        }
//        return true;
//    }
//
//    private boolean checkRecomposition(ItemStack[] normal, AssemblerRecipe recipe) {
//        if (ard.getRecipe(normal) != null) {
////        if (inputToRecipeMap.containsKey(normal)) {
//            return false;
//        }
//        return true;
//    }
//
//    public boolean removeAnyRecipe(AssemblerRecipe recipe) {
//        return removeDecompositionRecipe(recipe)
//               || removeRecompositionRecipe(recipe);
//    }
//
//    public boolean removeDecompositionRecipe(AssemblerRecipe recipe) {
////        return (outputToRecipeMap.remove(normalizedOutput(recipe)) != null);
//        return false;
//    }
//
//    public boolean removeRecompositionRecipe(AssemblerRecipe recipe) {
////        ItemStack[] normal = normalizedInput(recipe);
////        return normal != null && (inputToRecipeMap.remove(normal) != null);
//        return false;
//    }
//
//    private ItemStack normalizedOutput(AssemblerRecipe recipe) {
//        return normalizedItem(recipe.output);
//    }
//
//    private ItemStack[] normalizedInput(AssemblerRecipe recipe) {
//        return normalizedInput(recipe.input);
//    }
//
//    public boolean removeReversableRecipe(AssemblerRecipe recipe) {
//        return removeDecompositionRecipe(recipe)
//               && removeRecompositionRecipe(recipe);
//    }
//
//    public boolean canCraft(ItemStack[] input) {
//        if (input.length != 9) {
//            return false;
//        }
//        AssemblerRecipe recipe = getRecipe(input);
//        if (recipe == null) {
//            return false;
//        }
//
//        for (int i = 0; i < 9; ++i) {
//            ItemStack rec = recipe.input[i];
//            if (input[i] == null || rec == null) {
//                continue;
//            }
//
//            if (input[i].stackSize < input[i].stackSize) {
//                return false;
//            }
//            if (FemtocraftUtils.compareItem(rec, input[i]) != 0) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean canCraft(ItemStack input) {
//        AssemblerRecipe recipe = getRecipe(input);
//        return recipe != null && input.stackSize >= recipe.output.stackSize
//               && FemtocraftUtils.compareItem(recipe.output, input) == 0;
//    }
//
//    public ArrayList<AssemblerRecipe> getRecipesForTechLevel(EnumTechLevel level) {
////        return techLevelToRecipeMap.get(level);
//        return ard.getRecipesForLevel(level);
//    }
//
//    public ArrayList<AssemblerRecipe> getAllRecipes() {
//        return ard.getAllRecipes();
//    }
//
//    public ArrayList<AssemblerRecipe> getRecipesForTechnology(
//            ResearchTechnology tech) {
////        return technologyToRecipeMap.get(tech);
//        return ard.getRecipesForTech(tech);
//    }
//
//    public ArrayList<AssemblerRecipe> getRecipesForTechnology(String techName) {
//        return ard.getRecipesForTech(techName);
//    }
//
//    public boolean hasResearchedRecipe(AssemblerRecipe recipe, String username) {
//        return Femtocraft.researchManager().hasPlayerResearchedTechnology(
//                username, recipe.tech);
//    }
//
//    public static class AssemblerRecipeFoundException extends Exception {
//        public String errMsg;
//
//        public AssemblerRecipeFoundException(String message) {
//            errMsg = message;
//        }
//    }
//}
