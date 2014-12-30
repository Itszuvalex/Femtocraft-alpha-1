package com.itszuvalex.femtocraft.api.power;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Chris on 12/30/2014.
 */
public class ItemStackPowerContainerWrapper implements IPowerContainer {
    public final ItemStack itemStack;
    public final NBTPowerContainerWrapper nbtPowerContainerWrapper;

    public static ItemStackPowerContainerWrapper getWrapperForStack(ItemStack stack) {
        return getWrapperForStack(stack, true);
    }

    public static ItemStackPowerContainerWrapper getWrapperForStack(ItemStack stack, boolean forceNBT) {
        return stack == null ? null : new ItemStackPowerContainerWrapper(stack, forceNBT);
    }

    public ItemStackPowerContainerWrapper(ItemStack stack) throws IllegalArgumentException {
        this(stack, true);
    }

    /**
     * @param stack    ItemStack to wrap around.
     * @param forceNBT If true, will generate the nbt data structure on the item when it is attempted to be accessed and
     *                 not found.  Otherwise, will return empty values in its stead.
     */
    public ItemStackPowerContainerWrapper(ItemStack stack, boolean forceNBT) {
        if (stack == null) throw new IllegalArgumentException("ItemStack cannot be null.");
        itemStack = stack;
        this.nbtPowerContainerWrapper = NBTPowerContainerWrapper.wrapperFromNBT(stack.getTagCompound(), forceNBT);
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level) {
        return level == getTechLevel();
    }

    @Override
    public EnumTechLevel getTechLevel() {
        return nbtPowerContainerWrapper.getTechLevel();
    }

    @Override
    public int getCurrentPower() {
        return nbtPowerContainerWrapper.getCurrentPower();
    }

    @Override
    public int getMaxPower() {
        return nbtPowerContainerWrapper.getMaxPower();
    }

    @Override
    public float getFillPercentage() {
        int current = getCurrentPower();
        int max = getMaxPower();
        if (max == 0) return 0;
        return ((float) current) / ((float) max);
    }

    @Override
    public float getFillPercentageForCharging() {
        return getFillPercentage();
    }

    @Override
    public float getFillPercentageForOutput() {
        return getFillPercentage();
    }

    @Override
    public boolean canCharge() {
        return true;
    }

    @Override
    public int charge(int amount) {
        int room = getMaxPower() - getCurrentPower();
        amount = room < amount ? room : amount;
        setCurrentPower(getCurrentPower() + amount);
        return amount;
    }

    @Override
    public boolean consume(int amount) {
        if (amount > getCurrentPower()) return false;
        setCurrentPower(getCurrentPower() - amount);
        return true;
    }

    public boolean setCurrentPower(int amount) {
        return nbtPowerContainerWrapper.setCurrentPower(amount);
    }

    public boolean setMaxPower(int amount) {
        return nbtPowerContainerWrapper.setMaxPower(amount);
    }

    public boolean setTechLevel(EnumTechLevel level) {
        return nbtPowerContainerWrapper.setTechLevel(level);
    }

    public boolean copyFromPowerContainer(IPowerContainer container) {
        return nbtPowerContainerWrapper.copyFromPowerContainer(container);
    }

    public boolean attemptCopyToPowerContainer(IPowerContainer container) {
        return nbtPowerContainerWrapper.attemptCopyToPowerContainer(container);
    }

    public void copyToPowerContainer(PowerContainer container) {
        nbtPowerContainerWrapper.copyToPowerContainer(container);
    }

    public PowerContainer toContainer() {
        return nbtPowerContainerWrapper.toContainer();
    }

    public boolean hasPowerData() {return nbtPowerContainerWrapper.hasPowerData();}

    @SuppressWarnings("unchecked")
    public void addInformationToTooltip(List tooltip) {
        nbtPowerContainerWrapper.addInformationToTooltip(tooltip);
    }
}
