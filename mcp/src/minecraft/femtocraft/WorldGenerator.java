package femtocraft;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGenerator implements IWorldGenerator {

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

                (new WorldGenMinable(Femtocraft.oreTitanium.blockID,
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

                (new WorldGenMinable(Femtocraft.orePlatinum.blockID,
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

                (new WorldGenMinable(Femtocraft.oreThorium.blockID,
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

                (new WorldGenMinable(Femtocraft.oreFarenite.blockID,
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

                (new WorldGenMinable(Femtocraft.oreMalenite.blockID,
                                     FemtocraftConfigs.maleniteOreBlockPerVeinCount,
                                     Block.netherrack.blockID)).generate(world, random,
                                                                         xCoord, yCoord, zCoord);
            }
        }

    }

}
