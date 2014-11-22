package com.itszuvalex.femtocraft.power;

import com.itszuvalex.femtocraft.api.power.ICryogenHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/**
 * Created by Chris on 9/8/2014.
 */
public class CryogenPassiveHandler implements ICryogenHandler {
    public static float powerPerTickForSnow = 2f / 4f;
    public static float powerPerTickForIce = 5f / 4f;

    public CryogenPassiveHandler() {}

    @Override
    public boolean canHandleBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        return block == Blocks.ice || block == Blocks.snow;
    }

    @Override
    public float powerForBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        return block == Blocks.ice ? powerPerTickForIce : block == Blocks.snow ? powerPerTickForSnow : 0;
    }

    @Override
    public void usedBlockForPower(World world, int x, int y, int z) {

    }
}
