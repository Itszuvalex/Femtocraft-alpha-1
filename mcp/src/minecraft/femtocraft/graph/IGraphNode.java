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

import java.util.ArrayList;

public interface IGraphNode {
    static int UNINITIALIZED = -99999;

    public boolean isSink();

    public boolean isRoot();

    public void addParent(IGraphNode node);

    public void addChild(IGraphNode node);

    public int getY();

    public void setY(int y);

    public int getDisplayY();

    public int getX();

    public void setX(int x);

    public int getDisplayX();

    public ArrayList<IGraphNode> getParents();

    public ArrayList<IGraphNode> getChildren();

    public int getXPadding();

    public int getYPadding();

    /**
     * Sets y to be 1 greater than the max y of all this node's parents. This
     * assumes 0 is the minimum y.
     */
    public void findHeight();

    /**
     * Orders all parents to find their heights, and once it does, then
     * determines its own;
     */
    public void findHeightRecursive();

    /**
     * Sets height to be 1 less than the minimum of all this node's children.
     * This assumes findHeight has been run previously.
     */
    public void shrinkDown();
}
