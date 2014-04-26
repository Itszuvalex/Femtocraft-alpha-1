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

package femtocraft.graph;

import femtocraft.Femtocraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;

public abstract class Graph {
    protected float EMPTY_PADDING_MULTIPLER = 1.00f;

    protected int maxHeight = 20;
    protected int maxWidth = 20;

    protected int MAX_HILLCLIMB_ITERATIONS = 5000;
    protected int MAX_CHILD_X_DIST = 3;
    protected int MAX_MINIMIZE_PASSES = 10;

    public Graph() {
    }

    public abstract Collection<IGraphNode> getNodes();

    /**
     * Performs a variant of the Sugiyama algorithm to find locations for
     * Technologies.
     */
    public void computePlacements() {
        Femtocraft.logger
                .log(Level.INFO,
                     "Computing Placements. This process may take a while on slower computers.");
        computeHeights();

        // Prepare row count for crossing minimization
        int height = greatestHeight() + 1;
        ArrayList<IGraphNode>[] rows = new ArrayList[height];
        // Scope out the int array so it goes away when we're finished with it.
        // It's only for
        // assigning temporary x values to nodes.
        {
            int[] rowCount = new int[height];
            Arrays.fill(rowCount, 0);
            for (int i = 0; i < rows.length; ++i) {
                rows[i] = new ArrayList<IGraphNode>();
            }
            for (IGraphNode node : getNodes()) {
                rows[node.getY()].add(node);
                node.setX(rowCount[node.getY()]++);
            }
        }

        addDummyNodes(rows);
        // This isn't needed as we can simply expand to be the size of the
        // largest row
        // reduceRows(rows);
        minimizeCrossings(rows);
        Femtocraft.logger.log(Level.INFO, "Graph placement finished.");
    }

    private void addDummyNodes(ArrayList<IGraphNode>[] rows) {
        try {
            for (int i = 0; i < rows.length; ++i) {
                for (IGraphNode node : rows[i]) {
                    ArrayList<IGraphNode> remove = new ArrayList<IGraphNode>();
                    ArrayList<IGraphNode> add = new ArrayList<IGraphNode>();
                    for (IGraphNode child : node.getChildren()) {
                        int levelDif = Math.abs(node.getY() - child.getY());
                        if (levelDif > 1) {
                            remove.add(child);
                            IGraphNode prev = child;
                            for (int j = child.getY() - 1; j > node.getY(); --j) {
                                IGraphNode dummy =
                                        (IGraphNode) getDummyNodeClass()
                                                .newInstance();
                                int depth = j;
                                dummy.setY(depth);
                                int x = prev.getX();
                                if (x >= rows[j].size()) {
                                    rows[j].add(dummy);
                                }
                                else {
                                    rows[j].add(x, dummy);
                                }

                                for (int k = 0; k < rows[j].size(); ++k) {
                                    rows[j].get(k).setX(k);
                                }

                                prev.addParent(dummy);
                                dummy.addChild(prev);
                                prev = dummy;
                            }
                            add.add(prev);
                        }
                    }
                    for (IGraphNode child : add) {
                        child.addParent(node);
                    }
                    node.getChildren().addAll(add);

                    for (IGraphNode child : remove) {
                        child.getParents().remove(node);
                    }
                    node.getChildren().removeAll(remove);
                }
            }

            int width = (int) (greatestWidth() * EMPTY_PADDING_MULTIPLER);
            for (int y = 0; y < rows.length; ++y) {
                ArrayList<IGraphNode> row = rows[y];
                row.ensureCapacity(width);
                for (int x = row.size(); x < width; ++x) {
                    IGraphNode dummy = (IGraphNode) getDummyNodeClass()
                            .newInstance();
                    dummy.setY(y);
                    row.add(0, dummy);
                }

                for (int k = 0; k < row.size(); ++k) {
                    row.get(k).setX(k);
                }
            }
        } catch (InstantiationException e) {
            Femtocraft.logger.log(Level.SEVERE, "Invalid DummyNode class", e);
        } catch (IllegalAccessException e) {
            Femtocraft.logger.log(Level.SEVERE, "Error creating DummyNode " +
                    "instance", e);
        }
    }

    private void minimizeCrossings(ArrayList<IGraphNode>[] rows) {
        Femtocraft.logger.log(Level.INFO, "Minimizing edge crossings for "
                + rows.length + " rows.");
        for (int pass = 0; pass < MAX_MINIMIZE_PASSES; ++pass) {
            Femtocraft.logger.log(Level.INFO, "Minimize Pass " + pass + ".");
            for (int i = 0; i < rows.length - 1; ++i) {
                Femtocraft.logger.log(Level.INFO, "Minimizing " + i + "("
                        + rows[i].size() + ")" + "->" + (i + 1) + "("
                        + rows[i + 1].size() + ")" + ".");
                hillClimb(rows, rows[i], rows[i + 1]);
            }
        }
    }

