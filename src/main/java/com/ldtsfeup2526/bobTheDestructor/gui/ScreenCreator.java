package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.screen.Screen;
import com.ldtsfeup2526.bobTheDestructor.controller.input.InputReader;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;

public interface ScreenCreator {
    Screen createScreen(Resolution resolution, int fontSize, String title, KeyListener keyListener) throws URISyntaxException, IOException, FontFormatException;
}
