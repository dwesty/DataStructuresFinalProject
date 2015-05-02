import java.util.LinkedList;
import java.util.Stack;

/**
 * Travelling Salesman Problem
 * Finds path that hits all nodes
 * @author davidwestjr
 *
 */
public class TSP {
    
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
     * LinkedList for storing path. 
     */
    Stack<Integer> path;
    
    /**
     * Constructor for TSP. 
     * @param input The graph we're finding path in.
     */
    TSP(AdjacencyListGraph input) {
        this.graph = input;
        
        this.found = new Boolean[input.getNumVerts()];
        this.prev = new Integer[input.getNumVerts()];
        this.distance = new int[input.getNumVerts()];
        
        this.path = new Stack<Integer>();
        
        for (int i = 0; i < input.getNumVerts(); i++) {
            this.found[i] = false;
            this.prev[i] = null;
            this.distance[i] = Integer.MAX_VALUE;
        }
    }

    /**
     * Nearest Neighbors heuristic for TSP problem.
     * @param vtx1 The start vertex. 
     * @return A linked list containing all 
     * (vertex, distance) pairs in the path.
     */
    public LinkedList<LinkedList<Integer>> nearestNeighbors(int vtx1) {
        
        LinkedList<LinkedList<Integer>> output = 
                new LinkedList<LinkedList<Integer>>();
        
        int curVertex = vtx1; //start
        int dist = 0;
        LinkedList<Integer> firstVtx = new LinkedList<Integer>();
        firstVtx.addLast(vtx1); //vertex
        firstVtx.addLast(0); // weight
        firstVtx.addLast(dist); //total distance
        output.addFirst(firstVtx);
        
        
        // keep going til all vertecies visited
        /**
         * TODO: Check this is actually right
         */
        for (int i = 0; i <= this.graph.getNumVerts(); i++) {
            
            LinkedList<Integer> pathVtx = new LinkedList<Integer>();
            
            Integer minWeight = Integer.MAX_VALUE;
            int minVtx = 0;
            // find the shortest path from the current vertex
            for (int v : this.graph.getNeighbors(curVertex)) {
                
                //only consider unvisited vertices
                if (!this.found[v]) {
                    //compare the lowest weight from this 
                    Integer tempWeight = this.graph.getWeight(curVertex, v);
                    if (tempWeight < minWeight) {
                        minWeight = tempWeight;
                        minVtx = v;
                    }
                    dist += minWeight;
                }
                pathVtx.addLast(minVtx);
                pathVtx.addLast(minWeight);
                pathVtx.addLast(dist); //the total distance to that point
            }
            output.addFirst(pathVtx);
        }
        
        return output;
    }
}