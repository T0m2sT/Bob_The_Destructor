package com.ldtsfeup2526.bobTheDestructor.model.menu;

import java.util.List;

public abstract class Menu {
    private final List<Widget> widgets;
    private int currentButton = 0;

    public Menu() {
        this.widgets = createWidgets();
    }

    protected abstract List<Widget> createWidgets();

    public List<Widget> getWidgets() {
        return widgets;
    }

    public int getNumberOfWidgets() {
        return widgets.size();
    }

    public void moveDown() {
        getCurrentWidget().setWidgetState(WidgetState.UNSELECTED);
        currentButton++;
        currentButton %= getNumberOfWidgets();
        getCurrentWidget().setWidgetState(WidgetState.SELECTED);
    }

    public void moveUp() {
        getCurrentWidget().setWidgetState(WidgetState.UNSELECTED);
        currentButton += getNumberOfWidgets() - 1;
        currentButton %= getNumberOfWidgets();
        getCurrentWidget().setWidgetState(WidgetState.SELECTED);
    }

    public Widget getCurrentWidget() {
        return widgets.get(currentButton);
    }
}
