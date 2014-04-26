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

package femtocraft.transport.liquids.tiles;

import femtocraft.api.IInterfaceDevice;
import femtocraft.api.ISuctionPipe;
import femtocraft.core.tiles.TileEntityBase;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.Arrays;

public class TileEntitySuctionPipe extends TileEntityBase implements
                                                          ISuctionPipe {
    static final int renderLength = 10;
    private final float TRANSFER_RATIO = .1f;
    public
    @Saveable
    boolean[] tankconnections;
    public
    @Saveable
    boolean[] pipeconnections;
    public IFluidHandler[] neighbors;
    private
    @Saveable
    FluidTank tank;
    private
    @Saveable(desc = true)
    boolean output;
    private
    @Saveable
    int pressure;
    // Not @Saveable due to special rendering requirements
    private FluidStack renderFluid;
    private
    @Saveable
    int renderTick;

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraftforge.liquids.ITankContainer#fill(net.minecraftforge.common
     * .ForgeDirection, net.minecraftforge.liquids.LiquidStack, boolean)
     */
    public TileEntitySuctionPipe() {
        tank = new FluidTank(2000);
        tankconnections = new boolean[6];
        pipeconnections = new boolean[6];
        neighbors = new IFluidHandler[6];
        Arrays.fill(tankconnections, false);
        Arrays.fill(pipeconnections, false);
        Arrays.fill(neighbors, null);
        output = true;
        pressure = 0;
        renderFluid = null;
        renderTick = 0;
    }

    /**
     * @return True if this is an 'output' pipe. This means it will
     * automatically attempt to output liquid into neighboring
     * IFluidHandlers. If it is an input pipe, it will attempt to
     * automatically pull liquid from neighboring IFluidHandlers. In
     * either case, it will distribute liquids to neighboring
     * ISuctionPipe implementors that have less pressure.
     */
    public boolean isOutput() {
        return output;
    }

    public FluidStack getRenderFluid() {
        return renderFluid;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
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

    @Override
    public void updateEntity() {
        Arrays.fill(neighbors, null);
        checkConnections(neighbors);

        if (renderFluid != null && tank.getFluid() == null) {
            if (renderTick++ >= renderLength) {
                renderFluid = null;
                worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            }
        }
        else {
            renderTick = 0;
        }

        super.updateEntity();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();

        FluidStack pre = renderFluid;
        renderFluid = null;

        int[] pressures = new int[6];
        Arrays.fill(pressures, 0);

        calculatePressure(pressures, neighbors);
        distributeLiquid(pressures, neighbors);
        if (!output) {
            requestLiquid(pressures, neighbors);
        }

        FluidStack post = renderFluid;

        // Pass description packet to clients - fluid has either emptied, or
        // filled
        if (pre == null && post == null) {
            return;
        }

        if (((pre == null && post != null) || (pre != null && post == null))
                || ((pre.amount == 0 && post.amount > 0) || (pre.amount > 0 && post.amount == 0))) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
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

            TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy,
                                                                    locz);

            if (checkTile != null) {
                if (checkTile instanceof ISuctionPipe) {
                    pipeconnections[i] = true;
                    if (!oldpipes[i]) {
                        changed = true;
                    }
                    neighbors[i] = (IFluidHandler) checkTile;
                }
                else if (checkTile instanceof IFluidHandler) {
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
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
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

                FluidTankInfo info = chooseTank(infoArray, output);
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

    /**
     * @param tank
     * @param output
     * @return Negative if outputting into tank, equal to space remaining in
     * tank. If input, positive pressure equal to amount of fluid
     * remaining in tank.
     */
    private int getTankPressure(FluidTankInfo tank, boolean output) {
        int amount = tank.fluid == null ? 0 : tank.fluid.amount;
        return output ? (-(tank.capacity - amount) - 10) : amount + 10;
    }

    private void distributeLiquid(int[] pressures, IFluidHandler[] neighbors) {
        int distributeCount = 0;
        int ratioMax = 0;
        int amountToRemove = 0;

        if (tank.getFluid() == null) {
            return;
        }

        renderFluid = tank.getFluid();

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
        int amount = (int) (Math.min(tank.getFluidAmount(), tank.getCapacity()
                * TRANSFER_RATIO));

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

                int rationedAmount = (int) (((float) amount) * (((float) (pressure - pressures[i])) / ((float) ratioMax)));
                amountToRemove += neighbors[i].fill(dir.getOpposite(),
                                                    new FluidStack(tank.getFluid().getFluid(),
                                                                   rationedAmount), true
                );
            }
        }

        tank.drain(amountToRemove, true);
    }

    private void requestLiquid(int[] pressures, IFluidHandler[] neighbors) {
        int ratioMax = 0;
        int amountToAdd = 0;
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
                    .getFluid() == null || neighbors[i].canDrain(ForgeDirection
                                                                         .getOrientation(i).getOpposite(), tank.getFluid()
                                                                                                               .getFluid())))) {
                continue;
            }
            FluidTankInfo[] infoArray = neighbors[i].getTankInfo(ForgeDirection
                                                                         .getOrientation(i).getOpposite());

            FluidTankInfo info = chooseTank(infoArray, output);
            if (info == null) {
                continue;
            }

            int amount = (int) (info.fluid == null ? 0 : (info.fluid.amount));

            int rationedAmount = (int) (space * (((float) (pressures[i] - pressure)) / ((float) ratioMax)));

            rationedAmount = rationedAmount > amount ? amount : rationedAmount;

            if (tank.getFluid() == null) {
                tank.fill(neighbors[i].drain(ForgeDirection.getOrientation(i)
                                                           .getOpposite(), rationedAmount, true), true);
            }
            else {
                tank.fill(neighbors[i].drain(ForgeDirection.getOrientation(i)
                                                           .getOpposite(), new FluidStack(tank.getFluid()
                                                                                              .getFluid(), rationedAmount), true), true);
            }
        }

        if (tank.getFluid() != null) {
            renderFluid = tank.getFluid();
        }
    }

    private FluidTankInfo chooseTank(FluidTankInfo[] infoArray, boolean output) {
        if (infoArray.length == 0) {
            return null;
        }

        for (FluidTankInfo info : infoArray) {
            if (tank.getFluid() == null && output) {
                return info;
            }

            if (info.fluid == null && !output) {
                continue;
            }

            if (info.fluid == null & output) {
                return info;
            }

            if (info.fluid.isFluidEqual(tank.getFluid())) {
                return info;
            }
        }

        return infoArray[0];
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        tank.readFromNBT(par1nbtTagCompound);
        if (tank.getFluid() != null) {
            renderFluid = tank.getFluid();
        }
        output = par1nbtTagCompound.getBoolean("output");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
        tank.writeToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setBoolean("output", output);
    }

    @Override
    public int getPressure() {
        return pressure;
    }

    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);

        // FluidStack fluid = compound.hasKey("fluid") ? FluidStack
        // .loadFluidStackFromNBT(compound.getCompoundTag("fluid")) : null;
        // tank.setFluid(fluid);

        renderFluid = compound.hasKey("renderfluid") ? FluidStack
                .loadFluidStackFromNBT(compound.getCompoundTag("renderfluid"))
                : renderFluid;

        // output = compound.getBoolean("output");
        if (worldObj != null) {
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void saveToDescriptionCompound(NBTTagCompound compound) {
        super.saveToDescriptionCompound(compound);
        // NBTTagCompound fluid = new NBTTagCompound();
        // if (tank.getFluid() != null) {
        // tank.getFluid().writeToNBT(fluid);
        // compound.setTag("fluid", fluid);
        // }

        NBTTagCompound renderfluid = new NBTTagCompound();
        if (renderFluid != null) {
            renderFluid.writeToNBT(renderfluid);
            compound.setTag("renderfluid", renderfluid);
        }

        // compound.setBoolean("output", output);
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (!isUseableByPlayer(par5EntityPlayer)) {
            return false;
        }

        par5EntityPlayer.addChatMessage((worldObj.isRemote ? "Client:"
                : "Server:")
                                                + " Pressure = "
                                                + pressure
                                                + "; Amount = "
                                                + tank.getFluidAmount());

        ItemStack item = par5EntityPlayer.getHeldItem();
        if (item != null && item.getItem() instanceof IInterfaceDevice) {
            output = !output;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return true;
        }
        return super.onSideActivate(par5EntityPlayer, side);
    }
}
