package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;

import java.util.List;

public abstract class Menu {
    private final List<Button> buttons;
    private final SoundPlayer soundPlayer;
    private int currentButton = 0;

    public Menu() {
        this.buttons = createButtons();
        this.soundPlayer = createSoundPlayer();
    }

    protected abstract List<Button> createButtons();

    protected abstract SoundPlayer createSoundPlayer();

    public List<Button> getButtons() {
        return buttons;
    }

    public SoundPlayer getSoundPlayer() {return soundPlayer;}

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
