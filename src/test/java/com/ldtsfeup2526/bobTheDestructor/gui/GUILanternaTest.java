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
    }

    @Test
    void testDrawPixel() {
        TextGraphics graphics = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        
        gui.drawPixel(new Position(1, 2), new TextColor.RGB(255, 0, 0));
        
        verify(graphics).setBackgroundColor(new TextColor.RGB(255, 0, 0));
        verify(graphics).setCharacter(1, 2, ' ');
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
        
        verify(graphics, times(100)).setCharacter(anyInt(), anyInt(), eq(' '));
        verify(graphics, atLeastOnce()).setBackgroundColor(color);
    }
}
