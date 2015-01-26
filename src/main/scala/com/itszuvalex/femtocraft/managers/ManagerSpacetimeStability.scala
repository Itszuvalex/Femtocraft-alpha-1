package com.itszuvalex.femtocraft.managers

import cpw.mods.fml.common.Mod.EventHandler
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.event.world.ChunkEvent

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Christopher on 1/25/2015.
 */
object ManagerSpacetimeStability {
  val chunks = ArrayBuffer[Chunk]()

  @EventHandler
  def onChunkLoad(event: ChunkEvent.Load) = {
event.world
  }

  @EventHandler
  def onChunkUnload(event: ChunkEvent.Unload) = {

  }
}
