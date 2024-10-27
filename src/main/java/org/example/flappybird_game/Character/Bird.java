package org.example.flappybird_game.Character;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Bird {

    private double deltaY;
    private int jump;
    private Rectangle bird;

    public Bird(double moveDeltaY, int jump, Rectangle bird) {
        this.deltaY = moveDeltaY;
        this.jump = jump;
        this.bird = bird;
    }

    public void fly()
    {
        deltaY = -jump;
        if(bird.getLayoutY() + bird.getY() <= jump)
        {
            deltaY = -(bird.getLayoutY() + bird.getY());
        }

        move(deltaY);
    }

    public void move(double deltaY)
    {
        bird.setY(bird.getY() + deltaY);
    }

}
