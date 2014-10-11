package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Chris on 9/8/2014.
 */
public class TileEntityCryoEndothermalChargingBase extends TileEntityPowerProducer {
    public static final int maxStorage = 25000;

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return EnumTechLevel.NANO;
    }

    @Override
    public int getMaxPower() {
        return maxStorage;
    }

}
