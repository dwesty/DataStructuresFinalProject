import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Collections;


/**
 * Sorts an input file based on an ordering determined by a given dictionary. 
 */
public final class CodeSort {
    
    /**
     * For checkstyle compliance.
     */
    private CodeSort() {
    }
    
    /**
     * The main of the CodeSort.
     * @param args Three files as input, the sorted dictionary, 
     * the unsorted file, and the output file.
     */
    public static void main(String[] args) {
        
        /*
         * The process.
         * 1) Load all 3 files.
         * 2) Parse the input dictionary into strings. 
         * 3) Parse the unsorted file into strings.
         * 4) Parse the input graph to build a digraph of letter order.
         * 5) Get a topological ordering from the digraph. 
         * 6) Use the topological ordering to build a comparable wrapper.
         * 7) Merge sort the unsorted file.
         * 8) Store the mergesorted graph in the output text file. 
         */
        
        ArrayList<String> sortedDict = new ArrayList<String>();
        ArrayList<StringWrapper> unsortedList = new ArrayList<StringWrapper>();
        DiGraphWrapper<String> charOrder = 
                new DiGraphWrapper<String>(new AdjacencyListDiGraph(0));
        
        CodeSort cs = new CodeSort();
        
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new File(
                    cs.getClass().getResource("smallSorted.txt").getFile()));
            while (fileScanner.hasNext()) {
                sortedDict.add(fileScanner.next());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }   
       
        int index = 0;
        String a, b;
        
        for (int i = 1; i < sortedDict.size(); i++) {
            
            while (sortedDict.get(i).charAt(index) 
                    == sortedDict.get(i - 1).charAt(index)) {
                index++;
            }
            a = String.valueOf(sortedDict.get(i).charAt(index));
            b = String.valueOf(sortedDict.get(i - 1).charAt(index));
            
            //if a equals b, then one word is longer than the other
            // and every letter they have in common matches. 
            // This gives no knew information, so no new edges. 
            
            if (!a.equals(b)) {
                charOrder.addVertex(a);
                charOrder.addVertex(b);
            
                charOrder.addEdge(a, b);
            }
            
            index = 0;
        }
        
        LinkedList<String> precedence = charOrder.getTopologicalSort();
        
        try {
            fileScanner = new Scanner(new File(
                    cs.getClass().getResource("unsorted1.txt").getFile()));
            while (fileScanner.hasNext()) {
                unsortedList.add(
                        new StringWrapper(fileScanner.next(), precedence));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        
        Collections.sort(unsortedList);
        
        try {
            File file = new File("sorted1.txt");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            
            for (StringWrapper x : unsortedList) {
                output.write(x.codedWord + "\n");
            }
            output.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
        
    }

}
/**
 * Wraps the coded string and provides a compareTo function. 
 *
 */

class StringWrapper implements Comparable<StringWrapper> {
    /**
     * The encoded word being wrapped. 
     */
    String codedWord;
    
    /**
     * The topologically sorted array list used to compare the objects. 
     */
    LinkedList<String> topSorted;
    
    /**
     * Constructor for the Wrapper.
     * @param data The encoded word to be wrapped.
     * @param precedence The list giving letter precedence.
     */
    StringWrapper(String data, LinkedList<String> precedence) {
        this.codedWord = data;
        this.topSorted = precedence;
    }
    
    /**
     * Compares two String Wrappers using the letter precedence found in 
     * topSorted.
     * @param target The coded string to compare against
     * @return -1 if before, 0 if equal, 1 if after.
     */
    public int compareTo(StringWrapper target) {
        
        int index = 0;
        int pos1, pos2;
        
        while (index < this.codedWord.length() 
                && index < target.codedWord.length()) {
            
            //We see which comes first in the precedence list.
            pos1 = this.topSorted.indexOf(
                    String.valueOf(this.codedWord.charAt(index)));
            pos2 = this.topSorted.indexOf(
                    String.valueOf(target.codedWord.charAt(index)));
            
            if (pos1 < pos2) {
                return 1;
            } else if (pos2 < pos1) {
                return -1;
            } else {
                index++;
            }
        }
        
        //This means one or the other of the words was identical 
        // or identical and longer.
        if (this.codedWord.length() < target.codedWord.length()) {
            return 1;
        } else if (this.codedWord.length() > target.codedWord.length()) {
            return -1;
        } else {
            return 0;
        }
        
    }
}

