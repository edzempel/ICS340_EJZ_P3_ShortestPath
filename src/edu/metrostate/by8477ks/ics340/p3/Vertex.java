package edu.metrostate.by8477ks.ics340.p3;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Based on code from http://www.science.smith.edu/dftwiki/index.php/CSC212_Dijkstra%27s_Shortest_Path
 * <p>
 * Vertex class for creating vertices, adding adjacency lists, and resetting vertices
 */
class Vertex implements Comparable<Vertex> {
    private final String name;
    private ArrayList<Edge> adjacencies;
    private double minDistance = Double.POSITIVE_INFINITY;
    private Vertex previous;

    /**
     * Get Vertex name
     *
     * @return vertex name
     */
    public String getName() {
        return name;
    }

    /**
     * Get reference to Vertex adjacency list
     *
     * @return Vertex adjacency list
     */
    public ArrayList<Edge> getAdjacencies() {
        return adjacencies;
    }

    /**
     * set vertex adjacency list
     *
     * @param adjacencies
     */
    public void setAdjacencies(ArrayList<Edge> adjacencies) {
        this.adjacencies = adjacencies;
    }

    /**
     * Get current minimum distance to this vertex in the current graph after running Dijkstra.computePaths
     *
     * @return
     */
    public double getMinDistance() {
        return minDistance;
    }

    /**
     * Set minimum distance for this vertex
     *
     * @param minDistance
     */
    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    /**
     * Get previous vertex to this vertex in the current graph after running Dijkstra.computePaths on the desired
     * origin vertex
     *
     * @return
     */
    public Vertex getPrevious() {
        return previous;
    }

    /**
     * Set previous vertex to this vertex in the current graph after running Dijkstra.computePaths on the desired
     * origin vertex
     *
     * @param previous
     */
    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    /**
     * constructor for Vertex sets name and instantiates the adjacency list
     *
     * @param argName
     */
    public Vertex(String argName) {
        name = argName;
        adjacencies = new ArrayList<Edge>();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

    /**
     * Reset the minimum distance and previous vertex after using shortest path
     *
     * @param cityMap TreeMap<String, Vertex> where String is the name of the given vertex
     */
    public static void resetVertices(TreeMap<String, Vertex> cityMap) {
        for (Map.Entry<String, Vertex> city : cityMap.entrySet()
        ) {
            city.getValue().minDistance = Double.POSITIVE_INFINITY;
            city.getValue().previous = null;
        }
    }

    /**
     * Uses List of Floating Edges to add adjacent edges to relevant vertex in a TreeMap of edges
     *
     * @param cityMap       TreeMap<String, Vertex> where String is the name of the given vertex
     * @param floatingEdges name of origin, name of destination and weight of edge
     */
    public static void addEdges(TreeMap<String, Vertex> cityMap, ArrayList<FloatingEdge> floatingEdges) throws IllegalArgumentException {
        for (FloatingEdge floatingEdge : floatingEdges
        ) {
            Vertex origin = cityMap.get(floatingEdge.getOrigin());
            Vertex destination = cityMap.get(floatingEdge.getDestination());
            if (origin != null && destination != null)
                origin.adjacencies.add(new Edge(destination, floatingEdge.getWeight()));
            else{
                String errorMsg = "";
                if(origin == null)
                    errorMsg += floatingEdge.getOrigin();
                if(destination == null)
                    errorMsg += errorMsg.isEmpty() ? floatingEdge.getDestination() : ", " + floatingEdge.getDestination();
                throw new IllegalArgumentException(String.format("Invalid city name(s) %s.", errorMsg));
            }
        }
    }

}

/**
 * Based on code from http://www.science.smith.edu/dftwiki/index.php/CSC212_Dijkstra%27s_Shortest_Path
 * <p>
 * Edge class to use create adjacent edges for a given vertex
 */
class Edge {
    private final Vertex target;
    private final double weight;

    public Vertex getTarget() {
        return target;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * Constructor for Edges. Add adjacent vertices to an origin vertex.
     *
     * @param argTarget destination vertex
     * @param argWeight weight of edge
     */
    public Edge(Vertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}

/**
 * An intermediate class for creating edges without using the Vertex class
 */
class FloatingEdge {
    private String origin;
    private String destination;
    private int weight;

    /**
     * Constructor for creating floating edges
     *
     * @param origin      name of origin Vertex
     * @param destination name of destination Vertex
     * @param weight      of edge
     */
    public FloatingEdge(String origin, String destination, int weight) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Get name of origin Vertex
     *
     * @return
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Get name of destination Vertex
     *
     * @return
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Get weight of floating edge
     *
     * @return
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%s to %s is %d", origin, destination, weight);
    }
}

