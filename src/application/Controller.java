package application;

import Graph.Graph;
import Graph.Vertex;
import Model.GraphContainer;
import Model.PhysicalVertex;
import Model.Vector2;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public static final double VERTEX_RADIUS = 60;
    public static final double TIMESCALE = .1;

    private AnimationTimer frameTimer;
    private GraphContainer graphContainer;
    private long prevTime = -1;

    /* Canvas transform values */
    private Vector2 translation;
    private Vector2 scale;

    @FXML
    public Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized.");
        gc = canvas.getGraphicsContext2D();

        /* Do some housekeeping to the canvas settings */
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        /* Re-center the canvas offsets */
        translation = new Vector2(canvas.getWidth() / 2, canvas.getHeight() / 2);
        scale = new Vector2(2, 2);

        /* Generate a graph to use for this project */
        ArrayList<Vertex> verts = new ArrayList<>();
        verts.add(new Vertex("VERTEX 0", new ArrayList<>()));
        verts.add(new Vertex("VERTEX 1", new ArrayList<>()));
        verts.add(new Vertex("VERTEX 2", new ArrayList<>()));
        verts.add(new Vertex("VERTEX 3", new ArrayList<>()));
        verts.add(new Vertex("VERTEX 4", new ArrayList<>()));

        Graph.connectVertices(verts.get(0), verts.get(1));
        Graph.connectVertices(verts.get(1), verts.get(2));
        Graph.connectVertices(verts.get(2), verts.get(3));
        Graph.connectVertices(verts.get(3), verts.get(0));
        Graph.connectVertices(verts.get(0), verts.get(4));

        Graph g = new Graph(verts);
        graphContainer = new GraphContainer(g, canvas.getWidth(), canvas.getHeight());

        /* Create an animation timer for the canvas */
        frameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(prevTime == -1) prevTime = now;

                graphContainer.timeStep((now - prevTime) / 1000000000 * TIMESCALE);
                drawGraph();
            }
        };

        frameTimer.start();
    }

    private void drawGraph(){
        //very first manipulate transform values

        //first clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //now draw each vertex
        for(PhysicalVertex v : graphContainer.vertices){
            drawVertex(v);
            for(PhysicalVertex neighbor : graphContainer.getNeighboringVertices(v)){
                drawVertexConnection(v, neighbor);
            }
        }
    }

    /**
     * Draw a vertex, along with its label.
     * @param v
     */
    private void drawVertex(PhysicalVertex v){
        drawCenteredNode(v.getPosition().getX(), v.getPosition().getY());
        gc.fillText(v.getModelledVertex().getKey(), v.getPosition().getX() + translation.getX(), v.getPosition().getY() + translation.getY(), 2 * VERTEX_RADIUS);
    }

    /**
     * Draw an edge between two connected vertices.
     * @param v1
     * @param v2
     */
    private void drawVertexConnection(PhysicalVertex v1, PhysicalVertex v2){
        gc.strokeLine(v1.getPosition().getX() + translation.getX(), v1.getPosition().getY() + translation.getY(), v2.getPosition().getX() + translation.getX(), v2.getPosition().getY() + translation.getY());
    }

    private void drawCenteredNode(double x, double y){
        gc.strokeOval(x + translation.getX() - VERTEX_RADIUS / 2, y + translation.getY() - VERTEX_RADIUS / 2, VERTEX_RADIUS, VERTEX_RADIUS);
    }
}
