package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A graph is a simple implementation of a graph
 * data structure. We will use an adjacency list.
 */
public class Graph {
    private ArrayList<Vertex> vertices;

    public Graph(ArrayList<Vertex> vertices){
        this.vertices = vertices;
    }

    /**
     * Get a reference to the list of vertices in the graph.
     * @return An arraylist of vertices.
     */
    public ArrayList<Vertex> getVertices(){
        return vertices;
    }
}
