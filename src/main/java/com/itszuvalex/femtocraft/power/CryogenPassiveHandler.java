package com.itszuvalex.femtocraft.power;

import net.minecraft.block.Block;
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
        int id = world.getBlockId(x, y, z);
        return id == Block.ice.blockID || id == Block.blockSnow.blockID;
    }

    @Override
    public float powerForBlock(World world, int x, int y, int z) {
        int id = world.getBlockId(x, y, z);
        return id == Block.ice.blockID ? powerPerTickForIce : id == Block.blockSnow.blockID ? powerPerTickForSnow : 0;
    }

    @Override
    public void usedBlockForPower(World world, int x, int y, int z) {

    }
}
