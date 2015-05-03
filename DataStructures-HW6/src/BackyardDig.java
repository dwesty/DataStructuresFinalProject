import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
     * @throws IOException 
     * 
     */
    public static void main(String[] args) throws IOException {
        // 1. Read the input file
        // 2. Construct graph based on input file
        // 3. Find shortest path for all n vertecies 
        //      -> all pairs shortest path
        // 4. Compare work and choose overall minimized work path
        // 5. Construct output file based on above path
        
        
        int rows = 0;
        int cols = 0;
        
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
        System.out.println(bestMST.getAdjList());
        
        FileWriter writer = new FileWriter("OutputTest.txt");
        BufferedWriter bw = new BufferedWriter(writer);
        
        ArrayList<LinkedList<Integer>> tempAdjList = bestMST.getAdjList();
        
        int v = 0;
        for (LinkedList<Integer> adjVtcs : tempAdjList) {
            for (int i = 0; i < adjVtcs.size(); i++) {
                if (tempAdjList.get(adjVtcs.get(i))
                        != null) {
                    tempAdjList.get(adjVtcs.get(i)).remove();
                }
                int xStart = v / cols - 1;
                int yStart = (v % cols);
                int xEnd = adjVtcs.get(i) / cols - 1;
                int yEnd = (adjVtcs.get(i) % cols);
                
                try {
                    String first =
                            new String("(" + xStart + ", " + yStart + ")");
                    String second = new String("(" + xEnd + ", " + yEnd + ")");
                    System.out.print(first);
                    System.out.println(second);
                    bw.write(first);
                    bw.write(second);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            v++;
        }
        
    }
    
    
}