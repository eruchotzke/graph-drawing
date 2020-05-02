package application;

import Graph.Graph;
import Graph.Vertex;
import Model.GraphContainer;
import Model.PhysicalVertex;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public static final double VERTEX_RADIUS = 60;
    public static final double TIMESCALE = .01;

    private AnimationTimer frameTimer;
    private GraphContainer graphContainer;
    private long prevTime = -1;

    private double dx;
    private double dy;

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
        dx = canvas.getWidth() / 2;
        dy = canvas.getHeight() / 2;

        /* Generate a graph to use for this project */
        ArrayList<Vertex> verts = new ArrayList<>();
        verts.add(new Vertex("VERTEX 1", null));
        verts.add(new Vertex("VERTEX 2", null));
        Graph g = new Graph(verts);
        graphContainer = new GraphContainer(g);

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
        //first clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //now draw each vertex
        for(PhysicalVertex v : graphContainer.vertices){
            drawVertex(v);
        }
    }

    private void drawVertex(PhysicalVertex v){
        drawCenteredNode(v.getPosition().getX(), v.getPosition().getY());
        gc.fillText(v.getModelledVertex().getKey(), v.getPosition().getX() + dx, v.getPosition().getY() + dy, 2 * VERTEX_RADIUS);
    }

    private void drawCenteredNode(double x, double y){
        gc.strokeOval(x + dx - VERTEX_RADIUS / 2, y + dy - VERTEX_RADIUS / 2, VERTEX_RADIUS, VERTEX_RADIUS);
    }


}
