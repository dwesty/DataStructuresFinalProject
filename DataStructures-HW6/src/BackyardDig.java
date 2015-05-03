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
        HashMap<Integer, Integer> vtcs = new HashMap<Integer, Integer>();
        GraphWrapper<Integer> graph =
                new GraphWrapper<Integer>(new AdjacencyListGraph(0));
        
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
                String first = reader.next();
                int xStart = Integer.parseInt(first.substring(1, 2));
                int yStart = Integer.parseInt(first.substring(3, 4));
                String second = reader.next();
                int xEnd = Integer.parseInt(second.substring(1, 2));
                int yEnd = Integer.parseInt(second.substring(3, 4));
                int weight = reader.nextInt();
                
                Integer startVertex = xStart * cols + (yStart + 1);
                Integer endVertex = xEnd * cols + (yEnd + 1);
                vtcs.put(startVertex.hashCode(), startVertex);
                vtcs.put(endVertex.hashCode(), endVertex);
//                System.out.println(startVertex + ", " + endVertex);
                
                graph.addVertex(startVertex);
                graph.addVertex(endVertex);
                graph.addEdge(startVertex, endVertex, weight);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }   
        
        AdjacencyListGraph bestMST = null;
        Integer minWeight = Integer.MAX_VALUE;
        
        //find the best MST of all starting points
        for (int vtx : vtcs.values()) {
            PrimMST calcMST = new PrimMST(graph.getGraph());
            AdjacencyListGraph mst = calcMST.getMST(vtx - 1);
            Integer curWeight = mst.getTotalWeight();
            if (curWeight.compareTo(minWeight) < 0) {
                minWeight = curWeight;
                bestMST = mst;
            }
        }
        
        System.out.println("Least Work: " + minWeight);
        for (LinkedList<Integer> adjVtcs : bestMST) {
            ListIterator<Integer> listIterator
                = adjVtcs.listIterator();
        System.out.println(bestMST.getAdjList());
        
        
//        LinkedList<LinkedList<Integer>> minPath = graph.shortestPath();
//        
//        System.out.println("Shortest Path: ");
//        ListIterator<LinkedList<Integer>> listIterator = minPath.listIterator();
//        int work = listIterator.next().get(2);
//        System.out.println(work);
//        while (listIterator.hasNext()) {
//            int firstPoint = listIterator.next().get(0);
//            int nextPoint = listIterator.next().get(3);
//            System.out.println(firstPoint + " " + nextPoint);
//        } 
    }
    
    
}