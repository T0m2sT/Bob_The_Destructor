package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.Test;
import javax.sound.sampled.Clip;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameSoundLoaderTest {
    @Test
    void testGetInvalidPath() {
        GameSoundLoader loader = new GameSoundLoader();
        Clip clip = loader.get("invalid/path.wav");
        assertNull(clip);
    }
    @Test
    void testGetInvalidAudioFile() {
        GameSoundLoader loader = new GameSoundLoader();
        // Use a file that exists but is not audio (like a png)
        Clip clip = loader.get("background/bg1.png");
        assertNull(clip);
    }
    @Test
    void testGetAndCache() throws Exception {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            Clip mockClip = mock(Clip.class);
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mockClip);
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            GameSoundLoader loader = new GameSoundLoader();
            String path = "sounds/soundEffects/mining.wav";
            Clip clip1 = loader.get(path);
            assertNotNull(clip1);
            
            Clip clip2 = loader.get(path);
            assertSame(clip1, clip2);
        }
    }

    @Test
    void testGetException() throws Exception {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenThrow(new javax.sound.sampled.LineUnavailableException());
            
            GameSoundLoader loader = new GameSoundLoader();
            Clip clip = loader.get("sounds/soundEffects/mining.wav");
            assertNull(clip);
        }
    }
}
