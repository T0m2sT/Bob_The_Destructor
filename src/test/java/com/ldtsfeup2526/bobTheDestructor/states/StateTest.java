package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StateTest {
    private State<Object> state;
    private Object model;
    private SpriteLoader spriteLoader;
    private Controller<Object> controller;
    private ScreenViewer<Object> screenViewer;

    private SoundManager soundManager;

    @BeforeEach
    void setUp() throws IOException {
        model = new Object();
        spriteLoader = mock(SpriteLoader.class);
        soundManager = mock(SoundManager.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        controller = mock(Controller.class);
        screenViewer = mock(ScreenViewer.class);

        state = new State<Object>(model, spriteLoader, soundManager) {
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
    void testGetModel() {
        assertEquals(model, state.getModel());
    }

    @Test
    void testUpdate() throws IOException {
        Game game = mock(Game.class);
        GUI gui = mock(GUI.class);
        com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser actionParser = mock(com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser.class);
        when(actionParser.get()).thenReturn(java.util.List.of());
        
        state.update(game, gui, actionParser, 0.1);
        
        verify(controller).update(eq(game), anyList(), anyDouble());
        verify(screenViewer).draw(gui, 0.1);
    }
    @Test
    void testOnEnterExit() {
        Game game = mock(Game.class);
        state.onEnter(game);
        state.onExit(game);
    }

    @Test
    void testEndState() throws IOException {
        com.ldtsfeup2526.bobTheDestructor.model.stats.Stats stats = mock(com.ldtsfeup2526.bobTheDestructor.model.stats.Stats.class);
        EndState endState = new EndState(stats, spriteLoader, soundManager);
        
        ViewerProvider provider = mock(ViewerProvider.class);
        assertNotNull(endState.createScreenViewer(provider));
        assertNotNull(endState.createController());
    }

    @Test
    void testCreditsState() throws IOException {
        com.ldtsfeup2526.bobTheDestructor.model.credits.Credits credits = mock(com.ldtsfeup2526.bobTheDestructor.model.credits.Credits.class);
        CreditsState creditsState = new CreditsState(credits, spriteLoader, soundManager);
        
        ViewerProvider provider = mock(ViewerProvider.class);
        assertNotNull(creditsState.createScreenViewer(provider));
        assertNotNull(creditsState.createController());
    }
}
