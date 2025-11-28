package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;

import java.io.IOException;

public abstract class ScreenViewer<T> {
    private final T model;

    public ScreenViewer(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void draw(GUI gui) throws IOException;

}
