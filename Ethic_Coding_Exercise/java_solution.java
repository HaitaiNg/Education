/*
    Author: Haitai Ng 
    Refresh Date: 11.21.20 
    Description: This is my solution for the Ethic coding exercise. 

    Input: 
    
    The first input is a comma separated list denoting the associations between towns and their corresponding distance. 
    (example: "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7"). This is a directed graph. For AB5 the first char A represents the start town, 
    char B represents the destination town, and 5 represents the distance between A and B. This graph will be instatiated 
    at the beginning of this program. We assume that no modifications will occur to the graph. 

    Loop Condition: After the graph has been instantiated, the program will continously prompt the user for an integer
    Each integer will correspond to a particular question. 

    if "1" is submitted the user will be prompted for : "The distance of the route __". The user will have to provide 
    the designated route 



*/ 

import java.util.*;

class Graph{

    class Node{
        char id; 
        Node(char ID)
        {
            this.id = ID; 
        }
        //Dijkstras algorithm is here 
        private int distance = Integer.MAX_VALUE; //< this is used for Dijkstra's algorithm 
        public int getDistance(){
            return distance; 
        }
        public void setDistance(int distance){
            this.distance = distance; 
        }
        private List<Node> shortestPath = new LinkedList<>(); 
        public List<Node> getShortestPath(){
            return shortestPath; 
        }
        public void setShortestPath(LinkedList<Node> shortestPath){
            this.shortestPath = shortestPath;}
    }
    
    public Map<Node, Map<Node, Integer>> adjacencyList; 
    public List<String> allPathsBetweenTwoNodes; 


    public Graph(){
        adjacencyList = new HashMap<Node, Map<Node, Integer>>(); 
        allPathsBetweenTwoNodes = new ArrayList<String>(); 
    }
    
    // Add Node 
    void addNode(Character id)
    {
        adjacencyList.putIfAbsent(new Node(id), new HashMap<Node, Integer>()); 
    }
    // Add edge & connect nodes 
    void addEdge(Character idOne, Character idTwo, int weight)
    {
        // Simplified approach 
        // Node n1 = new Node(idOne);
        // Node n2 = new Node(idTwo); 
        // adjacencyList.get(n1).put(n2, weight); //< this throws a compile error 
        // ignoring weights for now 
        
        Node n2 = new Node(idTwo); 
        for(Node node: adjacencyList.keySet())
        {
            if(node.id == idOne){
                adjacencyList.get(node).put(n2, weight);
            }
        } 
    }

    Node getNode(String nodeName){
        for(Node node: adjacencyList.keySet()){
            if(node.id == nodeName.charAt(0)) return node; 
        }
        return null; 
    }



    //Get vertices for a particular node 
    Map<Node, Integer> getAdjacentVerticies(char id)
    {
        for(Node c: adjacencyList.keySet())
        {
            if(c.id == id)
            {
                return adjacencyList.get(c);
            }
        }
        return null;
    }

    // Part One 
    int getDistanceAlongRoute(String route)
    {
        int costOfRoute = 0; 
        int slow = 0; int fast = 1; 
        while( fast < route.length()){
            char current = route.charAt(slow);
            char next = route.charAt(fast);
            Map<Node, Integer> result = getAdjacentVerticies(current); 
            for(Node node : result.keySet())
            {
                if(node.id == next) costOfRoute += result.get(node);
                
            }
            slow++; fast++;
        }
        return costOfRoute; 
    }

    public static Node calculateClosestNode(Set<Node> unvisited){
        Node closestNode = null; 
        int closestDistance = Integer.MAX_VALUE; 
        for(Node node: unvisited){
            int nodeDistance = node.getDistance();
            if( nodeDistance< closestDistance)
            {
                closestDistance = nodeDistance;
                closestNode = node; 
            }
        }
        return closestNode; 
    }

