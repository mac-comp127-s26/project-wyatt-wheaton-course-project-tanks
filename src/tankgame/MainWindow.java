package tankgame;
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;


public class MainWindow {
    public static void main(String[] args) {
        final Color p1 = Color.BLUE;
        CanvasWindow canvas = new CanvasWindow("Tanks", 800, 800);
        Tank t0 = new Tank(canvas, 400, 400, p1, 0);
        
        while (true) {
            t0.registerInput(canvas.getKeysPressed());
            canvas.draw();
        }
    }
}
