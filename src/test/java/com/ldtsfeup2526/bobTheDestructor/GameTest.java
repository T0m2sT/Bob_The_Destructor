package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.states.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testGameConstructorPrints() throws Exception {
        try {
            new Game();
            String output = outContent.toString();
            assertTrue(output.contains("Starting GUI... "), "Console output should contain startup message");
        } catch (java.awt.HeadlessException e) {
            // Test skipped in headless environments to avoid headless exception;
        }
    }

    @Test
    void testSetState() throws Exception {
        try {
            Game game = new Game();
            State<?> newState = mock(State.class);
            
            game.setState(newState);

            Field stateField = Game.class.getDeclaredField("state");
            stateField.setAccessible(true);
            State<?> currentState = (State<?>) stateField.get(game);

            assertEquals(newState, currentState, "State should be updated to the new state");
        } catch (java.awt.HeadlessException e) {
            // Test skipped in headless environments to avoid headless exception;
        }
    }
}
