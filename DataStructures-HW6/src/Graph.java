import java.util.List;

/**
 * Graph interface with generic nodes.
 * 
 * @author hipstorian
 *
 * @param <T>
 *            Node type.
 */
public interface Graph<T> {
	
	/**
	 * Add an edge to the graph.
	 * 
	 * @param start
	 *            the starting node
	 * @param end
	 *            the end node
	 * @param weight
	 *            the weight of the node. Default is 1 (all equal)
	 */
	void addEdge(T start, T end, int weight);
	
	/**
	 * Removes an edge to the graph.
	 * 
	 * @param start
	 *            the starting node
	 * @param end
	 *            the end node
	 */
	void removeEdge(T start, T end);
	
	/**
	 * (Optional, return dummy if does not apply).
	 * Gets the weight of specified edge.
	 * 
	 * @param start
	 *            the starting node
	 * @param end
	 *            the end node
	 * @return weight of edge
	 */
	int getWeight(T start, T end);
	
	/**
	 * Returns a list of neighbor vertices.
	 * 
	 * @param node
	 *            the node of interest
	 * @return list of neighbors of a node
	 */
	List<T> getNeighbors(int node);
}
