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

package com.itszuvalex.femtocraft.graph;

import java.util.Collection;
import java.util.Map;

public abstract class MapGraph<K> extends Graph {
    private Map<K, IGraphNode> node_map;

    public MapGraph() {
        super();
        this.node_map = null;
    }

    public MapGraph(Map<K, IGraphNode> nodes) {
        super();
        setNodes(nodes);
    }

    @Override
    public Collection<IGraphNode> getNodes() {
        return node_map.values();
    }

    public void setNodes(Map<K, IGraphNode> nodes) {
        this.node_map = nodes;
    }

    public IGraphNode getNode(K key) {
        return node_map.get(key);
    }
}
