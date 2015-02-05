//package com.itszuvalex.femtocraft.industry.fabrication.traits
//
//import java.util
//import java.util.UUID
//
//import com.itszuvalex.femtocraft.api.core.ISaveable
//import net.minecraft.nbt.NBTTagCompound
//
//trait IWorkJob extends ISaveable {
//
//  def ID: UUID
//
//  override def saveToNBT(compound: NBTTagCompound): Unit = ???
//
//  override def loadFromNBT(compound: NBTTagCompound): Unit = ???
//
//  def output: util.Collection[IResource]
//
//  def input: util.Collection[IResource]
//
//  def start()
//
//  def finish()
//
//  def canStart: Boolean
//
//  def canFinish: Boolean
//
//  def priority: Int
//
//}
