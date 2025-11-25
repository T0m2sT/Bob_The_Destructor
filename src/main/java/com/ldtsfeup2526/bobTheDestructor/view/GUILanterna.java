package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GUILanterna extends GUI {
    private Screen screen;

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
        final Screen screen;
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);   // we will not use the cursor yet
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    public GUILanterna() throws IOException {
        this(200, 60);
    }

    private void draw(Position position, char c, String color) {
        // Draws the character c with given color to the screen in the provided position
        // (text graphics should be replaced with sprites later)
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.putString(position.getX(), position.getY(), "" + c);
    }

    @Override
    public void drawMiner(Position position) {
        draw(position, 'M', "yellow");
    }

    @Override
    public void drawElement(Position position){
        draw(position, 'X', "red");
    }

    @Override
    public void drawText(Position position, String text, String color) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.putString(position.getX(), position.getY(), text);
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
