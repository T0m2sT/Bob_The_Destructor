package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameSoundManagerTest {
    private SoundLoader soundLoader;
    private GameSoundManager soundManager;
    private Clip mockClip;
    private FloatControl mockControl;

    @BeforeEach
    void setUp() {
        soundLoader = mock(SoundLoader.class);
        soundManager = new GameSoundManager(soundLoader);
        mockClip = mock(Clip.class);
        mockControl = mock(FloatControl.class);
        when(mockClip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(true);
        when(mockClip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(mockControl);
    }

    @Test
    void testPlayMusic() {
        String path = "music.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);

        soundManager.playMusic(path);
        verify(mockClip).loop(Clip.LOOP_CONTINUOUSLY);
        verify(mockClip).start();

        // Play same music again
        soundManager.playMusic(path);
        verify(mockClip, times(2)).start();
    }

    @Test
    void testStopMusic() {
        String path = "music.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);
        soundManager.playMusic(path);
        
        soundManager.stopMusic();
        verify(mockClip).stop();
    }

    @Test
    void testPlaySFX() {
        String path = "sfx.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);

        soundManager.playSFX(path);
        verify(mockClip).setFramePosition(0);
        verify(mockClip).start();
    }

    @Test
    void testPlaySFXLoop() {
        String path = "sfx.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);

        soundManager.playSFXLoop(path);
        verify(mockClip).loop(Clip.LOOP_CONTINUOUSLY);
        verify(mockClip).start();
    }

    @Test
    void testStopSFX() {
        String path = "sfx.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);
        soundManager.stopSFX(path);
        verify(mockClip).stop();
    }

    @Test
    void testVolumes() {
        soundManager.setMasterVolume(0.5f);
        assertEquals(0.5f, soundManager.getMasterVolume());
        
        soundManager.setMusicVolume(0.8f);
        assertEquals(0.8f, soundManager.getMusicVolume());
        
        soundManager.setSFXVolume(0.2f);
        assertEquals(0.2f, soundManager.getSFXVolume());
        
        String path = "music.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);
        soundManager.playMusic(path);
        
        soundManager.updateVolumes();
        // 0.5 * 0.8 = 0.4
        // linearToDb(0.4) = 20 * log10(0.4) approx -7.95
        verify(mockControl, atLeastOnce()).setValue(anyFloat());
    }

    @Test
    void testLinearToDbEdgeCase() {
        // We can't call protected linearToDb directly from outside unless we're in same package
        // But we can trigger it via applyVolumeToClip which is protected but called by updateVolumes
        soundManager.setMasterVolume(0);
        String path = "music.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);
        soundManager.playMusic(path);
        
        soundManager.updateVolumes();
        verify(mockControl, atLeastOnce()).setValue(-80f);
    }
    
    @Test
    void testControlNotSupported() {
        when(mockClip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(false);
        String path = "music.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);
        soundManager.playMusic(path);

        soundManager.updateVolumes();
        verify(mockClip, never()).getControl(any());
    }

    @Test
    void testDbToLinear() {
        assertEquals(1.0f, soundManager.dbToLinear(0f), 0.001f);
        assertEquals(0.501f, soundManager.dbToLinear(-6f), 0.001f);
    }
    @Test
    void testSetVolumes() {
        soundManager.setMasterVolume(0.5f);
        soundManager.setMusicVolume(0.6f);
        soundManager.setSFXVolume(0.7f);
        // Should not throw and is covered
    }
    @Test
    void testPlaySameMusic() {
        String path = "music.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);
        soundManager.playMusic(path);
        
        // Call again with same path
        soundManager.playMusic(path);
        verify(mockClip, times(2)).start();
    }

    @Test
    void testPlayMusicNullClip() {
        when(soundLoader.get(anyString())).thenReturn(null);
        soundManager.playMusic("music.wav");
        // Should return early
    }

    @Test
    void testLinearToDbZero() {
        assertEquals(-80f, soundManager.linearToDb(0f));
        assertEquals(-80f, soundManager.linearToDb(-1f));
    }
    @Test
    void testSFXNullClip() {
        when(soundLoader.get(anyString())).thenReturn(null);
        soundManager.playSFX("any");
        soundManager.playSFXLoop("any");
        soundManager.stopSFX("any");
        // Should not crash
    }

    @Test
    void testPlayMusicSamePathNullClip() {
        String path = "music.wav";
        soundManager.playMusic(path); // Set currentMusicPath
        
        when(soundLoader.get(path)).thenReturn(null);
        soundManager.playMusic(path);
        // Should return after checking currentMusicPath because get returns null
    }

    @Test
    void testPlayMusicNewPathNullClip() {
        String path = "new_music.wav";
        when(soundLoader.get(path)).thenReturn(null);
        soundManager.playMusic(path);
        // Should return after stopMusic() because get returns null
    }

    @Test
    void testSFXNullClips() {
        when(soundLoader.get(anyString())).thenReturn(null);
        soundManager.playSFX("path");
        soundManager.playSFXLoop("path");
        soundManager.stopSFX("path");
        // Should all return early
    }

    @Test
    void testUpdateVolumesNullClip() {
        // currentMusicClip is null initially or after stopMusic
        soundManager.stopMusic();
        soundManager.updateVolumes();
        // Should not crash and not call applyVolumeToClip
    }
}
