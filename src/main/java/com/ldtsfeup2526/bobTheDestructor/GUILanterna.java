package com.ldtsfeup2526.bobTheDestructor;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GUILanterna extends GUI {
    private Screen screen;

    public GUILanterna() throws IOException {
        TerminalSize terminalSize = new TerminalSize(1280, 720);

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();

        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);   // we will not use the cursor yet
        screen.startScreen();
        screen.doResizeIfNecessary();
    }

    @Override
    public void draw() throws IOException {
        screen.clear();
        screen.setCharacter(2,1, TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }
}
