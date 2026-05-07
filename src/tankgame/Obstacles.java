package tankgame;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Obstacles {
    private int spacing;
    private int numRect;
    public List <Rectangle> rects = new ArrayList<>();
    private CanvasWindow canvas;
    
    // constructor to create setup for rectangles
    public Obstacles(CanvasWindow canvas) {
        this.spacing = 50;
        this.canvas = canvas;

        // randomly generates a num between 1 and 15 for number of rectangles
        this.numRect = new Random().nextInt(14) + 1;

       // creates the rectangles, stores them in "rects" arrayList and places rectangles onto Canvas
        createRectangles(canvas);

        // Creates invisible rectangles for all 4 sides of the screen and adds them to rects to prevent movement off of screen
        Rectangle b0 = new Rectangle(0, -25, canvas.getWidth(), 25);
        Rectangle b1 = new Rectangle(0, canvas.getHeight(), canvas.getWidth(), 25);
        Rectangle b2 = new Rectangle(canvas.getWidth(), 0, 25, canvas.getHeight());
        Rectangle b3 = new Rectangle(-25, 0, 25, canvas.getHeight());
        rects.add(b0);
        rects.add(b1);
        rects.add(b2);
        rects.add(b3);
    }

    // actual rectangle creation and canvas addition method
    private void createRectangles(CanvasWindow canvas) {
        Random random = new Random();
        int deadzoneMargin = 100; // used to create a deadzone 100 pixels in every direction from the screen where no obstacles can be created
        int gap = 50; // used to make sure that all obstacles are at least 50 pixels away from each other

        for (int i = 0; i < numRect; i++) { // for (random number between 1 and 15)
            // formula for creating a object randomly within the allowedZone and accounting for obstacle width and height
            int x = random.nextInt(canvas.getWidth() - 2 * deadzoneMargin - spacing) + deadzoneMargin;
            int y = random.nextInt(canvas.getHeight() - 2 * deadzoneMargin - spacing) + deadzoneMargin;

            // checks to see if randomly generated x and y are closer than 50 pixels to any other obstacles in rects
            boolean tooClose = false;
            for (Rectangle r : rects) {
                double dx = Math.abs(r.getX() - x);
                double dy = Math.abs (r.getY() - y);
                if (dx < spacing + gap && dy < spacing + gap) { // if closer than 50 pixels, then regenerate obstacle
                    i--;
                    tooClose = true;
                    break;
                }
            }

            // if not closer than 50 pixels, then create a new rectangle at that position, add to rects and canvas
            if (!tooClose) { 
                Rectangle r1 = new Rectangle(x, y, spacing, spacing);
                r1.setFillColor(getRandomColor()); // randomly generates one of 4 colors for obstacle to be colored
                rects.add(r1);
                canvas.add(r1);
            }
        } 
    }

    // getter method for accessing rects list
    public List <Rectangle> getRects() {
        return rects;
    }

    // used when reseting game and re-adding obstacles to canvas
    public void addToCanvas() {
        for (Rectangle r : rects) {
            canvas.add(r);
        }
    }

    // method that randomly provide 1 of 4 colors when called
    private Color getRandomColor() {
        Random random = new Random();
        int colorIndex = random.nextInt(4);
        if (colorIndex == 3) {
            return Color.GREEN;
        } else if (colorIndex == 2) {
            return Color.YELLOW;
        } else if (colorIndex == 1) {
            return Color.MAGENTA;
        } else {
            return Color.ORANGE;
        }
        
    }

    
}
