/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.graph

import java.util.Collection

import com.itszuvalex.femtocraft.Femtocraft
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

abstract class Graph {
  protected var EMPTY_PADDING_MULTIPLER : Float = 1.00f
  protected var maxHeight               : Int   = 20
  protected var maxWidth                : Int   = 20
  protected var MAX_HILLCLIMB_ITERATIONS: Int   = 5000
  protected var MAX_CHILD_X_DIST        : Int   = 3
  protected var MAX_MINIMIZE_PASSES     : Int   = 10

  /**
   * Performs a variant of the Sugiyama algorithm to find locations for Technologies.
   */
  def computePlacements() {
    Femtocraft.log(Level.TRACE, "Computing Placements. This process may take a while on slower computers.")
    computeHeights()
    val height = greatestHeight + 1
    val rows = new ArrayBuffer[ArrayBuffer[IGraphNode]](height)
    val rowCount = new ArrayBuffer[Int](height)
    for (i <- 0 until height) {
      rows += new ArrayBuffer[IGraphNode]
      rowCount += 0
    }

    getNodes.foreach {
                       node =>
                         rows.get(node.getY).add(node)
                         node.setX(rowCount.get(node.getY))
                         rowCount.update(node.getY, rowCount.get(node.getY) + 1)
                     }

    addDummyNodes(rows)
    minimizeCrossings(rows)
    Femtocraft.log(Level.TRACE, "Graph placement finished.")
  }

  private def computeHeights() {
    Femtocraft.log(Level.TRACE, "Computing heights.")
    getNodes.foreach(_.findHeightRecursive())
  }

  protected def greatestHeight: Int = {
    var greatestHeight: Int = -1
    getNodes.foreach(node => greatestHeight = Math.max(greatestHeight, node.getY))
    greatestHeight
  }

  def getNodes: Collection[IGraphNode]

  private def addDummyNodes(rows: ArrayBuffer[ArrayBuffer[IGraphNode]]) {
    try {
      rows.foreach { row1 =>
        row1.foreach { node =>
          val remove = new ArrayBuffer[IGraphNode]
          val add = new ArrayBuffer[IGraphNode]
          node.getChildren.foreach { child =>
            val levelDif: Int = Math.abs(node.getY - child.getY)
            if (levelDif > 1) {
              remove.add(child)
              var prev: IGraphNode = child
              (child.getY - 1 until node.getY by -1).foreach { j =>
                val dummy: IGraphNode = getDummyNodeClass.newInstance.asInstanceOf[IGraphNode]
                dummy.setY(j)
                val x: Int = prev.getX
                if (x >= rows(j).size) {
                  rows(j) += dummy
                }
                else {
                  rows(j).insert(x, dummy)
                }
                for (k <- 0 until rows(j).size) {
                  rows(j).get(k).setX(k)
                }
                prev.addParent(dummy)
                dummy.addChild(prev)
                prev = dummy
                                                             }
              add += prev
            }
                                   }
          add.foreach(_.addParent(node))
          node.getChildren.addAll(add)
          remove.foreach(_.getParents.remove(node))
          node.getChildren.removeAll(remove)
                     }
                   }
      val width: Int = (greatestWidth * EMPTY_PADDING_MULTIPLER).toInt

      for (y <- 0 until rows.length) {
        val row = rows(y)
        row.sizeHint(width)
        for (x <- row.size until width) {
          val dummy = getDummyNodeClass.newInstance.asInstanceOf[IGraphNode]
          dummy.setY(y)
          row.prepend(dummy)
        }


        for (k <- 0 until row.size) {
          row.get(k).setX(k)
        }
      }
    }
    catch {
      case e: InstantiationException =>
        Femtocraft.log(Level.ERROR, "Invalid DummyNode class")
        e.printStackTrace()
      case e: IllegalAccessException =>
        Femtocraft.log(Level.ERROR, "Error creating DummyNode instance")
        e.printStackTrace()
    }
  }

  private def minimizeCrossings(rows: ArrayBuffer[ArrayBuffer[IGraphNode]]) {
    Femtocraft.log(Level.TRACE, "Minimizing edge crossings for " + rows.length + " rows.")
    for (pass <- 0 until MAX_MINIMIZE_PASSES) {
      Femtocraft.log(Level.TRACE, "Minimize Pass " + pass + ".")
      for (i <- 0 until rows.length - 1) {
        Femtocraft.log(Level.TRACE,
                       "Minimizing " + i + "(" + rows(i).size + ")" + "->" + (i + 1) + "(" + rows(i + 1)
                                                                                             .size + ")" + ".")
        hillClimb(rows, rows(i), rows(i + 1))
      }
      for(i <- (0 until rows.length -2).reverse) {
        Femtocraft.log(Level.TRACE,
                       "Minimizing " + i + "(" + rows(i).size + ")" + "->" + (i + 1) + "(" + rows(i + 1)
                                                                                             .size + ")" + ".")
        hillClimb(rows, rows(i), rows(i + 1))
      }
    }

  }

