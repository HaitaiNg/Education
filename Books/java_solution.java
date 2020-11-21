import java.util.*;
import java.util.ArrayList; 


class Edge{
    int start = 0;
    int end = 0;
    int weight = 0;
    
    public Node(int start, int end, int weight)
    {
        this.start = start ;
        this.end = end;
        this.weight = weight; 
    }
}

public class java_solution{
    public static void main(String[] args)
    {
        int vertex = 5;
        int[][] matrix = new int[vertex+1][vertex+1]; 

        ArrayList<Node> edgeList = new ArrayList<Edge>(); 
        edgeList.add(new (1,4,2)); 
        edgeList.add(new Edge(4,2,15)); 
        edgeList.add(new Edge(2,5,8)); 
        edgeList.add(new Edge(3,5,6));
        edgeList.add(new Edge(3,1,10)); 
        edgeList.add(new Edge(3,4,5)); 

        for(int i = 0; i < edgeList.size(); i++)
        {
            Edge currentEdge = edgeList.get(i); 
            int startVertex = currentEdge.start; 
            int endVertex = currentEdge.end; 
            int weight = currentEdge.weight;
            matrix[startVertex][endVertex] = weight;  
        }
        
        for(int i = 1; i <= vertex; i++)
        {
            for(int j = 1; j <= vertex; j++)
            {
                System.out.print(matrix[i][j] + " "); 
            }
            System.out.println(); 
        }
    }
}
