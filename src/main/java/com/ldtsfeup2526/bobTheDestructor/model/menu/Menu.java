package com.ldtsfeup2526.bobTheDestructor.model.menu;

import java.util.List;

public abstract class Menu {
    private final List<Button> buttons;
    private int currentButton = 0;

    public Menu() {
        this.buttons = createButtons();
    }

    protected abstract List<Button> createButtons();

    public List<Button> getButtons() {
        return buttons;
    }

    public int getNumberOfButtons() {
        return buttons.size();
    }

    public void moveDown() {
        getCurrentButton().setButtonState(ButtonState.UNSELECTED);
        currentButton++;
        currentButton %= getNumberOfButtons();
        getCurrentButton().setButtonState(ButtonState.SELECTED);
    }

    public void moveUp() {
        getCurrentButton().setButtonState(ButtonState.UNSELECTED);
        currentButton += getNumberOfButtons() - 1;
        currentButton %= getNumberOfButtons();
        getCurrentButton().setButtonState(ButtonState.SELECTED);
    }

    public Button getCurrentButton() {
        return buttons.get(currentButton);
    }
}
