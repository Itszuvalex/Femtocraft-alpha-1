package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;

/**
 * Created by Chris on 9/8/2014.
 */
public class TileEntityCryoEndothermalChargingBase extends TileEntityPowerProducer {
    public static final int maxStorage = 25000;

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(EnumTechLevel.NANO, maxStorage);
    }

}
