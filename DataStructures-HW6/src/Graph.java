import java.util.List;


public interface Graph<T> {
	
	/**
	 * Add an edge to the graph.
	 * @param <T> type parameter
	 * 
	 * @param start
	 *            the starting node
	 * @param end
	 *            the end node
	 * @param weight
	 *            the weight of the node. Default is 1 (all equal)
	 */
	public void addEdge(T start, T end, int weight);
	
	/**
	 * Removes an edge to the graph.
	 * @param <T> type parameter
	 * 
	 * @param start
	 *            the starting node
	 * @param end
	 *            the end node
	 */
	public void removeEdge(T start, T end);
	
	/**
	 * Gets the weight of specified edge
	 * @param <T> type parameter
	 * 
	 * @param start
	 *            the starting node
	 * @param end
	 *            the end node
	 */
	public int getWeight(T start, T end);
	
	/**
	 * Returns a list of neighbor vertecies
	 * @param <T> type parameter
	 * 
	 * @param node
	 *            the node of interest
	 */
	public List<T> getNeighbors(int node);
}