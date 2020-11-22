

import java.util.*;

class Graph{

    // Node class definition 
    class Node{
        
        char id; 
        Node(char ID)
        {
            this.id = ID; 
        }

        private int distance = Integer.MAX_VALUE;
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
    // end Node class definition 


    // Graph member variables and methods 
    public Map<Node, Map<Node, Integer>> adjacencyList; 
    public List<String> allPathsBetweenTwoNodes; 
    public Graph(){
        adjacencyList = new HashMap<Node, Map<Node, Integer>>(); 
        allPathsBetweenTwoNodes = new ArrayList<String>(); 
    }

    void addEdge(Character idOne, Character idTwo, int weight){
        Node n2 = new Node(idTwo); 
        for(Node node: adjacencyList.keySet())
        {
            if(node.id == idOne){
                adjacencyList.get(node).put(n2, weight);
            }
        } 
    }

    void addNode(Character id){
        adjacencyList.putIfAbsent(new Node(id), new HashMap<Node, Integer>()); 
    }

    public void addPath(Graph graph, List<Character> visited)
    {
        String path = "";
        for(char node : visited){
            path += String.valueOf(node);
        }
        graph.allPathsBetweenTwoNodes.add(path.trim()); 
    }

    //Get vertices for a particular node 
    Map<Node, Integer> getAdjacentVerticies(char id){
        for(Node node: adjacencyList.keySet())
        {
            if(node.id == id)
            {
                return adjacencyList.get(node);
            }
        }
        return null;
    }

    Node getNode(String nodeName){
        for(Node node: adjacencyList.keySet()){
            if(node.id == nodeName.charAt(0)) return node; 
        }
        return null; 
    }

    // Get all possible paths from start node to end node 
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

    public Node calculateClosestNode(Set<Node> unvisited){
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

    public void calculateShortestDistance(Graph graph, Node adjacentNode, Node source, int weight){
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

    // Helper method to calculate the shortest route between two towns 
    public String dijkstrasAlgorithm(Graph graph, String start, String end)
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
                    //< adjacentNode.getKey() = node
                    //< adjacentNode.getValue() = node weight 
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


    public String calculateDistanceAlongRoute(String route)
    {
        int distanceAlongRoute  = getDistanceAlongRoute(route);
        if(distanceAlongRoute == 0) return("NO SUCH ROUTE").trim();
        else return (String.valueOf(distanceAlongRoute)).trim();
    }


    // Return the distance along a certain route 
    int getDistanceAlongRoute(String route){
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

    //Return the number of different routes between two towns with a distance of less than max distance
    public int getRoutesLimitedDistance(Graph graph, String start, String end, int maxDistance){
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
                // getVariations(distancesAndRoutes, allUniqueRoutes, route, maxDistance); 
            }
        }
        // return allUniqueRoutes.size() graph.getVariations(distancesAndRoutes, allUniqueRoutes, maxDistance);
        return allUniqueRoutes.size(); 
    }

    // Return the number of different routes between two towns with a maximum number of allocated stops 
    public int getRoutesLimitedStops(Graph graph, String start, String end, int maxNumberOfStops)
    {
        calculateAllPaths(graph, start, end);
        int routes = 0; maxNumberOfStops++;
        for(String path : graph.allPathsBetweenTwoNodes){
            if(start == end) path = (start.charAt(0) + path).trim();
            if(path.length() <= maxNumberOfStops){
                routes++; 
                // System.out.println(path + " " + path.length()); //< Debug: print out all paths that have number of stops <= maxNumberOfStops 
            }
            // System.out.println(path + " " + path.length());  //<Debug: print all possible paths between two nodes 
        }
        return routes;  
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
}
