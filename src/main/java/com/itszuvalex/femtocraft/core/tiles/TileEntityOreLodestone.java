package com.itszuvalex.femtocraft.core.tiles;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.core.FemtocraftMagnetUtils;
import com.itszuvalex.femtocraft.core.ore.BlockOreLodestone;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Chris on 9/21/2014.
 */
@Configurable
public class TileEntityOreLodestone extends TileEntity {
    @Configurable
    public static int numTicksPerUpdate = 4;
    int count = 0;

    @Override
    public boolean canUpdate() {
        return BlockOreLodestone.MAGNETIC;
    }

    @Override
    public void updateEntity() {
//        if (worldObj.isRemote) return;
        if (count++ > numTicksPerUpdate) {
            FemtocraftMagnetUtils.applyMagnetismFromBlock(Femtocraft.blockOreLodestone, worldObj, xCoord, yCoord,
                    zCoord, (double) numTicksPerUpdate / (double) 20);
            count = 0;
        }
    }
}
