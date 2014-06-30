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

package com.itszuvalex.femtocraft;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGeneratorOre implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
                break;
            default:
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
                break;
        }

    }

    private void generateEnd(World world, Random random, int i, int j) {
        // TODO Auto-generated method stub
    }

    private void generateSurface(World world, Random random, int i, int j) {

        if (FemtocraftConfigs.titaniumGen) {
            // Titanium
            for (int k = 0; k < FemtocraftConfigs.titaniumOreVeinsPerChunkCount; k++) {
                int xCoord = i + random.nextInt(16);
                int yCoord = random
                        .nextInt(FemtocraftConfigs.titaniumOreYHeightMax
                                         - FemtocraftConfigs.titaniumOreYHeightMin)
                        + FemtocraftConfigs.titaniumOreYHeightMin;
                int zCoord = j + random.nextInt(16);

                (new WorldGenMinable(Femtocraft.blockOreTitanium.blockID,
                                     FemtocraftConfigs.titaniumOreBlockPerVeinCount))
                        .generate(world, random, xCoord, yCoord, zCoord);

            }
        }

        // Platinum
        if (FemtocraftConfigs.platinumGen) {
            for (int k = 0; k < FemtocraftConfigs.platinumOreVeinsPerChunkCount; k++) {
                int xCoord = i + random.nextInt(16);
                int yCoord = random
                        .nextInt(FemtocraftConfigs.platinumOreYHeightMax
                                         - FemtocraftConfigs.platinumOreYHeightMin)
                        + FemtocraftConfigs.platinumOreYHeightMin;
                int zCoord = j + random.nextInt(16);

                (new WorldGenMinable(Femtocraft.blockOrePlatinum.blockID,
                                     FemtocraftConfigs.platinumOreBlockPerVeinCount))
                        .generate(world, random, xCoord, yCoord, zCoord);
            }
        }

        if (FemtocraftConfigs.thoriumGen) {
            // Thorium
            for (int k = 0; k < FemtocraftConfigs.thoriumOreVeinsPerChunkCount; k++) {
                int xCoord = i + random.nextInt(16);
                int yCoord = random
                        .nextInt(FemtocraftConfigs.thoriumOreYHeightMax
                                         - FemtocraftConfigs.thoriumOreYHeightMin)
                        + FemtocraftConfigs.thoriumOreYHeightMin;
                int zCoord = j + random.nextInt(16);

                (new WorldGenMinable(Femtocraft.blockOreThorium.blockID,
                                     FemtocraftConfigs.thoriumOreBlockPerVeinCount))
                        .generate(world, random, xCoord, yCoord, zCoord);

            }
        }

        if (FemtocraftConfigs.fareniteGen) {
            // Farenite
            for (int k = 0; k < FemtocraftConfigs.fareniteOreVeinsPerChunkCount; k++) {
                int xCoord = i + random.nextInt(16);
                int yCoord = random
                        .nextInt(FemtocraftConfigs.fareniteOreYHeightMax
                                         - FemtocraftConfigs.fareniteOreYHeightMin)
                        + FemtocraftConfigs.fareniteOreYHeightMin;
                int zCoord = j + random.nextInt(16);

                (new WorldGenMinable(Femtocraft.blockOreFarenite.blockID,
                                     FemtocraftConfigs.fareniteOreBlockPerVeinCount))
                        .generate(world, random, xCoord, yCoord, zCoord);

            }
        }
    }

    private void generateNether(World world, Random random, int i, int j) {
        if (FemtocraftConfigs.maleniteGen) {
            // Due to spread over all of Nether's height
            for (int k = 0; k < FemtocraftConfigs.maleniteOreVeinsPerChunkCount; ++k) {
                int xCoord = i + random.nextInt(16);
                int yCoord = random
                        .nextInt(FemtocraftConfigs.maleniteOreYHeightMax
                                         - FemtocraftConfigs.maleniteOreYHeightMin)
                        + FemtocraftConfigs.maleniteOreYHeightMin;
                int zCoord = j + random.nextInt(16);

                (new WorldGenMinable(Femtocraft.blockOreMalenite.blockID,
                                     FemtocraftConfigs.maleniteOreBlockPerVeinCount,
                                     Block.netherrack.blockID)).generate(world, random,
                                                                         xCoord, yCoord, zCoord);
            }
        }

    }

}
