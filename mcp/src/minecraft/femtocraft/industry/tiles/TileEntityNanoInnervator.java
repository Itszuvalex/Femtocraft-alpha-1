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
import femtocraft.industry.blocks.BlockNanoInnervator;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityNanoInnervator extends TileEntityBaseEntityMicroFurnace {
    public static int maxSmelt_default = 8;
    public static int ticksToCook_default = 60;
    public static int powerToCook_default = 80;

    public TileEntityNanoInnervator() {
        super();
        this.setTechLevel(EnumTechLevel.NANO);
        // TODO: Pull number from configs
        this.setMaxStorage(10000);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.NanoInnervatorGuiID;
    }

    @Override
    protected int getMaxSimultaneousSmelt() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return maxSmelt_default;
    }

    @Override
    protected int getTicksToCook() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return ticksToCook_default;
    }

    @Override
    protected int getPowerToCook() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return powerToCook_default;
    }

    @Override
    protected void updateBlockState(boolean working) {
        BlockNanoInnervator.updateFurnaceBlockState(working, worldObj, xCoord,
                                                    yCoord, zCoord);
    }
}
