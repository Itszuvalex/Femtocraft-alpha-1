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

package com.itszuvalex.femtocraft.research.items;


import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.Femtocraft$;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.core.items.ItemBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/5/14.
 */
public class ItemPortableResearchComputer extends ItemBase {

    public ItemPortableResearchComputer() {
        super("ItemPortableResearchComputer");
        setCreativeTab(Femtocraft.femtocraftTab());
        setMaxStackSize(1);
        setUnlocalizedName("ItemPortableResearchComputer");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.openGui(Femtocraft$.MODULE$,
                FemtocraftGuiHandler.ResearchComputerGuiID(),
                par2World, (int) par3EntityPlayer.posX,
                (int) par3EntityPlayer.posY,
                (int) par3EntityPlayer.posZ);
        return par1ItemStack;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        itemIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                 + ":" +
                                                 "ItemPortableResearchComputer");
    }
}
