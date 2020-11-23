
import java.util.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ethic {
    public Graph graph;

    public Ethic(){
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
        return graph.calculateDistanceAlongRoute(route);
    }

    // Question 2 : Return the number of routes with limited # of stops
    public String getRoutesWithLimitedStops(String start, String end, int maxNumberOfStops)
    {
        return String.valueOf(graph.getRoutesLimitedStops(graph, start, end, maxNumberOfStops));
    }

    // Question 3 : Return the number of routes with a distance less than max
    public String getRoutesWithLimitedDistance(String start, String end, int maxDistance) {
        return String.valueOf(graph.getRoutesLimitedDistance(graph, start, end, maxDistance));
    }

    // Question 4 : Return the shortest route between two towns
    public String getShortestRouteBetweenTwoTowns(String start, String end){
        return graph.dijkstrasAlgorithm(graph, start, end);
    }

    public void tokenizeString(String question){
        String[] tokenString = question.split(" ");
//        int idx = 0;
//        for(String c : tokenString){
//            System.out.println( String.valueOf(idx) + " " + c );
//            idx++;
//        }

        System.out.println(question);
        String result = "";
        if(question.contains("distance of the route")){
            tokenString[5] = tokenString[5].replace(".","");
            result = getDistanceAlongRoute(tokenString[5]);
        }
        else if(question.contains("number of trips")){
            result = getRoutesWithLimitedStops(tokenString[6], tokenString[10], Integer.parseInt(tokenString[15]));
        }
        else if(question.contains("number of different routes from")){
            result = getRoutesWithLimitedDistance(tokenString[6], tokenString[8], Integer.parseInt(tokenString[15]));
        }
        else if(question.contains("length of the shortest route")){
            result = getShortestRouteBetweenTwoTowns(tokenString[13], tokenString[15]);
        }
        else{
            result = "Error : the question is not in an acceptable format";
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        System.out.print("Please provide the absolute path to your input file: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = reader.readLine().trim();
        System.out.println(fileName);
        try{
            BufferedReader readFile = new BufferedReader(new FileReader(fileName));
            Ethic executable = new Ethic();
            String graph = readFile.readLine();
            System.out.println("Creating graph: " + graph);
            executable.createGraph(Arrays.asList(graph.split(",")));
            String question;
            while( (question = readFile.readLine()) != null){
                executable.tokenizeString(question);
            }
        }
        catch(Exception e){
            System.err.format("Exception occurred trying to read");
            e.printStackTrace();
        }
    }
}
