package femtocraft.research.gui.graph;

import java.util.ArrayList;

import femtocraft.graph.GraphNode;
import femtocraft.graph.IGraphNode;
import femtocraft.managers.research.ResearchTechnology;

public class TechNode extends GraphNode {
	private final ResearchTechnology technology;

	public TechNode(ResearchTechnology technology) {
		super();
		this.technology = technology;
	}

	@Override
	public int getY() {
		return (int) (technology.yDisplay / getYPadding());
	}

	@Override
	public int getDisplayY() {
		return (int) (getY() * getYPadding());
	}

	@Override
	public void setY(int y) {
		technology.yDisplay = (int) (y * getYPadding());
	}

	@Override
	public int getX() {
		return (int) (technology.xDisplay / getXPadding());
	}

	@Override
	public int getDisplayX() {
		return (int) (getX() * getXPadding());
	}

	@Override
	public void setX(int x) {
		technology.xDisplay = (int) (x * getXPadding());
	}

	public ResearchTechnology getTech() {
		return technology;
	}

	public void pruneParents() {
		ArrayList<IGraphNode> nodes = new ArrayList<IGraphNode>();

		for (IGraphNode parent : getParents()) {
			for (IGraphNode alt : getParents()) {
				if (parent == alt)
					continue;

				if ((((TechNode) alt)
						.hasParentTechRecursive(((TechNode) parent).technology))) {
					nodes.add(parent);
				}
			}
		}

		for (IGraphNode parent : nodes) {
			parent.getChildren().remove(this);
		}
		getParents().removeAll(nodes);
	}

	private boolean hasParentTechRecursive(ResearchTechnology tech) {
		boolean contains = tech == getTech();
		if (contains)
			return true;
		for (IGraphNode parent : getParents()) {
			contains = ((TechNode) parent).hasParentTechRecursive(tech)
					|| contains;
		}
		return contains;
	}

	@Override
	public int getXPadding() {
		return TechnologyGraph.X_PADDING;
	}

	@Override
	public int getYPadding() {
		return TechnologyGraph.Y_PADDING;
	}
}
