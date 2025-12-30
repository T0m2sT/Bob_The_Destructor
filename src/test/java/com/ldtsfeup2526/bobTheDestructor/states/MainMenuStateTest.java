package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MainMenuStateTest {

    @Test
    void testUpdate() throws IOException {
        MainMenu model = mock(MainMenu.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        
        com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        MainMenuState state = new MainMenuState(model, spriteLoader, soundManager);
        
        com.ldtsfeup2526.bobTheDestructor.Game game = mock(com.ldtsfeup2526.bobTheDestructor.Game.class);
        com.ldtsfeup2526.bobTheDestructor.gui.GUI gui = mock(com.ldtsfeup2526.bobTheDestructor.gui.GUI.class);
        com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser actionParser = mock(com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser.class);
        when(actionParser.get()).thenReturn(java.util.List.of());
        
        state.update(game, gui, actionParser, 0.1);
        
        verify(gui, atLeastOnce()).refresh();
    }

    @Test
    void testEnterExit() throws IOException {
        MainMenu model = mock(MainMenu.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        com.ldtsfeup2526.bobTheDestructor.Game game = mock(com.ldtsfeup2526.bobTheDestructor.Game.class);
        when(game.getSoundManager()).thenReturn(soundManager);
        
        com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        MainMenuState state = new MainMenuState(model, spriteLoader, soundManager);
        state.onEnter(game);
        verify(soundManager).playMusic("sounds/mainMenuSoundtrack.wav");
        
        state.onExit(game);
        verify(soundManager).stopMusic();
        
        assertNotNull(state.createController());
        assertNotNull(state.createScreenViewer(new com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider(spriteLoader)));
    }
}
