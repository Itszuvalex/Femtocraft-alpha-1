package femtocraft.research.gui.graph;

import java.util.ArrayList;

import femtocraft.managers.research.ResearchTechnology;

public class GraphNode {
	private final ResearchTechnology technology;
	private ArrayList<GraphNode> parents;
	private ArrayList<GraphNode> children;

	private static int UNINITIALIZED = -99999;
	protected static float padding = 2.0f;

	public GraphNode(ResearchTechnology technology) {
		this.technology = technology;
		parents = new ArrayList<GraphNode>();
		children = new ArrayList<GraphNode>();
		setX(UNINITIALIZED);
		setY(UNINITIALIZED);
	}

	public boolean isSink() {
		return children.size() == 0;
	}

	public boolean isRoot() {
		return parents.size() == 0;
	}

	public void addParent(GraphNode node) {
		parents.add(node);
	}

	public void addChild(GraphNode node) {
		children.add(node);
	}

	public int getY() {
		return (int) (technology.yDisplay / padding);
	}

	public void setY(int y) {
		technology.yDisplay = (int) (y * padding);
	}

	public int getX() {
		return (int) (technology.xDisplay / padding);
	}

	public void setX(int x) {
		technology.xDisplay = (int) (x * padding);
	}

	ArrayList<GraphNode> getParents() {
		return parents;
	}

	ArrayList<GraphNode> getChildren() {
		return children;
	}

	ResearchTechnology getTech() {
		return technology;
	}

	/**
	 * Sets y to be 1 greater than the max y of all this node's parents. This
	 * assumes 0 is the minimum y.
	 */
	public void findHeight() {
		// Prevent unnecessary recomputation
		if (getY() != UNINITIALIZED)
			return;

		int maxHeight = -1;
		for (GraphNode node : parents) {
			maxHeight = Math.max(maxHeight, node.getY());
		}
		setY(maxHeight + 1);
	}

	/**
	 * Orders all parents to find their heights, and once it does, then
	 * determines its own;
	 */
	public void findHeightRecursive() {
		// Prevent unnecessary recomputation
		if (getY() != UNINITIALIZED)
			return;

		for (GraphNode node : parents) {
			node.findHeightRecursive();
		}

		findHeight();
	}

	/**
	 * Sets height to be 1 less than the minimum of all this node's children.
	 * This assumes findHeight has been run previously.
	 */
	public void shrinkDown() {
		int minHeight = Integer.MAX_VALUE;
		for (GraphNode node : children) {
			minHeight = Math.min(minHeight, node.getY());
		}
		setY(minHeight == Integer.MAX_VALUE ? getY() : minHeight - 1);
	}
}
