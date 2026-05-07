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
        Obstacles obs = new Obstacles(canvas);
        Tank t0 = new Tank(canvas, 100, 100, p0, 0, obs);
        Tank t1 = new Tank(canvas, 700, 700, p1, 1, obs);

        // for testing purposes
        Bullet b0 = new Bullet(canvas, p0, obs, t0);
        Bullet b1 = new Bullet(canvas, p1, obs, t1);
;
        canvas.draw();
        
        // Loop where movement is registered and canvas updated
        canvas.animate(() -> {
            t0.registerInput(canvas.getKeysPressed());
            t1.registerInput(canvas.getKeysPressed()); 
            b0.moveBullet();
            b1.moveBullet();
        });

    }
}

