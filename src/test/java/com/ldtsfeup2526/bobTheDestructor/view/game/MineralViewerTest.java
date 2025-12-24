package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralType;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.PointingDirection;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MineralViewerTest {
    private MineralViewer viewer;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        doAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).draw(any(), any());
        
        viewer = new MineralViewer(spriteLoader);
    }

    @Test
    void testDraw() {
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.UNSELECTED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);
        
        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }

    @Test
    void testDrawDirections() throws IOException {
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.SELECTED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        GUI gui = mock(GUI.class);
        Sprite sprite = spriteLoader.get(""); // cached mock

        when(model.getDirection()).thenReturn(PointingDirection.DOWN);
        viewer.draw(model, gui, 0.1);
        verify(sprite, atLeastOnce()).drawFlipY(any(), any());

        when(model.getDirection()).thenReturn(PointingDirection.LEFT);
        viewer.draw(model, gui, 0.1);
        verify(sprite, atLeastOnce()).drawRotLeft(any(), any());

        when(model.getDirection()).thenReturn(PointingDirection.RIGHT);
        viewer.draw(model, gui, 0.1);
        verify(sprite, atLeastOnce()).drawRotRight(any(), any());
    }

    @Test
    void testDrawDestroyed() {
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.DESTROYED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        GUI gui = mock(GUI.class);
        
        viewer.draw(model, gui, 1.0); // Large deltaTime to finish animation
        verify(model, atLeastOnce()).notifyWhenAnimFinished(eq("CrackAnim"));
    }
}
