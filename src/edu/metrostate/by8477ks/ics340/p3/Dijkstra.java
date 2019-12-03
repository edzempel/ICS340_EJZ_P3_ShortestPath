package edu.metrostate.by8477ks.ics340.p3;

import java.util.*;

class Dijkstra {
    public static void computePaths(Vertex source) {
        source.setMinDistance(0.);
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.getAdjacencies()) {
                Vertex v = e.target;
                double weight = e.weight;
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

    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
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

        System.out.println("-------------------------");

        ArrayList<VertexPair> allCombos = VertexPair.getAllCombos(cityMap);
        for (VertexPair<Vertex> combo : allCombos
        ) {
            Vertex.resetVertices(cityMap);
            System.out.println(combo);
            printShortestPath(combo.getPair().get(VertexPair.VertexTypes.ORIGIN), combo.getPair().get(VertexPair.VertexTypes.DESTINATION));
            System.out.println();
        }

    }


    public static void printShortestPath(Vertex origin, Vertex destination) {
        computePaths(origin); // run Dijkstra
        System.out.println("Distance to " + destination + ": " + destination.getMinDistance());
        List<Vertex> path = getShortestPathTo(destination);
        System.out.println("Path: " + path);
    }


}
