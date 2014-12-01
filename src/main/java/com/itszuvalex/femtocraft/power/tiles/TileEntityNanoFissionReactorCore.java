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
import com.itszuvalex.femtocraft.FemtocraftGuiConstants;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler;
import com.itszuvalex.femtocraft.network.messages.MessageFissionReactorCore;
import com.itszuvalex.femtocraft.power.FissionReactorRegistry;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockNanoFissionReactor;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import org.apache.logging.log4j.Level;


public class TileEntityNanoFissionReactorCore extends TileEntityBase implements IInventory, IFluidHandler,
        IMultiBlockComponent {
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
    public static float minimumThoriumConcentrationToMeltSalt = .01f;
    public static int minimumHeat = 100;
    public static double cooledSaltConversionHeatRatio = 1.;
    public static double cooledSaltHeatMultiplier = .001;
    public static double moltenSaltHeatMultiplier = .002;
    public static double unstableTemperatureThreshold = .66;
    public static double criticalTemperatureThreshold = .83;
    public static double solidSaltToThoriumRatio = .2;
    public static double enviroHeatLossMultiplier = .99;
    public static int cooledSaltTankMaxAmount = 100000;
    public static int moltenSaltTankMaxAmount = 100000;
    public static int thoriumStoreMaxAmount = 100000;
    public static int temperatureMaxAmount = 3000;
    @Saveable
    private BaseInventory inventory;
    @Saveable(desc = true)
    private MultiBlockInfo info;
    @Saveable
    private FluidTank cooledSaltTank;
    @Saveable
    private FluidTank moltenSaltTank;
    private int thoriumStoreMax;
    @Saveable
    private int thoriumStoreCurrent;
    private int temperatureMax;
    @Saveable
    private float temperatureCurrent;
    @Saveable
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

    public enum ReactorState {
        INACTIVE,
        ACTIVE,
        UNSTABLE,
        CRITICAL
    }

    public ReactorState getState() {
        float temp = getTemperatureCurrent();
        float max = getTemperatureMax();
        if (temp <= 0) {
            return ReactorState.INACTIVE;
        }
        if (temp >= 0 && temp < max * unstableTemperatureThreshold) {
            return ReactorState.ACTIVE;
        }
        if (temp >= max * unstableTemperatureThreshold && temp < max * criticalTemperatureThreshold) {
            return ReactorState.UNSTABLE;
        }
        return ReactorState.CRITICAL;
    }

    @Override
    public void onInventoryChanged() {
        MultiBlockNanoFissionReactor.instance.onMultiblockInventoryChanged(worldObj, info.x(), info.y(), info.z());
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
        return FemtocraftGuiConstants.NanoFissionReactorGuiID();
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(),
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
        return isValidMultiBlock();
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
        if (getTemperatureCurrent() < minimumHeat) {
            return;
        }

        if (getThoriumConcentration() < getThoriumConcentrationTarget()) {
            ItemStack item = getStackInSlot(thoriumSlot);
            if (item == null) {
                return;
            }
            FissionReactorRegistry.FissionReactorReagent reagent = FissionReactorRegistry
                    .getThoriumSource(item);
            if (reagent == null) {
                return;
            }
            if (reagent.item().stackSize <= item.stackSize && getTemperatureCurrent() >= reagent.temp() &&
                getTemperatureCurrent() > minimumHeat &&
                (thoriumStoreMax - thoriumStoreCurrent) >= reagent.amount()) {
                decrStackSize(thoriumSlot, reagent.item().stackSize);
                setTemperatureCurrent(getTemperatureCurrent() - reagent.temp());
                thoriumStoreCurrent += reagent.amount();
                setModified();
            }
        }
    }

    public float getThoriumConcentration() {
        return ((float) thoriumStoreCurrent / (float) thoriumStoreMax);
    }

    private void meltSalt() {
        if (getThoriumConcentration() < minimumThoriumConcentrationToMeltSalt) {
            return;
        }

        if (getTemperatureCurrent() < minimumHeat) {
            return;
        }

        int saltAmount = Math.min(getCooledSaltAmount(), cooledSaltConversionPerTick);
        if (saltAmount > 0) {
            saltAmount = Math.min(saltAmount, (moltenSaltTank.getCapacity() - getMoltenSaltAmount()));
            int heatAmount = (int) (saltAmount * cooledSaltConversionHeatRatio);
            saltAmount = (int) Math.min(heatAmount, getTemperatureCurrent() / cooledSaltConversionHeatRatio);
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
            if (reagent != null) {
                if (reagent.item().stackSize <= item.stackSize && getTemperatureCurrent() >= reagent.temp() &&
                    (moltenSaltTank.getCapacity() - getMoltenSaltAmount()) >= reagent.amount() &&
                    thoriumStoreCurrent >= (reagent.amount() * solidSaltToThoriumRatio)) {
                    decrStackSize(saltSlot, reagent.item().stackSize);
                    setTemperatureCurrent(getTemperatureCurrent() - reagent.temp());
                    addMoltenSalt(reagent.amount());
                    thoriumStoreCurrent -= reagent.amount() * solidSaltToThoriumRatio;
                    setModified();
                }
            }
        }
    }

    public int addCooledSalt(int amount) {
        int ret = cooledSaltTank.fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt(), amount), true);
        if (ret > 0) {
            setModified();
        }
        return ret;
    }

    public int addMoltenSalt(int amount) {
        int ret = moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt(), amount), true);
        if (ret > 0) {
            setModified();
        }
        return ret;
    }

    private void gainHeat() {
        setTemperatureCurrent((float) (getTemperatureCurrent() +
                                       ((float) getCooledSaltAmount() * cooledSaltHeatMultiplier *
                                        getThoriumConcentration())));
        setTemperatureCurrent((float) (getTemperatureCurrent() +
                                       ((float) getMoltenSaltAmount() * moltenSaltHeatMultiplier *
                                        getThoriumConcentration())));

        ItemStack heatItem = inventory.getStackInSlot(heatSlot);
        if (heatItem != null) {
            FissionReactorRegistry.FissionReactorReagent result = FissionReactorRegistry.getHeatSource(heatItem);
            if (result != null) {
                if (result.item().stackSize <= heatItem.stackSize &&
                    ((result.temp() > 0 && (getTemperatureMax() - getTemperatureCurrent()) >= result.temp()) ||
                     (result.temp() < 0 && Math.abs(result.temp()) <= getTemperatureCurrent()))) {
                    decrStackSize(heatSlot, result.item().stackSize);
                    setTemperatureCurrent(getTemperatureCurrent() + result.temp());
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
        setTemperatureCurrent((float) (getTemperatureCurrent() * enviroHeatLossMultiplier));
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        FluidStack fill = null;
        if (resource.getFluid() == Femtocraft.fluidCooledContaminatedMoltenSalt()) {
            int amount = resource.amount;
            //limit based on thorium amount
            amount = (int) Math.min(amount,
                    Math.min(Math.max(getThoriumStoreCurrent() - minimumThoriumConcentrationToMeltSalt, 0),
                            resource.amount * contaminatedThoriumLossRatio) / contaminatedThoriumLossRatio);
            //limit based on cooledSaltTank remaining capacity
            amount = (int) Math.min(amount,
                    (moltenSaltTank.getCapacity() - getMoltenSaltAmount()) /
                    contaminatedSaltLossRatio);
            //create
            fill = new FluidStack(Femtocraft.fluidCooledMoltenSalt(), amount);
            if (doFill) {
                thoriumStoreCurrent -= amount * contaminatedThoriumLossRatio;
            }
        } else if (resource.getFluid() == Femtocraft.fluidCooledMoltenSalt()) {
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
        if (resource.getFluid() != Femtocraft.fluidMoltenSalt()) return null;
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
        return fluid == Femtocraft.fluidCooledContaminatedMoltenSalt() || fluid == Femtocraft.fluidCooledMoltenSalt();
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluid == Femtocraft.fluidMoltenSalt();
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
        onInventoryChanged();
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
        onInventoryChanged();
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
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
    public void openInventory() {
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
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
        return info != null && info.isValidMultiBlock();
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
            cooledSaltTank.fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt(), cooledMoltenSalt), true);
        } else {
            cooledSaltTank.getFluid().amount = cooledMoltenSalt;
        }
    }

    public void setMoltenSalt(int moltenSalt) {
        if (moltenSaltTank.getFluid() == null) {
            moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt(), moltenSalt), true);
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
        FemtocraftPacketHandler.INSTANCE().sendToServer(new MessageFissionReactorCore(xCoord, yCoord, zCoord,
                worldObj.provider.dimensionId, action));
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
                Femtocraft.log(Level.ERROR,
                        "Received invalid action for Fusion Reactor at x-" + xCoord + " y-" + yCoord + " z-" + zCoord +
                        " at dimension-" + worldObj.provider.dimensionId + ".");
        }
    }
}
