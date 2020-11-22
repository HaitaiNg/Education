import java.util.*; 

public class Ethic_Coding_Exercise {
    public Graph graph; 

    public Ethic_Coding_Exercise(){
        System.out.println("Graph Has Been Initialized"); 
        graph = new Graph(); 
    }

    public void createGraph(List<String> routes)
    {
        // Trim all leading  & trailing white spaces
        List<String> trimRoutes = new ArrayList<String>(); 
        for(String route: routes)
        {
            trimRoutes.add(route.trim()); 
        }
        routes = trimRoutes; 

        // Create Nodes 
        for(char id: getDistinctNodes(routes)){
            graph.addNode(id); 
        }
        // Create Edges 
        for(String route: routes){
            char start = route.charAt(0);
            char end = route.charAt(1); 
            int weight = Integer.parseInt(String.valueOf(route.substring(2, route.length())));
            graph.addEdge(start, end, weight);
        }
    }

    public Set<Character> getDistinctNodes(List<String> listOfNodes)
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

    // Question 1 : Return distance along route 
    public String getDistanceAlongRoute(String route){
        printDistanceAlongRoute(route);
        return graph.calculateDistanceAlongRoute(route); 
    }

    // Question 2 : Return the number of routes with limited # of stops 
    public String getRoutesWithLimitedStops(String start, String end, int maxNumberOfStops)
    {
        printRoutesWithLimitedStops(start, end, maxNumberOfStops);
        return String.valueOf(graph.getRoutesLimitedStops(graph, start, end, maxNumberOfStops));
    }

    // Question 3 : Return the number of routes with a distance less than max 
    public String getRoutesWithLimitedDistance(String start, String end, int maxDistance)
    {
        printRoutesWithLimitedDistance(start, end, maxDistance);
        return String.valueOf(graph.getRoutesLimitedDistance(graph, start, end, maxDistance));
    }

    // Question 4 : Return the shortest route between two towns 
    public String getShortestRouteBetweenTwoTowns(String start, String end){
        printGetShortestRouteBetweenTwoTowns(start, end);
        return graph.dijkstrasAlgorithm(graph, start, end); 
    }


    //< All print statements used for unit testing 
    public void printDistanceAlongRoute(String route){
        if(route == "\0") System.out.println(("The distance of the route _").trim()); 
        else System.out.println(("The distance of the route " + route).trim()); 
    }
    public void printRoutesWithLimitedStops(String start, String end, int maxNumberOfStops){
        if(start == "\0" || end == "\0") System.out.println("The number of trips starting at _ and ending at _ with a maximum of _ stops");
        else System.out.println("The number of trips starting at " + start + " and ending at " + end + " with a maximum of " + String.valueOf(maxNumberOfStops) + " stops");
    }
    public void printRoutesWithLimitedDistance(String start, String end, int maxDistance){
        if(start == "\0" || end == "\0") System.out.println("The number of different routes from _ to _ with a distance of less than _");
        else System.out.println("The number of different routes from " + start + " to " + end + " with a distance of less than " + String.valueOf(maxDistance));
    }
    public void printGetShortestRouteBetweenTwoTowns(String start, String end){
        if(start == "\0" || end == "\0") System.out.println("The length of the shortest route (in terms of distance to travel) from _ to _");
        else System.out.println("The length of the shortest route (in terms of distance to travel) from " + start + " to " + end);
    }
}
