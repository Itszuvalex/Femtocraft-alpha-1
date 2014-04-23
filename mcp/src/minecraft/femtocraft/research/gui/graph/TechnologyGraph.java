package femtocraft.research.gui.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

import femtocraft.Femtocraft;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.research.gui.GuiResearch;

public class TechnologyGraph {
	private final HashMap<String, ResearchTechnology> technologies;
	private final HashMap<String, GraphNode> nodes;
	private final ArrayList<DummyTechnology> dummies;

	private static final int maxHeight = 20;
	private static final int maxWidth = 20;
	private static final int MAX_HILLCLIMB_ITERATIONS = 5000;
	private static final int MAX_CHILD_X_DIST = 3;
	protected static final int X_PADDING = 1;
	protected static final int Y_PADDING = 2;
	private static final float EMPTY_PADDING_MULTIPLER = 1.00f;
	private static final int MAX_MINIMIZE_PASSES = 10;

	public TechnologyGraph(HashMap<String, ResearchTechnology> technologies) {
		Femtocraft.logger.log(Level.INFO, "Creating Graph of Technologies.");
		this.technologies = technologies;
		nodes = new HashMap<String, GraphNode>();
		dummies = new ArrayList<DummyTechnology>();

		Femtocraft.logger.log(Level.INFO, "Transfering Technologies to Nodes.");
		// Fill nodes dictionary.
		for (String name : technologies.keySet()) {
			nodes.put(name, new GraphNode(technologies.get(name)));
		}

		Femtocraft.logger.log(Level.INFO, "Linking edges.");
		// Add edges
		for (String name : technologies.keySet()) {
			GraphNode node = nodes.get(name);
			ResearchTechnology tech = node.getTech();
			if (tech == null)
				continue;
			if (tech.prerequisites == null)
				continue;
			for (ResearchTechnology prerequisite : tech.prerequisites) {
				GraphNode parent = nodes.get(prerequisite.name);
				if (parent == null)
					continue;
				parent.addChild(node);
				node.addParent(parent);
			}
		}

		for (GraphNode node : nodes.values()) {
			node.pruneParents();
		}
	}

	/**
	 * Performs a variant of the Sugiyama algorithm to find locations for
	 * Technologies.
	 */
	public void computePlacements() {
		Femtocraft.logger
				.log(Level.INFO,
						"Computing Placements of Technologies. This process may take a while on slower computers.");
		computeHeights();

		// Prepare row count for crossing minimization
		int height = greatestHeight() + 1;
		ArrayList<GraphNode>[] rows = new ArrayList[height];
		// Scope out the int array so it goes away when we're finished with it.
		// It's only for
		// assigning temporary x values to nodes.
		{
			int[] rowCount = new int[height];
			Arrays.fill(rowCount, 0);
			for (int i = 0; i < rows.length; ++i) {
				rows[i] = new ArrayList<GraphNode>();
			}
			for (GraphNode node : nodes.values()) {
				rows[node.getY()].add(node);
				node.setX(rowCount[node.getY()]++);
			}
		}

		addDummyNodes(rows);
		// This isn't needed as we can simply expand to be the size of the
		// largest row
		// reduceRows(rows);
		minimizeCrossings(rows);

		GuiResearch.setSize((int) (greatestHeight() * Y_PADDING),
				(int) (greatestWidth() * X_PADDING));

		Femtocraft.logger.log(Level.INFO,
				"Enjoy your custom built tech-tree, made just for you!");
	}

