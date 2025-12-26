package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

public class GameSoundManager extends SoundManager{
    private String currentMusicPath;
    private Clip currentMusicClip;

    public GameSoundManager(SoundLoader soundLoader) {
        super(soundLoader);
    }


    @Override
    public void playMusic(String soundFilePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (Objects.equals(currentMusicPath, soundFilePath)) {
            currentMusicClip = soundLoader.get(soundFilePath);
            currentMusicClip.start();
            return;
        }

        currentMusicPath = soundFilePath;

        stopMusic();

        currentMusicClip = soundLoader.get(soundFilePath);
        currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);

        currentMusicClip.setFramePosition(0);

        applyVolumeToClip(currentMusicClip, musicVolume);

        currentMusicClip.start();

    }

    @Override
    public void stopMusic() {
        if (currentMusicClip != null) {
            currentMusicClip.stop();
            currentMusicClip = null;
        }
    }

    @Override
    public void playSFX(String soundFilePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Clip sfx = soundLoader.get(soundFilePath);

        applyVolumeToClip(sfx, sfxVolume);

        sfx.start();
    }


    @Override
    public void setMasterVolume(float volume) {
        this.masterVolume = volume;
    }

    @Override
    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
    }

    @Override
    public void setSFXVolume(float volume) {
        this.sfxVolume = volume;
    }

    @Override
    public float getMasterVolume() {
        return masterVolume;
    }

    @Override
    public float getMusicVolume() {
        return musicVolume;
    }

    @Override
    public float getSFXVolume() {
        return sfxVolume;
    }
}
