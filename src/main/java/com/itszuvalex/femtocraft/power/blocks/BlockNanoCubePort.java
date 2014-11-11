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
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockNanoCube;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCubePort;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNanoCubePort extends TileContainer {
    public IIcon portInput;
    public IIcon portOutput;

    public BlockNanoCubePort() {
        super(Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockName("BlockNanoCubePort");
        setTickRandomly(true);
    }

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        TileEntity te = par1World.getTileEntity(x, y, z);
        if (te instanceof TileEntityNanoCubePort && ((TileEntityNanoCubePort) te).isValidMultiBlock() &&
            ((TileEntityNanoCubePort) te).output) {
            double spawnX =
                    x + getBlockBoundsMinX() + par5Random.nextFloat() * (getBlockBoundsMaxX() - getBlockBoundsMinX());
            double spawnY =
                    y + getBlockBoundsMinY() + par5Random.nextFloat() * (getBlockBoundsMaxY() - getBlockBoundsMinY());
            double spawnZ =
                    z + getBlockBoundsMinZ() + par5Random.nextFloat() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ());

            RenderUtils.spawnParticle(par1World, RenderUtils.NANO_POWER_PARTICLE, spawnX, spawnY, spawnZ);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess par1iBlockAccess, int par2,
                         int par3, int par4, int par5) {
        TileEntity te = par1iBlockAccess.getTileEntity(par2, par3, par4);
        if (te instanceof TileEntityNanoCubePort) {
            TileEntityNanoCubePort port = (TileEntityNanoCubePort) te;
            return port.output ? portOutput : portInput;
        }
        return super.getIcon(par1iBlockAccess, par2, par3, par4, par5);
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
        MultiBlockNanoCube.instance.formMultiBlockWithBlock(par1World, par2,
                par3, par4);
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraft.block.Block#registerIcons(net.minecraft.client.renderer
     * .texture.IconRegister)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = portInput = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                                   + ":" + "BlockNanoCubePort_input");
        portOutput = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                   + ":" + "BlockNanoCubePort_output");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileContainer#createNewTileEntity(net.minecraft
     * .world.World)
     */
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityNanoCubePort();
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
        if (te instanceof TileEntityNanoCubePort) {
            MultiBlockInfo info = ((TileEntityNanoCubePort) te).getInfo();
            MultiBlockNanoCube.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
