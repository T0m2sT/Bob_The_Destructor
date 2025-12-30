package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class SettingsMenuControllerTest {
    private SettingsMenu menu;
    private SettingsMenuController controller;
    private Game game;
    private WidgetController widgetController;

    @BeforeEach
    void setUp() throws IOException {
        menu = mock(SettingsMenu.class);
        widgetController = mock(WidgetController.class);
        controller = new SettingsMenuController(menu, widgetController);
        game = mock(Game.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        when(game.getSoundManager()).thenReturn(soundManager);
        
        Sprite mockSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(mockSprite);
        when(mockSprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
    }

    @Test
    void testUpdateUp() throws IOException {
        controller.update(game, List.of(Action.UP), 0.1);
        verify(menu).moveUp();
        verify(widgetController).updateWidgetState();
    }

    @Test
    void testUpdateDown() throws IOException {
        controller.update(game, List.of(Action.DOWN), 0.1);
        verify(menu).moveDown();
        verify(widgetController).updateWidgetState();
    }

    @Test
    void testUpdateQuit() throws IOException {
        controller.update(game, List.of(Action.QUIT), 0.1);
        verify(game, atLeastOnce()).setState(any());
    }

    @Test
    void testUpdateOtherAction() throws IOException {
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(widgetController).update(eq(game), anyList(), eq(0.1));
    }
    @Test
    void testUpdateMultipleActions() throws IOException {
        controller.update(game, List.of(Action.UP, Action.DOWN), 0.1);
        verify(menu).moveUp();
        verify(menu).moveDown();
        verify(widgetController, times(2)).updateWidgetState();
    }
}
