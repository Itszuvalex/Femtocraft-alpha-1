package com.itszuvalex.femtocraft.api.events

import cpw.mods.fml.common.eventhandler.{Cancelable, Event}
import net.minecraft.block.Block
import net.minecraft.world.World

import scala.beans.BeanProperty

/**
 * Created by Itszuvalex on 1/1/15.
 */
object EventSpatialRelocation {

  @Cancelable
  class Pickup(@BeanProperty val world: World, @BeanProperty val x: Int, @BeanProperty val y: Int,
               @BeanProperty val z: Int)
    extends Event with EventSpatialRelocation

  @Cancelable
  class Placement(@BeanProperty val world: World, @BeanProperty val x: Int, @BeanProperty val y: Int,
                  @BeanProperty val z: Int, @BeanProperty val block: Block)
    extends Event with EventSpatialRelocation

}

trait EventSpatialRelocation
