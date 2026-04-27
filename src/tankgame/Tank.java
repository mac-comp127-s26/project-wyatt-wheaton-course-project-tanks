package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;


import java.awt.Color;
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

    // The angle doesn't directly work with sin and cos functions, so we convert to radians before use.
    double angle = 270;
    double radians;

    // This is mostly self explanatory, but controlScheme will determine if this tank will use wasd or arrows
    // TODO: Implement a starting angle
    public Tank(CanvasWindow canvas, int startX, int startY, Color color, int controlScheme) {
        this.controlScheme = controlScheme;
        this.canvas = canvas;
        this.MaxX = canvas.getWidth();
        this.MaxY = canvas.getHeight();
        
        // This sets up our tank visual
        this.t = new GraphicsGroup(startX, startY);

        Ellipse body = new Ellipse(0,0, 30, 30);
        body.setStrokeColor(Color.BLACK);
        body.setFillColor(color);

        Rectangle barrel = new Rectangle(10, 15, 10, 30);
        barrel.setStrokeColor(Color.BLACK);
        barrel.setFillColor(color);

        t.add(body);
        t.add(barrel);
        t.setAnchor(15,15);

        canvas.add(t);
    }

    // This will be constantly called in a loop to handle all possible input (which will only be 5 keys PER CONTROL SCHEME)
    void registerInput(Set<Key> s) {
        if (controlScheme == 0) {
            // WASD version
            if (s.contains(Key.W)) {
                t.moveBy(-5 * Math.cos(radians), -5 * Math.sin(radians)); // 45 and 46 work but will never be called because of the issues discussed in main
            } else if (s.contains(Key.S)) {
                t.moveBy(5 * Math.cos(radians), 5 * Math.sin(radians));
            } 
            
            radians = angle * (Math.PI / 180);

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
        canvas.add(t);
    }
}
