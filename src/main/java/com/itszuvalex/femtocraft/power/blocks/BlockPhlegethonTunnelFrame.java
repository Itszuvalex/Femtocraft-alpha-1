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
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockPhlegethonTunnel;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelFrame;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/12/14.
 */
public class BlockPhlegethonTunnelFrame extends TileContainer {
    Icon[][] sideIcons_inactive;
    Icon[][] topIcons_inactive;
    Icon[][] botIcons_inactive;
    Icon[][] sideIcons_active;
    Icon[][] topIcons_active;
    Icon[][] botIcons_active;

    public BlockPhlegethonTunnelFrame(int id) {
        super(id, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockPhlegethonTunnelFrame");
        sideIcons_inactive = makeEmptyIconArray();
        sideIcons_active = makeEmptyIconArray();
        topIcons_inactive = makeEmptyIconArray();
        topIcons_active = makeEmptyIconArray();
        botIcons_inactive = makeEmptyIconArray();
        botIcons_active = makeEmptyIconArray();
    }

    private Icon[][] makeEmptyIconArray() {
        Icon[][] newArray = new Icon[3][];
        for (int i = 0; i < newArray.length; ++i) {
            newArray[i] = new Icon[3];
        }
        return newArray;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "BlockPhlegethonTunnelFrame_unformed");
        registerIcons(sideIcons_active, "side_active", par1IconRegister);
        registerIcons(sideIcons_inactive, "side_inactive", par1IconRegister);
        registerIcons(topIcons_inactive, "top_inactive", par1IconRegister);
        registerIcons(topIcons_active, "top_active", par1IconRegister);
        registerIcons(botIcons_inactive, "bot_inactive", par1IconRegister);
        registerIcons(botIcons_active, "bot_active", par1IconRegister);
    }

    private void registerIcons(Icon[][] array, String name, IconRegister register) {
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[i].length; ++j) {
                array[j][i] = register.registerIcon(
                        Femtocraft.ID.toLowerCase() + ":" + "BlockPhlegethonTunnel_" + name + "_" + i + "_" + j);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
                                int par3, int par4, int par5) {
        TileEntity te = par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityPhlegethonTunnelFrame) {
            TileEntityPhlegethonTunnelFrame frame = (TileEntityPhlegethonTunnelFrame) te;
            if (frame.isValidMultiBlock()) {
                MultiBlockInfo info = frame.getInfo();
                ForgeDirection dir = ForgeDirection.getOrientation(par5);
                TileEntityPhlegethonTunnelCore core = (TileEntityPhlegethonTunnelCore) par1iBlockAccess
                        .getBlockTileEntity(info.x(), info.y(), info.z());
                boolean active = core != null && core.isActive();
                return iconForSide(info, dir, active, par2, par3, par4);
            }
        }
        return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
    }

    private Icon iconForSide(MultiBlockInfo info, ForgeDirection dir, boolean active, int x,
                             int y, int z) {
        int xdif = x - info.x();
        int ydif = y - info.y();
        int zdif = z - info.z();

        switch (dir) {
            case UP:
                return iconFromGrid(dir, active, xdif, -zdif);
            case DOWN:
                return iconFromGrid(dir, active, xdif, -zdif);
            case NORTH:
                return iconFromGrid(dir, active, -xdif, ydif);
            case SOUTH:
                return iconFromGrid(dir, active, xdif, ydif);
            case EAST:
                return iconFromGrid(dir, active, -zdif, ydif);
            case WEST:
                return iconFromGrid(dir, active, zdif, ydif);
            default:
                return this.blockIcon;
        }
    }

    private Icon iconFromGrid(ForgeDirection side, boolean active, int xdif, int ydif) {
        try {
            switch (side) {
                case UP:
                    return active ? topIcons_active[xdif + 1][ydif + 1] : topIcons_inactive[xdif + 1][ydif + 1];
                case DOWN:
                    return active ? botIcons_active[xdif + 1][ydif + 1] : botIcons_inactive[xdif + 1][ydif + 1];
                default:
                    return active ? sideIcons_inactive[xdif + 1][ydif + 1] : sideIcons_inactive[xdif + 1][ydif + 1];
            }
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
        MultiBlockPhlegethonTunnel.instance.formMultiBlockWithBlock(par1World, par2,
                par3, par4);
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityPhlegethonTunnelFrame();
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
        if (te instanceof TileEntityPhlegethonTunnelFrame) {
            MultiBlockInfo info = ((TileEntityPhlegethonTunnelFrame) te).getInfo();
            MultiBlockPhlegethonTunnel.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

}