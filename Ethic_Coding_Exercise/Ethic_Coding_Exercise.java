
import java.util.*; 
import java.util.Collections; 

public class Ethic_Coding_Exercise {

    public static void sample_input()
    {
        String routes = "AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7"; 
        String[] values = routes.split(","); 
        List<String> listOfRoutes = Arrays.asList(values); 
        Graph graph = graph(listOfRoutes);
    }

    // javac *.java
    public static void main(String[] args)
    {
        sample_input();
        //1 
        // System.out.println(graph.calculateDistanceAlongRoute("ABC"));
        //4 
        //  System.out.println(graph.dijkstrasAlgorithm(graph, "E", "A")); 
        //  System.out.println(graph.dijkstrasAlgorithm(graph, "A", "C"));

        //3 
        // System.out.println(graph.getRoutesWithLimitedPaths(graph, "A", "D", 10));
        // System.out.println(graph.getRoutesWithLimitedPaths(graph, "C", "C", 10));

        // System.out.println(graph.getRoutesWithLimitedDistance(graph, "C", "C", 20)); 
        // System.out.println(graph.getRoutesWithLimitedDistance(graph, "C", "C", 10));
        // e
    }






    
}
