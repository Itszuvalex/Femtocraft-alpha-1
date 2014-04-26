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
