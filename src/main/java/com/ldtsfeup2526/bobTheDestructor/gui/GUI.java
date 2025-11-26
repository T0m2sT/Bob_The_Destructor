package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.ldtsfeup2526.bobTheDestructor.model.Position;

import java.io.IOException;

public interface GUI {

    Screen getScreen();
    void drawPixel(Position position, TextColor color);
    void clear();
    void refresh() throws IOException;
    void close() throws IOException;
}
