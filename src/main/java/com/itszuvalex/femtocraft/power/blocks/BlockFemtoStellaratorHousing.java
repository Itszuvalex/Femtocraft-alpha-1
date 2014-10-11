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
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockFemtoStellarator;
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoStellaratorHousing;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockFemtoStellaratorHousing extends TileContainer {
    public Icon[][] icons;

    public BlockFemtoStellaratorHousing(int par1) {
        super(par1, Material.iron);
        setUnlocalizedName("BlockStellaratorHousing");
        setCreativeTab(Femtocraft.femtocraftTab());
        icons = new Icon[5][];
        for (int i = 0; i < icons.length; ++i) {
            icons[i] = new Icon[5];
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityFemtoStellaratorHousing();
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
        if (te instanceof TileEntityFemtoStellaratorHousing) {
            MultiBlockInfo info = ((TileEntityFemtoStellaratorHousing) te).getInfo();
            MultiBlockFemtoStellarator.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
                                int par3, int par4, int par5) {
        TileEntity te = par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityFemtoStellaratorHousing) {
            TileEntityFemtoStellaratorHousing housing =
                    (TileEntityFemtoStellaratorHousing) te;
            if (housing.isValidMultiBlock()) {
                MultiBlockInfo info = housing.getInfo();
                ForgeDirection dir = ForgeDirection.getOrientation(par5);
                return iconForSide(info, dir, par2, par3, par4);
            }
        }
        return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
    }

    private Icon iconForSide(MultiBlockInfo info, ForgeDirection dir, int x,
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

    private Icon iconFromGrid(int xdif, int ydif) {
        // int i = (Math.abs(xdif + 2)) % icons.length;
        // int k = (Math.abs(ydif + 2)) % icons[i].length;
        try {
            int i = xdif + 2;
            int j = ydif + 2;
            return icons[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
            return this.blockIcon;
        }
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
        //Don't really have to do this, except for if something can
        // place/remove blocks at a distance.  This will be contained within
        // other blocks and should never be teh block to trigger the formation.
        MultiBlockFemtoStellarator.instance.formMultiBlockWithBlock(par1World, par2,
                par3, par4);
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                + ":" + "BlockFemtoStellaratorHousing_unformed");

        for (int x = 0; x < icons.length; ++x) {
            for (int y = 0; y < icons[x].length; ++y) {
                icons[x][y] = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                      .toLowerCase()
                        + ":"
                        + "BlockFemtoStellaratorHousing_"
                        + x
                        + "_"
                        + y);
            }
        }
    }
}
