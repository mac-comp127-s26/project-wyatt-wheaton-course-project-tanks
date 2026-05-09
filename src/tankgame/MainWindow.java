package tankgame;
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

/**
* Authors: Wheaton Gribb and Wyatt Petersen
*
* This is main, obviously. After initalizing everything, all of the movement is handled 
* within an animate() lambda. All of the game logic is also 
* handled here (removing lives, who wins / loses, game reset etc.)
*/

public class MainWindow {
    public static void main(String[] args) {

        // Colors for each player
        final Color p0 = Color.BLUE;
        final Color p1 = Color.RED;

        // Making Canvas, Obstacles, and Tank
        CanvasWindow canvas = new CanvasWindow("Tanks", 800, 800);
        canvas.setBackground(Color.BLACK);
        Obstacles obs = new Obstacles(canvas);
        Tank t0 = new Tank(canvas,75, 75, p0, 0, obs, 3);
        Tank t1 = new Tank(canvas, 700, 700, p1, 1, obs, 3);

        // Creating Bullet Objects
        Bullet b0 = new Bullet(canvas, p0, obs, t0);
        Bullet b1 = new Bullet(canvas, p1, obs, t1);
;
        canvas.draw();

        // For lambda Manipulation inside of canvas.animate (Frames elapsed)
        int[] resetTimer = {0};

        // Boolean for when to reset the game inside of Lambda
        boolean[] resetting = {false};
        
        // Loop where movement is registered and canvas updated
        canvas.animate(() -> {
            // If resetting true, then delay 5 second (300 frames), then reset the game, timer, and reset boolean
            if (resetting[0]) {
            resetTimer[0]++;
                if (resetTimer[0] >= 300) {  // 300 frames / 60 FPS = 5 seconds
                    resetting[0] = false;
                    resetTimer[0] = 0;
                    gameReset(canvas, t0, t1, obs, p0, p1, b0, b1);
                }
                return;  // skip game logic while resetting
            }
            // Passses the pressed key to both tanks, then updates the bullet position
            t0.registerInput(canvas.getKeysPressed());
            t1.registerInput(canvas.getKeysPressed()); 
            b0.moveBullet();
            b1.moveBullet();
            
            // checks for red tank to blue bullet collison, if true execute roundEnd procedure
            if (b0.checkPlayerHit(t1)) {
                    t1.takeHit();
                    resetting[0] = true;
                    canvas.removeAll();
                    canvas.setBackground(p0);
                    if (t1.livesRemaining >= 1) { // if Red lives remaining isn't 0 then show roundEnd screen for Blue
                        GraphicsText w = new GraphicsText("Blue Won that Round!", 150, 300);
                        w.setFontSize(50);
                        w.setFillColor(Color.GREEN);
                        if (t1.livesRemaining == 1) {
                            GraphicsText l = new GraphicsText("Red has " + t1.livesRemaining + " life left", 170, 450);
                            l.setFontSize(50);
                            l.setFillColor(p1);
                            canvas.add(l);
                        } else {
                            GraphicsText l = new GraphicsText("Red has " + t1.livesRemaining + " lives left", 170, 450);
                            l.setFontSize(50);
                            l.setFillColor(p1);
                            canvas.add(l);
                        }
                        canvas.add(w);
                    } else { // if Red lives is 0 then show gameEnd screen
                        GraphicsText w = new GraphicsText("Blue Has Won the Game!", 100, 350);
                        w.setFontSize(50);
                        w.setFillColor(Color.GREEN);
                        GraphicsText r = new GraphicsText("New game starting in 5 seconds", 200, 450);
                        r.setFillColor(Color.GREEN);
                        r.setFontSize(25);
                        canvas.add(w);
                        canvas.add(r);
                    }
            } 
            
            // checks for blue tank and red bullet collison, if true execute roundEnd procedure
            if (b1.checkPlayerHit(t0)) {
                t0.takeHit();
                resetting[0] = true;
                canvas.removeAll();
                canvas.setBackground(p1);
                if (t0.livesRemaining >= 1) { // if Blue lives remaining isn't 0 then show roundEnd screen for Red
                    GraphicsText w = new GraphicsText("Red Won that Round!", 150, 300);
                    w.setFontSize(50);
                    w.setFillColor(Color.GREEN);
                    if (t0.livesRemaining == 1) {
                        GraphicsText l = new GraphicsText("Blue has " + t0.livesRemaining + " life left", 170, 450);
                        l.setFontSize(50);
                        l.setFillColor(p0);
                        canvas.add(l);
                    } else {
                        GraphicsText l = new GraphicsText("Blue has " + t0.livesRemaining + " lives left", 170, 450);
                        l.setFontSize(50);
                        l.setFillColor(p0);
                        canvas.add(l);
                    }
                    canvas.add(w);
                } else { // if Red lives is 0 then show gameEnd screen
                    GraphicsText w = new GraphicsText("Red Has Won the Game!", 100, 350);
                    w.setFontSize(50);
                    w.setFillColor(Color.GREEN);
                    GraphicsText r = new GraphicsText("New game starting in 5 seconds", 200, 450);
                    r.setFillColor(Color.GREEN);
                    r.setFontSize(25);
                    canvas.add(w);
                    canvas.add(r);
                    }
            }
        });
    }

    // Game reset logic
    private static void gameReset(CanvasWindow canvas, Tank t0, Tank t1, Obstacles o, Color p0, Color p1, Bullet b0, Bullet b1) {
        if (t0.livesRemaining <= 0 || t1.livesRemaining <= 0) { // if either tank has 0 lives reset both to 3.
            t0.livesRemaining = 3;
            t1.livesRemaining = 3;
        }
        canvas.removeAll();
        canvas.setBackground(Color.BLACK);
        // re-add Obstacles
        o.addToCanvas();  

        // reposition Tanks and add to canvas
        t0.resetPosition(100, 100);
        t1.resetPosition(700, 700);
        canvas.add(t0.t);
        canvas.add(t1.t);

        // reposition Bullets and add to canvas
        b0.reset();
        b1.reset();
        canvas.add(b0.shape);
        canvas.add(b1.shape);

        // re-draw canvas elements
        canvas.draw();
    }
}

