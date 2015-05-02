import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * BackyardDig determines the network path which minimizes "digging" work.
 * 
 * @author davidwestjr
 *
 */
public final class BackyardDig {

    /**
     * Constructor to keep checkstyle happy.
     */
    private BackyardDig() {
    }

    /**
     * The BackyardDig main method.
     * 
     * @param args
     *            input file including square network size, and set of vertecies
     *            with work (weight) of edge.
     * 
     */
    public static void main(String[] args) {
        // 1. Read the input file
        // 2. Construct graph based on input file
        // 3. Find shortest path for all n vertecies 
        //      -> all pairs shortest path
        // 4. Compare work and choose overall minimized work path
        // 5. Construct output file based on above path
        
        int rows;
        int cols;
        HashMap<Integer, Tuple<Integer, Integer>> map = new
                HashMap<Integer, Tuple<Integer, Integer>>();
        //Creat array list with initial capacity of 0
        ArrayList<Integer> nodesAdded = new ArrayList<Integer>(0);
        AdjacencyListGraph graph = new AdjacencyListGraph(0);
        
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            rows = reader.nextInt();
            cols = reader.nextInt();
            
            while (reader.hasNext()) {
                
                /** Currently this is sensitive to bad inputs.
                 * 
                 * TODO: Write delimeter-based/String-parsing
                 * method to standardize
                 * 
                 */
                int xStart = reader.nextInt();
                int yStart = reader.nextInt();
                int xEnd = reader.nextInt();
                int yEnd = reader.nextInt();
                int weight = reader.nextInt();
                
                int startVertex = xStart * cols + yStart;
                int endVertex = xEnd * cols + yEnd;
                graph.addEdge(startVertex, endVertex, weight);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }   
        
        GraphWrapper<Integer> graphWrap = new GraphWrapper<Integer>(graph);
        LinkedList<LinkedList<Integer>> minPath = graphWrap.shortestPath();
        
        System.out.println("Shortest Path: ");
        ListIterator<LinkedList<Integer>> listIterator = minPath.listIterator();
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        } 
        

    }
    
    
}