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

package com.itszuvalex.femtocraft;

import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class FemtocraftOreGenerator implements IWorldGenerator {

    public static final String ORE_GEN = "OreGen";

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        generate(random, chunkX, chunkZ, world, null);

    }

    public static void retroGenerate(Random random, Chunk chunk, NBTTagCompound regionOreConfig) {
        generate(random, chunk.xPosition, chunk.zPosition, chunk.worldObj, regionOreConfig);
    }

    public static boolean shouldRetroGenerate(World world, NBTTagCompound regionOreConfig) {
        switch (world.provider.dimensionId) {
            case -1:
                return shouldRetroGenNether(world, regionOreConfig);
            default:
            case 0:
                return shouldRetroGenSurface(world, regionOreConfig);
            case 1:
                return shouldRetroGenEnd(world, regionOreConfig);
        }
    }

    private static boolean shouldRetroGenEnd(World world, NBTTagCompound regionOreConfig) {
        return false;
    }

    private static boolean shouldRetroGenSurface(World world, NBTTagCompound regionOreConfig) {
        return (FemtocraftConfigs.fareniteGen &&
                !getOreGenerated(regionOreConfig, Femtocraft.blockOreFarenite().getUnlocalizedName())) ||
               (FemtocraftConfigs.platinumGen &&
                !getOreGenerated(regionOreConfig, Femtocraft.blockOrePlatinum().getUnlocalizedName())) ||
               (FemtocraftConfigs.thoriumGen &&
                !getOreGenerated(regionOreConfig, Femtocraft.blockOreThorium().getUnlocalizedName())) ||
               (FemtocraftConfigs.titaniumGen &&
                !getOreGenerated(regionOreConfig, Femtocraft.blockOreTitanium().getUnlocalizedName()) ||
                (FemtocraftConfigs.lodestoneGen &&
                 !getOreGenerated(regionOreConfig, Femtocraft.blockOreLodestone().getLocalizedName())));
    }

    private static boolean shouldRetroGenNether(World world, NBTTagCompound regionOreConfig) {
        return FemtocraftConfigs.maleniteGen &&
               !getOreGenerated(regionOreConfig, Femtocraft.blockOreMalenite().getUnlocalizedName());
    }

    public static void generate(Random random, int chunkX, int chunkZ, World world, NBTTagCompound regionOreConfig) {
        switch (world.provider.dimensionId) {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16, regionOreConfig);
                break;
            default:
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16, regionOreConfig);
                break;
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16, regionOreConfig);
                break;
        }
    }

    private static void generateNether(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        if (FemtocraftConfigs.maleniteGen) {
            generateMalenite(world, random, i, j, regionOreConfig);
        }

    }

    private static void generateSurface(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {

        if (FemtocraftConfigs.titaniumGen) {
            generateTitanium(world, random, i, j, regionOreConfig);
        }

        if (FemtocraftConfigs.platinumGen) {
            generatePlatinum(world, random, i, j, regionOreConfig);
        }

        if (FemtocraftConfigs.thoriumGen) {
            generateThorium(world, random, i, j, regionOreConfig);
        }

        if (FemtocraftConfigs.fareniteGen) {
            generateFarenite(world, random, i, j, regionOreConfig);
        }

        if (FemtocraftConfigs.lodestoneGen) {
            generateLodestone(world, random, i, j, regionOreConfig);
        }
    }

    public static void generateLodestone(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        if (getOreGenerated(regionOreConfig, Femtocraft.blockOreLodestone().getUnlocalizedName())) {
            return;
        }

        {
            for (int k = 0; k < FemtocraftConfigs.lodestoneOreVeinsPerChunkCount; k++) {
                int xCoord = i + random.nextInt(16);
                int yCoord = random
                                     .nextInt(FemtocraftConfigs.lodestoneOreYHeightMax
                                              - FemtocraftConfigs.lodestoneOreYHeightMin)
                             + FemtocraftConfigs.lodestoneOreYHeightMin;
                int zCoord = j + random.nextInt(16);

                (new WorldGenMinable(Femtocraft.blockOreLodestone(),
                        FemtocraftConfigs.lodestoneOreBlockPerVeinCount))
                        .generate(world, random, xCoord, yCoord, zCoord);

            }
        }

        markOreGeneration(regionOreConfig, Femtocraft.blockOreLodestone().getUnlocalizedName(), true);
    }

    public static void generateFarenite(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        if (getOreGenerated(regionOreConfig, Femtocraft.blockOreFarenite().getUnlocalizedName())) {
            return;
        }

        {
            for (int k = 0; k < FemtocraftConfigs.fareniteOreVeinsPerChunkCount; k++) {
                int xCoord = i + random.nextInt(16);
                int yCoord = random
                                     .nextInt(FemtocraftConfigs.fareniteOreYHeightMax
                                              - FemtocraftConfigs.fareniteOreYHeightMin)
                             + FemtocraftConfigs.fareniteOreYHeightMin;
                int zCoord = j + random.nextInt(16);

                (new WorldGenMinable(Femtocraft.blockOreFarenite(),
                        FemtocraftConfigs.fareniteOreBlockPerVeinCount))
                        .generate(world, random, xCoord, yCoord, zCoord);

            }
        }

        markOreGeneration(regionOreConfig, Femtocraft.blockOreFarenite().getUnlocalizedName(), true);
    }

    public static void generateThorium(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        if (getOreGenerated(regionOreConfig, Femtocraft.blockOreThorium().getUnlocalizedName())) {
            return;
        }

        for (int k = 0; k < FemtocraftConfigs.thoriumOreVeinsPerChunkCount; k++) {
            int xCoord = i + random.nextInt(16);
            int yCoord = random
                                 .nextInt(FemtocraftConfigs.thoriumOreYHeightMax
                                          - FemtocraftConfigs.thoriumOreYHeightMin)
                         + FemtocraftConfigs.thoriumOreYHeightMin;
            int zCoord = j + random.nextInt(16);

            (new WorldGenMinable(Femtocraft.blockOreThorium(),
                    FemtocraftConfigs.thoriumOreBlockPerVeinCount))
                    .generate(world, random, xCoord, yCoord, zCoord);

        }

        markOreGeneration(regionOreConfig, Femtocraft.blockOreThorium().getUnlocalizedName(), true);
    }

    public static void generatePlatinum(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        if (getOreGenerated(regionOreConfig, Femtocraft.blockOrePlatinum().getUnlocalizedName())) {
            return;
        }

        for (int k = 0; k < FemtocraftConfigs.platinumOreVeinsPerChunkCount; k++) {
            int xCoord = i + random.nextInt(16);
            int yCoord = random
                                 .nextInt(FemtocraftConfigs.platinumOreYHeightMax
                                          - FemtocraftConfigs.platinumOreYHeightMin)
                         + FemtocraftConfigs.platinumOreYHeightMin;
            int zCoord = j + random.nextInt(16);

            (new WorldGenMinable(Femtocraft.blockOrePlatinum(),
                    FemtocraftConfigs.platinumOreBlockPerVeinCount))
                    .generate(world, random, xCoord, yCoord, zCoord);
        }

        markOreGeneration(regionOreConfig, Femtocraft.blockOrePlatinum().getUnlocalizedName(), true);
    }

    public static void generateTitanium(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        if (getOreGenerated(regionOreConfig, Femtocraft.blockOreTitanium().getUnlocalizedName())) {
            return;
        }


        for (int k = 0; k < FemtocraftConfigs.titaniumOreVeinsPerChunkCount; k++) {
            int xCoord = i + random.nextInt(16);
            int yCoord = random
                                 .nextInt(FemtocraftConfigs.titaniumOreYHeightMax
                                          - FemtocraftConfigs.titaniumOreYHeightMin)
                         + FemtocraftConfigs.titaniumOreYHeightMin;
            int zCoord = j + random.nextInt(16);

            (new WorldGenMinable(Femtocraft.blockOreTitanium(),
                    FemtocraftConfigs.titaniumOreBlockPerVeinCount))
                    .generate(world, random, xCoord, yCoord, zCoord);
        }

        markOreGeneration(regionOreConfig, Femtocraft.blockOreTitanium().getUnlocalizedName(), true);
    }

    private static void generateEnd(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        // TODO Auto-generated method stub
    }


    public static void generateMalenite(World world, Random random, int i, int j, NBTTagCompound regionOreConfig) {
        if (getOreGenerated(regionOreConfig, Femtocraft.blockOreMalenite().getUnlocalizedName())) {
            return;
        }

        // Due to spread over all of Nether's height
        for (int k = 0; k < FemtocraftConfigs.maleniteOreVeinsPerChunkCount; ++k) {
            int xCoord = i + random.nextInt(16);
            int yCoord = random
                                 .nextInt(FemtocraftConfigs.maleniteOreYHeightMax
                                          - FemtocraftConfigs.maleniteOreYHeightMin)
                         + FemtocraftConfigs.maleniteOreYHeightMin;
            int zCoord = j + random.nextInt(16);

            (new WorldGenMinable(Femtocraft.blockOreMalenite(),
                    FemtocraftConfigs.maleniteOreBlockPerVeinCount,
                    Blocks.netherrack)).generate(world, random,
                    xCoord, yCoord, zCoord);
        }

        markOreGeneration(regionOreConfig, Femtocraft.blockOreMalenite().getUnlocalizedName(), true);
    }


    public static void markOreGeneration(NBTTagCompound compound, String oreName, boolean value) {
        if (compound == null) return;

        NBTTagCompound femtocraftTag;
        if (!compound.hasKey(Femtocraft.ID().toLowerCase())) {
            femtocraftTag = new NBTTagCompound();
            compound.setTag(Femtocraft.ID().toLowerCase(), femtocraftTag);
        } else {
            femtocraftTag = compound.getCompoundTag(Femtocraft.ID().toLowerCase());
        }

        NBTTagCompound oreTag;
        if (!femtocraftTag.hasKey(ORE_GEN)) {
            oreTag = new NBTTagCompound();
            femtocraftTag.setTag(ORE_GEN, oreTag);
        } else {
            oreTag = femtocraftTag.getCompoundTag(ORE_GEN);
        }

        oreTag.setBoolean(oreName, value);

    }

    public static boolean getOreGenerated(NBTTagCompound compound, String oreName) {
        if (compound == null) return false;
        if (!compound.hasKey(Femtocraft.ID().toLowerCase())) return false;
        NBTTagCompound femtocraftTag = compound.getCompoundTag(Femtocraft.ID().toLowerCase());
        if (!femtocraftTag.hasKey(ORE_GEN)) return false;
        NBTTagCompound oreTag = femtocraftTag.getCompoundTag(ORE_GEN);
        if (!oreTag.hasKey(oreName)) return false;
        return oreTag.getBoolean(oreName);
    }

}
