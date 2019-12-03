package edu.metrostate.by8477ks.ics340.p3;

import java.util.*;

class Vertex implements Comparable<Vertex> {
    public final String name;
    public ArrayList<Edge> adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(String argName) {
        name = argName;
        adjacencies = new ArrayList<Edge>();
    }

    public String toString() {
        return name;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

    public static void resetVertices(TreeMap<String, Vertex> cityMap) {
        for (Map.Entry<String, Vertex> city : cityMap.entrySet()
        ) {
            city.getValue().minDistance = Double.POSITIVE_INFINITY;
            city.getValue().previous = null;
        }
    }

    public static void addEdges(TreeMap<String, Vertex> cityMap, ArrayList<FloatingEdge> floatingEdges) {
        for (FloatingEdge floatingEdge : floatingEdges
        ) {
            cityMap.get(floatingEdge.getOrigin()).adjacencies.add(new Edge(cityMap.get(floatingEdge.getDestination()), floatingEdge.getWeight()));
        }
    }

}


class Edge {
    public final Vertex target;
    public final double weight;

    public Edge(Vertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}

class FloatingEdge {
    private String origin;
    private String destination;
    private int weight;

    public FloatingEdge(String origin, String destination, int weight) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%s to %s is %d", origin, destination, weight);
    }
}

class Dijkstra {
    public static void computePaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);

                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        // mark all the vertices
        Vertex A = new Vertex("Anoka");
        Vertex S = new Vertex("St. Paul");
        Vertex M = new Vertex("Minneapolis");
        Vertex B = new Vertex("Big Lake");

        // set the edges and weight
        A.adjacencies.add(new Edge(S, 30));
        A.adjacencies.add(new Edge(M, 30));
        B.adjacencies.add(new Edge(M, 40));
        S.adjacencies.add(new Edge(M, 20));
        S.adjacencies.add(new Edge(A, 30));
        M.adjacencies.add(new Edge(A, 30));
        M.adjacencies.add(new Edge(S, 20));
        M.adjacencies.add(new Edge(B, 40));


        TreeMap<String, Vertex> cityMap = new TreeMap<String, Vertex>();
        cityMap.put(A.name, A);
        cityMap.put(B.name, B);
        cityMap.put(M.name, M);
        cityMap.put(S.name, S);

        Vertex.resetVertices(cityMap);

        System.out.println("-------------------------");

        ArrayList<MapVertexCombo> allCombos = MapVertexCombo.getAllCombos(cityMap);
        for (MapVertexCombo<Vertex> combo : allCombos
        ) {
            Vertex.resetVertices(cityMap);
            System.out.println(combo);
            printShortestPath(combo.getMap().get(VertexTypes.ORIGIN), combo.getMap().get(VertexTypes.DESTINATION));
            System.out.println();
        }

    }


    public static void printShortestPath(Vertex origin, Vertex destination) {
        computePaths(origin); // run Dijkstra
        System.out.println("Distance to " + destination + ": " + destination.minDistance);
        List<Vertex> path = getShortestPathTo(destination);
        System.out.println("Path: " + path);
    }


    public enum VertexTypes {
        ORIGIN, DESTINATION;
    }
}