package com.itszuvalex.femtocraft.api.research;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import net.minecraft.item.ItemStack;

/**
 * @author Itszuvalex (Christopher Harris)
 *         <p/>
 *         Interface for an Item that carries a Femtocraft Technology.
 */
public interface ITechnologyCarrier {

    void setTechnology(ItemStack stack, String name);

    String getTechnology(ItemStack stack);

    EnumTechLevel getTechnologyLevel(ItemStack stack);
}
