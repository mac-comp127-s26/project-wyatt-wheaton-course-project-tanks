package tankgame;
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;

// TODO: Implement Javadoc

public class MainWindow {
    public static void main(String[] args) {
        // Colors for each player
        final Color p1 = Color.BLUE;
        final Color p2 = Color.RED;

        // Making canvas and tank
        // TODO: Implement second tank
        CanvasWindow canvas = new CanvasWindow("Tanks", 800, 800);
        Tank t0 = new Tank(canvas, 400, 400, p1, 0);
        
        // Loop where movement is registered and canvas updated
        // TODO: fix getKeeysPressed
        while (true) {
            t0.registerInput(canvas.getKeysPressed()); // getKeysPressed doesn't work for unknown reasons
            // it will never return anything at all no matter how many keys you press, so that is our big obstacle.
            canvas.draw();
        }
    }
}
