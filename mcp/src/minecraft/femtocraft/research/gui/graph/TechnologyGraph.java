package femtocraft.research.gui.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

import femtocraft.Femtocraft;
import femtocraft.managers.research.ResearchTechnology;

public class TechnologyGraph {
	private final HashMap<String, ResearchTechnology> technologies;
	private final HashMap<String, GraphNode> nodes;

	private static final int maxHeight = 20;
	private static final int maxWidth = 20;

	public TechnologyGraph(HashMap<String, ResearchTechnology> technologies) {
		Femtocraft.logger.log(Level.INFO, "Creating Graph of Technologies.");
		this.technologies = technologies;
		nodes = new HashMap<String, GraphNode>();

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
	}

	/**
	 * Performs a variant of the Sugiyama algorithm to find locations for
	 * Technologies.
	 */
	public void computePlacements() {
		Femtocraft.logger.log(Level.INFO,
				"Computing Placements of Technologies.");
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

		reduceRows(rows);
		minimizeCrossings(rows);
	}

	private void minimizeCrossings(ArrayList<GraphNode>[] rows) {
		Femtocraft.logger.log(Level.INFO, "Minimizing edge crossings.");

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
}
