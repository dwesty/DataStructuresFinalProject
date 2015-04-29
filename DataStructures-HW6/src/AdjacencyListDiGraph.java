import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * An adjacency list implementation of the graph interface.
 */

public class AdjacencyListDiGraph {
    
    /**
     * Arrays used to represent indegree.
     */
    
    ArrayList<Integer> indegree; 
    
    /**
     * Arrays used to represent outdegree.
     */
    ArrayList<Integer> outdegree;
    
    /**
     * The adjacency list used to describe the graph.
     */
    
    ArrayList<LinkedList<Integer>> graph;
    
    /**
     * The number of vertices in the graph.
     */
    int numVerts;
    
   
    /**
     * Takes a size as input, and creates a graph of that size.
     * 
     * @param size the size of the graph to make.
     */
    AdjacencyListDiGraph(int size) {
        this.graph = new ArrayList<LinkedList<Integer>>();
        this.indegree = new ArrayList<Integer>();
        this.outdegree = new ArrayList<Integer>();
        this.numVerts = size;
        
        for (int i = 0; i < size; i++) {
            this.graph.add(i, new LinkedList<Integer>());
            this.indegree.add(i, 0);
            this.outdegree.add(i, 0);
        }
    }
    
    /**
     * Get the number of vertices in the graph. 
     * @return The number of vertices in the graph.
     */
    public int getNumVerts() {
        return this.numVerts;
    }
    
    /**
     * Adds an edge from vtx1 to vtx2.
     * Adjusts indegree and outdegree appropriately.
     * 
     * @param vtx1 The vtx the edge is incident from. 
     * @param vtx2 The vtx the edge is onto.
     */
    public void addEdge(int vtx1, int vtx2) {
        this.graph.get(vtx1).addLast(vtx2);
        this.outdegree.set(vtx1, this.outdegree.get(vtx1) + 1);
        this.indegree.set(vtx2, this.indegree.get(vtx2) + 1);

    }
    
    /**
     * Removes an edge from vtx1 to vtx2.
     * Adjusts indegree and outdegree appropriately.
     * 
     * @param vtx1 The vtx the edge is incident from. 
     * @param vtx2 The vtx the edge is onto.
     */
    public void removeEdge(int vtx1, int vtx2) {
        
        this.graph.get(vtx1).removeFirstOccurrence(vtx2);
        
        this.outdegree.set(vtx1, this.outdegree.get(vtx1) - 1);
        this.indegree.set(vtx2, this.indegree.get(vtx2) - 1);
    }
    
    /**
     * Returns an arrayList containing all vtcs reachable from the given vtx.
     * @param vtx The vtx in question.
     * @return An array list containing all vtcs reachable 
     */
    public ArrayList<Integer> getNeighbors(int vtx) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        Iterator<Integer> it = this.graph.get(vtx).iterator();
        while (it.hasNext()) {
            temp.add(it.next());
        }
        return temp;
    }
    /**
     * Gives the indegree of a given vertex.
     * @param vtx The vtx to get the indegree of.
     * @return The indegree of the given vtx.
     */
    public int getIndegree(int vtx) {
        return this.indegree.get(vtx);
    }
    
    /**
     * Gives the outdegree of a given vertex.
     * @param vtx The vtx to get the outdegree of.
     * @return The outdegree of the given vtx.
     */
    public int getOutdegree(int vtx) {
        return this.outdegree.get(vtx);
    }
    
    /**
     * Adds an additional vtx to the graph.
     */
    
    public void addVertex() {
        this.graph.add(new LinkedList<Integer>());
        this.indegree.add(0);
        this.outdegree.add(0);
        this.numVerts += 1;
    }
}
