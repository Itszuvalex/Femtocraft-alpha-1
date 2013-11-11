package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenFlowers extends WorldGenerator
{
    /** The ID of the plant block used in this plant generator. */
    private int plantBlockId;

    public WorldGenFlowers(int par1)
    {
        this.plantBlockId = par1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int l = 0; l < 64; ++l)
        {
            int i1 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int j1 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int k1 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(i1, j1, k1) && (!par1World.provider.hasNoSky || j1 < 127) && Block.blocksList[this.plantBlockId].canBlockStay(par1World, i1, j1, k1))
            {
                par1World.setBlock(i1, j1, k1, this.plantBlockId, 0, 2);
            }
        }

        return true;
    }
}
