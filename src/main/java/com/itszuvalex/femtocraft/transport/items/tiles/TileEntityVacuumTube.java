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

package com.itszuvalex.femtocraft.transport.items.tiles;

import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.transport.IVacuumTube;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntityVacuumTube extends TileEntityBase implements IVacuumTube {
    public static final String ITEM_MASK_KEY = "ItemMask";
    public static final String CONNECTION_MASK_KEY = "ConnectionMask";
    // hasItem array for client-side rendering only
    // Server will update this array and this alone to save bytes
    // Player has no need to know WHAT is in the pipes, anyways


    // Will not be @Saveable due to bit masking
    public boolean[] hasItem = new boolean[4];
    public
    @Saveable
    ItemStack queuedItem = null;
    ForgeDirection inputDir = ForgeDirection.UNKNOWN;
    ForgeDirection outputDir = ForgeDirection.UNKNOWN;
    private
    @Saveable
    BaseInventory items;
    private ISidedInventory inputSidedInv = null;
    private ISidedInventory outputSidedInv = null;
    private IInventory inputInv = null;
    private IInventory outputInv = null;
    private IVacuumTube inputTube = null;
    private IVacuumTube outputTube = null;
    private boolean overflowing = false;
    private boolean canFillInv = true;
    private boolean canExtractInv = true;
    private boolean needsCheckInput = false;
    private boolean needsCheckOutput = false;
    private boolean loading = true;

    public TileEntityVacuumTube() {
        super();
        items = new BaseInventory(4);
        Arrays.fill(hasItem, false);
    }

    @Override
    public boolean hasDescription() {
        return true;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound compound = pkt.func_148857_g();
        parseItemMask(compound.getByte(ITEM_MASK_KEY));
        parseConnectionMask(compound.getByte(CONNECTION_MASK_KEY));
    }

    @Override
    public void updateEntity() {
        loading = false;
        super.updateEntity();
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setByte("Connections", generateConnectionMask());
        par1nbtTagCompound.setByte("HasItems", generateItemMask());
    }

    @Override
    public Packet getDescriptionPacket() {
        return generatePacket();
    }

    @Override
    public void femtocraftServerUpdate() {

        if (needsCheckInput || inputDir != ForgeDirection.UNKNOWN
                               && missingInput()) {
            if (!addInput(inputDir)) {
                return;
            }
            needsCheckInput = false;
        }
        if (needsCheckOutput || outputDir != ForgeDirection.UNKNOWN
                                && missingOutput()) {
            if (!addOutput(outputDir)) {
                return;
            }
            needsCheckOutput = false;
        }

        if (items.getStackInSlot(3) != null) {
            // If next tube has a slot
            if (outputTube != null) {
                if (outputTube.insertItem(items.getStackInSlot(3), outputDir.getOpposite())) {
                    items.setInventorySlotContents(3, null);
                    hasItem[3] = false;
                    setUpdate();
                    markDirty();
                }
            } else if (outputSidedInv != null) {
                if (canFillInv) {
                    int side = FemtocraftUtils.indexOfForgeDirection(outputDir
                            .getOpposite());
                    int[] slots = outputSidedInv
                            .getAccessibleSlotsFromSide(side);
                    int invMax = outputSidedInv.getInventoryStackLimit();
                    for (int i = 0; i < slots.length && items.getStackInSlot(3) != null; i++) {
                        if (outputSidedInv.canInsertItem(slots[i], items.getStackInSlot(3),
                                side)) {
                            if (outputSidedInv.isItemValidForSlot(slots[i],
                                    items.getStackInSlot(3))) {
                                ItemStack slotStack = outputSidedInv
                                        .getStackInSlot(slots[i]);

                                // If no items in that slot
                                if (slotStack == null) {
                                    outputSidedInv.setInventorySlotContents(
                                            slots[i], items.getStackInSlot(3).copy());
                                    items.setInventorySlotContents(3, null);
                                    hasItem[3] = false;
                                    setUpdate();
                                    markDirty();
                                    outputSidedInv.markDirty();
                                }
                                // Combine items
                                else {
                                    // Account for possible mismatch
                                    if (items.getStackInSlot(3).getItem() != slotStack.getItem()) {
                                        continue;
                                    }
                                    if (!items.getStackInSlot(3).isStackable()) {
                                        continue;
                                    }

                                    int itemMax = slotStack.getMaxStackSize();
                                    int max = invMax > itemMax ? itemMax
                                            : invMax;
                                    int room = max - slotStack.stackSize;
                                    int amount =
                                            room > items.getStackInSlot(3).stackSize ? items.getStackInSlot(3).stackSize
                                                    : room;
                                    slotStack.stackSize += amount;
                                    items.decrStackSize(3, amount);
                                    if (items.getStackInSlot(3) == null || items.getStackInSlot(3).stackSize <= 0) {
                                        hasItem[3] = false;
                                        setUpdate();
                                    }
                                    markDirty();
                                    outputSidedInv.markDirty();
                                }
                            }
                        }
                    }
                    if (items.getStackInSlot(3) != null) {
                        canFillInv = false;
                        setUpdate();
                        markDirty();
                    }
                }
            } else if (outputInv != null) {
                if (canFillInv) {
                    int size = outputInv.getSizeInventory();
                    int invMax = outputInv.getInventoryStackLimit();
                    for (int i = 0; i < size && items.getStackInSlot(3) != null; i++) {
                        if (outputInv.isItemValidForSlot(i, items.getStackInSlot(3))) {
                            ItemStack slotStack = outputInv.getStackInSlot(i);

                            // If no items in that slot
                            if (slotStack == null) {
                                outputInv.setInventorySlotContents(i,
                                        items.getStackInSlot(3).copy());
                                items.setInventorySlotContents(3, null);
                                hasItem[3] = false;
                                setUpdate();
                                markDirty();
                                outputInv.markDirty();
                            }
                            // Combine items
                            else {
                                // Account for possible mismatch
                                if (items.getStackInSlot(3).getItem() != slotStack.getItem()) {
                                    continue;
                                }
                                if (!items.getStackInSlot(3).isStackable()) {
                                    continue;
                                }

                                int itemMax = slotStack.getMaxStackSize();
                                int max = invMax > itemMax ? itemMax : invMax;
                                int room = max - slotStack.stackSize;
                                int amount =
                                        room > items.getStackInSlot(3).stackSize ? items.getStackInSlot(3).stackSize
                                                : room;
                                slotStack.stackSize += amount;
                                items.decrStackSize(3, amount);
                                if (items.getStackInSlot(3) == null) {
                                    hasItem[3] = false;
                                    setUpdate();
                                }
                                markDirty();
                                outputInv.markDirty();
                            }
                        }

                    }
                    if (items.getStackInSlot(3) != null) {
                        canFillInv = false;
                        setUpdate();
                        markDirty();
                    }
                }
            } else if (missingOutput()) {
                ejectItem(3);
            }
        }

        // Move up rest of items, starting from end
        for (int i = items.getSizeInventory() - 2; i >= 0; i--) {
            // Move up only if room
            if (items.getStackInSlot(i + 1) == null) {
                if (hasItem[i]) {
                    setUpdate();
                    markDirty();
                }

                hasItem[i + 1] = hasItem[i];
                hasItem[i] = false;
                items.setInventorySlotContents(i + 1, items.getStackInSlot(i));
                items.setInventorySlotContents(i, null);
            }
        }

        // Blockage. We're done
        if (isOverflowing()) {
            overflowing = true;
            // Send overflow update
            setUpdate();
            markDirty();
            return;
        }

        // If being loaded by another BlockVacuumTube
        if (queuedItem != null) {
            items.setInventorySlotContents(0, queuedItem);
            hasItem[0] = true;
            queuedItem = null;
            setUpdate();
            markDirty();
        }
        // Pull from inventory
        else if (inputSidedInv != null) {
            if (canExtractInv) {
                int side = FemtocraftUtils.indexOfForgeDirection(inputDir
                        .getOpposite());
                int[] slots = inputSidedInv.getAccessibleSlotsFromSide(side);
                for (int slot : slots) {
                    ItemStack stack = inputSidedInv.getStackInSlot(slot);
                    if (stack != null) {
                        if (!inputSidedInv.canExtractItem(slot, stack, side)) {
                            continue;
                        }
                        if (!canAcceptItemStack(stack)) {
                            continue;
                        }
                        items.setInventorySlotContents(0, inputSidedInv.decrStackSize(slot, 64));
                        hasItem[0] = true;
                        inputSidedInv.markDirty();
                        setUpdate();
                        markDirty();
                        return;
                    }
                }
                canExtractInv = false;
            }
        } else if (inputInv != null) {
            if (canExtractInv) {
                int size = inputInv.getSizeInventory();
                for (int i = 0; i < size; i++) {
                    ItemStack stack = inputInv.getStackInSlot(i);
                    if (stack != null) {
                        if (!canAcceptItemStack(stack)) {
                            continue;
                        }
                        items.setInventorySlotContents(0, inputInv.decrStackSize(i, 64));
                        hasItem[0] = true;
                        inputInv.markDirty();
                        setUpdate();
                        markDirty();
                        return;
                    }
                }
                canExtractInv = false;
            }
        }
        // Suck them in, only if room
        else if (missingInput()) {
            ForgeDirection dir = inputDir;

            if (inputDir == ForgeDirection.UNKNOWN) {
                if (outputDir == ForgeDirection.UNKNOWN) {
                    dir = ForgeDirection.SOUTH;
                } else {
                    dir = outputDir.getOpposite();
                }
            }

            float x = xCoord + dir.offsetX;
            float y = yCoord + dir.offsetY;
            float z = zCoord + dir.offsetZ;

            List<EntityItem> items = worldObj.getEntitiesWithinAABB(
                    EntityItem.class,
                    AxisAlignedBB.getBoundingBox(x, y, z, x + 1.f,
                            y + 1.f, z + 1.f)
            );
            for (EntityItem item : items) {
                if (!canAcceptItemStack(item.getEntityItem())) {
                    continue;
                }
                item.addVelocity((xCoord + .5) - item.posX, (yCoord + .5)
                                                            - item.posY, (zCoord + .5) - item.posZ);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        byte connections = par1nbtTagCompound.getByte("Connections");
        parseConnectionMask(connections);
        byte hasItems = par1nbtTagCompound.getByte("HasItems");
        parseItemMask(hasItems);
    }

    public void parseConnectionMask(byte mask) {
        int input = mask & 7;
        int output = (mask >> 4) & 7;
        inputDir = ForgeDirection.getOrientation(input);
        outputDir = ForgeDirection.getOrientation(output);
        boolean hasInput = ((mask >> 3) & 1) == 1;
        boolean hasOutput = ((mask >> 7) & 1) == 1;

        if (worldObj == null) {
            return;
        }

        TileEntity inputTile = worldObj.getTileEntity(xCoord
                                                      + inputDir.offsetX, yCoord + inputDir.offsetY, zCoord
                                                                                                     +
                                                                                                     inputDir.offsetZ);
        if (inputTile == null) {
            if (hasInput) {
                needsCheckInput = true;
            }
        } else {
            addInput(inputDir);
        }

        TileEntity outputTile = worldObj.getTileEntity(xCoord
                                                       + outputDir.offsetX, yCoord + outputDir.offsetY, zCoord
                                                                                                        +
                                                                                                        outputDir
                                                                                                                .offsetZ);
        if (outputTile == null) {
            if (hasOutput) {
                needsCheckOutput = true;
            }
        } else {
            addOutput(outputDir);
        }

        setRenderUpdate();
    }

    public void parseItemMask(byte mask) {
        byte temp;

        for (int i = 0; i < hasItem.length; i++) {
            temp = mask;
            hasItem[i] = ((temp >> i) & 1) == 1;
        }

        temp = mask;
        overflowing = ((temp >> hasItem.length) & 1) == 1;
        setRenderUpdate();
    }

    private void clearInput() {
        inputInv = null;
        inputSidedInv = null;
        if (inputTube != null) {
            // To prevent Recursion
            IVacuumTube tube = inputTube;
            inputTube = null;
            tube.disconnect(inputDir.getOpposite());
            inputDir = ForgeDirection.UNKNOWN;
        }
        setUpdate();
        setModified();
    }

//    @Override
//    public boolean hasInput() {
//        return !missingInput();
//    }
//
//    @Override
//    public boolean hasOutput() {
//        return !missingOutput();
//    }

    private void clearOutput() {
        outputInv = null;
        outputSidedInv = null;
        if (outputTube != null) {
            // To prevent Recursion
            IVacuumTube tube = outputTube;
            outputTube = null;
            tube.disconnect(outputDir.getOpposite());
            outputDir = ForgeDirection.UNKNOWN;
        }

        setUpdate();
        setModified();
    }

    private void ejectItem(int slot) {
        ItemStack dropItem = items.getStackInSlot(slot);
        ejectItemStack(dropItem);
        items.setInventorySlotContents(slot, null);
        hasItem[slot] = false;
        setUpdate();
        markDirty();
    }

    protected boolean canAcceptItemStack(ItemStack item) {
        return true;
    }

    private void ejectItemStack(ItemStack dropItem) {
        if (dropItem == null) {
            return;
        }
        if (worldObj.isRemote) {
            return;
        }


        ForgeDirection dir = outputDir;
        if (dir == ForgeDirection.UNKNOWN) {
            if (inputDir == ForgeDirection.UNKNOWN) {
                dir = ForgeDirection.NORTH;
            } else {
                dir = inputDir.getOpposite();
            }
        }
        // Throw the item out into the world!
        EntityItem entityitem = new EntityItem(worldObj, xCoord + .5 + dir.offsetX * .6f,
                yCoord + .5 + dir.offsetY * .6f, zCoord + .5f + dir.offsetZ * .6f, dropItem.copy());

        entityitem.motionX = dir.offsetX;
        entityitem.motionY = dir.offsetY;
        entityitem.motionZ = dir.offsetZ;
        worldObj.spawnEntityInWorld(entityitem);
        setUpdate();
        markDirty();
    }

    private S35PacketUpdateTileEntity generatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setByte(ITEM_MASK_KEY, generateItemMask());
        compound.setByte(CONNECTION_MASK_KEY, generateConnectionMask());
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
    }

    public byte generateConnectionMask() {
        byte output = 0;
        output += FemtocraftUtils.indexOfForgeDirection(inputDir) & 7;
        output += missingInput() ? 0 : 1 << 3;
        output += (FemtocraftUtils.indexOfForgeDirection(outputDir) & 7) << 4;
        output += missingOutput() ? 0 : 1 << 7;
        return output;
    }

    public byte generateItemMask() {
        byte output = 0;

        for (int i = 0; i < hasItem.length; i++) {
            if (hasItem[i]) {
                output += 1 << i;
            }
        }

        if (isOverflowing()) {
            output += 1 << hasItem.length;
        }

        return output;
    }

    public boolean missingInput() {
        return (inputSidedInv == null) && (inputTube == null)
               && (inputInv == null);
    }

    public boolean missingOutput() {
        return (outputSidedInv == null) && (outputTube == null)
               && (outputInv == null);
    }

    @Override
    public boolean isOverflowing() {
        return (worldObj.isRemote && overflowing)
               || (((outputTube != null && !outputTube.canInsertItem(null,
                outputDir.getOpposite()
        )) ||
                    ((outputInv != null || outputSidedInv != null) && !canFillInv))
                   && hasItem[0] && hasItem[1] && hasItem[2] && hasItem[3] &&
                   (inputTube == null || queuedItem != null));
    }

    @Override
    public boolean canInsertItem(ItemStack item, ForgeDirection dir) {
        return queuedItem == null;
    }

    @Override
    public boolean insertItem(ItemStack item, ForgeDirection dir) {
        if (item == null) return true;
        if (isOverflowing()) {
            return false;
        }
        if (queuedItem == null) {
            queuedItem = item.copy();
            setUpdate();

            if (items.getStackInSlot(0) == null) {
                items.setInventorySlotContents(0, queuedItem);
                queuedItem = null;
                hasItem[0] = true;
            }
            markDirty();

            return true;
        }

        return false;
    }

    @Override
    public boolean isInput(ForgeDirection dir) {
        return dir == inputDir;
    }

    @Override
    public boolean isOutput(ForgeDirection dir) {
        return dir == outputDir;
    }

    @Override
    public void disconnect(ForgeDirection dir) {
        if (isInput(dir)) {
            clearInput();
        } else if (isOutput(dir)) {
            clearOutput();
        }
    }

    @Override
    public void markDirty() {
        if (!loading) { super.markDirty(); }
    }

    @Override
    public boolean addInput(ForgeDirection dir) {
        if (worldObj == null) {
            return false;
        }
        if (dir == outputDir) {
            return false;
        }
        if ((inputInv != null || inputSidedInv != null || inputTube != null)
            && (dir == inputDir)) {
            return true;
        }
        TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                yCoord + dir.offsetY, zCoord + dir.offsetZ);
        if (tile == null) {
            return false;
        }
        if (tile == this) {
            return false;
        }

        if (inputDir != ForgeDirection.UNKNOWN) {
            clearInput();
        }

        if (tile instanceof ISidedInventory) {
            inputSidedInv = (ISidedInventory) tile;
            inputInv = null;
            inputTube = null;
            inputDir = dir;
            setUpdate();
            setModified();
            return true;
        } else if (tile instanceof IVacuumTube) {
            inputTube = (IVacuumTube) tile;
            inputDir = dir;
            inputSidedInv = null;
            inputInv = null;
            inputTube.addOutput(dir.getOpposite());
            setUpdate();
            setModified();
            return true;
        } else if (tile instanceof IInventory) {
            inputTube = null;
            inputDir = dir;
            inputSidedInv = null;
            inputInv = (IInventory) tile;
            setUpdate();
            setModified();
            return true;
        }

        return false;
    }

    @Override
    public boolean addOutput(ForgeDirection dir) {
        if (worldObj == null) {
            return false;
        }
        if (dir == inputDir) {
            return false;
        }
        if ((outputInv != null || outputSidedInv != null || outputTube != null)
            && (dir == outputDir)) {
            return true;
        }
        TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                yCoord + dir.offsetY, zCoord + dir.offsetZ);
        if (tile == null) {
            return false;
        }
        if (tile == this) {
            return false;
        }

        if (outputDir != ForgeDirection.UNKNOWN) {
            clearOutput();
        }

        if (tile instanceof ISidedInventory) {
            outputSidedInv = (ISidedInventory) tile;
            outputInv = null;
            outputTube = null;
            outputDir = dir;
            setUpdate();
            setModified();
            return true;
        } else if (tile instanceof IVacuumTube) {
            outputTube = (IVacuumTube) tile;
            outputDir = dir;
            outputSidedInv = null;
            outputInv = null;
            outputTube.addInput(dir.getOpposite());
            setUpdate();
            setModified();
            return true;
        } else if (tile instanceof IInventory) {
            outputTube = null;
            outputDir = dir;
            outputSidedInv = null;
            outputInv = (IInventory) tile;
            setUpdate();
            setModified();
            return true;
        }

        return false;
    }

    public boolean isEndpoint() {
        return (missingInput() || missingOutput());
    }

    public void onBlockBreak() {
        clearInput();
        clearOutput();

        for (int i = 0; i < items.getSizeInventory(); i++) {
            ejectItem(i);
        }

        ejectItemStack(queuedItem);
    }

    public void searchForMissingConnection() {
        if (missingInput()) {
            searchForInput();
        }
        if (missingOutput()) {
            searchForOutput();
        }
    }

    public boolean searchForInput() {
        List<Integer> check = new ArrayList<>();

        // Search for BlockVacuumTube connections first
        for (int i = 0; i < 6; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }
            if (tile instanceof IVacuumTube) {
                IVacuumTube tube = (IVacuumTube) tile;
                if (tube.isOutput(ForgeDirection.UNKNOWN)) {
                    if (addInput(dir)) {
                        return true;
                    }
                }
            } else {
                check.add(i);
            }
        }

        for (Integer i : check) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }

            if (addInput(dir)) {
                return true;
            }
        }

        return false;
    }

    public boolean searchForOutput() {
        List<Integer> check = new ArrayList<>();

        // Check for VacuumTubeTiles first
        for (int i = 0; i < 6; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }
            if (tile instanceof IVacuumTube) {
                IVacuumTube tube = (IVacuumTube) tile;
                if (tube.isInput(ForgeDirection.UNKNOWN)) {
                    if (addOutput(dir)) {
                        return true;
                    }
                }
            } else {
                check.add(i);
            }
        }

        for (Integer i : check) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }

            if (addOutput(dir)) {
                return true;
            }
        }

        return false;
    }

    public void searchForFullConnections() {
        List<Integer> check = new ArrayList<>();

        // Check for VacuumTubeTiles first
        for (int i = 0; i < 6; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }
            if (tile instanceof IVacuumTube) {
                IVacuumTube tube = (IVacuumTube) tile;
                if (tube.isInput(ForgeDirection.UNKNOWN) && missingOutput() && addOutput(dir) ||
                    tube.isOutput(ForgeDirection.UNKNOWN) && missingInput() && addInput(dir)) {
                    continue;
                }
            } else {
                check.add(i);
            }
        }

        for (Integer i : check) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }

            if (missingOutput() && addOutput(dir) || missingInput() && addInput(dir)) {
                continue;
            }
        }
    }

    public boolean searchForConnection() {
        cycleSearch();

        return false;
    }

    private void cycleSearch() {
        int i = 0;


        if (missingInput() && !missingOutput()) {
            ForgeDirection temp = outputDir.getOpposite();
            clearOutput();
            addInput(temp);
            return;
        } else if (missingOutput() && !missingInput()) {
            ForgeDirection temp = inputDir.getOpposite();
            clearInput();
            addOutput(temp);
            return;
        }

        int lastOutputOrientation = FemtocraftUtils
                .indexOfForgeDirection(outputDir);
        int lastInputOrientation = FemtocraftUtils
                .indexOfForgeDirection(inputDir);

        do {
            lastOutputOrientation += 1;
            clearOutput();
            if (lastOutputOrientation >= 6) {
                int j = 0;

                lastOutputOrientation = 0;
                do {
                    lastInputOrientation += 1;
                    clearInput();
                    if (lastInputOrientation >= 6) {
                        lastInputOrientation = 0;
                    }
                    ++j;

                } while (!addInput(ForgeDirection
                        .getOrientation(lastInputOrientation)) && (j < 6));
            }

            ++i;

        } while (!addOutput(ForgeDirection
                .getOrientation(lastOutputOrientation)) && (i < 6));
    }

    private void removeLastInput() {
        if (inputTube == null) {
            return;
        }
        clearInput();
        setUpdate();
    }

    private void removeLastOutput() {
        if (outputTube == null) {
            return;
        }
        clearOutput();
        setUpdate();
    }

    private void setupReceiveFromTube(TileEntityVacuumTube tube,
                                      ForgeDirection dir) {
        tube.outputTube = this;
        tube.outputInv = null;
        tube.outputSidedInv = null;
        tube.outputDir = dir.getOpposite();
        tube.setUpdate();
        tube.markDirty();
        setUpdate();
        markDirty();
    }

    private void setupSendToTube(TileEntityVacuumTube tube, ForgeDirection dir) {
        tube.inputTube = this;
        tube.inputInv = null;
        tube.inputSidedInv = null;
        tube.inputDir = dir.getOpposite();
        tube.setUpdate();
        tube.markDirty();
        setUpdate();
        markDirty();
    }

    private void removeReceiveFromTube(TileEntityVacuumTube tube) {
        if (tube.outputTube != this) {
            return;
        }

        tube.outputTube = null;
        tube.outputDir = tube.inputDir.getOpposite();
        tube.setUpdate();
        tube.markDirty();
        setUpdate();
        markDirty();
    }

    private void removeSendToTube(TileEntityVacuumTube tube) {
        if (tube.inputTube != this) {
            return;
        }

        tube.inputTube = null;
        tube.inputDir = tube.outputDir.getOpposite();
        tube.setUpdate();
        tube.markDirty();
        setUpdate();
        markDirty();
    }

    public void validateConnections() {
        if (inputDir != ForgeDirection.UNKNOWN) {
            TileEntity tile = worldObj.getTileEntity(xCoord
                                                     + inputDir.offsetX, yCoord + inputDir.offsetY, zCoord
                                                                                                    +
                                                                                                    inputDir.offsetZ);
            if (tile == null) {
                inputTube = null;
                inputInv = null;
                inputSidedInv = null;
                setUpdate();
                markDirty();
                inputDir = ForgeDirection.UNKNOWN;
            } else {
                addInput(inputDir);
            }
        }
        if (outputDir != ForgeDirection.UNKNOWN) {
            TileEntity tile = worldObj.getTileEntity(xCoord
                                                     + outputDir.offsetX, yCoord + outputDir.offsetY, zCoord
                                                                                                      +
                                                                                                      outputDir
                                                                                                              .offsetZ);
            if (tile == null) {
                outputTube = null;
                outputInv = null;
                outputSidedInv = null;
                setUpdate();
                markDirty();
                outputDir = ForgeDirection.UNKNOWN;
            } else {
                addOutput(outputDir);
            }
        }
    }

    public void OnItemEntityCollision(EntityItem item) {
        if (queuedItem != null) {
            return;
        }
        if (!missingInput()) {
            return;
        }

        if (!canAcceptItemStack(item.getEntityItem())) {
            return;
        }
        if (insertItem(ItemStack.copyItemStack(item.getEntityItem()),
                inputDir)) {
            worldObj.removeEntity(item);
            setUpdate();
            markDirty();
        }
    }

    public void onNeighborTileChange() {
        canFillInv = true;
        canExtractInv = true;
        validateConnections();
        if (inputDir == ForgeDirection.UNKNOWN) {
            searchForInput();
        }
        if (outputDir == ForgeDirection.UNKNOWN) {
            searchForOutput();
        }
    }

    public ForgeDirection getInput() {
        return inputDir;
    }

    public ForgeDirection getOutput() {
        return outputDir;
    }
}
