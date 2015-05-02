import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * When given the in- puts specified below, will determine which k drivers to
 * alert, and then, when one driver accepts the ride request for that location,
 * will output directions to be used by that driver to get to the pickup
 * location as quickly as possible.
 * 
 * @author hipstorian
 *
 */
public class TaxiClass {
    /**
     * Map of locations.
     */
    private GraphWrapper<String> map;

    /**
     * MinHeap holding all taxis, ordered by number of minutes it takes to reach
     * a source value (as determined by Dijkstra's algorithm).
     */
    private AdaptableMinHeap taxiDistHeap;

    /**
     * HashMap between Taxi locations and serial numbers. Dirty solution,
     * really.
     */
    private HashMap<String, Integer> taxiLocationMap;

    /**
     * HashMap between serial numbers and Taxi locations. Dirty solution,
     * really.
     */
    private HashMap<Integer, String> locationTaxiMap;

    /**
     * Constructor.
     * 
     * @param mapLocations
     *            the name of a text file consisting of all map location names,
     *            one per line (location names may contain spaces)
     * @param mapConnections
     *            The name of a text file consisting of ordered pairs of lo-
     *            cation names to represent connections between them, one
     *            ordered pair per line
     * @param driverLocations
     *            the name of a text file indicating the current locations of
     *            all drivers. Each line will consist of an integer driver ID
     *            number, then whitespace, then the name of the map location
     *            nearest the driver.
     */
    public TaxiClass(String mapLocations, String mapConnections,
            String driverLocations) {
        final int x = 30;
        this.map = new GraphWrapper<String>(new AdjacencyListGraph(0));
        this.loadMapLocations(mapLocations);
        this.loadConnections(mapConnections);
        this.taxiLocationMap = new HashMap<String, Integer>();
        this.locationTaxiMap = new HashMap<Integer, String>();
        this.taxiDistHeap = new AdaptableMinHeap(x); // fix this
        this.loadDriverLocations(driverLocations);
    }

    /**
     * Display locations available.
     */
    public void mapMenu() {
        int counter = 0;
        System.out.println("Map locations are:");
        for (String location : this.map.keys) {
            System.out.println("\t" + ++counter + ") " + location);
        }
    }

    /**
     * Take input from user.
     *
     * @param location
     *            where user is at the moment, chosen form map menu. will become
     *            source in dijkstras
     * @param numDrivers
     *            number of drivers customers wants to choose from
     * @return an array of size numDrivers containing serial numbers of drivers
     *         able to pick up customer
     */
    public LinkedList<Integer>[] enterLocation(int location, int numDrivers) {
        LinkedList<Integer>[] availableDrivers = new LinkedList[numDrivers];
        Scanner input = new Scanner(System.in);
        int driver, time;
        // VV menu indexed at 1, program at 0
        int[] directions = this.dijkstrasAlgo(location - 1);

        for (int i = 0; i < numDrivers; i++) {
            availableDrivers[i] = this.taxiDistHeap.getMin();
        }

        System.out.println("The " + numDrivers
                + " drivers to alert about this pickup are:");
        for (int i = 0; i < numDrivers; i++) {
            System.out.println("\t" + availableDrivers[i].get(1) + " at "
                    + this.locationTaxiMap.get(availableDrivers[i].get(1)));
        }

        System.out.println("Enter the ID number of the driver who responded.");
        driver = input.nextInt();
        time = this.numMinutes(driver, availableDrivers);
        while (this.numMinutes(driver, availableDrivers) < 0) {
            System.out.println("Invalid serial number. Try again.");
            driver = input.nextInt();
            time = this.numMinutes(driver, availableDrivers);
        }

        System.out.println("Recommended route for driver " + driver + " is:");
        this.printRoute(directions,
                this.map.keys.indexOf(this.locationTaxiMap.get(driver)));
        System.out.println("\tExpected total time: " + time + " minutes.");
        return availableDrivers;
    }

    /**
     * Prints out route given dijkstra's prev[] output and the location of the
     * DRIVER chosen.
     * 
     * @param prev
     *            array output given by dijkstra's
     * @param location
     *            location of DRIVER or some point within ideal path to source.
     */
    private void printRoute(int[] prev, int location) {
        if (prev[location] == -1) {
            return;
        }
        System.out.println("\t(" + this.map.keys.get(location) + ", "
                + this.map.keys.get(prev[location]) + ")");
        this.printRoute(prev, prev[location]);
    }

    /**
     * Helper method for taxi loop. Given a list of drivers, determines number
     * of minutes required to get to source.
     * 
     * @param serial
     *            argument driver to find
     * @param drivers
     *            list of drivers
     * @return number or minutes. returns -1 if driver serial not found.
     */
    private int numMinutes(int serial, LinkedList<Integer>[] drivers) {
        for (int i = 0; i < drivers.length; i++) {
            if (serial == drivers[i].get(1)) {
                return drivers[i].get(0);
            }
        }
        return -1;
    }

