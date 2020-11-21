/*
    Author: Haitai Ng 
    Refresh Date: 11.19.20 
    Description: The railroad service has come to you 
*/ 

import java.util.*;
import java.util.Map;
import java.util.ArrayList; 


class Graph{

    class Node{
        char id; 
        Node(char ID)
        {
            this.id = ID; 
        }
        public int distance = Integer.MAX_VALUE; //< this is used for Dijkstra's algorithm 
        public int getDistance(){
            return distance; 
        }
        public void setDistance(int distance){
            this.distance = distance; 
        }
        public List<Node> shortestPath = new LinkedList<>(); 
        public List<Node> getShortestPath(){
            return shortestPath; 
        }
        public void setShortestPath(LinkedList<Node> shortestPath){
            this.shortestPath = shortestPath;}
    }
    
    public Map<Node, Map<Node, Integer>> adjacencyList; 


    public Graph(){
        adjacencyList = new HashMap<Node, Map<Node, Integer>>(); 
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

    //Depth first search. Given a start, find all possible connections to other stations
    Set<Character> depthFirstSearchTraversal(Graph graph, String root){
        Set<Character> visited = new LinkedHashSet<Character>(); 
        Stack<Character> stack = new Stack<Character>(); 
        stack.push(root.charAt(0)); 
        while(!stack.isEmpty()){
            char vertex = stack.pop(); 
            if(!visited.contains(vertex)){
                visited.add(vertex); 
                Map<Node, Integer> nodeMap = graph.getAdjacentVerticies((root.charAt(0)));
                for(Node node : nodeMap.keySet())
                {
                    stack.push(node.id);
                }
            }
        }
        return visited; 
    }

    //Breadth First Search
    // Set<Character> breadthFirstSearchTraversal(Graph graph, String start, String end)
    // {
    //     Set<Character> visited = new LinkedHashSet<Character>(); 
    //     Queue<Character> queue = new LinkedList<Character>(); 
    //     queue.add(start.charAt(0)); 
    //     visited.add(start.charAt(0)); 
    //     System.out.println(start.charAt(0));
    //     while(!queue.isEmpty()){
    //         Character vertex = queue.poll(); 
    //         System.out.print(vertex); 
    //         Map<Node, Integer> nodeMap = graph.getAdjacentVerticies(start.charAt(0)); 
    //         for(Node node : nodeMap.keySet()){
    //             if(!visited.contains(node.id)){
    //                 visited.add(node.id); 
    //                 queue.add(node.id); 
    //             }
    //             if(node.id == end.charAt(0)) System.out.println(node.id);
    //         }
    //     }
    //     return visited;
    // }

    //Breadth First Search
    // Set<Character> breadthFirstSearchTraversal(Graph graph, String start, String end)
    // {
    //     Set<Character> visited = new HashSet<Character>(); 
    //     Set<Character> currentPaths = new HashSet<Character>(); 
    //     return null;


    // }
    // // Part Two. The number of different routes between two towns 
    // int getNumberOfRoutesWithLimitedStops(Graph graph, String start, String end, int maxNumberOfStops){
        
    //     Set<Character> startRoutes = graph.depthFirstSearchTraversal(graph, start); 
    //     if(!startRoutes.contains(end.charAt(0))) return 0; 

    //     return 50; 
    // }

    boolean numberOne(String route)
    {
        // int distanceAlongRoute  = getDistanceAlongRoute(route);
        // if(distanceAlongRoute == 0) System.out.println("NO SUCH ROUTE");
        // else System.out.println("The distance of route " + route +  " " + String.valueOf(distanceAlongRoute));

        return false; 
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
            //Node z = graph.getNode(String.valueOf(adjacentNode.id)); 
            //System.out.println(z.distance);




            LinkedList<Node> shortestPath = new LinkedList<>(source.getShortestPath()); 
            shortestPath.add(source); 
            adjacentNode.setShortestPath(shortestPath);
        }
    }
    public static int dijkstrasAlgorithm(Graph graph, String start, String end)
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

         Node b = graph.getNode("Z");
         System.out.println(b.getDistance());

        return -1;  
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
        test.add("DC16"); 
        test.add("DE6"); 
        test.add("AD5"); 
        test.add("CE2");
        test.add("EB3");
        test.add("AE7");
        
        test.add("DZ50");

        // Create Graph 
        Graph graph = createGraph(test); 
        // graph.executeAllQuestions("CC");


        // Set<Character> resultOfA  = graph.depthFirstSearchTraversal(graph, "C");
        // System.out.println("-----"); 
        // for(Character x : resultOfA)
        // {
        //     System.out.println(x);
        // }
        // int boom = graph.getNumberOfRoutesWithLimitedStops(graph, "D", "A", 4); 
        // System.out.println(boom);
        // Set<Character> resultOfB  = graph.breadthFirstSearchTraversal(graph, "C", "A");
        // System.out.println("-----"); 
        // for(Character x : resultOfB)
        // {
        //     System.out.println(x);
        // }

        System.out.println(graph.dijkstrasAlgorithm(graph, "A", "C"));
    }
}