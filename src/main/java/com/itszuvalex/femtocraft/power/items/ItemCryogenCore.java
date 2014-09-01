package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.core.items.ItemBase;

/**
 * Created by Chris on 8/31/2014.
 */
public abstract class ItemCryogenCore extends ItemBase implements ICryogenCore {
    public ItemCryogenCore(int par1, String unlocalizedName) {
        super(par1, unlocalizedName);
        setMaxStackSize(1);
        setNoRepair();
    }
}
