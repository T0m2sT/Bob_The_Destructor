package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
/*
public class InputReaderLanterna implements InputReader {
    private final Screen screen;

    public InputReaderLanterna(Screen screen) {
        this.screen = screen;
    }

    @Override
    public Input readNextInput() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) return Input.NONE;

        switch (keyStroke.getKeyType()) {
            case KeyType.ArrowRight: return Input.RIGHT;
            case KeyType.ArrowUp: return Input.UP;
            case KeyType.ArrowDown: return Input.DOWN;
            case KeyType.ArrowLeft: return Input.LEFT;
            case KeyType.Character:
                switch (keyStroke.getCharacter()) {
                    case 'q': return Input.QUIT;
                    default: return Input.NONE;
                }
            case KeyType.Escape, KeyType.EOF: return Input.QUIT;
            case KeyType.Enter: return Input.SELECT;
            default: return Input.NONE;
        }
    }
}
*/