package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlayerViewerTest {
    private PlayerViewer viewer;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);

        doAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            com.ldtsfeup2526.bobTheDestructor.gui.GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).draw(any(), any());

        doAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            com.ldtsfeup2526.bobTheDestructor.gui.GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).drawFlipX(any(), any());

        viewer = new PlayerViewer(spriteLoader);
    }

    @Test
    void testDraw() {
        PlayerModel model = mock(PlayerModel.class);
        when(model.getState()).thenReturn(new IdleState(model));
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);

        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }

    @Test
    void testDrawMining() {
        PlayerModel model = mock(PlayerModel.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        when(model.getRigidBody()).thenReturn(rb);
        when(rb.getAcceleration()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        when(rb.getVelocity()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));

        com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState miningState = new com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState(model, null);
        when(model.getState()).thenReturn(miningState);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        GUI gui = mock(GUI.class);
        
        viewer.draw(model, gui, 0.3);
        verify(model).notifyWhenPickaxeHit();
        verify(model).notifyWhenAnimFinished(eq("MineAnim"));
    }

    @Test
    void testStateChangeStopsAnim() {
        PlayerModel model = mock(PlayerModel.class);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        GUI gui = mock(GUI.class);

        when(model.getState()).thenReturn(new IdleState(model));
        viewer.draw(model, gui, 0.1);

        when(model.getState()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.WalkingState(model));
        viewer.draw(model, gui, 0.1);
        
        when(model.getState()).thenReturn(new IdleState(model));
        viewer.draw(model, gui, 0.1);
    }

    @Test
    void testConstructorSetsOffsets() throws IOException {
        Sprite mockSprite = spriteLoader.get("");
        verify(mockSprite, atLeastOnce()).setOffset(any());
    }
    @Test
    void testDrawLookingLeft() {
        PlayerModel model = mock(PlayerModel.class);
        when(model.getState()).thenReturn(new IdleState(model));
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(false);
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);

        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }
    @Test
    void testDrawAllStates() {
        Class<?>[] states = {
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.WalkingState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.JumpingState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.FallingState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState.class
        };
        
        GUI gui = mock(GUI.class);
        PlayerModel model = mock(PlayerModel.class);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        
        for (Class<?> stateClass : states) {
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState state = (com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState) mock(stateClass);
            when(model.getState()).thenReturn(state);
            viewer.draw(model, gui, 0.1);
        }
    }
}
