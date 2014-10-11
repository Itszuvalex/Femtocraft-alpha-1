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
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoStellaratorCore;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockFemtoStellaratorCore extends TileContainer {
    public Icon outsideIcon;
    public Icon insideIcon;
    public Icon coreIcon;

    public BlockFemtoStellaratorCore(int id) {
        super(id, Material.iron);
        setUnlocalizedName("BlockStellaratorCore");
        setCreativeTab(Femtocraft.femtocraftTab());
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftStellaratorCoreRenderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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
        outsideIcon = blockIcon = par1IconRegister.registerIcon
                (Femtocraft.ID()
                        .toLowerCase()
                        + ":" +
                        "BlockFemtoStellaratorCore");
        insideIcon = par1IconRegister.registerIcon
                (Femtocraft.ID()
                        .toLowerCase()
                        + ":" +
                        "BlockFemtoStellaratorEnclosedInternals");
        coreIcon = par1IconRegister.registerIcon
                (Femtocraft.ID()
                        .toLowerCase()
                        + ":" +
                        "BlockFemtoStellaratorCore_core");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityFemtoStellaratorCore();
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
        if (te instanceof TileEntityFemtoStellaratorCore) {
            MultiBlockInfo info = ((TileEntityFemtoStellaratorCore) te).getInfo();
            MultiBlockFemtoStellarator.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

}
