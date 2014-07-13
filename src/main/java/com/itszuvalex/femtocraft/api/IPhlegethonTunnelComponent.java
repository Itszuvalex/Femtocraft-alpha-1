package com.itszuvalex.femtocraft.api;

import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
public interface IPhlegethonTunnelComponent extends IMultiBlockComponent {
    /**
     * Called when the state of the core changes.
     *
     * @param active The state the core is changing into.
     */
    void onCoreActivityChange(boolean active);
}
