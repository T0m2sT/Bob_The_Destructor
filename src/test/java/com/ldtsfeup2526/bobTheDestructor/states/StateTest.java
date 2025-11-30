package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class StateTest {
    private State<Object> state;
    private Object model;
    private ScreenViewer<Object> screenViewer;
    private Controller<Object> controller;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws IOException {
        model = new Object();
        screenViewer = Mockito.mock(ScreenViewer.class);
        controller = Mockito.mock(Controller.class);

        SpriteLoader spriteLoader = Mockito.mock(SpriteLoader.class);
        when(spriteLoader.get(anyString())).thenReturn(Mockito.mock(Sprite.class));

        state = new State<>(model, spriteLoader) {
            @Override
            public ScreenViewer<Object> createScreenViewer(ViewerProvider viewerProvider) {
                return screenViewer;
            }

            @Override
            public Controller<Object> createController() {
                return controller;
            }
        };
    }

    @Test
    void getModelTest() {
        assertEquals(model, state.getModel());
    }

    @Test
    void updateTest() throws IOException {
        GUI gui = Mockito.mock(GUI.class);
        state.update(gui);

        verify(screenViewer, times(1)).draw(gui);
    }
}
