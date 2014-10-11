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

import com.itszuvalex.femtocraft.api.power.IAtmosphericChargingAddon;
import com.itszuvalex.femtocraft.api.power.IAtmosphericChargingBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityAtmosphericChargingBase extends TileEntityPowerProducer {
    public int numCoils;
    public float powerPerTick;
    public
    @FemtocraftDataUtils.Saveable
    float storedPowerIncrement;

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return EnumTechLevel.MICRO;
    }

    @Override
    public int getMaxPower() {
        return 250;
    }

    public TileEntityAtmosphericChargingBase() {
        super();
        powerPerTick = 0;
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();

        numCoils = 0;
        powerPerTick = 0;

        Block base = Block.blocksList[worldObj.getBlockId(xCoord, yCoord,
                zCoord)];

        boolean searching = true;
        for (int i = 0; searching
                        && (i < ((IAtmosphericChargingBase) base).maxAddonsSupported(worldObj,
                xCoord, yCoord, zCoord)); ++i) {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord,
                    yCoord + i + 1, zCoord)];

            if ((!(block instanceof IAtmosphericChargingAddon))) {
                searching = false;
                continue;
            }

            IAtmosphericChargingAddon addon = (IAtmosphericChargingAddon) block;
            powerPerTick += addon.powerPerTick(worldObj, xCoord, yCoord + i
                                                                 + 1, zCoord);
            numCoils++;
        }

        storedPowerIncrement += powerPerTick;
        while (storedPowerIncrement > 1.0f) {
            storedPowerIncrement -= 1.0f;
            charge(ForgeDirection.UNKNOWN, 1);
        }
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        return !(from == ForgeDirection.UP);
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (!canPlayerUse(par5EntityPlayer)) {
            return false;
        }

        ItemStack item = par5EntityPlayer.getHeldItem();
        if (item != null
            && (item.getItem() instanceof ItemBlock)
            && Block.blocksList[((ItemBlock) item.getItem()).getBlockID()] instanceof IAtmosphericChargingAddon) {
            return true;
        }

        return super.onSideActivate(par5EntityPlayer, side);
    }

}
