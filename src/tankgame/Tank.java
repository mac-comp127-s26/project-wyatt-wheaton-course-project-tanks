package tankgame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;


import java.awt.Color;
import java.util.Set;

public class Tank {
    GraphicsGroup t;
    int controlScheme;

    double angle = 180;

    // This is mostly self explanatory, but controlScheme will determine if this tank will use wasd or arrows
    public Tank(CanvasWindow canvas, int startX, int startY, Color color, int controlScheme) {
        controlScheme = this.controlScheme;
        
        
        
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

    void registerInput(Set<Key> s) {
        if (s.contains(Key.LEFT_ARROW)) {
            t.rotateBy(5);
            angle += 5;
            t.add(t);
        }
    }
}
