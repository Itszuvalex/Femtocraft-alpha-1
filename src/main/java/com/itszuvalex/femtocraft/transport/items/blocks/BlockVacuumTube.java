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

package com.itszuvalex.femtocraft.transport.items.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.IInterfaceDevice;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockVacuumTube extends TileContainer {
    public IIcon indicatorIcon;
    public IIcon straightIcon;
    public IIcon straightInsetIcon;
    public IIcon straightInsetIcon_blackout;
    public IIcon turnIcon;
    public IIcon turnInsetIcon;
    public IIcon turnInsetIcon_blackout;
    public IIcon endIcon;
    public IIcon endInsetIcon;

    public BlockVacuumTube() {
        super(Material.iron);
        setBlockName("BlockVacuumTube");
        setHardness(3.5f);
        setStepSound(Block.soundTypeMetal);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockBounds();
    }

    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 4.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 12.0D / 16.0D;
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           Block par5, int par6) {

        TileEntity tile = par1World.getTileEntity(par2, par3, par4);
        if (tile != null) {
            if (tile instanceof TileEntityVacuumTube) {
                TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;

                tube.onBlockBreak();
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
                                EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        // tiles tile = par1World.getBlockTileEntity(par2, par3, par4);
        // if(tile != null)
        // {
        // if(tile instanceof TileEntityVacuumTube)
        // {
        // TileEntityVacuumTube tube = (TileEntityVacuumTube)tile;
        //
        // tube.searchForMissingConnection();
        // }
        // }
        super.onBlockPlacedBy(par1World, par2, par3, par4,
                par5EntityLivingBase, par6ItemStack);
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3,
                                    int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
                                    float par8, float par9) {

        if (par1World.isRemote) {
            return true;
        }

        ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
        if (item != null && item.getItem() instanceof IInterfaceDevice) {
            TileEntity tile = par1World.getTileEntity(par2, par3, par4);
            if (tile != null) {
                if (tile instanceof TileEntityVacuumTube) {
                    TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;
                    tube.searchForConnection();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityVacuumTube();
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {

        super.onBlockAdded(par1World, par2, par3, par4);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftVacuumTubeRenderID();
    }

    @Override
    public void addCollisionBoxesToList(World par1World, int x, int y, int z,
                                        AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {

        super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB,
                par6List, par7Entity);

        TileEntity tile = par1World.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityVacuumTube)) {
            return;
        }
        TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;

        AxisAlignedBB inputBB = boundingBoxForDirection(tube.getInput(), x, y,
                z);
        if (par5AxisAlignedBB.intersectsWith(inputBB)) {
            par6List.add(inputBB);
        }

        AxisAlignedBB outputBB = boundingBoxForDirection(tube.getOutput(), x,
                y, z);
        if (par5AxisAlignedBB.intersectsWith(outputBB)) {
            par6List.add(outputBB);
        }

    }

    private AxisAlignedBB boundingBoxForDirection(ForgeDirection dir, int x,
                                                  int y, int z) {
        double minX = getBlockBoundsMinX();
        double minY = getBlockBoundsMinY();
        double minZ = getBlockBoundsMinZ();

        double maxX = getBlockBoundsMaxX();
        double maxY = getBlockBoundsMaxY();
        double maxZ = getBlockBoundsMaxZ();

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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
                                                         int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox((double) x + 4.d / 16.d,
                (double) y + 4.d / 16.d, (double) z + 4.d / 16.d,
                (double) x + 12.d / 16.d, (double) y + 12.d / 16.d,
                (double) z + 12.d / 16.d);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x,
                                                        int y, int z) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                (double) x + getBlockBoundsMinX(),
                (double) y + getBlockBoundsMinY(),
                (double) z + getBlockBoundsMinZ(),
                (double) x + getBlockBoundsMaxX(),
                (double) y + getBlockBoundsMaxY(),
                (double) z + getBlockBoundsMaxZ());

        TileEntity tile = par1World.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityVacuumTube)) {
            return box;
        }
        TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;

        AxisAlignedBB inputBB = boundingBoxForDirection(tube.getInput(), x, y,
                z);
        AxisAlignedBB outputBB = boundingBoxForDirection(tube.getOutput(), x,
                y, z);

        return box.func_111270_a(inputBB).func_111270_a(outputBB);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }


    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
                                           int x, int y, int z) {
        TileEntity tile = par1iBlockAccess.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityVacuumTube)) {
            return;
        }
        TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;

        boundingBoxForDirection(tube.getInput(), x, y, z);
        boundingBoxForDirection(tube.getOutput(), x, y, z);

        double minX = 4.d / 16.d;
        double minY = 4.d / 16.d;
        double minZ = 4.d / 16.d;
        double maxX = 12.d / 16.d;
        double maxY = 12.d / 16.d;
        double maxZ = 12.d / 16.d;

        switch (tube.getInput()) {
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

        switch (tube.getOutput()) {
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

        setBlockBounds((float) minX, (float) minY, (float) minZ, (float) maxX,
                (float) maxY, (float) maxZ);
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
                                          int par4, Entity par5Entity) {
        if (par5Entity instanceof EntityItem) {
            TileEntity tile = par1World.getTileEntity(par2, par3, par4);
            if (!(tile instanceof TileEntityVacuumTube)) {
                return;
            }
            TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;
            tube.OnItemEntityCollision((EntityItem) par5Entity);
        }
    }

    @Override
    public void onPostBlockPlaced(World par1World, int par2, int par3,
                                  int par4, int par5) {
        TileEntity tile = par1World.getTileEntity(par2, par3, par4);
        if (tile != null) {
            if (tile instanceof TileEntityVacuumTube) {
                ((TileEntityVacuumTube) tile).searchForFullConnections();
            }
        }
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        indicatorIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                              .toLowerCase() + ":" + "VacuumTube_indicator");
        straightIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                             .toLowerCase() + ":" + "VacuumTube_side_straight");
        straightInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                  .toLowerCase() + ":" +
                                                          "VacuumTube_side_straight_inset");
        straightInsetIcon_blackout = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                           .toLowerCase() + ":" +
                                                                   "VacuumTube_side_straight_inset_blackout");
        turnIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                 + ":" + "VacuumTube_side_curved");
        turnInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                              .toLowerCase() + ":" + "VacuumTube_side_curved_inset");
        turnInsetIcon_blackout = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                       .toLowerCase() + ":" +
                                                               "VacuumTube_side_curved_inset_blackout");
        endIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                + ":" + "VacuumTube_end");
        endInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                             .toLowerCase() + ":" + "VacuumTube_end_inset");
    }


    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileEntityVacuumTube) {
                TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;

                // tube.validateConnections();
                // tube.searchForMissingConnection();
                tube.onNeighborTileChange();
            }
        }
        super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
    }
}
