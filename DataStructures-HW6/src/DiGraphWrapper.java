import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A class that maps the name of vtces to the graph representation.
 * 
 * @param <T> the input type to map from. 
 */

public class DiGraphWrapper<T> {

    /**
     * The load factor for the hashmap.
     */
    final float loadFactor = .5f;
    
    /**
     * The map from the input to index in the graph. 
     */
    HashMap<T, Integer> vtcs;
    
    /**
     * An arraylist containing all keys stored in the map.
     */
    ArrayList<T> keys;
    
    /**
     * The graph currently being wrapped. 
     */
    
    AdjacencyListDiGraph graph;
    
    /**
     * Constructor for the DiGraphWrapper.
     * @param inGraph the graph to wrap. 
     */
    DiGraphWrapper(AdjacencyListDiGraph inGraph) {
        this.keys = new ArrayList<T>();
        this.graph = inGraph;
        this.vtcs = new HashMap<T, Integer>(inGraph.getNumVerts() * 2 + 1, 
                this.loadFactor);
    }
    
    /**
     * Adds an element to the graph. 
     * @param element being added to the graph.
     */
    public void addVertex(T element) {
        if (!this.vtcs.containsKey(element)) {
            this.keys.add(element);
            this.vtcs.put(element, this.graph.getNumVerts());
            this.graph.addVertex();
        }
    }
    
    /**
     * Adds an edge between two vertices.
     * @param vtx1 Vertex incident from
     * @param vtx2 Vertex onto
     */
    public void addEdge(T vtx1, T vtx2) {
        this.graph.addEdge(this.vtcs.get(vtx1), this.vtcs.get(vtx2));
    }
    
    /**
     * Removes an edge between two vertices. 
     * @param vtx1 Vertex incident from
     * @param vtx2 Vertex onto
     */
    public void removeEdge(T vtx1, T vtx2) {
        this.graph.removeEdge(this.vtcs.get(vtx1), this.vtcs.get(vtx2));
    }
    
    /**
     * Gives the indegree of a vtx.
     * @param vtx The vertex of which to get the indegree. 
     * @return The indegree of the vertex. 
     */
    public int getIndegree(T vtx) {
        return this.graph.getIndegree(this.vtcs.get(vtx));
    }
    
    /**
     * Gives the outdegree of a vtx.
     * @param vtx The vertex of which to get the outdegree. 
     * @return The outdegree of the vertex. 
     */
    public int getOutdegree(T vtx) {
        return this.graph.getOutdegree(this.vtcs.get(vtx));
    }
    
    /**
     * Gives the number of vertices in the graph currently. 
     * @return The number of vertices. 
     */
    public int getNumVerts() {
        return this.graph.getNumVerts();
    }
    
    /**
     * Returns an arraylist containing the names of the neighbors of 
     * the given vertex.
     * @param vtx The vertex to find the neighbors of.
     * @return An array list containg the names (of type T) of the neighbors vtx
     */
    public ArrayList<T> getNeighbors(T vtx) {
        ArrayList<T> output = new ArrayList<T>();
        ArrayList<Integer> neighbors = 
                this.graph.getNeighbors(this.vtcs.get(vtx));
        
        for (int x : neighbors) {
            output.add(this.keys.get(x));
        }
        
        return output;
    }
    
    /**
     * Returns a topological sort of the graph in terms of type T. 
     * @return a topological sort in terms of T.
     */
    public LinkedList<T> getTopologicalSort() {
        
        TopologicalSorter sorter = new TopologicalSorter();
        
        LinkedList<T> output = new LinkedList<T>();
        LinkedList<Integer> topsort = sorter.sort(this.graph);
        
        for (int x : topsort) {
            output.addLast(this.keys.get(x));
        }
        
        return output;
    }
    
}
