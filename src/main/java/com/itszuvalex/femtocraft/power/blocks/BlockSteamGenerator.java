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
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockMagnetohydrodynamicGenerator;
import com.itszuvalex.femtocraft.power.tiles.TileEntitySteamGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/25/14.
 */
public class BlockSteamGenerator extends TileContainer {
    public BlockSteamGenerator(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setUnlocalizedName("BlockSteamGenerator");
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "BlockSteamGenerator");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntitySteamGenerator();
    }

    /*
    * (non-Javadoc)
    *
    * @see
    * net.minecraft.block.Block#onPostBlockPlaced(net.minecraft.world.World,
    * int, int, int, int)
    */
    @Override
    public void onPostBlockPlaced(World par1World, int par2, int par3,
                                  int par4, int par5) {
        MultiBlockMagnetohydrodynamicGenerator.instance.formMultiBlockWithBlock(par1World, par2,
                par3, par4);
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    /*
         * (non-Javadoc)
         *
         * @see
         * TileContainer#breakBlock(net.minecraft.world.World
         * , int, int, int, int, int)
         */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           int par5, int par6) {
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntitySteamGenerator) {
            MultiBlockInfo info = ((IMultiBlockComponent) te).getInfo();
            MultiBlockMagnetohydrodynamicGenerator.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
