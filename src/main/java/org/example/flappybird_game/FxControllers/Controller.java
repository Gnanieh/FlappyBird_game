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
    private double deltaY = 0.02;
    private int gravity = 0;
    private double time = 0;
    private int scoreVal = 0;
    private final int jump = 20;

    private boolean pipePassed = false;
    Bird bird;
    Obstacle obstacle;
    ArrayList<Rectangle> obstacles = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bird = new Bird(deltaY, jump, rectangleBird);
        obstacle = new Obstacle(anchorPane, 400, 600);;
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        load();
        gameLoop.start();
    }


    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("SPACE");
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
        scoreText.setText("Score: " + scoreVal);
    }


    private void update()
    {
        gravity++;
        time++;
        bird.move(deltaY * gravity);

        obstacle.moveObstacles(obstacles);
        if(time % 250 == 0)
        {
            System.out.println("MOVE");
            obstacles.addAll(obstacle.createObstacles());
            pipePassed = false;
        }

        if (!pipePassed && rectangleBird.getX() + rectangleBird.getLayoutX() > obstacles.getFirst().getX() + obstacles.getFirst().getLayoutX())
        {
            scoreVal++;
            scoreText.setText("Score: " + scoreVal);
            pipePassed = true;
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
         obstacles.clear();
         time = 0;
         gravity = 0;
         bird = new Bird(deltaY, jump, rectangleBird);
         scoreVal = 0;
         scoreText.setText("Score: " + scoreVal);
         pipePassed = false;
    }


    private void gameOver()
    {
        if (scoreVal >= 10)
        {
            gameLoop.stop();
            scoreText.setText("Game Over, You've Won!");
        }
    }
}