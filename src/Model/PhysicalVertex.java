package Model;

import Graph.Vertex;

import java.util.ArrayList;

/**
 * A physical vertex is a vertex with physics properties.
 */
public class PhysicalVertex {

    private Vertex modelledVertex;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    public PhysicalVertex(Vertex v, double x, double y){
        this.modelledVertex = v;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2();
        this.acceleration = new Vector2(1, 0);
    }

    /**
     * This method should be called to update the physics of the object.
     * @param delta The amount of time that has passed between calls.
     */
    public void timeStep(double delta, ArrayList<PhysicalVertex> others){
        //TODO calculate acceleration.
        this.velocity.add(Vector2.scale(acceleration, delta));
        this.position.add(Vector2.scale(velocity, delta));
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vertex getModelledVertex() {
        return modelledVertex;
    }
}

