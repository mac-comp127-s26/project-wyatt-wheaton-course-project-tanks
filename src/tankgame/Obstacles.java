package tankgame;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Obstacles {
    private double x;
    private double y;
    final Color color = Color.BLACK;
    private double spacing;
    private int numRect;
    public List <Rectangle> rects = new ArrayList<>();
    
    // constructor to create setup for rectangles
    public Obstacles(CanvasWindow canvas) {
        this.x = 50;
        this.y = 50;
        this.spacing = 50;
        this.numRect = new Random().nextInt(14) + 1;

        // uses random number between 1 and 10 to create random 100 by 100 squares around the canvas
        for (int i = 0; i < numRect; i++) {
            createRectangle(canvas);
        }

        // this makes sure you can't drive off screen - there might be a less clunky way of doing this
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
    private void createRectangle(CanvasWindow canvas) {
        int xMax = new Random().nextInt(canvas.getWidth() - 199) + 100;
        int yMax = new Random().nextInt(canvas.getHeight() - 199) + 100;
        System.out.println(xMax + " , " + yMax);
        Rectangle r1 = new Rectangle(xMax, yMax, spacing, spacing); // rectangle creation
        r1.setFillColor(color); // filling in walls
        rects.add(r1); // list of rectangles used for hitbox detection
        canvas.add(r1);
        
    }

    public List <Rectangle> getRects() {
        return rects;
    }

    
}
