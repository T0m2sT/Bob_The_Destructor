package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.screen.Screen;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public interface ScreenCreator {
    Screen createScreen(Resolution resolution, int fontSize, String title) throws URISyntaxException, IOException, FontFormatException;
}
