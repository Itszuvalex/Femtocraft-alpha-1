package com.itszuvalex.femtocraft.power.traits

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.power.{IPowerTileContainer, PowerNBTWrapper, PowerContainer}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.FemtocraftPowerAlgorithm
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 10/9/14.
 */
trait PowerTileContainer extends TileEntityBase with IPowerTileContainer {
  @Saveable
  val container: PowerContainer = defaultContainer

  @Saveable
  val connections = Array.fill[Boolean](6)(false)


  def isConnected(i: Int) = connections(i)

  def numConnections = connections.count(b => b)


  override def updateEntity() {
    checkConnections()
    super.updateEntity()
  }

  def checkConnections() {
    var changed = false
    ForgeDirection.VALID_DIRECTIONS.foreach(offset => {
      val prev = connections(offset.ordinal())
      connections(offset.ordinal()) = false
      val locx = this.xCoord + offset.offsetX
      val locy = this.yCoord + offset.offsetY
      val locz = this.zCoord + offset.offsetZ
      val checkTile = getWorldObj.getTileEntity(locx, locy, locz)
      checkTile match {
        case fc: IPowerTileContainer =>
          if (fc.canConnect(offset.getOpposite) && fc
                                                   .canAcceptPowerOfLevel(getTechLevel(offset.getOpposite),
                                                                          offset.getOpposite)) {
            connections(offset.ordinal()) = true
            if (prev != connections(offset.ordinal())) {
              changed = true
            }
          }
        case _                       =>
      }
    })
    if (changed) {
      setModified()
      setRenderUpdate()
    }
  }

  override def getTechLevel(to: ForgeDirection): EnumTechLevel = container.getTechLevel

  override def femtocraftServerUpdate(): Unit = {
    //TODO: Smart connections based on updates.
    FemtocraftPowerAlgorithm.distributePower(this, connections, getWorldObj, xCoord, yCoord, zCoord)
    super.femtocraftServerUpdate()
  }

  override def getMaxPower: Int = container.getMaxPower

  def defaultContainer: PowerContainer

  def setMaxStorage(maxStorage: Int) = {
    container.setMaxPower(maxStorage)
    setModified()
  }

  def setCurrentStorage(currentStorage: Int) = {
    container.setCurrentPower(currentStorage)
    setModified()
  }

  def setTechLevel(level: EnumTechLevel) = {
    container.setTechLevel(level)
    setModified()
  }

  override def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection) = container.getTechLevel equals level

  override def consume(amount: Int) = {
    setModified()
    container.consume(amount)
  }

  override def getFillPercentageForCharging(from: ForgeDirection) = container.getFillPercentageForCharging

  override def charge(from: ForgeDirection, amount: Int) = {
    setModified()
    container.charge(amount)
  }

  override def getFillPercentageForOutput(to: ForgeDirection) = container.getFillPercentageForOutput

  override def getCurrentPower = container.getCurrentPower

  override def getFillPercentage = container.getFillPercentage

  override def canCharge(from: ForgeDirection) = true

  override def canConnect(from: ForgeDirection) = true

  override def loadInfoFromItemNBT(compound: NBTTagCompound): Unit = {
    super.loadInfoFromItemNBT(compound)
    val wrapper = PowerNBTWrapper.wrapperFromNBT(compound)
    if (wrapper.hasPowerData) wrapper.copyToPowerContainer(container)
  }

  override def saveInfoToItemNBT(compound: NBTTagCompound) {
    super.saveInfoToItemNBT(compound)
    PowerNBTWrapper.wrapperFromNBT(compound).copyFromPowerContainer(container)
  }
}
