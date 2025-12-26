package com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class FallingSound extends SoundEffects {

    public FallingSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super("sounds/soundEffects/falling.wav");
    }
}