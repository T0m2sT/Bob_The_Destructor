package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SettingsMenuStateTest {

    @Test
    void testConstructor() throws IOException {
        SettingsMenu model = mock(SettingsMenu.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        
        com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        SettingsMenuState state = new SettingsMenuState(model, spriteLoader, soundManager);
        
        assertNotNull(state.getModel());
        assertNotNull(state.createController());
        assertNotNull(state.createScreenViewer(mock(com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider.class)));
    }
    @Test
    void testEnterExit() throws IOException {
        SettingsMenu model = mock(SettingsMenu.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        com.ldtsfeup2526.bobTheDestructor.Game game = mock(com.ldtsfeup2526.bobTheDestructor.Game.class);
        when(game.getSoundManager()).thenReturn(soundManager);

        // Mock SpriteLoader to return valid sprites
        com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));

        SettingsMenuState state = new SettingsMenuState(model, spriteLoader, soundManager);
        state.onEnter(game);
        verify(soundManager).playMusic("sounds/mainMenuSoundtrack.wav");

        state.onExit(game);
        verify(soundManager).stopMusic();
    }
}
