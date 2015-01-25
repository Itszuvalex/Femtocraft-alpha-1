package com.itszuvalex.femtocraft.industry.fabrication.traits

import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher on 1/22/2015.
 */
trait IInformationTransmitter {

  /**
   *
   * @return Direction A
   */
  def directionA: ForgeDirection

  /**
   *
   * @return DirectionB
   */
  def directionB: ForgeDirection

  def setDirectionA(dir: ForgeDirection) : Boolean

  def setDirectionB(dir: ForgeDirection) : Boolean

  /**
   *
   * @param dir Direction of access
   * @return If dir == dirA, dirB, if dir == dirB, dir A, UNKNOWN otherwise.
   */
  def nextDirection(dir: ForgeDirection): ForgeDirection

  /**
   *
   * @return Information transmitter in the A direction, if found.
   */
  def connectionA: IInformationTransmitter

  /**
   *
   * @return Information transmitter in the B direction, if found.
   */
  def connectionB: IInformationTransmitter

  /**
   *
   * @return Node found by traversing all A connections.
   */
  def nodeA: IFabricationSuiteNode

  /**
   *
   * @return Node found by traversing all B connections.
   */
  def nodeB: IFabricationSuiteNode

  /**
   *
   * @param dir Direction of access.
   * @return Node found by traversing the path in the opposite direction.
   */
  def nextConnection(dir: ForgeDirection): IFabricationSuiteNode

  /**
   *
   * @return (NodeA, NodeB0 found by traversing from this transmitter in both directions.
   */
  def connections: (IFabricationSuiteNode, IFabricationSuiteNode)

  /**
   *
   * @return True if has valid non-null nodes A and B.
   */
  def isTransmitting : Boolean
}
