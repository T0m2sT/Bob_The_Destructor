package com.ldtsfeup2526.bobTheDestructor.model.menu;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class MainMenuSoundtrack {
    private final AudioInputStream audioInput;
    private final Clip soundtrackClip;

    public MainMenuSoundtrack() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("sounds/mainMenuSoundtrack.wav")));
        soundtrackClip = AudioSystem.getClip();
    }

    public AudioInputStream getAudioInput() {
        return audioInput;
    }

    public Clip getSoundtrackClip() {
        return soundtrackClip;
    }
}
