package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import javax.swing.*;
import java.io.IOException;

public class InputReaderLanterna implements InputReader {
    private final Screen screen;

    public InputReaderLanterna(Screen screen) {
        this.screen = screen;
    }

    public INPUT readNextInput() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) return INPUT.NONE;

        switch (keyStroke.getKeyType()) {
            case KeyType.ArrowRight: return INPUT.RIGHT;
            case KeyType.ArrowUp: return INPUT.UP;
            case KeyType.ArrowDown: return INPUT.DOWN;
            case KeyType.ArrowLeft: return INPUT.LEFT;
            case KeyType.Character:
                switch (keyStroke.getCharacter()) {
                    case 'q': return INPUT.QUIT;
                    default: return INPUT.NONE;
                }
            case KeyType.Escape, KeyType.EOF: return INPUT.QUIT;
            case KeyType.Enter: return INPUT.SELECT;
            default: return INPUT.NONE;
        }
    }
}
