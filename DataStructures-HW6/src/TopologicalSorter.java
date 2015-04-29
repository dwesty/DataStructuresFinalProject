import java.util.LinkedList;

/**
 * A utility class designed to sort a DiGraph.
 */
public class TopologicalSorter {
    
    /**
     * Returns a topological sort of the given digraph. 
     * If no topological sorting exists, will return null.  
     * @param graph The graph to sort.
     * @return A topological sorting if one exists, or null. 
     */
    public LinkedList<Integer> sort(AdjacencyListDiGraph graph) {
        
        LinkedList<Integer> output = new LinkedList<Integer>();
        LinkedList<Integer> q = new LinkedList<Integer>();
        
        //Find the initial item to enqueue.
        for (int i = 0; i < graph.getNumVerts(); i++) {
            if (graph.getIndegree(i) == 0) {
                q.addLast(i);
            }
        }
        
        int currVtx;
        
        while (q.size() > 0) {
            currVtx = q.removeFirst();
            output.addLast(currVtx);
            
            for (int vtx : graph.getNeighbors(currVtx)) {
                if (graph.getIndegree(vtx) == 1) {
                    q.addLast(vtx);
                }
                graph.removeEdge(currVtx, vtx);
            }
        }
        return output;
    }
    
}
