package edu.metrostate.by8477ks.ics340.p3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Based on code from http://www.science.smith.edu/dftwiki/index.php/CSC212_Dijkstra%27s_Shortest_Path
 * <p>
 * Finds shortest path to all other vertices in a directed or non directed graph with no negative cycles
 */
class Dijkstra {
    /**
     * Compute the shortest path to all other vertices in the graph from the source vertex
     * There is no need to rerun this algorithm if the source vertex has not changed.
     * Instead, run getShortestPath on the desired target.
     * @param source the starting or origin vertex
     */
    public static void computePaths(Vertex source) {
        source.setMinDistance(0.);
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.getAdjacencies()) {
                Vertex v = e.getTarget();
                double weight = e.getWeight();
                double distanceThroughU = u.getMinDistance() + weight;
                if (distanceThroughU < v.getMinDistance()) {
                    vertexQueue.remove(v);

                    v.setMinDistance(distanceThroughU);
                    v.setPrevious(u);
                    vertexQueue.add(v);
                }
            }
        }
    }

    /**
     * Iterate through all nodes starting at the target until the source is reached (has a previous value of null)
     * Run resetVertices if the origin.previous != null
     * @param target Vertex
     * @return list of vertices in shortest path from origin to destination
     */
    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

    /**
     * Brute force use of the Dijkstra class to do initial testing
     * @param origin Vertex
     * @param destination Vertex
     */
    public static void printShortestPath(Vertex origin, Vertex destination) {
        computePaths(origin); // run Dijkstra
        System.out.println("Distance to " + destination + ": " + destination.getMinDistance());
        List<Vertex> path = getShortestPathTo(destination);
        System.out.println("Path: " + path);
    }


}
