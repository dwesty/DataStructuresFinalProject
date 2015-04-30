import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import java.util.LinkedList;

public class MinHeapTest {

    static MinHeap heap;
    static AdaptableMinHeap heap2;

    
    @Before
    public void setup() {
        heap = new MinHeap(10);
        
        heap.add(10, 0);
        heap.add(20, 1);
        heap.add(30, 2);
        heap.add(25, 3);
        heap.add(15, 4);
        heap.add(5, 5);
        
        heap2 = new AdaptableMinHeap(10);

        heap2.add(10, 0);
        heap2.add(20, 1);
        heap2.add(30, 2);
        heap2.add(25, 3);
        heap2.add(15, 4);
        heap2.add(5, 5);
        
    }
    
    @Test
    public void addTest() {
        assertEquals(5, heap.peek());
        
        heap.add(1, 6);
        
        assertEquals(1, heap.peek());
    }
    
    @Test
    public void sizeTest() {
        assertEquals(6, heap.size());
    }
    
    @Test
    public void getTest() {
        
        LinkedList<Integer> minPair = heap.getMin();
        
        assertEquals(5, (int) minPair.get(0));
        assertEquals(5, (int) minPair.get(1));
        assertEquals(5, heap.size());
        
        minPair = heap.getMin();
        
        assertEquals(10, (int) minPair.get(0));
        assertEquals(0, (int) minPair.get(1));
        assertEquals(4, heap.size());
        
        minPair = heap.getMin();

        
        assertEquals(15, (int) minPair.get(0));
        assertEquals(4, (int) minPair.get(1));
        assertEquals(3, heap.size());
    }
    
    @Test
    public void addTestAdaptable() {
        assertEquals(5, heap2.peek());
        heap2.add(1, 6);
        
        assertEquals(1, heap2.peek());
        assertEquals(0, (int) heap2.map.get(6));
        assertNotEquals(0, (int) heap2.map.get(5));
    }
    
    @Test
    public void sizeTestAdaptable() {
        assertEquals(6, heap2.size());
    }
    
    @Test
    public void getTestAdaptable() {
        
        LinkedList<Integer> minPair = heap2.getMin();
        
        assertEquals(5, (int) minPair.get(0));
        assertEquals(5, (int) minPair.get(1));
        assertEquals(5, heap2.size());
        assertEquals(0,(int) heap2.map.get(0));
        
        minPair = heap2.getMin();
        
        assertEquals(10, (int) minPair.get(0));
        assertEquals(0, (int) minPair.get(1));
        assertEquals(4, heap2.size());
        assertEquals(0, (int) heap2.map.get(4));
        
        minPair = heap2.getMin();

        
        assertEquals(15, (int) minPair.get(0));
        assertEquals(4, (int) minPair.get(1));
        assertEquals(3, heap2.size());
        
        assertEquals(0, (int) heap2.map.get(1));

        minPair = heap2.getMin();
        
        assertEquals(20, (int) minPair.get(0));
        assertEquals(1, (int) minPair.get(1));
        assertEquals(2, heap2.size());
        assertEquals(0, (int) heap2.map.get(3));
        
        minPair = heap2.getMin();
        
        assertEquals(25, (int) minPair.get(0));
        assertEquals(3, (int) minPair.get(1));
        assertEquals(1, heap2.size());
        assertEquals(0, (int) heap2.map.get(2));
        
        minPair = heap2.getMin();
        
        assertEquals(30, (int) minPair.get(0));
        assertEquals(2, (int) minPair.get(1));
        assertEquals(0, heap2.size());
        
        
    }

    @Test
    public void updateTest() {
        
        assertEquals(0, (int) heap2.map.get(5));
        
        heap2.updateDistance(45, 5);
        
        assertEquals((int) heap2.map.get(5), (int) heap2.size() -1 );
        assertEquals(10, heap2.peek());
    }
    
    @Test
    public void dijkstrasTest() {
        
        AdjacencyListGraph graph = new AdjacencyListGraph(0);
        
        for (int i = 0; i < 12; i++) {
            graph.addVertex();
        }
        
        graph.addEdge(0,1,1);
        graph.addEdge(1,2,8);
        graph.addEdge(2,3,4);
        graph.addEdge(3,4,4);
        graph.addEdge(0,4,18);
        
        LinkedList<LinkedList<Integer>> minPath;
        
        DijkstraMinPath pather = new DijkstraMinPath(graph);
           
        minPath = pather.getMinPath(0, 4);
        
        for (LinkedList<Integer> x : minPath) {
            System.out.println("Node: " + x.get(0));
            System.out.println("Edge Weight: " + x.get(1));
            System.out.println("Total Dist: " + x.get(2));
        }
        
    }
    
    @Test
    public void primsMSTTest() {
        
        AdjacencyListGraph graph = new AdjacencyListGraph(0);
        AdjacencyListGraph MST;
        
        for (int i = 0; i < 5; i++) {
            graph.addVertex();
        }
        
        graph.addEdge(0,1,1);
        graph.addEdge(1,2,8);
        graph.addEdge(2,3,4);
        graph.addEdge(3,4,4);
        graph.addEdge(0,4,18);
        
        PrimMST primmer = new PrimMST(graph);
        
        MST = primmer.getMST(0);
        
        for (int i = 0; i < MST.getNumVerts(); i++) {
            for (int x : MST.getNeighbors(i)) {
                System.out.println("Vertex " + i + " is connect to " + x + ".");
            }
        }
        
        
    }
    
}