    public static void calculateShortestDistance(Graph graph, Node adjacentNode, Node source, int weight){
        if(source.getDistance() + weight < adjacentNode.getDistance()){
            adjacentNode.setDistance(source.getDistance()+weight);
            //System.out.println(adjacentNode.getDistance());

            Node temp = graph.getNode(String.valueOf(adjacentNode.id));
            if(adjacentNode.getDistance() < temp.getDistance())
            {
                temp.setDistance(adjacentNode.getDistance());
            }
            LinkedList<Node> shortestPath = new LinkedList<>(source.getShortestPath()); 
            shortestPath.add(source); 
            adjacentNode.setShortestPath(shortestPath);
        }
    }
    public static String dijkstrasAlgorithm(Graph graph, String start, String end)
    {
        Node startNode = graph.getNode(start); 
        startNode.setDistance(0); 

        Set<Node> visited = new HashSet<Node>(); 
        Set<Node> unvisited = new HashSet<Node>(); 

        unvisited.add(startNode); 
        while(unvisited.size() > 0){
            Node currentNode = calculateClosestNode(unvisited); 
            unvisited.remove(currentNode);
            for(Map.Entry<Node, Integer> adjacentNode: graph.getAdjacentVerticies(currentNode.id).entrySet()){
                if(!visited.contains(adjacentNode.getKey())){
                    // adjacentNode.getKey() = node
                    // adjacentNode.getValue() = node weight 
                    calculateShortestDistance(graph, adjacentNode.getKey(), currentNode, adjacentNode.getValue()); 
                    unvisited.add(adjacentNode.getKey()); 
                }
            }
             visited.add(currentNode);    
         }
         int shortestDistance = graph.getNode(end).getDistance(); 
         if(shortestDistance == Integer.MAX_VALUE) return("NO SUCH ROUTE").trim();
        return String.valueOf(graph.getNode(end).getDistance());
    }

        // Variation of depth first search to find the route 
        public void search(Graph graph, String start, String end, List<Character> visited)
        {
            Map<Node, Integer> startNodes = graph.getAdjacentVerticies(visited.get(visited.size()-1)); 
            for(Node node : startNodes.keySet()){
                if(visited.contains(node.id)) continue;
                if(node.id == end.charAt(0)){
                    visited.add(node.id); 
                    addPath(graph, visited); 
                    visited.remove(visited.size() - 1); 
                    break; 
                }
            }
            for(Node node: startNodes.keySet()){
                if(visited.contains(node.id) || node.id == end.charAt(0)){
                    continue;
                }
                visited.add(node.id);
                search(graph, start, end, visited); 
                visited.remove(visited.size() - 1); 
            }
        }

        public void addPath(Graph graph, List<Character> visited)
        {
            String path = "";
            for(char node : visited){
                path += String.valueOf(node);
            }
            graph.allPathsBetweenTwoNodes.add(path.trim()); 
        }


        
        public String calculateDistanceAlongRoute(String route)
        {
            int distanceAlongRoute  = getDistanceAlongRoute(route);
            if(distanceAlongRoute == 0) return("NO SUCH ROUTE").trim();
            else return (String.valueOf(distanceAlongRoute)).trim();
        }


        public void calculateAllPaths(Graph graph, String start, String end)
        {
            List<Character> visited = new ArrayList<Character>(); 
            visited.add(start.charAt(0));

            //if start node & end node are the same, get the start node's adjacent verticies and find all paths from 
            // the start node's adjacent nodes to the end, and return those
            if(start == end){
                Map<Node, Integer> adjacentNodes = graph.getAdjacentVerticies(start.charAt(0));
                for(Node node: adjacentNodes.keySet()){
                    List<Character> visitedTemp = new ArrayList<Character>(); 
                    visitedTemp.add(node.id);
                    search(graph, String.valueOf(node.id), end, visitedTemp);
                }; 
            }
            else{
                search(graph, start, end, visited);
            }
        }

