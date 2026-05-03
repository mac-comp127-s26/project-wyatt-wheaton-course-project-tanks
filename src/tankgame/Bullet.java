package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.List;

public class Bullet {
    private double startX;
    private double startY;
    private double angle;
    private double radians;
    private Color color;
    private Obstacles o;
    List <Rectangle> rects;
    

    final double xVel;
    final double yVel;

    CanvasWindow canvas;
    Ellipse shape;

    public Bullet(CanvasWindow canvas, double startX, double startY, double angle, Color color, Obstacles o) {
        this.canvas = canvas;
        this.o = o;

        this.startX = startX;
        this.startY = startY;
        this.angle = angle;

        // the angle is fed in for clarity, but our calculations require radians
        radians = angle * (Math.PI / 180);
        rects = o.getRects();
        
        xVel = 5 * Math.cos(radians);
        yVel = 5 * Math.sin(radians);

        shape = new Ellipse(startX, startY, 10, 10);
        shape.setFillColor(color);
        canvas.add(shape);
    }

    // void for now, but will likely return a boolean with implementation of hit detection.
    void move() {
        shape.moveBy(xVel, yVel);
        if (checkObstaclesHitbox(rects)) {
            // remove it somehow?
        }

    }

    private boolean checkObstaclesHitbox(List <Rectangle> rects) {
        boolean hitCheck = false;
        for(Rectangle rect : rects) { // for every obstecle on the window
            if (rect.testHit(shape.getX(), shape.getY())) {
                hitCheck = true;
            }
        }
        return hitCheck;
    } 
}
