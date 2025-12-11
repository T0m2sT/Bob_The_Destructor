package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;

import java.io.IOException;
import java.util.List;

public abstract class ScreenViewer<T> {
    private final T model;

    public ScreenViewer(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void draw(GUI gui) throws IOException;

    protected <L extends ElementModel> void drawElement(GUI gui, L element, ElementViewer<L> viewer) {
        viewer.draw(element, gui);
    }

    protected  <L extends ElementModel> void drawElements(GUI gui, List<L> elements, ElementViewer<L> viewer) {
        for (L element: elements)
            drawElement(gui, element, viewer);
    }

}
