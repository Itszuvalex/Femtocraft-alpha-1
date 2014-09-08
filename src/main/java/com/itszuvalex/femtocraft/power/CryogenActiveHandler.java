package com.itszuvalex.femtocraft.power;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

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
            return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP, false);
        } else {
            int blockId = world.getBlockId(x, y, z);
            return blockId == Block.waterStill.blockID || blockId == Block.lavaStill.blockID;
        }
    }

    @Override
    public float powerForBlock(World world, int x, int y, int z) {
        if (world.isAirBlock(x, y, z)) {
            if (world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP, false)) {
                return airToSnowLayerPower;
            }
        } else {
            int blockId = world.getBlockId(x, y, z);
            if (blockId == Block.waterStill.blockID) {
                return waterToIcePower;
            } else if (blockId == Block.lavaStill.blockID) {
                return lavaToObsidianPower;
            }
        }
        return 0;
    }

    @Override
    public void usedBlockForPower(World world, int x, int y, int z) {
        if (world.isAirBlock(x, y, z)) {
            if (world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP, false)) {
                world.setBlock(x, y - 1, z, Block.snow.blockID);
            }
        } else {
            int blockId = world.getBlockId(x, y, z);
            if (blockId == Block.waterStill.blockID) {
                world.setBlock(x, y, z, Block.ice.blockID);
            } else if (blockId == Block.lavaStill.blockID) {
                world.setBlock(x, y, z, Block.obsidian.blockID);
            }
        }
    }
}
