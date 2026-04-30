package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.GraphicsObject;



import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Set;

// TODO: Implement javadoc

public class Tank {
    GraphicsGroup t;
    int controlScheme;
    CanvasWindow canvas;
    private double MaxX;
    private double MaxY;
    private double Minx;
    private double MinY;
    private double radius;
    private double cannonHeight;
    private double cannonWidth;
    private Ellipse hitIndicator = new Ellipse(0, 0, 1, 1);
    private Obstacles o;

    // The angle doesn't directly work with sin and cos functions, so we convert to radians before use.
    double angle = 270;
    double radians;

    // This is mostly self explanatory, but controlScheme will determine if this tank will use wasd or arrows
    // TODO: Implement a starting angle
    public Tank(CanvasWindow canvas, int startX, int startY, Color color, int controlScheme , Obstacles o) {
        this.controlScheme = controlScheme;
        this.canvas = canvas;
        this.MaxX = canvas.getWidth();
        this.MaxY = canvas.getHeight();
        this.radius = 30;
        this.cannonHeight = 30;
        this.cannonWidth = 10;
        this.o = o;
        
        // This sets up our tank visual
        this.t = new GraphicsGroup(startX, startY);

        Ellipse body = new Ellipse(0,0, radius, radius);
        body.setStrokeColor(Color.BLACK);
        body.setFillColor(color);

        Rectangle barrel = new Rectangle(10, 15, cannonWidth, cannonHeight);
        barrel.setStrokeColor(Color.BLACK);
        barrel.setFillColor(color);

        t.add(body);
        t.add(barrel);

        Rectangle2D bounds = t.getBounds();
        double boundX = bounds.getX();
        double boundY = bounds.getY();
        double boundHeight = bounds.getHeight();
        double boundWidth = bounds.getWidth();

        Rectangle boundingBox = new Rectangle(boundX, boundY, boundWidth, boundHeight);
        boundingBox.setStrokeColor(Color.YELLOW);
        boundingBox.setFilled(false);

        t.add(boundingBox);
        t.setAnchor(15,15);

        canvas.add(t);
    }

    // This will be constantly called in a loop to handle all possible input (which will only be 5 keys PER CONTROL SCHEME)
    void registerInput(Set<Key> s) {

        if (controlScheme == 0) {
            
            radians = angle * (Math.PI / 180);
            // WASD version
            if (s.contains(Key.W) && !(checkObstaclesHitbox(o.getRects(), -5 * Math.cos(radians), -5 * Math.sin(radians)))) {
                t.moveBy(-5 * Math.cos(radians), -5 * Math.sin(radians)); // 45 and 46 work but will never be called because of the issues discussed in main
            } else if (s.contains(Key.S) && !(checkObstaclesHitbox(o.getRects(), 5 * Math.cos(radians), 5 * Math.sin(radians))))  {
                t.moveBy(5 * Math.cos(radians), 5 * Math.sin(radians));
            } 

            if (s.contains(Key.A)) {
                t.rotateBy(-5);
                angle += -5;

            }else if (s.contains(Key.D)) {
                t.rotateBy(5);
                angle += 5;
            }
        } else {
            // Arrow keys version
            if (s.contains(Key.UP_ARROW)) {
                t.moveBy(-5 * Math.cos(radians), -5 * Math.sin(radians)); // 45 and 46 work but will never be called because of the issues discussed in main
            } else if (s.contains(Key.DOWN_ARROW)) {
                t.moveBy(5 * Math.cos(radians), 5 * Math.sin(radians));
            } 
            
            radians = angle * (Math.PI / 180);

            if (s.contains(Key.LEFT_ARROW)) {
                t.rotateBy(-5);
                angle += -5;

            }else if (s.contains(Key.RIGHT_ARROW)) {
                t.rotateBy(5);
                angle += 5;
            }
        }
    }

    boolean checkObstaclesHitbox(List <Rectangle> rects, double xOffset, double yOffset) {
        double xCenter = t.getX() + radius / 2;
        double yCenter = t.getY();
        double xDown = xCenter;
        double yDown = yCenter + radius;
        double xLeft = xCenter - radius / 2;
        double yLeft = yDown - radius / 2;
        double xRight = xCenter + radius / 2;
        double yRight = yDown - radius / 2;
        double cannonCenterX = xCenter * Math.cos(radians);
        double cannonCenterY = yDown + cannonHeight / 2 * Math.sin(radians);
        boolean hitCheck = false;
        for (Rectangle r : rects) {
            if ((r.testHit(xCenter + xOffset, yCenter + yOffset))) {
                displayHitUp(xCenter, yCenter);
                System.out.println("Collison Up!!" + xCenter + " " + yCenter);
                hitCheck = true;
            }
            if (r.testHit(xDown + xOffset, yDown + yOffset)) {
                displayHitDown(xDown, yDown);
                System.out.println("Collison Down!!" + xDown + " " + yDown);
                hitCheck = true;
            }
            if (r.testHit(xLeft + xOffset, yLeft + yOffset)) {
                displayHitLeft(xLeft, yLeft);
                System.out.println("Collison Left!!" + xLeft + " " + yLeft);
                hitCheck = true;
            }
            if (r.testHit(xRight + xOffset, yRight + yOffset)) {
                displayHitRight(xRight, yRight);
                System.out.println("Collison Left!!" + xRight + " " + yRight);
                hitCheck = true;
            }
            // if (r.testHit(cannonCenterX, cannonCenterY)) {
            //     displayHitRight(cannonCenterX, cannonCenterY);
            //     System.out.println("Collison Left!!" + cannonCenterX + " " + cannonCenterY);
            //     hitCheck = true;
            // }
        }
        return hitCheck;
    }

    private void displayHitUp(double x, double y) {
        Ellipse location1 = new Ellipse(x, y, 1, 1);
        location1.setFillColor(Color.ORANGE);
        location1.setFilled(true);
        canvas.add(location1);
        canvas.draw();
    }

    private void displayHitDown(double x, double y) {
        Ellipse location2 = new Ellipse(x, y, 1, 1);
        location2.setFillColor(Color.ORANGE);
        location2.setFilled(true);
        canvas.add(location2);
        canvas.draw();
    }

    private void displayHitLeft(double x, double y) {
        Ellipse location3 = new Ellipse(x, y, 1, 1);
        location3.setFillColor(Color.ORANGE);
        location3.setFilled(true);
        canvas.add(location3);
        canvas.draw();
    }

    private void displayHitRight(double x, double y) {
        Ellipse location4 = new Ellipse(x, y, 1, 1);
        location4.setFillColor(Color.ORANGE);
        location4.setFilled(true);
        canvas.add(location4);
        canvas.draw();
    }

    private void displayHitCannonCenter(double x, double y) {
        Ellipse location5 = new Ellipse(x, y, 1, 1);
        location5.setFillColor(Color.ORANGE);
        location5.setFilled(true);
        canvas.add(location5);
        canvas.draw();
    }
}
