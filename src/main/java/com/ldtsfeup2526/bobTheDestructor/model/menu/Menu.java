package com.ldtsfeup2526.bobTheDestructor.model.menu;

import java.util.List;

public abstract class Menu {
    private final List<Widget> widgets;
    private int currentWidget = 0;

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
        currentWidget++;
        currentWidget %= getNumberOfWidgets();
    }

    public void moveUp() {
        currentWidget += getNumberOfWidgets() - 1;
        currentWidget %= getNumberOfWidgets();
    }

    public Widget getCurrentWidget() {
        return widgets.get(currentWidget);
    }
}
