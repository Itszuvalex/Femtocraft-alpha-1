package com.itszuvalex.femtocraft.industry.fabrication

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.industry.fabrication.traits.{IFabricationSuiteNode, IInformationTransmitter}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

import scala.annotation.tailrec


/**
 * Created by Christopher on 1/22/2015.
 */
class InformationTransmitter extends TileEntityBase with IInformationTransmitter {
  @Saveable(desc = true) private var dirA = UNKNOWN
  @Saveable(desc = true) private var dirB = UNKNOWN

  /**
   *
   * @return Direction A
   */
  override def directionA = dirA

  /**
   *
   * @param dir Direction of access.
   * @return Node found by traversing the path in the opposite direction.
   */
  override def nextConnection(dir: ForgeDirection) = if (dir == UNKNOWN) null
  else {
    if (dir == dirA) nodeB
    else if (dir == dirB) nodeA
    else null
  }

  /**
   *
   * @return Node found by traversing all B connections.
   */
  override def connectionB = findNext(dirB)

  /**
   *
   * @return Node found by traversing all A connections.
   */
  override def connectionA = findNext(dirA)

  private def findNext(dir: ForgeDirection) = {
    if (dir == UNKNOWN) null
    else {
      val x = xCoord + dir.offsetX
      val y = yCoord + dir.offsetY
      val z = zCoord + dir.offsetZ
      if (worldObj.blockExists(x, y, z)) {
        worldObj.getTileEntity(x, y, z) match {
          case i: IInformationTransmitter => i
          case _                          => null
        }
      }
      else
        null
    }
  }

  /**
   *
   * @param dir Direction of access
   * @return If dir == dirA, dirB, if dir == dirB, dir A, UNKNOWN otherwise.
   */
  override def nextDirection(dir: ForgeDirection): ForgeDirection = if (dir != UNKNOWN) {
    if (dir == dirA) dirB
    else if (dir == dirB) dirA
    else UNKNOWN
  }
  else UNKNOWN

  /**
   *
   * @return (NodeA, NodeB0 found by traversing from this transmitter in both directions.
   */
  override def connections: (IFabricationSuiteNode, IFabricationSuiteNode) = (nodeA, nodeB)

  /**
   *
   * @return DirectionB
   */
  override def directionB = dirB

  override def setDirectionA(dir: ForgeDirection): Boolean = {
    if (dir != UNKNOWN && dir == dirB) return false
    dirA = dir
    true
  }

  override def setDirectionB(dir: ForgeDirection): Boolean = {
    if (dir != UNKNOWN && dir == dirA) return false
    dirB = dir
    true
  }

  /**
   *
   * @return Node found by traversing all A connections.
   */
  override def nodeA = traverseA(this, xCoord, yCoord, zCoord)

  /**
   *
   * @return Node found by traversing all B connections.
   */
  override def nodeB = traverseB(this, xCoord, yCoord, zCoord)

  @tailrec
  private def traverseB(iInformationTransmitter: IInformationTransmitter, x: Int, y: Int, z: Int): IFabricationSuiteNode = {
    if (iInformationTransmitter == null || iInformationTransmitter == this) return null
    if (!worldObj.blockExists(x, y, z)) return null
    val dir = iInformationTransmitter.directionB
    if (dir == UNKNOWN) return null
    worldObj.getTileEntity(x, y, z) match {
      case i: IFabricationSuiteNode   => i
      case l: IInformationTransmitter => traverseB(l, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)
      case _                          => null
    }
  }

  @tailrec
  private def traverseA(iInformationTransmitter: IInformationTransmitter, x: Int, y: Int, z: Int): IFabricationSuiteNode = {
    if (iInformationTransmitter == null || iInformationTransmitter == this) return null
    if (!worldObj.blockExists(x, y, z)) return null
    val dir = iInformationTransmitter.directionA
    if (dir == UNKNOWN) return null
    worldObj.getTileEntity(x, y, z) match {
      case i: IFabricationSuiteNode   => i
      case l: IInformationTransmitter => traverseA(l, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)
      case _                          => null
    }
  }
}
