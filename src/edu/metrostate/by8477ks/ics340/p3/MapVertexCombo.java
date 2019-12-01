package edu.metrostate.by8477ks.ics340.p3;

import java.util.ArrayList;
import java.util.EnumMap;

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


    public static <V> ArrayList<MapVertexCombo> getAllCombos(V... vertices) {

        ArrayList<V> list = new ArrayList<V>();
        for (V vertex : vertices
        ) {
            list.add(vertex);
        }
        int outputSize = list.size() * (list.size() - 1) / 2;
        ArrayList<MapVertexCombo> output = new ArrayList<MapVertexCombo>();

        for (int i = 0; i < list.size(); i++) {  // O(n * (n-1))
            for (int j = i + 1; j < list.size(); j++) {
                output.add(new MapVertexCombo<V>(list.get(i), list.get(j)));
            }
        }

        return output;

    }

    @Override
    public String toString() {
        return this.map.get(Dijkstra.VertexTypes.ORIGIN) + " to  " + this.map.get(Dijkstra.VertexTypes.DESTINATION);
    }

}
