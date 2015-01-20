package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource
import net.minecraftforge.fluids.FluidStack

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourceFluidStack(private val fluidStack: FluidStack) extends IResource[FluidStack, Int] {
  override def resource = fluidStack

  override def utilize(resource: Int) = {
    val amount = Math.max(resource, amount)
    fluidStack.amount -= amount
    new ResourceFluidStack(new FluidStack(fluidStack.getFluid, amount))
  }

  override def amount = fluidStack.amount

  override def resourceName = if (fluidStack == null) "Fluid" else fluidStack.getLocalizedName
}
