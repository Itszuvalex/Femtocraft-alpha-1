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
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockNanoFissionReactor;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorHousing;
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
 * Created by Christopher Harris (Itszuvalex) on 7/6/14.
 */
public class BlockNanoFissionReactorHousing extends TileContainer {
    Icon[][] formedSides;

    public BlockNanoFissionReactorHousing(int id) {
        super(id, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setUnlocalizedName("BlockNanoFissionReactorHousing");

        formedSides = new Icon[3][];
        for (int i = 0; i < formedSides.length; ++i) {
            formedSides[i] = new Icon[3];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
                                int par3, int par4, int par5) {
        TileEntity te = par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityNanoFissionReactorHousing) {
            TileEntityNanoFissionReactorHousing frame = (TileEntityNanoFissionReactorHousing) te;
            if (frame.isValidMultiBlock()) {
                MultiBlockInfo info = frame.getInfo();
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
        try {
            int i = xdif + 1;
            int k = ydif + 1;
            return formedSides[i][k];
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
        MultiBlockNanoFissionReactor.instance.formMultiBlockWithBlock(par1World, par2,
                par3, par4);
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                + ":" + "BlockNanoFissionReactorHousing_unformed");

        for (int i = 0; i < formedSides.length; ++i) {
            for (int j = 0; j < formedSides[i].length; ++j) {
                formedSides[i][j] = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "BlockNanoFissionReactorHousing_" + i + "_" + j);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityNanoFissionReactorHousing();
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           int par5, int par6) {
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityNanoFissionReactorHousing) {
            MultiBlockInfo info = ((IMultiBlockComponent) te).getInfo();
            MultiBlockNanoFissionReactor.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}