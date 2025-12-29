package com.ldtsfeup2526.bobTheDestructor.model.stats;

import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;

public class Stats {
    private final int mineralsCollected;
    private final int seconds;
    private final int minutes;

    public Stats(SceneManager sceneManager) {
        this.mineralsCollected = sceneManager.getTotalMineralsCollected();
        this.seconds = (int) ((sceneManager.getTimePassed() / 1000) % 60);
        this.minutes = (int) ((sceneManager.getTimePassed() / 1000) / 60);
    }

    public int getMineralsCollected() {
        return mineralsCollected;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }
}
