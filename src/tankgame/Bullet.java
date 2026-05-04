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
    private int numPoints = 16;
    private double diameter;
    List <Rectangle> rects;
    private double[][] bulletPoints = new double [numPoints] [2];
    Tank tank;
    

    final double xVel;
    final double yVel;

    CanvasWindow canvas;
    Ellipse shape;

    public Bullet(CanvasWindow canvas, double angle, Color color, Obstacles o, Tank tank) {
        this.canvas = canvas;
        this.o = o;

        this.tank = tank;
        this.startX = tank.t.getX();
        this.startY = tank.t.getY();
        this.angle = angle;
        this.diameter = 10;

        // the angle is fed in for clarity, but our calculations require radians
        radians = angle * (Math.PI / 180);
        rects = o.getRects();
        
        xVel = 5 * Math.cos(radians);
        yVel = 5 * Math.sin(radians);

        shape = new Ellipse(startX, startY, diameter, diameter);
        shape.setFillColor(color);

        updateBulletPoints();

        canvas.add(shape);
    }

    // void for now, but will likely return a boolean with implementation of hit detection.
    void moveBullet() {
        shape.moveBy(xVel, yVel);
        updateBulletPoints();
        if (checkObstaclesHitbox(rects)) {
            shape.setPosition(tank.t.getX(), tank.t.getY());
        }

    }

    private boolean checkObstaclesHitbox(List <Rectangle> rects) {
        boolean hitCheck = false;
        for(Rectangle rect : rects) { // for every obstecle on the window
            for (double [] point : bulletPoints) {
                if (rect.testHit(point[0], point[1])) {
                    hitCheck = true;
                }
            }
            
        }
        return hitCheck;
    } 

    private void updateBulletPoints() {
        double centerX = shape.getX() + 5;
        double centerY = shape.getY() + 5;
        double radius = diameter / 2;

        for (int i = 0; i < numPoints; i++) {
            double evenlySpacedAngle = 2 * Math.PI * i / numPoints;
            bulletPoints[i][0] = centerX + radius * Math.cos(evenlySpacedAngle);
            bulletPoints[i][1] = centerY + radius * Math.sin(evenlySpacedAngle);
        }

    }
}
