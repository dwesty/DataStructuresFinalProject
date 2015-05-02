import java.util.Scanner;

/**
 * Driver class for TasiClass.
 * 
 * @author hipstorian
 *
 */
public final class Taxis {
    /**
     * Scanner object.
     */
    private static Scanner input = new Scanner(System.in);
    
    /**
     * Checkstyle!
     */
    private Taxis() {

    }

    /**
     * Taxi.
     * 
     * @param arg
     *            command line arg
     */
    public static void main(String[] arg) {
        TaxiClass x = new TaxiClass(arg[0], arg[1], arg[2]);
        int location;
        final int numDrivers = 3;
        System.out.println("Enter number of "
                + "recent client pickup request location:");
        location = input.nextInt();
        x.mapMenu();
        x.enterLocation(location, numDrivers);
        System.out.println("DONE");
    }

}
