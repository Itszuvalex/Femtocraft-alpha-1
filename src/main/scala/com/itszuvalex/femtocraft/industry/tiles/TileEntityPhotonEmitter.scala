package com.itszuvalex.femtocraft.industry.tiles

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.industry.IPhotonEmitterReflectionChamber
import com.itszuvalex.femtocraft.api.items.IInterfaceDevice
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.industry.LaserRegistry
import com.itszuvalex.femtocraft.industry.tiles.TileEntityPhotonEmitter._
import com.itszuvalex.femtocraft.power.traits.PowerConsumer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 2/28/15.
 */
object TileEntityPhotonEmitter {
  @Configurable val POWER_STORAGE            = 20000
  @Configurable val POWER_TIER               = EnumTechLevel.NANO
  @Configurable val POWER_PER_TICK           = 20
  @Configurable val DEFAULT_STRENGTH         = 100
  @Configurable val DEFAULT_DISTANCE         = 8
  @Configurable val MAX_CHAMBERS             = 6
  @Configurable val MINIMUM_ACTIVATION_POWER = 2000
  @Configurable val INTERACTION_TIER         = EnumTechLevel.NANO
}

@Configurable class TileEntityPhotonEmitter extends TileEntityBase with PowerConsumer {
  @Saveable(desc = true) var emitterDirection  : Int = 0
  @Saveable(desc = true) var extensionDirection: Int = 0
  @Saveable              var active                  = false

  def getEmissionSide: Int = emitterDirection

  def getExtensionSide: Int = extensionDirection

  /**
   * Gated update call. This will only be called on the server, and only if the tile's {@link #shouldTick()} returns
   * true. This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to
   * update.
   */
  override def femtocraftServerUpdate(): Unit = {
    if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) return
    val emitDir = ForgeDirection.getOrientation(emitterDirection)
    if (emitDir == ForgeDirection.UNKNOWN) return
    if (!active && getCurrentPower < MINIMUM_ACTIVATION_POWER) return

    var power, strength, distance: Int = 0
    var xOffset, yOffset, zOffset: Int = 0
    val extendDir = ForgeDirection.getOrientation(extensionDirection)
    if (extendDir != ForgeDirection.UNKNOWN)
      (0 until MAX_CHAMBERS).forall { i =>
        xOffset += extendDir.offsetX
        yOffset += extendDir.offsetY
        zOffset += extendDir.offsetZ
        worldObj.getTileEntity(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset) match {
          case chamber: IPhotonEmitterReflectionChamber =>
            power += chamber.getPowerPerTick
            strength += chamber.getStrengthBonus
            distance += chamber.getDistanceBonus
            true
          case _ => false
        }
                                    }

    if (consume(POWER_PER_TICK + power)) {
      active = true
      LaserRegistry.makeLaser(worldObj, xCoord + emitDir.offsetX, yCoord + emitDir.offsetY, zCoord + emitDir.offsetZ,
                              LaserRegistry.MODULATION_DEFAULT, emitDir, DEFAULT_STRENGTH + strength, DEFAULT_DISTANCE + distance)
    }
    else active = false
  }

  override def handleDescriptionNBT(compound: NBTTagCompound): Unit = {
    super.handleDescriptionNBT(compound)
    setRenderUpdate()
  }

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    val itemstack = par5EntityPlayer.getCurrentEquippedItem
    if (itemstack == null) return false
    if (worldObj.isRemote) return true
    itemstack.getItem match {
      case inter: IInterfaceDevice if inter.getInterfaceLevel.tier >= INTERACTION_TIER.tier =>
        if (par5EntityPlayer.isSneaking) {
          extensionDirection = ForgeDirection.getOrientation(side).ordinal()
          if (extensionDirection == emitterDirection) emitterDirection = ForgeDirection.UNKNOWN.ordinal()
        } else {
          emitterDirection = ForgeDirection.getOrientation(side).ordinal()
          if (extensionDirection == emitterDirection) extensionDirection = ForgeDirection.UNKNOWN.ordinal()
        }
        setUpdate()
        setModified()
        true
      case _ => false
    }
  }

  override def defaultContainer = new PowerContainer(POWER_TIER, POWER_STORAGE)
}
