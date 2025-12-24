package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.mockito.Mockito.*;

public class MainMenuControllerTest {
    private MainMenu menu;
    private MainMenuController controller;
    private Game game;
    private SoundPlayer soundPlayer;

    @BeforeEach
    void setUp() throws IOException {
        menu = mock(MainMenu.class);
        soundPlayer = mock(SoundPlayer.class);
        when(menu.getSoundPlayer()).thenReturn(soundPlayer);

        when(soundPlayer.getSound()).thenReturn(null);
        
        controller = new MainMenuController(menu);
        game = mock(Game.class);
    }

    @Test
    void testOnQuit() {
        controller.onQuit(game);
        verify(game).setState(null);
    }

    @Test
    void testUpdateUp() throws IOException {
        controller.update(game, List.of(Action.UP));
        verify(menu).moveUp();
    }

    @Test
    void testUpdateDown() throws IOException {
        controller.update(game, List.of(Action.DOWN));
        verify(menu).moveDown();
    }

    @Test
    void testUpdateQuit() throws IOException {
        controller.update(game, List.of(Action.QUIT));
        verify(game).setState(null);
    }

    @Test
    void testConstructorWithSound() {
        MainMenu menuWithSound = mock(MainMenu.class);
        SoundPlayer player = mock(SoundPlayer.class);
        Clip clip = mock(Clip.class);
        FloatControl control = mock(FloatControl.class);
        
        when(menuWithSound.getSoundPlayer()).thenReturn(player);
        when(player.getSound()).thenReturn(clip);
        when(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(true);
        when(clip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(control);
        
        new MainMenuController(menuWithSound);
        
        verify(control).setValue(anyFloat());
        verify(player).start();
    }

    @Test
    void testConstructorWithSoundNoControl() {
        MainMenu menuWithSound = mock(MainMenu.class);
        SoundPlayer player = mock(SoundPlayer.class);
        Clip clip = mock(Clip.class);
        
        when(menuWithSound.getSoundPlayer()).thenReturn(player);
        when(player.getSound()).thenReturn(clip);
        when(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(false);
        
        new MainMenuController(menuWithSound);
        
        verify(player).start();
    }

    @Test
    void testUpdateOtherAction() throws IOException {
        com.ldtsfeup2526.bobTheDestructor.model.menu.Button button = mock(com.ldtsfeup2526.bobTheDestructor.model.menu.Button.class);
        when(menu.getCurrentButton()).thenReturn(button);
        when(button.getButtonType()).thenReturn(com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType.EXIT);
        
        controller.update(game, List.of(Action.SELECT));
        verify(game).setState(null);
    }
}
