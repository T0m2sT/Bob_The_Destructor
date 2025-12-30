package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.FontFormatException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GUILanternaTest {
    private Screen screen;
    private ScreenCreator screenCreator;
    private GUILanterna gui;
    private KeyListener keyListener;
    private Resolution resolution;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException, FontFormatException {
        screen = mock(Screen.class);
        screenCreator = mock(ScreenCreator.class);
        keyListener = mock(KeyListener.class);
        resolution = new Resolution(10, 10);
        
        when(screenCreator.createScreen(any(), anyInt(), anyString(), any())).thenReturn(screen);
        
        gui = new GUILanterna(screenCreator, keyListener, resolution, 12, "Title");
        
        // Verify initialization calls
        verify(screen).setCursorPosition(null);
        verify(screen).startScreen();
    }

    @Test
    void testDrawPixel() {
        TextGraphics graphics = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        
        Position pos = new Position(1, 2);
        TextColor color = new TextColor.RGB(255, 0, 0);
        gui.drawPixel(pos, color);
        
        verify(graphics).setBackgroundColor(color);
        verify(graphics).setCharacter(1, 2, ' ');
        verifyNoMoreInteractions(graphics);
    }

    @Test
    void testClear() {
        gui.clear();
        verify(screen).clear();
    }

    @Test
    void testRefresh() throws IOException {
        gui.refresh();
        verify(screen).refresh();
    }

    @Test
    void testClose() throws IOException {
        gui.close();
        verify(screen).close();
    }
    
    @Test
    void testDrawBackground() {
        TextGraphics graphics = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        TextColor color = new TextColor.RGB(0, 0, 0);
        
        gui.drawBackground(color);
        
        verify(graphics, times(1)).setBackgroundColor(color);
        verify(graphics, times(100)).setCharacter(anyInt(), anyInt(), eq(' '));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                verify(graphics).setCharacter(i, j, ' ');
            }
        }
        verifyNoMoreInteractions(graphics);
    }

    @Test
    void testGetScreen() {
        assertEquals(screen, gui.getScreen());
    }

    @Test
    void testDrawBackgroundLoops() throws Exception {
        TextGraphics graphics = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        Resolution res = new Resolution(2, 2);
        GUILanterna gui2 = new GUILanterna(screenCreator, keyListener, res, 8, "Title");
        
        gui2.drawBackground(new TextColor.RGB(0, 0, 0));
        
        // Should draw 4 pixels (2x2)
        verify(graphics, times(4)).setCharacter(anyInt(), anyInt(), eq(' '));
    }

    @Test
    void testDrawPixelWithDifferentColors() {
        TextGraphics graphics = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        
        gui.drawPixel(new Position(1, 1), TextColor.ANSI.BLUE);
        verify(graphics).setBackgroundColor(TextColor.ANSI.BLUE);
        verify(graphics).setCharacter(1, 1, ' ');
    }

    @Test
    void testConstructorsWithoutScreenCreator() throws IOException, URISyntaxException, FontFormatException {
        try (var mockedLanternaScreenCreator = mockConstruction(LanternaScreenCreator.class, (mock, context) -> {
            when(mock.createScreen(any(), anyInt(), anyString(), any())).thenReturn(mock(Screen.class));
        })) {
            new GUILanterna(keyListener, "Title");
            assertEquals(1, mockedLanternaScreenCreator.constructed().size());

            new GUILanterna(keyListener, resolution, 12, "Title");
            assertEquals(2, mockedLanternaScreenCreator.constructed().size());
        }
    }
}
