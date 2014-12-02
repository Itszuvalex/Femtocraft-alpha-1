///*
// * ******************************************************************************
// *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// *  * Itszuvalex@gmail.com
// *  *
// *  * This program is free software; you can redistribute it and/or
// *  * modify it under the terms of the GNU General Public License
// *  * as published by the Free Software Foundation; either version 2
// *  * of the License, or (at your option) any later version.
// *  *
// *  * This program is distributed in the hope that it will be useful,
// *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  * GNU General Public License for more details.
// *  *
// *  * You should have received a copy of the GNU General Public License
// *  * along with this program; if not, write to the Free Software
// *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// *  *****************************************************************************
// */
//package com.itszuvalex.femtocraft.research.gui.graph
//
//import java.util
//
//import com.itszuvalex.femtocraft.Femtocraft
//import com.itszuvalex.femtocraft.api.core.Configurable
//import com.itszuvalex.femtocraft.api.research.ITechnology
//import com.itszuvalex.femtocraft.graph.{IGraphNode, MapGraph}
//import com.itszuvalex.femtocraft.research.gui.GuiResearch
//import org.apache.logging.log4j.Level
//
//import scala.collection.JavaConversions._
//import scala.collection.mutable
//
//object TechnologyGraph {
//  @Configurable val X_PADDING = 1
//  @Configurable val Y_PADDING = 2
//}
//
//@Configurable class TechnologyGraph(technologies: util.Map[String, ITechnology]) extends MapGraph[String] {
//  {
//    Femtocraft.log(Level.INFO, "Creating Graph of Technologies.")
//    val nodes = new mutable.HashMap[String, IGraphNode]
//    Femtocraft.log(Level.TRACE, "Transferring Technologies to Nodes.")
//    technologies.foreach { pair =>
//      nodes.put(pair._1, new TechNode(pair._2))
//                         }
//    Femtocraft.log(Level.TRACE, "Linking edges.")
//    nodes.values.foreach { case node: TechNode =>
//      val tech = node.getTech
//      if (!(tech == null || tech.getPrerequisites == null)) {
//        tech.getPrerequisites.map(nodes.get(_).orNull).filterNot(_ == null).
//        foreach { parent =>
//          parent.addChild(node)
//          node.addParent(parent)
//                }
//      }
//                         }
//    nodes.values.foreach { case node: TechNode => node.pruneParents()}
//    setNodes(nodes)
//  }
//
//  override def computePlacements() {
//    super.computePlacements()
//    GuiResearch.setSize(greatestWidth * TechnologyGraph.X_PADDING, greatestHeight * TechnologyGraph.Y_PADDING)
//  }
//
//  protected def getDummyNodeClass: Class[_] = classOf[DummyTechNode]
//}
