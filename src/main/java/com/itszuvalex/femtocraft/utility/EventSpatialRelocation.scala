package com.itszuvalex.femtocraft.utility

import cpw.mods.fml.common.eventhandler.{Cancelable, Event}
import net.minecraft.block.Block
import net.minecraft.world.World

/**
 * Created by Itszuvalex on 1/1/15.
 */
object EventSpatialRelocation {


  @Cancelable
  class Pickup(val world: World,
               val x: Int,
               val y: Int,
               val z: Int) extends Event {

  }

  @Cancelable
  class Placement(val world: World,
                  val x: Int,
                  val y: Int,
                  val z: Int,
                  val block: Block) extends Event {

  }

}
