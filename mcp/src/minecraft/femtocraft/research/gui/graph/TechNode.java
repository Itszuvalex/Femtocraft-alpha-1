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
		setX(UNINITIALIZED);
		setY(UNINITIALIZED);
	}

	@Override
	public int getY() {
		if (technology == null)
			return super.getY();
		else
			return (int) (technology.yDisplay / getYPadding());
	}

	@Override
	public void setY(int y) {
		if (technology == null)
			super.setY(y);
		else
			technology.yDisplay = (int) (y * getYPadding());
	}

	@Override
	public int getX() {
		if (technology == null)
			return super.getX();
		else
			return (int) (technology.xDisplay / getXPadding());
	}

	@Override
	public void setX(int x) {
		if (technology == null)
			super.setX(x);
		else
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
