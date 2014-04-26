package femtocraft.research.gui.graph;

import femtocraft.Femtocraft;
import femtocraft.graph.IGraphNode;
import femtocraft.graph.MapGraph;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.research.gui.GuiResearch;

import java.util.HashMap;
import java.util.logging.Level;

public class TechnologyGraph extends MapGraph<String> {
    public static final int X_PADDING = 1;
    public static final int Y_PADDING = 2;
    private final HashMap<String, ResearchTechnology> technologies;

    public TechnologyGraph(HashMap<String, ResearchTechnology> technologies) {
        super();
        Femtocraft.logger.log(Level.INFO, "Creating Graph of Technologies.");
        this.technologies = technologies;
        HashMap<String, IGraphNode> nodes = new HashMap<String, IGraphNode>();

        Femtocraft.logger.log(Level.INFO, "Transfering Technologies to Nodes.");
        // Fill nodes dictionary.
        for (String name : technologies.keySet()) {
            nodes.put(name, new TechNode(technologies.get(name)));
        }

        Femtocraft.logger.log(Level.INFO, "Linking edges.");
        // Add edges
        for (String name : technologies.keySet()) {
            TechNode node = (TechNode) nodes.get(name);
            ResearchTechnology tech = node.getTech();
            if (tech == null) {
                continue;
            }
            if (tech.prerequisites == null) {
                continue;
            }
            for (ResearchTechnology prerequisite : tech.prerequisites) {
                IGraphNode parent = (IGraphNode) nodes.get(prerequisite.name);
                if (parent == null) {
                    continue;
                }
                parent.addChild(node);
                node.addParent(parent);
            }
        }

        for (IGraphNode node : nodes.values()) {
            ((TechNode) node).pruneParents();
        }

        setNodes(nodes);

        GuiResearch.setSize(greatestWidth() * X_PADDING, greatestHeight()
                * Y_PADDING);
    }

    @Override
    public void computePlacements() {
        super.computePlacements();
        GuiResearch.setSize(greatestWidth() * X_PADDING, greatestHeight()
                * Y_PADDING);
    }
}
