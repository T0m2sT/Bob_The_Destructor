package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameSoundLoader implements SoundLoader{
    private final Map<String, Clip> clipMap = new HashMap<>();

    @Override
    public Clip get(String soundFilePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (clipMap.containsKey(soundFilePath)) {
            Clip clip = clipMap.get(soundFilePath);
            return clip;
        }

        URL resource = getClass().getClassLoader().getResource(soundFilePath);

        AudioInputStream audioIn = AudioSystem.getAudioInputStream(Objects.requireNonNull(resource));
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);

        clipMap.put(soundFilePath, clip);
        clip.setFramePosition(0);
        return clip;
    }
}
