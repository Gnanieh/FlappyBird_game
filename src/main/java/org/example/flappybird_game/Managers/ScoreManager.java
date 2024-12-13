package org.example.flappybird_game.Managers;

import lombok.Getter;
import lombok.Setter;
import org.example.flappybird_game.Listeners.ScoreListener;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

public class ScoreManager {
    private static ScoreManager instance;
    private int score;
    private final List<ScoreListener> listeners;

    private ScoreManager() {
        score = 0;
        listeners = new ArrayList<>();
    }

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public void incrementScore() {
        score++;
        notifyListeners();
    }


    public void addScore(int points) {
        score += points;
        notifyListeners();
    }

    public void resetScore() {
        score = 0;
        for (ScoreListener listener : listeners) {
            listener.onScoreReset();
        }
    }

    public void addListener(ScoreListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ScoreListener listener : listeners) {
            listener.onScoreIncreased(score);
        }

    }

}
