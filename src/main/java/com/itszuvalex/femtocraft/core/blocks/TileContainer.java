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

package com.itszuvalex.femtocraft.core.blocks;

import com.itszuvalex.femtocraft.core.items.CoreItemBlock;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class TileContainer extends BlockContainer {
    protected static boolean shouldDrop = true;

    public TileContainer(Material par2Material) {
        super(par2Material);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityBase();
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5,
                           int par6) {
        if (shouldDrop) {
            TileEntity te = par1World.getTileEntity(par2, par3, par4);
            if (te instanceof TileEntityBase) {
                TileEntityBase tile = (TileEntityBase) te;

                ItemStack stack = new ItemStack(par5);
                Item item = stack.getItem();
                if ((item instanceof CoreItemBlock)
                    && ((CoreItemBlock) item).hasItemNBT()) {
                    stack.stackTagCompound = new NBTTagCompound();
                    tile.saveInfoToItemNBT(stack.stackTagCompound);
                }

                EntityItem spawn = new EntityItem(par1World, par2 + .5d,
                        par3 + .5d, par4 + .5d, stack);
                spawn.delayBeforeCanPickup = 10;
                par1World.spawnEntityInWorld(spawn);
            }
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3,
                                    int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
                                    float par8, float par9) {
        TileEntity te = par1World.getTileEntity(par2, par3, par4);
        if (te instanceof TileEntityBase) {
            if (((TileEntityBase) te).canPlayerUse(par5EntityPlayer)) {
                return ((TileEntityBase) te).onSideActivate(par5EntityPlayer, par6);
            } else {
                par5EntityPlayer.addChatMessage(new ChatComponentText(
                        ((TileEntityBase) te).getOwner() + " is the owner of this block."));
                return true;
            }
        }
        return super.onBlockActivated(par1World, par2, par3, par4,
                par5EntityPlayer, par6, par7, par8, par9);
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
                                EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {

        if (!par1World.isRemote) {
            TileEntity te = par1World.getTileEntity(par2, par3, par4);
            if (te instanceof TileEntityBase) {
                if (par5EntityLivingBase == null) {
                    return;
                }
                if (par5EntityLivingBase instanceof EntityPlayerMP) {
                    Item item = par6ItemStack.getItem();
                    if ((item instanceof CoreItemBlock)
                        && (((CoreItemBlock) item).hasItemNBT())) {
                        ((TileEntityBase) te)
                                .loadInfoFromItemNBT(par6ItemStack.stackTagCompound);
                    }
                    if (((TileEntityBase) te).getOwner() == null
                        || ((TileEntityBase) te).getOwner().isEmpty()) {
                        ((TileEntityBase) te)
                                .setOwner(par5EntityLivingBase.getCommandSenderName());
                    }
                }
            }
        }
        super.onBlockPlacedBy(par1World, par2, par3, par4,
                par5EntityLivingBase, par6ItemStack);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityBase) {
            if (!((TileEntityBase) te).canPlayerUse(player)) {
                player.addChatMessage(new ChatComponentText(
                        ((TileEntityBase) te).getOwner() + " is the owner of this block."));
                return false;
            }
        }
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }


    //    @Override
//    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x,
//                                       int y, int z) {
//        TileEntity te = world.getTileEntity(x, y, z);
//        if (te instanceof TileEntityBase) {
//            if (!((TileEntityBase) te).canPlayerUse(player)) {
//                player.addChatMessage(((TileEntityBase) te).getOwner() + " is the owner of this block.");
//                return false;
//            }
//        }
//        return super.removeBlockByPlayer(world, player, x, y, z);
//    }


    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te instanceof TileEntityBase && entity instanceof EntityPlayer
               && ((TileEntityBase) te).canPlayerUse((EntityPlayer) entity)
               && super.canEntityDestroy(world, x, y, z, entity);
    }
}
