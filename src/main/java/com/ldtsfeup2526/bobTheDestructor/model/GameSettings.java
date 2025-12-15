package com.ldtsfeup2526.bobTheDestructor.model;

public class GameSettings {
    private static GameSettings instance;

    private float masterGain = 0.0f;

    private GameSettings() {}

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public float getMasterGain() {
        return masterGain;
    }

    public void setMasterGain(float masterGain) {
        this.masterGain = masterGain;
    }
}