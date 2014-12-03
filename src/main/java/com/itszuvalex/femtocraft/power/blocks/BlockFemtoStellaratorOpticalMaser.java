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
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorCore;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockFemtoStellarator;
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoStellaratorOpticalMaser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFemtoStellaratorOpticalMaser extends TileContainer {
    public IIcon activeIcon;

    public BlockFemtoStellaratorOpticalMaser() {
        super(Material.iron);
        setBlockName("BlockStellaratorOpticalMaser");
        setCreativeTab(Femtocraft.femtocraftTab());
    }

    @Override
    public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        TileEntity te = par1IBlockAccess.getTileEntity(par2, par3, par4);
        if (te instanceof TileEntityFemtoStellaratorOpticalMaser) {
            TileEntityFemtoStellaratorOpticalMaser maser = (TileEntityFemtoStellaratorOpticalMaser) te;
            if (maser.isIgniting() || maser.isSustaining()) {
                return activeIcon;
            }
        }
        return blockIcon;
    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3,
                                      int par4, Block par5) {
        if (!canBlockStay(par1World, par2, par3, par4)) {
            breakBlock(par1World, par2, par3, par4, par5,
                    par1World.getBlockMetadata(par2, par3, par4));
            par1World.setBlockToAir(par2, par3, par4);
        }
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
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
        if (te instanceof TileEntityFemtoStellaratorOpticalMaser) {
            MultiBlockInfo info = ((TileEntityFemtoStellaratorOpticalMaser) te).getInfo();
            MultiBlockFemtoStellarator.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityFemtoStellaratorOpticalMaser();
    }

    private boolean hasFocusAsNeighbor(World par1World, int par2, int par3,
                                       int par4) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (par1World.getBlock(par2 + dir.offsetX, par3 + dir.offsetY,
                    par4 + dir.offsetZ) == Femtocraft.blockStellaratorFocus()) {
                TileEntity te = par1World.getTileEntity(par2 + 2 * dir
                                .offsetX,
                        par3 + 2 * dir
                                .offsetY,
                        par4 + 2 * dir
                                .offsetZ
                );
                if (te instanceof IFusionReactorCore) {
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return canBlockStay(par1World, par2, par3, par4);
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        return hasFocusAsNeighbor(par1World, par2, par3, par4);
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
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(
                Femtocraft.ID().toLowerCase() + ":" + "BlockFemtoStellaratorOpticalMaser");
        activeIcon = par1IconRegister.registerIcon(
                Femtocraft.ID().toLowerCase() + ":" + "BlockFemtoStellaratorOpticalMaser_active");
    }
}
