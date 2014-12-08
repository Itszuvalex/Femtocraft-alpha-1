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

package com.itszuvalex.femtocraft.api.power;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.core.ISaveable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class PowerContainer implements IPowerContainer, ISaveable {
    private EnumTechLevel level;
    private int maxPower;
    private int currentPower;

    public PowerContainer(EnumTechLevel level, int maxPower) {
        this.level = level;
        this.maxPower = maxPower;
        this.currentPower = 0;
    }

    public PowerContainer() {
        level = EnumTechLevel.MACRO;
        maxPower = 0;
        currentPower = 0;
    }

    public static PowerContainer createFromNBT(NBTTagCompound nbt) {
        PowerContainer cont = new PowerContainer();
        cont.loadFromNBT(nbt);
        return cont;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level) {
        return this.level == level;
    }

    @Override
    public EnumTechLevel getTechLevel() {
        return level;
    }

    public void setTechLevel(EnumTechLevel level) {
        this.level = level;
    }

    @Override
    public int getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(int currentPower) {
        this.currentPower = currentPower;
    }

    @Override
    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    @Override
    public float getFillPercentage() {
        return ((float) currentPower) / ((float) maxPower);
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
        int room = maxPower - currentPower;
        amount = room < amount ? room : amount;
        currentPower += amount;
        return amount;
    }

    @Override
    public boolean consume(int amount) {
        if (amount > currentPower) {
            return false;
        }

        currentPower -= amount;
        return true;
    }

    @Override
    public void saveToNBT(NBTTagCompound nbt) {
        nbt.setInteger("maxPower", maxPower);
        nbt.setInteger("currentPower", currentPower);
        nbt.setString("enumTechLevel", level.key);
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        maxPower = nbt.getInteger("maxPower");
        currentPower = nbt.getInteger("currentPower");
        level = EnumTechLevel.getTech(nbt.getString("enumTechLevel"));
    }

    @SuppressWarnings("unchecked")
    public void addInformationToTooltip(List tooltip) {
        String value = level.getTooltipEnum() + "Power: "
                       + EnumChatFormatting.RESET + currentPower + "/" + maxPower + " OP";
        tooltip.add(value);
    }

}
