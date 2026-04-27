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
    
    public Obstacles(CanvasWindow canvas) {
        this.x = 100;
        this.y = 100;
        this.spacing = 50;
        this.numRect = new Random().nextInt(9) + 1;
        for (int i = 0; i < numRect; i ++) {
            createRectangle(canvas, x, y);
        }
    
    }

    private void createRectangle(CanvasWindow canvas, double x, double y) {
        int xMax = new Random().nextInt(canvas.getWidth());
        int yMax = new Random().nextInt(canvas.getHeight());
        Rectangle r1 = new Rectangle(xMax, yMax, spacing, spacing);
        rects.add(r1);
        canvas.add(r1);
        canvas.draw();
    }

    public List <Rectangle> getRects() {
        return rects;
    }
}
