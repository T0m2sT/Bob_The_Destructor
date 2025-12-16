package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;

public class BackgroundMusicPlayer implements SoundPlayer{
    private Clip musicClip;

    public BackgroundMusicPlayer(Clip musicClip) {
        this.musicClip = musicClip;
    }

    @Override
    public void start() {
        musicClip.setMicrosecondPosition(0);
        musicClip.start();
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
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
