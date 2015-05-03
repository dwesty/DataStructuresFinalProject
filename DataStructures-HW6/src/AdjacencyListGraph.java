import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * An adjacency list implementation of the graph interface.
 */

public class AdjacencyListGraph {

    /**
     * An array list to hold the degree of each vertex.
     */
    ArrayList<Integer> degree;

    /**
     * The adjacency list used to describe the graph.
     */
    ArrayList<LinkedList<Integer>> graph;

    /**
     * A parallel adjacency list if the graph has weights.
     */
    ArrayList<LinkedList<Integer>> weights;

    /**
     * The number of vertices in the graph.
     */
    int numVerts;

    /**
     * Takes a size as input, and creates a graph of that size.
     * 
     * @param size
     *            the size of the graph to make.
     */
    AdjacencyListGraph(int size) {
        this.graph = new ArrayList<LinkedList<Integer>>();
        this.weights = new ArrayList<LinkedList<Integer>>();
        this.degree = new ArrayList<Integer>();
        this.numVerts = size;

        for (int i = 0; i < size; i++) {
            this.graph.add(i, new LinkedList<Integer>());
            this.weights.add(i, new LinkedList<Integer>());
            this.degree.add(i, 0);
        }
    }

    /**
     * Get the number of vertices in the graph.
     * 
     * @return The number of vertices in the graph.
     */
    public int getNumVerts() {
        return this.numVerts;
    }

    /**
     * Adds an edge from vtx1 to vtx2. Adjusts the degree appropriately.
     * 
     * @param vtx1
     *            The vtx the edge is incident from.
     * @param vtx2
     *            The vtx the edge is onto.
     * @param weight
     *            The weight of the edge.
     */
    public void addEdge(int vtx1, int vtx2, int weight) {
        this.graph.get(vtx1).addLast(vtx2);
        this.graph.get(vtx2).addLast(vtx1);

        this.weights.get(vtx1).addLast(weight);
        this.weights.get(vtx2).addLast(weight);

        this.degree.set(vtx1, this.degree.get(vtx1) + 1);
        this.degree.set(vtx2, this.degree.get(vtx2) + 1);

    }

    /**
     * Removes an edge from vtx1 to vtx2. Adjusts degree appropriately.
     * 
     * @param vtx1
     *            The vtx the edge is incident from.
     * @param vtx2
     *            The vtx the edge is onto.
     */
    public void removeEdge(int vtx1, int vtx2) {

        int pos1 = this.graph.get(vtx1).indexOf(vtx2);
        int pos2 = this.graph.get(vtx2).indexOf(vtx1);

        this.graph.get(vtx1).remove(pos1);
        this.weights.get(vtx1).remove(pos1);

        this.graph.get(vtx2).remove(pos2);
        this.weights.get(vtx2).remove(pos2);

        this.degree.set(vtx1, this.degree.get(vtx1) - 1);
        this.degree.set(vtx2, this.degree.get(vtx2) - 1);
    }

    /**
     * Returns an arrayList containing all vtcs reachable from the given vtx.
     * 
     * @param vtx
     *            The vtx in question.
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
     * Gives the degree of a given vertex.
     * 
     * @param vtx
     *            The vtx to get the degree of.
     * @return The degree of the given vtx.
     */
    public int getDegree(int vtx) {
        return this.degree.get(vtx);
    }

    /**
     * Returns the weight of the edge between the given vertices.
     * 
     * @param vtx1
     *            The first vertex.
     * @param vtx2
     *            The second vertex.
     * @return The value of the weight between the edges.
     */

    public int getWeight(int vtx1, int vtx2) {
        int pos = this.graph.get(vtx1).indexOf(vtx2);
        return this.weights.get(vtx1).get(pos);
    }

    /**
     * Adds an additional vtx to the graph.
     */

    public void addVertex() {
        this.graph.add(new LinkedList<Integer>());
        this.weights.add(new LinkedList<Integer>());
        this.degree.add(0);
        this.numVerts += 1;
    }

    /**
     * Returns the adjacency list representing the graph.
     * 
     * @return the adjacency list.
     */
    public ArrayList<LinkedList<Integer>> getAdjList() {
        return this.graph;
    }

    /**
     * Returns the total summed weight of the network.
     * 
     * @return the adjacency list.
     */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (LinkedList<Integer> adjVtcs : this.graph) {
            ListIterator<Integer> listIterator
                = adjVtcs.listIterator();
            int curWeight = this.getWeight(
                    this.graph.indexOf(adjVtcs), listIterator.next());
            totalWeight += curWeight;
        }
        return totalWeight / 2; //Might need to change 
    }
}
