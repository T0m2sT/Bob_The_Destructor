package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputReaderLanternaTest {
    private Screen screen;
    private InputReaderLanterna inputReader;

    @BeforeEach
    void setUp() {
        screen = Mockito.mock(Screen.class);
        inputReader = new InputReaderLanterna(screen);
    }

    @Test
    void readNextInputUp() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        assertEquals(InputReader.INPUT.UP, inputReader.readNextInput());
    }

    @Test
    void readNextInputDown() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        assertEquals(InputReader.INPUT.DOWN, inputReader.readNextInput());
    }

    @Test
    void readNextInputRight() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowRight));
        assertEquals(InputReader.INPUT.RIGHT, inputReader.readNextInput());
    }

    @Test
    void readNextInputLeft() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        assertEquals(InputReader.INPUT.LEFT, inputReader.readNextInput());
    }

    @Test
    void readNextInputQuitEOF() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.EOF));
        assertEquals(InputReader.INPUT.QUIT, inputReader.readNextInput());
    }

    @Test
    void readNextInputQuitChar() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke('q', false, false));
        assertEquals(InputReader.INPUT.QUIT, inputReader.readNextInput());
    }

    @Test
    void readNextInputSelect() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(InputReader.INPUT.SELECT, inputReader.readNextInput());
    }

    @Test
    void readNextInputNone() throws IOException {
        Mockito.when(screen.pollInput()).thenReturn(null);
        assertEquals(InputReader.INPUT.NONE, inputReader.readNextInput());
    }

    @Test
    void readNextInputOther() throws IOException {
        // Example of a key that is not mapped
        Mockito.when(screen.pollInput()).thenReturn(new KeyStroke('a', false, false));
        assertEquals(InputReader.INPUT.NONE, inputReader.readNextInput());
    }
}