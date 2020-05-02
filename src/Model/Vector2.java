package Model;

/**
 * A simple class to model a 2D point.
 */
public class Vector2 {

    private double x, y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2(){
        this.x = 0;
        this.y = 0;
    }

    public void add(Vector2 other){
        x += other.x;
        y += other.y;
    }

    public void scale(double value){
        x *= value;
        y *= value;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /* Static Methods */

    public static Vector2 add(Vector2 vector1, Vector2 vector2){
        return new Vector2(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    public static Vector2 scale(Vector2 vector, double amount){
        return new Vector2(vector.x * amount, vector.y * amount);
    }
}
