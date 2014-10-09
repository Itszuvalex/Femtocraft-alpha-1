/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.api.transport;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

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
     * @return True if given ForgeDirection is an input.  This allows
     * multiple connections, compared to simply returning "input direction",
     * causing lockout.
     */
    public boolean isInput(ForgeDirection dir);

    /**
     * @return True if given ForgeDirection is an output.  This allows
     * multiple connections, compared to simply returning "output direction",
     * causing lockout.
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
