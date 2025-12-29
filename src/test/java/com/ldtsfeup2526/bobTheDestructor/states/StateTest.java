package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
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

        when(spriteLoader.get(anyString()))
                .thenAnswer(inv -> new Sprite(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));

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
        Game game = mock(Game.class);
        GUI gui = mock(GUI.class);
        ActionParser actionParser = mock(ActionParser.class);
        when(actionParser.get()).thenReturn(List.of(Action.NONE));

        double deltaTime = 0.016;

        state.update(game, gui, actionParser, deltaTime);

        verify(controller, times(1)).update(eq(game), eq(List.of(Action.NONE)));
        verify(screenViewer, times(1)).draw(eq(gui), anyDouble());
    }
}
