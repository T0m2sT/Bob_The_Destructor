package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;
import java.util.Objects;

public class GameSoundManager extends SoundManager{
    private String currentMusicPath;
    private Clip currentMusicClip;

    public GameSoundManager(SoundLoader soundLoader) {
        super(soundLoader);
    }


    @Override
    public void playMusic(String soundFilePath) {
        if (Objects.equals(currentMusicPath, soundFilePath)) {
            currentMusicClip = soundLoader.get(soundFilePath);
            if (currentMusicClip == null) return;
            currentMusicClip.start();
            return;
        }

        currentMusicPath = soundFilePath;

        stopMusic();

        currentMusicClip = soundLoader.get(soundFilePath);
        if (currentMusicClip == null) return;
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
    public void playSFX(String soundFilePath) {
        Clip sfx = soundLoader.get(soundFilePath);
        if (sfx == null) return;

        sfx.setFramePosition(0);

        applyVolumeToClip(sfx, sfxVolume);

        sfx.start();
    }

    public void playSFXLoop(String soundFilePath) {
        Clip sfx = soundLoader.get(soundFilePath);
        if (sfx == null) return;

        sfx.setFramePosition(0);

        applyVolumeToClip(sfx, sfxVolume);


        sfx.loop(Clip.LOOP_CONTINUOUSLY);
        sfx.start();
    }

    @Override
    public void stopSFX(String soundFilePath) {
        Clip sfx = soundLoader.get(soundFilePath);
        if (sfx == null) return;

        sfx.stop();
    }

    @Override
    public void updateVolumes() {
        applyVolumeToClip(currentMusicClip, musicVolume);
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
