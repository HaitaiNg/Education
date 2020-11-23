import java.util.*;
import org.junit.Test;
import org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Unit_Test_II {
    Ethic createExecutable(String routes){
        Ethic ethic = new Ethic();
        ethic.createGraph(Arrays.asList(routes.split(",")));
        return ethic;
    }

    @Test
    public void sample_input_III() {
        Ethic sample = createExecutable("HJ4,HL3,HM4,HN10,JH4,JK5,KJ6,LH2,MH2,MN9,NM8");
        //< Question One
        assertEquals("13", sample.getDistanceAlongRoute("HMN"));
        assertEquals("18", sample.getDistanceAlongRoute("HNM"));
        assertEquals("12", sample.getDistanceAlongRoute("LHN"));
        assertEquals("34", sample.getDistanceAlongRoute("LHMNMHJK"));
        //< Question Two
        assertEquals("0", sample.getRoutesWithLimitedStops("N", "K", 3));
        assertEquals("1", sample.getRoutesWithLimitedStops("N", "K", 5));
        //< Question Three
        assertEquals("4", sample.getRoutesWithLimitedDistance("L", "M", 20));
        assertEquals("2", sample.getRoutesWithLimitedDistance("L", "M", 15));
        assertEquals("4", sample.getRoutesWithLimitedDistance("L", "M", 21));
        //< Question Four
        assertEquals("12", sample.getShortestRouteBetweenTwoTowns("L", "N"));
    }
}
