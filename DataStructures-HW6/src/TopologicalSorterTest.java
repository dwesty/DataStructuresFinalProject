import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import java.util.LinkedList;

public class TopologicalSorterTest {

    static AdjacencyListDiGraph graph;
    static DiGraphWrapper<String> wrap;
    
    @Before
    public void setup() {
        graph = new AdjacencyListDiGraph(3);
        
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        
        wrap = new DiGraphWrapper<String>(new AdjacencyListDiGraph(0));
        
        wrap.addVertex("A");
        wrap.addVertex("B");
        wrap.addVertex("C");
        
        wrap.addEdge("A", "B");
        wrap.addEdge("B", "C");
    }

    @Test
    public void getNumVertsTest() {
        assertEquals(graph.getNumVerts(), 3);
    }
    
    @Test
    public void getNeighborsTest() {
        
        assertEquals(graph.getNeighbors(2).size(), 0);
        
        assertNotEquals(graph.getNeighbors(0).size(), 0);
        assertNotEquals(graph.getNeighbors(1).size(), 0);
    }
    
    @Test
    public void getOutdegreeTest() {        

        assertEquals(graph.getOutdegree(0), 1);
        assertEquals(graph.getOutdegree(1), 1);
        assertEquals(graph.getOutdegree(2), 0);
    }
    
    @Test
    public void getIndegreeTest() {        

        assertEquals(graph.getIndegree(0), 0);
        assertEquals(graph.getIndegree(1), 1);
        assertEquals(graph.getIndegree(2), 1);
    }
    
    @Test
    public void removeEdgeTest() {
        graph.removeEdge(0,1);
        
        assertEquals(graph.getOutdegree(0), 0);
        assertEquals(graph.getOutdegree(1), 1);
        assertEquals(graph.getOutdegree(2), 0);
        
        assertEquals(graph.getIndegree(0), 0);
        assertEquals(graph.getIndegree(1), 0);
        assertEquals(graph.getIndegree(2), 1);
        
    }
    
    @Test
    public void addVertexTest() {
        graph.addVertex();
        graph.addVertex();
        
        assertEquals(graph.getNumVerts(), 5);
        assertEquals(graph.getIndegree(3), 0);
        assertEquals(graph.getOutdegree(3), 0);
        assertEquals(graph.getIndegree(4), 0);
        assertEquals(graph.getOutdegree(4), 0);
        assertEquals(graph.getNeighbors(3).size(), 0);
        assertEquals(graph.getNeighbors(4).size(), 0);
    }
    
    @Test
    public void TopOrderingTest1() {
      
        TopologicalSorter sorter = new TopologicalSorter();
        LinkedList<Integer> testList = sorter.sort(graph);
        
        assertEquals(testList.size(), 3);
        assertEquals(testList.get(0), (Integer) 0);
        assertEquals(testList.get(1), (Integer) 1);
        assertEquals(testList.get(2), (Integer) 2);
        
    }

    @Test
    public void TopOrderingTest2() {
        
        graph.removeEdge(0, 1);
        graph.removeEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(1, 2);
  
        TopologicalSorter sorter = new TopologicalSorter();
        LinkedList<Integer> testList = sorter.sort(graph);
    
        assertEquals(testList.size(), 3);
        assertEquals(testList.get(0), (Integer) 1);
        assertEquals(testList.get(1), (Integer) 2);
        assertEquals(testList.get(2), (Integer) 0);
    }
    
    @Test
    public void TopOrderingTest3() {
        
        graph.removeEdge(0, 1);
        graph.removeEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(1, 2);
        
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 4);
        graph.addEdge(4, 3);
        
        TopologicalSorter sorter = new TopologicalSorter();
        LinkedList<Integer> testList = sorter.sort(graph);
     
        /*
        0 -> 4
        1 -> 2
        2 -> 0
        3 -> null
        4 -> 3
        
        Top Ordering: 1 -> 2 -> 0 -> 4 -> 3
         */
        
        assertEquals(testList.size(), 5);
        assertEquals(testList.get(0), (Integer) 1);
        assertEquals(testList.get(1), (Integer) 2);
        assertEquals(testList.get(2), (Integer) 0);
        assertEquals(testList.get(3), (Integer) 4);
        assertEquals(testList.get(4), (Integer) 3);
        
    }
    
    @Test
    public void WrapperGetNumVertsTest() {
        assertEquals(3, wrap.getNumVerts());
    }
    
    @Test
    public void WrapperRemoveEdgeTest() {
        
        wrap.removeEdge("A","B");
        
        assertEquals(wrap.getOutdegree("A"), 0);
        assertEquals(wrap.getOutdegree("B"), 1);
        assertEquals(wrap.getOutdegree("C"), 0);
        
        assertEquals(wrap.getIndegree("A"), 0);
        assertEquals(wrap.getIndegree("B"), 0);
        assertEquals(wrap.getIndegree("C"), 1);
    }
    
    @Test
    public void WrapperAddVertexTest() {
        wrap.addVertex("D");
        wrap.addVertex("E");
        
        assertEquals(wrap.getNumVerts(), 5);
        assertEquals(wrap.getIndegree("D"), 0);
        assertEquals(wrap.getOutdegree("D"), 0);
        assertEquals(wrap.getIndegree("E"), 0);
        assertEquals(wrap.getOutdegree("E"), 0);
        assertEquals(wrap.getNeighbors("D").size(), 0);
        assertEquals(wrap.getNeighbors("E").size(), 0);
    }
    
    @Test
    public void WrapperTopologicalSortTest() {
        
        LinkedList<String> testList = wrap.getTopologicalSort();
        
        assertEquals(testList.size(), 3);
        assertEquals(testList.get(0), "A");
        assertEquals(testList.get(1), "B");
        assertEquals(testList.get(2), "C");
    }
    
    @Test
    public void WrapperTopOrderingTest2() {
        
        wrap.removeEdge("A", "B");
        wrap.removeEdge("B", "C");
        wrap.addEdge("C", "A");
        wrap.addEdge("B", "C");
        
        wrap.addVertex("D");
        wrap.addVertex("E");
        wrap.addEdge("A", "E");
        wrap.addEdge("E", "D");
        
        LinkedList<String> testList = wrap.getTopologicalSort();
     
        /*
        A -> E
        B -> C
        C -> A
        D -> null
        E -> D
        
        Top Ordering: B -> C -> A -> E -> D
         */
        
        assertEquals(testList.size(), 5);
        assertEquals(testList.get(0), "B");
        assertEquals(testList.get(1), "C");
        assertEquals(testList.get(2), "A");
        assertEquals(testList.get(3), "E");
        assertEquals(testList.get(4), "D");
        
    }
    
}