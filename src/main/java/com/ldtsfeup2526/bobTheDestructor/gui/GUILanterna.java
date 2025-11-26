package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GUILanterna implements GUI {
    private final Screen screen;

    public GUILanterna(int width, int height) throws IOException {
        Terminal terminal = createTerminal(width, height);

        this.screen = createScreen(terminal);
    }

    private Terminal createTerminal(int width, int height) throws IOException {
        TerminalSize terminalSize = new TerminalSize(width, height);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();
        return terminal;
    }

    private Screen createScreen(Terminal terminal) throws IOException {
        Screen screen;
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);   // we will not use the cursor yet
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    public GUILanterna() throws IOException {
        this(200, 60);
    }

    public GUILanterna(Screen screen) {this.screen = screen;}

    public Screen getScreen() {return screen;}

    @Override
    public void drawPixel(Position position, TextColor color) {
        // Draws the character c with given color to the screen in the provided position
        // (text graphics should be replaced with sprites later)
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(color);
        graphics.putString(position.getX(), position.getY(), "X");
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
