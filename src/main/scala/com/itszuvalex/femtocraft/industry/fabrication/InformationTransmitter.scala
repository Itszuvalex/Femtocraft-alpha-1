package com.itszuvalex.femtocraft.industry.fabrication

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.industry.fabrication.traits.{IFabricationSuiteNode, IInformationTransmitter}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

import scala.util.control.TailCalls.{TailRec, _}


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
   * @return Node found by traversing all A connections.
   */
  override def connectionA = findNext(dirA)

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
  override def nodeA = traverseA(this).result

  /**
   *
   * @return Node found by traversing all B connections.
   */
  override def nodeB = traverseB(this).result

  @TailRec
  private def traverseB(iInformationTransmitter: IInformationTransmitter): TailRec[IFabricationSuiteNode] = {
    if (iInformationTransmitter == null || iInformationTransmitter == this) return null
    tailcall(traverseB(iInformationTransmitter.connectionB))
  }

  @TailRec
  private def traverseA(iInformationTransmitter: IInformationTransmitter): TailRec[IFabricationSuiteNode] = {
    if (iInformationTransmitter == null || iInformationTransmitter == this) return null
    tailcall(traverseA(iInformationTransmitter.connectionB))
  }
}
