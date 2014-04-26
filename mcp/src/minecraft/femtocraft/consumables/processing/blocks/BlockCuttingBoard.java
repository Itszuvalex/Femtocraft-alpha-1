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

package femtocraft.consumables.processing.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.proxy.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCuttingBoard extends Block {

    private static final float MIN_DIST = 1.0f / 16.0f;
    private static final float MAX_DIST = 15.0f / 16.0f;
    /**
     * Is the random generator used by furnace to drop the inventory contents in
     * random directions.
     */
    private final Random furnaceRand = new Random();
    @SideOnly(Side.CLIENT)
    public Icon cuttingBoardSideNS;
    @SideOnly(Side.CLIENT)
    public Icon cuttingBoardSideW;
    @SideOnly(Side.CLIENT)
    public Icon cuttingBoardSideE;

    public BlockCuttingBoard(int id) {
        super(id, Material.cake);
        this.setBlockBounds(MIN_DIST, 0.0F, MIN_DIST, MAX_DIST, MIN_DIST,
                            MAX_DIST);
        setCreativeTab(Femtocraft.femtocraftTab);

        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "cuttingBoard"); // top
        setTextureName(Femtocraft.ID.toLowerCase() + ":"
                               + "cuttingBoard_NorthSouth");
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "cuttingBoard_Left");
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "cuttingBoard_Right");
    }

    public Icon getCuttingBoardSideNS() {
        return cuttingBoardSideNS;
    }

    public Icon getCuttingBoardSideW() {
        return cuttingBoardSideW;
    }

    public Icon getCuttingBoardSideE() {
        return cuttingBoardSideE;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether
     * or not to render the shared face of two adjacent blocks and also whether
     * the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.cuttingBoardRenderType;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        // For now the block can always be rendered
        ProxyClient.CuttingBoardRenderPass = pass;
        return true;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                           + ":" + "cuttingBoard"); // top
        this.cuttingBoardSideNS = iconRegister.registerIcon(Femtocraft.ID
                                                                    .toLowerCase() + ":" + "cuttingBoard_NorthSouth");
        this.cuttingBoardSideNS = new IconFlipped(this.cuttingBoardSideNS,
                                                  true, false);
        this.cuttingBoardSideE = iconRegister.registerIcon(Femtocraft.ID
                                                                   .toLowerCase() + ":" + "cuttingBoard_Left");
        this.cuttingBoardSideW = iconRegister.registerIcon(Femtocraft.ID
                                                                   .toLowerCase() + ":" + "cuttingBoard_Right");
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z,
     * neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int x, int y, int z,
                                      int neighborBlockID) {
        if (!par1World.doesBlockHaveSolidTopSurface(x, y - 1, z)) {
            this.dropBlockAsItem(par1World, x, y, z,
                                 par1World.getBlockMetadata(x, y, z), 0);
            par1World.setBlockToAir(x, y, z);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified
     * coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int x, int y, int z) {
        return par1World.doesBlockHaveSolidTopSurface(x, y - 1, z);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push
     * but can move over, 2 = total immobility and stop pistons
     */
    public int getMobilityFlag() {
        return 1;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3,
                                    int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
                                    float par8, float par9) {
        if (par1World.isRemote) {
            return true;
        }
        else {
            TileEntityFurnace tileentityfurnace = (TileEntityFurnace) par1World
                    .getBlockTileEntity(par2, par3, par4);

            if (tileentityfurnace != null) {
                par5EntityPlayer.displayGUIFurnace(tileentityfurnace);
            }

            return true;
        }
    }

    public void breakBlock(World par1World, int par2, int par3, int par4,
                           int par5, int par6) {
        TileEntityFurnace tileentityfurnace = (TileEntityFurnace) par1World
                .getBlockTileEntity(par2, par3, par4);

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

            par1World.func_96440_m(par2, par3, par4, par5);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

}
