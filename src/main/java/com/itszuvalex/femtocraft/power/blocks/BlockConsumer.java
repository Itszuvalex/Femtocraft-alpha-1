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

package com.itszuvalex.femtocraft.power.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPowerConsumerTest;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockConsumer extends BlockPowerContainer {

    public BlockConsumer(int par1, Material par2Material) {
        super(par1, par2Material);
        setCreativeTab(Femtocraft.femtocraftTab);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityPowerConsumerTest();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

}
