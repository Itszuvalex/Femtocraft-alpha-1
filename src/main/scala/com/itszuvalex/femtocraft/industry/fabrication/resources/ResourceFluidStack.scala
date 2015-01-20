package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource
import net.minecraftforge.fluids.FluidStack

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourceFluidStack(private val fluidStack: FluidStack) extends IResource[FluidStack, Int] {

  override def resourceName = if (resource == null) "Fluid" else resource.getLocalizedName

  override def contains(other: IResource[FluidStack, Int]) =
    if (equivalent(other))
      amount >= other.amount
    else
      false

  override def utilize(other: IResource[FluidStack, Int]) = if (equivalent(other)) utilize(other.amount) else utilize(0)

  override def equivalent(other: IResource[FluidStack, Int]): Boolean = {
    if (other.resource == null && resource != null) false
    else if (resource == null && other.resource != null) false
    else resource.isFluidEqual(other.resource)

  }

  override def resource = fluidStack

  override def utilize(other: Int) = {
    val drain = Math.max(other, amount)
    resource.amount -= drain
    new ResourceFluidStack(new FluidStack(fluidStack.getFluid, drain))
  }

  override def amount = resource.amount

}
