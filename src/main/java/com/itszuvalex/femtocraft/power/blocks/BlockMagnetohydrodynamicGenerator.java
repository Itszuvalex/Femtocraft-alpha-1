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
import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockMagnetohydrodynamicGenerator;
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagnetohydrodynamicGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/25/14.
 */
public class BlockMagnetohydrodynamicGenerator extends TileContainer {
    private IIcon[][] formedSides;


    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(
                Femtocraft.ID().toLowerCase() + ":" + "BlockMagnetohydrodynamicGenerator_unformed");
        for (int i = 0; i < formedSides.length; ++i) {
            for (int j = 0; j < formedSides[i].length; ++j) {
                formedSides[j][i] = par1IconRegister.registerIcon(
                        Femtocraft.ID().toLowerCase() + ":" + "BlockMagnetohydrodynamicGenerator_" + i + "_" + j);
            }
        }
    }

    public BlockMagnetohydrodynamicGenerator() {
        super(Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockName("BlockMagnetohydrodynamicGenerator");
        formedSides = new IIcon[3][];
        for (int i = 0; i < formedSides.length; ++i) {
            formedSides[i] = new IIcon[3];
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityMagnetohydrodynamicGenerator();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess par1iBlockAccess, int par2,
                         int par3, int par4, int par5) {
        TileEntity te = par1iBlockAccess.getTileEntity(par2, par3, par4);
        if (te instanceof TileEntityMagnetohydrodynamicGenerator) {
            TileEntityMagnetohydrodynamicGenerator frame = (TileEntityMagnetohydrodynamicGenerator) te;
            if (frame.isValidMultiBlock()) {
                MultiBlockInfo info = frame.getInfo();
                ForgeDirection dir = ForgeDirection.getOrientation(par5);
                return iconForSide(info, dir, par2, par3, par4);
            }
        }
        return super.getIcon(par1iBlockAccess, par2, par3, par4, par5);
    }

    private IIcon iconForSide(MultiBlockInfo info, ForgeDirection dir, int x,
                              int y, int z) {
        int xdif = x - info.x();
        int ydif = y - info.y();
        int zdif = z - info.z();

        switch (dir) {
            case UP:
                return iconFromGrid(xdif, -zdif);
            case DOWN:
                return iconFromGrid(xdif, -zdif);
            case NORTH:
                return iconFromGrid(-xdif, ydif);
            case SOUTH:
                return iconFromGrid(xdif, ydif);
            case EAST:
                return iconFromGrid(-zdif, ydif);
            case WEST:
                return iconFromGrid(zdif, ydif);
            default:
                return this.blockIcon;
        }
    }

    private IIcon iconFromGrid(int xdif, int ydif) {
        try {
            return formedSides[xdif + 1][ydif + 1];
        } catch (IndexOutOfBoundsException ignored) {
        }
        return this.blockIcon;
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
                           Block par5, int par6) {
        TileEntity te = par1World.getTileEntity(par2, par3, par4);
        if (te instanceof TileEntityMagnetohydrodynamicGenerator) {
            MultiBlockInfo info = ((IMultiBlockComponent) te).getInfo();
            MultiBlockMagnetohydrodynamicGenerator.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
