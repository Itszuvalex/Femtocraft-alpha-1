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

package com.itszuvalex.femtocraft.industry.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityFemtoRepurposer;
import com.itszuvalex.femtocraft.render.RenderSimpleMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFemtoRepurposer extends TileContainer {
    /**
     * Is the random generator used by furnace to drop the inventory contents in
     * random directions.
     */
    private final Random rand = new Random();

    @SideOnly(Side.CLIENT)
    private Icon frontIcon;

    public BlockFemtoRepurposer(int par1) {
        super(par1, Material.iron);
        setUnlocalizedName("BlockFemtoRepurposer");
        setHardness(3.5f);
        setStepSound(Block.soundMetalFootstep);
        setCreativeTab(Femtocraft.femtocraftTab);
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
    public Icon getIcon(int par1, int par2) {
        if (par1 == par2) {
            return frontIcon;
        }
        else {
            return blockIcon;
        }
    }

    /**
     * If this returns true, then comparators facing away from this block will
     * use the value from getComparatorInputOverride instead of the actual
     * redstone signal strength.
     */
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is
     * used instead of the redstone signal strength when this block inputs to a
     * comparator.
     */
    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3,
                                          int par4, int par5) {
        return Container.calcRedstoneFromInventory((IInventory) par1World
                .getBlockTileEntity(par2, par3, par4));
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "FemtoMachineBlock_side");
        frontIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "FemtoRepurposer_front");
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
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1]) {
                b0 = 3;
            }

            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l]) {
                b0 = 2;
            }

            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1]) {
                b0 = 5;
            }

            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1]) {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World) {
        return new TileEntityFemtoRepurposer();
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an
     * update, as appropriate
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           int par5, int par6) {
        TileEntityFemtoRepurposer tileEntity = (TileEntityFemtoRepurposer) par1World
                .getBlockTileEntity(par2, par3, par4);

        if (tileEntity != null) {
            for (int j1 = 0; j1 < tileEntity.getSizeInventory(); ++j1) {
                ItemStack itemstack = tileEntity.getStackInSlot(j1);

                if (itemstack != null) {
                    float f = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int k1 = this.rand.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize) {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(par1World,
                                (double) ((float) par2 + f),
                                (double) ((float) par3 + f1),
                                (double) ((float) par4 + f2), new ItemStack(
                                itemstack.itemID, k1,
                                itemstack.getItemDamage())
                        );

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound(
                                    (NBTTagCompound) itemstack.getTagCompound()
                                                              .copy()
                            );
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) this.rand
                                .nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) this.rand
                                .nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) this.rand
                                .nextGaussian() * f3);
                        par1World.spawnEntityInWorld(entityitem);
                    }
                }
            }

            if (tileEntity.isWorking()) {
                ItemStack itemstack = tileEntity.deconstructingStack;

                if (itemstack != null) {
                    float f = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int k1 = this.rand.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize) {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(par1World,
                                (double) ((float) par2 + f),
                                (double) ((float) par3 + f1),
                                (double) ((float) par4 + f2), new ItemStack(
                                itemstack.itemID, k1,
                                itemstack.getItemDamage())
                        );

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound(
                                    (NBTTagCompound) itemstack.getTagCompound()
                                                              .copy()
                            );
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) this.rand
                                .nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) this.rand
                                .nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) this.rand
                                .nextGaussian() * f3);
                        par1World.spawnEntityInWorld(entityitem);
                    }
                }
            }

            par1World.func_96440_m(par2, par3, par4, par5);
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

        // if (par6ItemStack.hasDisplayName())
        // {
        // ((TileEntityBaseEntityMicroDeconstructor)par1World.getBlockTileEntity(par2,
        // par3,
        // par4)).setGuiDisplayName(par6ItemStack.getDisplayName());
        // }
    }
}