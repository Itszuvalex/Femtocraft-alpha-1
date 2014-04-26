package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityPowerProducerTest extends TileEntityPowerProducer {
    private int amountPerTick;

    public TileEntityPowerProducerTest() {
        super();
        amountPerTick = 10;
        setTechLevel(EnumTechLevel.MICRO);
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        charge(ForgeDirection.UNKNOWN, amountPerTick);
    }

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        float val = getFillPercentage();
        return val > .75f ? val : .25f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        return 1.0f;
    }
}
