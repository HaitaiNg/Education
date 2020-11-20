/*
    Author: Haitai Ng 
    Refresh Date: 11.19.20 
    Description: The railroad service has come to you 

    Input: 

    Output: 
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

    // Part Two. The number of different routes between two towns 
    int getNumberOfRoutesWithLimitedStops(char start, char end, int maxNumberOfStops){
        return 0; 
    }

    // Part Three. The number of different routes between two towns 
    int getNumberOfRoutesWithLimitedDistance(char start, char end, int maxDistance){
        return 0; 
    }

    //  Shortest route between two towns 
    int getShortestRouteBetweenTwoTowns(char start, char end)
    {
        return 0; 
    }



    boolean executeAllQuestions(String route)
    {
        int distanceAlongRoute  = getDistanceAlongRoute(route);
        if(distanceAlongRoute == 0) System.out.println("NO SUCH ROUTE");
        else System.out.println("The distance of route " + route +  " " + String.valueOf(distanceAlongRoute));

        return false; 
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
            int weight = Integer.parseInt(String.valueOf(route.charAt(2))); 
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
        graph.executeAllQuestions("CDC");
    }
}