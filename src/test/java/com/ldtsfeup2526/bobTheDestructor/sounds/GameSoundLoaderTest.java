package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.Test;
import javax.sound.sampled.Clip;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameSoundLoaderTest {
    @Test
    void testGetInvalidPath() {
        GameSoundLoader loader = new GameSoundLoader();
        assertThrows(NullPointerException.class, () -> {
        loader.get("invalid/path.wav");
    });
    }
    @Test
    void testGetInvalidAudioFile() {
        GameSoundLoader loader = new GameSoundLoader();
        Clip clip = loader.get("background/bg1.png");
        assertNull(clip);
    }
    @Test
    void testGetAndCache() throws Exception {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            Clip mockClip = mock(Clip.class);
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mockClip);
            javax.sound.sampled.AudioInputStream mockAIS = mock(javax.sound.sampled.AudioInputStream.class);
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mockAIS);

            GameSoundLoader loader = new GameSoundLoader();
            String path = "sounds/soundEffects/mining.wav";
            Clip clip1 = loader.get(path);
            assertNotNull(clip1);
            verify(mockClip).open(mockAIS);
            verify(mockClip).setFramePosition(0);
            
            Clip clip2 = loader.get(path);
            assertSame(clip1, clip2);
            verify(mockClip, times(1)).open(any(javax.sound.sampled.AudioInputStream.class));
        }
    }

    @Test
    void testGetException() throws Exception {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenThrow(new javax.sound.sampled.LineUnavailableException("Mocked exception"));
            
            java.io.ByteArrayOutputStream errContent = new java.io.ByteArrayOutputStream();
            java.io.PrintStream originalErr = System.err;
            System.setErr(new java.io.PrintStream(errContent));
            
            try {
                GameSoundLoader loader = new GameSoundLoader();
                Clip clip = loader.get("sounds/soundEffects/mining.wav");
                assertNull(clip);
                assertTrue(errContent.toString().contains("Mocked exception"), "StackTrace should be printed");
            } finally {
                System.setErr(originalErr);
            }
        }
    }
}
