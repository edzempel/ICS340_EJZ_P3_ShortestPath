package edu.metrostate.by8477ks.ics340.p3;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;

public class MapVertexCombo<V> {
    EnumMap<Dijkstra.VertexTypes, V> map;

    public EnumMap<Dijkstra.VertexTypes, V> getMap() {
        return map;
    }



    public MapVertexCombo(V origin, V destination) {
        map = new EnumMap<Dijkstra.VertexTypes, V>(Dijkstra.VertexTypes.class);
        map.put(Dijkstra.VertexTypes.ORIGIN, origin);
        map.put(Dijkstra.VertexTypes.DESTINATION, destination);
    }


    public static ArrayList<MapVertexCombo> getAllCombos(TreeMap<String, Vertex> vertices) {

        ArrayList<Vertex> list = new ArrayList<Vertex>();
        for ( Map.Entry<String, Vertex> vertex : vertices.entrySet()
        ) {
            list.add(vertex.getValue());
        }
        int outputSize = list.size() * (list.size() - 1) / 2;
        ArrayList<MapVertexCombo> output = new ArrayList<MapVertexCombo>();

        for (int i = 0; i < list.size(); i++) {  // O(n * (n-1))
            for (int j = i + 1; j < list.size(); j++) {
                output.add(new MapVertexCombo<Vertex>(list.get(i), list.get(j)));
            }
        }

        return output;

    }

    @Override
    public String toString() {
        return this.map.get(Dijkstra.VertexTypes.ORIGIN) + " to  " + this.map.get(Dijkstra.VertexTypes.DESTINATION);
    }

}
