package com.ldtsfeup2526.bobTheDestructor.gui;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GUILanternaTest {
    private GUILanterna gui;
    private Screen screen;
    private TextGraphics tg;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException, FontFormatException {
        LanternaScreenCreator lscreenCreator = mock(LanternaScreenCreator.class);
        screen = mock(Screen.class);
        tg = mock(TextGraphics.class);

        when(lscreenCreator.createScreen(any(), anyInt(), any(), any())).thenReturn(screen);
        when(screen.newTextGraphics()).thenReturn(tg);


        gui = new GUILanterna(lscreenCreator, mock(KeyListener.class), new Resolution(240, 135), 6, "Test");
    }

    @Test
    void getScreen() {
        assert gui.getScreen() == screen;
    }


    @Test
    void drawPixel() {
        gui.drawPixel(new Position(2, 3),new TextColor.RGB(255, 0, 0));
        verify(tg, times(1)).setBackgroundColor(new TextColor.RGB(255, 0, 0));
        verify(tg, times(1)).setCharacter(2, 3, ' ');
    }

    @Test
    void clearScreen() {
        gui.clear();
        verify(screen, times(1)).clear();
    }

    @Test
    void refreshScreen() throws IOException {
        gui.refresh();
        verify(screen, times(1)).refresh();
    }

    @Test
    void closeScreen() throws IOException {
        gui.close();
        verify(screen, times(1)).close();
    }
}
