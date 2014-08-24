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

package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.power.FissionReactorRegistry;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;

public class TileEntityNanoFissionReactorCore extends TileEntityBase implements IInventory, IFluidHandler,
        IMultiBlockComponent {
    public static final String PACKET_CHANNEL = "Femtocraft" + "." + "fiss";

    public static final byte incrementAction = 0;
    public static final byte decrementAction = 1;
    public static final byte abortAction = 2;
    public static final int heatSlot = 0;
    public static final int thoriumSlot = 2;
    public static final int saltSlot = 1;
    public static int cooledSaltConversionPerTick = 100;
    public static float contaminatedSaltLossRatio = .7f;
    public static float contaminatedThoriumLossRatio = .3f;
    public static float thoriumConcentrationTargetIncrementAmount = .01f;
    public static double cooledSaltConversionHeatRatio = 1.;
    public static double cooledSaltHeatMultiplier = .01;
    public static double moltenSaltHeatMultiplier = .02;
    public static double unstableTemperatureThreshold = .66;
    public static double criticalTemperatureThreshold = .83;
    public static double solidSaltToThoriumRatio = .2;
    public static double enviroHeatLossMultiplier = .99;
    public static int cooledSaltTankMaxAmount = 100000;
    public static int moltenSaltTankMaxAmount = 100000;
    public static int thoriumStoreMaxAmount = 100000;
    public static int temperatureMaxAmount = 3000;
    @FemtocraftDataUtils.Saveable
    private BaseInventory inventory;
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info;
    @FemtocraftDataUtils.Saveable
    private FluidTank cooledSaltTank;
    @FemtocraftDataUtils.Saveable
    private FluidTank moltenSaltTank;
    private int thoriumStoreMax;
    @FemtocraftDataUtils.Saveable
    private int thoriumStoreCurrent;
    private int temperatureMax;
    @FemtocraftDataUtils.Saveable
    private float temperatureCurrent;
    @FemtocraftDataUtils.Saveable
    private float thoriumConcentrationTarget;

    public TileEntityNanoFissionReactorCore() {
        inventory = new BaseInventory(3);
        info = new MultiBlockInfo();
        cooledSaltTank = new FluidTank(cooledSaltTankMaxAmount);
        moltenSaltTank = new FluidTank(moltenSaltTankMaxAmount);
        thoriumStoreMax = thoriumStoreMaxAmount;
        temperatureMax = temperatureMaxAmount;
        thoriumStoreCurrent = 0;
        temperatureCurrent = 0;
        thoriumConcentrationTarget = 0;
    }

    public float getThoriumConcentrationTarget() {
        return thoriumConcentrationTarget;
    }

    public void setThoriumConcentrationTarget(float thoriumConcentrationTarget) {
        this.thoriumConcentrationTarget = Math.min(Math.max(thoriumConcentrationTarget, 0f), 1.f);
        setModified();
    }

    public void incrementThoriumConcentrationTarget() {
        setThoriumConcentrationTarget(getThoriumConcentrationTarget() + thoriumConcentrationTargetIncrementAmount);
    }

    public void decrementThoriumConcentrationTarget() {
        setThoriumConcentrationTarget(getThoriumConcentrationTarget() - thoriumConcentrationTargetIncrementAmount);
    }

    public float getTemperatureCurrent() {
        return temperatureCurrent;
    }

    public void setTemperatureCurrent(float temperatureCurrent) {
        this.temperatureCurrent = temperatureCurrent;
    }

    public int getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(int temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public int getThoriumStoreCurrent() {
        return thoriumStoreCurrent;
    }

    public void setThoriumStoreCurrent(int thoriumStoreCurrent) {
        this.thoriumStoreCurrent = thoriumStoreCurrent;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.NanoFissionReactorGuiID;
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(),
                    info.z());
            // Big Oops? Or chunk unloaded...despite having player activating it
            // >.>
            if (te == null) {
                return false;
            }

            par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, info.x(),
                    info.y(), info.z());
            return true;
        }
        return false;
    }

    @Override
    public boolean hasGUI() {
        return info.isValidMultiBlock();
    }

    public void abortReaction() {
        thoriumStoreCurrent = 0;
        cooledSaltTank.setFluid(null);
        moltenSaltTank.setFluid(null);
        setUpdate();
        setModified();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        loseHeat();
        gainHeat();
        meltThorium();
        meltSalt();
        meltWorld();
    }

    private void meltWorld() {
        if (temperatureCurrent > temperatureMax) {

        } else if (temperatureCurrent > temperatureMax * criticalTemperatureThreshold) {

        } else if (temperatureCurrent > temperatureMax * unstableTemperatureThreshold) {

        }
    }

    private void meltThorium() {
        if (getThoriumConcentration() < thoriumConcentrationTarget) {
            ItemStack item = getStackInSlot(thoriumSlot);
            if (item == null) {
                return;
            }
            FissionReactorRegistry.FissionReactorReagent reagent = FissionReactorRegistry
                    .getThoriumSource(item);
            if (reagent == null) {
                return;
            }
            if (reagent.item.stackSize <= item.stackSize && getTemperatureCurrent() >= reagent.temp &&
                    (thoriumStoreMax - thoriumStoreCurrent) >= reagent.amount) {
                decrStackSize(thoriumSlot, reagent.item.stackSize);
                temperatureCurrent -= reagent.temp;
                thoriumStoreCurrent += reagent.amount;
                setModified();
            }
        }
    }

    public float getThoriumConcentration() {
        return ((float) thoriumStoreCurrent / (float) thoriumStoreMax);
    }

    private void meltSalt() {
        int saltAmount = Math.min(getCooledSaltAmount(), cooledSaltConversionPerTick);
        if (saltAmount > 0) {
            saltAmount = Math.min(saltAmount, (moltenSaltTankMaxAmount - getMoltenSaltAmount()));
            int heatAmount = (int) (saltAmount * cooledSaltConversionHeatRatio);
            saltAmount = Math.min(heatAmount, (int) getTemperatureCurrent());
            if (saltAmount > 0) {
                cooledSaltTank.drain(saltAmount, true);
                setTemperatureCurrent((float) (getTemperatureCurrent() - saltAmount * cooledSaltConversionHeatRatio));
                addMoltenSalt(saltAmount);
                setModified();
            }
        } else {
            ItemStack item = getStackInSlot(saltSlot);
            if (item == null) {
                return;
            }
            FissionReactorRegistry.FissionReactorReagent reagent = FissionReactorRegistry.getSaltSource(item);
            if (reagent == null) {
                return;
            }
            if (reagent.item.stackSize <= item.stackSize && getTemperatureCurrent() >= reagent.temp &&
                    (moltenSaltTank.getCapacity() - getMoltenSaltAmount()) >= reagent.amount &&
                    thoriumStoreCurrent >= (reagent.amount * solidSaltToThoriumRatio)) {
                decrStackSize(saltSlot, reagent.item.stackSize);
                temperatureCurrent -= reagent.temp;
                addMoltenSalt(reagent.amount);
                thoriumStoreCurrent -= reagent.amount * solidSaltToThoriumRatio;
                setModified();
            }
        }
    }

    public int addCooledSalt(int amount) {
        int ret = cooledSaltTank.fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt, amount), true);
        if (ret > 0) {
            setModified();
        }
        return ret;
    }

    public int addMoltenSalt(int amount) {
        int ret = moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt, amount), true);
        if (ret > 0) {
            setModified();
        }
        return ret;
    }

    private void gainHeat() {
        temperatureCurrent += getCooledSaltAmount() * cooledSaltHeatMultiplier * getThoriumConcentration();
        temperatureCurrent += getMoltenSaltAmount() * moltenSaltHeatMultiplier * getThoriumConcentration();

        ItemStack heatItem = inventory.getStackInSlot(heatSlot);
        if (heatItem != null) {
            FissionReactorRegistry.FissionReactorReagent result = FissionReactorRegistry.getHeatSource(heatItem);
            if (result != null) {
                if (result.item.stackSize <= heatItem.stackSize &&
                        ((result.temp > 0 && (getTemperatureMax() - getTemperatureCurrent()) >= result.temp) ||
                                (result.temp < 0 && Math.abs(result.temp) <= getTemperatureCurrent()))) {
                    inventory.decrStackSize(heatSlot, result.item.stackSize);
                    setTemperatureCurrent(getTemperatureCurrent() + result.temp);
                    setModified();
                }
            }
        }
    }

    public int getMoltenSaltAmount() {
        return moltenSaltTank.getFluidAmount();
    }

    public int getCooledSaltAmount() {
        return cooledSaltTank.getFluidAmount();
    }

    private void loseHeat() {
        temperatureCurrent *= enviroHeatLossMultiplier;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        FluidStack fill = null;
        if (resource.getFluid() == Femtocraft.fluidCooledContaminatedMoltenSalt) {
            int amount = resource.amount;
            //limit based on thorium amount
            amount = (int) Math.min(amount, Math.min(getThoriumStoreCurrent(),
                    resource.amount * contaminatedThoriumLossRatio) / contaminatedThoriumLossRatio);
            //limit based on cooledSaltTank remaining capacity
            amount = (int) Math.min(amount,
                    (moltenSaltTank.getCapacity() - getMoltenSaltAmount()) /
                            contaminatedSaltLossRatio);
            //create
            fill = new FluidStack(Femtocraft.fluidCooledMoltenSalt, amount);
            if (doFill) {
                thoriumStoreCurrent -= amount * contaminatedThoriumLossRatio;
            }
        } else if (resource.getFluid() == Femtocraft.fluidCooledMoltenSalt) {
            fill = resource;
        } else {
            return 0;
        }

        int result = cooledSaltTank.fill(fill, doFill);
        if (result > 0) {
            setModified();
        }
        return result;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource.getFluid() != Femtocraft.fluidMoltenSalt) return null;
        return drain(from, resource.amount, doDrain);
    }


    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack result = moltenSaltTank.drain(maxDrain, doDrain);
        if (result != null && result.amount > 0) {
            setModified();
        }
        return result;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == Femtocraft.fluidCooledContaminatedMoltenSalt || fluid == Femtocraft.fluidCooledMoltenSalt;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluid == Femtocraft.fluidMoltenSalt;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{
                cooledSaltTank.getInfo(), moltenSaltTank.getInfo()
        };
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        ItemStack stack = inventory.decrStackSize(i, j);
        setModified();
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory.setInventorySlotContents(i, itemstack);
        setModified();
    }

    @Override
    public String getInvName() {
        return inventory.getInvName();
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return canPlayerUse(entityplayer);
    }

    @Override
    public void openChest() {
        inventory.openChest();
    }

    @Override
    public void closeChest() {
        inventory.closeChest();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        switch (i) {
            case heatSlot:
                return FissionReactorRegistry.getHeatSource(itemstack) != null;
            case saltSlot:
                return FissionReactorRegistry.getSaltSource(itemstack) != null;
            case thoriumSlot:
                return FissionReactorRegistry.getThoriumSource(itemstack) != null;
        }
        return false;
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean ret = info.formMultiBlock(world, x, y, z);
        if (ret) {
            setModified();
            setUpdate();
        }
        return ret;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean ret = info.breakMultiBlock(world, x, y, z);
        if (ret) {
            setModified();
            setUpdate();
        }
        return ret;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    public void setCooledMoltenSalt(int cooledMoltenSalt) {
        if (cooledSaltTank.getFluid() == null) {
            cooledSaltTank.fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt, cooledMoltenSalt), true);
        } else {
            cooledSaltTank.getFluid().amount = cooledMoltenSalt;
        }
    }

    public void setMoltenSalt(int moltenSalt) {
        if (moltenSaltTank.getFluid() == null) {
            moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt, moltenSalt), true);
        } else {
            moltenSaltTank.getFluid().amount = moltenSalt;
        }
    }

    public IFluidTank getCooledSaltTank() {
        return cooledSaltTank;
    }

    public IFluidTank getMoltenSaltTank() {
        return moltenSaltTank;
    }

    public void onIncrementClick() {
        onClick(incrementAction);
    }

    public void onDecrementClick() {
        onClick(decrementAction);
    }

    public void onAbortClick() {
        onClick(abortAction);
    }

    private void onClick(byte action) {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = TileEntityNanoFissionReactorCore.PACKET_CHANNEL;

        ByteArrayOutputStream bos = new ByteArrayOutputStream(17);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(xCoord);
            outputStream.writeInt(yCoord);
            outputStream.writeInt(zCoord);
            outputStream.writeInt(worldObj.provider.dimensionId);
            outputStream.writeByte(action);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        packet.data = bos.toByteArray();
        packet.length = bos.size();

        PacketDispatcher.sendPacketToServer(packet);
    }

    public void handleAction(byte action) {
        switch (action) {
            case incrementAction:
                incrementThoriumConcentrationTarget();
                break;
            case decrementAction:
                decrementThoriumConcentrationTarget();
                break;
            case abortAction:
                abortReaction();
                break;
            default:
                Femtocraft.logger.log(Level.SEVERE,
                        "Received invalid action for Fusion Reactor at x-" + xCoord + " y-" + yCoord + " z-" + zCoord +
                                " at dimension-" + worldObj.provider.dimensionId + ".");
        }
    }
}
