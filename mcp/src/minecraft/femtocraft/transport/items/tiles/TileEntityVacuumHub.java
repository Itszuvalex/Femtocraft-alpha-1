/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.transport.items.tiles;

import femtocraft.api.IVacuumTube;
import femtocraft.core.tiles.TileEntityBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 6/8/14.
 */
public class TileEntityVacuumHub extends TileEntityBase implements IVacuumTube {
    public TileEntityVacuumHub() {

    }

    @Override
    public boolean isOverflowing() {
        return false;
    }

    @Override
    public boolean canInsertItem(ItemStack item, ForgeDirection dir) {
        return false;
    }

    @Override
    public boolean insertItem(ItemStack item, ForgeDirection dir) {
        return false;
    }

    @Override
    public boolean isInput(ForgeDirection dir) {
        return false;
    }

    @Override
    public boolean isOutput(ForgeDirection dir) {
        return false;
    }

    @Override
    public void disconnect(ForgeDirection dir) {

    }

    @Override
    public boolean addInput(ForgeDirection input) {
        return false;
    }

    @Override
    public boolean addOutput(ForgeDirection output) {
        return false;
    }
}
