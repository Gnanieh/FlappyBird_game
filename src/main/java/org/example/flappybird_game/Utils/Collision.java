package org.example.flappybird_game.Utils;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public abstract class Collision {
    protected boolean detectCollisionWithObstacles(ArrayList<Rectangle> obstacles, Rectangle bird, AnchorPane pane)
    {
        for (Rectangle rectangle : obstacles) {
            if (rectangle.getBoundsInParent().intersects(bird.getBoundsInParent()) || bird.getY() > pane.getHeight()) {
                return true;
            }
        }

        return  false;
    }
}
