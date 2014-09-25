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

package com.itszuvalex.femtocraft.core.ore;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.core.MagnetRegistry;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.core.tiles.TileEntityOreLodestone;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

@Configurable
public class BlockOreLodestone extends TileContainer {
    @Configurable(comment = "Maximum amount Lodestone nuggets to drop.")
    public static int DROP_AMOUNT_MAX = 4;
    @Configurable(comment = "Minimum amount of Lodestone nuggets to drop.")
    public static int DROP_AMOUNT_MIN = 2;
    @Configurable(comment = "Set to false to prevent tile entity ticks, and prevent Magnetism from pulling items from" +
                            " your inventory.")
    public static boolean MAGNETIC = true;

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityOreLodestone();
    }

    public BlockOreLodestone(int id) {
        super(id, Material.rock);
        setCreativeTab(Femtocraft.femtocraftTab);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "BlockOreLodestone");
        setUnlocalizedName("BlockOreLodestone");
        setHardness(3.0f);
        setStepSound(Block.soundStoneFootstep);
        setResistance(1f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockOreLodestone");
    }

    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        if (par1World.isRemote) return;

        ItemStack item = par5EntityPlayer.getHeldItem();
        if (MAGNETIC && MagnetRegistry.isMagnet(item)) {
            EntityItem ei = par5EntityPlayer.entityDropItem(item, par5EntityPlayer.height / 2f);
            ei.delayBeforeCanPickup = 20;
            par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);
        }
    }

    @Override
    public int idDropped(int par1, Random random, int par2) {
        return Femtocraft.itemNuggetLodestone.itemID;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(DROP_AMOUNT_MAX + 1 - DROP_AMOUNT_MIN) + DROP_AMOUNT_MIN;
    }
}
