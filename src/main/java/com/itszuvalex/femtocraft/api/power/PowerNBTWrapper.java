package com.itszuvalex.femtocraft.api.power;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Chris on 12/30/2014.
 */
public class PowerNBTWrapper implements IPowerContainer {
    public final NBTTagCompound parent;
    public final boolean forceNBT;
    public static final String powerCompoundTag = "FemtocraftPower";
    public static final String powerCurrentTag = "CurrentPower";
    public static final String powerMaximumTag = "MaximumPower";
    public static final String powerLevelTag = "PowerLevel";

    public static PowerNBTWrapper wrapperFromNBT(NBTTagCompound parent) {return wrapperFromNBT(parent, true);}

    public static PowerNBTWrapper wrapperFromNBT(NBTTagCompound parent, boolean forceNBT) {
        return parent == null ? null : new PowerNBTWrapper(parent, forceNBT);
    }

    public PowerNBTWrapper(NBTTagCompound parent) {
        this(parent, true);
    }

    public PowerNBTWrapper(NBTTagCompound parent, boolean forceNBT) {
        this.parent = parent;
        this.forceNBT = forceNBT;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level) {
        return level == getTechLevel();
    }

    @Override
    public EnumTechLevel getTechLevel() {
        if (!hasPowerData()) return null;
        return EnumTechLevel.getTech(getPowerTagCompound().getString(powerLevelTag));
    }

    @Override
    public int getCurrentPower() {
        if (!hasPowerData()) return 0;
        return getPowerTagCompound().getInteger(powerCurrentTag);
    }

    @Override
    public int getMaxPower() {
        if (!hasPowerData()) return 0;
        return getPowerTagCompound().getInteger(powerMaximumTag);
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
        return hasPowerData();
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

    public NBTTagCompound getPowerTagCompound() {
        NBTTagCompound powerCompound = parent.getCompoundTag(powerCompoundTag);
        if ((powerCompound == null || powerCompound.hasNoTags()) && forceNBT) {
            powerCompound = new NBTTagCompound();
            parent.setTag(powerCompoundTag, powerCompound);
        }
        return powerCompound;
    }

    public boolean setCurrentPower(int amount) {
        if (!hasPowerData() && !forceNBT) return false;
        if (amount < 0 || amount > getMaxPower()) return false;
        getPowerTagCompound().setInteger(powerCurrentTag, amount);
        return true;
    }

    public boolean setMaxPower(int amount) {
        if (!hasPowerData() && !forceNBT) return false;
        getPowerTagCompound().setInteger(powerMaximumTag, amount);
        if (getCurrentPower() > getMaxPower()) setCurrentPower(getMaxPower());
        return true;
    }

    public boolean setTechLevel(EnumTechLevel level) {
        if (!hasPowerData() && !forceNBT) return false;
        getPowerTagCompound().setString(powerLevelTag, level.key);
        return true;
    }

    public boolean copyFromPowerContainer(IPowerContainer container) {
        boolean result = setTechLevel(container.getTechLevel());
        result = setMaxPower(container.getMaxPower()) && result;
        return setCurrentPower(container.getCurrentPower()) && result;
    }

    public boolean attemptCopyToPowerContainer(IPowerContainer container) {
        if (getTechLevel() != container.getTechLevel()) return false;
        boolean result = container.consume(container.getCurrentPower());
        result = (container.charge(getCurrentPower()) == getCurrentPower()) && result;
        return result;
    }

    public void copyToPowerContainer(PowerContainer container) {
        container.setTechLevel(getTechLevel());
        container.setMaxPower(getMaxPower());
        container.setCurrentPower(getCurrentPower());
    }

    public PowerContainer toContainer() {
        PowerContainer container = new PowerContainer();
        copyToPowerContainer(container);
        return container;
    }

    public boolean hasPowerData() {
        return getPowerTagCompound() != null && !getPowerTagCompound().hasNoTags();
    }

    @SuppressWarnings("unchecked")
    public void addInformationToTooltip(List tooltip) {
        if (!hasPowerData()) return;
        String value = getTechLevel().getTooltipEnum() + "Power: "
                       + EnumChatFormatting.RESET + getCurrentPower() + "/" + getMaxPower() + " OP";
        tooltip.add(value);
    }
}
