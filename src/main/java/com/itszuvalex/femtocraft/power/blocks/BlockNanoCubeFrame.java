/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package com.itszuvalex.femtocraft.power.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockNanoCube;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCubeFrame;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockNanoCubeFrame extends TileContainer {
    public Icon formedCorners[];
    public Icon formedSides[];

    public BlockNanoCubeFrame(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockNanoCubeFrame");
        formedCorners = new Icon[4];
        formedSides = new Icon[4];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
                                int par3, int par4, int par5) {
        TileEntity te = par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityNanoCubeFrame) {
            TileEntityNanoCubeFrame frame = (TileEntityNanoCubeFrame) te;
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
        int ydif = y - info.y() - 1;
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
        if (xdif == -1) {
            if (ydif == -1) {
                return formedCorners[2];
            }
            if (ydif == 0) {
                return formedSides[3];
            }
            if (ydif == 1) {
                return formedCorners[0];
            }
        }
        if (xdif == 0) {
            if (ydif == -1) {
                return formedSides[1];
            }
            if (ydif == 1) {
                return formedSides[0];
            }
        }
        if (xdif == 1) {
            if (ydif == -1) {
                return formedCorners[3];
            }
            if (ydif == 0) {
                return formedSides[2];
            }
            if (ydif == 1) {
                return formedCorners[1];
            }
        }

        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                          + ":" + "BlockNanoCubeFrame_unformed");

        formedCorners[0] = par1IconRegister.registerIcon(Femtocraft.ID
                                                                 .toLowerCase() + ":" + "BlockNanoCubeFrame_top_left");
        formedCorners[1] = par1IconRegister.registerIcon(Femtocraft.ID
                                                                 .toLowerCase() + ":" + "BlockNanoCubeFrame_top_right");
        formedCorners[2] = par1IconRegister.registerIcon(Femtocraft.ID
                                                                 .toLowerCase() + ":" + "BlockNanoCubeFrame_bot_left");
        formedCorners[3] = par1IconRegister.registerIcon(Femtocraft.ID
                                                                 .toLowerCase() + ":" + "BlockNanoCubeFrame_bot_right");

        formedSides[0] = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockNanoCubeFrame_top");
        formedSides[1] = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockNanoCubeFrame_bot");
        formedSides[2] = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockNanoCubeFrame_right");
        formedSides[3] = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockNanoCubeFrame_left");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityNanoCubeFrame();
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

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           int par5, int par6) {
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityNanoCubeFrame) {
            MultiBlockInfo info = ((TileEntityNanoCubeFrame) te).getInfo();
            MultiBlockNanoCube.instance.breakMultiBlock(par1World, info.x(),
                                                        info.y(), info.z());
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
