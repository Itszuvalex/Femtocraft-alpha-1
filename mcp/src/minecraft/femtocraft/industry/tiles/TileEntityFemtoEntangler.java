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

package femtocraft.industry.tiles;

import femtocraft.FemtocraftGuiHandler;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityFemtoEntangler extends TileEntityBaseEntityNanoEnmesher {
    public static final int outputSlot = 13;
    public static final int inventorySize = 14;
    public static int powerToCook_default = 160;
    public static float tickMultiplier_default = .5f;

    public TileEntityFemtoEntangler() {
        super();
        setTechLevel(EnumTechLevel.FEMTO);
        setMaxStorage(10000);
        inventory.setInventorySize(inventorySize);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.FemtoEntanglerGuiID;
    }

    @Override
    protected float getTickMultiplier() {
        return tickMultiplier_default;
    }

    @Override
    protected int getPowerToCook() {
        return powerToCook_default;
    }

    @Override
    protected EnumTechLevel getTechLevel() {
        return EnumTechLevel.FEMTO;
    }

    @Override
    protected int getOutputSlotIndex() {
        return outputSlot;
    }
}