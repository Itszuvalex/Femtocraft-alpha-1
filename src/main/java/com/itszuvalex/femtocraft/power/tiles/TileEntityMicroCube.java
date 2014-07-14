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
import com.itszuvalex.femtocraft.api.IInterfaceDevice;
import com.itszuvalex.femtocraft.api.PowerContainer;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

import java.util.Arrays;

public class TileEntityMicroCube extends TileEntityPowerBase {
    static final public String packetChannel = Femtocraft.ID + ".MCube";
    public static final int maxStorage = 10000;
    public static final EnumTechLevel ENUM_TECH_LEVEL = EnumTechLevel.MICRO;
    public boolean[] outputs = new boolean[6]; // Not @Saveable due to bit masking

    public TileEntityMicroCube() {
        super();
        setMaxStorage(maxStorage);
        setTechLevel(ENUM_TECH_LEVEL);
        Arrays.fill(outputs, false);
        setTechLevel(ENUM_TECH_LEVEL);
    }

    public static PowerContainer getDefaultContainer() {
        return new PowerContainer(ENUM_TECH_LEVEL, maxStorage);
    }

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        return outputs[FemtocraftUtils.indexOfForgeDirection(from)] ? 1.f : 0.f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        return outputs[FemtocraftUtils.indexOfForgeDirection(to)] ? 1.f : 0.f;
    }

    // @Override
    // public String getPacketChannel() {
    // return packetChannel;
    // }

    @Override
    public boolean canCharge(ForgeDirection from) {
        return !outputs[FemtocraftUtils.indexOfForgeDirection(from)]
                && super.canCharge(from);
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        if (!outputs[FemtocraftUtils.indexOfForgeDirection(from)]) {
            return super.charge(from, amount);
        }

        return 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        parseOutputMask(par1nbtTagCompound.getByte("outputs"));
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setByte("outputs", generateOutputMask());
    }

    @Override
    public void saveToDescriptionCompound(NBTTagCompound compound) {
        super.saveToDescriptionCompound(compound);
        compound.setByte("outputs", generateOutputMask());
    }

    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        parseOutputMask(compound.getByte("outputs"));
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {

        ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
        if (item != null && (item.getItem() instanceof IInterfaceDevice)) {
            if (!canPlayerUse(par5EntityPlayer)) {
                return false;
            }

            ForgeDirection dir = ForgeDirection.getOrientation(side);
            if (par5EntityPlayer.isSneaking()) {
                dir = dir.getOpposite();
            }

            int s = FemtocraftUtils.indexOfForgeDirection(dir);
            outputs[s] = !outputs[s];
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

            return true;
        }

        return super.onSideActivate(par5EntityPlayer, side);
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.MicroCubeGuiID;
    }

    public byte generateOutputMask() {
        byte output = 0;

        for (int i = 0; i < 6; i++) {
            if (outputs[i]) {
                output += 1 << i;
            }
        }
        return output;
    }

    public void parseOutputMask(byte mask) {
        byte temp;

        for (int i = 0; i < 6; i++) {
            temp = mask;
            outputs[i] = (((temp >> i) & 1) == 1);
        }

        if (worldObj != null) {
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }

}
