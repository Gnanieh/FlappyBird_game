package org.example.flappybird_game.Utils;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import org.example.flappybird_game.Listeners.ScoreListener;
import org.example.flappybird_game.Managers.ScoreManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

@Getter
@Setter

public class Obstacle extends Collision {
    private AnchorPane anchorPane;
    private double anchorPaneWidth;
    private double anchorPaneHeight;
    ScoreManager scoreManager;
    public double movement = MIN_MOVEMENT;
    private static final int PIPE_WIDTH = 25;
    private static final double PIPE_SPACE = 200;
    public static final double MAX_MOVEMENT = 3.5;
    private static final double MIN_MOVEMENT = 1;
    private static final double MOVEMENT_INCREMENT = 0.35;
    private static final int PIPE_MIN_HEIGHT = 50;
    private static final int PIPE_MAX_HEIGHT = 300;
    private static final int DYNAMIC_PIPE_WIDTH = 10;
    private static final int DYNAMIC_PIPE_SPACE = 20;

    public Obstacle(AnchorPane anchorPane, double anchorPaneWidth, double anchorPaneHeight) {
        this.anchorPane = anchorPane;
        this.anchorPaneWidth = anchorPaneWidth;
        this.anchorPaneHeight = anchorPaneHeight;
        this.scoreManager = ScoreManager.getInstance();
        setupScoreListener();
    }

    private void setupScoreListener() {
        ScoreManager.getInstance().addListener(new ScoreListener() {
            @Override
            public void onScoreIncreased(int newScore) {
                if (movement < MAX_MOVEMENT) {
                    movement += MOVEMENT_INCREMENT;
                    System.out.println("Movement increased: " + movement);
                }
            }

            @Override
            public void onScoreReset() {
                movement = MIN_MOVEMENT;
            }
        });
    }

    private int dynamicPipeWidth() {
        return (int) (PIPE_WIDTH + Math.random() * DYNAMIC_PIPE_WIDTH);
    }

    private double dynamicPipeSpace() {
        return PIPE_SPACE - (Math.random() * DYNAMIC_PIPE_SPACE);
    }


    public ArrayList<Rectangle> createObstacles() {
        double posX = anchorPaneWidth;
        double pipeTop = PIPE_MIN_HEIGHT + Math.random() * (PIPE_MAX_HEIGHT - PIPE_MIN_HEIGHT);
        double pipeBot = anchorPaneHeight - dynamicPipeSpace() - pipeTop;

        int dynamicWidth = dynamicPipeWidth();

        Rectangle pipeTopRec = new Rectangle(posX, 0, dynamicWidth, pipeTop);
        Rectangle pipeBotRec = new Rectangle(posX, pipeBot + pipeTop, dynamicWidth, pipeBot);

        pipeTopRec.setStyle("-fx-fill: linear-gradient(to bottom, green, darkgreen);");
        pipeBotRec.setStyle("-fx-fill: linear-gradient(to top, green, darkgreen);");

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
        //System.out.println("MOVED SHAPE");
    }


    public boolean isDead(ArrayList<Rectangle> obstacles, Rectangle bird, AnchorPane anchorPane)
    {
        return(detectCollisionWithObstacles(obstacles, bird, anchorPane));
    }

}
