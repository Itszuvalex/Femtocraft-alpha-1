package femtocraft.graph;

import java.util.Collection;
import java.util.Map;

public abstract class MapGraph<K> extends Graph {
	private Map<K, IGraphNode> node_map;

	public MapGraph() {
		super();
		this.node_map = null;
	}

	public MapGraph(Map<K, IGraphNode> nodes) {
		super();
		setNodes(nodes);
	}

	@Override
	public Collection<IGraphNode> getNodes() {
		return node_map.values();
	}

	public void setNodes(Map<K, IGraphNode> nodes) {
		this.node_map = nodes;
	}

	public IGraphNode getNode(K key) {
		return node_map.get(key);
	}
}
