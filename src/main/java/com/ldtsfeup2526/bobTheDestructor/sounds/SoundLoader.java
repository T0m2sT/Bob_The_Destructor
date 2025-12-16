package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundLoader {
    public Clip loadSound(AudioInputStream audioInput, Clip musicClip) throws Exception {
        if (audioInput == null) {
            throw new IllegalArgumentException("AudioInputStream is null");
        }
        if (musicClip == null) {
            throw new IllegalArgumentException("Clip is null");
        }

        try (AudioInputStream in = audioInput) {
            if (musicClip.isOpen()) {
                musicClip.stop();
                musicClip.close();
            }

            AudioFormat baseFormat = in.getFormat();

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            try (AudioInputStream decoded = AudioSystem.getAudioInputStream(decodedFormat, in)) {
                musicClip.open(decoded);
            }

            return musicClip;
        } catch (Exception e) {
            throw new Exception("Unable to load sound file (cause: " + e.getClass().getSimpleName() + ")", e);
        }
    }
}
