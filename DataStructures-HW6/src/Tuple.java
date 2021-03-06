/**
 * 2-Tuple Class for coordinate pairs.
 * @author davidwestjr
 * 
 * Adapted from SO users Maerics and Aram Kocharyan
 *
 * @param <X> the x coord type
 * @param <Y> the y coord type
 * 
 */
public class Tuple<X, Y> { 
    /**
     * x-coordinate of type X.
     */
    public final X x; 
    
    /**
     * y-coordinate of type Y.
     */
    public final Y y; 
    
    /**
     * Default constructor.
     * @param xCoord the x coordinate
     * @param yCoord the y coordinate
     */
    public Tuple(X xCoord, Y yCoord) { 
        this.x = xCoord; 
        this.y = yCoord; 
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Tuple)){
            return false;
        }
        Tuple<X,Y> other_ = (Tuple<X,Y>) other;
        return other_.x == this.x && other_.y == this.y;
    }

    /**
     * TODO: Check hashing method. 
     * @return the hash code for the tuple
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        
        /**
         * TODO: rewrite the inline conditionals.
         */
        result = prime * result + ((this.x == null) ? 0 : this.x.hashCode());
        result = prime * result + ((this.y == null) ? 0 : this.y.hashCode());
        return result;
    }
}