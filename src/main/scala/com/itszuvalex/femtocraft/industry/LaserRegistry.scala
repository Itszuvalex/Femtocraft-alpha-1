package com.itszuvalex.femtocraft.industry

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * Created by Christopher on 2/20/2015.
 */
object LaserRegistry {

  private val laserInfoMap = new mutable.HashMap[String, LaserInfo]

  val MODULATION_DEFAULT = "Default"

  registerLaser(MODULATION_DEFAULT, FemtocraftUtils.colorFromARGB(1, 1, 1, 1))

  def getModulations: util.Collection[String] = laserInfoMap.keySet

  def getColor(modulation: String): Int = laserInfoMap.get(modulation) match {
    case Some(info) => info.color
    case _ => FemtocraftUtils.colorFromARGB(255, 1, 1, 1)
  }

  def registerLaser(modulation: String, color: Int) = laserInfoMap(modulation) = new LaserInfo(color)

  /**
   * Utility function for spawning laser blocks in the world.  If the block where the laser is being attempted to spawn is a
   * ILaserInteractable, then it will be interacted with as if the specified laser exists.
   *
   * I.E.  The laser already exists, will sustain it, or 'overwrite' it if this laser is stronger.
   *
   * Otherwise, it spawns a laser and interacts with it.
   *
   * @param world World to spawn laser
   * @param x X location of laser
   * @param y Y location of laser
   * @param z Z location of laser
   * @param modulation Modulation of laser
   * @param direction Direction of laser
   * @param strength Strength of laser block
   * @param distance Distance for laser block to propagate.
   */
  def makeLaser(world: World, x: Int, y: Int, z: Int, modulation: String, direction: ForgeDirection, strength: Int, distance: Int): Unit = {
    if (distance == 0) return
    if(!getModulations.contains(modulation)) return
    if (world.blockExists(x, y, z)) {
      world.getTileEntity(x, y, z) match {
        case int: ILaserInteractable =>
          int.interact(direction, strength, modulation, distance)
        case _ if world.isAirBlock(x, y, z) =>
          world.setBlock(x, y, z, Femtocraft.blockLaser)
          world.getTileEntity(x, y, z) match {
            case int: ILaserInteractable =>
              int.interact(direction, strength, modulation, distance)
            case _ =>
          }
        case _ =>
      }
    }
  }

  private class LaserInfo(val color: Int)

}
