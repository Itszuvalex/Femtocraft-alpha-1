package com.itszuvalex.femtocraft.managers.assembler

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Chris on 12/8/2014.
 */
object AssemblerDefaults {

  def getMacroDefaults: ArrayBuffer[AssemblerRecipe] = {
    val ret = new ArrayBuffer[AssemblerRecipe]()

    ret
  }


  def getMicroDefaults: ArrayBuffer[AssemblerRecipe] = {
    val ret = new ArrayBuffer[AssemblerRecipe]()
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.stone), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.grass), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.dirt), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.cobblestone), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand),
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Blocks.planks), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null,
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null,
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null), 1,
                               new ItemStack(Blocks.sapling), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null, null,
                                         null, null), 1, new ItemStack(Blocks.sand), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.leaves), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.web), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.deadbush), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null,
                                                new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null,
                                                null), 1, new ItemStack(Blocks.yellow_flower), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFibrousStrand), null, null,
                                                new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null),
                               1, new ItemStack(Blocks.red_flower), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null, null,
                                         null, null), 1, new ItemStack(Blocks.brown_mushroom), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null,
                                         null, null), 1, new ItemStack(Blocks.red_mushroom), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice),
                                                new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Blocks.mossy_cobblestone),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null,
                                         null, null), 1, new ItemStack(Blocks.obsidian), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null,
                                         null, null), 1, new ItemStack(Blocks.ice), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), null, null,
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null,
                                                null), 1, new ItemStack(Blocks.cactus), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand),
                                                new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Blocks.pumpkin),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemMineralLattice), null, null,
                                         null, null, null), 1, new ItemStack(Blocks.netherrack), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal),
                                                new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null,
                                                null, null), 1, new ItemStack(Blocks.soul_sand), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemConductiveAlloy),
                                                new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null,
                                                null, null), 1, new ItemStack(Items.glowstone_dust),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel),
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Blocks.melon_block),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null,
                                         null, null, null), 1, new ItemStack(Blocks.vine), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores),
                                                new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Blocks.mycelium),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null,
                                         null, null, null), 1, new ItemStack(Blocks.waterlily), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemConductiveAlloy),
                                                new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Blocks.end_stone),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null,
                                         null, null, null), 1, new ItemStack(Blocks.cocoa), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null,
                                                new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null,
                                                null, null), 1, new ItemStack(Items.apple), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice),
                                                new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null,
                                                null, null), 1, new ItemStack(Items.coal), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal, 8),
                                                new ItemStack(Femtocraft.itemIonicChunk, 8),
                                                new ItemStack(Femtocraft.itemMicroCrystal, 8),
                                                new ItemStack(Femtocraft.itemIonicChunk, 8),
                                                new ItemStack(Femtocraft.itemMicroCrystal, 8),
                                                new ItemStack(Femtocraft.itemIonicChunk, 8),
                                                new ItemStack(Femtocraft.itemMicroCrystal, 8),
                                                new ItemStack(Femtocraft.itemIonicChunk, 8),
                                                new ItemStack(Femtocraft.itemMicroCrystal, 8)), 1,
                               new ItemStack(Items.diamond), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite), null, null, null, null, null,
                                         null, null, null), 1, new ItemStack(Items.iron_ingot), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite),
                                                new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.gold_ingot),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand),
                                         null, null, null), 1, new ItemStack(Items.stick), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null,
                                         null, null, null, null), 1, new ItemStack(Items.string), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null,
                                         null, null, null), 1, new ItemStack(Items.feather), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk),
                                                new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.gunpowder),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null,
                                         null, null, null, null), 1, new ItemStack(Items.wheat_seeds),
                        EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial),
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.wheat), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null,
                                         null, null), 1, new ItemStack(Items.flint), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial),
                                                new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.porkchop),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null, null,
                                         null, null), 1, new ItemStack(Items.cooked_porkchop), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk),
                                                new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.redstone),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, null, null, null,
                                                new ItemStack(Femtocraft.itemMineralLattice)), 1,
                               new ItemStack(Items.snowball), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain),
                                                new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.leather), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null,
                                         null, null), 1, new ItemStack(Items.clay_ball), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null,
                                         null, null, null), 1, new ItemStack(Items.reeds), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null,
                                         null, null), 1, new ItemStack(Items.slime_ball), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster),
                                                new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null,
                                                null, null, null, null), 1, new ItemStack(Items.egg),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), null, null,
                                                new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null,
                                                null), 1, new ItemStack(Items.fish), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain),
                                                new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.cooked_fished),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial),
                                                new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null,
                                                null, null, null), 1,
                               new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial),
                                                new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.bone), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb),
                                                new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null,
                                                null, null, null, null), 1, new ItemStack(Items.pumpkin_seeds),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemHardenedBulb),
                                                new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null,
                                                null, null, null), 1, new ItemStack(Items.melon_seeds),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemNerveCluster),
                                                new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null,
                                                null, null, null), 1, new ItemStack(Items.beef), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null,
                                         null, null), 1, new ItemStack(Items.cooked_beef), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), null, null,
                                                new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null,
                                                null, null), 1, new ItemStack(Items.chicken), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemProteinChain), null, null, null, null,
                                         null, null), 1, new ItemStack(Items.cooked_chicken), EnumTechLevel.MICRO,
                        FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament),
                                                new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.rotten_flesh),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemIonicChunk), null,
                                                new ItemStack(Femtocraft.itemIonicChunk),
                                                new ItemStack(Femtocraft.itemOrganometallicPlate),
                                                new ItemStack(Femtocraft.itemIonicChunk), null,
                                                new ItemStack(Femtocraft.itemIonicChunk), null), 1,
                               new ItemStack(Items.ender_pearl), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster),
                                                new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null,
                                                null, null), 1, new ItemStack(Items.ghast_tear), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores),
                                                new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null,
                                                null, null, null, null), 1, new ItemStack(Items.nether_wart),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster),
                                                new ItemStack(Femtocraft.itemOrganometallicPlate), null, null, null,
                                                null, null, null, null), 1, new ItemStack(Items.spider_eye),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel),
                                                new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.blaze_powder),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite),
                                                new ItemStack(Femtocraft.itemConductiveAlloy), null, null, null, null,
                                                null, null, null), 1, new ItemStack(Items.emerald), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemReplicatingMaterial),
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null,
                                                null, null), 1, new ItemStack(Items.carrot), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemReplicatingMaterial),
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null), 1,
                               new ItemStack(Items.potato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, null,
                                                new ItemStack(Femtocraft.itemFibrousStrand), null, null), 1,
                               new ItemStack(Items.baked_potato), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new
        AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemSynthesizedFiber), null, null,
                                         null, null, null), 1, new ItemStack(Items.poisonous_potato),
                        EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.egg), new ItemStack(Items.sugar, 2),
                                                new ItemStack(Items.wheat, 3), null, null, null, null, null, null), 1,
                               new ItemStack(Items.cake), EnumTechLevel.MACRO,
                               FemtocraftTechnologies.MACROSCOPIC_STRUCTURES, AssemblerRecipe.RecipeType.Decomposition)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb, 64),
                                                new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                                                new ItemStack(Femtocraft.itemHardenedBulb, 64),
                                                new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                                                new ItemStack(Items.diamond, 64),
                                                new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                                                new ItemStack(Femtocraft.itemHardenedBulb, 64),
                                                new ItemStack(Femtocraft.itemOrganometallicPlate, 64),
                                                new ItemStack(Femtocraft.itemHardenedBulb, 64)), 1,
                               new ItemStack(Items.nether_star), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.NETHER_STAR_FABRICATION)
    ret
  }

  def getNanoDefaults: ArrayBuffer[AssemblerRecipe] = {
    val ret = new ArrayBuffer[AssemblerRecipe]()
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemCrystallite, 2),
                                                new ItemStack(Femtocraft.itemElectrite, 2),
                                                new ItemStack(Femtocraft.itemCrystallite, 2),
                                                new ItemStack(Femtocraft.itemElectrite, 2),
                                                new ItemStack(Femtocraft.itemCrystallite, 2),
                                                new ItemStack(Femtocraft.itemElectrite, 2),
                                                new ItemStack(Femtocraft.itemCrystallite, 2),
                                                new ItemStack(Femtocraft.itemElectrite, 2),
                                                new ItemStack(Femtocraft.itemCrystallite, 2)), 2,
                               new ItemStack(Femtocraft.itemMicroCrystal), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFaunite),
                                                new ItemStack(Femtocraft.itemMineralite),
                                                new ItemStack(Femtocraft.itemFaunite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemProteinChain), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFaunite),
                                                new ItemStack(Femtocraft.itemElectrite),
                                                new ItemStack(Femtocraft.itemFaunite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemNerveCluster), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMetallite), null,
                                                new ItemStack(Femtocraft.itemElectrite),
                                                new ItemStack(Femtocraft.itemElectrite),
                                                new ItemStack(Femtocraft.itemElectrite), null,
                                                new ItemStack(Femtocraft.itemMetallite), null), 2,
                               new ItemStack(Femtocraft.itemConductiveAlloy), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMineralite), null,
                                                new ItemStack(Femtocraft.itemMetallite),
                                                new ItemStack(Femtocraft.itemMetallite),
                                                new ItemStack(Femtocraft.itemMetallite), null,
                                                new ItemStack(Femtocraft.itemMineralite), null), 2,
                               new ItemStack(Femtocraft.itemMetalComposite), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), null,
                                                new ItemStack(Femtocraft.itemMineralite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemFibrousStrand), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemMineralite), null,
                                                new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemMineralLattice), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite),
                                                new ItemStack(Femtocraft.itemCrystallite),
                                                new ItemStack(Femtocraft.itemFlorite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemFungalSpores), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemElectrite),
                                                new ItemStack(Femtocraft.itemMineralite),
                                                new ItemStack(Femtocraft.itemElectrite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemIonicChunk), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite),
                                                new ItemStack(Femtocraft.itemFaunite),
                                                new ItemStack(Femtocraft.itemFlorite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemReplicatingMaterial), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCrystallite),
                                                new ItemStack(Femtocraft.itemFaunite),
                                                new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemSpinyFilament), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCrystallite),
                                                new ItemStack(Femtocraft.itemMetallite),
                                                new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemHardenedBulb), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemElectrite),
                                                new ItemStack(Femtocraft.itemFlorite),
                                                new ItemStack(Femtocraft.itemElectrite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemMorphicChannel), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite),
                                                new ItemStack(Femtocraft.itemMetallite),
                                                new ItemStack(Femtocraft.itemFlorite), null, null, null), 2,
                               new ItemStack(Femtocraft.itemSynthesizedFiber), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMetallite), null,
                                                new ItemStack(Femtocraft.itemFaunite),
                                                new ItemStack(Femtocraft.itemFaunite),
                                                new ItemStack(Femtocraft.itemFaunite), null,
                                                new ItemStack(Femtocraft.itemMetallite), null), 2,
                               new ItemStack(Femtocraft.itemOrganometallicPlate), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ADVANCED_CHEMISTRY)
    ret
  }

  def getFemtoDefaults: ArrayBuffer[AssemblerRecipe] = {
    val ret = new ArrayBuffer[AssemblerRecipe]()
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon),
                                                new ItemStack(Femtocraft.itemPlaneoid),
                                                new ItemStack(Femtocraft.itemRectangulon), null, null, null), 3,
                               new ItemStack(Femtocraft.itemCrystallite), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCubit),
                                                new ItemStack(Femtocraft.itemPlaneoid),
                                                new ItemStack(Femtocraft.itemCubit), null, null, null), 3,
                               new ItemStack(Femtocraft.itemMineralite), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCubit),
                                                new ItemStack(Femtocraft.itemRectangulon),
                                                new ItemStack(Femtocraft.itemCubit), null, null, null), 3,
                               new ItemStack(Femtocraft.itemMetallite), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon),
                                                new ItemStack(Femtocraft.itemCubit),
                                                new ItemStack(Femtocraft.itemRectangulon), null, null, null), 3,
                               new ItemStack(Femtocraft.itemFaunite), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid),
                                                new ItemStack(Femtocraft.itemCubit),
                                                new ItemStack(Femtocraft.itemPlaneoid), null, null, null), 3,
                               new ItemStack(Femtocraft.itemElectrite), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS)
    ret += new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid),
                                                new ItemStack(Femtocraft.itemRectangulon),
                                                new ItemStack(Femtocraft.itemPlaneoid), null, null, null), 3,
                               new ItemStack(Femtocraft.itemFlorite), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS)
    ret
  }

  def getFemtocraftDefaults: ArrayBuffer[AssemblerRecipe] = {
    val ret = new ArrayBuffer[AssemblerRecipe]()
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.paper, 3), null, null, null, null,
                                                null, null, null, null), 0,
                               new ItemStack(Femtocraft.itemPaperSchematic), EnumTechLevel.MACRO,
                               FemtocraftTechnologies.ALGORITHMS, AssemblerRecipe.RecipeType.Decomposition)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpoolGold),
                                                new ItemStack(Femtocraft.itemSpoolGold),
                                                new ItemStack(Femtocraft.itemSpoolGold),
                                                new ItemStack(Femtocraft.itemConductivePowder),
                                                new ItemStack(Femtocraft.itemConductivePowder),
                                                new ItemStack(Femtocraft.itemConductivePowder), new
                                                    ItemStack(Blocks.planks, 1,
                                                              OreDictionary.WILDCARD_VALUE), new
                                                    ItemStack(Blocks.planks, 1,
                                                              OreDictionary.WILDCARD_VALUE), new
                                                    ItemStack(Blocks.planks, 1,
                                                              OreDictionary.WILDCARD_VALUE)), 0,
                               new ItemStack(Femtocraft.itemMicrochip, 6), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.BASIC_CIRCUITS, AssemblerRecipe.RecipeType.Recomposition)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.blockMicroCube),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemMicrochip),
                                                new ItemStack(Femtocraft.blockMicroChargingCoil),
                                                new ItemStack(Femtocraft.itemMicrochip),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.blockMicroCube),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0,
                               new ItemStack(Femtocraft.blockMicroChargingCapacitor), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.POTENTIAL_HARVESTING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Items.gold_ingot),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium), null,
                                                new ItemStack(Femtocraft.itemVacuumCore), null,
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Items.gold_ingot),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0,
                               new ItemStack(Femtocraft.blockVacuumTube, 16), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.VACUUM_TUBES)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemDopedBoard),
                                                new ItemStack(Femtocraft.itemMicrochip),
                                                new ItemStack(Femtocraft.itemDopedBoard),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.quartz)), 0,
                               new ItemStack(Femtocraft.itemNanochip, 2), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.NANO_CIRCUITS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.comparator),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.redstone),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.redstone),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.comparator)), 0,
                               new ItemStack(Femtocraft.itemNanoCalculator), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.NANO_CIRCUITS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.redstone),
                                                new ItemStack(Items.comparator),
                                                new ItemStack(Items.redstone),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Blocks.redstone_torch),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.redstone),
                                                new ItemStack(Items.comparator)), 0,
                               new ItemStack(Femtocraft.itemNanoRegulator), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.NANO_CIRCUITS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.repeater),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.repeater),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.comparator),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Items.repeater)), 0,
                               new ItemStack(Femtocraft.itemNanoSimulator), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.NANO_CIRCUITS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.quartz), null, null,
                                                new ItemStack(Femtocraft.itemIngotFarenite), null, null,
                                                new ItemStack(Items.quartz), null, null), 0,
                               new ItemStack(Femtocraft.itemFluidicConductor), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.FARENITE_STABILIZATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Femtocraft.itemMicroPlating),
                                                new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0,
                               new ItemStack(Femtocraft.itemNanoPlating, 3), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.ARTIFICIAL_MATERIALS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoCalculator),
                                                new ItemStack(Femtocraft.itemMicroLogicCore),
                                                new ItemStack(Femtocraft.itemNanoCalculator), null, null,
                                                null, null, null, null), 0,
                               new ItemStack(Femtocraft.itemBasicAICore), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.ADVANCED_PROGRAMMING)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemNanoSimulator), null,
                                                new ItemStack(Femtocraft.itemNanoCalculator),
                                                new ItemStack(Femtocraft.itemBasicAICore),
                                                new ItemStack(Femtocraft.itemNanoCalculator), null,
                                                new ItemStack(Femtocraft.itemNanoSimulator), null), 0,
                               new ItemStack(Femtocraft.itemLearningCore), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.PATTERN_RECOGNITION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null,
                                                new ItemStack(Femtocraft.itemBasicAICore), null, null,
                                                new ItemStack(Femtocraft.itemNanoCalculator), null, null),
                               0, new ItemStack(Femtocraft.itemSchedulerCore), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.WORKLOAD_SCHEDULING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null,
                                                null, new ItemStack(Femtocraft.itemBasicAICore), null,
                                                null, null, new ItemStack(Femtocraft.itemNanoRegulator)),
                               0, new ItemStack(Femtocraft.itemManagerCore), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.RESOURCE_OPTIMIZATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.itemNanoPlating)), 0,
                               new ItemStack(Femtocraft.itemNanoCoil, 6), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.FARENITE_STABILIZATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.quartz), new ItemStack(Items.quartz),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemNanoCoil),
                                                new ItemStack(Femtocraft.itemNanoCoil),
                                                new ItemStack(Femtocraft.itemNanoCoil),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoPlating)), 0,
                               new ItemStack(Femtocraft.blockNanoCable, 8), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.FARENITE_STABILIZATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.itemSchedulerCore),
                                                new ItemStack(Femtocraft.blockMicroFurnaceUnlit),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemNanoPlating)), 0,
                               new ItemStack(Femtocraft.blockNanoInnervatorUnlit), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.KINETIC_DISSOCIATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.blockMicroDeconstructor),
                                                new ItemStack(Femtocraft.itemSchedulerCore),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0,
                               new ItemStack(Femtocraft.blockNanoDismantler), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.ATOMIC_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemSchedulerCore),
                                                new ItemStack(Femtocraft.blockMicroReconstructor),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                                                new ItemStack(Femtocraft.itemManagerCore)), 0,
                               new ItemStack(Femtocraft.blockNanoFabricator), EnumTechLevel.MICRO,
                               FemtocraftTechnologies.ATOMIC_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.itemSchedulerCore),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.blockNanoCable),
                                                new ItemStack(Femtocraft.itemNanoCoil),
                                                new ItemStack(Femtocraft.blockNanoCable)), 0,
                               new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoCoil),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoCoil),
                                                new ItemStack(Femtocraft.itemNanoPlating)), 0,
                               new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil),
                               EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Items.quartz), new ItemStack(Items.clock),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemNanoCalculator),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemNanoCalculator)), 0,
                               new ItemStack(Femtocraft.itemTemporalResonator), EnumTechLevel.NANO,
                               FemtocraftTechnologies.SPACETIME_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Items.ender_pearl),
                                                new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Items.ender_pearl),
                                                new ItemStack(Items.compass),
                                                new ItemStack(Items.ender_pearl),
                                                new ItemStack(Femtocraft.itemNanoSimulator),
                                                new ItemStack(Items.ender_pearl),
                                                new ItemStack(Femtocraft.itemNanoSimulator)), 0,
                               new ItemStack(Femtocraft.itemDimensionalMonopole), EnumTechLevel.NANO,
                               FemtocraftTechnologies.SPACETIME_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.iron_ingot),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Items.iron_ingot),
                                                new ItemStack(Items.iron_ingot),
                                                new ItemStack(Femtocraft.itemTemporalResonator),
                                                new ItemStack(Items.iron_ingot),
                                                new ItemStack(Items.iron_ingot),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Items.iron_ingot)), 0,
                               new ItemStack(Femtocraft.itemSelfFulfillingOracle), EnumTechLevel.NANO,
                               FemtocraftTechnologies.SPACETIME_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemDimensionalMonopole),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum)), 0,
                               new ItemStack(Femtocraft.itemCrossDimensionalCommunicator),
                               EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoCalculator),
                                                new ItemStack(Femtocraft.itemSelfFulfillingOracle),
                                                new ItemStack(Femtocraft.itemNanoCalculator),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.blockNanoFabricator),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Femtocraft.itemTemporalResonator),
                                                new ItemStack(Femtocraft.itemNanoRegulator)), 0,
                               new ItemStack(Femtocraft.blockNanoHorologe), EnumTechLevel.NANO,
                               FemtocraftTechnologies.TEMPORAL_PIPELINING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoSimulator),
                                                new ItemStack(Femtocraft.itemCrossDimensionalCommunicator),
                                                new ItemStack(Femtocraft.itemNanoSimulator),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.blockNanoFabricator),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Femtocraft.itemDimensionalMonopole),
                                                new ItemStack(Femtocraft.itemNanoRegulator)), 0,
                               new ItemStack(Femtocraft.blockNanoEnmesher), EnumTechLevel.NANO,
                               FemtocraftTechnologies.DIMENSIONAL_BRAIDING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum),
                                                new ItemStack(Femtocraft.itemIngotPlatinum)), 0,
                               new ItemStack(Femtocraft.itemDigitalSchematic, 8), EnumTechLevel.NANO,
                               FemtocraftTechnologies.DIGITIZED_WORKLOADS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockMicroCable),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.blockNanoCable), null, null, null,
                                                null, null, null), 0,
                               new ItemStack(Femtocraft.blockOrbitalEqualizer), EnumTechLevel.NANO,
                               FemtocraftTechnologies.POTENTIALITY_TRANSFORMATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockMicroCube),
                                                new ItemStack(Femtocraft.itemNanoRegulator),
                                                new ItemStack(Femtocraft.blockMicroCube),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.blockMicroCube),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.blockMicroCube),
                                                new ItemStack(Femtocraft.itemNanoCalculator),
                                                new ItemStack(Femtocraft.blockMicroCube)), 0,
                               new ItemStack(Femtocraft.blockNanoCubeFrame), EnumTechLevel.NANO,
                               FemtocraftTechnologies.INDUSTRIAL_STORAGE)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockNanoCable),
                                                new ItemStack(Femtocraft.blockNanoCable),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.blockNanoCubeFrame),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.blockNanoCable),
                                                new ItemStack(Femtocraft.blockNanoCable)), 0,
                               new ItemStack(Femtocraft.blockNanoCubePort), EnumTechLevel.NANO,
                               FemtocraftTechnologies.INDUSTRIAL_STORAGE)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemFluidicConductor),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemNanochip)), 0,
                               new ItemStack(Femtocraft.itemFissionReactorPlating), EnumTechLevel.NANO,
                               FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null,
                                                new ItemStack(Femtocraft.itemFissionReactorPlating), null,
                                                null, new ItemStack(Femtocraft.itemNanoRegulator), null,
                                                null), 0,
                               new ItemStack(Femtocraft.blockFissionReactorHousing), EnumTechLevel.NANO,
                               FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemLearningCore),
                                                new ItemStack(Femtocraft.blockFissionReactorHousing),
                                                new ItemStack(Femtocraft.itemSchedulerCore),
                                                new ItemStack(Femtocraft.blockFissionReactorHousing),
                                                new ItemStack(Items.diamond),
                                                new ItemStack(Femtocraft.blockFissionReactorHousing),
                                                new ItemStack(Femtocraft.itemManagerCore),
                                                new ItemStack(Femtocraft.blockFissionReactorHousing),
                                                new ItemStack(Femtocraft.itemManagerCore)), 0,
                               new ItemStack(Femtocraft.blockFissionReactorCore), EnumTechLevel.NANO,
                               FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemFissionReactorPlating),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.blockOrbitalEqualizer),
                                                new ItemStack(Blocks.chest),
                                                new ItemStack(Femtocraft.blockOrbitalEqualizer),
                                                new ItemStack(Femtocraft.itemNanochip),
                                                new ItemStack(Femtocraft.itemFissionReactorPlating),
                                                new ItemStack(Femtocraft.itemNanochip)), 0,
                               new ItemStack(Femtocraft.blockDecontaminationChamber), EnumTechLevel.NANO,
                               FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotFarenite), null,
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Femtocraft.itemIngotThorium),
                                                new ItemStack(Femtocraft.itemIngotFarenite),
                                                new ItemStack(Femtocraft.itemIngotThorium),
                                                new ItemStack(Femtocraft.itemIngotFarenite), null,
                                                new ItemStack(Femtocraft.itemIngotFarenite)), 0,
                               new ItemStack(Femtocraft.itemIngotThFaSalt, 2), EnumTechLevel.NANO,
                               FemtocraftTechnologies.THORIUM_FISSIBILITY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.ender_eye),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.ender_eye),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.ender_eye),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.ender_eye)), 0,
                               new ItemStack(Femtocraft.itemMinosGate, 2), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_INTERACTIVITY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.magma_cream),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.magma_cream),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.magma_cream),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.magma_cream)), 0,
                               new ItemStack(Femtocraft.itemCharosGate, 2), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_INTERACTIVITY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.fire_charge),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.fire_charge),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.quartz),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.fire_charge),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.fire_charge)), 0,
                               new ItemStack(Femtocraft.itemCerberusGate, 2), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_INTERACTIVITY)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMinosGate), null,
                                                new ItemStack(Femtocraft.itemCharosGate),
                                                new ItemStack(Items.ghast_tear),
                                                new ItemStack(Femtocraft.itemCharosGate), null,
                                                new ItemStack(Femtocraft.itemMinosGate), null), 0,
                               new ItemStack(Femtocraft.itemErinyesCircuit, 3), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_COMPUTING)
    ret += new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemCerberusGate), null,
                                                new ItemStack(Femtocraft.itemMinosGate),
                                                new ItemStack(Items.book),
                                                new ItemStack(Femtocraft.itemCharosGate), null,
                                                new ItemStack(Femtocraft.itemMinosGate), null), 0,
                               new ItemStack(Femtocraft.itemMinervaComplex), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_COMPUTING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.slime_ball), null,
                                                new ItemStack(Items.slime_ball),
                                                new ItemStack(Femtocraft.itemMinosGate),
                                                new ItemStack(Blocks.sticky_piston),
                                                new ItemStack(Femtocraft.itemCerberusGate),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Femtocraft.itemErinyesCircuit)), 0,
                               new ItemStack(Femtocraft.itemAtlasMount), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_ROBOTICS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Femtocraft.itemMinervaComplex),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.feather),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Femtocraft.itemMinervaComplex),
                                                new ItemStack(Femtocraft.itemErinyesCircuit)), 0,
                               new ItemStack(Femtocraft.itemHermesBus), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_COMPUTING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.gunpowder),
                                                new ItemStack(Items.ghast_tear),
                                                new ItemStack(Items.gunpowder),
                                                new ItemStack(Femtocraft.itemMinervaComplex),
                                                new ItemStack(Femtocraft.itemPandoraCube),
                                                new ItemStack(Femtocraft.itemMinervaComplex),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Blocks.piston),
                                                new ItemStack(Femtocraft.itemErinyesCircuit)), 0,
                               new ItemStack(Femtocraft.itemHerculesDrive), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_ROBOTICS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMinervaComplex),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Femtocraft.itemMinervaComplex),
                                                new ItemStack(Femtocraft.itemInfallibleEstimator),
                                                new ItemStack(Items.comparator),
                                                new ItemStack(Femtocraft.itemPanLocationalComputer),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Femtocraft.itemErinyesCircuit)), 0,
                               new ItemStack(Femtocraft.itemOrpheusProcessor), EnumTechLevel.NANO,
                               FemtocraftTechnologies.QUANTUM_COMPUTING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.diamond),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Items.diamond),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Femtocraft.itemNanoPlating),
                                                new ItemStack(Femtocraft.itemAtlasMount),
                                                new ItemStack(Items.diamond),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Items.diamond)), 0,
                               new ItemStack(Femtocraft.itemFemtoPlating, 2), EnumTechLevel.NANO,
                               FemtocraftTechnologies.ELEMENT_MANUFACTURING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemErinyesCircuit),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockNanoInnervatorUnlit),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemCerberusGate),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemCerberusGate)), 0,
                               new ItemStack(Femtocraft.blockFemtoImpulserUnlit), EnumTechLevel.NANO,
                               FemtocraftTechnologies.PARTICLE_EXCITATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemHerculesDrive),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockNanoDismantler),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemCharosGate),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemCharosGate)), 0,
                               new ItemStack(Femtocraft.blockFemtoRepurposer), EnumTechLevel.NANO,
                               FemtocraftTechnologies.PARTICLE_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemHerculesDrive),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockNanoFabricator),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemMinosGate),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemMinosGate)), 0,
                               new ItemStack(Femtocraft.blockFemtoCoagulator), EnumTechLevel.NANO,
                               FemtocraftTechnologies.PARTICLE_MANIPULATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemIngotMalenite),
                                                new ItemStack(Items.netherbrick)), 0,
                               new ItemStack(Femtocraft.itemStyxValve), EnumTechLevel.NANO,
                               FemtocraftTechnologies.DEMONIC_PARTICULATES)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemStyxValve),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemStyxValve),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemStyxValve),
                                                new ItemStack(Femtocraft.itemFemtoPlating)), 0,
                               new ItemStack(Femtocraft.itemFemtoCoil, 6), EnumTechLevel.NANO,
                               FemtocraftTechnologies.DEMONIC_PARTICULATES)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.diamond), new ItemStack(Items.diamond),
                                                new ItemStack(Items.diamond),
                                                new ItemStack(Femtocraft.itemFemtoCoil),
                                                new ItemStack(Femtocraft.itemFemtoCoil),
                                                new ItemStack(Femtocraft.itemFemtoCoil),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating)), 0,
                               new ItemStack(Femtocraft.blockFemtoCable, 8), EnumTechLevel.NANO,
                               FemtocraftTechnologies.DEMONIC_PARTICULATES)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemPandoraCube),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                                                new ItemStack(Items.nether_star),
                                                new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemPandoraCube),
                                                new ItemStack(Femtocraft.itemFemtoPlating)), 0,
                               new ItemStack(Femtocraft.blockPhlegethonTunnelCore), EnumTechLevel.NANO,
                               FemtocraftTechnologies.SPONTANEOUS_GENERATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemDimensionalMonopole),
                                                new ItemStack(Items.ender_eye),
                                                new ItemStack(Femtocraft.itemDimensionalMonopole),
                                                new ItemStack(Items.ender_eye),
                                                new ItemStack(Items.diamond),
                                                new ItemStack(Items.ender_eye),
                                                new ItemStack(Femtocraft.itemDimensionalMonopole),
                                                new ItemStack(Items.ender_eye),
                                                new ItemStack(Femtocraft.itemDimensionalMonopole)), 0,
                               new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer), EnumTechLevel.NANO,
                               FemtocraftTechnologies.SPONTANEOUS_GENERATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Items.netherbrick)), 0,
                               new ItemStack(Femtocraft.blockPhlegethonTunnelFrame), EnumTechLevel.NANO,
                               FemtocraftTechnologies.SPONTANEOUS_GENERATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Blocks.piston),
                                                new ItemStack(Blocks.iron_block),
                                                new ItemStack(Blocks.piston),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemFemtoPlating)), 0,
                               new ItemStack(Femtocraft.blockSisyphusStabilizer), EnumTechLevel.NANO,
                               FemtocraftTechnologies.SPONTANEOUS_GENERATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHerculesDrive),
                                                new ItemStack(Femtocraft.itemOrpheusProcessor),
                                                new ItemStack(Femtocraft.itemHerculesDrive),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Items.nether_star),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Femtocraft.itemMinervaComplex),
                                                new ItemStack(Femtocraft.itemAtlasMount),
                                                new ItemStack(Femtocraft.itemMinervaComplex)), 0,
                               new ItemStack(Femtocraft.itemQuantumSchematic, 8), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.SPIN_RETENTION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemOrpheusProcessor),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockNanoHorologe),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemInfallibleEstimator),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemInfallibleEstimator)), 0,
                               new ItemStack(Femtocraft.blockFemtoChronoshifter), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.TEMPORAL_THREADING)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHerculesDrive),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemHerculesDrive),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockNanoEnmesher),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemOrpheusProcessor),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemOrpheusProcessor)), 0,
                               new ItemStack(Femtocraft.blockFemtoEntangler), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.DIMENSIONAL_SUPERPOSITIONS)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockNanoCable),
                                                new ItemStack(Femtocraft.itemStyxValve),
                                                new ItemStack(Femtocraft.blockFemtoCable), null, null,
                                                null, null, null, null), 0,
                               new ItemStack(Femtocraft.blockNullEqualizer), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.SPONTANEOUS_GENERATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockFemtoCable),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockFemtoCable),
                                                new ItemStack(Femtocraft.itemAtlasMount),
                                                new ItemStack(Femtocraft.blockFemtoCable),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.blockFemtoCable),
                                                new ItemStack(Femtocraft.itemFemtoPlating)), 0,
                               new ItemStack(Femtocraft.blockFemtoCubePort), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.CORRUPTION_STABILIZATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Items.netherbrick)), 0,
                               new ItemStack(Femtocraft.blockFemtoCubeFrame), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.CORRUPTION_STABILIZATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Femtocraft.itemCerberusGate),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Femtocraft.itemHermesBus),
                                                new ItemStack(Femtocraft.itemFemtoPlating)), 0,
                               new ItemStack(Femtocraft.blockFemtoCubeChassis), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.CORRUPTION_STABILIZATION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.diamond),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Items.diamond), new ItemStack(Items.diamond),
                                                new ItemStack(Femtocraft.itemFemtoPlating),
                                                new ItemStack(Items.diamond), new ItemStack(Items.diamond),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Items.diamond)), 0,
                               new ItemStack(Femtocraft.itemStellaratorPlating), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.STELLAR_MIMICRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockSisyphusStabilizer),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockSisyphusStabilizer),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.nether_star),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockSisyphusStabilizer),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockSisyphusStabilizer)), 0,
                               new ItemStack(Femtocraft.blockStellaratorCore), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.STELLAR_MIMICRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Blocks.glass),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Blocks.glass), new ItemStack(Items.diamond),
                                                new ItemStack(Blocks.glass),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Blocks.glass),
                                                new ItemStack(Femtocraft.itemStellaratorPlating)), 0,
                               new ItemStack(Femtocraft.blockStellaratorFocus), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.STELLAR_MIMICRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Blocks.nether_brick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.netherbrick)), 0,
                               new ItemStack(Femtocraft.blockStellaratorHousing), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.STELLAR_MIMICRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.netherbrick)), 0,
                               new ItemStack(Femtocraft.blockStellaratorOpticalMaser), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.STELLAR_MIMICRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.blaze_rod),
                                                new ItemStack(Items.blaze_rod),
                                                new ItemStack(Items.blaze_rod),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.itemStellaratorPlating)), 0,
                               new ItemStack(Femtocraft.blockPlasmaConduit, 8), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.STELLAR_MIMICRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.blockPlasmaConduit),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Items.blaze_rod),
                                                new ItemStack(Blocks.dispenser),
                                                new ItemStack(Items.netherbrick),
                                                new ItemStack(Femtocraft.blockPlasmaConduit),
                                                new ItemStack(Items.netherbrick)), 0,
                               new ItemStack(Femtocraft.blockPlasmaVent), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.STELLAR_MIMICRY)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockPlasmaConduit),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockPlasmaConduit),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.itemFemtoCoil),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockPlasmaConduit),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockPlasmaConduit)), 0,
                               new ItemStack(Femtocraft.blockPlasmaTurbine), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.ENERGY_CONVERSION)
    ret += new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockSisyphusStabilizer),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockSisyphusStabilizer),
                                                new ItemStack(Blocks.diamond_block),
                                                new ItemStack(Femtocraft.blockSisyphusStabilizer),
                                                new ItemStack(Femtocraft.itemStellaratorPlating),
                                                new ItemStack(Femtocraft.blockSisyphusStabilizer),
                                                new ItemStack(Femtocraft.itemStellaratorPlating)), 0,
                               new ItemStack(Femtocraft.blockPlasmaCondenser), EnumTechLevel.FEMTO,
                               FemtocraftTechnologies.MATTER_CONVERSION)
    ret
  }
}
