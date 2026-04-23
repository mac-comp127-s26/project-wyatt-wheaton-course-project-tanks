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


    double angle = 180;

    // This is mostly self explanatory, but controlScheme will determine if this tank will use wasd or arrows
    // TODO: Implement a starting angle
    public Tank(CanvasWindow canvas, int startX, int startY, Color color, int controlScheme) {
        controlScheme = this.controlScheme;
        this.canvas = canvas;
        
        // This sets up our tank visual
        this.t = new GraphicsGroup(startX, startY);

        Ellipse body = new Ellipse(0,0, 30, 30);
        body.setStrokeColor(Color.BLACK);
        body.setFillColor(color);

        Rectangle barrel = new Rectangle(10, 15, 10, 50);
        barrel.setStrokeColor(Color.BLACK);
        barrel.setFillColor(color);

        t.add(body);
        t.add(barrel);

        canvas.add(t);
    }

    // This will be constantly called in a loop to handle all possible input (which will only be 5 keys)
    // TODO: Implement control schemes, one with arrows the other with wasd
    void registerInput(Set<Key> s) {
        if (s.contains(Key.W)) {
            t.moveBy(0, 5); // 45 and 46 work but will never be called because of the issues discussed in main
            canvas.add(t);
        }
    }
}
