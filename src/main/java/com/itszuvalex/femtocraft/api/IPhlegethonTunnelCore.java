package com.itszuvalex.femtocraft.api;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/12/14.
 */
public interface IPhlegethonTunnelCore extends IPhlegethonTunnelComponent, IPowerContainer {
    /**
     * @return True if the Phlegethon Tunnel is active.
     */
    boolean isActive();

    /**
     * @return Base amount of power this core generates.
     */
    float getPowerGenBase();

    /**
     * Be careful when calling this from IPhlegethonTunnelAddon.
     * This may cause a recursive loop as the core will request all addon's power contributions.
     *
     * @return Amount of power this core generates, including all 6 neighboring addons.
     */
    float getTotalPowerGen();

    /**
     * @return Returns height of the Phlegethon Tunnel core.
     */
    int getHeight();

    /**
     * @return True if it successfully activates.  If it's already active, it should return false.
     */
    boolean activate();

    /**
     * @return True if it successfully deactivates.  If it's already inactive, it should return false;
     */
    boolean deactivate();
}
