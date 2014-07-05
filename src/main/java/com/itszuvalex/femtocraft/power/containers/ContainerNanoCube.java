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

package com.itszuvalex.femtocraft.power.containers;

import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCubePort;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerNanoCube extends Container {
    private final TileEntityNanoCubePort controller;
    private int lastPower = 0;

    public ContainerNanoCube(TileEntityNanoCubePort controller) {
        super();
        this.controller = controller;
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1iCrafting) {
        super.addCraftingToCrafters(par1iCrafting);
        par1iCrafting.sendProgressBarUpdate(this, 0,
                controller.getCurrentPower());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int power = controller.getCurrentPower();
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (lastPower != power) {
                icrafting.sendProgressBarUpdate(this, 0, power);
            }
        }
        lastPower = power;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);

        if (par1 == 0) {
            controller.setCurrentStorage(par2);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.controller.canPlayerUse(entityplayer);
    }
}
