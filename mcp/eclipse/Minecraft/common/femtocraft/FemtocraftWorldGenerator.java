package femtocraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class FemtocraftWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
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
		
		if(FemtocraftConfigs.titaniumGen) {
			//Titanium
			for(int k =0; k < 7; k++) {
				int Xcoord = i + random.nextInt(16);
				int Ycoord = random.nextInt(40);
				int Zcoord = j + random.nextInt(16);
				
				(new WorldGenMinable(Femtocraft.oreTitanium.blockID, 6))
						.generate(world, random, Xcoord,  Ycoord,  Zcoord);
				
			}
		}
		
		//Platinum
		if (FemtocraftConfigs.platinumGen) {
			for (int k = 0; k < 5; k++) {
				int Xcoord = i + random.nextInt(16);
				int Ycoord = random.nextInt(30);
				int Zcoord = j + random.nextInt(16);

				(new WorldGenMinable(Femtocraft.orePlatinum.blockID, 5))
						.generate(world, random, Xcoord, Ycoord, Zcoord);
			}
		}
		
		if (FemtocraftConfigs.thoriumGen) {
			//Thorium
			for (int k = 0; k < 8; k++) {
				int Xcoord = i + random.nextInt(16);
				int Ycoord = random.nextInt(50);
				int Zcoord = j + random.nextInt(16);

				(new WorldGenMinable(Femtocraft.oreThorium.blockID, 6))
						.generate(world, random, Xcoord, Ycoord, Zcoord);

			}
		}
		
		if (FemtocraftConfigs.fareniteGen) {
			//Farenite
			for (int k = 0; k < 10; k++) {
				int Xcoord = i + random.nextInt(16);
				int Ycoord = random.nextInt(40);
				int Zcoord = j + random.nextInt(16);

				(new WorldGenMinable(Femtocraft.oreFarenite.blockID, 6))
						.generate(world, random, Xcoord, Ycoord, Zcoord);

			}
		}
	}

	private void generateNether(World world, Random random, int i, int j) {
		if(FemtocraftConfigs.maleniteGen)
		{
			//Due to spread over all of Nether's height
			for(int k = 0; k < 14; ++k)
			{
				int Xcoord = i + random.nextInt(16);
				int Ycoord = random.nextInt(108) + 10;
				int Zcoord = j + random.nextInt(16);
				
				(new WorldGenMinable(Femtocraft.oreMalenite.blockID, 10, Block.netherrack.blockID))
					.generate(world, random, Xcoord, Ycoord, Zcoord);
			}
		}
		
	}

}
