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
import com.itszuvalex.femtocraft.power.plasma.IFusionReactorCore;
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoStellaratorFocus;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockFemtoStellaratorFocus extends TileContainer {
    public Icon outsideIcon;
    public Icon insideIcon;

    public BlockFemtoStellaratorFocus(int par1) {
        super(par1, Material.iron);
        setUnlocalizedName("BlockStellaratorFocus");
        setCreativeTab(Femtocraft.femtocraftTab());
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftStellaratorFocusRenderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3,
                                      int par4, int par5) {
        if (!canBlockStay(par1World, par2, par3, par4)) {
            breakBlock(par1World, par2, par3, par4, blockID,
                    par1World.getBlockMetadata(par2, par3, par4));
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return canBlockStay(par1World, par2, par3, par4);
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        return hasCoreAsNeighbor(par1World, par2, par3, par4);
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
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = outsideIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                .toLowerCase() + ":" + "BlockFemtoStellaratorFocus");
        insideIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase
                () + ":" + "BlockFemtoStellaratorHollowInternals");
    }

    private boolean hasCoreAsNeighbor(World par1World, int par2, int par3, int par4) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = par1World.getBlockTileEntity(par2 + dir.offsetX,
                    par3 + dir.offsetY,
                    par4 + dir.offsetZ);
            if (te instanceof IFusionReactorCore) {
                return true;
            }
        }

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityFemtoStellaratorFocus();
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
        if (te instanceof TileEntityFemtoStellaratorFocus) {
            MultiBlockInfo info = ((TileEntityFemtoStellaratorFocus) te).getInfo();
            MultiBlockFemtoStellarator.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
