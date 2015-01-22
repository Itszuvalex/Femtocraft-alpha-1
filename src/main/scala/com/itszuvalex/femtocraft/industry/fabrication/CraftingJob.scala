package com.itszuvalex.femtocraft.industry.fabrication

import java.util

import com.itszuvalex.femtocraft.industry.fabrication.traits.{IResource, ICraftingJob}
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by Itszuvalex on 1/19/15.
 */
object CraftingJob {
  def fromNBT(nbt: NBTTagCompound): CraftingJob = {
    val job = new CraftingJob
    job.loadFromNBT(nbt)
    job
  }
}

class CraftingJob extends ICraftingJob {
  override def parent: ICraftingJob = ???

  override def children: util.Collection[ICraftingJob] = ???

  override def saveToNBT(compound: NBTTagCompound): Unit = ???

  override def loadFromNBT(compound: NBTTagCompound): Unit = ???

  override def output: util.Collection[IResource] = ???

  override def finish: Unit = ???

  override def input: util.Collection[IResource] = ???

  override def canStart: Boolean = ???

  override def start: Unit = ???
}
