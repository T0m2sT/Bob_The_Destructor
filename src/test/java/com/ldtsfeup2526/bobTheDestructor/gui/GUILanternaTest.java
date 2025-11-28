package com.ldtsfeup2526.bobTheDestructor.gui;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class GUILanternaTest {
    private GUILanterna gui;
    private Screen screen;
    private TextGraphics tg;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException, FontFormatException {
        LanternaScreenCreator lscreenCreator = Mockito.mock(LanternaScreenCreator.class);
        screen = Mockito.mock(Screen.class);
        tg = Mockito.mock(TextGraphics.class);

        Mockito.when(lscreenCreator.createScreen(any(), anyInt(), any())).thenReturn(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(tg);


        gui = new GUILanterna(lscreenCreator, "Bob, The Destructor");
    }

    @Test
    void getScreen() {
        assert gui.getScreen() == screen;
    }


    @Test
    void drawPixel() {
        gui.drawPixel(new Position(2, 3),new TextColor.RGB(255, 0, 0));
        Mockito.verify(tg, Mockito.times(1)).setBackgroundColor(new TextColor.RGB(255, 0, 0));
        Mockito.verify(tg, Mockito.times(1)).setCharacter(2, 3, ' ');
    }

    @Test
    void clearScreen() {
        gui.clear();
        Mockito.verify(screen, Mockito.times(1)).clear();
    }

    @Test
    void refreshScreen() throws IOException {
        gui.refresh();
        Mockito.verify(screen, Mockito.times(1)).refresh();
    }

    @Test
    void closeScreen() throws IOException {
        gui.close();
        Mockito.verify(screen, Mockito.times(1)).close();
    }
}
