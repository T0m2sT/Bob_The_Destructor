package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        verify(charSprite, times(4)).draw(argThat(p -> p.getX() == 90 && p.getY() == 100), eq(gui));
        
        reset(charSprite);

        viewer.draw(new Position(100, 100), "BOB", gui);
        verify(charSprite, times(3)).draw(argThat(p -> p.getX() == 93 && p.getY() == 100), eq(gui));
        verify(charSprite, atLeastOnce()).setOffset(any());
    }

    @Test
    void testDrawAtTextStart() {
        GUI gui = mock(GUI.class);
        viewer.drawAtTextStart(new Position(100, 100), "BOB", gui);
        // startTextPos = (100, 100)
        verify(charSprite, times(3)).draw(argThat(p -> p.getX() == 100 && p.getY() == 100), eq(gui));
        verify(charSprite, times(3)).setOffset(any());
    }
}
