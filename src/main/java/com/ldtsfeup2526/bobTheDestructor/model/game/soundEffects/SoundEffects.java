package com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public abstract class SoundEffects {
    private final AudioInputStream audioInput;
    private final Clip soundtrackClip;

    protected SoundEffects(String soundFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.audioInput = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(getClass().getClassLoader().getResource(soundFilePath))
        );
        this.soundtrackClip = AudioSystem.getClip();
    }

    public AudioInputStream getAudioInput() {
        return audioInput;
    }

    public Clip getSoundtrackClip() {
        return soundtrackClip;
    }
}