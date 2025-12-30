package com.ldtsfeup2526.bobTheDestructor.controller.input;

import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.states.SettingsMenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        parser.notifyStateChange(mock(MainMenuState.class));
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'a'));
        
        List<Action> actions = parser.get();
        assertEquals(1, actions.size());
        assertEquals(Action.LEFT, actions.get(0));

        actions = parser.get();
        assertEquals(0, actions.size());
    }

    @Test
    void testGetActionsWithHold() {
        parser.notifyStateChange(mock(GameState.class));
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'a'));
        
        List<Action> actions = parser.get();
        assertEquals(1, actions.size());
        assertEquals(Action.LEFT, actions.get(0));
        
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
    void testNotifyStateChangeHold() {
        // MainMenu -> No Hold
        parser.notifyStateChange(mock(MainMenuState.class));
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, 'a'));
        parser.get();
        assertTrue(reader.getInputFinished().contains(KeyEvent.VK_LEFT));

        // GameState -> Allow Hold
        parser.notifyStateChange(mock(GameState.class));
        reader.keyReleased(new KeyEvent(source, KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_LEFT, 'a'));
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, 'a'));
        parser.get();
        assertFalse(reader.getInputFinished().contains(KeyEvent.VK_LEFT));
        
        // Other (Settings) -> No Hold
        parser.notifyStateChange(mock(SettingsMenuState.class));
        parser.get();
        assertTrue(reader.getInputFinished().contains(KeyEvent.VK_LEFT));
    }

    @Test
    void testParseInputToggles() {
        // Test keys that always add to inputFinished regardless of allowKeyHold
        int[] toggleKeys = {KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE, KeyEvent.VK_SHIFT};
        
        parser.notifyStateChange(mock(GameState.class)); // Even if hold allowed
        
        for (int key : toggleKeys) {
            reader.keyReleased(new KeyEvent(source, KeyEvent.KEY_RELEASED, 0, 0, key, ' '));
            reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, 0, 0, key, ' '));
            parser.get();
            assertTrue(reader.getInputFinished().contains(key), "Key " + key + " should be in finished");
        }
    }

    @Test
    void testParseInputAllKeys() {
        int[] keys = {KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_RIGHT, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE, KeyEvent.VK_SHIFT, KeyEvent.VK_Z};
        Action[] expected = {Action.UP, Action.UP, Action.DOWN, Action.DOWN, Action.LEFT, Action.LEFT, Action.RIGHT, Action.RIGHT, Action.JUMP, Action.SELECT, Action.QUIT, Action.MINE, Action.NONE};
        
        parser.notifyStateChange(mock(MainMenuState.class));

        for (int i = 0; i < keys.length; i++) {
            reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keys[i], (char)0));
            
            reader.getInputFinished().clear();
            
            List<Action> actions = parser.get();
            if (expected[i] != Action.NONE) {
                assertTrue(actions.contains(expected[i]), "Failed for key: " + keys[i]);
            } else {
                assertEquals(0, actions.size());
            }
            
            if (keys[i] != KeyEvent.VK_Z) {
                assertTrue(reader.getInputFinished().contains(keys[i]), "Key should be finished: " + keys[i]);
            }
            
            reader.keyReleased(new KeyEvent(source, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, keys[i], (char)0));
        }
    }
    @Test
    void testActionNone() {
        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_Z, 'z'));
        List<Action> actions = parser.get();
        assertFalse(actions.contains(Action.NONE));
        assertEquals(0, actions.size());
    }
}
