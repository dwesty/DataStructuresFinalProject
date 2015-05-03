import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A class to find MSTs.
 */
public class PrimMST {
    
    /**
     * The graph to find the MST on.
     */
    AdjacencyListGraph graph;
    
    /**
     * Booleans to decide whether a given vertex is explored. 
     */
    Boolean [] found;
    
    /**
     * Integers to represent previous vertices.
     */
    
    Integer [] prev;
    
    /**
     * An array to represent the distance for each vertex.
     */
    
    int [] distance;
    
    /**
     * A MinHeap for storing (distance, vertex) pairs. 
     */
    AdaptableMinHeap lowestDistance;
    
    /**
     * Constructor for DijkstraMinPath. 
     * @param input The graph we're finding MinPaths in.
     */
    PrimMST(AdjacencyListGraph input) {
        this.graph = input;
        
        this.found = new Boolean[input.getNumVerts()];
        this.prev = new Integer[input.getNumVerts()];
        this.distance = new int[input.getNumVerts()];
        
        this.lowestDistance = new AdaptableMinHeap(input.getNumVerts());
        
        for (int i = 0; i < input.getNumVerts(); i++) {
            this.found[i] = false;
            this.prev[i] = null;
            this.distance[i] = Integer.MAX_VALUE;
        }
    }

    /**
     * Gets the MST sourced from the start node using Prim's Algorithm.
     * @param vtx The start vertex. 
     * @return A graph containing the min spanning tree.  
     */
    public AdjacencyListGraph getMST(int vtx) {
        
        this.lowestDistance.add(0, vtx); //start
        LinkedList<Integer> x;
        int weight;
        
        for (int i = 0; i < this.graph.getNumVerts(); i++) {
            if (this.lowestDistance.size() == 0) {
                break;
            }
            
            x = this.lowestDistance.getMin();
            this.found[x.get(1)] = true;
            
            for (int v : this.graph.getNeighbors(x.get(1))) {
                weight = this.graph.getWeight(x.get(1), v);
                if (!this.found[v]) {
                    if (weight < this.distance[v]) {
                        this.distance[v] = weight;
                        this.prev[v] = x.get(1);
                        this.lowestDistance.add(weight, v);
                    }
                }
            }
        }
        
        AdjacencyListGraph output = new AdjacencyListGraph(0);
        
        for (int i = 0; i < this.graph.getNumVerts(); i++) {
            output.addVertex();
        }
    
        for (int i = 0; i < this.graph.getNumVerts(); i++) {
            if (this.prev[i] != null) {
                output.addEdge(i, this.prev[i], 
                        this.graph.getWeight(i, this.prev[i]));
            }
        }
        
        return output;
    }
    
}