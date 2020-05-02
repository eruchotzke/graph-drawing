package Model;

import Graph.Vertex;

import java.util.ArrayList;

/**
 * A physical vertex is a vertex with physics properties.
 */
public class PhysicalVertex {

    public static double REPEL_FACTOR = 10;
    public static double DAMP_FACTOR = .5;
    public static double SPRING_REST = 300;
    public static double SPRING_FORCE = .001;

    private Vertex modelledVertex;

    private Vector2 position;
    private Vector2 oldPosition;
    private Vector2 acceleration;

    public PhysicalVertex(Vertex v, double x, double y){
        this.modelledVertex = v;
        this.position = new Vector2(x, y);
        this.oldPosition = new Vector2(x, y); //zero initial velocity
        this.acceleration = new Vector2();
    }

    /**
     * Update the acceleration for this vertex using the vertices around it.
     */
    public void updateAcceleration(GraphContainer model){
        acceleration = new Vector2(0, 0);
        //use an inverse square law to repel vertices
        for(PhysicalVertex v : model.vertices){
            if(v == this) continue;
            //get the normalized vector away from the other vertex
            Vector2 away = v.getPosition().getVectorTowards(position);
            //accelerate away at a proportional rate to distance.
            acceleration.add(Vector2.scale(away, REPEL_FACTOR / position.getDistance(v.position)));
        }

        //also, accelerate towards neighbors using a spring force, rest distance constant
        ArrayList<PhysicalVertex> neighbors = model.getNeighboringVertices(this);
        for(PhysicalVertex v : neighbors){
            //get the distance
            double dist = position.getDistance(v.position);
            double force = 0.5 * SPRING_FORCE * (dist - SPRING_REST);

            //now apply this to an acceleration vector
            acceleration.add(Vector2.scale(position.getVectorTowards(v.position), force));
        }
    }

    /**
     * Use verlet integration to calculate the new position of this object.
     */
    public void updatePosition(double delta){
        //first do the verlet integration over the x-axis
        double newX = position.getX() + DAMP_FACTOR * (position.getX() - oldPosition.getX()) + acceleration.getX() * delta * delta;
        double newY = position.getY() + DAMP_FACTOR * (position.getY() - oldPosition.getY()) + acceleration.getY() * delta * delta;

        oldPosition = position;
        position = new Vector2(newX, newY);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vertex getModelledVertex() {
        return modelledVertex;
    }
}

