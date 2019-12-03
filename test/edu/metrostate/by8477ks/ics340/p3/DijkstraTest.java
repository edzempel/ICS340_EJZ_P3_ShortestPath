package edu.metrostate.by8477ks.ics340.p3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import static edu.metrostate.by8477ks.ics340.p3.Dijkstra.getShortestPathTo;
import static edu.metrostate.by8477ks.ics340.p3.Dijkstra.printShortestPath;
import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void computePathsTest() {
        // mark all the vertices
        Vertex A = new Vertex("Anoka");
        Vertex S = new Vertex("St. Paul");
        Vertex M = new Vertex("Minneapolis");
        Vertex B = new Vertex("Big Lake");

        // set the edges and weight
        A.getAdjacencies().add(new Edge(S, 30));
        A.getAdjacencies().add(new Edge(M, 30));
        B.getAdjacencies().add(new Edge(M, 40));
        S.getAdjacencies().add(new Edge(M, 20));
        S.getAdjacencies().add(new Edge(A, 30));
        M.getAdjacencies().add(new Edge(A, 30));
        M.getAdjacencies().add(new Edge(S, 20));
        M.getAdjacencies().add(new Edge(B, 40));


        TreeMap<String, Vertex> cityMap = new TreeMap<String, Vertex>();
        cityMap.put(A.getName(), A);
        cityMap.put(B.getName(), B);
        cityMap.put(M.getName(), M);
        cityMap.put(S.getName(), S);

        Vertex.resetVertices(cityMap);

        ArrayList<VertexPair> allCombos = VertexPair.getAllPairs(cityMap);
        ArrayList<String> comboStrings = new ArrayList<String>();
        ArrayList<String> pathStrings = new ArrayList<String>();

        for (VertexPair<Vertex> combo : allCombos
        ) {
            Vertex.resetVertices(cityMap);
            comboStrings.add(combo.toString());
            List<Vertex> path = getShortestPathTo(combo.getPair().get(VertexPair.VertexTypes.DESTINATION));
            pathStrings.add("Path: " + path);
            printShortestPath(combo.getPair().get(VertexPair.VertexTypes.ORIGIN), combo.getPair().get(VertexPair.VertexTypes.DESTINATION));

        }

        assertEquals(new ArrayList<String>(Arrays.asList("Anoka to  Big Lake",
                "Anoka to  Minneapolis",
                "Anoka to  St. Paul",
                "Big Lake to  Minneapolis",
                "Big Lake to  St. Paul",
                "Minneapolis to  St. Paul")), comboStrings);

        assertEquals(new ArrayList<String>(Arrays.asList("Path: [Anoka, Minneapolis, Big Lake]",
                "Path: [Anoka, Minneapolis]",
                "Path: [Anoka, St. Paul]",
                "Path: [Big Lake, Minneapolis]",
                "Path: [Big Lake, Minneapolis, St. Paul]",
                "Path: [Minneapolis, St. Paul]")), pathStrings);
    }

    @Test
    void getShortestPathToTest() {
        fail();
    }
}