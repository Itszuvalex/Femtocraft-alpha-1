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

package com.itszuvalex.femtocraft.transport.liquids.tiles;

import com.itszuvalex.femtocraft.api.items.IInterfaceDevice;
import com.itszuvalex.femtocraft.api.core.Configurable;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.transport.ISuctionPipe;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.Arrays;


public
@Configurable
class TileEntitySuctionPipe extends TileEntityBase implements
        ISuctionPipe {
    static final int renderLength = 20;
    @Configurable
    private final float TRANSFER_RATIO = .1f;
    @Configurable
    private final int CAPACITY = 2000;
    public
    @Saveable
    boolean[] tankconnections;
    public
    @Saveable
    boolean[] pipeconnections;
    public IFluidHandler[] neighbors;
    private
    @Saveable(desc = true)
    FluidTank tank = new FluidTank(CAPACITY);
    private
    @Saveable(desc = true)
    boolean output = true;
    private
    @Saveable
    int pressure = 0;
    private FluidStack renderFluid = null;
    @Saveable(desc = true)
    private FluidStack passthroughFluid = null;
    private
    @Saveable
    int renderTick = 0;
    @Saveable(desc = true)
    private boolean blackout = true;

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraftforge.liquids.ITankContainer#fill(net.minecraftforge.common
     * .ForgeDirection, net.minecraftforge.liquids.LiquidStack, boolean)
     */
    public TileEntitySuctionPipe() {
        super();
        tankconnections = new boolean[6];
        pipeconnections = new boolean[6];
        neighbors = new IFluidHandler[6];
        Arrays.fill(tankconnections, false);
        Arrays.fill(pipeconnections, false);
        Arrays.fill(neighbors, null);
    }

    /**
     * @return True if this is an 'output' pipe. This means it will automatically attempt to output liquid into
     * neighboring IFluidHandlers. If it is an input pipe, it will attempt to automatically pull liquid from neighboring
     * IFluidHandlers. In either case, it will distribute liquids to neighboring ISuctionPipe implementors that have
     * less pressure.
     */
    public boolean isOutput() {
        return output;
    }

    public FluidStack getRenderFluid() {
        return renderFluid;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int amount = tank.fill(resource, doFill);
        setModified();
        return amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }
        FluidStack stack = tank.drain(resource.amount, doDrain);
        setModified();
        return stack;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack stack = tank.drain(maxDrain, doDrain);
        setModified();
        return stack;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    private void calculatePressure(int[] pressures, IFluidHandler[] neighbors) {
        int totalPressure = 0;
        int pipeCount = 0;
        for (int i = 0; i < 6; ++i) {
            if (neighbors[i] == null) {
                continue;
            }

            // If connected to a tank
            // If outputting, negative pressure equal to remaining space
            // If inputting, positive pressure equal to amount of fluid in the
            // tank
            if (tankconnections[i]) {
                FluidTankInfo[] infoArray = neighbors[i]
                        .getTankInfo(ForgeDirection.getOrientation(i)
                                .getOpposite());
                ++pipeCount;

                FluidTankInfo info = chooseTank(neighbors[i], ForgeDirection.getOrientation(i).getOpposite(),
                        infoArray, output);
                if (info == null) {
                    continue;
                }
                pressures[i] = getTankPressure(info, output);
                totalPressure += pressures[i];
            }

            // Otherwise, ask pipe for its pressure
            if (pipeconnections[i]) {
                ++pipeCount;
                pressures[i] = ((ISuctionPipe) neighbors[i]).getPressure();
                totalPressure += pressures[i];
            }
        }

        pressure = pipeCount == 0 ? 0 : (totalPressure / pipeCount);
    }

    private void distributeLiquid(int[] pressures, IFluidHandler[] neighbors) {
        int ratioMax = 0;
        int amountToRemove = 0;

        if (tank.getFluid() == null) {
            return;
        }

        updatePassthroughFluid();

        // Sum pressure differences for tanks with less pressure than us
        for (int i = 0; i < 6; ++i) {
            if (neighbors[i] == null) {
                continue;
            }

            ForgeDirection dir = ForgeDirection.getOrientation(i);

            if (tankconnections[i]
                && pressures[i] < pressure
                && neighbors[i].canFill(dir.getOpposite(), tank.getFluid()
                    .getFluid()) && output) {
                ratioMax += pressure - pressures[i];
            }
            if (pipeconnections[i]
                && pressures[i] < pressure
                && neighbors[i].canFill(dir.getOpposite(), tank.getFluid()
                    .getFluid())) {
                ratioMax += pressure - pressures[i];
            }
        }

        // TODO: get it to transmit miniscule amounts
        int amount = (int) Math.min(tank.getFluidAmount(), tank.getCapacity()
                                                           * TRANSFER_RATIO);

        for (int i = 0; i < 6; ++i) {
            if (neighbors[i] == null) {
                continue;
            }

            if (tankconnections[i] && !output) {
                continue;
            }

            if (pressures[i] < pressure
                && neighbors[i].canFill(ForgeDirection.getOrientation(i)
                    .getOpposite(), tank.getFluid().getFluid())) {

                ForgeDirection dir = ForgeDirection.getOrientation(i);

                int rationedAmount = (int) ((float) amount * ((float) (pressure - pressures[i]) / (float) ratioMax));
                amountToRemove += neighbors[i].fill(dir.getOpposite(),
                        new FluidStack(tank.getFluid().getFluid(),
                                rationedAmount), true
                );
            }
        }

        tank.drain(amountToRemove, true);
        if (tank.getFluid() == null) {
            setUpdate();
        }
        setModified();
    }

    private void requestLiquid(int[] pressures, IFluidHandler[] neighbors) {
        int ratioMax = 0;
        int space = tank.getCapacity() - tank.getFluidAmount();

        for (int i = 0; i < 6; ++i) {
            if (tankconnections[i]
                && pressure < pressures[i]
                && (tank.getFluid() == null || neighbors[i].canDrain(
                    ForgeDirection.getOrientation(i).getOpposite(),
                    tank.getFluid().getFluid()))) {
                ratioMax += pressures[i] - pressure;
            }
        }

        for (int i = 0; i < 6; ++i) {
            if (!(tankconnections[i] && pressure < pressures[i] && (tank
                                                                            .getFluid() == null ||
                                                                    neighbors[i].canDrain(ForgeDirection
                                                                                    .getOrientation(i).getOpposite(),
                                                                            tank.getFluid()
                                                                                    .getFluid())))) {
                continue;
            }
            FluidTankInfo[] infoArray = neighbors[i].getTankInfo(ForgeDirection
                    .getOrientation(i).getOpposite());

            FluidTankInfo info = chooseTank(neighbors[i], ForgeDirection.getOrientation(i).getOpposite(), infoArray,
                    output);
            if (info == null) {
                continue;
            }

            int amount = (int) (info.fluid == null ? 0 : info.fluid.amount);

            int rationedAmount = (int) (space * ((float) (pressures[i] - pressure) / (float) ratioMax));

            rationedAmount = rationedAmount > amount ? amount : rationedAmount;

            if (tank.getFluid() == null) {
                tank.fill(neighbors[i].drain(ForgeDirection.getOrientation(i)
                        .getOpposite(), rationedAmount, true), true);
            } else {
                tank.fill(neighbors[i].drain(ForgeDirection.getOrientation(i)
                        .getOpposite(), new FluidStack(tank.getFluid()
                        .getFluid(), rationedAmount), true), true);
            }
        }
        updatePassthroughFluid();
        setUpdate();
        setModified();
    }

    private FluidTankInfo chooseTank(IFluidHandler block, ForgeDirection dir, FluidTankInfo[] infoArray,
                                     boolean output) {
        if (infoArray.length == 0) {
            return null;
        }

        return output ? chooseOutputTank(block, dir, infoArray) : chooseInputTank(block, dir, infoArray);
    }

    private FluidTankInfo chooseInputTank(IFluidHandler block, ForgeDirection dir, FluidTankInfo[] infoArray) {
        FluidTankInfo emptyTank = null;
        for (FluidTankInfo info : infoArray) {
            if (info.fluid == null && emptyTank == null) {
                emptyTank = info;
            }

            if (info.fluid != null &&
                (tank.getFluid() == null ||
                 (info.fluid.isFluidEqual(tank.getFluid()) && block.canDrain(dir, tank.getFluid().getFluid())))) {
                return info;
            }
        }
        return emptyTank;
    }

    private FluidTankInfo chooseOutputTank(IFluidHandler block, ForgeDirection dir, FluidTankInfo[] infoArray) {
        FluidTankInfo emptyTank = null;
        for (FluidTankInfo info : infoArray) {
            if (info.fluid == null && emptyTank == null) {
                emptyTank = info;
            }

            if (info.fluid != null &&
                (tank.getFluid() == null ||
                 (block.canFill(dir, tank.getFluid().getFluid())))) {
                return info;
            }
        }
        return emptyTank;
    }

    /**
     * @param tank
     * @param output
     * @return Negative if outputting into tank, equal to space remaining in tank. If input, positive pressure equal to
     * amount of fluid remaining in tank.
     */
    private int getTankPressure(FluidTankInfo tank, boolean output) {
        int amount = tank.fluid == null ? 0 : tank.fluid.amount;
        return output ? (-(tank.capacity - amount) - 10) : amount + 10;
    }

    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        if (passthroughFluid != null) {
            renderFluid = passthroughFluid;
        }
        setRenderUpdate();
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (!canPlayerUse(par5EntityPlayer)) {
            return false;
        }

        par5EntityPlayer.addChatMessage(new ChatComponentText((worldObj.isRemote ? "Client:"
                : "Server:")
                                                              + " Pressure = "
                                                              + pressure
                                                              + "; Amount = "
                                                              + tank.getFluidAmount()));
        if (!worldObj.isRemote) {
            ItemStack item = par5EntityPlayer.getHeldItem();
            if (item != null && item.getItem() instanceof IInterfaceDevice) {
                if (par5EntityPlayer.isSneaking()) {
                    blackout = !blackout;
                    super.setUpdate();
                    return true;
                } else {
                    output = !output;
                    super.setUpdate();
                    return true;
                }
            }
        }
        return super.onSideActivate(par5EntityPlayer, side);
    }

    @Override
    public void setUpdate() {
        if (!blackout) super.setUpdate();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();

//        boolean pre = renderFluid != null && renderFluid.amount > 0;
//        renderFluid = tank.getFluid();

        int[] pressures = new int[6];
        Arrays.fill(pressures, 0);
        boolean prev = passthroughFluid != null;
        passthroughFluid = null;

        calculatePressure(pressures, neighbors);
        distributeLiquid(pressures, neighbors);
        if (!output) {
            requestLiquid(pressures, neighbors);
//            if (tank.getFluid() != null) renderFluid = tank.getFluid();
        }

//        boolean post = renderFluid != null && renderFluid.amount > 0;

        // Pass description packet to clients - fluid has either emptied, or
        // filled
//        if (!blackout && (pre != post)) {
//            setUpdate();
//        }
        if (prev != (passthroughFluid != null)) setUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        updatePassthroughFluid();
    }

    @Override
    public void updateEntity() {
        Arrays.fill(neighbors, null);
        checkConnections(neighbors);

        if (worldObj.isRemote) {
            if (renderFluid != null && passthroughFluid == null) {
                if (renderTick >= renderLength) {
                    renderFluid = null;
                    setRenderUpdate();
                    renderTick = 0;
                } else {
                    renderTick++;
                }
            } else {
                renderTick = 0;
            }
        }

        super.updateEntity();
    }

    private void checkConnections(IFluidHandler[] neighbors) {
        boolean[] oldpipes = tankconnections.clone();
        boolean[] oldtanks = pipeconnections.clone();
        boolean changed = false;

        Arrays.fill(tankconnections, false);
        Arrays.fill(pipeconnections, false);

        for (int i = 0; i < 6; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);

            int locx = this.xCoord + dir.offsetX;
            int locy = this.yCoord + dir.offsetY;
            int locz = this.zCoord + dir.offsetZ;

            TileEntity checkTile = this.worldObj.getTileEntity(locx, locy,
                    locz);

            if (checkTile != null) {
                if (checkTile instanceof ISuctionPipe) {
                    pipeconnections[i] = true;
                    if (!oldpipes[i]) {
                        changed = true;
                    }
                    neighbors[i] = (IFluidHandler) checkTile;
                } else if (checkTile instanceof IFluidHandler) {
                    tankconnections[i] = true;
                    if (!oldtanks[i]) {
                        changed = true;
                    }
                    neighbors[i] = (IFluidHandler) checkTile;
                }

                if (oldpipes[i] || oldtanks[i]) {
                    changed = true;
                }
            }
        }

        if (changed) {
            setRenderUpdate();
        }
    }

    private void updatePassthroughFluid() {
        if (passthroughFluid == null && tank.getFluid() != null) {
            passthroughFluid = tank.getFluid();
        } else if (tank.getFluid() != null && !passthroughFluid.isFluidEqual(tank.getFluid())) {
            passthroughFluid = tank.getFluid();
        }
    }

    @Override
    public int getPressure() {
        return pressure;
    }

    public boolean isBlackout() {
        return blackout;
    }
}
