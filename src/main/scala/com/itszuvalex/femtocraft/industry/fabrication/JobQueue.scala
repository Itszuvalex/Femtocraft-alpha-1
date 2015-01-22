package com.itszuvalex.femtocraft.industry.fabrication

import com.itszuvalex.femtocraft.api.core.ISaveable
import com.itszuvalex.femtocraft.industry.fabrication.JobQueue._
import com.itszuvalex.femtocraft.industry.fabrication.traits.IWorkJob
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}

/**
 * Created by Itszuvalex on 1/19/15.
 */
object JobQueue {
  val QUEUE_TAG = "Queue"

  case object JobOrdering extends Ordering[IWorkJob] {
    override def compare(x: IWorkJob, y: IWorkJob) = x.priority - y.priority
  }
}

class JobQueue extends collection.mutable.PriorityQueue[IWorkJob] with ISaveable {
  override def saveToNBT(compound: NBTTagCompound) {
    val tagList = new NBTTagList
    foreach { job => tagList.appendTag({val n = new NBTTagCompound; job.saveToNBT(n); n})}
    compound.setTag(QUEUE_TAG, tagList)
  }

  override def loadFromNBT(compound: NBTTagCompound) {
    clear()
    val queue = compound.getTagList(QUEUE_TAG, 10)
    (0 until queue.tagCount).map(queue.getCompoundTagAt).foreach(this += CraftingJob.fromNBT(_))
  }
}