  protected def getDummyNodeClass: Class[_]

  protected def greatestWidth: Int = {
    var greatestWidth: Int = -1
    getNodes.foreach { node => greatestWidth = Math.max(greatestWidth, node.getX)}
    greatestWidth
  }

  private def hillClimb(rows: ArrayBuffer[ArrayBuffer[IGraphNode]], row1: ArrayBuffer[IGraphNode],
                        row2: ArrayBuffer[IGraphNode]) {
    var crossings: Int = totalCrossingCount(rows)
    var distance: Float = totalDistance(rows)
    if (row2.size < 2) {
      return
    }

    for (i1 <- 0 until row2.size - 1) {
      for (i2 <- i1 + 1 until row2.size) {
        if (i1 != i2) {
          val h: Boolean = heuristics(row2.get(i1), row2.get(i2))
          switch_x_pos(row2.get(i1), row2.get(i2))
          val new_cross_count = totalCrossingCount(rows)
          val new_distance = totalDistance(rows)
          if (!heuristics(row2.get(i1), row2.get(i2))) {
            switch_x_pos(row2.get(i1), row2.get(i2))
          }
          else {
            if (new_cross_count < crossings || !h) {
              crossings = new_cross_count
              distance = new_distance
            }
            else if (new_cross_count == crossings) {
              if (new_distance < distance) {
                distance = new_distance
              }
              else {
                switch_x_pos(row2.get(i1), row2.get(i2))
              }
            }
            else {
              switch_x_pos(row2.get(i1), row2.get(i2))
            }
          }
        }
      }
    }
  }

  private def totalCrossingCount(rows: ArrayBuffer[ArrayBuffer[IGraphNode]]): Int = {
    var connections: Int = 0

    for (i <- 0 until (rows.length - 1)) {
      connections += crossingCount(rows(i), rows(i + 1))
    }
    connections
  }

  private def totalDistance(rows: ArrayBuffer[ArrayBuffer[IGraphNode]]): Float = {
    var distance: Float = 0
    for (i <- 0 until (rows.length - 1)) {
      distance += edgeDistance(rows(i), rows(i + 1))
    }
    distance
  }

  private def heuristics(node1: IGraphNode, node2: IGraphNode): Boolean = true

  private def switch_x_pos(node1: IGraphNode, node2: IGraphNode) {
    val prev = node1.getX
    node1.setX(node2.getX)
    node2.setX(prev)
  }

  private def crossingCount(row1: ArrayBuffer[IGraphNode], row2: ArrayBuffer[IGraphNode]): Int = {
    var connections: Int = 0
    val x_top = new ArrayBuffer[Integer]
    val x_bot = new ArrayBuffer[Integer]
    row1.foreach(node => node.getChildren.foreach { child => x_top += node.getX; x_bot += child.getX})
    for (a <- 0 until x_top.size) {
      for (b <- a + 1 until (x_top.size - 1)) {
        if (isCrossing(x_top.get(a), x_bot.get(a), x_top.get(b), x_bot.get(b))) {
          connections += 1
        }
      }
    }
    connections
  }

  private def edgeDistance(row1: ArrayBuffer[IGraphNode], row2: ArrayBuffer[IGraphNode]): Float = {
    var distance: Float = 0
    row1.
    foreach(node =>
              node.getChildren.
              foreach(child =>
                        distance += Math.sqrt(Math.pow(child.getX - node.getX, 2) +
                                              Math.pow(child.getY - node.getY, 2)).toFloat
                     ))
    distance
  }

  private def isCrossing(x_top_1: Int, x_connection_1: Int, x_top_2: Int, x_connection_2: Int): Boolean = {
    (x_top_1 < x_top_2 && x_connection_1 > x_connection_2) || (x_top_1 > x_top_2 && x_connection_1 < x_connection_2)
  }

  private def reduceRows(rows: ArrayBuffer[ArrayBuffer[IGraphNode]]) {
    Femtocraft.log(Level.TRACE, "Reducing row widths.")
    for (row <- rows) {
      var ri: Int = 0
      while (row.size > maxWidth && {ri += 1; ri - 1} < row.size) {
        val node: IGraphNode = row.get(ri)
        val height: Int = node.getY
        node.shrinkDown()
        val newHeight: Int = node.getY
        if (height != newHeight) {
          row.remove(node)
          rows(newHeight).add(node)
        }
      }
    }
    for (row <- rows.reverse)
    {

    }
  }
}
