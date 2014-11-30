package com.itszuvalex.femtocraft.core.traits.tile

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids._

/**
 * Created by Chris on 11/30/2014.
 */
trait MassTank extends TileEntityBase with IFluidHandler {
  @Saveable val massTank = defaultTank

  def defaultTank: FluidTank

  override def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean) = massTank.fill(resource, doFill)

  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = {
    if (resource == null || !resource.isFluidEqual(massTank.getFluid)) null
    else massTank.drain(resource.amount, doDrain)
  }

  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean) = massTank.drain(maxDrain, doDrain)

  override def canFill(from: ForgeDirection, fluid: Fluid): Boolean = fluid == Femtocraft.fluidMass

  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = fluid == Femtocraft.fluidMass

  override def getTankInfo(from: ForgeDirection): Array[FluidTankInfo] = Array(massTank.getInfo)
}
