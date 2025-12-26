package com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class JumpingSound extends SoundEffects {

    public JumpingSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super("sounds/soundEffects/jumping.wav");
    }
}