import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import java.util.LinkedList;

public class GraphTest {

    static AdjacencyListGraph graph;
    static GraphWrapper<String> wrap;
    
    @Before
    public void setup() {
        graph = new AdjacencyListGraph(3);
        
        graph.addEdge(0, 1, 0);
        graph.addEdge(1, 2, 0);
        
        wrap = new GraphWrapper<String>(new AdjacencyListGraph(0));
        
        wrap.addVertex("A");
        wrap.addVertex("B");
        wrap.addVertex("C");
        
        wrap.addEdge("A", "B", 0);
        wrap.addEdge("B", "C", 0);
    }

    @Test
    public void getWeightTest() {
        
        assertEquals(graph.getWeight(0, 1), 0);
        assertEquals(graph.getWeight(1, 2), 0);
        
        
        
    }
    
    @Test
    public void wrapGetWeightTest() {
        
        assertEquals(wrap.getWeight("A", "B"), 0);
        assertEquals(wrap.getWeight("B", "C"), 0);
        
        
        
    }
    
    @Test
    public void getNumVertsTest() {
        assertEquals(graph.getNumVerts(), 3);
    }
    
    @Test
    public void getNeighborsTest() {
        
        assertEquals(graph.getNeighbors(2).size(), 1);
        assertEquals(graph.getNeighbors(0).size(), 1);
        assertEquals(graph.getNeighbors(1).size(), 2);
    }
    
    @Test
    public void getDegreeTest() {        

        assertEquals(graph.getDegree(0), 1);
        assertEquals(graph.getDegree(1), 2);
        assertEquals(graph.getDegree(2), 1);
    }
    
    @Test
    public void removeEdgeTest() {
        graph.removeEdge(0,1);
        
        assertEquals(graph.getDegree(0), 0);
        assertEquals(graph.getDegree(1), 1);
        assertEquals(graph.getDegree(2), 1);
        
    }
    
    @Test
    public void addVertexTest() {
        graph.addVertex();
        graph.addVertex();
        
        assertEquals(graph.getNumVerts(), 5);
        assertEquals(graph.getDegree(3), 0);
        assertEquals(graph.getDegree(3), 0);
        assertEquals(graph.getNeighbors(3).size(), 0);
        assertEquals(graph.getNeighbors(4).size(), 0);
    }
 
    @Test
    public void wrapAddVertexTest() {
        wrap.addVertex("D");
        wrap.addVertex("E");
        
        assertEquals(wrap.getNumVerts(), 5);
        assertEquals(wrap.getDegree("D"), 0);
        assertEquals(wrap.getDegree("E"), 0);
        assertEquals(wrap.getNeighbors("D").size(), 0);
        assertEquals(wrap.getNeighbors("E").size(), 0);
    }
    
    @Test 
    public void wrapGetDegreeTest() {
        assertEquals(wrap.getDegree("A"), 1);
        assertEquals(wrap.getDegree("B"), 2);
        assertEquals(wrap.getDegree("C"), 1);
    }
    
    @Test
    public void wrapGetNeighborsTest() {
        
        assertEquals(wrap.getNeighbors("C").size(), 1);
        assertEquals(wrap.getNeighbors("A").size(), 1);
        assertEquals(wrap.getNeighbors("B").size(), 2);
    }
    
    @Test
    public void wrapGetNumVertsTest() {
        assertEquals(wrap.getNumVerts(), 3);

    }
    
    @Test
    public void wrapRemoveEdgeTest() {
        wrap.removeEdge("A","B");
        
        assertEquals(wrap.getDegree("A"), 0);
        assertEquals(wrap.getDegree("B"), 1);
        assertEquals(wrap.getDegree("C"), 1);
    }
    
}