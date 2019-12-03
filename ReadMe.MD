# ICS 340 Project 3
 - Edward Zempel
 - Fall 2019
## Instructions
### Application
1. Import into IntelliJ
2. Run Main.main()
### Tests
1. Navigate to project > src > test
2. run test classes using JUnit5
## Overview
Given a file with a list of hyphen separated cities (Minneapolis-St. Paul-Bloomington) followed by edges (distance-origin-destination), compute the shortest path between each pair of cities and write to a file. The output file appears in the same directory as the source file.
## Analysis
All read write operations are O(n) and are not the most significant in the application. Also, adding vertices to the TreeMap is worst case O(n) where n is the number of vertices. this occurs when the first city added is at the beginning or the end of the alphabet in respect to the cities that come after it.

The VertexPairs.getAllPairs() method has 2 nested for loops for determining all pairs of cities. It loops (n*(n-1)/2) times which is O(n^2) broadly speaking.

The path finding element in the Dijkstra class uses the Java Standard library Priority Queue. This requires running this O(E log V) function for each new starting vertex. The Controller checks if the starting vertex has already had its minDistance set to 0.Therefore this program does not compute paths redundantly. This limits path compute to V-1 times. Overall the path finding portion is O((V-1)E log V)

## Overall Complexity
The highest order part of the algorithm is the path finding. Because of this the complexity of the program overall in O(VE lg V) where E is the number of edges and V is the number of vertices.
