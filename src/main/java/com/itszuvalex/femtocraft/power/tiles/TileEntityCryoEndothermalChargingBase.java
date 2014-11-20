package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.power.ICryoEndothermalChargingBase;
import com.itszuvalex.femtocraft.api.power.PowerContainer;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Chris on 9/8/2014.
 */
public class TileEntityCryoEndothermalChargingBase extends TileEntityPowerProducer implements
        ICryoEndothermalChargingBase {
    public static final int maxStorage = 25000;
    public static final EnumTechLevel powerLevel = EnumTechLevel.NANO;

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(powerLevel, maxStorage);
    }

    @Override
    public int addPower(int power) {
        return charge(ForgeDirection.DOWN, power);
    }

    @Override
    public boolean isTechLevelSupported(EnumTechLevel tech) {
        return powerLevel == tech;
    }
}
