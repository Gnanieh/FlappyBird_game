package org.example.flappybird_game.Utils;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Bird {

    private double moveDeltaY;
    private double time;
    private int jump;


    public Bird(double moveDeltaY, double time, int jump) {
        this.moveDeltaY = moveDeltaY;
        this.time = time;
        this.jump = jump;
    }


}
