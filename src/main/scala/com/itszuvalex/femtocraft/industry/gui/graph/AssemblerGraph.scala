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
package com.itszuvalex.femtocraft.industry.gui.graph

import java.util
import com.itszuvalex.femtocraft.api.AssemblerRecipe
import com.itszuvalex.femtocraft.graph.{IGraphNode, MapGraph}
import com.itszuvalex.femtocraft.implicits.IDImplicits._
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 4/26/14.
 */
class AssemblerGraph(recipes: util.Collection[AssemblerRecipe]) extends MapGraph[Integer] {
  val nodes = new util.HashMap[Integer, IGraphNode]
  recipes.foreach { recipe =>
    if (!nodes.containsKey(recipe.output.getItem.itemID)) {
      nodes.put(recipe.output.getItem.itemID, new AssemblerNode(new ItemStack(recipe.output.getItem)))
    }
                  }
  recipes.foreach { recipe =>
    val output = nodes.get(recipe.output.getItem.itemID)
    if (output != null) {
      recipe.input.foreach { stack =>
        if (stack != null) {
          val child = nodes.get(stack.getItem.itemID)
          if (child != null) {
            output.addChild(child)
            child.addParent(output)
          }
        }
                           }
    }
                  }
  setNodes(nodes)

  protected def getDummyNodeClass = classOf[DummyAssemblerNode]
}
