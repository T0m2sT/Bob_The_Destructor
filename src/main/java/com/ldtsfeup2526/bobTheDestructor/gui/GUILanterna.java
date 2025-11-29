package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldtsfeup2526.bobTheDestructor.controller.input.InputReader;
import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;

public class GUILanterna implements GUI {
    private final ScreenCreator screenCreator = new LanternaScreenCreator();
    private final String title;
    private final Resolution resolution;
    private Screen screen;

    public GUILanterna(KeyListener keyListener, String title) throws IOException, URISyntaxException, FontFormatException {
        this.title = title;
        this.resolution = new Resolution(240, 135);
        createScreen(6, keyListener);
    }

    public GUILanterna(KeyListener keyListener, Resolution resolution, int fontSize, String title) throws IOException, URISyntaxException, FontFormatException {
        this.title = title;
        this.resolution = resolution;
        createScreen(fontSize, keyListener);
    }

    private void createScreen(int fontSize, KeyListener keyListener) throws IOException, URISyntaxException, FontFormatException {
        screen = screenCreator.createScreen(resolution, fontSize, title, keyListener);

        screen.setCursorPosition(null);
        screen.startScreen();
    }

    @Override
    public void drawPixel(Position position, TextColor color) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(color);
        graphics.setCharacter(position.getX(), position.getY(), ' ');
    }

    public Screen getScreen() {
        return screen;
    }

    @Override
    public void clear(){
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }
}
