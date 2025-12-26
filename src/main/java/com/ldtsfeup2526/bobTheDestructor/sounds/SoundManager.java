package com.ldtsfeup2526.bobTheDestructor.sounds;

public abstract class SoundManager {
    private final SoundLoader soundLoader;

    public SoundManager(SoundLoader soundLoader) {
        this.soundLoader = soundLoader;
    }

    public abstract void playMusic(String soundFilePath);
    public abstract void stopMusic();

    public abstract void playSFXR(String soundFilePath);

}
