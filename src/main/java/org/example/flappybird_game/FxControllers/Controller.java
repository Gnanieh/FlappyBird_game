package org.example.flappybird_game.FxControllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import org.example.flappybird_game.Utils.Bird;
import org.example.flappybird_game.Utils.Logic;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    AnimationTimer gameLoop;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Rectangle rectangleBird;
    Bird bird = new Bird(0.02, 0, 75);
    private int i = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("SPACE");
        if(keyEvent.getCode() == KeyCode.SPACE)
        {
            fly(bird);
        }
    }

    public void fly(Bird bird)
    {
        if (rectangleBird.getLayoutY() + rectangleBird.getY() <= bird.getJump())
        {
            moveBirdY(-(rectangleBird).getLayoutY() + rectangleBird.getY());
            bird.setTime(0);
        }
        else {
            moveBirdY(-bird.getJump());
            bird.setTime(0);
        }
    }


    public void moveBirdY(double deltaY)
    {
        rectangleBird.setY(rectangleBird.getY() + deltaY);
    }


    private void update()
    {
        bird.setTime(bird.getTime()+1);
        moveBirdY(bird.getMoveDeltaY() * bird.getTime());

        if (isBirdDead()){
            resetBird();
        }
    }


    private boolean isBirdDead()
    {
        double birdY = rectangleBird.getY() + rectangleBird.getLayoutY();
        return birdY >= anchorPane.getHeight();
    }

    private void resetBird()
    {
         rectangleBird.setY(0);
         bird.setTime(0);
    }





}