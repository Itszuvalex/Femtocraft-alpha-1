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

package com.itszuvalex.femtocraft.research.items;

import com.itszuvalex.femtocraft.api.ITechnologyCarrier;
import com.itszuvalex.femtocraft.managers.research.ResearchPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemTechnologyCarrier extends Item implements
                                                         ITechnologyCarrier {
    private final static String researchKey = "techName";

    public ItemTechnologyCarrier(int par1) {
        super(par1);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraft.item.Item#onItemRightClick(net.minecraft.item.ItemStack,
     * net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer)
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
                                      EntityPlayer par3EntityPlayer) {
        ResearchPlayer pr = Femtocraft.researchManager
                .getPlayerResearch(par3EntityPlayer.username);
        if (pr != null) {
            String tech = getTechnology(par1ItemStack);
            if (!(tech == null || tech.isEmpty())) {
                if (!par2World.isRemote) {
                    if (pr.researchTechnology(tech, false)) {
                        ResearchTechnology rt = Femtocraft.researchManager
                                .getTechnology(tech);
                        if (rt != null && rt.discoverItem != null) {
                            return rt.discoverItem.copy();
                        }

                        par1ItemStack.stackSize = 0;
                    }
                }
            }
        }
        return super.onItemRightClick(par1ItemStack, par2World,
                                      par3EntityPlayer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack,
                               EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        NBTTagCompound compound = par1ItemStack.stackTagCompound;
        if (compound != null) {
            par3List.add(getTechnologyLevel(par1ItemStack).getTooltipEnum()
                                 + getTechnology(par1ItemStack) + EnumChatFormatting.RESET);
        }
        else {
            par3List.add("This is only valid if made via");
            par3List.add("Femtocraft Research Console.");
        }
    }

    @Override
    public void setTechnology(ItemStack itemStack, String name) {
        NBTTagCompound compound = itemStack.stackTagCompound;
        if (compound == null) {
            compound = itemStack.stackTagCompound = new NBTTagCompound();
        }

        compound.setString(researchKey, name);
    }

    @Override
    public String getTechnology(ItemStack itemStack) {
        NBTTagCompound compound = itemStack.stackTagCompound;
        if (compound == null) {
            return null;
        }

        return compound.getString(researchKey);
    }

}
