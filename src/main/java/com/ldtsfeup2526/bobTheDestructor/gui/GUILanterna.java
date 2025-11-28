package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class GUILanterna implements GUI {
    //To review if variable should be on parent class;
    private ScreenCreator screenCreator = new LanternaScreenCreator();
    private final String title;
    private final Resolution resolution;
    private Screen screen;

    public GUILanterna(String title) throws IOException, URISyntaxException, FontFormatException {
        this.title = title;
        this.resolution = new Resolution(240, 135);
        createScreen(6);
    }

    public GUILanterna(LanternaScreenCreator screenCreator, String title) throws IOException, URISyntaxException, FontFormatException {
        this.title = title;
        this.resolution = new Resolution(240, 135);
        this.screenCreator = screenCreator;
        createScreen(6);
    }

    public GUILanterna(Resolution resolution, int fontSize, String title) throws IOException, URISyntaxException, FontFormatException {
        this.title = title;
        this.resolution = resolution;
        createScreen(fontSize);
    }

    private void createScreen(int fontSize) throws IOException, URISyntaxException, FontFormatException {
        screen = screenCreator.createScreen(resolution, fontSize, title);

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
