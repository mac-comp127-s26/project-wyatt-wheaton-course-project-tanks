package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import java.awt.Color;

public class Bullet {
    private double startX;
    private double startY;
    private double angle;
    private double radians;
    private Color color;
    

    final double xVel;
    final double yVel;

    CanvasWindow canvas;
    Ellipse shape;

    public Bullet(CanvasWindow canvas, double startX, double startY, double angle, Color color) {
        this.canvas = canvas;

        this.startX = startX;
        this.startY = startY;
        this.angle = angle;

        // the angle is fed in for clarity, but our calculations require radians
        radians = angle * (Math.PI / 180);
        
        xVel = 5 * Math.cos(radians);
        yVel = 5 * Math.sin(radians);

        shape = new Ellipse(startX, startY, 10, 10);
        shape.setFillColor(color);
        canvas.add(shape);
    }

    boolean move() {
        shape.moveBy(xVel, yVel);
        return true;
    }


}
