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

package com.itszuvalex.femtocraft.core.items;

import com.itszuvalex.femtocraft.Femtocraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class CoreItemBlock extends ItemBlock {

    public CoreItemBlock(int par1) {
        super(par1);
        setCreativeTab(Femtocraft.femtocraftTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack,
                               EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

        if (hasItemNBT()) {
            NBTTagCompound nbt = par1ItemStack.getTagCompound();
            if (nbt == null) {
                nbt = par1ItemStack.stackTagCompound = new NBTTagCompound();
            }

            String owner = nbt.getString("owner");

            String ownerLabelString = EnumChatFormatting.GRAY + "Owner:"
                    + EnumChatFormatting.RESET;
            String ownerString;
            if (owner.isEmpty()) {
                ownerString = EnumChatFormatting.ITALIC + "unassigned"
                        + EnumChatFormatting.RESET;
            }
            else {
                ownerString = owner;
            }

            par3List.add(String.format("%s %s", ownerLabelString,
                    ownerString));
        }
    }

    /**
     * @return true if this block, when in item form, should have NBTData. If
     * you want this block to be stackable in item form, this must
     * return false. Otherwise, Femtocraft will addInput NBT data
     * automatically.
     */
    public boolean hasItemNBT() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3,
                                           int par4, int par5, EntityPlayer par6EntityPlayer,
                                           ItemStack par7ItemStack) {
        return canPlayerPlace(par6EntityPlayer) && super.canPlaceItemBlockOnSide(par1World, par2, par3, par4, par5, par6EntityPlayer, par7ItemStack);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
                                World world, int x, int y, int z, int side, float hitX, float hitY,
                                float hitZ, int metadata) {
        return canPlayerPlace(player) && super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }

    private boolean canPlayerPlace(EntityPlayer player) {
        return player != null;

    }
}
