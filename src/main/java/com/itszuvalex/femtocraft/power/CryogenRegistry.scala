package com.itszuvalex.femtocraft.power

import com.itszuvalex.femtocraft.api.power.ICryogenHandler
import net.minecraft.world.World

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Chris on 8/19/2014.
 */
object CryogenRegistry {
  private val passiveHandlers = new ArrayBuffer[ICryogenHandler]
  private val activeHandlers  = new ArrayBuffer[ICryogenHandler]

  def init() {
    registerPassiveHandler(new CryogenPassiveHandler)
    registerActiveHandler(new CryogenActiveHandler)
  }

  def registerPassiveHandler(handler: ICryogenHandler) = passiveHandlers += handler

  def registerActiveHandler(handler: ICryogenHandler) = activeHandlers += handler

  def unregisterPassiveHandler(handler: ICryogenHandler) = passiveHandlers -= handler

  def unregisterActiveHandler(handler: ICryogenHandler) = activeHandlers -= handler

  def getPassivePower(world: World, x: Int, y: Int, z: Int) = getPower(world, x, y, z, passiveHandlers)

  def getActivePower(world: World, x: Int, y: Int, z: Int) = getPower(world, x, y, z, activeHandlers)

  private def getPower(world: World, x: Int, y: Int, z: Int, handlers: ArrayBuffer[ICryogenHandler]): Float = {
    handlers.filter(_.canHandleBlock(world, x, y, z)).foreach { handler =>
      val amount = handler.powerForBlock(world, x, y, z)
      handler.usedBlockForPower(world, x, y, z)
      return amount
                                                              }
    0
  }
}

