package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;

public class Widget extends ElementModel {
    private final WidgetType widgetType;
    private WidgetState widgetState;

    public Widget(WidgetType widgetType) {
        this(widgetType, WidgetState.UNSELECTED, new Position(0, 0));
    }

    public Widget(WidgetType widgetType, WidgetState widgetState, Position position) {
        super(position);
        this.widgetState = widgetState;
        this.widgetType = widgetType;

    }

    public WidgetState getButtonState() {
        return widgetState;
    }

    public void setButtonState(WidgetState widgetState) {
        this.widgetState = widgetState;
    }

    public WidgetType getButtonType() {
        return widgetType;
    }
}
