package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class TitleViewerTest {
    private TitleViewer viewer;
    private SpriteLoader spriteLoader;
    private Sprite charSprite;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        charSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(charSprite);
        viewer = new TitleViewer(spriteLoader);
    }

    @Test
    void testDrawStringDifferentLengths() {
        GUI gui = mock(GUI.class);

        viewer.draw(new Position(100, 100), "BOBS", gui);
        
        InOrder inOrder = inOrder(charSprite);
        inOrder.verify(charSprite).setOffset(any());
        inOrder.verify(charSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 90 && p.getY() == 100), eq(gui));
        
        reset(charSprite);

        viewer.draw(new Position(100, 100), "BOB", gui);
        
        inOrder = inOrder(charSprite);
        inOrder.verify(charSprite).setOffset(any());
        inOrder.verify(charSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 93 && p.getY() == 100), eq(gui));
    }

    @Test
    void testDrawStringBoundary() {
        GUI gui = mock(GUI.class);
        // length 1 -> halfWidth = 1 * 5 / 2 = 2
        viewer.draw(new Position(100, 100), "A", gui);
        verify(charSprite).draw(argThat(p -> p.getX() == 98 && p.getY() == 100), eq(gui));
        
        reset(charSprite);
        // length 2 -> halfWidth = 2 * 5 / 2 = 5
        viewer.draw(new Position(100, 100), "AB", gui);
        verify(charSprite, times(2)).draw(argThat(p -> p.getX() == 95 && p.getY() == 100), eq(gui));
    }

    @Test
    void testDrawAtTextStart() {
        GUI gui = mock(GUI.class);
        viewer.drawAtTextStart(new Position(100, 100), "BOB", gui);
        // startTextPos = (100, 100)
        verify(charSprite, times(3)).draw(argThat(p -> p.getX() == 100 && p.getY() == 100), eq(gui));
        verify(charSprite, atLeastOnce()).setOffset(any());
    }
}
