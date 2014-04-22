package femtocraft.research.gui.graph;

public class DummyNode extends GraphNode {
	private int x;
	private int y;

	public DummyNode() {
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#getY()
	 */
	@Override
	public int getY() {
		return (int) (y / TechnologyGraph.X_PADDING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#setY(int)
	 */
	@Override
	public void setY(int y) {
		this.y = (int) (y * TechnologyGraph.X_PADDING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#getX()
	 */
	@Override
	public int getX() {
		return (int) (x / TechnologyGraph.X_PADDING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.x = (int) (x * TechnologyGraph.X_PADDING);
	}

}
