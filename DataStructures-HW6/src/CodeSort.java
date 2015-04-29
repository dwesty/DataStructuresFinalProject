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
        
    }

}
