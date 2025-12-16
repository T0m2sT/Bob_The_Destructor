package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class GameSoundtrack {
    private final AudioInputStream audioInput;
    private final Clip soundtrackClip;

    public GameSoundtrack() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("sounds/gameSoundtrack.wav")));
        soundtrackClip = AudioSystem.getClip();
    }

    public AudioInputStream getAudioInput() {
        return audioInput;
    }

    public Clip getSoundtrackClip() {
        return soundtrackClip;
    }
}
