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
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNanoFissionReactor extends ContainerInv<TileEntityNanoFissionReactorCore> {
    private int lastHeat = 0;
    private int lastCooledMoltenSalt = 0;
    private int lastMoltenSalt = 0;
    private int lastThorium = 0;

    private static final int heatIndex = 0;
    private static final int cooledMoltenSaltIndex = 1;
    private static final int moltenSaltIndex = 2;
    private static final int thoriumIndex = 3;

    public ContainerNanoFissionReactor(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                                       TileEntityNanoFissionReactorCore inventory) {
        super(player, inventory, 0, 2);
        this.addSlotToContainer(new Slot(inventory, TileEntityNanoFissionReactorCore.heatSlot, 8, 8));
        this.addSlotToContainer(new Slot(inventory, TileEntityNanoFissionReactorCore.saltSlot, 112, 8));
        this.addSlotToContainer(new Slot(inventory, TileEntityNanoFissionReactorCore.thoriumSlot, 112, 28));
        addPlayerInventorySlots(par1InventoryPlayer);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, heatIndex,
                (int) this.inventory.getTemperatureCurrent());
        par1ICrafting.sendProgressBarUpdate(this, cooledMoltenSaltIndex,
                this.inventory.getCooledSaltAmount());
        par1ICrafting.sendProgressBarUpdate(this, moltenSaltIndex,
                this.inventory.getMoltenSaltAmount());
        par1ICrafting.sendProgressBarUpdate(this, thoriumIndex, this.inventory.getThoriumStoreCurrent());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastHeat != (int) this.inventory.getTemperatureCurrent()) {
                icrafting.sendProgressBarUpdate(this, heatIndex,
                        (int) this.inventory.getTemperatureCurrent());
            }
            if (this.lastCooledMoltenSalt != this.inventory.getCooledSaltAmount()) {
                icrafting.sendProgressBarUpdate(this, cooledMoltenSaltIndex,
                        this.inventory.getCooledSaltAmount());
            }
            if (this.lastMoltenSalt != this.inventory.getMoltenSaltAmount()) {
                icrafting.sendProgressBarUpdate(this, moltenSaltIndex,
                        this.inventory.getMoltenSaltAmount());
            }
            if (this.lastThorium != this.inventory.getTemperatureCurrent()) {
                icrafting.sendProgressBarUpdate(this, thoriumIndex, this.inventory.getThoriumStoreCurrent());
            }
        }

        this.lastHeat = (int) this.inventory.getTemperatureCurrent();
        this.lastCooledMoltenSalt = this.inventory.getCooledSaltAmount();
        this.lastMoltenSalt = this.inventory.getMoltenSaltAmount();
        this.lastHeat = this.inventory.getThoriumStoreCurrent();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        switch (par1) {
            case heatIndex:
                this.inventory.setTemperatureCurrent(par2);
                break;
            case cooledMoltenSaltIndex:
                this.inventory.setCooledMoltenSalt(par2);
                break;
            case moltenSaltIndex:
                this.inventory.setMoltenSalt(par2);
                break;
            case thoriumIndex:
                this.inventory.setThoriumStoreCurrent(par2);
                break;
            default:
        }
    }

    @Override
    protected boolean eligibleForInput(ItemStack item) {
        return false;
    }
}


