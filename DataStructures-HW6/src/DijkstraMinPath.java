import java.util.LinkedList;
import java.util.ArrayList;

/**
 * A class to find shortest weight paths.
 */
public class DijkstraMinPath {
    
    /**
     * The graph to find the MinPath on.
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
    DijkstraMinPath(AdjacencyListGraph input) {
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
     * Gets the shortest path between the vertices using Dijkstra's Algorithm.
     * O(MlogN)
     * @param vtx1 The start vertex. 
     * @param vtx2 The target vertex. 
     * @return A linked list containing all 
     * (vertex, distance) pairs in the path.
     */
    public LinkedList<LinkedList<Integer>> getMinPath(int vtx1, int vtx2) {
        
        LinkedList<LinkedList<Integer>> output = 
                new LinkedList<LinkedList<Integer>>();
        
        this.lowestDistance.add(0, vtx1); //start
        LinkedList<Integer> x, temp;
        ArrayList<Integer> neighbors;
        int dist;
        
        for (int i = 0; i < this.graph.getNumVerts(); i++) {
            
            x = this.lowestDistance.getMin();
            this.found[x.get(1)] = true;
            
            for (int j : this.graph.getNeighbors(x.get(1))) {
                
            }
            
            
        }
        
        
        
        int vtx = vtx2;
        while (vtx != vtx1) {
            temp = new LinkedList<Integer>();
            temp.addLast(vtx);
            temp.addLast(this.graph.getWeight(this.prev[vtx], vtx));
            temp.addLast(this.distance[vtx]);
            output.addFirst(temp);
            
            vtx = this.prev[vtx];
        }
        temp = new LinkedList<Integer>();
        temp.addLast(vtx);
        temp.addLast(0);
        temp.addLast(0);
        output.addFirst(temp);
        
        return output;
    }
    
}
