package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.*;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.states.CreditsState;
import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.states.SettingsMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class WidgetControllerTest {
    private Menu menu;
    private SoundManager soundManager;
    private WidgetController controller;
    private Game game;
    private Widget currentWidget;

    @BeforeEach
    void setUp() throws IOException {
        menu = mock(Menu.class);
        soundManager = mock(SoundManager.class);
        controller = new WidgetController(menu, soundManager);
        game = mock(Game.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        when(game.getSoundManager()).thenReturn(soundManager);
        currentWidget = mock(Widget.class);
        when(menu.getCurrentWidget()).thenReturn(currentWidget);
        
        Sprite mockSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(mockSprite);
        when(mockSprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(new java.awt.image.BufferedImage(8, 8, java.awt.image.BufferedImage.TYPE_INT_ARGB));
    }

    @Test
    void testUpdatePlay() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game).setState(any(GameState.class));
    }

    @Test
    void testUpdateConfig() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.CONFIG);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game).setState(any(SettingsMenuState.class));
    }

    @Test
    void testUpdateCredits() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.CREDITS);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game).setState(any(CreditsState.class));
        
        // Mutate to test that any action triggers it (based on code)
        reset(game);
        Sprite s = mock(Sprite.class);
        when(s.getSize()).thenReturn(new Size(1, 1));
        SpriteLoader sl = mock(SpriteLoader.class);
        when(sl.get(anyString())).thenReturn(s);
        when(game.getSpriteLoader()).thenReturn(sl);
        when(game.getSoundManager()).thenReturn(soundManager);
        controller.update(game, List.of(Action.UP), 0.1);
        verify(game, atLeastOnce()).setState(any(CreditsState.class));
    }

    @Test
    void testUpdateExit() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.EXIT);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game).setState(null);
    }

    @Test
    void testUpdateVolumesDetailed() throws IOException {
        GameSettings settings = GameSettings.getInstance();
        settings.setMasterVolume(0.5f);
        settings.setMusicVolume(0.5f);
        settings.setSfxVolume(0.5f);

        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MASTER_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(soundManager).setMasterVolume(floatThat(v -> Math.abs(v - 0.4f) < 0.001f));
        verify(soundManager).updateVolumes();

        reset(soundManager);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MUSIC_VOLUME);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        verify(soundManager).setMusicVolume(floatThat(v -> Math.abs(v - 0.6f) < 0.001f));
        verify(soundManager).updateVolumes();

        reset(soundManager);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.SFX_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(soundManager).setSFXVolume(floatThat(v -> Math.abs(v - 0.4f) < 0.001f));
        verify(soundManager).updateVolumes();

        // Test no action for volume
        reset(soundManager);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.EXIT);
        controller.update(game, List.of(Action.UP), 0.1);
        verify(soundManager, never()).setMasterVolume(anyFloat());
        verify(soundManager, never()).setMusicVolume(anyFloat());
        verify(soundManager, never()).setSFXVolume(anyFloat());
        verify(soundManager, never()).updateVolumes();
    }

    @Test
    void testUpdateWidgetState() {
        Widget w1 = new Widget(WidgetType.PLAY, WidgetState.SELECTED, null);
        Widget w2 = new Widget(WidgetType.EXIT, WidgetState.UNSELECTED, null);
        when(menu.getWidgets()).thenReturn(List.of(w1, w2));
        when(menu.getCurrentWidget()).thenReturn(w2);

        controller.updateWidgetState();

        assertEquals(WidgetState.UNSELECTED, w1.getWidgetState());
        assertEquals(WidgetState.SELECTED, w2.getWidgetState());
    }
    @Test
    void testUpdateVolumesMinMax() throws IOException {
        GameSettings settings = GameSettings.getInstance();
        settings.setMasterVolume(0.05f);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MASTER_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        assertEquals(0.0f, settings.getMasterVolume(), 0.001f);

        settings.setMasterVolume(0.95f);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        assertEquals(1.0f, settings.getMasterVolume(), 0.001f);

        settings.setMusicVolume(0.05f);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MUSIC_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        assertEquals(0.0f, settings.getMusicVolume(), 0.001f);

        settings.setMusicVolume(0.95f);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        assertEquals(1.0f, settings.getMusicVolume(), 0.001f);

        settings.setSfxVolume(0.05f);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.SFX_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        assertEquals(0.0f, settings.getSfxVolume(), 0.001f);

        settings.setSfxVolume(0.95f);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        assertEquals(1.0f, settings.getSfxVolume(), 0.001f);
    }

    @Test
    void testUpdateNoAction() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.UP), 0.1);
        verify(game, never()).setState(any());
    }
    @Test
    void testUpdateExitNoSelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.EXIT);
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(game, never()).setState(null);
    }

    @Test
    void testUpdatePlayNoSelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(game, never()).setState(any());
    }

    @Test
    void testUpdateConfigNoSelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.CONFIG);
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(game, never()).setState(any());
    }

    @Test
    void testUpdateVolumesNoAction() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MASTER_VOLUME);
        controller.update(game, List.of(Action.SELECT), 0.1);
    }
    @Test
    void testUpdateCreditsWithOtherAction() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.CREDITS);
        controller.update(game, List.of(Action.UP), 0.1);
        verify(game).setState(any(CreditsState.class));
    }

    @Test
    void testUpdateExitSelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.EXIT);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game).setState(null);
    }
    @Test
    void testUpdatePlaySelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game).setState(any(GameState.class));
    }

    @Test
    void testUpdateMultipleActions() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.UP, Action.SELECT), 0.1);
        verify(game, times(1)).setState(any(GameState.class));
        
        // Test multiple volume actions
        GameSettings settings = GameSettings.getInstance();
        settings.setMasterVolume(0.5f);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MASTER_VOLUME);
        controller.update(game, List.of(Action.LEFT, Action.LEFT), 0.1);
        assertEquals(0.3f, settings.getMasterVolume(), 0.001f);
    }

    @Test
    void testUpdatePlayNotSelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.UP), 0.1);
        verify(game, never()).setState(any());
    }

    @Test
    void testUpdateConfigNotSelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.CONFIG);
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(game, never()).setState(any());
    }

    @Test
    void testUpdateExitNotSelect() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.EXIT);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        verify(game, never()).setState(any());
    }

    @Test
    void testUpdateVolumesNotLeftRight() throws IOException {
        GameSettings settings = GameSettings.getInstance();
        float master = settings.getMasterVolume();
        float music = settings.getMusicVolume();
        float sfx = settings.getSfxVolume();

        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MASTER_VOLUME);
        controller.update(game, List.of(Action.SELECT), 0.1);
        assertEquals(master, settings.getMasterVolume());

        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MUSIC_VOLUME);
        controller.update(game, List.of(Action.UP), 0.1);
        assertEquals(music, settings.getMusicVolume());

        when(currentWidget.getWidgetType()).thenReturn(WidgetType.SFX_VOLUME);
        controller.update(game, List.of(Action.DOWN), 0.1);
        assertEquals(sfx, settings.getSfxVolume());
    }

    @Test
    void testUpdateEmptyActions() throws IOException {
        controller.update(game, List.of(), 0.1);
        verify(game, never()).setState(any());
    }
    @Test
    void testUpdateVolumesBoundary() throws IOException {
        GameSettings settings = GameSettings.getInstance();
        settings.setMasterVolume(0.05f);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MASTER_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        assertEquals(0.0f, settings.getMasterVolume(), 0.001f);

        settings.setMasterVolume(0.95f);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        assertEquals(1.0f, settings.getMasterVolume(), 0.001f);

        settings.setMusicVolume(0.05f);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.MUSIC_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        assertEquals(0.0f, settings.getMusicVolume(), 0.001f);

        settings.setMusicVolume(0.95f);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        assertEquals(1.0f, settings.getMusicVolume(), 0.001f);

        settings.setSfxVolume(0.05f);
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.SFX_VOLUME);
        controller.update(game, List.of(Action.LEFT), 0.1);
        assertEquals(0.0f, settings.getSfxVolume(), 0.001f);

        settings.setSfxVolume(0.95f);
        controller.update(game, List.of(Action.RIGHT), 0.1);
        assertEquals(1.0f, settings.getSfxVolume(), 0.001f);
    }
    @Test
    void testUpdateCreditsAnyAction() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.CREDITS);
        controller.update(game, List.of(Action.UP), 0.1);
        verify(game).setState(any(CreditsState.class));
    }
    @Test
    void testUpdateNullWidgetAndType() throws IOException {
        when(menu.getCurrentWidget()).thenReturn(null);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game, never()).setState(any());

        Widget w = mock(Widget.class);
        when(menu.getCurrentWidget()).thenReturn(w);
        when(w.getWidgetType()).thenReturn(null);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game, never()).setState(any());
    }
    @Test
    void testUpdateMultipleActionsUnique() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.UP, Action.SELECT), 0.1);
        verify(game).setState(any(GameState.class));
    }
    @Test
    void testUpdateMultipleActionsDistinct() throws IOException {
        when(currentWidget.getWidgetType()).thenReturn(WidgetType.PLAY);
        controller.update(game, List.of(Action.UP, Action.SELECT), 0.1);
        verify(game).setState(any(GameState.class));
    }

    @Test
    void testUpdateNoActions() throws IOException {
        controller.update(game, List.of(), 0.1);
        verify(game, never()).setState(any());
    }
    @Test
    void testUpdateNullWidgetInList() throws IOException {
        Widget w1 = mock(Widget.class);
        when(w1.getWidgetType()).thenReturn(null);
        Widget w2 = mock(Widget.class);
        when(w2.getWidgetType()).thenReturn(WidgetType.PLAY);
        
        when(menu.getCurrentWidget()).thenReturn(w1).thenReturn(w2);
        
        controller.update(game, List.of(Action.UP, Action.SELECT), 0.1);
        verify(game).setState(any(GameState.class));
    }
    @Test
    void testUpdateWidgetIsNullInLoop() throws IOException {
        when(menu.getCurrentWidget()).thenReturn(null);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game, never()).setState(any());
    }

    @Test
    void testUpdateWidgetTypeIsNullInLoop() throws IOException {
        Widget w = mock(Widget.class);
        when(w.getWidgetType()).thenReturn(null);
        when(menu.getCurrentWidget()).thenReturn(w);
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(game, never()).setState(any());
    }
}
