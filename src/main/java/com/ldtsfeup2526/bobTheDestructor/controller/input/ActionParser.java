package com.ldtsfeup2526.bobTheDestructor.controller.input;

import javax.accessibility.AccessibleIcon;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ActionParser {
    private final InputReader inputReader = new InputReader();
    private List<Action> currentActions = new ArrayList<>();

    public InputReader getInputReader() {
        return inputReader;
    }

    public List<Action> get() {
        currentActions.clear();

        List<Integer> inputPressed = inputReader.getInputPressed();
        for (Integer integer : inputPressed) {
            Action action = parseInput(integer);

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
            default:
                return Action.NONE;
        }
    }
}
