package tankgame;
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;

// TODO: Implement Javadoc

public class MainWindow {
    public static void main(String[] args) {
        // Colors for each player
        final Color p0 = Color.BLUE;
        final Color p1 = Color.RED;

        // Making canvas and tank
        CanvasWindow canvas = new CanvasWindow("Tanks", 800, 800);
        Tank t0 = new Tank(canvas, 200, 200, p0, 0);
        Tank t1 = new Tank(canvas, 600, 600, p1, 1);
        Obstacles obs = new Obstacles(canvas);
        System.out.println(obs.getRects());
        canvas.draw();
        
        // Loop where movement is registered and canvas updated
        canvas.animate(() -> {
            t0.registerInput(canvas.getKeysPressed());
            t1.registerInput(canvas.getKeysPressed()); 
            
        });

        }
    }

