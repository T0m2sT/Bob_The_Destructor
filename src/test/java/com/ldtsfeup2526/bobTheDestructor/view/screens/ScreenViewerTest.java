package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScreenViewerTest {

    @Test
    void getModelTest() {
        Object model = new Object();

        ScreenViewer<Object> screenViewer = new ScreenViewer<>(model) {
            @Override
            public void draw(GUI gui) {}
        };

        assertEquals(model, screenViewer.getModel());
    }
}
