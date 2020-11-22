import java.util.*;



public class unit_tests {
    
    //< Cumulative testing 
    // Unit test that for the provided sample input 
    public static void sample_input(String routes)
    {
        Ethic_Coding_Exercise sample_test = new Ethic_Coding_Exercise();
        sample_test.createGraph(Arrays.asList(routes.split(",")));
        System.out.println(sample_test.getDistanceAlongRoute("ABC"));
        System.out.println(sample_test.getRoutesWithLimitedStops("C", "C", 3));
        System.out.println(sample_test.getRoutesWithLimitedDistance("C", "C", 30));
        System.out.println(sample_test.getShortestRouteBetweenTwoTowns("A", "C"));
        assertTrue(9, sample_test.getDistanceAlongRoute("ABC")); 
    }
 
    // Component testing (Testing each function thoroughly)
    // Question One : 
    public static void sample_input_extended_DistanceAlongRoute(String routes)
    {
    }

    // Question Two 
    public static void sample_input_extended_RoutesWithLimitedStops(String routes)
    {

    }

    // Question Three 
    public static void sample_input_extended_RoutesWithLimitedDistance(String routes){

    }

    // Question Four 
    public static void sample_input_extended_ShortestRoutesBetweenTwoTowns(String routes){

    }

    // javac *.java
    public static void main(String[] args)
    {
        
        sample_input("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7"); 
        System.out.println();
        sample_input_extended("AB50,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");

    }
}
