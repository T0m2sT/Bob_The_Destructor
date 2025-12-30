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
        verify(mockClip).setFramePosition(0);
        verify(mockClip).start();
        verify(mockControl).setValue(anyFloat());

        // Play same music again
        soundManager.playMusic(path);
        verify(mockClip, times(2)).start();
        // verify loop was NOT called again (it was only called when path changed)
        verify(mockClip, times(1)).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Test
    void testPlayMusicChangesPath() {
        String path1 = "music1.wav";
        String path2 = "music2.wav";
        Clip clip1 = mock(Clip.class);
        Clip clip2 = mock(Clip.class);
        when(soundLoader.get(path1)).thenReturn(clip1);
        when(soundLoader.get(path2)).thenReturn(clip2);
        
        soundManager.playMusic(path1);
        verify(clip1).start();
        
        soundManager.playMusic(path2);
        verify(clip1).stop();
        verify(clip2).start();
        verify(clip2).loop(Clip.LOOP_CONTINUOUSLY);
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
        verify(mockControl).setValue(anyFloat());
    }

    @Test
    void testPlaySFXLoop() {
        String path = "sfx.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);

        soundManager.playSFXLoop(path);
        verify(mockClip).setFramePosition(0);
        verify(mockClip).loop(Clip.LOOP_CONTINUOUSLY);
        verify(mockClip).start();
        verify(mockControl).setValue(anyFloat());
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
        
        reset(mockControl);
        soundManager.updateVolumes();
        // 0.5 * 0.8 = 0.4
        // linearToDb(0.4) = 20 * log10(0.4) approx -7.95
        verify(mockControl).setValue(floatThat(v -> Math.abs(v - (-7.9588f)) < 0.01f));
    }

    @Test
    void testLinearToDbEdgeCase() {
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
    }
    @Test
    void testPlaySameMusic() {
        String path = "music.wav";
        when(soundLoader.get(path)).thenReturn(mockClip);
        soundManager.playMusic(path);
        
        soundManager.playMusic(path);
        verify(mockClip, times(2)).start();
    }

    @Test
    void testPlayMusicNullClip() {
        when(soundLoader.get(anyString())).thenReturn(null);
        soundManager.playMusic("music.wav");
    }

    @Test
    void testLinearToDbDetailed() {
        // linearToDb(v) = 20 * log10(v)
        // linearToDb(1.0) = 0
        // linearToDb(0.1) = -20
        // v = masterVolume * volume
        
        soundManager.setMasterVolume(1.0f);
        soundManager.setMusicVolume(0.1f);
        when(soundLoader.get("music.wav")).thenReturn(mockClip);
        soundManager.playMusic("music.wav");
        
        // Final volume = 1.0 * 0.1 = 0.1
        // linearToDb(0.1) = -20.0f
        verify(mockControl).setValue(eq(-20.0f));
    }

    @Test
    void testApplyVolumeToClipMultiplication() {
        soundManager.setMasterVolume(0.5f);
        soundManager.setSFXVolume(0.5f);
        when(soundLoader.get("sfx.wav")).thenReturn(mockClip);
        
        soundManager.playSFX("sfx.wav");
        
        // finalVolume = 0.5 * 0.5 = 0.25
        // linearToDb(0.25) = 20 * log10(0.25) approx -12.04
        verify(mockControl).setValue(floatThat(v -> Math.abs(v - (-12.0412f)) < 0.001f));
    }
    @Test
    void testSFXNullClip() {
        when(soundLoader.get(anyString())).thenReturn(null);
        soundManager.playSFX("any");
        soundManager.playSFXLoop("any");
        soundManager.stopSFX("any");
    }

    @Test
    void testPlayMusicSamePathNullClip() {
        String path = "music.wav";
        soundManager.playMusic(path);
        
        when(soundLoader.get(path)).thenReturn(null);
        soundManager.playMusic(path);
    }

    @Test
    void testPlayMusicNewPathNullClip() {
        String path = "new_music.wav";
        when(soundLoader.get(path)).thenReturn(null);
        soundManager.playMusic(path);
    }

    @Test
    void testSFXNullClips() {
        when(soundLoader.get(anyString())).thenReturn(null);
        soundManager.playSFX("path");
        soundManager.playSFXLoop("path");
        soundManager.stopSFX("path");
    }

    @Test
    void testUpdateVolumesNullClip() {
        soundManager.stopMusic();
        soundManager.updateVolumes();
    }
}
