package com.itszuvalex.femtocraft.industry.fabrication

import com.itszuvalex.femtocraft.api.core.ISaveable
import com.itszuvalex.femtocraft.industry.fabrication.CraftingQueue._
import com.itszuvalex.femtocraft.industry.fabrication.traits.ICraftingJob
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}

/**
 * Created by Itszuvalex on 1/19/15.
 */
object CraftingQueue {
  val QUEUE_TAG = "Queue"
}

class CraftingQueue extends collection.mutable.Queue[ICraftingJob] with ISaveable {
  override def saveToNBT(compound: NBTTagCompound) {
    val tagList = new NBTTagList
    foreach { job => tagList.appendTag({val n = new NBTTagCompound; job.saveToNBT(n); n})}
    compound.setTag(QUEUE_TAG, tagList)
  }

  override def loadFromNBT(compound: NBTTagCompound) {
    val queue = compound.getTagList(QUEUE_TAG, 10)
    (0 until queue.tagCount).map(queue.getCompoundTagAt).foreach(this += CraftingJob.fromNBT(_))
  }
}
