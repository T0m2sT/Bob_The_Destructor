package com.ldtsfeup2526.bobTheDestructor.model.menu;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainMenuSoundtrack {
    private final AudioInputStream audioInput;
    private final Clip soundtrackClip;

    public MainMenuSoundtrack() {
        AudioInputStream input = null;
        Clip clip = null;
        try {
            URL url = getClass().getClassLoader().getResource("sounds/mainMenuSoundtrack.wav");
            if (url != null) {
                input = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
            } else {
                // No soundtrack
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.audioInput = input;
        this.soundtrackClip = clip;
    }

    public AudioInputStream getAudioInput() {
        return audioInput;
    }

    public Clip getSoundtrackClip() {
        return soundtrackClip;
    }
}
