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
package com.itszuvalex.femtocraft.power.tiles

import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.{Inventory, MultiBlockComponent}
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler
import com.itszuvalex.femtocraft.network.messages.MessageFissionReactorCore
import com.itszuvalex.femtocraft.power.FissionReactorRegistry
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockNanoFissionReactor
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore._
import com.itszuvalex.femtocraft.api.utils.BaseInventory
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids._
import org.apache.logging.log4j.Level

object TileEntityNanoFissionReactorCore {
  val incrementAction                          : Byte   = 0
  val decrementAction                          : Byte   = 1
  val abortAction                              : Byte   = 2
  val heatSlot                                          = 0
  val thoriumSlot                                       = 2
  val saltSlot                                          = 1
  //***********************************************************
  @Configurable(comment = "Amount of cooled salt converted to molten salt per tick.")
  val cooledSaltConversionPerTick              : Int    = 100
  @Configurable(comment = "Contaminated salt converts to cooled salt at this ratio.")
  val contaminatedSaltLossRatio                : Float  = .7f
  @Configurable(comment = "Contamianted salt consumes thorium at this ratio.")
  val contaminatedThoriumLossRatio             : Float  = .3f
  @Configurable(comment = "When the plus or minus button is hit, increment thorium concentration target by this amount.")
  val thoriumConcentrationTargetIncrementAmount: Float  = .01f
  @Configurable(comment = "Minimum thorium concentration before salt is melted.")
  val minimumThoriumConcentrationToMeltSalt    : Float  = .01f
  @Configurable(comment = "Minimum heat required before the reactor melts anything.")
  val minimumHeat                              : Int    = 100
  @Configurable(comment = "Heat required per mB of cooled salt to convert it to molten salt.")
  val cooledSaltConversionHeatRatio            : Double = 1D
  @Configurable(comment = "Amount of heat generated per mB of cooled salt per tick.")
  val cooledSaltHeatMultiplier                 : Double = .001
  @Configurable(comment = "Amount of heat generated per mB of molten salt per tick.")
  val moltenSaltHeatMultiplier                 : Double = .002
  @Configurable(comment = "% of maximum heat that represents the minimum of the UNSTABLE threshold.")
  val unstableTemperatureThreshold             : Double = .66
  @Configurable(comment = "% of maximum heat that represents the minimum of the CRITICAL threshold.")
  val criticalTemperatureThreshold             : Double = .83
  @Configurable(comment = "Amount of solid salt needed per thorium")
  val solidSaltToThoriumRatio                  : Double = .2
  @Configurable(comment = "Heat is multiplied by this amount every tick.")
  val enviroHeatLossMultiplier                 : Double = .99
  @Configurable(comment = "Tank size for cooled salt.")
  val cooledSaltTankMaxAmount                  : Int    = 100000
  @Configurable(comment = "Tank size for molten salt.")
  val moltenSaltTankMaxAmount                  : Int    = 100000
  @Configurable(comment = "Amount of stored thorium maximum.")
  val thoriumStoreMax                          : Int    = 100000
  @Configurable(comment = "Maximum temperature.")
  val temperatureMaxAmount                     : Int    = 3000

  object ReactorState extends Enumeration {
    type ReactorState = Value
    val INACTIVE, ACTIVE, UNSTABLE, CRITICAL = Value
  }

}

