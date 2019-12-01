package edu.metrostate.by8477ks.ics340.p3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

import static edu.metrostate.by8477ks.ics340.p3.Controller.readFileStartingAt;
import static edu.metrostate.by8477ks.ics340.p3.Controller.readHeaderLine;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    File sourceFile;
    private String filePath = "C:\\Users\\meanderthalz\\Documents\\test_files\\p3test\\sample-map.txt";

    @BeforeEach
    void setUp() {
        sourceFile = new File(filePath);
        assertTrue(sourceFile.exists(), "File does not exist.");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void readHeaderLineTest() {
        try {
            TreeMap<String, Vertex> cityMap = readHeaderLine(sourceFile);
            assertEquals(4, cityMap.size());
            assertEquals("Anoka", cityMap.get("Anoka").name);
            assertEquals("Minneapolis", cityMap.get("Minneapolis").name);
            assertEquals("Big Lake", cityMap.get("Big Lake").name);
            assertEquals("St. Paul", cityMap.get("St. Paul").name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    void readFileStartingAtTest() {
        try {
            ArrayList<FloatingEdge> edges = readFileStartingAt(sourceFile, 1);
            assertEquals(8, edges.size());
            assertEquals("[Minneapolis to Anoka is 30, Anoka to Minneapolis is 30, Minneapolis to St. Paul is 20, St. Paul to Minneapolis is 20, Big Lake to Minneapolis is 40, Minneapolis to Big Lake is 40, St. Paul to Anoka is 30, Anoka to St. Paul is 30]", edges.toString());
//            System.out.println(edges);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}