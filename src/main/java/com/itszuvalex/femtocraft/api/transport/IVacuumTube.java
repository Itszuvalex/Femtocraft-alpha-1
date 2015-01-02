package com.itszuvalex.femtocraft.api.transport;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Itszuvalex
 */
public interface IVacuumTube {

    /**
     * @return Returns True if tube cannot accept any more items.
     */
    public boolean isOverflowing();

    /**
     * @param item ItemStack to be inserted into tube.
     * @return True if ItemStack is able to be put into tube.
     */
    public boolean canInsertItem(ItemStack item, ForgeDirection dir);

    /**
     * @param item ItemStack to be inserted into tube.
     * @return True if ItemStack was successfully put into tube.
     */
    public boolean insertItem(ItemStack item, ForgeDirection dir);

    /**
     * @return True if given ForgeDirection is an input.  This allows multiple connections, compared to simply returning
     * "input direction", causing lockout.
     */
    public boolean isInput(ForgeDirection dir);

    /**
     * @return True if given ForgeDirection is an output.  This allows multiple connections, compared to simply
     * returning "output direction", causing lockout.
     */
    public boolean isOutput(ForgeDirection dir);

    /**
     * Clears connections to this tube from the given side.
     */
    public void disconnect(ForgeDirection dir);

    /**
     * @param input Direction tube will now accept input from.
     * @return True if input successfully set.
     */
    public boolean addInput(ForgeDirection input);

    /**
     * @param output Direction tube will now send output to.
     * @return True if input successfully set.
     */
    public boolean addOutput(ForgeDirection output);
}
