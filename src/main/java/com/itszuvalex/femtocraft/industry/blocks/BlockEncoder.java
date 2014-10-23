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
import com.itszuvalex.femtocraft.industry.tiles.TileEntityEncoder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockEncoder extends TileContainer {
    public IIcon top;
    public IIcon side;

    public BlockEncoder() {
        super(Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setHardness(3.5f);
        setStepSound(Block.soundTypeMetal);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        switch (ForgeDirection.getOrientation(par1)) {
            case UP:
                return top;
            case DOWN:
                return blockIcon;
            default:
                return side;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        top = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":"
                                            + "BlockEncoder_top");
        side = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":"
                                             + "BlockEncoder_side");
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                  + ":" + "MicroMachineBlock_side");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityEncoder();
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           Block par5, int par6) {

        TileEntityEncoder encoder = (TileEntityEncoder) par1World
                .getTileEntity(par2, par3, par4);

        Random random = new Random();

        if (encoder != null) {
            for (int j1 = 0; j1 < encoder.getSizeInventory(); ++j1) {
                ItemStack itemstack = encoder.getStackInSlotOnClosing(j1);

                if (itemstack != null) {
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = random.nextFloat() * 0.8F + 0.1F;
                    float f2 = random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int k1 = random.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize) {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(par1World,
                                (double) ((float) par2 + f),
                                (double) ((float) par3 + f1),
                                (double) ((float) par4 + f2), new ItemStack(
                                itemstack.getItem(), k1,
                                itemstack.getItemDamage())
                        );

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound(
                                    (NBTTagCompound) itemstack.getTagCompound()
                                            .copy()
                            );
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) random
                                .nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) random
                                .nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) random
                                .nextGaussian() * f3);
                        par1World.spawnEntityInWorld(entityitem);
                    }
                }
            }
            par1World.func_147453_f(par2, par3, par4, par5);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

}
