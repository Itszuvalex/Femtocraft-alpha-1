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

import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.graph.GraphNode;
import com.itszuvalex.femtocraft.graph.IGraphNode;

import java.util.ArrayList;

public class TechNode extends GraphNode {
    private final ITechnology technology;

    public TechNode(ITechnology technology) {
        super();
        this.technology = technology;
        setX(UNINITIALIZED);
        setY(UNINITIALIZED);
    }

    @Override
    public int getXPadding() {
        return TechnologyGraph.X_PADDING;
    }

    @Override
    public int getYPadding() {
        return TechnologyGraph.Y_PADDING;
    }

    public ITechnology getTech() {
        return technology;
    }

    public void pruneParents() {
        ArrayList<IGraphNode> nodes = new ArrayList<IGraphNode>();

        ArrayList<IGraphNode> parents = getParents();

        for (IGraphNode parent : getParents()) {
            for (IGraphNode alt : getParents()) {
                if (parent == alt) {
                    continue;
                }

                if (((TechNode) alt)
                        .hasParentTechRecursive(((TechNode) parent).technology)) {
                    nodes.add(parent);
                }
            }
        }

        for (IGraphNode parent : nodes) {
            parent.getChildren().remove(this);
        }
        getParents().removeAll(nodes);
    }

    private boolean hasParentTechRecursive(ITechnology tech) {
        boolean contains = tech == getTech();
        if (contains) {
            return true;
        }
        for (IGraphNode parent : getParents()) {
            contains = ((TechNode) parent).hasParentTechRecursive(tech)
                       || contains;
        }
        return contains;
    }
}
