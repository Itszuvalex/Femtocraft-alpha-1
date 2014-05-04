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

package femtocraft.transport.items.tiles;

import femtocraft.api.IVacuumTube;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntityVacuumTube extends TileEntity implements IVacuumTube {
    // NOT A FEMTOCRAFTTILEENTITY AT THIS POINT IN TIME, @SAVEABLE DOESN"T WORK.
    // DURP

    // hasItem array for client-side rendering only
    // Server will update this array and this alone to save bytes
    // Player has no need to know WHAT is in the pipes, anyways
    static final public String packetChannel = "Femtocraft" + ".VTube";

    // Will not be @Saveable due to bit masking
    public boolean[] hasItem = new boolean[4];
    public
    @Saveable
    ItemStack queuedItem = null;
    ForgeDirection inputDir = ForgeDirection.UNKNOWN;
    ForgeDirection outputDir = ForgeDirection.UNKNOWN;
    private
    @Saveable
    ItemStack[] items = new ItemStack[4];
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

    public TileEntityVacuumTube() {
        super();
        Arrays.fill(hasItem, false);
        Arrays.fill(items, null);
    }

    public boolean isEndpoint() {
        return (missingInput() || missingOutput());
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
                || (((outputTube != null && !outputTube.canInsertItem(null)) || ((outputInv != null || outputSidedInv != null) && !canFillInv))
                && hasItem[0] && hasItem[1] && hasItem[2] && hasItem[3] && ((inputTube == null || queuedItem != null)));
    }

    public boolean canInsertItem(ItemStack item) {
        return queuedItem == null;
    }

    @Override
    public boolean insertItem(ItemStack item) {
        if (isOverflowing()) {
            return false;
        }
        if (queuedItem == null) {
            queuedItem = item.copy();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);

            if (items[0] == null) {
                items[0] = queuedItem;
                queuedItem = null;
                hasItem[0] = true;
            }

            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);

            return true;
        }

        return false;
    }

    @Override
    public boolean hasInput() {
        return !missingInput();
    }

    @Override
    public boolean hasOutput() {
        return !missingOutput();
    }

    @Override
    public void disconnect(ForgeDirection dir) {
        if (isInput(dir)) {
            clearInput();
        }
        else if (isOutput(dir)) {
            clearOutput();
        }
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

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);

        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

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

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);

        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    @Override
    public boolean isOutput(ForgeDirection dir) {
        return hasOutput() && dir == outputDir;
    }

    @Override
    public boolean isInput(ForgeDirection dir) {
        return hasInput() && dir == inputDir;
    }

    public void onBlockBreak() {
        clearInput();
        clearOutput();

        for (int i = 0; i < items.length; i++) {
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

    public void searchForFullConnections() {
        searchForInput();
        searchForOutput();
    }

    public boolean searchForInput() {
        List<Integer> check = new ArrayList<Integer>();

        // Search for BlockVacuumTube connections first
        for (int i = 0; i < 6; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
                                                          yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }
            if (tile instanceof TileEntityVacuumTube) {
                TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;
                if (tube.missingOutput()) {
                    if (addInput(dir)) {
                        return true;
                    }
                }
            }

            check.add(i);
        }

        for (Integer i : check) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
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
        List<Integer> check = new ArrayList<Integer>();

        // Check for VacuumTubeTiles first
        for (int i = 0; i < 6; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
                                                          yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile == null) {
                continue;
            }
            if (tile instanceof TileEntityVacuumTube) {
                TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;
                if (tube.missingInput()) {
                    if (addOutput(dir)) {
                        return true;
                    }
                }
            }

            check.add(i);
        }

        for (Integer i : check) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
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

    public boolean searchForConnection() {
        cycleSearch();

        return false;
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
        TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
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
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
            return true;
        }

        if (tile instanceof IVacuumTube) {

            inputTube = (IVacuumTube) tile;
            inputDir = dir;
            inputSidedInv = null;
            inputInv = null;
            inputTube.addOutput(dir.getOpposite());
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
            return true;
        }

        if (tile instanceof IInventory) {
            inputTube = null;
            inputDir = dir;
            inputSidedInv = null;
            inputInv = (IInventory) tile;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
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
        TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
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
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
            return true;
        }

        if (tile instanceof IVacuumTube) {

            outputTube = (IVacuumTube) tile;
            outputDir = dir;
            outputSidedInv = null;
            outputInv = null;
            outputTube.addInput(dir.getOpposite());
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
            return true;
        }

        if (tile instanceof IInventory) {
            outputTube = null;
            outputDir = dir;
            outputSidedInv = null;
            outputInv = (IInventory) tile;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
            return true;
        }

        return false;
    }

    private void cycleSearch() {
        int i = 0;

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

                } while ((!addInput(ForgeDirection
                                            .getOrientation(lastInputOrientation)) && (j < 6)));
            }

            ++i;

        } while ((!addOutput(ForgeDirection
                                     .getOrientation(lastOutputOrientation)) && (i < 6)));
    }

    private void removeLastInput() {
        if (inputTube == null) {
            return;
        }
        clearInput();
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    private void removeLastOutput() {
        if (outputTube == null) {
            return;
        }
        clearOutput();
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    private void setupReceiveFromTube(TileEntityVacuumTube tube,
                                      ForgeDirection dir) {
        tube.outputTube = this;
        tube.outputInv = null;
        tube.outputSidedInv = null;
        tube.outputDir = dir.getOpposite();
        tube.worldObj.markBlockForUpdate(tube.xCoord, tube.yCoord, tube.zCoord);
        tube.worldObj.markBlockForRenderUpdate(tube.xCoord, tube.yCoord,
                                               tube.zCoord);
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    private void setupSendToTube(TileEntityVacuumTube tube, ForgeDirection dir) {
        tube.inputTube = this;
        tube.inputInv = null;
        tube.inputSidedInv = null;
        tube.inputDir = dir.getOpposite();
        tube.worldObj.markBlockForUpdate(tube.xCoord, tube.yCoord, tube.zCoord);
        tube.worldObj.markBlockForRenderUpdate(tube.xCoord, tube.yCoord,
                                               tube.zCoord);
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    private void removeReceiveFromTube(TileEntityVacuumTube tube) {
        if (tube.outputTube != this) {
            return;
        }

        tube.outputTube = null;
        tube.outputDir = tube.inputDir.getOpposite();
        tube.worldObj.markBlockForUpdate(tube.xCoord, tube.yCoord, tube.zCoord);
        tube.worldObj.markBlockForRenderUpdate(tube.xCoord, tube.yCoord,
                                               tube.zCoord);
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    private void removeSendToTube(TileEntityVacuumTube tube) {
        if (tube.inputTube != this) {
            return;
        }

        tube.inputTube = null;
        tube.inputDir = tube.outputDir.getOpposite();
        tube.worldObj.markBlockForUpdate(tube.xCoord, tube.yCoord, tube.zCoord);
        tube.worldObj.markBlockForRenderUpdate(tube.xCoord, tube.yCoord,
                                               tube.zCoord);
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    public void validateConnections() {
        if (inputDir != ForgeDirection.UNKNOWN) {
            TileEntity tile = worldObj.getBlockTileEntity(xCoord
                                                                  + inputDir.offsetX, yCoord + inputDir.offsetY, zCoord
                                                                  + inputDir.offsetZ);
            if (tile == null) {
                inputTube = null;
                inputInv = null;
                inputSidedInv = null;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
                worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord,
                                                     this);
            }
            else {
                addInput(inputDir);
            }
        }
        if (outputDir != ForgeDirection.UNKNOWN) {
            TileEntity tile = worldObj.getBlockTileEntity(xCoord
                                                                  + outputDir.offsetX, yCoord + outputDir.offsetY, zCoord
                                                                  + outputDir.offsetZ);
            if (tile == null) {
                outputTube = null;
                outputInv = null;
                outputSidedInv = null;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
                worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord,
                                                     this);
            }
            else {
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

        insertItem(ItemStack.copyItemStack(item.getEntityItem()));
        worldObj.removeEntity(item);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);

        NBTTagList nbttaglist = par1nbtTagCompound.getTagList("Items");
        items = new ItemStack[items.length];
        hasItem = new boolean[hasItem.length];
        Arrays.fill(items, null);
        Arrays.fill(hasItem, false);

        for (int i = 0; i < nbttaglist.tagCount() - 1; ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
                    .tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < items.length) {
                items[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                hasItem[b0] = true;
            }
        }

        NBTTagCompound nbttagcompoundsmelt = (NBTTagCompound) nbttaglist
                .tagAt(nbttaglist.tagCount() - 1);
        if (nbttagcompoundsmelt.getBoolean("Queued")) {
            queuedItem = ItemStack.loadItemStackFromNBT(nbttagcompoundsmelt);
        }
        else {
            queuedItem = null;
        }

        byte connections = par1nbtTagCompound.getByte("Connections");
        parseConnectionMask(connections);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);

        NBTTagList taglist = new NBTTagList();
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.items[i].writeToNBT(nbttagcompound1);
                taglist.appendTag(nbttagcompound1);
            }
        }

        NBTTagCompound queueCompound = new NBTTagCompound();
        boolean queued = queuedItem != null;
        queueCompound.setBoolean("Queued", queued);
        if (queued) {
            queuedItem.writeToNBT(queueCompound);
        }
        taglist.appendTag(queueCompound);

        par1nbtTagCompound.setTag("Items", taglist);

        par1nbtTagCompound.setByte("Connections", generateConnectionMask());
    }

    public void onNeighborTileChange() {
        canFillInv = true;
        canExtractInv = true;
    }

    @Override
    public void updateEntity() {

        if (worldObj.isRemote) {
            // if(overflowing) return;
            //
            // for(int i = hasItem.length-2; i >= 0; i--)
            // {
            // if(hasItem[i+1] = hasItem[i])
            // worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            // }
            return;
        }

        if (needsCheckInput || inputDir != ForgeDirection.UNKNOWN
                && missingInput()) {
            needsCheckInput = false;
            addInput(inputDir);
        }
        if (needsCheckOutput || outputDir != ForgeDirection.UNKNOWN
                && missingOutput()) {
            needsCheckOutput = false;
            addOutput(outputDir);
        }

        if (items[3] != null) {
            // If next tube has a slot
            if (outputTube != null) {
                if (outputTube.insertItem(items[3])) {
                    items[3] = null;
                    hasItem[3] = false;
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
                    worldObj.markTileEntityChunkModified(xCoord, yCoord,
                                                         zCoord, this);
                }
            }
            else if (outputSidedInv != null) {
                if (canFillInv) {
                    int side = FemtocraftUtils.indexOfForgeDirection(outputDir
                                                                             .getOpposite());
                    int[] slots = outputSidedInv
                            .getAccessibleSlotsFromSide(side);
                    int invMax = outputSidedInv.getInventoryStackLimit();
                    for (int i = 0; i < slots.length && items[3] != null; i++) {
                        if (outputSidedInv.canInsertItem(slots[i], items[3],
                                                         side)) {
                            if (outputSidedInv.isItemValidForSlot(slots[i],
                                                                  items[3])) {
                                ItemStack slotStack = outputSidedInv
                                        .getStackInSlot(slots[i]);

                                // If no items in that slot
                                if (slotStack == null) {
                                    outputSidedInv.setInventorySlotContents(
                                            slots[i], items[3].copy());
                                    items[3] = null;
                                    hasItem[3] = false;
                                    worldObj.markBlockForUpdate(xCoord, yCoord,
                                                                zCoord);
                                    worldObj.markBlockForRenderUpdate(xCoord,
                                                                      yCoord, zCoord);
                                    worldObj.markTileEntityChunkModified(
                                            xCoord, yCoord, zCoord, this);
                                    outputSidedInv.onInventoryChanged();
                                }
                                // Combine items
                                else {
                                    // Account for possible mismatch
                                    if (items[3].itemID != slotStack.itemID) {
                                        continue;
                                    }
                                    if (!items[3].isStackable()) {
                                        continue;
                                    }

                                    int itemMax = slotStack.getMaxStackSize();
                                    int max = invMax > itemMax ? itemMax
                                            : invMax;
                                    int room = max - slotStack.stackSize;
                                    int amount = room > items[3].stackSize ? items[3].stackSize
                                            : room;
                                    slotStack.stackSize += amount;
                                    items[3].stackSize -= amount;
                                    if (items[3].stackSize <= 0) {
                                        items[3] = null;
                                        hasItem[3] = false;
                                        worldObj.markBlockForUpdate(xCoord,
                                                                    yCoord, zCoord);
                                        worldObj.markBlockForRenderUpdate(
                                                xCoord, yCoord, zCoord);
                                    }
                                    worldObj.markTileEntityChunkModified(
                                            xCoord, yCoord, zCoord, this);
                                    outputSidedInv.onInventoryChanged();
                                }
                            }
                        }
                    }
                    if (items[3] != null) {
                        canFillInv = false;
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        worldObj.markBlockForRenderUpdate(xCoord, yCoord,
                                                          zCoord);
                        worldObj.markTileEntityChunkModified(xCoord, yCoord,
                                                             zCoord, this);
                    }
                }
            }
            else if (outputInv != null) {
                if (canFillInv) {
                    int size = outputInv.getSizeInventory();
                    int invMax = outputInv.getInventoryStackLimit();
                    for (int i = 0; i < size && items[3] != null; i++) {
                        if (outputInv.isItemValidForSlot(i, items[3])) {
                            ItemStack slotStack = outputInv.getStackInSlot(i);

                            // If no items in that slot
                            if (slotStack == null) {
                                outputInv.setInventorySlotContents(i,
                                                                   items[3].copy());
                                items[3] = null;
                                hasItem[3] = false;
                                worldObj.markBlockForUpdate(xCoord, yCoord,
                                                            zCoord);
                                worldObj.markBlockForRenderUpdate(xCoord,
                                                                  yCoord, zCoord);
                                worldObj.markTileEntityChunkModified(xCoord,
                                                                     yCoord, zCoord, this);
                                outputInv.onInventoryChanged();
                            }
                            // Combine items
                            else {
                                // Account for possible mismatch
                                if (items[3].itemID != slotStack.itemID) {
                                    continue;
                                }
                                if (!items[3].isStackable()) {
                                    continue;
                                }

                                int itemMax = slotStack.getMaxStackSize();
                                int max = invMax > itemMax ? itemMax : invMax;
                                int room = max - slotStack.stackSize;
                                int amount = room > items[3].stackSize ? items[3].stackSize
                                        : room;
                                slotStack.stackSize += amount;
                                items[3].stackSize -= amount;
                                if (items[3].stackSize <= 0) {
                                    items[3] = null;
                                    hasItem[3] = false;
                                    worldObj.markBlockForUpdate(xCoord, yCoord,
                                                                zCoord);
                                    worldObj.markBlockForRenderUpdate(xCoord,
                                                                      yCoord, zCoord);
                                }
                                worldObj.markTileEntityChunkModified(xCoord,
                                                                     yCoord, zCoord, this);
                                outputInv.onInventoryChanged();
                            }
                        }

                    }
                    if (items[3] != null) {
                        canFillInv = false;
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        worldObj.markBlockForRenderUpdate(xCoord, yCoord,
                                                          zCoord);
                    }
                }
            }
            else if (missingOutput()) {
                ejectItem(3);
            }
        }

        // Move up rest of items, starting from end
        for (int i = items.length - 2; i >= 0; i--) {
            // Move up only if room
            if (items[i + 1] == null) {
                if (hasItem[i]) {
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
                    worldObj.markTileEntityChunkModified(xCoord, yCoord,
                                                         zCoord, this);
                }

                hasItem[i + 1] = hasItem[i];
                hasItem[i] = false;
                items[i + 1] = items[i];
                items[i] = null;
            }
        }

        // Blockage. We're done
        if (isOverflowing()) {
            overflowing = true;
            // Send overflow update
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            return;
        }

        // If being loaded by another BlockVacuumTube
        if (queuedItem != null) {
            items[0] = queuedItem;
            hasItem[0] = true;
            queuedItem = null;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
        }
        // Pull from inventory
        else if (inputSidedInv != null) {
            if (canExtractInv) {
                boolean extracted = false;
                int side = FemtocraftUtils.indexOfForgeDirection(inputDir
                                                                         .getOpposite());
                int[] slots = inputSidedInv.getAccessibleSlotsFromSide(side);
                for (int slot : slots) {
                    ItemStack stack = inputSidedInv.getStackInSlot(slot);
                    if (stack != null) {
                        if (!inputSidedInv.canExtractItem(slot, stack, side)) {
                            continue;
                        }
                        extracted = true;
                        items[0] = inputSidedInv.decrStackSize(slot, 64);
                        hasItem[0] = true;
                        inputSidedInv.onInventoryChanged();
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        worldObj.markBlockForRenderUpdate(xCoord, yCoord,
                                                          zCoord);
                        worldObj.markTileEntityChunkModified(xCoord, yCoord,
                                                             zCoord, this);
                        return;
                    }
                }
                if (!extracted) {
                    canExtractInv = false;
                }
            }
        }
        else if (inputInv != null) {
            if (canExtractInv) {
                boolean extracted = false;
                int size = inputInv.getSizeInventory();
                for (int i = 0; i < size; i++) {
                    ItemStack stack = inputInv.getStackInSlot(i);
                    if (stack != null) {
                        extracted = true;
                        items[0] = inputInv.decrStackSize(i, 64);
                        hasItem[0] = true;
                        inputInv.onInventoryChanged();
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        worldObj.markBlockForRenderUpdate(xCoord, yCoord,
                                                          zCoord);
                        worldObj.markTileEntityChunkModified(xCoord, yCoord,
                                                             zCoord, this);
                        return;
                    }
                }
                if (!extracted) {
                    canExtractInv = false;
                }
            }
        }
        // Suck them in, only if room
        else if (missingInput()) {
            ForgeDirection dir = inputDir;

            if (inputDir == ForgeDirection.UNKNOWN) {
                if (outputDir == ForgeDirection.UNKNOWN) {
                    dir = ForgeDirection.SOUTH;
                }
                else {
                    dir = outputDir.getOpposite();
                }
            }

            float x = xCoord + dir.offsetX;
            float y = yCoord + dir.offsetY;
            float z = zCoord + dir.offsetZ;

            List<EntityItem> items = worldObj.getEntitiesWithinAABB(
                    EntityItem.class,
                    AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1.f,
                                                        y + 1.f, z + 1.f)
            );
            for (EntityItem item : items) {
                item.addVelocity((xCoord + .5) - item.posX, (yCoord + .5)
                        - item.posY, (zCoord + .5) - item.posZ);
            }
        }
    }

    private void ejectItem(int slot) {
        ItemStack dropItem = items[slot];
        ejectItemStack(dropItem);
        items[slot] = null;
        hasItem[slot] = false;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    private void ejectItemStack(ItemStack dropItem) {
        if (dropItem == null) {
            return;
        }
        if (worldObj.isRemote) {
            return;
        }

        // Throw the item out into the world!
        EntityItem entityitem = new EntityItem(worldObj, xCoord + .5,
                                               yCoord + .5, zCoord + .5, dropItem.copy());
        ForgeDirection dir = outputDir;
        if (dir == ForgeDirection.UNKNOWN) {
            if (inputDir == ForgeDirection.UNKNOWN) {
                dir = ForgeDirection.NORTH;
            }
            else {
                dir = inputDir.getOpposite();
            }
        }

        entityitem.motionX = dir.offsetX;
        entityitem.motionY = dir.offsetY;
        entityitem.motionZ = dir.offsetZ;
        worldObj.spawnEntityInWorld(entityitem);
    }

    @Override
    public Packet getDescriptionPacket() {
        return generatePacket();
    }

    private Packet250CustomPayload generatePacket() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(14);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(xCoord);
            outputStream.writeInt(yCoord);
            outputStream.writeInt(zCoord);
            // write the relevant information here... exemple:
            outputStream.writeByte(generateItemMask());
            outputStream.writeByte(generateConnectionMask());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = packetChannel;
        packet.data = bos.toByteArray();
        packet.length = bos.size();

        return packet;
    }

    public byte generateConnectionMask() {
        byte output = 0;
        output += (FemtocraftUtils.indexOfForgeDirection(inputDir) & 7);
        output += missingInput() ? 0 : 1 << 3;
        output += (FemtocraftUtils.indexOfForgeDirection(outputDir) & 7) << 4;
        output += missingOutput() ? 0 : 1 << 7;
        return output;
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

        TileEntity inputTile = worldObj.getBlockTileEntity(xCoord
                                                                   + inputDir.offsetX, yCoord + inputDir.offsetY, zCoord
                                                                   + inputDir.offsetZ);
        if (inputTile == null) {
            if (hasInput) {
                needsCheckInput = true;
            }
        }
        else {
            addInput(inputDir);
        }

        TileEntity outputTile = worldObj.getBlockTileEntity(xCoord
                                                                    + outputDir.offsetX, yCoord + outputDir.offsetY, zCoord
                                                                    + outputDir.offsetZ);
        if (outputTile == null) {
            if (hasOutput) {
                needsCheckOutput = true;
            }
        }
        else {
            addOutput(outputDir);
        }
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

    public void parseItemMask(byte mask) {
        byte temp;

        for (int i = 0; i < hasItem.length; i++) {
            temp = mask;
            hasItem[i] = (((temp >> i) & 1) == 1);
        }

        temp = mask;
        overflowing = (((temp >> hasItem.length) & 1) == 1);
    }

    public ForgeDirection getInput() {
        return inputDir;
    }

    public ForgeDirection getOutput() {
        return outputDir;
    }
}
