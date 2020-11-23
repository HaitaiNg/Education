import java.util.*;
import org.junit.Test;
import org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Unit_Test_I {
    Ethic createExecutable(String routes){
        Ethic ethic = new Ethic();
        ethic.createGraph(Arrays.asList(routes.split(",")));
        return ethic;
    }

    @Test
    public void sample_input() {
        Ethic sample = createExecutable("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
        assertEquals("9", sample.getDistanceAlongRoute("ABC"));
        assertEquals("2", sample.getRoutesWithLimitedStops("C", "C", 3));
        assertEquals("7", sample.getRoutesWithLimitedDistance("C", "C", 30));
        assertEquals("9", sample.getShortestRouteBetweenTwoTowns("A", "C"));
    }

    /* Component testing (Testing each function using the graph provided
     in the problem description */

    @Test //< Question One
    public void distanceAlongRoute() {
        Ethic sample = createExecutable("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
        assertEquals("26", sample.getDistanceAlongRoute("ABCDEB"));
        assertEquals("24", sample.getDistanceAlongRoute("DEBCEBCE"));
        assertEquals("18", sample.getDistanceAlongRoute("CDCE"));
        assertEquals("NO SUCH ROUTE", sample.getDistanceAlongRoute("AC"));
        assertEquals("NO SUCH ROUTE", sample.getDistanceAlongRoute("BA"));
    }

    @Test //< Question Two
    public void routesWithLimitedStops(){
        Ethic sample = createExecutable("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
        assertEquals("2", sample.getRoutesWithLimitedStops("C", "C", 3));
        assertEquals("2", sample.getRoutesWithLimitedStops("A", "C", 2));
        assertEquals("1", sample.getRoutesWithLimitedStops("C", "E", 1));
        assertEquals("2", sample.getRoutesWithLimitedStops("C", "E", 2));

    }

    @Test //< Question Three
    public void routesWithLimitedDistance(){
        Ethic sample = createExecutable("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
        assertEquals("7", sample.getRoutesWithLimitedDistance("C", "C", 30));
        assertEquals("4", sample.getRoutesWithLimitedDistance("C", "E", 10));
    }

    @Test //< Question Four
    public void shortestRoutesBetweenTwoTowns(){
        Ethic sample = createExecutable("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
        assertEquals("9", sample.getShortestRouteBetweenTwoTowns("A", "C"));
        assertEquals("2", sample.getShortestRouteBetweenTwoTowns("C", "E"));
        assertEquals("5", sample.getShortestRouteBetweenTwoTowns("C", "B"));
    }

    @Test //< Tokenize String
    public void tokenize(){
        Ethic sample = createExecutable("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
        sample.tokenizeString("The distance of the route ABC.");
        sample.tokenizeString("The number of trips starting at C and ending at C with a maximum of 3 stops");
        sample.tokenizeString("The number of different routes from C to C with a distance of less than 30");
        sample.tokenizeString("The length of the shortest route (in terms of distance to travel) from A to C");
    }
}
