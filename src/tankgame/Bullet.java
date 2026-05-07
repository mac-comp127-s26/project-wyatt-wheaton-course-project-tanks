package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.List;

public class Bullet {
    private double startX;
    private double startY;
    private Color color;
    private Obstacles o;
    private int numPoints = 16;
    private double diameter;
    List <Rectangle> rects;
    private double[][] bulletPoints = new double [numPoints] [2];
    Tank tank;
    

    double xVel;
    double yVel;

    CanvasWindow canvas;
    Ellipse shape;

    public Bullet(CanvasWindow canvas, Color color, Obstacles o, Tank tank) {
        this.canvas = canvas;
        this.o = o;
        this.tank = tank;
        this.startX = tank.cannonRotated()[0];
        this.startY = tank.cannonRotated()[1];
        this.diameter = 10;

        rects = o.getRects();
        xVel = 5 * Math.cos(tank.getAngle());
        yVel = 5 * Math.sin(tank.getAngle());

        shape = new Ellipse(startX - diameter / 2, startY - diameter / 2, diameter, diameter);
        shape.setFillColor(color);
        updateBulletPoints();
        canvas.add(shape);
    }

    // Moves bullet by xVel and yVel then checks for points collison with tank, if collision detected then move the bullet
    // to the tip of the cannon and re-update the angle
    void moveBullet() {
        shape.moveBy(xVel, yVel);
        updateBulletPoints();
        if (checkObstaclesHitbox(rects)) {
            shape.setPosition(tank.cannonRotated()[0] - diameter / 2, tank.cannonRotated()[1] - diameter / 2);
            xVel = 5 * Math.cos(tank.getAngle());
            yVel = 5 * Math.sin(tank.getAngle());
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

    // updates the array that contains the collsion points of the bullet ("numPoints" of them)
    private void updateBulletPoints() {
        double centerX = shape.getX();
        double centerY = shape.getY();
        double radius = diameter / 2;
        for (int i = 0; i < numPoints; i++) {
            double evenlySpacedAngle = 2 * Math.PI * i / numPoints;
            bulletPoints[i][0] = centerX + radius * Math.cos(evenlySpacedAngle);
            bulletPoints[i][1] = centerY + radius * Math.sin(evenlySpacedAngle);
        }
    }
 }

