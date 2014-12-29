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

package com.itszuvalex.femtocraft.transport.liquids.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.transport.liquids.tiles.TileEntitySuctionPipe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockSuctionPipe extends TileContainer {
    public IIcon center;
    public IIcon center_blackout;
    public IIcon connector;
    public IIcon connector_blackout;
    public IIcon connector_tank;

    public BlockSuctionPipe() {
        super(Material.iron);
        setBlockName("BlockSuctionPipe");
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockBounds();
    }

    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 6.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 10.0D / 16.0D;
    }

        @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftSuctionPipeRenderID();
    }

    @Override
    public void addCollisionBoxesToList(World par1World, int x, int y, int z,
                                        AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {

        super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB,
                par6List, par7Entity);

        TileEntity tile = par1World.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntitySuctionPipe)) {
            return;
        }
        TileEntitySuctionPipe pipe = (TileEntitySuctionPipe) tile;

        for (int i = 0; i < 6; ++i) {
            if (pipe.neighbors[i] == null) {
                continue;
            }
            AxisAlignedBB bb = boundingBoxForDirection(
                    ForgeDirection.getOrientation(i), x, y, z);
            if (par5AxisAlignedBB.intersectsWith(bb)) {
                par6List.add(bb);
            }
        }
    }

    protected AxisAlignedBB boundingBoxForDirection(ForgeDirection dir, int x,
                                                    int y, int z) {
        double minX = 6.f / 16.d;
        double minY = 6.f / 16.d;
        double minZ = 6.f / 16.d;
        double maxX = 10.f / 16.d;
        double maxY = 10.f / 16.d;
        double maxZ = 10.f / 16.d;

        switch (dir) {
            case UP:
                maxY = 1;
                break;
            case DOWN:
                minY = 0;
                break;
            case NORTH:
                minZ = 0;
                break;
            case SOUTH:
                maxZ = 1;
                break;
            case EAST:
                maxX = 1;
                break;
            case WEST:
                minX = 0;
                break;
            default:
                break;
        }

        return AxisAlignedBB.getBoundingBox((double) x + minX,
                (double) y + minY, (double) z + minZ, (double) x + maxX,
                (double) y + maxY, (double) z + maxZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
                                                         int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox((double) x + 6.f / 16.f,
                (double) y + 6.f / 16.f, (double) z + 6.f / 16.f,
                (double) x + 10.f / 16.f, (double) y + 10.f / 16.f,
                (double) z + 10.f / 16.f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x,
                                                        int y, int z) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                (double) x + 6.f / 16.f, (double) y + 6.f / 16.f,
                (double) z + 6.f / 16.f, (double) x + 10.f / 16.f,
                (double) y + 10.f / 16.f, (double) z + 10.f / 16.f);

        TileEntity tile = par1World.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntitySuctionPipe)) {
            return box;
        }
        TileEntitySuctionPipe pipe = (TileEntitySuctionPipe) tile;

        for (int i = 0; i < 6; ++i) {
            if (pipe.neighbors[i] == null) {
                continue;
            }
            box = box.func_111270_a(boundingBoxForDirection(
                    ForgeDirection.getOrientation(i), x, y, z));
        }

        return box;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
                                           int x, int y, int z) {
        TileEntity tile = par1iBlockAccess.getTileEntity(x, y, z);
        if (tile == null) {
            return;
        }
        if (!(tile instanceof TileEntitySuctionPipe)) {
            return;
        }

        double minX = 6.d / 16.d;
        double minY = 6.d / 16.d;
        double minZ = 6.d / 16.d;
        double maxX = 10.d / 16.d;
        double maxY = 10.d / 16.d;
        double maxZ = 10.d / 16.d;
        TileEntitySuctionPipe pipe = (TileEntitySuctionPipe) tile;

        for (int i = 0; i < 6; ++i) {
            if (pipe.neighbors[i] == null) {
                continue;
            }
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            switch (dir) {
                case UP:
                    maxY = 1;
                    break;
                case DOWN:
                    minY = 0;
                    break;
                case NORTH:
                    minZ = 0;
                    break;
                case SOUTH:
                    maxZ = 1;
                    break;
                case EAST:
                    maxX = 1;
                    break;
                case WEST:
                    minX = 0;
                    break;
                default:
                    break;
            }
        }

        setBlockBounds((float) minX, (float) minY, (float) minZ, (float) maxX,
                (float) maxY, (float) maxZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        center = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                               + ":" + "BlockSuctionPipe_center");
        center_blackout = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                        + ":" + "BlockSuctionPipe_center_blackout");
        connector = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                  + ":" + "BlockSuctionPipe_connector");
        connector_blackout = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                           + ":" + "BlockSuctionPipe_connector_blackout");
        connector_tank = par1IconRegister.registerIcon(Femtocraft.ID()
                                                               .toLowerCase() + ":" +
                                                       "BlockSuctionPipe_connector_tank");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySuctionPipe();
    }

}