    private void hillClimb(ArrayList<IGraphNode>[] rows,
                           ArrayList<IGraphNode> row1, ArrayList<IGraphNode> row2) {
        int crossings = totalCrossingCount(rows);
        float distance = totalDistance(rows);
        // for (int i = 0; i < MAX_HILLCLIMB_ITERATIONS; ++i) {

        if (row2.size() < 2) {
            return;
        }

        for (int i1 = 0; i1 < row2.size(); ++i1) {
            for (int i2 = 0; i2 < row2.size(); ++i2) {
                if (i1 == i2) {
                    continue;
                }

                boolean h = heuristics(row2.get(i1), row2.get(i2));
                // Switch x
                switch_x_pos(row2.get(i1), row2.get(i2));

                // If not better, undo
                int new_cross_count = totalCrossingCount(rows);
                float new_distance = totalDistance(rows);

                if (!heuristics(row2.get(i1), row2.get(i2))) {
                    switch_x_pos(row2.get(i1), row2.get(i2));
                }
                else {
                    if (new_cross_count < crossings || !h) {
                        crossings = new_cross_count;
                        distance = new_distance;
                    }
                    else if (new_cross_count == crossings) {
                        if (new_distance < distance) {
                            distance = new_distance;
                        }
                        else {
                            switch_x_pos(row2.get(i1), row2.get(i2));
                        }
                    }
                    else {
                        switch_x_pos(row2.get(i1), row2.get(i2));
                    }
                }
            }
            // }
        }
    }

    private boolean heuristics(IGraphNode node1, IGraphNode node2) {
        // for (GraphNode child : node1.getChildren()) {
        // if (Math.abs(node1.getX() - child.getX()) > Math.floor(node1
        // .getChildren().size() / 2.f))
        // return false;
        // }
        // for (GraphNode child : node2.getChildren()) {
        // if (Math.abs(node2.getX() - child.getX()) > Math.floor(node2
        // .getChildren().size() / 2.f))
        // return false;
        // }

        return true;
    }

    private void switch_x_pos(IGraphNode node1, IGraphNode node2) {
        int prev = node1.getX();
        node1.setX(node2.getX());
        node2.setX(prev);
    }

    private int totalCrossingCount(ArrayList<IGraphNode>[] rows) {
        int connections = 0;
        for (int i = 0; i < (rows.length - 1); ++i) {
            connections += crossingCount(rows[i], rows[i + 1]);
        }
        return connections;
    }

    private int crossingCount(ArrayList<IGraphNode> row1,
                              ArrayList<IGraphNode> row2) {
        int connections = 0;

        ArrayList<Integer> x_top = new ArrayList<Integer>();
        ArrayList<Integer> x_bot = new ArrayList<Integer>();

        for (IGraphNode node : row1) {
            for (IGraphNode child : node.getChildren()) {
                x_top.add(node.getX());
                x_bot.add(child.getX());
            }
        }

        for (int a = 0; a < x_top.size(); ++a) {
            for (int b = a + 1; b < (x_top.size() - 1); ++b) {
                if (isCrossing(x_top.get(a), x_bot.get(a), x_top.get(b),
                               x_bot.get(b))) {
                    ++connections;
                }
            }
        }
        return connections;
    }

    private boolean isCrossing(int x_top_1, int x_connection_1, int x_top_2,
                               int x_connection_2) {
        return ((x_top_1 < x_top_2 && x_connection_1 > x_connection_2) || (x_top_1 > x_top_2 && x_connection_1 < x_connection_2));
    }

    private float totalDistance(ArrayList<IGraphNode>[] rows) {
        float distance = 0;
        for (int i = 0; i < (rows.length - 1); ++i) {
            distance += edgeDistance(rows[i], rows[i + 1]);
        }
        return distance;
    }

    private float edgeDistance(ArrayList<IGraphNode> row1,
                               ArrayList<IGraphNode> row2) {
        // We assume dummy nodes have been made, thus all childs of nodes in
        // row1 are in row2.
        float distance = 0;
        for (IGraphNode node : row1) {
            for (IGraphNode child : node.getChildren()) {
                distance += Math.sqrt(Math.pow(child.getX() - node.getX(), 2)
                                              + Math.pow(child.getY() - node.getY(), 2));
            }
        }
        return distance;
    }

    private void reduceRows(ArrayList<IGraphNode>[] rows) {
        Femtocraft.logger.log(Level.INFO, "Reducing row widths.");
        for (int i = 0; i < rows.length; ++i) {
            ArrayList<IGraphNode> row = rows[i];
            int ri = 0;
            while (row.size() > maxWidth && ri++ < row.size()) {
                IGraphNode node = row.get(ri);
                int height = node.getY();
                node.shrinkDown();
                int newHeight = node.getY();
                if (height != newHeight) {
                    row.remove(node);
                    rows[newHeight].add(node);
                }
            }
        }
    }

    private void computeHeights() {
        Femtocraft.logger.log(Level.INFO, "Computing heights.");
        for (IGraphNode node : getNodes()) {
            // For finding stand-alone technologies with no parents or children.
            if (node.isRoot()) {
                node.findHeight();
            }
            // Otherwise, compute the height tree from the bottom up.
            if (node.isSink()) {
                node.findHeightRecursive();
            }
        }
    }

    protected int greatestHeight() {
        int greatestHeight = -1;
        for (IGraphNode node : getNodes()) {
            greatestHeight = Math.max(greatestHeight, node.getY());
        }
        return greatestHeight;
    }

    protected int greatestWidth() {
        int greatestWidth = -1;
        for (IGraphNode node : getNodes()) {
            greatestWidth = Math.max(greatestWidth, node.getX());
        }
        return greatestWidth;
    }

    protected abstract Class getDummyNodeClass();
}
