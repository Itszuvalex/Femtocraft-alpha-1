package femtocraft.graph;

import java.util.ArrayList;

public abstract class GraphNode implements IGraphNode {
    private ArrayList<IGraphNode> parents;
    private ArrayList<IGraphNode> children;
    private int x;
    private int y;

    public GraphNode() {
        parents = new ArrayList<IGraphNode>();
        children = new ArrayList<IGraphNode>();
        setX(UNINITIALIZED);
        setY(UNINITIALIZED);
    }

    @Override
    public int getY() {
        return (int) (y / getYPadding());
    }

    @Override
    public void setY(int y) {
        this.y = (int) (y * getYPadding());
    }

    @Override
    public int getDisplayY() {
        return (int) (getY() * getYPadding());
    }

    @Override
    public int getX() {
        return (int) (x / getXPadding());
    }

    @Override
    public void setX(int x) {
        this.x = (int) (x * getXPadding());
    }

    @Override
    public int getDisplayX() {
        return (int) (getX() * getXPadding());
    }

    @Override
    public boolean isSink() {
        return children.size() == 0;
    }

    @Override
    public boolean isRoot() {
        return parents.size() == 0;
    }

    @Override
    public void addParent(IGraphNode node) {
        parents.add(node);
    }

    @Override
    public void addChild(IGraphNode node) {
        children.add(node);
    }

    @Override
    public ArrayList<IGraphNode> getParents() {
        return parents;
    }

    @Override
    public ArrayList<IGraphNode> getChildren() {
        return children;
    }

    /**
     * Sets y to be 1 greater than the max y of all this node's parents. This
     * assumes 0 is the minimum y.
     */
    @Override
    public void findHeight() {
        // Prevent unnecessary recomputation
        // if (getY() <= UNINITIALIZED)
        // return;

        int maxHeight = -1;
        for (IGraphNode node : parents) {
            maxHeight = Math.max(maxHeight, node.getY());
        }
        setY(maxHeight + 1);
    }

    /**
     * Orders all parents to find their heights, and once it does, then
     * determines its own;
     */
    @Override
    public void findHeightRecursive() {
        // Prevent unnecessary recomputation
        // if (getY() <= UNINITIALIZED)
        // return;

        for (IGraphNode node : parents) {
            node.findHeightRecursive();
        }

        findHeight();
    }

    /**
     * Sets height to be 1 less than the minimum of all this node's children.
     * This assumes findHeight has been run previously.
     */
    @Override
    public void shrinkDown() {
        int minHeight = Integer.MAX_VALUE;
        for (IGraphNode node : children) {
            minHeight = Math.min(minHeight, node.getY());
        }
        setY(minHeight == Integer.MAX_VALUE ? getY() : minHeight - 1);
    }
}
