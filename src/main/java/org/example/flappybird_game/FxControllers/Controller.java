package org.example.flappybird_game.FxControllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import org.example.flappybird_game.Character.Bird;
import org.example.flappybird_game.Listeners.ScoreListener;
import org.example.flappybird_game.Managers.ScoreManager;
import org.example.flappybird_game.Utils.Obstacle;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



@Getter
@Setter

public class Controller implements Initializable {

    AnimationTimer gameLoop;
    @FXML
    public Text scoreText;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Rectangle rectangleBird;
    private Rectangle box;


    private double deltaY = 0.02;
    private int gravity = 0;
    private double time = 0;
    private double interval = MAX_INTERVAL;

    private static final int INTERVAL_DECREMENT = 25;
    private final int JUMP = 20;
    private static double MAX_INTERVAL = 500;
    private static double MIN_INTERVAL = 225;
    private static final int MAX_SCORE = 15;
    private static final int ANCHOR_PANE_WIDTH = 400;
    private static final int ANCHOR_PANE_HEIGHT = 600;

    private boolean pipePassed = false;
    Bird bird;
    Obstacle obstacle;
    ArrayList<Rectangle> obstacles = new ArrayList<>();
    ScoreManager scoreManager = ScoreManager.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bird = new Bird(deltaY, JUMP, rectangleBird);
        obstacle = new Obstacle(anchorPane, ANCHOR_PANE_WIDTH, ANCHOR_PANE_HEIGHT);;
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        setupScoreListener();
        load();
        gameLoop.start();
    }

    private void setupScoreListener()
    {
        ScoreManager.getInstance().addListener(new ScoreListener() {
            @Override
            public void onScoreIncreased(int score)
            {
                if (interval > MIN_INTERVAL)
                {
                    interval = interval - INTERVAL_DECREMENT;
                    System.out.println("Interval " + interval);
                }
            }

            @Override
            public void onScoreReset() {
                interval = MAX_INTERVAL;
            }
        });
    }

    public void keyPressed(KeyEvent keyEvent) {
        //System.out.println("SPACE");
        if(keyEvent.getCode() == KeyCode.SPACE)
        {
            bird.fly();
            gravity = 0;
            //System.out.println(anchorPane.getWidth() + " " + anchorPane.getHeight());
        }
    }

    public void load()
    {
        obstacles.addAll(obstacle.createObstacles());
        scoreText.setText("Score: " + scoreManager.getScore());
    }


    private void update()
    {
        gravity++;
        time++;
        bird.move(deltaY * gravity);

        obstacle.moveObstacles(obstacles);
        if(time % interval == 0)
        {
            System.out.println("ADD OBSTACLES");
            load();
            pipePassed = false;
        }

        for (Rectangle o : obstacles) {
            if ((!pipePassed && (rectangleBird.getX() + rectangleBird.getLayoutX()) > (o.getX() + o.getLayoutX()))) {
                scoreManager.incrementScore();
                scoreText.setText("Score: " + scoreManager.getScore());
                pipePassed = true;
                System.out.println("SCORED");
            }
        }
        if(obstacle.isDead(obstacles, rectangleBird, anchorPane)){
            resetBird();
            pipePassed = true;
        }

        gameOver();
    }

    private void resetBird()
    {
         rectangleBird.setY(0);
         anchorPane.getChildren().removeAll(obstacles);
         obstacles.removeAll(obstacles);
         obstacles.clear();
         time = 0;
         gravity = 0;
         bird = new Bird(deltaY, JUMP, rectangleBird);
         scoreManager.resetScore();
         scoreText.setText("Score: " + scoreManager.getScore());
         pipePassed = false;

    }


    private void gameOver()
    {
        if (scoreManager.getScore() >= MAX_SCORE)
        {
            gameLoop.stop();
            scoreText.setText("Game Over, You've Won!");
        }
    }
}