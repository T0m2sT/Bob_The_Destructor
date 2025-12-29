package com.ldtsfeup2526.bobTheDestructor.model;

public class GameSettings {
    private static GameSettings instance;

    private float masterVolume = 1;
    private float musicVolume = 1;
    private float sfxVolume = 1;

    private GameSettings() {}

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public float getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(float masterGain) {
        this.masterVolume = masterGain;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(float sfxVolume) {
        this.sfxVolume = sfxVolume;
    }
}