package Model;

import Graph.*;
import java.util.ArrayList;

public class GraphContainer {

    public ArrayList<PhysicalVertex> vertices;

    public GraphContainer(Graph toModel){
        vertices = new ArrayList<>();

        for(Vertex v : toModel.getVertices()){
            vertices.add(new PhysicalVertex(v, 0, 0));
        }
    }

    public void timeStep(double delta){
        for(PhysicalVertex v : vertices){
            v.timeStep(delta, vertices);
        }
    }
}
