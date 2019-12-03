package edu.metrostate.by8477ks.ics340.p3;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Create Vertex pairs with a specific origin and destination
 * @param <V>
 */
public class VertexPair<V> {
    private EnumMap<VertexTypes, V> pair;

    /**
     * Return vertex pair
     * @return
     */
    public EnumMap<VertexTypes, V> getPair() {
        return pair;
    }

    /**
     * Constructor for VertexPair class
     * @param origin
     * @param destination
     */
    public VertexPair(V origin, V destination) {
        pair = new EnumMap<VertexTypes, V>(VertexTypes.class);
        pair.put(VertexTypes.ORIGIN, origin);
        pair.put(VertexTypes.DESTINATION, destination);
    }

    /**
     * (n*(n-1)/2) where n is number of cities has O(n^2)
     * Returns all size 2 combinations in the given set of vertices
     * @param vertices TreeMap<String, Vertex> of vertices
     * @return all combinations of vertices
     */
    public static ArrayList<VertexPair> getAllPairs(TreeMap<String, Vertex> vertices) {

        ArrayList<Vertex> list = new ArrayList<Vertex>();
        for (Map.Entry<String, Vertex> vertex : vertices.entrySet()
        ) {
            list.add(vertex.getValue());
        }
        int outputSize = list.size() * (list.size() - 1) / 2;
        ArrayList<VertexPair> output = new ArrayList<VertexPair>();

        for (int i = 0; i < list.size(); i++) {  // O(n * (n-1))
            for (int j = i + 1; j < list.size(); j++) {
                output.add(new VertexPair<Vertex>(list.get(i), list.get(j)));
            }
        }

        return output;

    }

    @Override
    public String toString() {
        return this.pair.get(VertexTypes.ORIGIN) + " to  " + this.pair.get(VertexTypes.DESTINATION);
    }

    /**
     * Restricts vertices in VertexPair to origin and destination
     */
    public enum VertexTypes {
        ORIGIN, DESTINATION
    }
}
