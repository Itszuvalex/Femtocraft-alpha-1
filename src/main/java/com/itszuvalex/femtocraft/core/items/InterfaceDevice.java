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

package com.itszuvalex.femtocraft.core.items;

import com.itszuvalex.femtocraft.api.IInterfaceDevice;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class InterfaceDevice extends Item implements IInterfaceDevice {
    private EnumTechLevel level;

    public InterfaceDevice(int par1, EnumTechLevel level) {
        super(par1);
        this.level = level;
    }

    @Override
    public EnumTechLevel getInterfaceLevel() {
        return level;
    }

    @Override
    public boolean shouldPassSneakingClickToBlock(World par2World, int par4,
                                                  int par5, int par6) {
        return true;
    }
}