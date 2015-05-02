import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * A class that maps the name of vtces to the graph representation.
 * 
 * @param <T> the input type to map from. 
 */

public class GraphWrapper<T> {

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
    
    AdjacencyListGraph graph;
    
    /**
     * Constructor for the GraphWrapper.
     * @param inGraph the graph to wrap. 
     */
    GraphWrapper(AdjacencyListGraph inGraph) {
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
     * @param weight The weight of the edge between the vertices. 
     */
    public void addEdge(T vtx1, T vtx2, int weight) {
        this.graph.addEdge(this.vtcs.get(vtx1), this.vtcs.get(vtx2), weight);
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
     * Gives the degree of a vtx.
     * @param vtx The vertex of which to get the degree. 
     * @return The degree of the vertex. 
     */
    public int getDegree(T vtx) {
        return this.graph.getDegree(this.vtcs.get(vtx));
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
     * Returns the weight between the edges. 
     * @param vtx1 The first vertex.
     * @param vtx2 The second vertex.
     * @return The value of the weight between them. 
     */
    public int getWeight(T vtx1, T vtx2) {
        return this.graph.getWeight(this.vtcs.get(vtx1), this.vtcs.get(vtx2));
    }
    
    /**
     * Finds the overall shortest path.
     * @return arrayList of vertecies in order of lowest
     */
    public LinkedList<LinkedList<Integer>> shortestPath() {
        LinkedList<LinkedList<Integer>> shortestPath =
                new LinkedList<LinkedList<Integer>>();
        int leastWork;
        DijkstraMinPath dijk = new DijkstraMinPath(this.graph);
        
        //for vertices in hashmap
        for (T key: this.vtcs.keySet()) {
            LinkedList<LinkedList<Integer>> curPath =
                    dijk.getMinPathAll(this.vtcs.get(key));
            
            int curWork = 0;
            ListIterator<LinkedList<Integer>> listIterator =
                    curPath.listIterator();
            while (listIterator.hasNext()) {
                int curWeight = listIterator.next().get(2);
                int curDist = listIterator.next().get(3);
                curWork += curWeight * curDist;
            }
            if (curWork < leastWork) {
                shortestPath = curPath;
            }
        }
        return shortestPath;
        
        //   perform Dijkstra (single source)
        
    }
}
