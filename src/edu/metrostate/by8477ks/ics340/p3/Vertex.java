package edu.metrostate.by8477ks.ics340.p3;

import java.util.*;

class Vertex implements Comparable<Vertex> {
    private final String name;
    private ArrayList<Edge> adjacencies;
    private double minDistance = Double.POSITIVE_INFINITY;
    private Vertex previous;

    public String getName() {
        return name;
    }

    public ArrayList<Edge> getAdjacencies() {
        return adjacencies;
    }

    public void setAdjacencies(ArrayList<Edge> adjacencies) {
        this.adjacencies = adjacencies;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

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
    public static void addEdges(TreeMap<String, Vertex> cityMap, ArrayList<FloatingEdge> floatingEdges) {
        for (FloatingEdge floatingEdge : floatingEdges
        ) {
            cityMap.get(floatingEdge.getOrigin()).adjacencies.add(new Edge(cityMap.get(floatingEdge.getDestination()), floatingEdge.getWeight()));
        }
    }

}

/**
 * Based on code from http://www.science.smith.edu/dftwiki/index.php/CSC212_Dijkstra%27s_Shortest_Path
 * <p>
 * Edge class to use create adjacent edges for a given vertex
 */
class Edge {
    public final Vertex target;
    public final double weight;

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

