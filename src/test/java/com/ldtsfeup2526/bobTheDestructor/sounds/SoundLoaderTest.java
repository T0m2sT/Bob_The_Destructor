package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.Test;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SoundLoaderTest {
    @Test
    void testLoadSoundNull() {
        SoundLoader loader = new SoundLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.loadSound(null, mock(Clip.class)));
        assertThrows(IllegalArgumentException.class, () -> loader.loadSound(mock(AudioInputStream.class), null));
    }

    @Test
    void testLoadSoundAlreadyOpen() throws Exception {
        SoundLoader loader = new SoundLoader();
        AudioInputStream ais = mock(AudioInputStream.class);
        Clip clip = mock(Clip.class);
        
        when(clip.isOpen()).thenReturn(true);
        when(ais.getFormat()).thenReturn(new AudioFormat(44100, 16, 2, true, false));
        
        // This will likely fail on AudioSystem.getAudioInputStream if not careful
        // But we want to test the isOpen check
        try {
            loader.loadSound(ais, clip);
        } catch (Exception e) {
            // expected to fail later
        }
        
        verify(clip).stop();
        verify(clip).close();
    }
}
