package femtocraft.industry.tiles;

import femtocraft.power.tiles.TileEntityPowerConsumer;

public class TileEntityBaseEntityIndustry extends TileEntityPowerConsumer {

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();

        if (!isWorking()) {
            if (canStartWork()) {
                startWork();
            }
        }
        else {
            continueWork();
        }

        if (isWorking() && canFinishWork()) {
            finishWork();
        }
    }

    public boolean isWorking() {
        return false;
    }

    protected boolean canStartWork() {
        return false;
    }

    protected void startWork() {
    }

    protected void continueWork() {
    }

    protected boolean canFinishWork() {
        return true;
    }

    protected void finishWork() {
    }
}
