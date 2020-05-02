package Graph;

import java.util.ArrayList;

/**
 * A vertex is a simple implementation of a vertex in a graph.
 */
public class Vertex {
    private String key;
    private ArrayList<Vertex> neighbors;

    public Vertex(String key, ArrayList<Vertex> neighbors){
        this.key = key;
        this.neighbors = neighbors;
    }

    public ArrayList<Vertex> getNeighbors() {
        return neighbors;
    }

    public String getKey(){
        return key;
    }
}
