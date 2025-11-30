package com.ldtsfeup2526.bobTheDestructor.controller.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputReader implements KeyListener {
    private List<Integer> inputPressed = new ArrayList<>();
    private List<Integer> inputFinished = new ArrayList<>();

    public List<Integer> getInputPressed() {
        return inputPressed;
    }

    public List<Integer> getInputFinished() {
        return inputFinished;
    }

    public void addInputFinished(Integer integer) {
        if (!inputFinished.contains(integer)) {
            inputFinished.add(integer);
        }
    }

    public void updateInputPressed() {
        for (Integer integer : inputFinished) {
            inputPressed.remove(integer);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Integer keyCode = e.getKeyCode();

        if (!inputPressed.contains(keyCode) && !inputFinished.contains(keyCode)) {
            inputPressed.add(keyCode);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        Integer keyCode = e.getKeyCode();

        inputPressed.remove(keyCode);
        inputFinished.remove(keyCode);
    }

}
