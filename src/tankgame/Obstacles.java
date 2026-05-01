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
    final Color color = Color.BLUE;
    private double spacing;
    private int numRect;
    public List <Rectangle> rects = new ArrayList<>();
    
    // constructor to create setup for rectangles
    public Obstacles(CanvasWindow canvas) {
        this.x = 50;
        this.y = 50;
        this.spacing = 50;
        this.numRect = new Random().nextInt(9) + 1;

        // uses random number between 1 and 10 to create random 100 by 100 squares around the canvas
        for (int i = 0; i < numRect; i++) {
            createRectangle(canvas);
        }
    
    }
    // actual rectangle creation and canvas addition method
    private void createRectangle(CanvasWindow canvas) {
        int xMax = new Random().nextInt(canvas.getWidth());
        int yMax = new Random().nextInt(canvas.getHeight());
        Rectangle r1 = new Rectangle(xMax, yMax, spacing, spacing); // rectangle creation
        rects.add(r1); // list of rectangles used for hitbox detection
        canvas.add(r1);
        
    }

    public List <Rectangle> getRects() {
        return rects;
    }

    
}