        public int getRoutesWithLimitedPaths(Graph graph, String start, String end, int maxNumberOfStops)
        {
            calculateAllPaths(graph, start, end);
            int routes = 0; maxNumberOfStops++;
            for(String path : graph.allPathsBetweenTwoNodes){
                if(start == end) path = (start.charAt(0) + path).trim();
                if(path.length() < maxNumberOfStops){
                    routes++; 
                    // Debug: print out all paths that have number of stops <= maxNumberOfStops 
                    // System.out.println(path + " " + path.len gth()); 
                }
                // Debug: print all possible paths between two nodes 
                // System.out.println(path + " " + path.length());
            }
            return routes;  
        }

        public int getRoutesWithLimitedDistance(Graph graph, String start, String end, int maxDistance){
            calculateAllPaths(graph, start, end);
            Map<Integer, List<String>> distancesAndRoutes = new HashMap<Integer, List<String>>(); 
            for(String path: graph.allPathsBetweenTwoNodes){
                path = (start.charAt(0)+path).trim();
                int distance = graph.getDistanceAlongRoute(path); 
                List<String> listOfRoutes = distancesAndRoutes.getOrDefault(distance, new ArrayList<String>());
                listOfRoutes.add(path);
                distancesAndRoutes.put(distance, listOfRoutes); 
            }

            Set<String> allUniqueRoutes = new HashSet<String>(); 
            for(int route: distancesAndRoutes.keySet())
            {
                if(route <= maxDistance){
                    for(String path : distancesAndRoutes.get(route))
                    {
                        allUniqueRoutes.add(path); 
                    }
                    getVariations(distancesAndRoutes, allUniqueRoutes, route, maxDistance); 
                }
            }
            // return allUniqueRoutes.size() graph.getVariations(distancesAndRoutes, allUniqueRoutes, maxDistance);
            return -1; 
        }

        public int getVariations(Map<Integer, List<String>> distanceAndRoutes, Set<String> allUniqueRoutes, int route, int maxDistance)
        {



            return allUniqueRoutes.size(); 
        }
}


public class java_solution{
    
    public static Set<Character> getDistinctNodes(ArrayList<String> listOfNodes)
    {
        Set<Character> distinctNodes = new HashSet<Character>(); 
        for(String node: listOfNodes)
        {
            char start = node.charAt(0);
            char end = node.charAt(1); 
            if(!distinctNodes.contains(start)) distinctNodes.add(start); 
            if(!distinctNodes.contains(end)) distinctNodes.add(end); 
        }
        return distinctNodes; 
    }

    public static Graph createGraph(ArrayList<String> routes)
    {
        Graph graph = new Graph(); 
        // Create nodes 
        for(char id: getDistinctNodes(routes)){
            graph.addNode(id); 
        }
        // Create edges 
        for(String route: routes){
            char start = route.charAt(0);
            char end = route.charAt(1); 
            int weight = Integer.parseInt(String.valueOf(route.substring(2, route.length())));
            graph.addEdge(start, end, weight);
        }
        return graph; 
    }

    
    public static void main(String[] args)
    {
        ArrayList<String> test = new ArrayList<String>(); 
        test.add("AB5"); 
        test.add("BC4"); 
        test.add("CD8"); 
        test.add("DC8"); 
        test.add("DE6"); 
        test.add("AD5"); 
        test.add("CE2");
        test.add("EB3");
        test.add("AE7");
        
        // Create Graph 
        Graph graph = createGraph(test); 
  
        //1 
        // System.out.println(graph.calculateDistanceAlongRoute("ABC"));
        //4 
        //  System.out.println(graph.dijkstrasAlgorithm(graph, "E", "A")); 
        //  System.out.println(graph.dijkstrasAlgorithm(graph, "A", "C"));

        //3 
        // System.out.println(graph.getRoutesWithLimitedPaths(graph, "A", "D", 10));
        // System.out.println(graph.getRoutesWithLimitedPaths(graph, "C", "C", 10));

        System.out.println(graph.getRoutesWithLimitedDistance(graph, "C", "C", 20)); 
        // System.out.println(graph.getRoutesWithLimitedDistance(graph, "C", "C", 10));
        // e
    }
}