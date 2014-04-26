package femtocraft.industry.tiles;

import femtocraft.managers.research.EnumTechLevel;

public class TileEntityNanoDismantler extends
                                      TileEntityBaseEntityMicroDeconstructor {
    public static int maxSmelt_default = 8;
    public static int ticksToCook_default = 60;
    public static int powerToCook_default = 80;

    public TileEntityNanoDismantler() {
        super();
        this.setTechLevel(EnumTechLevel.NANO);
        // TODO: Pull number from configs
        this.setMaxStorage(10000);
    }

    @Override
    protected int getMaxSimultaneousSmelt() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return maxSmelt_default;
    }

    @Override
    protected int getTicksToCook() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return ticksToCook_default;
    }

    @Override
    protected int getPowerToCook() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return powerToCook_default;
    }
}
