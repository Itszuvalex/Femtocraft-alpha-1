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

package com.itszuvalex.femtocraft.research.tiles;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.api.ITechnologyCarrier;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;

public class TileEntityResearchConsole extends TileEntityBase implements
                                                              IInventory {
    public static final String PACKET_CHANNEL = Femtocraft.RESEARCH_CONSOLE_CHANNEL();
    private static final int ticksToResearch = 400;
    public
    @FemtocraftDataUtils.Saveable(desc = true)
    String displayTech = null;
    private
    @FemtocraftDataUtils.Saveable(desc = true)
    String researchingTech = null;
    private
    @FemtocraftDataUtils.Saveable
    int progress = 0;
    private
    @FemtocraftDataUtils.Saveable
    int progressMax = 0;
    private
    @FemtocraftDataUtils.Saveable
    ItemStack[] inventory = new ItemStack[10];

    public TileEntityResearchConsole() {
        super();
    }

    public int getResearchProgress() {
        return progress;
    }

    public void setResearchProgress(int progress) {
        this.progress = progress;
    }

    public int getResearchProgressScaled(int scale) {
        if (progressMax == 0) {
            return 0;
        }
        return (progress * scale) / progressMax;
    }

    public boolean isResearching() {
        return researchingTech != null;
    }

    public int getResearchMax() {
        return progressMax;
    }

    public void setResearchMax(int progressMax) {
        this.progressMax = progressMax;
    }

    public String getResearchingName() {
        return researchingTech;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        checkForTechnology();
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        if (researchingTech != null) {
            if (progress++ >= progressMax) {
                finishWork();
            }
        }
    }

    private void finishWork() {
        progress = 0;
        progressMax = 0;

        ResearchTechnology tech = Femtocraft.researchManager()
                                            .getTechnology(researchingTech);
        if (tech == null) {
            researchingTech = null;
            return;
        }

        Item techItem = null;
        switch (tech.level) {
            case MACRO:
            case MICRO:
                techItem = Femtocraft.itemMicroTechnology();
                break;
            case NANO:
                techItem = Femtocraft.itemNanoTechnology();
                break;
            case DIMENSIONAL:
            case TEMPORAL:
            case FEMTO:
                techItem = Femtocraft.itemFemtoTechnology();
                break;
        }
        ItemStack techstack = new ItemStack(techItem, 1);
        ((ITechnologyCarrier) techItem).setTechnology(techstack,
                researchingTech);
        researchingTech = null;
        inventory[inventory.length - 1] = techstack;
        onInventoryChanged();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        checkForTechnology();
    }

    private void checkForTechnology() {
        if (worldObj == null) {
            return;
        }
        if (worldObj.isRemote) {
            return;
        }

        boolean hadTech = displayTech != null;
        displayTech = null;

        for (ResearchTechnology tech : Femtocraft.researchManager()
                                                 .getTechnologies()) {
            if (Femtocraft.researchManager().hasPlayerDiscoveredTechnology(
                    getOwner(), tech) && !Femtocraft.researchManager().hasPlayerResearchedTechnology(getOwner(), tech)) {
                if (matchesTechnology(tech)) {
                    displayTech = tech.name;
                    if (worldObj != null) {
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    }
                    return;
                }
            }
        }

        if (hadTech && displayTech == null) {
            if (worldObj != null) {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    private boolean matchesTechnology(ResearchTechnology tech) {
        if (tech == null) {
            return false;
        }
        if (tech.researchMaterials == null || tech.researchMaterials.length == 0) {
            return false;
        }
        for (int i = 0; i < 9 && i < tech.researchMaterials.length; ++i) {
            if (!compareItemStack(tech.researchMaterials[i], inventory[i])) {
                return false;
            }
        }

        return true;
    }

    private boolean compareItemStack(ItemStack tech, ItemStack inv) {
        if (tech == null && inv == null) {
            return true;
        }
        if (tech == null && inv != null) {
            return false;
        }
        if (tech != null && inv == null) {
            return false;
        }

        if (tech.itemID != inv.itemID) {
            return false;
        }
        if (tech.getItemDamage() != inv.getItemDamage()) {
            return false;
        }
        if (tech.stackSize > inv.stackSize) {
            return false;
        }

        return true;
    }

    @Override
    public void saveToDescriptionCompound(NBTTagCompound compound) {
        super.saveToDescriptionCompound(compound);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        super.onDataPacket(net, pkt);
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityBase#hasGUI()
     */
    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.ResearchConsoleGuiID;
    }

    public void startWork() {
        if (!canWork()) {
            return;
        }

        researchingTech = displayTech;
        progressMax = ticksToResearch;
        progress = 0;

        ResearchTechnology tech = Femtocraft.researchManager()
                                            .getTechnology(displayTech);

        for (int i = 0; i < 9 && i < tech.researchMaterials.length; ++i) {
            if (tech.researchMaterials[i] == null) {
                continue;
            }
            decrStackSize(i, tech.researchMaterials[i].stackSize);
        }

        this.onInventoryChanged();
    }

    private boolean canWork() {
        if (displayTech == null || displayTech.isEmpty()) {
            return false;
        }
        if (researchingTech != null && !researchingTech.isEmpty()) {
            return false;
        }
        ResearchTechnology tech = Femtocraft.researchManager()
                                            .getTechnology(displayTech);

        return matchesTechnology(tech);
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (this.inventory[i] != null) {
            ItemStack itemstack;

            if (this.inventory[i].stackSize <= j) {
                itemstack = this.inventory[i];
                this.inventory[i] = null;
                return itemstack;
            }
            else {
                itemstack = this.inventory[i].splitStack(j);

                if (this.inventory[i].stackSize == 0) {
                    this.inventory[i] = null;
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory[i];
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;

    }

    @Override
    public String getInvName() {
        return "Research Console";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return canPlayerUse(entityplayer);
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        switch (i) {
            case 9:
                return false;
            default:
                return true;
        }
    }

}
