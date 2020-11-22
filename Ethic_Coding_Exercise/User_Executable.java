import java.util.*;

public class User_Executable {
    

    public static void sample_input(String routes)
    {
        Ethic_Coding_Exercise sample_test = new Ethic_Coding_Exercise();
        sample_test.createGraph(Arrays.asList(routes.split(",")));
        System.out.println(sample_test.getDistanceAlongRoute("ABC"));
        System.out.println(sample_test.getRoutesWithLimitedStops("C", "C", 3));
        System.out.println(sample_test.getRoutesWithLimitedDistance("C", "C", 30));
        System.out.println(sample_test.getShortestRouteBetweenTwoTowns("A", "C"));
    }


    /* print statements that need to be added 
    The distance of the route 
    The number of trips starting at _ and ending at _ with a maximum of _ stops 
    THe number of different routes from _ to _ with a distance of less than _ 
    The length of the shortest route (in terms of distance to travel) from _ to _ 


    */ 

    // javac *.java
    public static void main(String[] args)
    {
        
        sample_input("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7"); 

    }
}
