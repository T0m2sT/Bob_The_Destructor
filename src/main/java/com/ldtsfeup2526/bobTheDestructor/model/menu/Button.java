package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;

public class Button extends ElementModel {
    private final ButtonType buttonType;
    private ButtonState buttonState;

    public Button(ButtonType buttonType) {
        this(buttonType, ButtonState.UNSELECTED, new Position(0, 0));
    }

    public Button(ButtonType buttonType, ButtonState buttonState, Position position) {
        super(position);
        this.buttonState = buttonState;
        this.buttonType = buttonType;

    }

    public ButtonState getButtonState() {
        return buttonState;
    }

    public void setButtonState(ButtonState buttonState) {
        this.buttonState = buttonState;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }
}
