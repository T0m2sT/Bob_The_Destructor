package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;

class LanternaGUITest {
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
    void drawMiner() {
        gui.drawMiner(new Position(1, 1));

        Mockito.verify(tg, Mockito.times(1)).setForegroundColor(new TextColor.RGB(255, 255, 0));
        Mockito.verify(tg, Mockito.times(1)).putString(1, 1, "M");
    }

    @Test
    void drawElement() {
        gui.drawElement(new Position(2, 3));

        Mockito.verify(tg, Mockito.times(1)).setForegroundColor(new TextColor.RGB(255, 0, 0));
        Mockito.verify(tg, Mockito.times(1)).putString(2, 3, "X");
    }

    @Test
    void drawText() {
        gui.drawText(new Position(1, 1), "Test text", "blue");

        Mockito.verify(tg, Mockito.times(1)).setForegroundColor(new TextColor.RGB(0, 0, 255));
        Mockito.verify(tg, Mockito.times(1)).putString(1, 1, "Test text");
    }
}