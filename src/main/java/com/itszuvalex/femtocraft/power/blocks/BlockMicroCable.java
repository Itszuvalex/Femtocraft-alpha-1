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
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCable;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
import java.util.Random;

public class BlockMicroCable extends BlockPowerContainer {
    public IIcon coreBorder;
    public IIcon connector;
    public IIcon coil;
    public IIcon coilEdge;
    public IIcon border;

    public BlockMicroCable(Material par2Material) {
        super(par2Material);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockName("blockMicroCable");
        setHardness(1.0f);
        setStepSound(Block.soundTypeMetal);
        setBlockBounds();
        setTickRandomly(true);
    }

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        double spawnX =
                x + getBlockBoundsMinX() + par5Random.nextFloat() * (getBlockBoundsMaxX() - getBlockBoundsMinX());
        double spawnY =
                y + getBlockBoundsMinY() + par5Random.nextFloat() * (getBlockBoundsMaxY() - getBlockBoundsMinY());
        double spawnZ =
                z + getBlockBoundsMinZ() + par5Random.nextFloat() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ());

        RenderUtils.spawnParticle(par1World, RenderUtils.MICRO_POWER_PARTICLE, spawnX, spawnY, spawnZ);
    }

    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 4.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 12.0D / 16.0D;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityMicroCable();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.microCableRenderID;
    }

    @Override
    public void addCollisionBoxesToList(World par1World, int x, int y, int z,
                                        AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {

        super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB,
                par6List, par7Entity);

        TileEntity tile = par1World.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityMicroCable)) {
            return;
        }
        TileEntityMicroCable cable = (TileEntityMicroCable) tile;

        for (int i = 0; i < 6; ++i) {
            if (!cable.connections()[i]) {
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
        double minX = 4.f / 16.d;
        double minY = 4.f / 16.d;
        double minZ = 4.f / 16.d;
        double maxX = 12.f / 16.d;
        double maxY = 12.f / 16.d;
        double maxZ = 12.f / 16.d;

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
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x,
                                                        int y, int z) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                (double) x + 4.f / 16.f, (double) y + 4.f / 16.f,
                (double) z + 4.f / 16.f, (double) x + 12.f / 16.f,
                (double) y + 12.f / 16.f, (double) z + 12.f / 16.f);

        TileEntity tile = par1World.getTileEntity(x, y, z);

        if (!(tile instanceof TileEntityMicroCable)) {
            return box;
        }
        TileEntityMicroCable cable = (TileEntityMicroCable) tile;

        for (int i = 0; i < 6; ++i) {
            if (!cable.connections()[i]) {
                continue;
            }
            box = box.func_111270_a(boundingBoxForDirection(
                    ForgeDirection.getOrientation(i), x, y, z));
        }

        return box;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
                                                         int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox((double) x + 4.f / 16.f,
                (double) y + 4.f / 16.f, (double) z + 4.f / 16.f,
                (double) x + 12.f / 16.f, (double) y + 12.f / 16.f,
                (double) z + 12.f / 16.f);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
                                           int x, int y, int z) {

        double minX = 4.d / 16.d;
        double minY = 4.d / 16.d;
        double minZ = 4.d / 16.d;
        double maxX = 12.d / 16.d;
        double maxY = 12.d / 16.d;
        double maxZ = 12.d / 16.d;

        TileEntity tile = par1iBlockAccess.getTileEntity(x, y, z);

        if (!(tile instanceof TileEntityMicroCable)) {
            setBlockBounds((float) minX, (float) minY,
                    (float) minZ, (float) maxX, (float) maxY,
                    (float) maxZ);
            return;
        }

        TileEntityMicroCable cable = (TileEntityMicroCable) tile;

        for (int i = 0; i < 6; ++i) {
            if (!cable.connections()[i]) {
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
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                               .toLowerCase() + ":" + "FemtopowerCableCoil");
        this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                .toLowerCase() + ":" + "FemtopowerCableCoreBorder");
        this.connector = par1IconRegister.registerIcon(Femtocraft.ID()
                                                               .toLowerCase() + ":" + "FemtopowerCableConnector");
        this.coil = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                  + ":" + "FemtopowerCableCoil");
        this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID()
                                                              .toLowerCase() + ":" + "FemtopowerCableCoilEdge");
        this.border = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                    + ":" + "FemtopowerCableBorder");
    }

}
