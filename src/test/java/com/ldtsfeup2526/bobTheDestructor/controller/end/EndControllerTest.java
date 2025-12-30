package com.ldtsfeup2526.bobTheDestructor.controller.end;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.stats.Stats;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.mockito.Mockito.*;

public class EndControllerTest {
    @Test
    void testUpdateQuit() throws IOException {
        Stats model = mock(Stats.class);
        EndController controller = new EndController(model);
        Game game = mock(Game.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        when(game.getSoundManager()).thenReturn(soundManager);
        
        Sprite mockSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(mockSprite);
        when(mockSprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        controller.update(game, List.of(Action.QUIT), 0.1);
        verify(game).setState(any(MainMenuState.class));
    }

    @Test
    void testUpdateNoAction() throws IOException {
        Stats model = mock(Stats.class);
        EndController controller = new EndController(model);
        Game game = mock(Game.class);
        
        controller.update(game, List.of(Action.UP), 0.1);
        verify(game, never()).setState(any());
    }
    @Test
    void testUpdateMultipleActions() throws IOException {
        Stats model = mock(Stats.class);
        EndController controller = new EndController(model);
        Game game = mock(Game.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        when(game.getSoundManager()).thenReturn(soundManager);
        Sprite mockSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(mockSprite);
        when(mockSprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));

        controller.update(game, List.of(Action.UP, Action.QUIT), 0.1);
        verify(game, times(1)).setState(any(MainMenuState.class));
    }

    @Test
    void testUpdateEmpty() throws IOException {
        Stats model = mock(Stats.class);
        EndController controller = new EndController(model);
        Game game = mock(Game.class);
        controller.update(game, List.of(), 0.1);
        verify(game, never()).setState(any());
    }
}
