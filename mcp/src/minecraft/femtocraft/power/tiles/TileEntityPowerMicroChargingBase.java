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

package femtocraft.power.tiles;

import femtocraft.api.IChargingBase;
import femtocraft.api.IChargingCoil;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityPowerMicroChargingBase extends TileEntityPowerProducer {
    public int numCoils;
    public float powerPerTick;
    public
    @Saveable
    float storedPowerIncrement;

    public TileEntityPowerMicroChargingBase() {
        super();
        powerPerTick = 0;
        setTechLevel(EnumTechLevel.MICRO);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!worldObj.isRemote) {
            numCoils = 0;
            powerPerTick = 0;

            Block base = Block.blocksList[worldObj.getBlockId(xCoord, yCoord,
                                                              zCoord)];

            boolean searching = true;
            for (int i = 0; searching
                    && (i < ((IChargingBase) base).maxCoilsSupported(worldObj,
                                                                     xCoord, yCoord, zCoord)); ++i) {
                Block block = Block.blocksList[worldObj.getBlockId(xCoord,
                                                                   yCoord + i + 1, zCoord)];

                if ((!(block instanceof IChargingCoil))) {
                    searching = false;
                    continue;
                }

                IChargingCoil coil = (IChargingCoil) block;
                powerPerTick += coil.powerPerTick(worldObj, xCoord, yCoord + i
                        + 1, zCoord);
                numCoils++;
            }

            storedPowerIncrement += powerPerTick;
            while (storedPowerIncrement > 1.0f) {
                storedPowerIncrement -= 1.0f;
                charge(ForgeDirection.UNKNOWN, 1);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        // par1nbtTagCompound.getFloat("powerIncrement");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
        // par1nbtTagCompound.setFloat("powerIncrement", storedPowerIncrement);
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        return !(from == ForgeDirection.UP);
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (!isUseableByPlayer(par5EntityPlayer)) {
            return false;
        }

        ItemStack item = par5EntityPlayer.getHeldItem();
        if (item != null
                && (item.getItem() instanceof ItemBlock)
                && Block.blocksList[((ItemBlock) item.getItem()).getBlockID()] instanceof IChargingCoil) {
            return true;
        }

        return super.onSideActivate(par5EntityPlayer, side);
    }

}
