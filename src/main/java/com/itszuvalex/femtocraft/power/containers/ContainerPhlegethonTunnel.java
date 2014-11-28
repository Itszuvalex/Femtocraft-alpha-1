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

package com.itszuvalex.femtocraft.power.containers;

import com.itszuvalex.femtocraft.core.container.ContainerInv;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPhlegethonTunnel extends ContainerInv<TileEntityPhlegethonTunnelCore> {
    private static final int powerIndex = 0;
    private int lastPower = 0;

    public ContainerPhlegethonTunnel(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                                     TileEntityPhlegethonTunnelCore inventory) {
        super(player, inventory, 0, 0);
        this.addSlotToContainer(new Slot(inventory, 0, 22, 25));
        addPlayerInventorySlots(par1InventoryPlayer);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        sendUpdateToCrafter(this, par1ICrafting, powerIndex, inventory().getCurrentPower());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            if (this.lastPower !=
                inventory().getCurrentPower()) {
                sendUpdateToCrafter(this, icrafting, powerIndex, inventory().getCurrentPower());
            }
        }

        lastPower = inventory().getCurrentPower();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        switch (par1) {
            case powerIndex:
                this.inventory().setCurrentStorage(par2);
                break;
            default:
        }
    }

    @Override
    public boolean eligibleForInput(ItemStack item) {
        return false;
    }
}


