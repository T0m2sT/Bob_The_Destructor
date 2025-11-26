package com.ldtsfeup2526.bobTheDestructor.gui;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GUILanternaTest {
    private Screen screen;
    private GUILanterna gui;
    private TextGraphics tg;

    @BeforeEach
    void setUp() {
        screen = Mockito.mock(Screen.class);
        tg = Mockito.mock(TextGraphics.class);

        Mockito.when(screen.newTextGraphics()).thenReturn(tg);

        gui = new GUILanterna(screen);
    }

    @Test
    void drawPixel() {
        gui.drawPixel(new Position(2, 3),new TextColor.RGB(255, 0, 0));

        Mockito.verify(tg, Mockito.times(1)).setForegroundColor(new TextColor.RGB(255, 0, 0));
        Mockito.verify(tg, Mockito.times(1)).putString(2, 3, "X");
    }
}