    /**
     * Find shortest distances from all locations in this.map to a source.
     * Automatically updates this.taxiDistHeap.
     * 
     * @param source
     *            location of customer, determined from a this.mapMenu()
     * @return a list of paths interpretable by prev[]
     */
    private int[] dijkstrasAlgo(int source) {
        /* SETUP */
        int[] dist = new int[this.map.getNumVerts()];
        int[] prev = new int[this.map.getNumVerts()];
        boolean[] found = new boolean[this.map.getNumVerts()];
        int neighborIndex, newDist; // lol, newDist
        for (int i = 0; i < this.map.getNumVerts(); i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
            found[i] = false;
        }
        dist[source] = 0; // prev of source is irrelevant

        /* ACTUAL ALGORITHM */
        for (int n = 0; n < this.map.getNumVerts(); n++) {
            int min = this.smallestUnfound(dist, found); // min unfound vtx
            if (min < 0) { // all are found or graph is disconnected
                break;
            }
            found[min] = true;
            String minLoc = this.map.keys.get(min);
            for (String currNeighbor : this.map.getNeighbors(minLoc)) {
                newDist = dist[min] + this.map.getWeight(currNeighbor, minLoc);
                neighborIndex = this.map.keys.indexOf(currNeighbor);

                /* UPDATING HEAP */
                if (newDist < dist[neighborIndex]) {
                    dist[neighborIndex] = newDist; // upd8 dist
                    if (this.taxiLocationMap.get(currNeighbor) != null) {
                        this.taxiDistHeap.updateDistance(newDist,
                                this.taxiLocationMap.get(currNeighbor));
                    }
                    prev[neighborIndex] = min;
                }

            }
        }
        return prev;
    }

    /**
     * Helper method for Dijkstra's. Finds index of smallest unfound vertex.
     * 
     * @param dist
     *            array of distances in for a CONNECTED graph with POSITIVE
     *            weights
     * @param found
     *            array same length as dist; refers to whether a node of
     *            corresponding index has been examined yet.
     * @return index of smallest unfound vertex. refer to
     *         this.map.keys.get(RETURN VALUE) to find corresponding location.
     */
    private int smallestUnfound(int[] dist, boolean[] found) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < found.length; i++) {
            if (!found[i] && dist[i] < min) {
                min = dist[i];
                minIndex = i;
            }
        }
        // System.out.println("min at " + minIndex);
        return minIndex;
    }

    /**
     * Load in edge weights to adjacency matrix.
     * 
     * @param mapConnections
     *            The name of a text file consisting of ordered pairs of
     *            location names to represent connections between them, one
     *            ordered pair per line
     */
    private void loadConnections(String mapConnections) {
        System.out.println("Collecting map connections from " + mapConnections
                + "...");
        Scanner weightreader;
        try {
            weightreader = new Scanner(new File(mapConnections));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int counter = 0;
        while (weightreader.hasNextLine()) {
            this.interprentConnectionInput(weightreader.nextLine().trim());
            counter++;
        }
        weightreader.close();
        System.out.println(counter + " connections input.");
    }

    /**
     * Take a line of mapConnections input. Parse and interpret the line of
     * text, and enter edge weight into correponding slots in adjcency matrix.
     * 
     * @param input
     *            single line of input from textbook. must be of the form "(" +
     *            location1 + ", " + location to + ") " + number of minutes
     *            requred to reach said location. number of minutes must be a
     *            positive integer.
     */
    private void interprentConnectionInput(String input) {
        String location1, location2;
        String[] splitInput;
        int weight;
        splitInput = input.split(",");
        location1 = splitInput[0].substring(1).trim();
        location2 = splitInput[1].substring(0, splitInput[1].indexOf(")"))
                .trim();
        weight = Integer.parseInt(splitInput[1].substring(
                splitInput[1].indexOf(")") + 1).trim());
        this.map.addEdge(location1, location2, weight);
    }

    /**
     * Load in map locations from a file.
     * 
     * @param mapLocations
     *            text file consisting of all map location names, one per line
     *            (location names may contain spaces)
     */
    private void loadMapLocations(String mapLocations) {
        System.out.println("Collecting map locations from " + mapLocations
                + "...");
        Scanner mapreader;
        try {
            mapreader = new Scanner(new File(mapLocations));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int counter = 0;
        while (mapreader.hasNextLine()) {
            this.map.addVertex(mapreader.nextLine().trim());
            counter++;
        }
        System.out.println(counter + " locations input.");
        mapreader.close();
    }

    /**
     * Load driver locations into a priority queue.
     * 
     * @param driverLocations
     *            the name of a text file indicating the current locations of
     *            all drivers. Each line will consist of an integer driver ID
     *            number, then whitespace, then the name of the map location
     *            nearest the driver.
     */
    private void loadDriverLocations(String driverLocations) {
        System.out.println("Collecting driver locations from "
                + driverLocations + "...");
        Scanner driverreader;
        try {
            driverreader = new Scanner(new File(driverLocations));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int counter = 0;
        while (driverreader.hasNextLine()) {
            this.interpretDriverInput(driverreader.nextLine());
            counter++;
        }
        System.out.println(counter + " drivers input.");
        driverreader.close();
    }

    /**
     * Interprets a line of text from driver txt file.
     * 
     * @param line
     *            text of the form [seral number] + whitespace + [location],
     *            where location is a valid member of the map graph.
     */
    private void interpretDriverInput(String line) {
        int serial;
        String location;
        Scanner driverReader = new Scanner(line);
        serial = driverReader.nextInt();
                //Integer.parseInt(line.replaceAll("[\\D]", ""));
        location = driverReader.nextLine().trim();
                //line.replaceAll("\\d", "").trim();
        this.taxiLocationMap.put(location, serial);
        this.locationTaxiMap.put(serial, location);
        this.taxiDistHeap.add(Integer.MAX_VALUE, serial);
        driverReader.close();
    }
}
