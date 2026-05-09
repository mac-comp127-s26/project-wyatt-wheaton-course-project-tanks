package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.List;

/**
* Authors: Wheaton Gribb and Wyatt Petersen
*
* This class is paired with a tank to continously shoot bullets at given angle.
* There is one instance of bullet per tank, and methods within to check if it hit another tank.
* These methods will be used by the game logic in main.
*/

public class Bullet {
    private int numPoints = 16;
    private double startX;
    private double startY;
    private double diameter;
    private double[][] bulletPoints = new double [numPoints] [2];
    private double xVel;
    private double yVel;
    private List <Rectangle> rects;
    private Tank tank;
    Ellipse shape;

    public Bullet(CanvasWindow canvas, Color color, Obstacles o, Tank tank) {
        this.tank = tank;
        this.startX = tank.cannonRotated()[0];
        this.startY = tank.cannonRotated()[1];
        this.diameter = 10;

        // stores rectangle list and set x and y velocities according to tank angle (in radians)
        rects = o.getRects();
        xVel = 5 * Math.cos(tank.getAngle());
        yVel = 5 * Math.sin(tank.getAngle());


        // creates bullet where the cannonTip is, diameter / 2 is the left corner of ellipse offset
        shape = new Ellipse(startX - diameter / 2, startY - diameter / 2, diameter, diameter);
        shape.setFillColor(color);
        canvas.add(shape);

        // store bullet Collision points
        updateBulletPoints();
    }

    // Moves bullet by xVel and yVel then checks for points collison with obstacles, if collision detected then move the bullet
    // to the tip of the cannon and re-update the angle
    void moveBullet() {
        shape.moveBy(xVel, yVel);
        updateBulletPoints(); // recalculates collsion points of bullet after moving
        if (checkObstaclesHitbox(rects)) {
            shape.setPosition(tank.cannonRotated()[0] - diameter / 2, tank.cannonRotated()[1] - diameter / 2);
            xVel = 5 * Math.cos(tank.getAngle());
            yVel = 5 * Math.sin(tank.getAngle());
        }
    }

    // checks every Obstacle on screen against the numPoint number of bullet collsion points, if any overlap then returns true
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

    // checks every bullet collison point in one array against the collison points of the given tank and 
    // returns true if the distance between the two in a straight line is less than the diameter / 2
    public boolean checkPlayerHit(Tank tank) {
        for (double [] bPoint : bulletPoints) {
            for (double [] tPoint : tank.bodyPoints) {
                double dx = tPoint[0] - bPoint[0];
                double dy = tPoint[1] - bPoint[1];
                double hypothenuse = Math.sqrt((dx * dx) + (dy * dy));
                if (hypothenuse <= diameter / 2) {
                    return true;
            }
            }
        }
        return false;
    }

    // method for reseting the position of the bullet, xVel, yVel, and Bullet collision points after reseting the game
    public void reset() {
        shape.setPosition(tank.cannonRotated()[0] - diameter / 2, tank.cannonRotated()[1] - diameter / 2);
        xVel = 5 * Math.cos(tank.getAngle());
        yVel = 5 * Math.sin(tank.getAngle());
        updateBulletPoints();
    }
 }

