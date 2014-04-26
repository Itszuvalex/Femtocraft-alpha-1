package femtocraft.research.gui.graph;

public class DummyTechNode extends TechNode {
	private int x;
	private int y;

	public DummyTechNode() {
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#getY()
	 */
	@Override
	public int getY() {
		return (int) (y / getYPadding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#setY(int)
	 */
	@Override
	public void setY(int y) {
		this.y = (int) (y * getYPadding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#getX()
	 */
	@Override
	public int getX() {
		return (int) (x / getXPadding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.research.gui.graph.GraphNode#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.x = (int) (x * getXPadding());
	}

}
