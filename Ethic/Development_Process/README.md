----------------------
# Ethic Coding Exercise
###### Refresh Date: 11.20.20
###### Author: Haitai Ng (HaitaiNg@gmail.com) / 248-977-9325
This directory stores supplemental material for my submission for the Ethic Coding Exercise.

##### Description and Interpretation
After reading the problem description, I first drew out the weighted directed graph. I then solved the problems 1-4 to understand what was being asked. ***Please see paper_documentation.jpg or paper_documentation.pdf for my written work.***

##### Preliminary Thoughts
- Quickly reviewed some concepts regarding directed weighted graphs.
- Graph traversal algorithms (breadth first search, depth first search)
- How to represent a directed weighted graph (adjacency matrix vs adjacency list)
- Dijkstra's algorithm for calculating shortest path between two nodes
- How to handle user input: display questions on console vs pass in file

##### Solving the problems:
- In order to create and represent the graph, I considered using an adjacency matrix due to the quick loop up time and speed, however I had some difficulty figuring out how I could accurately represent the edges with characters instead of integers. Therefore ***I decided to use an adjacency list.***

- The adjacency list. Each entry in the adjacency list is a node. The node will have a map entry that contains the connected nodes and their weights. (example: node A -> { node B : 5, node D : 8, node E : 7}). Please refer to paper_documentation.jpg for a visual representation.

- For question 1, I accepted the path, then started at the root. From the root (node), I would then traverse the corresponding adjacency list and check if the next node in the path was in the adjacency list. I would add the weight to a variable. For next steps, I would update root to next node, get adjacency list, check next node in path, continue. Once we reached the destination node, I would return the cumulative weight.

- For question 2, I used a modified depth first search algorithm to find all the paths from a start node to an end node. I then traversed through all the possible paths and check if the number of stops is less than the allocated number of stops. If the path met these conditions, I would add it to a set. I would then return the size of the set.

- For question 3, I re-used the modified depth first search algorithm to find all the paths from a start node to an end node. I then traversed through all the possible paths between the start node and end nodes. I computed the path’s weight by reusing the function I wrote to solve question 1 and determined whether that returned distance was less than the allocated distance. The challenging component for this question was calculating all permutations of cyclic combinations. I had to write multiple support methods. To otbtain the permutations I wrote a recursive function to calculate the various combinations of paths within the allocated distance.

- For question 4, the user must provide two arguments: start node (single character) denoting the starting location; the destination node (single character). I used Dijkstra’s algorithm to calculate the shortest path from a start node to all nodes and updated each node’s distance attribute. The distance attribute for each node is the numerical distance from the root node to that particular node. If no path exists, the distance will be Integer.MAX_VALUE (2^31-1). Given the destination node, I would return the distance attribute for that node denoting the shortest distance between root and that destination node.

- Writing and testing my program using unit tests helped me identify major bugs, and errors in my code.

##### Support
Please contact Haitai Ng if there any questions or concerns. Thank you for your time.
