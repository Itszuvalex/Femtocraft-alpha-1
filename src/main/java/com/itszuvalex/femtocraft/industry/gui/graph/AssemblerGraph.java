/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package com.itszuvalex.femtocraft.industry.gui.graph;

import com.itszuvalex.femtocraft.graph.MapGraph;
import com.itszuvalex.femtocraft.graph.IGraphNode;
import com.itszuvalex.femtocraft.managers.assembler.AssemblerRecipe;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/26/14.
 */
public class AssemblerGraph extends MapGraph<Integer> {
    public AssemblerGraph(Collection<AssemblerRecipe> recipes) {
        super();
        Map<Integer, IGraphNode> nodes = new HashMap<Integer,
                IGraphNode>();

        //Put recipes into map, mapped by output item ID
        for (AssemblerRecipe recipe : recipes) {
            if (!nodes.containsKey(recipe.output.itemID)) {
                nodes.put(recipe.output.itemID, new AssemblerNode(new ItemStack
                                                                          (recipe.output
                                                                                   .getItem())));
            }
        }

        //Set up children and parents
        for (AssemblerRecipe recipe : recipes) {
            IGraphNode output = nodes.get(recipe.output.itemID);
            if (output == null) {
                continue;
            }

            for (ItemStack stack : recipe.input) {
                if (stack == null) {
                    continue;
                }
                IGraphNode child = nodes.get(stack.itemID);
                if (child == null) {
                    continue;
                }
                output.addChild(child);
                child.addParent(output);
            }
        }

        setNodes(nodes);
    }

    @Override
    protected Class getDummyNodeClass() {
        return DummyAssemblerNode.class;
    }
}