@Configurable class TileEntityNanoFissionReactorCore
  extends TileEntityBase with Inventory with IFluidHandler with MultiBlockComponent {
  @Saveable private val cooledSaltTank             = new FluidTank(cooledSaltTankMaxAmount)
  @Saveable private val moltenSaltTank             = new FluidTank(moltenSaltTankMaxAmount)
  @Saveable private var thoriumStoreCurrent        = 0
  private           var temperatureMax             = temperatureMaxAmount
  @Saveable private var temperatureCurrent         = 0f
  @Saveable private var thoriumConcentrationTarget = 0f

  override def defaultInventory: BaseInventory = new BaseInventory(3)

  def getState = {
    val temp = getTemperatureCurrent
    val max = getTemperatureMax
    temp match {
      case _ if temp <= minimumHeat                       => ReactorState.INACTIVE
      case _ if temp < max * unstableTemperatureThreshold => ReactorState.ACTIVE
      case _ if temp < max * criticalTemperatureThreshold => ReactorState.UNSTABLE
      case _                                              => ReactorState.CRITICAL
    }
  }

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    if (isValidMultiBlock) {
      val te: TileEntity = worldObj.getTileEntity(info.x, info.y, info.z)
      if (te == null) {
        return false
      }
      par5EntityPlayer.openGui(getMod, getGuiID, worldObj, info.x, info.y, info.z)
      return true
    }
    false
  }

  override def getGuiID = FemtocraftGuiConstants.NanoFissionReactorGuiID

  override def femtocraftServerUpdate() {
    super.femtocraftServerUpdate()
    loseHeat()
    gainHeat()
    meltThorium()
    meltSalt()
    meltWorld()
  }

  private def meltWorld() {
    if (temperatureCurrent > temperatureMax) {
    }
    else if (temperatureCurrent > temperatureMax * criticalTemperatureThreshold) {
    }
    else if (temperatureCurrent > temperatureMax * unstableTemperatureThreshold) {
    }
  }

  private def meltThorium() {
    if (getTemperatureCurrent < minimumHeat) {
      return
    }
    if (getThoriumConcentration < getThoriumConcentrationTarget) {
      val item: ItemStack = getStackInSlot(thoriumSlot)
      if (item == null) {
        return
      }
      val reagent: FissionReactorRegistry.FissionReactorReagent = FissionReactorRegistry.getThoriumSource(item)
      if (reagent == null) {
        return
      }
      if (reagent.item.stackSize <= item.stackSize && getTemperatureCurrent >= reagent
                                                                               .temp && getTemperatureCurrent > minimumHeat && (thoriumStoreMax - thoriumStoreCurrent) >= reagent
                                                                                                                                                                          .amount) {
        decrStackSize(thoriumSlot, reagent.item.stackSize)
        setTemperatureCurrent(getTemperatureCurrent - reagent.temp)
        thoriumStoreCurrent += reagent.amount
        setModified()
      }
    }
  }

  def getThoriumConcentrationTarget = thoriumConcentrationTarget

  private def meltSalt() {
    if (getThoriumConcentration < minimumThoriumConcentrationToMeltSalt) {
      return
    }
    if (getTemperatureCurrent < minimumHeat) {
      return
    }
    var saltAmount: Int = Math.min(getCooledSaltAmount, cooledSaltConversionPerTick)
    if (saltAmount > 0) {
      saltAmount = Math.min(saltAmount, moltenSaltTank.getCapacity - getMoltenSaltAmount)
      val heatAmount: Int = (saltAmount * cooledSaltConversionHeatRatio).toInt
      saltAmount = Math.min(heatAmount, getTemperatureCurrent / cooledSaltConversionHeatRatio).toInt
      if (saltAmount > 0) {
        cooledSaltTank.drain(saltAmount, true)
        setTemperatureCurrent((getTemperatureCurrent - saltAmount * cooledSaltConversionHeatRatio).toFloat)
        addMoltenSalt(saltAmount)
        setModified()
      }
    }
    else {
      val item: ItemStack = getStackInSlot(saltSlot)
      if (item == null) {
        return
      }
      val reagent: FissionReactorRegistry.FissionReactorReagent = FissionReactorRegistry.getSaltSource(item)
      if (reagent != null) {
        if (reagent.item.stackSize <= item.stackSize && getTemperatureCurrent >= reagent.temp && (moltenSaltTank
                                                                                                  .getCapacity - getMoltenSaltAmount) >= reagent
                                                                                                                                         .amount && thoriumStoreCurrent >= (reagent
                                                                                                                                                                            .amount * solidSaltToThoriumRatio)) {
          decrStackSize(saltSlot, reagent.item.stackSize)
          setTemperatureCurrent(getTemperatureCurrent - reagent.temp)
          addMoltenSalt(reagent.amount)
          thoriumStoreCurrent -= (reagent.amount * solidSaltToThoriumRatio).toInt
          setModified()
        }
      }
    }
  }

  def addMoltenSalt(amount: Int): Int = {
    val ret = moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt, amount), true)
    if (ret > 0) {
      setModified()
    }
    ret
  }

  def getThoriumConcentration = thoriumStoreCurrent.toFloat / thoriumStoreMax.toFloat

  def getCooledSaltAmount = cooledSaltTank.getFluidAmount

  def getMoltenSaltAmount = moltenSaltTank.getFluidAmount

  private def gainHeat() {
    setTemperatureCurrent((getTemperatureCurrent + (getCooledSaltAmount
                                                    .toFloat * cooledSaltHeatMultiplier * getThoriumConcentration))
                          .toFloat)
    setTemperatureCurrent((getTemperatureCurrent + (getMoltenSaltAmount
                                                    .toFloat * moltenSaltHeatMultiplier * getThoriumConcentration))
                          .toFloat)
    val heatItem: ItemStack = inventory.getStackInSlot(heatSlot)
    if (heatItem != null) {
      val result: FissionReactorRegistry.FissionReactorReagent = FissionReactorRegistry.getHeatSource(heatItem)
      if (result != null) {
        if (result.item.stackSize <= heatItem.stackSize && ((result
                                                             .temp > 0 && (getTemperatureMax - getTemperatureCurrent) >= result
                                                                                                                         .temp) || (result
                                                                                                                                    .temp < 0 && Math
                                                                                                                                                 .abs(result
                                                                                                                                                      .temp) <= getTemperatureCurrent))) {
          decrStackSize(heatSlot, result.item.stackSize)
          setTemperatureCurrent(getTemperatureCurrent + result.temp)
          setModified()
        }
      }
    }
  }

  def getTemperatureMax = temperatureMax

  def setTemperatureMax(temperatureMax: Int) {
    this.temperatureMax = temperatureMax
  }

  private def loseHeat() {
    setTemperatureCurrent((getTemperatureCurrent * enviroHeatLossMultiplier).toFloat)
  }

  def getTemperatureCurrent = temperatureCurrent

  def setTemperatureCurrent(temperatureCurrent: Float) {
    this.temperatureCurrent = temperatureCurrent
  }

  override def hasGUI = isValidMultiBlock

  def addCooledSalt(amount: Int): Int = {
    val ret: Int = cooledSaltTank.fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt, amount), true)
    if (ret > 0) {
      setModified()
    }
    ret
  }

  def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean): Int = {
    var fill: FluidStack = null
    if (resource.getFluid eq Femtocraft.fluidCooledContaminatedMoltenSalt) {
      var amount: Int = resource.amount
      amount = Math.min(amount, Math.min(Math.max(getThoriumStoreCurrent - minimumThoriumConcentrationToMeltSalt, 0),
                                         resource.amount * contaminatedThoriumLossRatio) / contaminatedThoriumLossRatio)
               .toInt
      amount = Math.min(amount, (moltenSaltTank.getCapacity - getMoltenSaltAmount) / contaminatedSaltLossRatio).toInt
      fill = new FluidStack(Femtocraft.fluidCooledMoltenSalt, amount)
      if (doFill) {
        thoriumStoreCurrent -= (amount * contaminatedThoriumLossRatio).toInt
      }
    }
    else if (resource.getFluid eq Femtocraft.fluidCooledMoltenSalt) {
      fill = resource
    }
    else {
      return 0
    }
    val result: Int = cooledSaltTank.fill(fill, doFill)
    if (result > 0) {
      setModified()
    }
    result
  }

  def getThoriumStoreCurrent = thoriumStoreCurrent

  def setThoriumStoreCurrent(thoriumStoreCurrent: Int) {
    this.thoriumStoreCurrent = thoriumStoreCurrent
  }

  def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = {
    if (resource.getFluid ne Femtocraft.fluidMoltenSalt) return null
    drain(from, resource.amount, doDrain)
  }

  def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = {
    val result: FluidStack = moltenSaltTank.drain(maxDrain, doDrain)
    if (result != null && result.amount > 0) {
      setModified()
    }
    result
  }

  def canFill(from: ForgeDirection, fluid: Fluid) = fluid == Femtocraft
                                                             .fluidCooledContaminatedMoltenSalt || fluid == Femtocraft
                                                                                                            .fluidCooledMoltenSalt

  def canDrain(from: ForgeDirection, fluid: Fluid) = fluid == Femtocraft.fluidMoltenSalt

  def getTankInfo(from: ForgeDirection): Array[FluidTankInfo] = Array[FluidTankInfo](cooledSaltTank.getInfo,
                                                                                     moltenSaltTank.getInfo)

  override def markDirty() {
    MultiBlockNanoFissionReactor.onMultiblockInventoryChanged(worldObj, info.x, info.y, info.z)
    super.markDirty()
  }

  override def isItemValidForSlot(i: Int, itemstack: ItemStack): Boolean = i match {
    case `heatSlot`    => FissionReactorRegistry.getHeatSource(itemstack) != null
    case `saltSlot`    => FissionReactorRegistry.getSaltSource(itemstack) != null
    case `thoriumSlot` => FissionReactorRegistry.getThoriumSource(itemstack) != null
    case _             => false
  }

  def setCooledMoltenSalt(cooledMoltenSalt: Int) {
    if (cooledSaltTank.getFluid == null) {
      cooledSaltTank.fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt, cooledMoltenSalt), true)
    }
    else {
      cooledSaltTank.getFluid.amount = cooledMoltenSalt
    }
  }

  def setMoltenSalt(moltenSalt: Int) {
    if (moltenSaltTank.getFluid == null) {
      moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt, moltenSalt), true)
    }
    else {
      moltenSaltTank.getFluid.amount = moltenSalt
    }
  }

  def getCooledSaltTank = cooledSaltTank

  def getMoltenSaltTank = moltenSaltTank

  def onIncrementClick() {
    onClick(incrementAction)
  }

  def onDecrementClick() {
    onClick(decrementAction)
  }

  def onAbortClick() {
    onClick(abortAction)
  }

  private def onClick(action: Byte) {
    FemtocraftPacketHandler.INSTANCE
    .sendToServer(new MessageFissionReactorCore(xCoord, yCoord, zCoord, worldObj.provider.dimensionId, action))
  }

  def handleAction(action: Byte) {
    action match {
      case `incrementAction` => incrementThoriumConcentrationTarget()
      case `decrementAction` => decrementThoriumConcentrationTarget()
      case `abortAction`     => abortReaction()
      case _                 => Femtocraft.log(Level.ERROR,
                                               "Received invalid action for Fusion Reactor at x-" + xCoord + " y-" + yCoord + " z-" + zCoord + " at dimension-" + worldObj
                                                                                                                                                                  .provider
                                                                                                                                                                  .dimensionId + ".")
    }
  }

  def incrementThoriumConcentrationTarget() {
    setThoriumConcentrationTarget(getThoriumConcentrationTarget + thoriumConcentrationTargetIncrementAmount)
  }

  def setThoriumConcentrationTarget(thoriumConcentrationTarget: Float) {
    this.thoriumConcentrationTarget = Math.min(Math.max(thoriumConcentrationTarget, 0f), 1f)
    setModified()
  }

  def decrementThoriumConcentrationTarget() {
    setThoriumConcentrationTarget(getThoriumConcentrationTarget - thoriumConcentrationTargetIncrementAmount)
  }

  def abortReaction() {
    thoriumStoreCurrent = 0
    cooledSaltTank.setFluid(null)
    moltenSaltTank.setFluid(null)
    setUpdate()
    setModified()
  }
}
