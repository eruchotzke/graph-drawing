package Model;

import Graph.*;
import java.util.ArrayList;
import java.util.Random;

public class GraphContainer {

    public ArrayList<PhysicalVertex> vertices;

    public GraphContainer(Graph toModel, double maxX, double maxY){
        vertices = new ArrayList<>();

        Random rand = new Random();
        for(Vertex v : toModel.getVertices()){
            vertices.add(new PhysicalVertex(v, rand.nextDouble() * maxX / 2 - maxX / 2, rand.nextDouble() * maxY / 2 - maxY / 2));
        }
    }

    public ArrayList<PhysicalVertex> getNeighboringVertices(PhysicalVertex v){
        ArrayList<PhysicalVertex> ret = new ArrayList<>();

        Vertex vert = v.getModelledVertex();

        for(PhysicalVertex pv : vertices){
            if(vert.getNeighbors().contains(pv.getModelledVertex())){
                ret.add(pv);
            }
        }

        return ret;
    }

    public void timeStep(double delta){
        for(PhysicalVertex v : vertices){
            v.updateAcceleration(this);
        }

        for(PhysicalVertex v : vertices){
            v.updatePosition(delta);
        }
    }
}
