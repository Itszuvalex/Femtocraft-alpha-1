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

package com.itszuvalex.femtocraft.industry.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityNanoInnervator;
import com.itszuvalex.femtocraft.render.RenderSimpleMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNanoInnervator extends TileContainer {
    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory;
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private final Random furnaceRand = new Random();
    /**
     * True if this is an active furnace, false if idle
     */
    private final boolean isActive;
    @SideOnly(Side.CLIENT)
    private IIcon frontIcon;

    public BlockNanoInnervator(boolean par2) {
        super(Material.iron);
        this.isActive = par2;
        setHardness(3.5f);
        setStepSound(Block.soundTypeMetal);
        setCreativeTab(Femtocraft.femtocraftTab());
        if (par2) {
            setLightLevel(0.875F);
        }
    }

    public static void updateFurnaceBlockState(boolean par0, World par1World,
                                               int par2, int par3, int par4) {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
        keepFurnaceInventory = true;
        TileContainer.shouldDrop_$eq(false);
        if (par0) {
            par1World.setBlock(par2, par3, par4,
                    Femtocraft.blockNanoInnervatorLit());
        } else {
            par1World.setBlock(par2, par3, par4,
                    Femtocraft.blockNanoInnervatorUnlit());
        }
        TileContainer.shouldDrop_$eq(true);
        keepFurnaceInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);

        if (tileentity != null) {
            tileentity.validate();
            par1World.setTileEntity(par2, par3, par4, tileentity);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderSimpleMachine.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int par1, int par2) {
        if (par1 == par2) {
            return frontIcon;
        } else {
            return blockIcon;
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return new ItemStack(Femtocraft.blockNanoInnervatorUnlit());
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Femtocraft.blockNanoInnervatorUnlit());
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to addInput particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3,
                                  int par4, Random par5Random) {
        if (this.isActive) {
            int l = par1World.getBlockMetadata(par2, par3, par4);
            float f = (float) par2 + 0.5F;
            float f1 = (float) par3 + 0.0F + par5Random.nextFloat() * 6.0F
                                             / 16.0F;
            float f2 = (float) par4 + 0.5F;
            float f3 = 0.52F;
            float f4 = par5Random.nextFloat() * 0.6F - 0.3F;

            if (l == 4) {
                par1World.spawnParticle("smoke", (double) (f - f3),
                        (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double) (f - f3),
                        (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 5) {
                par1World.spawnParticle("smoke", (double) (f + f3),
                        (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double) (f + f3),
                        (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 2) {
                par1World.spawnParticle("smoke", (double) (f + f4),
                        (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double) (f + f4),
                        (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
            } else if (l == 3) {
                par1World.spawnParticle("smoke", (double) (f + f4),
                        (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double) (f + f4),
                        (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3,
                                          int par4, int par5) {
        return Container.calcRedstoneFromInventory((IInventory) par1World
                .getTileEntity(par2, par3, par4));
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                  + ":" + "NanoMachineBlock_side");
        frontIcon = par1IconRegister.registerIcon(this.isActive ? Femtocraft.ID()
                                                                          .toLowerCase() + ":" +
                                                                  "NanoInnervator_front_lit"
                : Femtocraft.ID().toLowerCase() + ":"
                  + "NanoInnervator_front_unlit");
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    /**
     * set a blocks direction
     */
    private void setDefaultDirection(World par1World, int par2, int par3,
                                     int par4) {
        if (!par1World.isRemote) {
            Block l = par1World.getBlock(par2, par3, par4 - 1);
            Block i1 = par1World.getBlock(par2, par3, par4 + 1);
            Block j1 = par1World.getBlock(par2 - 1, par3, par4);
            Block k1 = par1World.getBlock(par2 + 1, par3, par4);
            byte b0 = 3;

            if (l.isOpaqueCube() && !i1.isOpaqueCube()) {
                b0 = 3;
            }

            if (i1.isOpaqueCube() && !l.isOpaqueCube()) {
                b0 = 2;
            }

            if (j1.isOpaqueCube() && !k1.isOpaqueCube()) {
                b0 = 5;
            }

            if (k1.isOpaqueCube() && !j1.isOpaqueCube()) {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int metadata) {
        return new TileEntityNanoInnervator();
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           Block par5, int par6) {
        if (!keepFurnaceInventory) {
            TileEntityNanoInnervator tileentityfurnace = (TileEntityNanoInnervator) par1World
                    .getTileEntity(par2, par3, par4);

            if (tileentityfurnace != null) {
                for (int j1 = 0; j1 < tileentityfurnace.getSizeInventory(); ++j1) {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(j1);

                    if (itemstack != null) {
                        float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0) {
                            int k1 = this.furnaceRand.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize) {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(par1World,
                                    (double) ((float) par2 + f),
                                    (double) ((float) par3 + f1),
                                    (double) ((float) par4 + f2),
                                    new ItemStack(itemstack.getItem(), k1,
                                            itemstack.getItemDamage())
                            );

                            if (itemstack.hasTagCompound()) {
                                entityitem.getEntityItem().setTagCompound(
                                        (NBTTagCompound) itemstack
                                                .getTagCompound().copy()
                                );
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3);
                            entityitem.motionY = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                if (tileentityfurnace.isWorking()) {
                    ItemStack itemstack = tileentityfurnace.smeltingStack;

                    if (itemstack != null) {
                        float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0) {
                            int k1 = this.furnaceRand.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize) {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(par1World,
                                    (double) ((float) par2 + f),
                                    (double) ((float) par3 + f1),
                                    (double) ((float) par4 + f2),
                                    new ItemStack(itemstack.getItem(), k1,
                                            itemstack.getItemDamage())
                            );

                            if (itemstack.hasTagCompound()) {
                                entityitem.getEntityItem().setTagCompound(
                                        (NBTTagCompound) itemstack
                                                .getTagCompound().copy()
                                );
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3);
                            entityitem.motionY = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }
                par1World.func_147453_f(par2, par3, par4, par5);
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
                                EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4,
                par5EntityLivingBase, par6ItemStack);

        int l = MathHelper
                        .floor_double((double) (par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }

        if (l == 1) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }

        if (l == 2) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }

        if (l == 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }
    }
}