	private void addDummyNodes(ArrayList<GraphNode>[] rows) {
		for (int i = 0; i < rows.length; ++i) {
			for (GraphNode node : rows[i]) {
				ArrayList<GraphNode> remove = new ArrayList<GraphNode>();
				ArrayList<GraphNode> add = new ArrayList<GraphNode>();
				for (GraphNode child : node.getChildren()) {
					int levelDif = Math.abs(node.getY() - child.getY());
					if (levelDif > 1) {
						remove.add(child);
						GraphNode prev = child;
						for (int j = child.getY() - 1; j > node.getY(); --j) {
							GraphNode dummy = new DummyNode();
							int depth = j;
							dummy.setY(depth);
							int x = prev.getX();
							if (x >= rows[j].size()) {
								rows[j].add(dummy);
							} else {
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
				for (GraphNode child : add) {
					child.addParent(node);
				}
				node.getChildren().addAll(add);

				for (GraphNode child : remove) {
					child.getParents().remove(node);
				}
				node.getChildren().removeAll(remove);
			}
		}

		int width = (int) (greatestWidth() * EMPTY_PADDING_MULTIPLER);
		for (int y = 1; y < rows.length; ++y) {
			ArrayList<GraphNode> row = rows[y];
			row.ensureCapacity(width);
			for (int x = row.size(); x < width; ++x) {
				DummyNode dummy = new DummyNode();
				dummy.setY(y);
				row.add(0, dummy);
			}

			for (int k = 0; k < row.size(); ++k) {
				row.get(k).setX(k);
			}
		}
	}

	private void minimizeCrossings(ArrayList<GraphNode>[] rows) {
		Femtocraft.logger.log(Level.INFO, "Minimizing edge crossings for "
				+ rows.length + " rows.");
		for (int pass = 0; pass < MAX_MINIMIZE_PASSES; ++pass) {
			for (int i = 0; i < rows.length - 1; ++i) {
				Femtocraft.logger.log(Level.INFO, "Minimizing " + i + "("
						+ rows[i].size() + ")" + "->" + (i + 1) + "("
						+ rows[i + 1].size() + ")" + ".");
				hillClimb(rows, rows[i], rows[i + 1]);
			}
		}
	}

	private void hillClimb(ArrayList<GraphNode>[] rows,
			ArrayList<GraphNode> row1, ArrayList<GraphNode> row2) {
		int crossings = totalCrossingCount(rows);
		float distance = totalDistance(rows);
		// for (int i = 0; i < MAX_HILLCLIMB_ITERATIONS; ++i) {

		if (row2.size() < 2)
			return;

		for (int i1 = 0; i1 < row2.size(); ++i1) {
			for (int i2 = 0; i2 < row2.size(); ++i2) {
				if (i1 == i2)
					continue;
				// Switch x
				switch_x_pos(row2.get(i1), row2.get(i2));

				// If not better, undo
				int new_cross_count = totalCrossingCount(rows);
				float new_distance = totalDistance(rows);

				if (!heuristics(row2.get(i1), row2.get(i2))) {
					switch_x_pos(row2.get(i1), row2.get(i2));
				} else {
					if (new_cross_count < crossings) {
						crossings = new_cross_count;
						distance = new_distance;
					} else if (new_cross_count == crossings) {
						if (new_distance < distance) {
							distance = new_distance;
						} else {
							switch_x_pos(row2.get(i1), row2.get(i2));
						}
					} else {
						switch_x_pos(row2.get(i1), row2.get(i2));
					}
				}
			}
			// }
		}
	}

	private boolean heuristics(GraphNode node1, GraphNode node2) {
		// if (Math.abs(node1.getX() - node2.getX()) > MAX_CHILD_X_DIST)
		// return false;

		return true;
	}

	private void switch_x_pos(GraphNode node1, GraphNode node2) {
		int prev = node1.getX();
		node1.setX(node2.getX());
		node2.setX(prev);
	}

	private int totalCrossingCount(ArrayList<GraphNode>[] rows) {
		int connections = 0;
		for (int i = 0; i < (rows.length - 1); ++i) {
			connections += crossingCount(rows[i], rows[i + 1]);
		}
		return connections;
	}

	private int crossingCount(ArrayList<GraphNode> row1,
			ArrayList<GraphNode> row2) {
		int connections = 0;

		ArrayList<Integer> x_top = new ArrayList<Integer>();
		ArrayList<Integer> x_bot = new ArrayList<Integer>();

		for (GraphNode node : row1) {
			for (GraphNode child : node.getChildren()) {
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

	boolean isCrossing(int x_top_1, int x_connection_1, int x_top_2,
			int x_connection_2) {
		return ((x_top_1 < x_top_2 && x_connection_1 > x_connection_2) || (x_top_1 > x_top_2 && x_connection_1 < x_connection_2));
	}

	private float totalDistance(ArrayList<GraphNode>[] rows) {
		float distance = 0;
		for (int i = 0; i < (rows.length - 1); ++i) {
			distance += edgeDistance(rows[i], rows[i + 1]);
		}
		return distance;
	}

	private float edgeDistance(ArrayList<GraphNode> row1,
			ArrayList<GraphNode> row2) {
		// We assume dummy nodes have been made, thus all childs of nodes in
		// row1 are in row2.
		float distance = 0;
		for (GraphNode node : row1) {
			for (GraphNode child : node.getChildren()) {
				distance += Math
						.sqrt(Math.pow(child.getX() - node.getX(), 2) + 1);
			}
		}
		return distance;
	}

	private void reduceRows(ArrayList<GraphNode>[] rows) {
		Femtocraft.logger.log(Level.INFO, "Reducing row widths.");
		for (int i = 0; i < rows.length; ++i) {
			ArrayList<GraphNode> row = rows[i];
			int ri = 0;
			while (row.size() > maxWidth && ri++ < row.size()) {
				GraphNode node = row.get(ri);
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
		for (GraphNode node : nodes.values()) {
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

	private int greatestHeight() {
		int greatestHeight = -1;
		for (GraphNode node : nodes.values()) {
			greatestHeight = Math.max(greatestHeight, node.getY());
		}
		return greatestHeight;
	}

	private int greatestWidth() {
		int greatestWidth = -1;
		for (GraphNode node : nodes.values()) {
			greatestWidth = Math.max(greatestWidth, node.getX());
		}
		return greatestWidth;
	}

	public ArrayList<DummyTechnology> getDummyTechs() {
		return dummies;
	}

	public GraphNode getNode(String name) {
		return nodes.get(name);
	}
}
