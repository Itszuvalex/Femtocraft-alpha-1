package com.itszuvalex.femtocraft.api.power.plasma;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public interface IFusionReactorComponent extends IPlasmaContainer {

    /**
     * Called when the ignition process begins.  Likely started by a player.
     *
     * @param core Core this is a component of.
     */
    void beginIgnitionProcess(IFusionReactorCore core);

    /**
     * Called when the ignition process ends, whether it was successful or not.
     *
     * @param core Core this is a component of.
     */
    void endIgnitionProcess(IFusionReactorCore core);

    /**
     * @return Core this is a component of
     */
    IFusionReactorCore getCore();

    /**
     * Called when a self-sustaining reaction is ended, for any reason.
     *
     * @param core Core this is a component of.
     */
    void onReactionStop(IFusionReactorCore core);

}
