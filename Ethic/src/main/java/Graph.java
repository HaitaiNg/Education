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
        private List<Node> shortestPath = new LinkedList<Node>();
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

    public void calculateAllPaths(Graph graph, String start, String end)
    {
        List<Character> visited = new ArrayList<Character>();
        visited.add(start.charAt(0));
        // if start node & end node are the same, get the start node's adjacent verticies and find all paths from
        // the start node's adjacent nodes to the end, and return those
        if(start.equals(end)){
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
            Node temp = graph.getNode(String.valueOf(adjacentNode.id));
            if(adjacentNode.getDistance() < temp.getDistance())
            {
                temp.setDistance(adjacentNode.getDistance());
            }
            LinkedList<Node> shortestPath = new LinkedList<Node>(source.getShortestPath());
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

    public Set<String> computeCycles(Set<String> cycles, List<String> routes, int multiple, String prefix){
        if(multiple == 0){
            cycles.add(prefix);
            return cycles;
        }
        for(int i = 0; i < routes.size(); i++)
        {
            String newPrefix = prefix + routes.get(i);
            computeCycles(cycles, routes, multiple-1, newPrefix);
        }
        return cycles;
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
            if(start.equals(end) || path.charAt(0) != start.charAt(0)) path = (start.charAt(0) + path).trim();
            int distance = graph.getDistanceAlongRoute(path);
            List<String> listOfRoutes = distancesAndRoutes.getOrDefault(distance, new ArrayList<String>());
            listOfRoutes.add(path);
            distancesAndRoutes.put(distance, listOfRoutes);
        }

        Set<String> allUniqueRoutes = new HashSet<String>();
        for(int route: distancesAndRoutes.keySet()) {
            if(route <= maxDistance){
                for(String path : distancesAndRoutes.get(route)) {
                    allUniqueRoutes.add(path);
                    //< Debug
//                    System.out.println(String.valueOf(route) + " " + path);
                }
            }
        }
        //< Recursively add single cycles (ex: CEBCCEBC, CEBCCEBCCEBC)
        getSingleCycleVariations(allUniqueRoutes, distancesAndRoutes, maxDistance);
        //< Recursively add all variant cycles (ex: CDCCEBC, CEBCCDC)]
        getVariantCycles(allUniqueRoutes, distancesAndRoutes, maxDistance);

        //< Debug
//        for(String route : allUniqueRoutes){
//            System.out.println(route);
//        }
        Set<String> checkResult = new HashSet<String>();
        for(String route: allUniqueRoutes){
            if(route.charAt(route.length() - 1) == end.charAt(0)) checkResult.add(route);
        }
//        System.out.println();
        //< Debug
//        for(String route : checkResult){
//            System.out.println(route);
//        }

        return checkResult.size();
    }

    //< Note: Not a very efficient solution for getting variations
    Set<String> getVariantCycles(Set<String> allUniqueRoutes, Map<Integer, List<String>> distanceAndRoutes, int maxDistance){
        for(int distance : distanceAndRoutes.keySet()){
            List<String> base = distanceAndRoutes.get(distance);
            for(int subRoute : distanceAndRoutes.keySet()){
               if(distance + subRoute < maxDistance){
                   base.addAll(distanceAndRoutes.get(subRoute));
                   Set<String> baseSet = new HashSet<String>(base); //< remove duplicates
                   List<String> baseList = new ArrayList<String>(baseSet);
                   permutate(allUniqueRoutes, baseList);
               }
            }
        }
        return allUniqueRoutes;
    }

    Set<String> getSingleCycleVariations(Set<String> allUniqueRoutes, Map<Integer, List<String>> distancesAndRoutes, int maxDistance){
        for(int distance : distancesAndRoutes.keySet())
        {
            int tmp = distance; int multiple = 1;
            while(tmp < maxDistance){
                if(tmp + distance >= maxDistance) break;
                else{
                    multiple++;
                    tmp += distance;
                    Set<String> cycles = getGraphCycles(distancesAndRoutes.get(distance), multiple);
                    for(String cycle: cycles){
                        allUniqueRoutes.add(cycle);
                        //< Debug
//                        System.out.println(String.valueOf(tmp) + " " + cycle);
                    }
                }
            }
        }
        return allUniqueRoutes;
    }

    public Set<String> getGraphCycles(List<String> routes, int multiple){
        Set<String> cycles = new HashSet<String>();
        cycles = computeCycles(cycles, routes, multiple, "");
        return cycles;
    }

    // Return the number of different routes between two towns with a maximum number of allocated stops
    public int getRoutesLimitedStops(Graph graph, String start, String end, int maxNumberOfStops){
        calculateAllPaths(graph, start, end);
        Set<String> routes = new HashSet<String>();
        maxNumberOfStops++;
        for(String path : graph.allPathsBetweenTwoNodes){
            if(start.equals(end) || path.charAt(0) != start.charAt(0)) path = (start.charAt(0) + path).trim();
            if(path.length() <= maxNumberOfStops && path.charAt(path.length()-1) == end.charAt(0)){
                routes.add(path);
//                 System.out.println(path + " " + path.length()); //< Debug: print out all paths that have number of stops <= maxNumberOfStops
            }
//             System.out.println(path + " " + path.length());  //< Debug: print all possible paths between two nodes
        }
        return routes.size();
    }

    public Set<String> permutate(Set<String> allUniqueRoutes, List<String> routes){
        List<String> result = new ArrayList<>();
        String[] array = new String[routes.size()];
        int index = 0;
        for(String route : routes) array[index++]=route;
        permutateAlgorithm(result, new ArrayList<>(), array);
        for(String path : result){
            //< Debug
//            System.out.println(path);
            allUniqueRoutes.add(path);
        }
        return allUniqueRoutes;
    }

    public void permutateAlgorithm(List<String> tmp, List<String> result, String[] array){
        if(result.size() == array.length){
            tmp.add(String.join("", result));
        }
        else{
            for(int i = 0; i < array.length; i++){
                if(result.contains(array[i])) continue;
                result.add(array[i]); //<  add
                permutateAlgorithm(tmp, result, array); // recursive call
                result.remove(result.size()-1); // unchoose
            }
        }
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
