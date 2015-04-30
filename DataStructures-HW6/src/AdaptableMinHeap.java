import java.util.LinkedList;
import java.util.HashMap;

/**
 * Uses a HashMap to make an Adaptable MinHeap.
 *
 */
public class AdaptableMinHeap {
    
    /**
     * Loadfactor.
     */
    final float loadFactor = .5f;
    
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
     * The HashMap between distances and their position in the heap. 
     */
    HashMap<Integer, Integer> map;
    
    /**
     * Creates a new MinHeap of size, size.
     * @param size The max size of the heap.
     */
    AdaptableMinHeap(int size) {
        this.heap = new int[size];
        this.verts = new int[size];
        this.map = new HashMap<Integer, Integer>(size * 2, this.loadFactor);
        
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
                
        if (this.map.containsKey(vtx)) {
            this.updateDistance(element, vtx);
            return;
        }
                
        this.heap[this.size()] = element;
        this.verts[this.size()] = vtx;
        this.map.put(vtx, this.size());
        
        int index = this.size();
        int temp, temp2;
        this.currSize++;

        //Bubble up.
        while (this.heap[this.getParent(index)] > element) {
            temp = this.heap[this.getParent(index)];
            temp2 = this.verts[this.getParent(index)];
            
            this.heap[this.getParent(index)] = element;
            this.verts[this.getParent(index)] = vtx;
            this.map.put(vtx, this.getParent(index));
            
            this.heap[index] = temp;
            this.verts[index] = temp2;
            this.map.put(temp2, index);
            
            index = this.getParent(index);
        }
    }
    /**
     * Bubbles down a vertex to its appropriate position. 
     * @param pos The vertext to bubble. 
     */
    private void bubbleDown(int pos) {
        int childPos = this.getLeftChild(pos);
        int temp, temp2;
        
        if (childPos + 1 > this.size()) {
            return;
        } else if (this.heap[pos] < this.heap[childPos]
                && this.heap[pos] < this.heap[childPos + 1]) {
            return;
        } else {
            if (this.heap[childPos] < this.heap[childPos + 1]) {
                temp = this.heap[pos]; 
                temp2 = this.verts[pos];
                
                this.heap[pos] = this.heap[childPos];
                this.verts[pos] = this.verts[childPos];
                
                this.heap[childPos] = temp;
                this.verts[childPos] = temp2;
                
                this.bubbleDown(childPos);
                
                this.map.put(this.verts[pos], pos);
                
            } else {
                childPos = childPos + 1;
                
                temp = this.heap[pos];
                temp2 = this.verts[pos];
                
                this.heap[pos] = this.heap[childPos];
                this.verts[pos] = this.verts[childPos];
                
                this.heap[childPos] = temp;
                this.verts[childPos] = temp2;
                
                this.bubbleDown(childPos);
                
                this.map.put(this.verts[pos], pos);

            }
        }
    }
    
    /**
     * Returns the min value of the heap.
     * Modifies the resulting heap so that it satisfies heap property. 
     * @return The min value of the heap.
     */
    public LinkedList<Integer> getMin() {
        
        if (this.heap[0] == Integer.MAX_VALUE) {
            return null;
        }
        
        LinkedList<Integer> minPair = new LinkedList<Integer>();
        minPair.addLast(this.heap[0]);
        minPair.addLast(this.verts[0]);
        this.map.remove(this.verts[0]);
        
        
        this.heap[0] = this.heap[this.getLastPos()];
        this.verts[0] = this.verts[this.getLastPos()];
        this.map.put(this.verts[0],  0);
        
        this.heap[this.getLastPos()] = Integer.MAX_VALUE;
        this.currSize--;

        this.bubbleDown(0);
        
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
        if (this.size() > 0) {
            return this.size() - 1;
        } else {
            return 0;
        }
    }
    
    /**
     * If a distance is updated, changes the heap and bubbles it up or down.
     * @param newDistance The new distance. 
     * @param vertex the vertex whose distance needs changing. 
     */
    public void updateDistance(int newDistance, int vertex) {
        
        if (!this.map.containsKey(vertex)) {
            return;
        }
                
        int index = this.map.get(vertex);        
        this.heap[index] = newDistance;
        
        int temp, temp2;
        
        //Bubble up.
        while (this.heap[this.getParent(index)] > newDistance) {
            temp = this.heap[this.getParent(index)];
            temp2 = this.verts[this.getParent(index)];
        
            this.heap[this.getParent(index)] = newDistance;
            this.verts[this.getParent(index)] = vertex;
            this.map.put(vertex, this.getParent(index));
        
            this.heap[index] = temp;
            this.verts[index] = temp2;
            this.map.put(temp2, index);
        
            index = this.getParent(index);
        }
        
        if (this.getLeftChild(index) >= this.size() 
                || this.getLeftChild(index) + 1 > this.size()) {
            return;
        }
        
        //Bubble Down
        while (this.heap[index] > this.heap[this.getLeftChild(index)] 
                || this.heap[index] > this.heap[this.getLeftChild(index) + 1]) {
            if (this.heap[this.getLeftChild(index)] 
                    <= this.heap[this.getLeftChild(index) + 1]) {
                
                temp = this.heap[index];
                temp2 = this.verts[index];
                
                this.heap[index] = this.heap[this.getLeftChild(index)];
                this.verts[index] = this.verts[this.getLeftChild(index)];
                this.map.put(this.verts[index], index);
                
                this.heap[this.getLeftChild(index)] = temp;
                this.verts[this.getLeftChild(index)] = temp2;
                this.map.put(temp2, this.getLeftChild(index));
                
                index = this.getLeftChild(index);
                
                if (this.getLeftChild(index) + 1 > this.size()) {
                    return;
                }
            } else {
                temp = this.heap[index];
                temp2 = this.verts[index];

                this.heap[index] = this.heap[this.getLeftChild(index) + 1];
                this.verts[index] = this.verts[this.getLeftChild(index) + 1];
                this.map.put(this.verts[index], index);


                this.heap[this.getLeftChild(index) + 1] = temp;
                this.verts[this.getLeftChild(index) + 1] = temp2;
                this.map.put(temp2, this.getLeftChild(index) + 1);

                
                index = this.getLeftChild(index) + 1;
                
                if (this.getLeftChild(index) + 1 > this.size()) {
                    return;
                }
            }
        }
        
    }
    
    
}
