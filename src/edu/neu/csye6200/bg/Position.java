package edu.neu.csye6200.bg;

/**
 * @author fjj1213
 */
public class Position {

    protected double x;
    protected double y;

    public Position(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return ("Pos[" + x + "," + y + "]");
    }

}
