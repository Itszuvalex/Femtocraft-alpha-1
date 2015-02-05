package com.itszuvalex.femtocraft.industry.fabrication.resources

import java.util.UUID

import com.itszuvalex.femtocraft.industry.fabrication.traits._
import net.minecraftforge.fluids.FluidStack

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourceFluid(private val fluidStack: FluidStack) extends IResource[FluidStack, Int] {

  override def resourceName = if (resource == null) "Fluid" else resource.getLocalizedName

  override def contains(other: IResource[FluidStack, Int]) =
    if (equivalent(other))
      amount >= other.amount
    else
      false

  /**
   *
   * @return View of this object as a provided resource.  Null if it isn't one.
   */
  override def asProvided: IProvidedResource[FluidStack, Int] = ???

  /**
   *
   * @return View of this object as a requested resource.  Null if it isn't one.
   */
  override def asRequested: IRequestedResource[FluidStack, Int] = ???

  /**
   *
   * @return View of this object as a renewable resource.  Null if it isn't one.
   */
  override def asRenewable: IRenewableResource[FluidStack, Int] = ???

  /**
   *
   * @return View of this object as a consumable resource.  Null if it isn't one.
   */
  override def asConsumable: IConsumableResource[FluidStack, Int] = ???

  override def equivalent(other: IResource[FluidStack, Int]): Boolean = {
    if (other.resource == null && resource != null) false
    else if (resource == null && other.resource != null) false
    else resource.isFluidEqual(other.resource)

  }

  override def resource = fluidStack

  override def amount = resource.amount

}
