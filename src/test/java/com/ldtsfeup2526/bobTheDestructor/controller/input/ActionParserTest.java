package com.ldtsfeup2526.bobTheDestructor.controller.input;

import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ActionParserTest {
    private ActionParser parser;
    private InputReader reader;
    private Component source = new Component() {};

    @BeforeEach
    void setUp() {
        parser = new ActionParser();
        reader = parser.getInputReader();
    }

    @Test
    void testGetActionsNoHold() {
        parser.notifyStateChange(mock(MainMenuState.class)); // allowKeyHold = false
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'a'));
        
        List<Action> actions = parser.get();
        assertEquals(1, actions.size());
        assertEquals(Action.LEFT, actions.get(0));
        
        // Should be finished and removed in the same call
        actions = parser.get();
        assertEquals(0, actions.size());
    }

    @Test
    void testGetActionsWithHold() {
        parser.notifyStateChange(mock(GameState.class)); // allowKeyHold = true
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'a'));
        
        List<Action> actions = parser.get();
        assertEquals(1, actions.size());
        assertEquals(Action.LEFT, actions.get(0));
        
        // Should still be there
        actions = parser.get();
        assertEquals(1, actions.size());
        assertEquals(Action.LEFT, actions.get(0));
    }

    @Test
    void testParseInputUnknownKey() {
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_Z, 'z'));
        List<Action> actions = parser.get();
        assertEquals(0, actions.size());
    }

    @Test
    void testNotifyStateChangeOther() {
        parser.notifyStateChange(mock(GameState.class));
        parser.notifyStateChange(mock(com.ldtsfeup2526.bobTheDestructor.states.SettingsMenuState.class));
        // allowKeyHold should remain true if notifyStateChange doesn't change it for other states?
        // Actually the code only checks for MainMenuState and GameState.
        // If it was true, it stays true.
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'a'));
        List<Action> actions = parser.get();
        assertEquals(1, actions.size());
        actions = parser.get();
        assertEquals(1, actions.size());
    }

    @Test
    void testParseInputAllKeys() {
        int[] keys = {KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_RIGHT, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE, KeyEvent.VK_SHIFT};
        Action[] expected = {Action.UP, Action.UP, Action.DOWN, Action.DOWN, Action.LEFT, Action.LEFT, Action.RIGHT, Action.RIGHT, Action.JUMP, Action.SELECT, Action.QUIT, Action.MINE};

        for (int i = 0; i < keys.length; i++) {
            reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keys[i], (char)0));
            List<Action> actions = parser.get();
            assertTrue(actions.contains(expected[i]), "Failed for key: " + keys[i]);
            reader.keyReleased(new KeyEvent(source, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, keys[i], (char)0));
        }
    }
}
