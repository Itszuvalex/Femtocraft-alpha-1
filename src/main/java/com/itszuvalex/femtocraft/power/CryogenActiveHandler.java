package com.itszuvalex.femtocraft.power;

import com.itszuvalex.femtocraft.api.power.ICryogenHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Chris on 9/8/2014.
 */
public class CryogenActiveHandler implements ICryogenHandler {
    public static float waterToIcePower = 100f;
    public static float lavaToObsidianPower = 300f;
    public static float airToSnowLayerPower = 10f;

    public CryogenActiveHandler() {}

    @Override
    public boolean canHandleBlock(World world, int x, int y, int z) {
        if (world.isAirBlock(x, y, z)) {
            return world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP);
        } else {
            Block block = world.getBlock(x, y, z);
            return block == Blocks.water || block == Blocks.lava;
        }
    }

    @Override
    public float powerForBlock(World world, int x, int y, int z) {
        if (world.isAirBlock(x, y, z)) {
            if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
                return airToSnowLayerPower;
            }
        } else {
            Block block = world.getBlock(x, y, z);
            if (block == Blocks.water) {
                return waterToIcePower;
            } else if (block == Blocks.lava) {
                return lavaToObsidianPower;
            }
        }
        return 0;
    }

    @Override
    public void usedBlockForPower(World world, int x, int y, int z) {
        if (world.isAirBlock(x, y, z)) {
            if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
                world.setBlock(x, y, z, Blocks.snow_layer);
            }
        } else {
            Block block = world.getBlock(x, y, z);
            if (block == Blocks.water) {
                world.setBlock(x, y, z, Blocks.ice);
            } else if (block == Blocks.lava) {
                world.setBlock(x, y, z, Blocks.obsidian);
            }
        }
    }
}
