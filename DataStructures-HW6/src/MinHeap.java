import java.util.LinkedList;

/**
 * Creates a MinHeap of constant size
 * this is okay in this problem domain, as all graphs
 * have n maximum verts, so the heap will never need to resize.
 * 
 * Vtcs are stored in this min heap as well to allow 
 * min distance, vertex pairs. 
 */

public class MinHeap {

    /**
     * Stores the heap.
     */
    int []heap;
    
    /**
     * Stores the associated verts.
     */
    int []verts;
    
    /**
     * The current size of the heap.
     */
    
    int currSize;
    
    /**
     * Creates a new MinHeap of size, size.
     * @param size The max size of the heap.
     */
    MinHeap(int size) {
        this.heap = new int[size];
        this.verts = new int[size];
        
        //I'll be honest. Kind of a hack. 
        for (int i = 0; i < size; i++) {
            this.heap[i] = Integer.MAX_VALUE;
        }
        
        this.currSize = 0;
    }
    
    /**
     * Returns the value of the top element of the 
     * heap but does not remove it. 
     * @return The value of the min element of the heap.
     */
    public int peek() {
        return this.heap[0];
    }
    
    /**
     * Returns the current size of the heap.
     * @return The size of the heap.
     */
    public int size() {
        return this.currSize;
    }
    /**
     * Adds a new element to the heap.
     * The resulting heap must obey the heap order property.
     * @param element The number to be added to the heap. 
     * @param vtx The corresponding vertex that goes with value.
     */
    public void add(int element, int vtx) {
        
        this.heap[this.size()] = element;
        this.verts[this.size()] = vtx;
        
        int index = this.size();
        int temp, temp2;
        this.currSize++;

        //Bubble up.
        while (this.heap[this.getParent(index)] > element) {
            temp = this.heap[this.getParent(index)];
            temp2 = this.verts[this.getParent(index)];
            
            this.heap[this.getParent(index)] = element;
            this.verts[this.getParent(index)] = vtx;
            
            this.heap[index] = temp;
            this.verts[index] = temp2;
            
            index = this.getParent(index);
        }
    }
    /**
     * Returns the min value of the heap.
     * Modifies the resulting heap so that it satisfies heap property. 
     * @return The min value of the heap.
     */
    public LinkedList<Integer> getMin() {
        
        LinkedList<Integer> minPair = new LinkedList<Integer>();
        minPair.addLast(this.heap[0]);
        minPair.addLast(this.verts[0]);
        
        
        this.heap[0] = this.heap[this.getLastPos()];
        this.verts[0] = this.heap[this.getLastPos()];
        
        int index = 0;
        int temp, temp2;
      
        /*
        if (this.getLeftChild(index) + 1 >= this.size()) {
            if (this.heap[index] > this.heap[this.getLeftChild(index)]) {
                temp = this.heap[index];
                this.heap[index = this.heap[this.g]]
            }
            this.currSize--;
            return min;
        }
        */
        
        //Bubble down
        while (this.heap[index] > this.heap[this.getLeftChild(index)] 
                || this.heap[index] > this.heap[this.getLeftChild(index) + 1]) {
            if (this.heap[this.getLeftChild(index)] 
                    <= this.heap[this.getLeftChild(index) + 1]) {
                
                temp = this.heap[index];
                temp2 = this.verts[index];
                
                this.heap[index] = this.heap[this.getLeftChild(index)];
                this.verts[index] = this.verts[this.getLeftChild(index)];
                
                this.heap[this.getLeftChild(index)] = temp;
                this.verts[this.getLeftChild(index)] = temp2;
                
                index = this.getLeftChild(index);
                if (index >= this.size()) {
                    break;
                }
            } else {
                temp = this.heap[index];
                temp2 = this.verts[index];

                this.heap[index] = this.heap[this.getLeftChild(index) + 1];
                this.verts[index] = this.verts[this.getLeftChild(index) + 1];

                this.heap[this.getLeftChild(index) + 1] = temp;
                this.verts[this.getLeftChild(index) + 1] = temp2;

                
                index = this.getLeftChild(index) + 1;
                if (index >= this.size()) {
                    break;
                }
            }
        }
        
        this.currSize--;
        return minPair;
    }
    
    /**
     * Gives the index of the parent node for the given node.
     * @param pos The given node. 
     * @return The index of its parent. 
     */
    private int getParent(int pos) {
        return (int) Math.floor((pos - 1) / 2);
    }
    
    /**
     * Gives the index of the left child node for the given node.
     * @param pos The given node.
     * @return The index of its parent.
     */
    private int getLeftChild(int pos) {
        return ((2 * pos) + 1);
    }
    /**
     * Returns the position of the last node in the heap.
     * @return The position of the last node in the heap.
     */
    private int getLastPos() {
        return this.size() - 1;
    }
}
