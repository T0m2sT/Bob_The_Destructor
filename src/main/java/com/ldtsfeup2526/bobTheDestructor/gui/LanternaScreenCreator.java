package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class LanternaScreenCreator implements ScreenCreator {

    public Screen createScreen(Resolution resolution, int fontSize, String title, KeyListener keyListener) throws URISyntaxException, IOException, FontFormatException {
        TerminalSize terminalSize = new TerminalSize(resolution.getWidth(), resolution.getHeight());
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        terminalFactory.setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);

        AWTTerminalFontConfiguration font = loadFont(fontSize);
        terminalFactory.setTerminalEmulatorFontConfiguration(font);
        TerminalScreen screen = terminalFactory.createScreen();
        AWTTerminalFrame terminal = (AWTTerminalFrame) screen.getTerminal();
        terminal.getComponent(0).addKeyListener(keyListener);
        terminal.setTitle(title);
        return screen;
    }

    private AWTTerminalFontConfiguration loadFont(int fontSize) throws URISyntaxException, IOException, FontFormatException {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        File fontFile = new File(Objects.requireNonNull(resource).toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);

        return AWTTerminalFontConfiguration.newInstance(font);
    }
}
