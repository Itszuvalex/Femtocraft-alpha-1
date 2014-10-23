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
import com.itszuvalex.femtocraft.power.tiles.TileEntityPlasmaConduit;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/12/14.
 */
public class BlockPlasmaConduit extends TileContainer {
    public IIcon center, center_filled;
    public IIcon input, input_filled, output, output_filled;

    public BlockPlasmaConduit() {
        super(Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockName("BlockBlockPlasmaConduit");
        setBlockBounds();
    }

    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 2.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 14.0D / 16.0D;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityPlasmaConduit();
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           Block par5, int par6) {

        TileEntity tile = par1World.getTileEntity(par2, par3, par4);
        if (tile != null) {
            if (tile instanceof TileEntityPlasmaConduit) {
                TileEntityPlasmaConduit conduit = (TileEntityPlasmaConduit) tile;
                conduit.onBlockBreak();
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftPlasmaConduitID;
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase,
                                ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        TileEntity te = par1World.getTileEntity(par2, par3, par4);
        if (te instanceof TileEntityPlasmaConduit) {
            TileEntityPlasmaConduit conduit = (TileEntityPlasmaConduit) te;
            conduit.searchForConnections();
        }
    }

    @Override
    public void addCollisionBoxesToList(World par1World, int x, int y, int z,
                                        AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {

        super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB,
                par6List, par7Entity);

        TileEntity tile = par1World.getTileEntity(x, y, z);
        if (tile instanceof TileEntityPlasmaConduit) {
            TileEntityPlasmaConduit conduit = (TileEntityPlasmaConduit) tile;

            AxisAlignedBB bb = boundingBoxForDirection(
                    conduit.getInputDir(), x, y, z);
            if (par5AxisAlignedBB.intersectsWith(bb)) {
                par6List.add(bb);
            }
            bb = boundingBoxForDirection(
                    conduit.getOutputDir(), x, y, z);
            if (par5AxisAlignedBB.intersectsWith(bb)) {
                par6List.add(bb);
            }
        }
    }

    protected AxisAlignedBB boundingBoxForDirection(ForgeDirection dir, int x,
                                                    int y, int z) {
        double minX = 2.f / 16.d;
        double minY = 2.f / 16.d;
        double minZ = 2.f / 16.d;
        double maxX = 14.f / 16.d;
        double maxY = 14.f / 16.d;
        double maxZ = 14.f / 16.d;

        if (dir != null) {
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

        return AxisAlignedBB.getBoundingBox((double) x + minX,
                (double) y + minY, (double) z + minZ, (double) x + maxX,
                (double) y + maxY, (double) z + maxZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x,
                                                        int y, int z) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                (double) x + 2.f / 16.f, (double) y + 2.f / 16.f,
                (double) z + 2.f / 16.f, (double) x + 14.f / 16.f,
                (double) y + 14.f / 16.f, (double) z + 14.f / 16.f);

        TileEntity tile = par1World.getTileEntity(x, y, z);
        if (tile == null) {
            return box;
        }
        if (!(tile instanceof TileEntityPlasmaConduit)) {
            return box;
        }
        TileEntityPlasmaConduit cable = (TileEntityPlasmaConduit) tile;

        box = box.func_111270_a(boundingBoxForDirection(
                cable.getInputDir(), x, y, z));
        box = box.func_111270_a(boundingBoxForDirection(
                cable.getOutputDir(), x, y, z));

        return box;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
                                                         int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox((double) x + 2.f / 16.f,
                (double) y + 2.f / 16.f, (double) z + 2.f / 16.f,
                (double) x + 14.f / 16.f, (double) y + 14.f / 16.f,
                (double) z + 14.f / 16.f);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
                                           int x, int y, int z) {


        double minX = 2.d / 16.d;
        double minY = 2.d / 16.d;
        double minZ = 2.d / 16.d;
        double maxX = 14.d / 16.d;
        double maxY = 14.d / 16.d;
        double maxZ = 14.d / 16.d;

        TileEntity tile = par1iBlockAccess.getTileEntity(x, y, z);

        if (tile instanceof TileEntityPlasmaConduit) {
            TileEntityPlasmaConduit cable = (TileEntityPlasmaConduit) tile;

            switch (cable.getInputDir()) {
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
            switch (cable.getOutputDir()) {
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
        center = blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                           + ":" + "BlockPlasmaConduit_core");
        center_filled = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                      + ":" + "BlockPlasmaConduit_core_filled");
        input = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                              + ":" + "BlockPlasmaConduit_connector_input");
        input_filled = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                     + ":" + "BlockPlasmaConduit_connector_input_filled");
        output = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                               + ":" + "BlockPlasmaConduit_connector_output");
        output_filled = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                      + ":" + "BlockPlasmaConduit_connector_output_filled");
    }
}