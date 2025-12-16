package com.ldtsfeup2526.bobTheDestructor.controller.input;

import com.ldtsfeup2526.bobTheDestructor.states.IGameStateObserver;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ActionParser implements IGameStateObserver {
    private final InputReader inputReader = new InputReader();
    private List<Action> currentActions = new ArrayList<>();
    private boolean allowKeyHold = false;

    public InputReader getInputReader() {
        return inputReader;
    }

    private void setAllowKeyHold(boolean allowKeyHold) {
        this.allowKeyHold = allowKeyHold;
    }

    public List<Action> get() {
        currentActions.clear();

        List<Integer> inputPressed = new ArrayList<>(inputReader.getInputPressed());
        for (Integer integer : inputPressed) {
            Action action = parseInput(integer);

            if (!allowKeyHold) {
                inputReader.addInputFinished(integer);
            }

            if (action != Action.NONE) {
                currentActions.add(parseInput(integer));
            }
        }
        inputReader.updateInputPressed();

        return currentActions;
    }

    private Action parseInput(Integer input) {
        switch (input) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                return Action.UP;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                return Action.DOWN;
            case KeyEvent.VK_LEFT:
            case  KeyEvent.VK_A:
                return Action.LEFT;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                return Action.RIGHT;
            case KeyEvent.VK_SPACE:
                inputReader.addInputFinished(input);
                return Action.JUMP;
            case KeyEvent.VK_ENTER:
                inputReader.addInputFinished(input);
                return Action.SELECT;
            case KeyEvent.VK_ESCAPE:
                inputReader.addInputFinished(input);
                return Action.QUIT;
            case KeyEvent.VK_SHIFT:
                inputReader.addInputFinished(input);
                return Action.MINE;
            default:
                return Action.NONE;
        }
    }

    @Override
    public void notifyStateChange(State<?> state) {
        if (state instanceof MainMenuState) {
            setAllowKeyHold(false);
        } else if (state instanceof GameState) {
            setAllowKeyHold(true);
        }
    }
}
