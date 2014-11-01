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

package com.itszuvalex.femtocraft.research.gui.graph;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.graph.IGraphNode;
import com.itszuvalex.femtocraft.graph.MapGraph;
import com.itszuvalex.femtocraft.managers.research.Technology;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;

import java.util.HashMap;
import java.util.logging.Level;

public class TechnologyGraph extends MapGraph<String> {
    public static final int X_PADDING = 1;
    public static final int Y_PADDING = 2;

    public TechnologyGraph(HashMap<String, ITechnology> technologies) {
        super();
        Femtocraft.log(Level.INFO, "Creating Graph of Technologies.");
        HashMap<String, IGraphNode> nodes = new HashMap<String, IGraphNode>();

        Femtocraft.log(Level.FINE, "Transferring Technologies to Nodes.");
        // Fill nodes dictionary.
        for (String name : technologies.keySet()) {
            nodes.put(name, new TechNode(technologies.get(name)));
        }

        Femtocraft.log(Level.FINE, "Linking edges.");
        // Add edges
        for (String name : technologies.keySet()) {
            TechNode node = (TechNode) nodes.get(name);
            Technology tech = node.getTech();
            if (tech == null) {
                continue;
            }
            if (tech.prerequisites == null) {
                continue;
            }
            for (String prerequisite : tech.prerequisites) {
                IGraphNode parent = nodes.get(prerequisite);
                if (parent == null) {
                    continue;
                }
                parent.addChild(node);
                node.addParent(parent);
            }
        }

        for (IGraphNode node : nodes.values()) {
            ((TechNode) node).pruneParents();
        }

        setNodes(nodes);
    }

    @Override
    public void computePlacements() {
        super.computePlacements();
        GuiResearch.setSize(greatestWidth() * X_PADDING, greatestHeight()
                * Y_PADDING);
    }

    @Override
    protected Class getDummyNodeClass() {
        return DummyTechNode.class;
    }

}
