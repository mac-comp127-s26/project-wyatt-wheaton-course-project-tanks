package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;

import java.awt.Color;
import java.util.List;
import java.util.Set;

// TODO: Implement javadoc

public class Tank {
    public GraphicsGroup t;
    int controlScheme; // for different tank controls
    CanvasWindow canvas;
    private double diameter; // diameter of circle
    private double cannonHeight;
    private double cannonWidth;
    private Obstacles o;
    private int numPoints = 16; // number of points on the body of the tank which are used as hitbox detectors
    private double[][] bodyPoints = new double [numPoints] [2]; // creates an array of 2 double arrays for 16 points on the body of the Tank
    private double cannonWidthOffset = 15;
    private double cannonHeightOffset = 45;
    private double angle;
    private double radians;

    // The angle is in degrees. Since Math.cos and Math.sin are in radians, we convert to radians before use.
    

    // This is mostly self explanatory, but controlScheme will determine if this tank will use wasd or arrows
    // TODO: Implement a starting angle
    public Tank(CanvasWindow canvas, int startX, int startY, Color color, int controlScheme , Obstacles o) {
        this.controlScheme = controlScheme;
        this.canvas = canvas;
        this.diameter = 30;
        this.cannonHeight = 30;
        this.cannonWidth = 10;
        this.o = o;
        this.angle = 90;
        this.radians = angle * (Math.PI / 180);
        
        // This sets up our tank visual
        this.t = new GraphicsGroup(startX, startY);

        // Tank body creation
        Ellipse body = new Ellipse(0,0, diameter, diameter);
        body.setStrokeColor(Color.BLACK);
        body.setFillColor(color);

        // Tank cannon creation
        Rectangle barrel = new Rectangle(10, 15, cannonWidth, cannonHeight);
        barrel.setStrokeColor(Color.BLACK);
        barrel.setFillColor(color);

        // updates bodyPoints Array to contain starting points of tank hitbox Points
        updateBodyPoints();

        t.add(body);
        t.add(barrel);

        t.setAnchor(15,15);
        canvas.add(t);
    }

    // This will be constantly called in a loop to handle all possible input (which will only be 5 keys PER CONTROL SCHEME)
    void registerInput(Set<Key> s) {
        radians = angle * (Math.PI / 180);

        if (controlScheme == 0) {
            
            // checks if key pressed is w or s and if the next move will not be touching/inside an obstacle. 
            if (s.contains(Key.W) && !(checkObstaclesHitbox(o.getRects(), 5 * Math.cos(radians), 5 * Math.sin(radians)))) {
                t.moveBy(5 * Math.cos(radians), 5 * Math.sin(radians)); 
                updateBodyPoints();
            } else if (s.contains(Key.S) && !(checkObstaclesHitbox(o.getRects(), -5 * Math.cos(radians), -5 * Math.sin(radians))))  {
                t.moveBy(-5 * Math.cos(radians), -5 * Math.sin(radians));
                updateBodyPoints();
            } 

            // checks if key pressed is a or d, then rotates the ENTIRE figure by 5 degrees either clockwise or counterclockwise
            if (s.contains(Key.A)) {
                    t.rotateBy(-5);
                    angle -= 5;
                } else if (s.contains(Key.D)) {
                    t.rotateBy(5);
                    angle += 5;
                } 
            } else {
                
                // checks if key pressed is up or down arrow and if the next move will not be touching/inside an obstacle. 
                if (s.contains(Key.UP_ARROW) && !(checkObstaclesHitbox(o.getRects(), 5 * Math.cos(radians), 5 * Math.sin(radians)))) {
                    t.moveBy(5 * Math.cos(radians), 5 * Math.sin(radians)); 
                    updateBodyPoints();
                } else if (s.contains(Key.DOWN_ARROW) && !(checkObstaclesHitbox(o.getRects(), -5 * Math.cos(radians), -5 * Math.sin(radians))))  {
                    t.moveBy(-5 * Math.cos(radians), -5 * Math.sin(radians));
                    updateBodyPoints();
                } 

                // checks if key pressed is left or right arrow, then rotates the ENTIRE figure by 5 degrees either clockwise or counterclockwise
                if (s.contains(Key.LEFT_ARROW)) {
                    t.rotateBy(-5);
                    angle -= 5;
                } else if (s.contains(Key.RIGHT_ARROW)) {
                    t.rotateBy(5);
                    angle += 5;
                } 
            }
        }
    
    // used to check collisons between tanks and obstecles
   private boolean checkObstaclesHitbox(List <Rectangle> rects, double xOffset, double yOffset) {
        boolean hitCheck = false;
        for(Rectangle rect : rects) { // for every obstecle on the window
            for (double[] point : bodyPoints) { // for every point within the bodyPoint array
                if(rect.testHit(point[0] + xOffset, point[1] + yOffset)) { // check to see if the next move will collide with an obstacle
                    hitCheck = true;
                }
            }
        }
        return hitCheck;
    }

    // TODO remove this helper method when done with collision testing (important for testing so I left it in here)

    // private void displayHit(double x, double y) {
    //     Ellipse location1 = new Ellipse(x, y, 1, 1);
    //     location1.setFillColor(Color.ORANGE);
    //     location1.setFilled(true);
    //     canvas.add(location1);
    //     canvas.draw();
    // }

    // Helper method which derives the current position of numPoints Points on the tank body and updates the list with them
    private void updateBodyPoints() {
        double centerX = t.getX() + 15;
        double centerY = t.getY() + 15;
        double radius = diameter / 2;

        for (int i = 0; i < numPoints; i++) {
            double evenlySpacedAngle = 2 * Math.PI * i / numPoints;
            bodyPoints[i][0] = centerX + radius * Math.cos(evenlySpacedAngle);
            bodyPoints[i][1] = centerY + radius * Math.sin(evenlySpacedAngle);
        }

    }

    // creation and updating of cannonPoints (need for manipulating bullet location)
    public double [] cannonRotated() {
        double xOffset = cannonWidthOffset - 15; // subtract anchor
        double yOffset = cannonHeightOffset - 15; // subtract anchor
        double angle = getAngle() * (180 / Math.PI); // for subtracting original tank angle
        angle = (angle - 90) * (Math.PI / 180);  // for subtracting the orginal tank angle
        
        //2D Rotation Matrix (tank position + offset of cannon (width/height) + rotation matrix based on tank angle)
        double x = t.getX() + 15 + ((xOffset * Math.cos(angle)) - (yOffset * Math.sin(angle)));
        double y = t.getY() + 15 + ((xOffset * Math.sin(angle)) + (yOffset * Math.cos(angle)));
        
        return new double [] {x, y};
    }

    public double getAngle() {
        return radians;
    }

    
}
