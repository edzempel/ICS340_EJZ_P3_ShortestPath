# ICS 340 Project 3
 - Edward Zempel
 - Fall 2019
## Instructions
### Application
1. import into IntelliJ
2. Run Main.main()
### Tests
1. Navigate to project > src > test
2. run test classes using JUnit5
## Overview
Given a file with a list of legal denominations or bills (such as $1, 5$) and target total amounts the application generates two files. They detail the combinations of bills that total to that target amount and the time in milliseconds taken to produce that calculation. One file uses a recursive algorithm and the other uses a dynamic algorithm.
The files appear in the same directory as the source file.
## Analysis
All read write operations are O(n) and are not the most significant in the application. There are some basic sorts for the header line which is O(n lg n) based on the number of elements in the file header. The algorithms have the largest impact on computation.
## Recursive algorithm
The complexity of the recursive version in O(2^n). Permutations would be even worse than combinations. Every coin needs to be checked as part of a valid combination as either in or out to the nth power times. The recursive algorithm does many unnecessary computations.
## Dynamic algorithm
The dynamic algorithm calculates the combinations for each amount separately. It uses 2 nested for loops. It's complexity is O(n^2) where n is the amount to be calculated if the numbers of bills types matches the target amount.