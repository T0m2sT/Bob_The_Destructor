package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;

public class SoundEffectsPlayer implements SoundPlayer {
    private Clip musicClip;

    public SoundEffectsPlayer(Clip musicClip) {
        this.musicClip = musicClip;
    }

    @Override
    public void start() {
        musicClip.setMicrosecondPosition(0);
        musicClip.start();
    }

    @Override
    public void stop() {
        musicClip.stop();
    }

    @Override
    public void setSound(Clip musicClip) {
        this.musicClip = musicClip;
    }

    @Override
    public Clip getSound() {
        return musicClip;
    }
}
