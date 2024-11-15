package org.example.flappybird_game.Utils;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

@Getter
@Setter

public class Obstacle extends Collision {
    private AnchorPane anchorPane;
    private double anchorPaneWidth;
    private double anchorPaneHeight;
    private static final int pipeWidth = 25;
    private static final double pipeSpace = 200;
    private static final double movement = 0.75;
    private static final int pipeMinHeight= 50;
    private static final int pipeMaxHeight= 300;


    public Obstacle(AnchorPane anchorPane, double anchorPaneWidth, double anchorPaneHeight) {
        this.anchorPane = anchorPane;
        this.anchorPaneWidth = anchorPaneWidth;
        this.anchorPaneHeight = anchorPaneHeight;
    }

    public ArrayList<Rectangle> createObstacles() {
        double posX = anchorPaneWidth;
        double pipeTop = pipeMinHeight + Math.random() * (pipeMaxHeight - pipeMinHeight);
        double pipeBot = anchorPaneHeight - pipeSpace - pipeTop;

        Rectangle pipeTopRec = new Rectangle(posX, 0, pipeWidth, pipeTop);
        Rectangle pipeBotRec = new Rectangle(posX, pipeBot + pipeTop, pipeWidth, pipeBot);

        anchorPane.getChildren().addAll(pipeTopRec, pipeBotRec);
        return new ArrayList<>(Arrays.asList(pipeTopRec, pipeBotRec));
    }


    public void moveObstacles(ArrayList<Rectangle> obstacles) {
        Iterator<Rectangle> iterator = obstacles.iterator();

        while (iterator.hasNext()) {
            Rectangle obstacle = iterator.next();
            moveShape(obstacle, -movement);

            if (obstacle.getX() <= -obstacle.getWidth()) {
                anchorPane.getChildren().remove(obstacle);
                iterator.remove();
                System.out.println("DELETE");
            }
        }

    }


    private void moveShape(Rectangle obstacle, double deltaX)
    {
        obstacle.setX(obstacle.getX() + deltaX);
    }

    public boolean isDead(ArrayList<Rectangle> obstacles, Rectangle bird, AnchorPane anchorPane)
    {
        return(collisionDetection(obstacles, bird, anchorPane));
    }

}
